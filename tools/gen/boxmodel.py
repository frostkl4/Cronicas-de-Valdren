"""Mini compilador de modelos de caixa (estilo Blockbench / Minecraft Java).

Um modelo e uma arvore de Parts (ossos) com Cubes. A partir da mesma definicao gera:
  * o metodo createBodyLayer() em Java (Forge 1.20.1, mapeamentos oficiais)
  * a textura (box UV) pintada por funcoes de material
  * uma textura de brilho (camada emissiva) opcional
  * uma previa em PNG (renderizador simples por software)
  * um arquivo .bbmodel para abrir no Blockbench Web

Convencoes (iguais ao ModelPart do Minecraft):
  * Y cresce para baixo, a entidade olha para -Z, o chao fica em y = 24.
  * Regiao de UV de um cubo (w, h, d) em (u, v):
        topo   (u+d,       v)     w x d
        baixo  (u+d+w,     v)     w x d
        oeste  (u,         v+d)   d x h   (lado -X)
        frente (u+d,       v+d)   w x h   (-Z)
        leste  (u+d+w,     v+d)   d x h   (+X)
        tras   (u+2d+w,    v+d)   w x h   (+Z)
"""
import base64
import io
import json
import math
import uuid

from PIL import Image, ImageDraw

FACES = ("top", "bottom", "west", "north", "east", "south")
NORMALS = {"top": (0, -1, 0), "bottom": (0, 1, 0), "west": (-1, 0, 0),
           "east": (1, 0, 0), "north": (0, 0, -1), "south": (0, 0, 1)}


class Cube:
    def __init__(self, x, y, z, w, h, d, mat="base", inflate=0.0, tag=None, mirror=False):
        self.o = (float(x), float(y), float(z))
        self.s = (int(w), int(h), int(d))
        self.mat = mat
        self.inflate = inflate
        self.tag = tag or {}
        self.uv = None
        self.mirror = mirror

    def region(self, face):
        u, v = self.uv
        w, h, d = self.s
        return {
            "top": (u + d, v, w, d),
            "bottom": (u + d + w, v, w, d),
            "west": (u, v + d, d, h),
            "north": (u + d, v + d, w, h),
            "east": (u + d + w, v + d, d, h),
            "south": (u + 2 * d + w, v + d, w, h),
        }[face]

    def texel_point(self, face, i, j):
        """Centro do texel (i, j) da face, em coordenadas locais da parte."""
        x0, y0, z0 = self.o
        w, h, d = self.s
        x1, y1, z1 = x0 + w, y0 + h, z0 + d
        if face == "north":
            return (x0 + i + .5, y0 + j + .5, z0)
        if face == "south":
            return (x1 - i - .5, y0 + j + .5, z1)
        if face == "west":
            return (x0, y0 + j + .5, z1 - i - .5)
        if face == "east":
            return (x1, y0 + j + .5, z0 + i + .5)
        if face == "top":
            return (x0 + i + .5, y0, z1 - j - .5)
        return (x0 + i + .5, y1, z1 - j - .5)


class Part:
    def __init__(self, name, pivot=(0, 0, 0), rot=(0, 0, 0), cubes=None):
        self.name = name
        self.pivot = tuple(float(a) for a in pivot)
        self.rot = tuple(float(a) for a in rot)
        self.cubes = list(cubes or [])
        self.children = []
        self.parent = None

    def add(self, part):
        part.parent = self
        self.children.append(part)
        return part

    def cube(self, *a, **k):
        c = Cube(*a, **k)
        self.cubes.append(c)
        return c

    def walk(self):
        yield self
        for c in self.children:
            yield from c.walk()

    def abs_pivot(self):
        p = self.pivot
        q = self.parent
        while q is not None:
            p = (p[0] + q.pivot[0], p[1] + q.pivot[1], p[2] + q.pivot[2])
            q = q.parent
        return p


