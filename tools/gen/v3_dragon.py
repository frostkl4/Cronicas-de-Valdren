"""Vorthrax v3: geometria esculpida.

Ferramentas de escultura:
  rbox / rbox_y  volumes com arestas chanfradas (seção octogonal), em vez de caixas puras
  chain          cadeia de segmentos que afinam e giram (chifres, garras, espinhos curvos)
  spike          espinho inclinado para trás, feito de 3 degraus
  membrane       painel de membrana em degraus entre dois dedos da asa

Coordenadas: unidades de 1/16 de bloco, Y para baixo, chão em y = 24, a cabeça aponta para -Z.
"""
import math

from boxmodel import Part, Model
from v2_common import mirror_part


# ------------------------------------------------------------------ ferramentas
def rbox(p, x, y, z, w, h, d, r, mat, tag=None):
    """Caixa ao longo de Z com as 4 arestas longas chanfradas (seção octogonal no plano XY)."""
    tag = tag or {}
    if r <= 0 or w <= 2 * r or h <= 2 * r:
        p.cube(x, y, z, w, h, d, mat, tag=tag)
        return
    p.cube(x, y + r, z, w, h - 2 * r, d, mat, tag=tag)
    p.cube(x + r, y, z, w - 2 * r, r, d, mat, tag=dict(tag))
    p.cube(x + r, y + h - r, z, w - 2 * r, r, d, mat, tag=dict(tag))


def rbox_y(p, x, y, z, w, h, d, r, mat, tag=None):
    """Caixa ao longo de Y com as arestas verticais chanfradas (seção octogonal no plano XZ)."""
    tag = tag or {}
    if r <= 0 or w <= 2 * r or d <= 2 * r:
        p.cube(x, y, z, w, h, d, mat, tag=tag)
        return
    p.cube(x, y, z + r, w, h, d - 2 * r, mat, tag=tag)
    p.cube(x + r, y, z, w - 2 * r, h, r, mat, tag=dict(tag))
    p.cube(x + r, y, z + d - r, w - 2 * r, h, r, mat, tag=dict(tag))


def chain(parent, name, pivot, rot, segs, mat, axis="z", tip_mat=None):
    """Cadeia curva. segs: [(espessura, comprimento, (rx, ry, rz) do próximo)]. Cresce em +axis."""
    p = parent.add(Part(name, pivot, rot))
    first = p
    for k, (t, ln, nxt) in enumerate(segs):
        m = tip_mat if (tip_mat and k == len(segs) - 1) else mat
        if axis == "z":
            p.cube(-t / 2, -t / 2, 0, t, t, ln, m)
            off = (0, 0, ln - 0.5)
        elif axis == "-z":
            p.cube(-t / 2, -t / 2, -ln, t, t, ln, m)
            off = (0, 0, -ln + 0.5)
        elif axis == "-y":
            p.cube(-t / 2, -ln, -t / 2, t, ln, t, m)
            off = (0, -ln + 0.5, 0)
        else:  # "y"
            p.cube(-t / 2, 0, -t / 2, t, ln, t, m)
            off = (0, ln - 0.5, 0)
        if k < len(segs) - 1:
            p = p.add(Part(f"{name}_{k + 2}", off, nxt))
    return first


def spike(parent, name, x, y, z, base, height, mat="spine", lean=-0.35, tip="crys"):
    """Espinho dorsal de 3 degraus inclinado para trás (lean negativo = para trás)."""
    p = parent.add(Part(name, (x, y, z), (lean, 0, 0)))
    h1 = max(1, round(height * 0.45))
    h2 = max(1, round(height * 0.35))
    h3 = max(1, height - h1 - h2)
    p.cube(-base / 2, -h1, -base / 2, base, h1, base, mat)
    b2 = max(1, base - 1)
    p.cube(-b2 / 2, -h1 - h2, -b2 / 2, b2, h2, b2, mat)
    b3 = max(1, base - 2)
    p.cube(-b3 / 2, -h1 - h2 - h3, -b3 / 2, b3, h3, b3, tip)
    return p


