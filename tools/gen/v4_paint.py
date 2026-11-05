"""Pintura do Vorthrax v4: corpo branco-gelo com sombras azuladas, ossos com bisel forte, membranas azuis em
degradê com nuvens de geada, rachaduras escuras e relâmpagos brancos (que também vão para a textura _glow),
garras quase pretas e cristais brilhando.
"""
import math
import random

from pix import h01, fbm
import v4_dragon as D

ICE = [(48, 66, 92), (72, 98, 128), (106, 136, 166), (144, 174, 200), (184, 208, 228), (218, 233, 244),
       (242, 249, 253)]
HORN = [(66, 104, 142), (102, 152, 192), (150, 198, 230), (198, 230, 248), (236, 250, 255)]
HORN2 = [(70, 140, 190), (108, 186, 232), (156, 222, 250), (210, 244, 255)]
CRYS = [(96, 200, 245), (150, 230, 255), (205, 246, 255), (250, 255, 255)]
CLAW = [(12, 14, 20), (26, 30, 38), (44, 50, 60), (70, 78, 92), (106, 116, 132)]
MEMB = [(10, 30, 58), (18, 50, 88), (26, 74, 122), (38, 104, 158), (58, 138, 194), (98, 180, 226),
        (156, 216, 242)]
PLATE = [(84, 124, 160), (122, 168, 200), (168, 208, 232), (208, 234, 248), (238, 249, 255)]
TONGUE = [(30, 52, 88), (46, 76, 120), (66, 104, 152)]
FACE = {"top": 0.15, "north": 0.0, "south": -0.06, "west": -0.03, "east": -0.03, "bottom": -0.26}


def pick(ramp, t):
    return ramp[max(0, min(len(ramp) - 1, int(t * len(ramp))))]


def base_t(c, base, bevel=0.12, grad=0.18):
    t = base + FACE[c.face]
    if c.is_side:
        t += (0.5 - c.vfrac) * grad
        if c.fh >= 3:
            if c.j == 0:
                t += bevel
            elif c.j == c.fh - 1:
                t -= bevel * 0.8
        if c.fw >= 3 and (c.i == 0 or c.i == c.fw - 1):
            t -= 0.04
    elif c.face == "top" and c.fw >= 3 and c.fh >= 3 and c.edge == 0:
        t += 0.05
    g = c.gp
    t += (fbm(g[0], g[1], g[2], s=9.0, seed=11) - 0.5) * 0.07
    t += (h01(c.i, c.j, int(g[0] * 7), int(g[2] * 7), int(g[1] * 7)) - 0.5) * 0.03
    return t


def scales(c, t):
    """Fileiras de escamas discretas nas laterais."""
    if not c.is_side or c.fw < 4 or c.fh < 4:
        return t
    lp = c.lp
    row = int(math.floor(lp[1] / 3.0))
    hz = lp[2] if c.face in ("east", "west") else lp[0]
    if int(math.floor(lp[1])) % 3 == 0 and (int(math.floor(hz)) + row * 3) % 6 == 0:
        t -= 0.06
    return t


# ------------------------------------------------------------------ relâmpagos nas membranas
_CACHE = {}


def _raster(cells, a, b):
    n = int(max(abs(b[0] - a[0]), abs(b[1] - a[1])) * 4) + 1
    for s in range(n + 1):
        u = s / n
        cells.add((math.floor(a[0] + (b[0] - a[0]) * u), math.floor(a[1] + (b[1] - a[1]) * u)))


def _bolt(rng, cells, p, ang, budget, depth=0):
    while budget > 0:
        ln = rng.uniform(1.2, 3.0)
        a = ang + rng.uniform(-0.9, 0.9)
        q = (p[0] + math.cos(a) * ln, p[1] + math.sin(a) * ln)
        _raster(cells, p, q)
        p = q
        budget -= ln
        if depth < 2 and rng.random() < 0.22:
            _bolt(rng, cells, p, ang + rng.choice((-1, 1)) * rng.uniform(0.5, 1.2), budget * 0.5, depth + 1)