class Model:
    def __init__(self, name, tw, th, roots):
        self.name = name
        self.tw, self.th = tw, th
        self.roots = roots

    def parts(self):
        for r in self.roots:
            yield from r.walk()

    def cubes(self):
        for p in self.parts():
            for c in p.cubes:
                yield p, c

    # ---------------------------------------------------------------- UV
    def pack(self):
        items = [c for _, c in self.cubes()]
        items.sort(key=lambda c: (-(c.s[2] + c.s[1]), -(2 * (c.s[2] + c.s[0]))))
        x = y = 0
        shelf_h = 0
        for c in items:
            w = 2 * (c.s[2] + c.s[0])
            h = c.s[2] + c.s[1]
            if x + w > self.tw:
                x = 0
                y += shelf_h
                shelf_h = 0
            if w > self.tw:
                raise ValueError(f"{self.name}: cubo largo demais para a textura ({w})")
            c.uv = (x, y)
            x += w
            shelf_h = max(shelf_h, h)
        if y + shelf_h > self.th:
            raise ValueError(f"{self.name}: textura {self.tw}x{self.th} pequena demais (precisa {y + shelf_h})")

    # ---------------------------------------------------------- textura
    def paint(self, painters, glow=False):
        img = Image.new("RGBA", (self.tw, self.th), (0, 0, 0, 0))
        gimg = Image.new("RGBA", (self.tw, self.th), (0, 0, 0, 0)) if glow else None
        px = img.load()
        gx = gimg.load() if glow else None
        for part, c in self.cubes():
            fn = painters if callable(painters) else painters[c.mat]
            ap = part.abs_pivot()
            for face in FACES:
                u, v, fw, fh = c.region(face)
                for j in range(fh):
                    for i in range(fw):
                        lp = c.texel_point(face, i, j)
                        gp = (lp[0] + ap[0], lp[1] + ap[1], lp[2] + ap[2])
                        ctx = Ctx(face, i, j, fw, fh, lp, gp, c, part)
                        out = fn(ctx)
                        if isinstance(out, tuple) and len(out) == 2 and isinstance(out[0], tuple):
                            col, g = out
                        else:
                            col, g = out, None
                        if col is not None:
                            px[u + i, v + j] = _rgba(col)
                        if glow and g is not None:
                            gx[u + i, v + j] = _rgba(g)
        return img, gimg

    # -------------------------------------------------------------- java
    def java_layer(self, indent="        "):
        lines = [f"{indent}MeshDefinition mesh = new MeshDefinition();",
                 f"{indent}PartDefinition root = mesh.getRoot();"]
        names = {}

        def var(p):
            return "p_" + p.name

        def emit(p, parent_var):
            cb = "CubeListBuilder.create()"
            for c in p.cubes:
                cb += f".texOffs({c.uv[0]}, {c.uv[1]})"
                if c.mirror:
                    cb += ".mirror()"
                x, y, z = c.o
                w, h, d = c.s
                cb += (f".addBox({jf(x)}, {jf(y)}, {jf(z)}, {jf(w)}, {jf(h)}, {jf(d)}, "
                       f"new CubeDeformation({jf(c.inflate)}))")
                if c.mirror:
                    cb += ".mirror(false)"
            px_, py_, pz_ = p.pivot
            rx, ry, rz = p.rot
            pose = (f"PartPose.offsetAndRotation({jf(px_)}, {jf(py_)}, {jf(pz_)}, "
                    f"{jf(rx)}, {jf(ry)}, {jf(rz)})")
            prefix = f"PartDefinition {var(p)} = " if p.children else ""
            lines.append(f"{indent}{prefix}{parent_var}.addOrReplaceChild(\"{p.name}\", {cb}, {pose});")
            names[p.name] = p
            for ch in p.children:
                emit(ch, var(p))

        for r in self.roots:
            emit(r, "root")
        lines.append(f"{indent}return LayerDefinition.create(mesh, {self.tw}, {self.th});")
        return "\n".join(lines)

    def java_fields(self, wanted):
        """Declaracoes + atribuicoes no construtor para as partes pedidas."""
        decl, assign = [], []
        for p in self.parts():
            if p.name not in wanted:
                continue
            chain = []
            q = p
            while q is not None:
                chain.append(q.name)
                q = q.parent
            chain.reverse()
            expr = "root" + "".join(f".getChild(\"{n}\")" for n in chain)
            decl.append(f"    private final ModelPart {camel(p.name)};")
            assign.append(f"        this.{camel(p.name)} = {expr};")
        return "\n".join(decl), "\n".join(assign)

    # ----------------------------------------------------------- previa
    def preview(self, tex, size=420, yaw=35, pitch=20, pose=None, scale=None, bg=(34, 36, 44)):
        """Renderiza o modelo com a textura. pose: dict nome -> (rx, ry, rz) somados."""
        pose = pose or {}
        quads = []
        tpx = tex.load()

        def rot_matrix(rx, ry, rz):
            cx, sx = math.cos(rx), math.sin(rx)
            cy, sy = math.cos(ry), math.sin(ry)
            cz, sz = math.cos(rz), math.sin(rz)
            Rx = [[1, 0, 0], [0, cx, -sx], [0, sx, cx]]
            Ry = [[cy, 0, sy], [0, 1, 0], [-sy, 0, cy]]
            Rz = [[cz, -sz, 0], [sz, cz, 0], [0, 0, 1]]
            return mm(Rz, mm(Ry, Rx))

        def walk(p, M, T):
            r = p.rot
            extra = pose.get(p.name, (0, 0, 0))
            R = rot_matrix(r[0] + extra[0], r[1] + extra[1], r[2] + extra[2])
            pt = add(T, mv(M, p.pivot))
            M2 = mm(M, R)
            for c in p.cubes:
                infl = c.inflate
                for face in FACES:
                    u, v, fw, fh = c.region(face)
                    for j in range(fh):
                        for i in range(fw):
                            col = tpx[u + i, v + j]
                            if col[3] < 8:
                                continue
                            corners = texel_corners(c, face, i, j, infl)
                            wc = [add(pt, mv(M2, k)) for k in corners]
                            quads.append((wc, col, face, mv(M2, NORMALS[face])))
            for ch in p.children:
                walk(ch, M2, pt)

        I3 = [[1, 0, 0], [0, 1, 0], [0, 0, 1]]
        for r in self.roots:
            walk(r, I3, (0, 0, 0))
        # Java -> mundo: x e y invertidos (igual ao renderizador do jogo)
        cyw, syw = math.cos(math.radians(yaw)), math.sin(math.radians(yaw))
        cp, sp = math.cos(math.radians(pitch)), math.sin(math.radians(pitch))
        proj = []
        allx, ally = [], []
        def view(x, y, z):
            # Java -> mundo do jogo (-x, 24-y, z) e giro de 180 graus para ver a frente
            X, Y, Z = x, 24 - y, -z
            X, Z = X * cyw - Z * syw, X * syw + Z * cyw
            Y, Z = Y * cp - Z * sp, Y * sp + Z * cp
            return (X, Y, Z)

        for wc, col, face, n in quads:
            pts = [view(*k) for k in wc]
            # normal: mesma rotacao, sem translacao
            X, Y, Z = n[0], -n[1], -n[2]
            X, Z = X * cyw - Z * syw, X * syw + Z * cyw
            Y, Z = Y * cp - Z * sp, Y * sp + Z * cp
            proj.append((pts, col, face, (X, Y, Z)))
            for p_ in pts:
                allx.append(p_[0])
                ally.append(p_[1])
        if not proj:
            return Image.new("RGB", (size, size), bg)
        minx, maxx, miny, maxy = min(allx), max(allx), min(ally), max(ally)
        span = max(maxx - minx, maxy - miny) * 1.08
        sc = scale or (size / span)
        cx_, cy_ = (minx + maxx) / 2, (miny + maxy) / 2
        img = Image.new("RGB", (size, size), bg)
        dr = ImageDraw.Draw(img)
        shade = {"top": 1.0, "bottom": .55, "north": .86, "south": .74, "west": .8, "east": .8}
        proj.sort(key=lambda q: sum(p[2] for p in q[0]) / 4.0, reverse=False)
        for pts, col, face, nrm in proj:
            if nrm[2] < -1e-6:  # face de costas para a camera
                continue
            f = shade[face]
            c = (int(col[0] * f), int(col[1] * f), int(col[2] * f))
            poly = [((p[0] - cx_) * sc + size / 2, size / 2 - (p[1] - cy_) * sc) for p in pts]
            dr.polygon(poly, fill=c)
        return img

    # ------------------------------------------------------ blockbench
    def bbmodel(self, tex):
        buf = io.BytesIO()
        tex.save(buf, "PNG")
        data = "data:image/png;base64," + base64.b64encode(buf.getvalue()).decode()
        elements = []

        def group(p):
            ap = p.abs_pivot()
            origin = [-ap[0], 24 - ap[1], ap[2]]
            g = {"name": p.name, "origin": r3(origin),
                 "rotation": r3([-math.degrees(p.rot[0]), -math.degrees(p.rot[1]), math.degrees(p.rot[2])]),
                 "color": 0, "uuid": str(uuid.uuid4()), "export": True, "isOpen": False,
                 "locked": False, "visibility": True, "autouv": 0, "children": []}
            for c in p.cubes:
                x0, y0, z0 = (c.o[0] + ap[0], c.o[1] + ap[1], c.o[2] + ap[2])
                x1, y1, z1 = x0 + c.s[0], y0 + c.s[1], z0 + c.s[2]
                el = {"name": p.name, "box_uv": True, "rescale": False, "locked": False,
                      "from": r3([-x1, 24 - y1, z0]), "to": r3([-x0, 24 - y0, z1]),
                      "autouv": 0, "color": 0, "inflate": c.inflate, "origin": r3(origin),
                      "uv_offset": list(c.uv), "mirror_uv": c.mirror, "type": "cube",
                      "uuid": str(uuid.uuid4()), "faces": {}}
                elements.append(el)
                g["children"].append(el["uuid"])
            for ch in p.children:
                g["children"].append(group(ch))
            return g

        outliner = [group(r) for r in self.roots]
        return {
            "meta": {"format_version": "4.5", "model_format": "modded_entity", "box_uv": True},
            "name": self.name, "model_identifier": self.name,
            "modded_entity_entity_class": "", "modded_entity_version": "1.17", "modded_entity_flip_y": True,
            "visible_box": [1, 1, 0], "variable_placeholders": "", "variable_placeholder_buttons": [],
            "timeline_setups": [], "unhandled_root_fields": {},
            "resolution": {"width": self.tw, "height": self.th},
            "elements": elements, "outliner": outliner,
            "textures": [{"path": "", "name": self.name + ".png", "folder": "", "namespace": "",
                          "id": "0", "width": self.tw, "height": self.th, "uv_width": self.tw,
                          "uv_height": self.th, "particle": False, "render_mode": "default",
                          "render_sides": "auto", "frame_time": 1, "visible": True, "mode": "bitmap",
                          "saved": True, "uuid": str(uuid.uuid4()), "source": data}],
        }