def membrane(p, length, gap, mat="memb", steps=4, thick=1):
    """Painel em degraus atrás de um dedo (dedo ao longo de +X, membrana para +Z)."""
    for s in range(steps):
        x0 = length * s / steps
        x1 = length * (s + 1) / steps
        xm = (x0 + x1) / 2
        depth = max(2, int(round(math.tan(gap) * xm)))
        p.cube(round(x0), 0, 0.5, max(1, round(x1) - round(x0)), thick, depth, mat,
               tag={"membrane": True, "step": s, "steps": steps})


# ------------------------------------------------------------ pontos no mundo
def _rot(rx, ry, rz):
    cx, sx, cy, sy, cz, sz = math.cos(rx), math.sin(rx), math.cos(ry), math.sin(ry), math.cos(rz), math.sin(rz)
    Rx = [[1, 0, 0], [0, cx, -sx], [0, sx, cx]]
    Ry = [[cy, 0, sy], [0, 1, 0], [-sy, 0, cy]]
    Rz = [[cz, -sz, 0], [sz, cz, 0], [0, 0, 1]]
    return _mm(Rz, _mm(Ry, Rx))


def _mm(A, B):
    return [[sum(A[r][k] * B[k][c] for k in range(3)) for c in range(3)] for r in range(3)]


def _mv(M, v):
    return tuple(M[r][0] * v[0] + M[r][1] * v[1] + M[r][2] * v[2] for r in range(3))


def lowest_point(part, pose=None, M=None, T=(0, 0, 0)):
    """Maior Y (ponto mais baixo) da subárvore, já com as rotações."""
    pose = pose or {}
    M = M or [[1, 0, 0], [0, 1, 0], [0, 0, 1]]
    ex = pose.get(part.name, (0, 0, 0))
    R = _rot(part.rot[0] + ex[0], part.rot[1] + ex[1], part.rot[2] + ex[2])
    pt = tuple(T[k] + _mv(M, part.pivot)[k] for k in range(3))
    M2 = _mm(M, R)
    best = -1e9
    for c in part.cubes:
        x0, y0, z0 = c.o
        w, h, d = c.s
        for cx in (x0, x0 + w):
            for cy in (y0, y0 + h):
                for cz in (z0, z0 + d):
                    v = _mv(M2, (cx, cy, cz))
                    best = max(best, pt[1] + v[1])
    for ch in part.children:
        best = max(best, lowest_point(ch, pose, M2, pt))
    return best


# ===================================================================== modelo
BODY_Y = -2  # pivô do corpo (bloco absoluto)


