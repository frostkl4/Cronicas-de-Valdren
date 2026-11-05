"""Vorthrax v4: direção de arte "dragão de gelo e raio" (corpo branco-gelo esguio, asas enormes com ossos
grossos, membranas azuis com relâmpagos, coroa de espinhos, cristais brilhando nas pontas).

Coordenadas: unidades de 1/16 de bloco, Y para baixo, chão em y = 24, a cabeça aponta para -Z, +X = esquerda.

Materiais: ice, ice_d, plate, bone, horn, horn2, crys, claw, memb, eye, dark, teeth, tongue.
As membranas levam no tag {"memb": chave, "ox": deslocamento}; a pintura usa a chave para desenhar os
relâmpagos contínuos entre o dedo e a ponta do dedo (que é outro osso, para poder dobrar).
"""
import math

from boxmodel import Part, Model
from v2_common import mirror_part
from v3_dragon import rbox, rbox_y, lowest_point

BODY_Y = -3
MEMB_PANELS = {}  # chave -> lista de (x0, x1, z0, z1) em coordenadas do dedo


# ------------------------------------------------------------------ ferramentas
def chain2(parent, name, pivot, rot, segs, mats, axis="z"):
    """Cadeia que afina: segs [(espessura, comprimento, (rx, ry, rz) do próximo)], um material por segmento."""
    if isinstance(mats, str):
        mats = [mats] * len(segs)
    p = parent.add(Part(name, pivot, rot))
    first = p
    for k, (t, ln, nxt) in enumerate(segs):
        m = mats[min(k, len(mats) - 1)]
        if axis == "z":
            p.cube(-t / 2, -t / 2, 0, t, t, ln, m)
            off = (0, 0, ln - 0.5)
        elif axis == "-z":
            p.cube(-t / 2, -t / 2, -ln, t, t, ln, m)
            off = (0, 0, -ln + 0.5)
        elif axis == "-y":
            p.cube(-t / 2, -ln, -t / 2, t, ln, t, m)
            off = (0, -ln + 0.5, 0)
        else:
            p.cube(-t / 2, 0, -t / 2, t, ln, t, m)
            off = (0, ln - 0.5, 0)
        if k < len(segs) - 1:
            p = p.add(Part(f"{name}_{k + 2}", off, nxt or (0, 0, 0)))
    return first


def ice_spike(parent, name, x, y, z, base, height, lean=-0.35, side=0.0, mat="horn", tip="crys"):
    """Espinho de gelo em 3 degraus (sobe em -Y), inclinado para trás; ponta de cristal."""
    p = parent.add(Part(name, (x, y, z), (lean, 0, side)))
    h1 = max(1, round(height * 0.45))
    h2 = max(1, round(height * 0.35))
    h3 = max(1, height - h1 - h2)
    p.cube(-base / 2, -h1, -base / 2, base, h1, base, mat)
    b2 = max(1, base - 1)
    p.cube(-b2 / 2, -h1 - h2, -b2 / 2, b2, h2, b2, "horn2" if mat == "horn" else mat)
    b3 = max(1, base - 2)
    p.cube(-b3 / 2, -h1 - h2 - h3, -b3 / 2, b3, h3, b3, tip)
    return p


def _h(*a):
    n = 2166136261
    for v in a:
        n ^= (int(v) * 16777619) & 0xFFFFFFFF
        n = (n * 16777619) & 0xFFFFFFFF
        n ^= n >> 13
    return (n & 0xFFFF) / 65536.0