class Ctx:
    __slots__ = ("face", "i", "j", "fw", "fh", "lp", "gp", "cube", "part")

    def __init__(self, face, i, j, fw, fh, lp, gp, cube, part):
        self.face, self.i, self.j, self.fw, self.fh = face, i, j, fw, fh
        self.lp, self.gp, self.cube, self.part = lp, gp, cube, part

    @property
    def edge(self):
        """Distancia (em texels) ate a borda mais proxima da face."""
        return min(self.i, self.j, self.fw - 1 - self.i, self.fh - 1 - self.j)

    @property
    def vfrac(self):
        """0 no alto do cubo, 1 embaixo (laterais); topo=0, baixo=1."""
        if self.face == "top":
            return 0.0
        if self.face == "bottom":
            return 1.0
        return (self.j + .5) / max(1, self.fh)

    @property
    def is_side(self):
        return self.face in ("north", "south", "west", "east")


def texel_corners(c, face, i, j, infl):
    x0, y0, z0 = c.o
    w, h, d = c.s
    x1, y1, z1 = x0 + w, y0 + h, z0 + d
    x0 -= infl; y0 -= infl; z0 -= infl
    x1 += infl; y1 += infl; z1 += infl
    sx = (x1 - x0) / max(w, 1)
    sy = (y1 - y0) / max(h, 1)
    sz = (z1 - z0) / max(d, 1)
    if face == "north":
        a, b = x0 + i * sx, y0 + j * sy
        return [(a, b, z0), (a + sx, b, z0), (a + sx, b + sy, z0), (a, b + sy, z0)]
    if face == "south":
        a, b = x1 - i * sx, y0 + j * sy
        return [(a, b, z1), (a - sx, b, z1), (a - sx, b + sy, z1), (a, b + sy, z1)]
    if face == "west":
        a, b = z1 - i * sz, y0 + j * sy
        return [(x0, b, a), (x0, b, a - sz), (x0, b + sy, a - sz), (x0, b + sy, a)]
    if face == "east":
        a, b = z0 + i * sz, y0 + j * sy
        return [(x1, b, a), (x1, b, a + sz), (x1, b + sy, a + sz), (x1, b + sy, a)]
    if face == "top":
        a, b = x0 + i * sx, z1 - j * sz
        return [(a, y0, b), (a, y0, b - sz), (a + sx, y0, b - sz), (a + sx, y0, b)]
    a, b = x0 + i * sx, z1 - j * sz
    return [(a, y1, b), (a + sx, y1, b), (a + sx, y1, b - sz), (a, y1, b - sz)]