def build(pose_for_ground=None):
    body = Part("body", (0, BODY_Y, 0))

    # ---------------- tronco: peito largo, cintura fina, quadril médio
    rbox(body, -10, -10, -18, 20, 18, 20, 3, "scale")
    body.cube(-9.5, 0, -21, 7, 8, 9, "scale")
    body.cube(2.5, 0, -21, 7, 8, 9, "scale")
    body.cube(-2, 4, -22, 4, 5, 6, "plate")
    body.cube(-12, -11, -16, 5, 7, 11, "scale_d")
    body.cube(7, -11, -16, 5, 7, 11, "scale_d")
    body.cube(-13, -12.5, -17, 6, 3, 12, "plate")
    body.cube(7, -12.5, -17, 6, 3, 12, "plate")
    body.cube(-12.5, -10, -18.5, 3, 5, 9, "plate")
    body.cube(9.5, -10, -18.5, 3, 5, 9, "plate")
    for k, x in enumerate((-6, -2, 2)):
        body.cube(x, -11, -19.5, 4, 3, 3, "plate")
    rbox(body, -9, -9, -1, 18, 16, 5, 3, "scale")
    rbox(body, -8, -8, 2, 16, 14, 16, 3, "scale")
    rbox(body, -9, -9, 18, 18, 15, 13, 3, "scale")
    body.cube(-6, -11, 22, 12, 3, 7, "scale_d")
    # placas do ventre, sobrepostas
    for k, z in enumerate(range(-17, 30, 5)):
        bottom = 8 if z < 0 else 6
        w = 12 if z < 0 else (10 if z < 18 else 11)
        body.cube(-w / 2, bottom - 1, z, w, 2, 6, "plate")
    # escamas salientes nos flancos
    for z in (4, 9, 14):
        body.cube(-9, -4, z, 1, 6, 4, "plate")
        body.cube(8, -4, z, 1, 6, 4, "plate")
    for z in (-14, -8):
        body.cube(-11, -5, z, 1, 7, 5, "plate")
        body.cube(10, -5, z, 1, 7, 5, "plate")
    # crista dorsal
    tops = [(-15, -10, 10, 4), (-10, -10, 12, 4), (-5, -10, 10, 4), (0, -9, 8, 3), (5, -8, 7, 3), (10, -8, 6, 3),
            (15, -8, 6, 3), (21, -11, 6, 3), (27, -11, 5, 2)]
    for k, (z, y, hgt, b) in enumerate(tops):
        spike(body, f"crest_{k}", 0, y, z, b, hgt)
    # cristais de raio nos ombros
    for side, sx in (("r", -1), ("l", 1)):
        cl = body.add(Part(f"crystals_{side}", (6 * sx, -10, -10), (0.2, 0, 0.45 * sx)))
        cl.cube(-1, -6, -1, 2, 6, 2, "crys")
        a = cl.add(Part(f"crystals_{side}_b", (1.5 * sx, 0, 1.5), (-0.3, 0, 0.4 * sx)))
        a.cube(-0.5, -4, -0.5, 1, 4, 1, "crys")
        b = cl.add(Part(f"crystals_{side}_c", (-1.0 * sx, 0, -1.5), (0.35, 0, -0.2 * sx)))
        b.cube(-0.5, -3, -0.5, 1, 3, 1, "crys")

    # ---------------- pescoço em S (5 segmentos)
    neck_specs = [("neck1", (0, -6, -19), 9, 15, 13, -0.8), ("neck2", None, 8, 13, 12, -0.35),
                  ("neck3", None, 8, 12, 11, 0.15), ("neck4", None, 8, 11, 10, 0.45), ("neck5", None, 7, 10, 9, 0.4)]
    parent, prev_len = body, 0
    for k, (nm, pv, ln, w, h, rx) in enumerate(neck_specs):
        seg = parent.add(Part(nm, pv if pv else (0, 0, -prev_len), (rx, 0, 0)))
        rbox(seg, -w / 2, -h / 2, -ln - 1, w, h, ln + 2, 2, "scale")
        seg.cube(-w / 2 + 2, h / 2 - 1, -ln, w - 4, 2, ln, "plate")
        spike(seg, f"{nm}_spike", 0, -h / 2, -ln / 2, 3, 6)
        parent, prev_len = seg, ln

    # ---------------- cabeça esculpida
    head = parent.add(Part("head", (0, 0, -7), (0.15, 0, 0)))
    rbox(head, -7, -7, -11, 14, 11, 12, 2, "scale")
    head.cube(-5, -9, -3, 10, 3, 5, "scale_d")
    head.cube(-8, -9, -14, 5, 4, 8, "scale_d")
    head.cube(3, -9, -14, 5, 4, 8, "scale_d")
    head.cube(-7.6, -5, -13, 1, 2, 3, "eye")
    head.cube(6.6, -5, -13, 1, 2, 3, "eye")
    head.cube(-8.5, -3, -11, 3, 5, 7, "scale")
    head.cube(5.5, -3, -11, 3, 5, 7, "scale")
    rbox(head, -5.5, -6, -23, 11, 8, 12, 2, "scale")
    rbox(head, -4.5, -5.5, -29, 9, 7, 7, 2, "scale")
    head.cube(-3.5, -5, -32, 7, 5, 3, "scale")
    head.cube(-1.5, -8, -27, 3, 2, 15, "scale_d")
    head.cube(-3, -4.5, -32.5, 1, 1, 1, "dark")
    head.cube(2, -4.5, -32.5, 1, 1, 1, "dark")
    nh = head.add(Part("nose_horn", (0, -8, -26), (-0.5, 0, 0)))
    nh.cube(-1, -4, -1, 2, 4, 2, "horn")
    nh.cube(-0.5, -6, -0.5, 1, 2, 1, "horn")
    for k, z in enumerate(range(-31, -12, 2)):
        ln = 3 if k % 3 == 1 else 2
        yb = 0 if z < -29 else (1.5 if z < -22 else 2)
        for x in (-4.5, 3.5):
            head.cube(x, yb, z, 1, ln, 1, "teeth")
    for side, sx in (("r", -1), ("l", 1)):
        for k, z in enumerate((-12, -9, -6)):
            spike(head, f"brow_spike_{side}{k}", 6 * sx, -9, z, 1, 3 - (k == 2), mat="horn", lean=-0.7, tip="horn")
        chain(head, f"horn_{side}", (5 * sx, -8, -4), (0.32, 0.3 * sx, 0),
              [(4, 7, (0.2, 0.05 * sx, 0)), (4, 7, (0.2, 0.05 * sx, 0)), (3, 6, (0.22, 0.04 * sx, 0)),
               (3, 6, (0.24, 0.03 * sx, 0)), (2, 5, (0.25, 0, 0)), (1, 4, (0, 0, 0))], "horn", tip_mat="crys")
        chain(head, f"horn2_{side}", (7 * sx, -5, -3), (0.2, 0.7 * sx, 0),
              [(3, 6, (0.25, 0.1 * sx, 0)), (2, 5, (0.25, 0, 0)), (1, 4, (0, 0, 0))], "horn")
        chain(head, f"cheek_spike_{side}", (8 * sx, -1, -6), (0.1, 1.0 * sx, 0),
              [(2, 4, (0.1, 0.2 * sx, 0)), (1, 3, (0, 0, 0))], "horn")

    jaw = head.add(Part("jaw", (0, 3, -9), (0.08, 0, 0)))
    rbox(jaw, -5, -1, -14, 10, 5, 15, 1, "scale")
    jaw.cube(-4, -1, -21, 8, 4, 7, "scale")
    jaw.cube(-2, 3, -20, 4, 2, 4, "scale_d")
    jaw.cube(-2, -1.5, -18, 4, 1, 14, "tongue")
    for k, z in enumerate(range(-20, -7, 2)):
        ln = 2 + (k % 2)
        for x in (-3.5, 2.5):
            jaw.cube(x, -1 - ln, z, 1, ln, 1, "teeth")
    for side, sx in (("r", -1), ("l", 1)):
        for k, z in enumerate((-10, -5)):
            fr = jaw.add(Part(f"frill_{side}{k}", (5 * sx, 2, z), (0.7, 0, 0.25 * sx)))
            fr.cube(-0.5, 0, -1.5, 1, 5 - k, 3, "memb")
            fr.cube(-0.5, 4 - k, -0.5, 1, 2, 1, "horn")

    # ---------------- pernas da frente
    front = []
    for side, sx in (("r", -1), ("l", 1)):
        leg = body.add(Part(f"leg_f{side}", (11 * sx, 2, -12), (0.35, 0, 0)))
        rbox_y(leg, -3.5, -6, -5, 7, 8, 10, 2, "scale")
        rbox_y(leg, -3, 1, -4, 6, 9, 8, 2, "scale")
        leg.cube(-2.5, 2, -5, 5, 6, 2, "scale")
        leg.cube(-4, -7, -4, 3, 4, 8, "plate") if sx < 0 else leg.cube(1, -7, -4, 3, 4, 8, "plate")
        el = leg.add(Part(f"leg_f{side}_fore", (0, 10, 0), (-0.75, 0, 0)))
        rbox_y(el, -3, 0, -3.5, 6, 5, 7, 2, "scale")
        rbox_y(el, -2.5, 4, -3, 5, 6, 6, 1, "scale")
        spike(el, f"leg_f{side}_elbow", 0, 1, 3, 2, 4, mat="horn", lean=-1.3, tip="horn")
        wr = el.add(Part(f"leg_f{side}_hand", (0, 9.5, 0), (0.4, 0, 0)))
        wr.cube(-3, 0, -4, 6, 3, 7, "scale_d")
        for k, x in enumerate((-2, 0, 2)):
            f = wr.add(Part(f"leg_f{side}_finger{k}", (x, 1.5, -4), (0.15, (x / 6.0) * 0.4, 0)))
            f.cube(-1, -0.5, -3, 2, 2, 3, "scale_d")
            chain(f, f"leg_f{side}_claw{k}", (0, 0.5, -3), (0.5, 0, 0), [(1, 2, (0.6, 0, 0)), (1, 2, (0, 0, 0))],
                  "claw", axis="-z")
        chain(wr, f"leg_f{side}_dew", (2.5 * sx, 1.5, 1), (0.6, 0.6 * sx, 0), [(1, 2, (0, 0, 0))], "claw", axis="z")
        front.append(leg)

    # ---------------- pernas de trás (digitígradas)
    back = []
    for side, sx in (("r", -1), ("l", 1)):
        leg = body.add(Part(f"leg_b{side}", (10.5 * sx, -2, 25)))
        rbox_y(leg, -4, -6, -7, 8, 11, 13, 3, "scale")
        rbox_y(leg, -3.5, 4, -6, 7, 6, 10, 2, "scale")
        knee = leg.add(Part(f"leg_b{side}_shin", (0, 9, -3), (0.55, 0, 0)))
        rbox_y(knee, -2.5, 0, -2.5, 5, 10, 5, 1, "scale")
        hock = knee.add(Part(f"leg_b{side}_meta", (0, 9.5, 0), (-1.0, 0, 0)))
        hock.cube(-2, 0, -2, 4, 8, 4, "scale_d")
        spike(hock, f"leg_b{side}_spur", 0, 1, 2, 1, 3, mat="horn", lean=-1.6, tip="horn")
        foot = hock.add(Part(f"leg_b{side}_foot", (0, 7.5, 0), (0.45, 0, 0)))
        foot.cube(-3, 0, -4, 6, 2, 6, "scale_d")
        for k, x in enumerate((-2, 0, 2)):
            t = foot.add(Part(f"leg_b{side}_toe{k}", (x, 1, -4), (0.1, (x / 6.0) * 0.35, 0)))
            t.cube(-1, -1, -3, 2, 2, 3, "scale_d")
            chain(t, f"leg_b{side}_claw{k}", (0, 0, -3), (0.5, 0, 0), [(1, 2, (0.6, 0, 0)), (1, 2, (0, 0, 0))],
                  "claw", axis="-z")
        back.append(leg)

    # ---------------- asas: úmero, antebraço, polegar com garra, 4 dedos e painéis de membrana
    wl = body.add(Part("wing_l", (7, -12, -10)))
    wl.cube(-1, -2.5, -2.5, 4, 5, 5, "scale")
    wl.cube(0, -1.5, -1.5, 15, 3, 3, "bone")
    for st, (x0, dep) in enumerate(((0, 24), (5, 21), (10, 18))):
        wl.cube(x0, 0, 1.5, 5, 1, dep, "memb", tag={"membrane": True, "step": 3, "steps": 4})
    fore = wl.add(Part("wing_l_fore", (15, 0, 0), (0, 0.2, 0)))
    fore.cube(-1.5, -2, -2, 3, 4, 4, "scale")
    fore.cube(0, -1, -1, 17, 2, 2, "bone")
    for st, (x0, w, dep) in enumerate(((0, 5, 17), (5, 4, 15), (9, 4, 13), (13, 4, 11))):
        fore.cube(x0, 0, 1, w, 1, dep, "memb", tag={"membrane": True, "step": 3, "steps": 4})
    spike(fore, "wing_l_elbow", 0, -2, 0, 1, 3, mat="horn", lean=-0.8, tip="horn")
    wrist = fore.add(Part("wing_l_wrist", (17, 0, 0)))
    wrist.cube(-1, -1.5, -1.5, 3, 3, 3, "scale")
    chain(wrist, "wing_l_thumb", (1, -1, -1.5), (-0.4, 0.5, 0), [(2, 3, (-0.5, 0, 0)), (1, 3, (0, 0, 0))],
          "claw", axis="-z")
    fingers = [(-0.12, 24), (-0.5, 26), (-0.9, 23), (-1.3, 19)]
    for k, (yaw, ln) in enumerate(fingers):
        f = wrist.add(Part(f"wing_l_f{k}", (0.5, 0, 0), (0, yaw, 0)))
        f.cube(0, -1, -1, 2, 2, 2, "scale")
        f.cube(1, -0.5, -0.5, ln, 1, 1, "bone")
        f.cube(ln + 1, -0.5, -0.5, 2, 1, 1, "claw")
        gap = (fingers[k + 1][0] - yaw) * -1 if k < 3 else 0.55
        membrane(f, ln + 1, gap)
    mirror_part(wl, lambda n: n.replace("wing_l", "wing_r"), body)

    # ---------------- cauda: 9 segmentos que afinam, com lâmina de raio
    tail_specs = [(12, 12, 11, -0.18, 0.0), (11, 11, 10, -0.1, 0.06), (11, 10, 9, -0.04, 0.08),
                  (10, 9, 8, 0.02, 0.08), (10, 8, 7, 0.06, 0.06), (9, 7, 6, 0.08, 0.04), (9, 6, 5, 0.1, 0.0),
                  (8, 5, 4, 0.12, -0.04), (8, 4, 3, 0.14, -0.06)]
    parent, prev_len = body, 0
    for k, (ln, w, h, rx, ry) in enumerate(tail_specs):
        pv = (0, -4, 30) if k == 0 else (0, 0, prev_len)
        seg = parent.add(Part(f"tail{k + 1}", pv, (rx, ry, 0)))
        rbox(seg, -w / 2, -h / 2, -1, w, h, ln + 2, 2 if w >= 7 else 1, "scale")
        if w >= 6:
            seg.cube(-w / 2 + 1.5, h / 2 - 1, 0, w - 3, 2, ln, "plate")
        if k < 7:
            spike(seg, f"tail{k + 1}_spike", 0, -h / 2, ln / 2, 2 if k < 4 else 1, max(2, 5 - k // 2))
        if k < 3:
            seg.cube(-w / 2 - 0.5, -1, 2, 1, 4, ln - 4, "plate")
            seg.cube(w / 2 - 0.5, -1, 2, 1, 4, ln - 4, "plate")
        parent, prev_len = seg, ln
    blade = parent.add(Part("tail_blade", (0, 0, prev_len)))
    for (y, z, h, d) in ((-13, 1, 5, 3), (-9, -1, 5, 4), (-5, 2, 5, 4), (-1, 4, 4, 4), (2, 7, 5, 4),
                         (6, 9, 4, 3), (9, 12, 3, 3)):
        blade.cube(-0.5, y, z, 1, h, d, "crys")
    blade.cube(-1, -2, 0, 2, 4, 3, "spine")

    m = Model("storm_dragon", 512, 512, [body])
    return m


def ground_fit(m, pose=None):
    """Ajusta a altura do pivô de cada perna para os pés tocarem o chão (y = 24)."""
    body = m.roots[0]
    for leg in body.children:
        if not leg.name.startswith("leg_"):
            continue
        low = lowest_point(leg, pose, T=(0, BODY_Y, 0))
        dy = 24.0 - low
        leg.pivot = (leg.pivot[0], leg.pivot[1] + dy, leg.pivot[2])


def dragon_v3():
    m = build()
    ground_fit(m)
    m.pack()
    return m


# ---------------------------------------------------------------- poses
def _wings(h, f, w, fan):
    pose = {"wing_l": h, "wing_l_fore": f, "wing_l_wrist": w}
    for key, v in list(pose.items()):
        pose[key.replace("wing_l", "wing_r")] = (v[0], -v[1], -v[2])
    for k in range(4):
        pose[f"wing_l_f{k}"] = (0, fan[k], 0)
        pose[f"wing_r_f{k}"] = (0, -fan[k], 0)
    return pose


# no chão: asas erguidas atrás dos ombros, leque meio fechado
POSE_GROUND = _wings((0, -0.25, -1.25), (0, -1.0, 0.25), (0, -0.2, 0.6), (0.0, 0.15, 0.3, 0.45))
POSE_FLY = {
    "wing_l": (0, 0.15, -0.25), "wing_l_fore": (0, -0.1, 0.1), "wing_r": (0, -0.15, 0.25), "wing_r_fore": (0, 0.1, -0.1),
    "leg_fl": (0.9, 0, 0), "leg_fr": (0.9, 0, 0), "leg_bl": (1.0, 0, 0), "leg_br": (1.0, 0, 0),
    "tail1": (0.12, 0, 0), "neck1": (0.2, 0, 0),
}