def strips(parts, key, depth_fn, xmax, y, z0=1.0, width=2, ragged=0.16, layer=True):
    """Membrana em tiras de `width` ao longo de X; depth_fn(xc) dá a profundidade (Z) de cada tira.
    parts: [(x_inicio, parte)]: cada tira vai para a última parte cujo início <= x (ponta do dedo dobrável).
    layer: camadas extras (uma em cima, outra embaixo) perto do osso, em blocos de 4, para a membrana
    ficar mais grossa junto do osso e afinar em degraus até a borda."""
    rects = MEMB_PANELS.setdefault(key, [])

    def part_at(x):
        ox, part = parts[0]
        for (sx, sp) in parts:
            if x >= sx - 1e-6:
                ox, part = sx, sp
        return ox, part

    x = 0
    k = 0
    while x < xmax - 0.01:
        w = min(width, int(math.ceil(xmax - x)))
        xc = x + w / 2
        d = depth_fn(xc)
        r = _h(len(key), k, int(xmax))
        if ragged and d > 6:
            d += -2 if r < ragged * 0.5 else (-1 if r < ragged else (1 if r > 0.85 else 0))
        d = int(round(d - z0))
        if d >= 1:
            ox, part = part_at(x)
            part.cube(x - ox, y, z0, w, 1, d, "memb", tag={"memb": key, "ox": ox})
            rects.append((x, x + w, z0, z0 + d))
        x += w
        k += 1
    if not layer:
        return
    x = 0
    while x < xmax - 2.01:
        ox, part = part_at(x)
        if part is not parts[0][1]:
            break
        w = min(4, int(math.floor(xmax - x)))
        if len(parts) > 1 and x + w > parts[1][0]:
            w = int(parts[1][0] - x)
        if w < 2:
            break
        d = min(depth_fn(x + 0.5), depth_fn(x + w - 0.5)) - z0
        top = int(round(d * 0.42))
        if top >= 3:
            part.cube(x - ox, y - 1, z0, w, 1, top, "memb", tag={"memb": key, "ox": ox, "layer": 1})
            part.cube(x - ox, y + 1, z0, w, 1, max(2, top - 2), "memb", tag={"memb": key, "ox": ox, "layer": 2})
        x += w


def fan_depth(L0, L1, gap, scallop):
    """Profundidade da membrana entre um dedo (eixo X, comprimento L0) e o próximo (ângulo gap, comprimento L1)."""
    def fn(xc):
        best = 0.0
        z = 0.5
        while z < 90:
            th = math.atan2(z, xc)
            if th > gap:
                break
            r = math.hypot(xc, z)
            R = L0 + (L1 - L0) * th / gap - scallop * math.sin(math.pi * th / gap)
            if r > R:
                break
            best = z
            z += 0.5
        return best
    return fn