def panel_info(key, right):
    k = (key, right)
    if k in _CACHE:
        return _CACHE[k]
    rects = D.MEMB_PANELS[key]
    x0 = min(r[0] for r in rects)
    x1 = max(r[1] for r in rects)
    z0 = min(r[2] for r in rects)
    z1 = max(r[3] for r in rects)
    depth = {}
    for (a, b, c, d) in rects:
        for x in range(int(math.floor(a)), int(math.ceil(b))):
            depth[x] = max(depth.get(x, 0), d)
    rng = random.Random(f"{key}-{right}")
    bolts, cracks = set(), set()
    area = sum((b - a) * (d - c) for a, b, c, d in rects)
    for _ in range(1 + int(area / 520)):
        sx = rng.uniform(x0 + 2, x1 - 3)
        _bolt(rng, bolts, (sx, z0 + rng.uniform(0.5, 3.0)), math.pi / 2 + rng.uniform(-0.8, 0.8),
              rng.uniform(0.7, 1.1) * (z1 - z0))
    for _ in range(1 + int(area / 300)):
        sx = rng.uniform(x0, x1)
        _bolt(rng, cracks, (sx, rng.uniform(z0, z1)), rng.uniform(0, math.tau), rng.uniform(5, 12), depth=2)
    halo, sparks = set(), set()
    for (x, z) in bolts:
        for dx, dz in ((1, 0), (-1, 0), (0, 1), (0, -1)):
            if (x + dx, z + dz) not in bolts:
                halo.add((x + dx, z + dz))
        for dx in (-3, -2, 2, 3):
            for dz in (-3, -2, 2, 3):
                cell = (x + dx, z + dz)
                if cell not in bolts and rng.random() < 0.012:
                    sparks.add(cell)
    info = {"x0": x0, "x1": x1, "z0": z0, "z1": z1, "bolts": bolts, "halo": halo, "cracks": cracks,
            "sparks": sparks, "depth": depth, "seed": len(key) * 7 + (3 if right else 0)}
    _CACHE[k] = info
    return info


def paint_memb(c):
    tag = c.cube.tag
    right = c.part.name.startswith("wing_r")
    info = panel_info(tag["memb"], right)
    x = abs(c.lp[0]) + tag["ox"] if not right else abs(c.lp[0]) + tag["ox"]
    z = c.lp[2]
    cx, cz = math.floor(x), math.floor(z)
    if c.face not in ("top", "bottom"):
        # bordas finas: geada clara
        light = c.face == "south"
        col = (176, 224, 246) if light else (120, 184, 222)
        return col, None
    zn = (z - info["z0"]) / max(1.0, info["z1"] - info["z0"])
    s = info["seed"]
    t = 0.8 - 0.3 * zn + (fbm(x, z, 0, s=9.0, seed=s) - 0.5) * 0.24
    frost = fbm(x + 40, z, 0, s=6.0, seed=s + 5)
    if frost > 0.6:
        t += 0.12 + (0.1 if frost > 0.68 else 0) + (0.08 if frost > 0.75 else 0)
    t += (h01(cx, cz, s, 3) - 0.5) * 0.04
    d = info["depth"].get(cx, 0)
    if d and z > d - 1.0 and not tag.get("layer"):
        t = max(t, 0.86)
    if tag.get("layer"):
        t += 0.04
    if c.face == "bottom":
        t -= 0.08
    glow = None
    col = pick(MEMB, t)
    if (cx, cz) in info["cracks"] and (cx, cz) not in info["bolts"]:
        col = (16, 42, 78)
    if (cx, cz) in info["bolts"]:
        col = (255, 255, 255)
        glow = (225, 248, 255)
    elif (cx, cz) in info["halo"]:
        col = tuple(int(v * 0.7 + w * 0.3) for v, w in zip(col, (176, 234, 255)))
        glow = (26, 70, 104)
    elif (cx, cz) in info["sparks"]:
        col = (196, 240, 255)
        glow = (90, 170, 210)
    return col, glow


# ------------------------------------------------------------------ pintor principal
def painter(c):
    m = c.cube.mat
    if m == "memb":
        return paint_memb(c)
    if m in ("ice", "ice_d", "plate"):
        base = {"ice": 0.66, "ice_d": 0.52, "plate": 0.86}[m]
        t = base_t(c, base)
        if m == "ice":
            t = scales(c, t)
        if m == "plate":
            if c.edge == 0 and c.fw >= 2 and c.fh >= 2:
                t -= 0.08
            if c.face == "bottom":
                t += 0.14
            return pick(PLATE, t)
        if c.cube.tag.get("nostrils") and c.face == "top" and math.floor(c.lp[2]) == -21 \
                and math.floor(c.lp[0]) in (-2, 1):
            return (20, 28, 42)
        return pick(ICE, t)
    if m == "bone":
        t = base_t(c, 0.74, bevel=0.16, grad=0.22)
        return pick(ICE, t)
    if m == "horn":
        return pick(HORN, base_t(c, 0.62, grad=0.1))
    if m == "horn2":
        col = pick(HORN2, base_t(c, 0.6, grad=0.1))
        return col, (20, 60, 86)
    if m == "crys":
        col = pick(CRYS, base_t(c, 0.62, bevel=0.2, grad=0.0) + 0.1)
        return col, col
    if m == "claw":
        return pick(CLAW, base_t(c, 0.38, bevel=0.2))
    if m == "eye":
        col = (255, 255, 255) if c.i == c.fw // 2 else (196, 248, 255)
        return col, col
    if m == "dark":
        return (20, 26, 38)
    if m == "teeth":
        return (232, 241, 248) if c.j < c.fh - 1 or c.fh == 1 else (196, 210, 222)
    if m == "tongue":
        return pick(TONGUE, base_t(c, 0.5))
    return (255, 0, 255)
