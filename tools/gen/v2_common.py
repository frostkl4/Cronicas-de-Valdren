"""Peças comuns da reformulação visual (v2): esqueleto humanoide, espelhamento, paletas."""
from boxmodel import Part
from pix import ramp, hexc

# Paletas compartilhadas (coerência visual entre os personagens)
GOLD = ramp("#4a3210", "#6e4c18", "#94681f", "#bb8a2c", "#dcae45", "#f5d27a", "#fff0b8")
SILVER = ramp("#3a3f4a", "#575e6c", "#7a8392", "#a2abb8", "#c9d0da", "#eef2f7")
LEATHER = ramp("#1a110b", "#2a1b11", "#3b2718", "#4f3520", "#65442a", "#7d5636")
LEATHER_D = ramp("#120c08", "#1c130d", "#281b12", "#352418", "#432f1f")
WOOD = ramp("#2b1b0f", "#3f2816", "#57391f", "#714b29", "#8b5f35")
IRON = ramp("#262a31", "#3a404a", "#525a66", "#6e7884", "#949eaa", "#c2cad3")
VOID = ramp("#020204", "#06060a", "#0b0b12", "#12121c")
BONE = ramp("#4a4238", "#6b6152", "#8f846f", "#b4a88f", "#d6cbb2", "#f0e8d4")
SKIN_TAN = ramp("#4e2c1e", "#6b3e2a", "#8a5438", "#a86c48", "#c4875c", "#dca37a", "#efc29c")
SKIN_BROWN = ramp("#3a2016", "#53301f", "#6d4029", "#895337", "#a26946", "#bb815a", "#d29c74")
SKIN_PALE = ramp("#5e4436", "#7e5c4a", "#9e7864", "#bd9580", "#d6b19c", "#ecccb8")


def skeleton():
    """Os 7 ossos que o HumanoidModel exige (posições fixas: o Minecraft reposiciona eles)."""
    head = Part("head", (0, 0, 0))
    hat = Part("hat", (0, 0, 0))
    body = Part("body", (0, 0, 0))
    ra = Part("right_arm", (-5, 2, 0))
    la = Part("left_arm", (5, 2, 0))
    rl = Part("right_leg", (-1.9, 12, 0))
    ll = Part("left_leg", (1.9, 12, 0))
    return head, hat, body, ra, la, rl, ll


def mirror_part(src, name_map, parent):
    """Cria o lado esquerdo espelhando (eixo X) uma parte do lado direito e seus filhos."""
    p = Part(name_map(src.name), (-src.pivot[0], src.pivot[1], src.pivot[2]),
             (src.rot[0], -src.rot[1], -src.rot[2]))
    for c in src.cubes:
        x, y, z = c.o
        w, h, d = c.s
        tag = dict(c.tag)
        sw = {"east": "west", "west": "east"}
        g = tag.get("grid")
        if g:
            ng = {}
            for face, rows in g.items():
                ng[sw.get(face, face)] = [r[::-1] for r in rows]
            tag["grid"] = ng
        if tag.get("cols"):
            nc = []
            for x0, x1, mn, faces in tag["cols"]:
                for f in faces:
                    fw = w if f in ("north", "south") else d
                    nc.append((fw - x1, fw - x0, mn, (sw.get(f, f),)))
            tag["cols"] = nc
        p.cube(-(x + w), y, z, w, h, d, c.mat, inflate=c.inflate, tag=tag)
    parent.add(p)
    for ch in src.children:
        mirror_part(ch, name_map, p)
    return p


def lr(name):
    return name.replace("_r", "_l").replace("right", "left")


def humanoid_roots(head, hat, body, ra, la, rl, ll):
    return [head, hat, body, ra, la, rl, ll]


def ring(part, cx, cy, z, radius, n, size, mat, depth=1, tag=None):
    import math
    for k in range(n):
        a = k * 2 * math.pi / n
        part.cube(cx + math.cos(a) * radius - size / 2, cy + math.sin(a) * radius - size / 2, z,
                  size, size, depth, mat, tag=tag or {})


def c(h):
    return hexc(h)