def mm(A, B):
    return [[sum(A[r][k] * B[k][c] for k in range(3)) for c in range(3)] for r in range(3)]


def mv(M, v):
    return tuple(M[r][0] * v[0] + M[r][1] * v[1] + M[r][2] * v[2] for r in range(3))


def add(a, b):
    return (a[0] + b[0], a[1] + b[1], a[2] + b[2])


def sub(a, b):
    return (a[0] - b[0], a[1] - b[1], a[2] - b[2])


def cross(a, b):
    return (a[1] * b[2] - a[2] * b[1], a[2] * b[0] - a[0] * b[2], a[0] * b[1] - a[1] * b[0])


def r3(v):
    return [round(float(a), 4) for a in v]


def jf(v):
    v = float(v)
    s = f"{v:.4f}".rstrip("0").rstrip(".")
    if s in ("-0", ""):
        s = "0"
    if "." not in s:
        s += ".0"
    return s + "F"


def camel(name):
    parts = name.split("_")
    return parts[0] + "".join(p.capitalize() for p in parts[1:])


def _rgba(c):
    if len(c) == 3:
        return (int(c[0]), int(c[1]), int(c[2]), 255)
    return tuple(int(a) for a in c)


def save_bbmodel(model, tex, path):
    with open(path, "w") as f:
        json.dump(model.bbmodel(tex), f)