# ===================================================================== modelo
def build():
    MEMB_PANELS.clear()
    body = Part("body", (0, BODY_Y, 0))

    # ---------------- tronco esguio: peito, cintura fina, quadril
    rbox(body, -8, -8, -16, 16, 15, 17, 2, "ice")
    body.cube(-7, 1, -18, 6, 6, 6, "ice")
    body.cube(1, 1, -18, 6, 6, 6, "ice")
    body.cube(-2, 5, -19, 4, 3, 9, "plate")
    body.cube(-9, -10, -15, 5, 5, 9, "ice_d")
    body.cube(4, -10, -15, 5, 5, 9, "ice_d")
    body.cube(-6, -9.5, -14, 12, 2, 12, "ice_d")
    rbox(body, -7, -7, -1, 14, 13, 4, 2, "ice")
    rbox(body, -6, -6, 2, 12, 11, 12, 2, "ice")
    rbox(body, -7, -7, 13, 14, 12, 12, 2, "ice")
    body.cube(-5, -8.5, 15, 10, 2, 8, "ice_d")
    for z in range(-15, 24, 4):
        y = 7 if z < -1 else 5
        w = 10 if z < -1 else 8
        body.cube(-w / 2, y - 0.5, z, w, 1, 4, "plate")
    for z in (3, 8):
        body.cube(-6.5, -3, z, 1, 5, 4, "plate")
        body.cube(5.5, -3, z, 1, 5, 4, "plate")
    tops = [(-13, -9.5, 6, 3), (-8, -9.5, 7, 3), (-3, -8, 6, 3), (2, -6, 5, 2), (6, -6, 5, 2), (10, -6, 5, 2),
            (15, -8.5, 6, 2), (20, -8.5, 5, 2)]
    for k, (z, y, hgt, b) in enumerate(tops):
        ice_spike(body, f"crest_{k}", 0, y, z, b, hgt)
    for side, sx in (("r", -1), ("l", 1)):
        cl = body.add(Part(f"shards_{side}", (8 * sx, -6, -5), (0.25, 0, 0.7 * sx)))
        cl.cube(-1, -5, -1, 2, 5, 2, "horn")
        cl.cube(-0.5, -7, -0.5, 1, 2, 1, "crys")
        b = cl.add(Part(f"shards_{side}_b", (1.5 * sx, 0, 2), (-0.4, 0, 0.35 * sx)))
        b.cube(-0.5, -4, -0.5, 1, 4, 1, "horn")
        b.cube(-0.5, -5, -0.5, 1, 1, 1, "crys")

    # ---------------- pescoço em S, fino
    neck_specs = [("neck1", (0, -4, -15), 8, 9, 9, -0.95), ("neck2", None, 7, 8, 8, -0.35),
                  ("neck3", None, 7, 8, 8, 0.15), ("neck4", None, 7, 7, 7, 0.45), ("neck5", None, 6, 7, 7, 0.45)]
    parent, prev_len = body, 0
    for nm, pv, ln, w, h, rx in neck_specs:
        seg = parent.add(Part(nm, pv if pv else (0, 0, -prev_len), (rx, 0, 0)))
        rbox(seg, -w / 2, -h / 2, -ln - 1, w, h, ln + 2, 2, "ice")
        seg.cube(-w / 2 + 1.5, h / 2 - 0.5, -ln, w - 3, 1, ln, "plate")
        ice_spike(seg, f"{nm}_spike", 0, -h / 2, -ln / 2, 2, 5)
        parent, prev_len = seg, ln

    # ---------------- cabeça pequena e angulosa com coroa de espinhos de gelo
    head = parent.add(Part("head", (0, 0, -prev_len), (0.25, 0, 0)))
    rbox(head, -5, -5, -9, 10, 8, 10, 2, "ice")
    head.cube(-4, -6.5, -7, 8, 2, 7, "ice_d")
    head.cube(-6, -6, -12, 4, 3, 6, "ice_d")
    head.cube(2, -6, -12, 4, 3, 6, "ice_d")
    head.cube(-6, -3.5, -11, 1, 1, 3, "eye")
    head.cube(5, -3.5, -11, 1, 1, 3, "eye")
    head.cube(-6, -2.5, -10, 2, 4, 6, "ice")
    head.cube(4, -2.5, -10, 2, 4, 6, "ice")
    rbox(head, -4, -4, -18, 8, 6, 9, 1, "ice")
    head.cube(-3, -3.5, -21, 6, 5, 3, "ice", tag={"nostrils": True})
    head.cube(-2, -3, -23, 4, 4, 2, "ice")
    head.cube(-1, -5, -21, 2, 1, 12, "ice_d")
    ice_spike(head, "nose_spike", 0, -5, -19, 2, 3, lean=-0.6)
    for k, z in enumerate(range(-21, -10, 2)):
        yb = 1.5 if z < -17 else 2
        for x in (-3.5, 2.5):
            head.cube(x, yb, z, 1, 2 + (k % 2), 1, "teeth")
    for k, (z, hgt) in enumerate(((-8, 4), (-5, 5), (-2, 5))):
        ice_spike(head, f"crest_h{k}", 0, -6.5, z, 2, hgt, lean=-0.75)
    for side, sx in (("r", -1), ("l", 1)):
        chain2(head, f"horn_{side}", (3.5 * sx, -6, -3), (0.55, 0.28 * sx, 0),
               [(3, 6, (0.12, 0.04 * sx, 0)), (3, 6, (0.12, 0.04 * sx, 0)), (2, 6, (0.14, 0.02 * sx, 0)),
                (2, 5, (0.16, 0, 0)), (1, 5, (0, 0, 0))], ["horn", "horn", "horn", "horn2", "crys"])
        fan = [((5.0 * sx, -4, 0), (0.35, 0.55 * sx, 0), 9), ((5.5 * sx, -2, -1), (0.12, 0.85 * sx, 0), 8),
               ((5.0 * sx, 0, -2), (-0.08, 1.1 * sx, 0), 7), ((3.5 * sx, -5, 1), (0.75, 0.3 * sx, 0), 8)]
        for k, (pv, rot, ln) in enumerate(fan):
            chain2(head, f"crown_{side}{k}", pv, rot,
                   [(2, ln // 2 + 1, (0.1, 0.05 * sx, 0)), (1, ln // 2, (0.05, 0, 0)), (1, 2, None)],
                   ["horn", "horn2", "crys"])
        for k, (y, z, ry, rx) in enumerate(((0, -6, 1.25, 0.1), (1.5, -3, 1.35, -0.15), (-1.5, -8.5, 1.1, 0.25))):
            chain2(head, f"cheek_{side}{k}", (5.5 * sx, y, z), (rx, ry * sx, 0),
                   [(2, 4, (0, 0.15 * sx, 0)), (1, 3, None), (1, 2, None)], ["horn", "horn2", "crys"])

    jaw = head.add(Part("jaw", (0, 2, -8), (0.1, 0, 0)))
    rbox(jaw, -4, -1, -11, 8, 3, 12, 1, "ice")
    jaw.cube(-3, -1, -13, 6, 3, 2, "ice")
    jaw.cube(-2, -1, -15, 4, 3, 2, "ice")
    jaw.cube(-2, 2, -12, 4, 1, 9, "plate")
    jaw.cube(-2, -1.5, -12, 4, 1, 10, "tongue")
    for k, z in enumerate(range(-13, -3, 2)):
        ln = 2 + (k % 2)
        for x in (-3, 2):
            jaw.cube(x, -1 - ln, z, 1, ln, 1, "teeth")
    for side, sx in (("r", -1), ("l", 1)):
        chain2(jaw, f"jaw_spike_{side}", (3.5 * sx, 1.5, -2), (-0.6, 0.5 * sx, 0),
               [(2, 3, (-0.2, 0, 0)), (1, 3, None), (1, 2, None)], ["horn", "horn2", "crys"])

    # ---------------- pernas da frente (finas, garras escuras)
    for side, sx in (("r", -1), ("l", 1)):
        leg = body.add(Part(f"leg_f{side}", (8 * sx, 3, -11), (0.3, 0, 0)))
        rbox_y(leg, -3, -4, -3.5, 6, 9, 7, 1, "ice")
        leg.cube(-3.5, -5, -3, 7, 3, 6, "ice_d")
        fore = leg.add(Part(f"leg_f{side}_fore", (0, 5, 0), (-0.6, 0, 0)))
        rbox_y(fore, -2, 0, -2.5, 4, 9, 5, 1, "ice")
        ice_spike(fore, f"leg_f{side}_elbow", 0, 1, 2, 2, 3, lean=-1.4)
        hand = fore.add(Part(f"leg_f{side}_hand", (0, 8.5, 0), (0.3, 0, 0)))
        hand.cube(-2.5, 0, -3.5, 5, 2, 6, "ice_d")
        for k, x in enumerate((-1.5, 0, 1.5)):
            hand.cube(x - 0.5, 0.5, -5.5, 1, 1, 2, "ice")
            chain2(hand, f"leg_f{side}_claw{k}", (x, 1, -5.5), (0.5, x / 6 * 0.5, 0),
                   [(1, 2, (0.6, 0, 0)), (1, 2, None)], "claw", axis="-z")

    # ---------------- pernas de trás (digitígradas)
    for side, sx in (("r", -1), ("l", 1)):
        leg = body.add(Part(f"leg_b{side}", (7.5 * sx, -1, 20)))
        rbox_y(leg, -3.5, -5, -5.5, 7, 11, 11, 2, "ice")
        leg.cube(-3, 4, -4.5, 6, 4, 8, "ice")
        shin = leg.add(Part(f"leg_b{side}_shin", (0, 8, -2), (0.55, 0, 0)))
        rbox_y(shin, -2, 0, -2, 4, 9, 4, 1, "ice")
        meta = shin.add(Part(f"leg_b{side}_meta", (0, 8.5, 0), (-1.0, 0, 0)))
        meta.cube(-1.5, 0, -1.5, 3, 7, 3, "ice_d")
        ice_spike(meta, f"leg_b{side}_spur", 0, 1, 1.5, 1, 3, lean=-1.6)
        foot = meta.add(Part(f"leg_b{side}_foot", (0, 6.5, 0), (0.45, 0, 0)))
        foot.cube(-2.5, 0, -3.5, 5, 2, 5, "ice_d")
        for k, x in enumerate((-1.5, 0, 1.5)):
            foot.cube(x - 0.5, 0.5, -5.5, 1, 1, 2, "ice")
            chain2(foot, f"leg_b{side}_claw{k}", (x, 1, -5.5), (0.5, x / 6 * 0.4, 0),
                   [(1, 2, (0.6, 0, 0)), (1, 2, None)], "claw", axis="-z")

    # ---------------- asas enormes: ossos grossos brancos + membranas em tiras
    wl = body.add(Part("wing_l", (6, -9, -10)))
    wl.cube(-2.5, -3.5, -3.5, 7, 7, 7, "ice")
    wl.cube(2, -2.5, -2.5, 22, 5, 5, "bone")
    wl.cube(4, -3.5, -1.5, 18, 1, 3, "bone")
    wl.cube(20, -3, -3, 4, 6, 6, "bone")
    ice_spike(wl, "wing_l_hs0", 8, -3.5, 0, 1, 3, lean=-0.7)
    ice_spike(wl, "wing_l_hs1", 15, -3.5, 0, 1, 3, lean=-0.7)
    strips([(0, wl)], "wing_l_in",
           lambda xc: 27 * (1 - xc / 24) + 23 * (xc / 24) - 5 * math.sin(math.pi * xc / 24), 24, -0.6, z0=1.0)

    fore = wl.add(Part("wing_l_fore", (24, 0, 0)))
    fore.cube(-3, -3, -3, 6, 6, 6, "bone")
    fore.cube(2, -2, -2, 28, 4, 4, "bone")
    fore.cube(4, -3, -1, 24, 1, 2, "bone")
    ice_spike(fore, "wing_l_elbow", 0, -3, 0, 2, 4, lean=-0.9)
    strips([(0, fore)], "wing_l_fore",
           lambda xc: 23 + 3 * xc / 30 - 5 * math.sin(math.pi * xc / 30), 30, -0.4, z0=1.0)

    wrist = fore.add(Part("wing_l_wrist", (30, 0, 0)))
    wrist.cube(-2.5, -2.5, -2.5, 5, 5, 5, "bone")
    chain2(wrist, "wing_l_thumb", (0, -1, -2), (-0.35, -0.5, 0),
           [(2, 3, (-0.3, 0, 0)), (2, 3, (-0.5, 0, 0)), (1, 3, None)], ["bone", "claw", "claw"], axis="-z")
    fingers = [(-0.05, 30), (-0.6, 34), (-1.15, 28), (-1.7, 20)]
    for k, (yaw, ln) in enumerate(fingers):
        f = wrist.add(Part(f"wing_l_f{k}", (0.5, 0, 0), (0, yaw, 0)))
        a = int(ln * 0.55)
        b = ln - a
        f.cube(0, -2, -2, 4, 4, 4, "bone")
        f.cube(3, -1.5, -1.5, a, 3, 3, "bone")
        tip = f.add(Part(f"wing_l_f{k}_tip", (3 + a, 0, 0)))
        tip.cube(-1, -1.5, -1.5, 2, 3, 3, "bone")
        tip.cube(0.5, -1, -1, b, 2, 2, "bone")
        if k == 0:
            tip.cube(b, -1, -1, 4, 2, 2, "claw")
            tip.cube(b + 4, -0.5, -0.5, 3, 1, 1, "claw")
            tip.cube(b + 7, -0.5, -0.5, 2, 1, 1, "crys")
        else:
            tip.cube(b, -0.5, -0.5, 4, 1, 1, "crys")
        L0 = ln + 3
        if k < 3:
            gap = yaw - fingers[k + 1][0]
            L1 = fingers[k + 1][1] + 3
        else:
            gap, L1 = 0.6, 15
        chord = math.sqrt(L0 * L0 + L1 * L1 - 2 * L0 * L1 * math.cos(gap))
        strips([(0, f), (3 + a, tip)], f"wing_l_f{k}", fan_depth(L0, L1, gap, 0.2 * chord), L0,
               -0.6 if k % 2 == 0 else -0.4, z0=0.5)
    mirror_part(wl, lambda n: n.replace("wing_l", "wing_r"), body)

    # ---------------- cauda fina terminando em lança de cristal
    tail_specs = [(10, 9, 8, -0.2, 0.0), (10, 8, 7, -0.08, 0.05), (9, 7, 6, -0.02, 0.07), (9, 6, 6, 0.03, 0.07),
                  (8, 6, 5, 0.06, 0.05), (8, 5, 4, 0.08, 0.03), (7, 4, 4, 0.1, 0.0), (7, 3, 3, 0.12, -0.03),
                  (6, 3, 3, 0.12, -0.05)]
    parent, prev_len = body, 0
    for k, (ln, w, h, rx, ry) in enumerate(tail_specs):
        pv = (0, -2, 24) if k == 0 else (0, 0, prev_len)
        seg = parent.add(Part(f"tail{k + 1}", pv, (rx, ry, 0)))
        rbox(seg, -w / 2, -h / 2, -1, w, h, ln + 2, 1 if w >= 4 else 0, "ice")
        if w >= 5:
            seg.cube(-w / 2 + 1, h / 2 - 0.5, 0, w - 2, 1, ln, "plate")
        if k < 8:
            ice_spike(seg, f"tail{k + 1}_spike", 0, -h / 2, ln / 2, 2 if k < 4 else 1, max(2, 5 - k // 2))
        parent, prev_len = seg, ln
    tip = parent.add(Part("tail_tip", (0, 0, prev_len)))
    tip.cube(-1.5, -1.5, -0.5, 3, 3, 4, "horn")
    tip.cube(-1, -1, 3.5, 2, 2, 6, "horn2")
    tip.cube(-0.5, -0.5, 9.5, 1, 1, 7, "crys")
    for sx in (-1, 1):
        tip.cube(1.5 if sx > 0 else -4.5, -0.5, 0.5, 3, 1, 6, "crys")
        tip.cube(4.5 if sx > 0 else -6.5, -0.5, 1.5, 2, 1, 3, "crys")

    return Model("storm_dragon", 512, 1024, [body])


def ground_fit(m, pose=None):
    body = m.roots[0]
    for leg in body.children:
        if leg.name.startswith("leg_"):
            low = lowest_point(leg, pose, T=(0, BODY_Y, 0))
            leg.pivot = (leg.pivot[0], leg.pivot[1] + 24.0 - low, leg.pivot[2])


def dragon_v4():
    m = build()
    ground_fit(m)
    m.pack()
    return m


# ---------------------------------------------------------------- poses
def mirror_pose(pose):
    out = dict(pose)
    for key, v in pose.items():
        if key.startswith("wing_l"):
            out[key.replace("wing_l", "wing_r")] = (v[0], -v[1], -v[2])
    return out


# parado: asas erguidas, cotovelo alto, membrana caindo como um manto e dedos apontando para trás
POSE_GROUND = mirror_pose({
    "wing_l": (0.0, -0.6, -1.4), "wing_l_fore": (0.0, -2.2, -0.45), "wing_l_wrist": (0.0, 0.2, 0.6),
    "wing_l_f0": (0, 0.3, 0), "wing_l_f1": (0, 0.5, 0), "wing_l_f2": (0, 0.7, 0), "wing_l_f3": (0, 0.9, 0),
    "wing_l_f0_tip": (0, 0, 0.25), "wing_l_f1_tip": (0, 0, 0.2), "wing_l_f2_tip": (0, 0, 0.15),
    "wing_l_f3_tip": (0, 0, 0.1),
})
POSE_FLY = mirror_pose({
    "wing_l": (0, 0.1, -0.3), "wing_l_fore": (0, -0.05, 0.12), "wing_l_wrist": (0, 0, 0.05),
    "wing_l_f0_tip": (0, 0, 0.08), "wing_l_f1_tip": (0, 0, 0.06),
})
POSE_FLY.update({"leg_fl": (0.9, 0, 0), "leg_fr": (0.9, 0, 0), "leg_bl": (1.0, 0, 0), "leg_br": (1.0, 0, 0),
                 "tail1": (0.12, 0, 0), "neck1": (0.2, 0, 0)})
