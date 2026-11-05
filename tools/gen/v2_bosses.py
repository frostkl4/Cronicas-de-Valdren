"""Chefes v2: Cavaleiro Sem Luz (humanoide) e Vorthrax (dragão em escala cheia)."""
import math

from boxmodel import Part, Model
from pix import ramp, hexc, h01, fbm, vnoise
from stylepaint import M, make_painter
from v2_common import (skeleton, mirror_part, lr, humanoid_roots, ring, GOLD, LEATHER, VOID, BONE)

# ======================================================== CAVALEIRO SEM LUZ
STEEL = ramp("#0b0d12", "#13161d", "#1c2029", "#262b36", "#323946", "#424b5b", "#596475", "#7d8896")
CAPE = ramp("#08050c", "#100a17", "#1a1024", "#251733", "#311f44", "#422a5c", "#55377a")
VIOLET = ramp("#3a1a66", "#5f2fa6", "#8b52e6", "#b585ff", "#dcc4ff", "#f6eeff")
OBSID = ramp("#09080c", "#121019", "#1c1926", "#282435", "#373147", "#4a425e")

KNIGHT_MATS = {
    "steel": M(STEEL, "metal", 0.58), "steel_d": M(STEEL, "metal", 0.4), "gold": M(GOLD, "trim", 0.58),
    "violet": M(VIOLET, "trim", 0.62, glow=True), "cape": M(CAPE, "cloth", 0.58),
    "leather": M(LEATHER, "leather", 0.5), "horn": M(OBSID, "along", 0.5, axis="-x"),
    "horn_l": M(OBSID, "along", 0.5, axis="x"), "spike": M(OBSID, "along", 0.55, axis="-y"),
    "void": M(VOID, "flat", 0.4, bevel=False),
}
EYE = hexc("#c9a2ff")
EYE2 = hexc("#f4ecff")


def knight_model():
    head, hat, body, ra, la, rl, ll = skeleton()
    # elmo: visor com olhos violeta, respiros e friso dourado
    head.cube(-4.5, -9, -4.5, 9, 9, 9, "steel", tag={
        "rows": [(8, 9, "gold")],
        "grid": {"north": [
            ".........",
            ".........",
            ".........",
            "ddddddddd",
            "dVvdddvVd",
            "ddddddddd",
            "..s.s.s..",
            "..s.s.s..",
            "ggggggggg",
        ]},
        "pal": {"d": hexc("#05050a"), "V": EYE2, "v": EYE, "s": "void", "g": "gold"}, "glowchars": "Vv"})
    head.cube(-5, -8, -5.2, 10, 2, 2, "steel_d", tag={"rows": [(0, 1, "gold")]})
    head.cube(-1, -12, -4.5, 2, 3, 10, "steel_d", tag={"rows": [(0, 1, "gold")]})
    head.cube(-0.5, -13, -2.5, 1, 1, 6, "violet")
    hr = head.add(Part("horn_r", (-4.5, -6.5, 0), (0, 0.15, 0.35)))
    hr.cube(-4, -1, -1, 4, 2, 2, "horn")
    hr2 = hr.add(Part("horn_r2", (-4, 0, 0), (0, 0, 0.7)))
    hr2.cube(-3, -1, -1, 3, 2, 2, "horn")
    hr3 = hr2.add(Part("horn_r3", (-3, 0, 0), (0, 0, 0.6)))
    hr3.cube(-3, -0.5, -0.5, 3, 1, 1, "violet")
    mirror_part(hr, lambda n: n.replace("_r", "_l"), head)
    halo = head.add(Part("halo", (0, -5, 5.6)))
    ring(halo, 0, 0, 0, 7.0, 22, 2, "violet")
    halo.cube(-1, -1, 0.5, 2, 2, 1, "void")

    # peito com o emblema do eclipse
    body.cube(-5, -0.5, -3, 10, 8, 6, "steel", tag={
        "grid": {"north": [
            "..........",
            "...gggg...",
            "..gdddVg..",
            "..gdddvg..",
            "..gdddvg..",
            "...gggg...",
            "..........",
            "ssssssssss",
        ]},
        "pal": {"g": "gold", "d": "void", "V": EYE2, "v": "violet", "s": "steel_d"}, "glowchars": "V"})
    body.cube(-4.5, -2, -3.5, 9, 2, 7, "steel_d", tag={"rows": [(0, 1, "gold")]})
    body.cube(-4.5, 7.5, -2.5, 9, 3, 5, "steel_d")
    body.cube(-5, 10.5, -3, 10, 2, 6, "leather", tag={
        "grid": {"north": ["....gg....", "....gg...."]}, "pal": {"g": "gold"}})
    tf = body.add(Part("tasset_f", (0, 12, -3)))
    tf.cube(-4.5, 0, -0.5, 9, 5, 1, "steel", tag={"rows": [(4, 5, "gold")]})
    tb = body.add(Part("tasset_b", (0, 12, 3)))
    tb.cube(-4.5, 0, -0.5, 9, 5, 1, "steel", tag={"rows": [(4, 5, "gold")]})
    ts = body.add(Part("tasset_r", (-4.6, 12, 0)))
    ts.cube(-0.5, 0, -2.5, 1, 4, 5, "steel_d", tag={"rows": [(3, 4, "gold")]})
    mirror_part(ts, lr, body)
    cape = body.add(Part("cape", (0, -1, 3.4), (0.1, 0, 0)))
    cape.cube(-5.5, 0, 0, 11, 12, 1, "cape", tag={"rows": [(0, 1, "gold")]})
    capel = cape.add(Part("cape_lower", (0, 12, 0)))
    capel.cube(-5.5, 0, 0, 11, 10, 1, "cape", tag={"cut": "rag2"})

    # braço direito: braçadeira, manopla e ombreira com espinhos
    ra.cube(-4, -2, -2.5, 5, 7, 5, "steel_d")
    ra.cube(-4.5, 5, -3, 6, 6, 6, "steel", tag={"rows": [(0, 1, "gold")]})
    pr = ra.add(Part("pauldron_r", (-2, -2, 0)))
    pr.cube(-4.5, -2.5, -4, 7, 4, 8, "steel", tag={"rows": [(3, 4, "gold")]})
    pr.cube(-5, 0.5, -4.5, 6, 2, 9, "steel_d")
    for k, z in enumerate((-2.5, -0.5, 1.5)):
        hgt = 3 if k == 1 else 2
        pr.cube(-3, -2.5 - hgt, z, 1, hgt, 1, "spike")
    for c in ra.cubes:
        la.cube(-(c.o[0] + c.s[0]), c.o[1], c.o[2], *c.s, c.mat, tag=dict(c.tag))
    mirror_part(pr, lr, la)

    # pernas: coxote, joelheira, grevas e sapatos de placa
    rl.cube(-2.5, 0, -2.5, 5, 6, 5, "steel_d")
    rl.cube(-2.5, 5, -3.4, 5, 3, 2, "steel", tag={"rows": [(0, 1, "gold")]})
    rl.cube(-2.5, 6, -2.5, 5, 4, 5, "steel")
    rl.cube(-3, 10, -3.5, 6, 2, 6, "steel_d")
    rl.cube(-2.5, 10, -5, 5, 2, 2, "steel")
    for c in rl.cubes:
        ll.cube(-(c.o[0] + c.s[0]), c.o[1], c.o[2], *c.s, c.mat, tag=dict(c.tag))

    m = Model("lightless_knight_model", 128, 128, humanoid_roots(head, hat, body, ra, la, rl, ll))
    m.pack()
    return m


KNIGHT_ANIM = """
        float partial = ageInTicks - entity.tickCount;
        float hurt = entity.hurtTime > 0 ? (entity.hurtTime - partial) / 10.0F : 0.0F;
        float walk = Math.min(limbSwingAmount, 1.0F);
        double speed = entity.getDeltaMovement().horizontalDistance();
        float dash = (float) Math.min(1.0D, Math.max(0.0D, speed - 0.2D) * 2.0D);
        this.cape.xRot += walk * 0.7F + dash * 0.8F + Mth.sin(ageInTicks * 0.06F) * 0.04F;
        this.capeLower.xRot += walk * 0.35F + dash * 0.4F + Mth.sin(ageInTicks * 0.08F + 1.0F) * 0.06F;
        this.tassetF.xRot += Math.min(0.0F, Math.min(this.rightLeg.xRot, this.leftLeg.xRot)) * 0.8F;
        this.tassetB.xRot += Math.max(0.0F, Math.max(this.rightLeg.xRot, this.leftLeg.xRot)) * 0.8F;
        this.tassetR.zRot += walk * 0.12F;
        this.tassetL.zRot -= walk * 0.12F;
        this.halo.zRot += ageInTicks * 0.025F;
        this.halo.y += Mth.sin(ageInTicks * 0.1F) * 0.35F;
        this.head.xRot -= hurt * 0.45F;
        this.body.xRot -= hurt * 0.12F;
        this.rightArm.xRot -= 0.12F;
        if (this.attackTime > 0.0F) {
            this.leftArm.xRot = this.rightArm.xRot;
            this.leftArm.yRot = 0.45F + this.rightArm.yRot;
            this.leftArm.zRot = 0.0F;
        }
"""
KNIGHT_PARTS = ["horn_r", "horn_l", "halo", "tasset_f", "tasset_b", "tasset_r", "tasset_l", "cape", "cape_lower",
                "pauldron_r", "pauldron_l"]

# ================================================================= VORTHRAX
SCALE = ramp("#090c11", "#0f141b", "#161d27", "#1f2834", "#293545", "#344457", "#43576e", "#587089", "#7590ab")
BELLY = ramp("#1b2230", "#263040", "#344053", "#455368", "#5b6a80", "#76869b", "#93a3b6")
HORN = ramp("#2a2622", "#433d36", "#625a4f", "#877d6e", "#ad a291".replace(" ", ""), "#d1c7b3", "#ece4d2")
MEMB = ramp("#060c16", "#0a1422", "#0f1d31", "#152943", "#1c3656", "#24456b", "#2e5680")
TEETH = ramp("#5c574c", "#8e8676", "#bdb4a0", "#e2dac6", "#f7f1e2")
CLAW = ramp("#0c0d10", "#191b20", "#272a31", "#3a3e47", "#525865")
VEIN = hexc("#79f3ff")
VEIN2 = hexc("#e4ffff")
EYE_D = hexc("#a8faff")


def _bolt(x, y, seed, width=0.012, density=0.58):
    if vnoise(x, y, 0, s=13, seed=seed + 99) < density:
        return False
    v = fbm(x, y, 0, s=9, seed=seed, oct=2)
    return abs(v - 0.5) < width


def scale_veins(c):
    if c.face in ("top", "east", "west") and _bolt(c.gp[0] * 0.5 + c.gp[2], c.gp[1] + c.gp[2] * 0.25, 13, 0.008, 0.64):
        return VEIN, VEIN
    return None


def membrane_fn(c):
    if c.face not in ("top", "bottom"):
        return None
    seg = c.cube.tag.get("seg", 0)
    ax = abs(c.gp[0]) + seg * 3
    az = c.gp[2]
    # trailing edge recortado em ondas (fica vazado)
    back = c.cube.o[2] + c.cube.s[2] + c.part.abs_pivot()[2]
    dist = back - az
    wave = 3.0 + 2.5 * math.sin(abs(c.gp[0]) * 0.55)
    if dist < wave - 2.0:
        return (0, 0, 0, 0)
    # dedos da asa
    if (abs(c.gp[0]) % 11.0) < 1.0 and dist > wave:
        col = SCALE[3] if c.face == "top" else SCALE[2]
        return col
    if dist < wave - 0.8:
        return MEMB[1]
    if _bolt(ax * 0.7, az * 0.7, 51 + seg, 0.014, 0.45):
        g = VEIN2 if h01(c.i, c.j, 3) > 0.65 else VEIN
        return g, g
    return None


DRAGON_MATS = {
    "scale": M(SCALE, "scale", 0.55, grad=0.25, noise=0.0, dither=False), "scale_d": M(SCALE, "scale", 0.4, grad=0.25, noise=0.0, dither=False),
    "belly": M(BELLY, "metal", 0.5, grad=0.1), "horn": M(HORN, "along", 0.45),
    "horn_y": M(HORN, "along", 0.45, axis="-y"), "teeth": M(TEETH, "flat", 0.7, bevel=False, noise=0.08),
    "claw": M(CLAW, "along", 0.45, axis="y"), "memb": M(MEMB, "cloth", 0.55, grad=0.0, folds=11),
    "spine": M(SCALE, "metal", 0.45), "vein": M(ramp("#1b6f8f", "#2fa3c4", "#5fd6ef", "#a8f6ff", "#e8ffff"), "trim", 0.6, glow=True),
    "bone": M(SCALE, "metal", 0.42),
    "vein_d": M(ramp("#0f3f55", "#17607d", "#2390ad", "#45bfd8", "#86e6f5"), "trim", 0.45, glow=True),
}


def _plates(part, zs, x=-1.5, top=-10, base_h=4, w=3, d=4):
    for k, z in enumerate(zs):
        h = base_h + (k % 2)
        part.cube(x, top - h, z, w, h, d, "spine", tag={"rows": [(0, 1, "vein_d")]})


def dragon_model():
    V = {"fn": scale_veins}
    body = Part("body", (0, -2, 0))
    body.cube(-11, -10, -20, 22, 20, 22, "scale", tag=V)
    body.cube(-10, -9, 2, 20, 17, 16, "scale", tag=V)
    body.cube(-9, -9, 18, 18, 15, 10, "scale", tag=V)
    body.cube(-8, 8, -18, 16, 2, 34, "belly", tag={"rows": [(0, 2, "belly")]})
    body.cube(-12, -12, -16, 7, 6, 10, "scale_d")
    body.cube(5, -12, -16, 7, 6, 10, "scale_d")
    _plates(body, (-18, -11, -4, 3, 10, 17, 23), top=-10)

    n1 = body.add(Part("neck1", (0, -5, -19), (-1.0, 0, 0)))
    n1.cube(-7, -7, -14, 14, 13, 15, "scale", tag=V)
    n1.cube(-5, 6, -13, 10, 1, 13, "belly")
    _plates(n1, (-12, -6), top=-7, base_h=3)
    n2 = n1.add(Part("neck2", (0, -1, -13), (0.3, 0, 0)))
    n2.cube(-6, -6, -13, 12, 11, 14, "scale", tag=V)
    n2.cube(-4, 5, -12, 8, 1, 12, "belly")
    _plates(n2, (-11, -5), top=-6, base_h=3)
    n3 = n2.add(Part("neck3", (0, 0, -12), (0.35, 0, 0)))
    n3.cube(-5, -5, -12, 10, 9, 13, "scale", tag=V)
    n3.cube(-3.5, 4, -11, 7, 1, 11, "belly")
    _plates(n3, (-9,), top=-5, base_h=3, w=2, x=-1)

    head = n3.add(Part("head", (0, 0, -11), (0.38, 0, 0)))
    eyes = {"east": ["................", "................", "................", "................",
                     "...ddd..........", "..dEeed.........", "...ddd..........", "................"],
            "west": ["................", "................", "................", "................",
                     "..........ddd...", ".........deeEd..", "..........ddd...", "................"]}
    head.cube(-8, -8, -14, 16, 13, 16, "scale", tag={"grid": eyes, "pal": {"d": hexc("#05080c"), "E": VEIN2, "e": EYE_D},
                                                      "glowchars": "Ee"})
    head.cube(-6, -5, -28, 12, 8, 15, "scale")
    head.cube(-5, -6, -27, 3, 2, 3, "scale_d")
    head.cube(2, -6, -27, 3, 2, 3, "scale_d")
    head.cube(-9, -10, -16, 6, 4, 10, "scale_d", tag={"rows": [(0, 1, "spine")]})
    head.cube(3, -10, -16, 6, 4, 10, "scale_d", tag={"rows": [(0, 1, "spine")]})
    head.cube(-1.5, -7, -27.5, 3, 2, 6, "horn_y")
    head.cube(-10.5, -4, -8, 3, 7, 8, "spine", tag={"cols": [(0, 1, "vein", ("west",))]})
    head.cube(7.5, -4, -8, 3, 7, 8, "spine", tag={"cols": [(7, 8, "vein", ("east",))]})
    head.cube(-5.5, 3, -27, 11, 2, 13, "teeth", tag={"grid": {f: ["t.t.t.t.t.t.t", "t t t t t t t"] for f in ("west", "east")},
                                                     "pal": {"t": "teeth"}})
    for side, sx in (("r", -1), ("l", 1)):
        h1 = head.add(Part(f"horn_{side}", (6 * sx, -8, -6), (0.55, 0.3 * sx, 0)))
        h1.cube(-2, -2, 0, 4, 4, 10, "horn")
        h2 = h1.add(Part(f"horn_{side}2", (0, 0, 9), (0.4, 0, 0)))
        h2.cube(-1.5, -1.5, 0, 3, 3, 9, "horn")
        h3 = h2.add(Part(f"horn_{side}3", (0, 0, 8), (0.45, 0, 0)))
        h3.cube(-1, -1, 0, 2, 2, 7, "horn")
        h4 = h3.add(Part(f"horn_{side}4", (0, 0, 6.5), (0.35, 0, 0)))
        h4.cube(-0.5, -0.5, 0, 1, 1, 4, "vein")
        head.cube(4 * sx - 1, -10, -11, 2, 3, 3, "horn_y")
    jaw = head.add(Part("jaw", (0, 4, -12), (0.1, 0, 0)))
    jaw.cube(-5.5, 0, -16, 11, 5, 17, "scale")
    jaw.cube(-5, -2, -15.5, 10, 2, 12, "teeth", tag={"grid": {f: ["t t t t t t ", "tttttttttttt"] for f in ("west", "east")},
                                                    "pal": {"t": "teeth"}})
    jaw.cube(-1, 4, -14, 2, 3, 4, "horn_y")

    prev = body
    specs = [("tail1", (0, -3, 28), (-0.15, 0, 0), (-7, -6, -2, 14, 12, 18)),
             ("tail2", (0, 0.5, 16), (-0.1, 0, 0), (-5.5, -5, 0, 11, 10, 18)),
             ("tail3", (0, 0.5, 17), (0, 0, 0), (-4, -3.5, 0, 8, 7, 18)),
             ("tail4", (0, 0, 17), (0, 0, 0), (-2.5, -2.5, 0, 5, 5, 16)),
             ("tail5", (0, 0, 15), (0, 0, 0), (-1.5, -1.5, 0, 3, 3, 14))]
    for k, (nm, pv, rt, box) in enumerate(specs):
        t = prev.add(Part(nm, pv, rt))
        t.cube(*box, "scale", tag=V)
        if k < 3:
            _plates(t, (2, 9) if k < 2 else (4,), top=box[1], base_h=3 - k // 2, w=2 if k else 3, x=-1 if k else -1.5)
        prev = t
    prev.cube(-0.5, -7, 3, 1, 14, 10, "spine", tag={"cols": [(0, 1, "vein", ("north", "south"))],
                                                    "rows": [(0, 1, "vein"), (13, 14, "vein")]})

    def foot(p, y):
        p.cube(-4.5, y, -8, 9, 4, 11, "scale_d")
        for x in (-4, -1, 2):
            p.cube(x, y + 1, -11, 2, 3, 3, "claw")
        p.cube(-1, y + 1, 3, 2, 2, 2, "claw")

    for side, sx in (("r", -1), ("l", 1)):
        fl = body.add(Part(f"leg_f{side}", (12 * sx, -1, -12)))
        fl.cube(-4, -3, -5, 8, 15, 10, "scale", tag=V)
        fore = fl.add(Part(f"leg_f{side}_fore", (0, 11, 1)))
        fore.cube(-3, 0, -3.5, 6, 11, 7, "scale")
        ff = fore.add(Part(f"leg_f{side}_foot", (0, 10, 0)))
        foot(ff, 2)
        bl = body.add(Part(f"leg_b{side}", (11 * sx, -1, 20)))
        bl.cube(-5, -4, -7, 10, 17, 13, "scale", tag=V)
        shin = bl.add(Part(f"leg_b{side}_shin", (0, 12, 3)))
        shin.cube(-3.5, 0, -3, 7, 10, 7, "scale")
        bf = shin.add(Part(f"leg_b{side}_foot", (0, 9, 0)))
        foot(bf, 2)

    for side, sx in (("r", -1), ("l", 1)):
        w1 = body.add(Part(f"wing_{side}", (10 * sx, -11, -12)))
        lo = lambda n: 0 if sx > 0 else -n
        w1.cube(lo(26), -2, -2, 26, 4, 4, "bone")
        w1.cube(lo(26), 0, 0, 26, 1, 30, "memb", tag={"fn": membrane_fn, "seg": 0})
        w2 = w1.add(Part(f"wing_{side}_mid", (26 * sx, 0, 0)))
        w2.cube(lo(24), -1.5, -1.5, 24, 3, 3, "bone")
        w2.cube(lo(24), 0, 0, 24, 1, 28, "memb", tag={"fn": membrane_fn, "seg": 1})
        w2.cube((-1 if sx > 0 else -2), -4, -2, 3, 4, 3, "claw")
        w3 = w2.add(Part(f"wing_{side}_tip", (24 * sx, 0, 0)))
        w3.cube(lo(22), -1, -1, 22, 2, 2, "bone")
        w3.cube(lo(22), 0, 0, 22, 1, 22, "memb", tag={"fn": membrane_fn, "seg": 2})

    m = Model("storm_dragon", 512, 256, [body])
    m.pack()
    return m


DRAGON_ANIM = """
        float t = ageInTicks;
        float partial = ageInTicks - entity.tickCount;
        boolean flying = entity.isFlying();
        float yaw = netHeadYaw * Mth.DEG_TO_RAD;
        float pitch = headPitch * Mth.DEG_TO_RAD;
        float hurt = entity.hurtTime > 0 ? (entity.hurtTime - partial) / 10.0F : 0.0F;
        float death = entity.deathTime > 0 ? Math.min(1.0F, (entity.deathTime + partial) / 20.0F) : 0.0F;
        float roar = entity.getRoarAmount(partial);
        float breathe = Mth.sin(t * 0.07F);
        this.neck1.yRot += yaw * 0.25F;
        this.neck2.yRot += yaw * 0.25F;
        this.neck3.yRot += yaw * 0.25F;
        this.head.yRot += yaw * 0.25F;
        this.neck1.xRot += pitch * 0.2F + breathe * 0.03F - roar * 0.35F + hurt * 0.2F;
        this.neck3.xRot += roar * 0.15F;
        this.head.xRot += pitch * 0.4F + roar * 0.2F - hurt * 0.35F;
        this.jaw.xRot += 0.05F + this.attackTime * 1.0F + roar * 0.9F + hurt * 0.4F + breathe * 0.02F;
        this.body.y += breathe * 0.6F;
        this.tail1.yRot += Mth.sin(t * 0.06F) * 0.06F;
        this.tail2.yRot += Mth.sin(t * 0.06F - 0.5F) * 0.09F;
        this.tail3.yRot += Mth.sin(t * 0.06F - 1.0F) * 0.12F;
        this.tail4.yRot += Mth.sin(t * 0.06F - 1.5F) * 0.15F;
        this.tail5.yRot += Mth.sin(t * 0.06F - 2.0F) * 0.18F;
        this.tail3.xRot += Mth.sin(t * 0.05F) * 0.04F;
        if (flying) {
            float f = t * 0.28F;
            this.wingL.zRot += -Mth.sin(f) * 0.65F + 0.05F;
            this.wingR.zRot += Mth.sin(f) * 0.65F - 0.05F;
            this.wingLMid.zRot += -Mth.sin(f - 0.7F) * 0.35F;
            this.wingRMid.zRot += Mth.sin(f - 0.7F) * 0.35F;
            this.wingLTip.zRot += -Mth.sin(f - 1.4F) * 0.4F;
            this.wingRTip.zRot += Mth.sin(f - 1.4F) * 0.4F;
            this.wingL.yRot += Mth.cos(f) * 0.12F;
            this.wingR.yRot -= Mth.cos(f) * 0.12F;
            this.body.y += Mth.sin(f) * 1.5F;
            this.legFl.xRot += 0.9F;
            this.legFr.xRot += 0.9F;
            this.legFlFore.xRot += 0.6F;
            this.legFrFore.xRot += 0.6F;
            this.legBl.xRot += 1.0F;
            this.legBr.xRot += 1.0F;
            this.legBlShin.xRot += 0.5F;
            this.legBrShin.xRot += 0.5F;
            this.legBlFoot.xRot += 0.6F;
            this.legBrFoot.xRot += 0.6F;
            this.tail1.xRot += 0.12F;
            this.neck1.xRot += 0.15F;
        } else {
            this.wingL.xRot += -1.5708F;
            this.wingL.yRot += -1.5708F;
            this.wingL.zRot += -0.45F + breathe * 0.03F;
            this.wingLMid.zRot += 2.85F;
            this.wingLTip.zRot += -2.7F;
            this.wingR.xRot += -1.5708F;
            this.wingR.yRot += 1.5708F;
            this.wingR.zRot += 0.45F - breathe * 0.03F;
            this.wingRMid.zRot += -2.85F;
            this.wingRTip.zRot += 2.7F;
            float swing = limbSwing * 0.35F;
            float amt = Math.min(limbSwingAmount, 1.0F);
            float a = Mth.cos(swing) * 0.5F * amt;
            float b = Mth.cos(swing + Mth.PI) * 0.5F * amt;
            this.legFl.xRot += a;
            this.legBr.xRot += a;
            this.legFr.xRot += b;
            this.legBl.xRot += b;
            this.legFlFore.xRot += Math.max(0.0F, -a) * 0.6F;
            this.legFrFore.xRot += Math.max(0.0F, -b) * 0.6F;
            this.legFlFoot.xRot -= a + Math.max(0.0F, -a) * 0.6F;
            this.legFrFoot.xRot -= b + Math.max(0.0F, -b) * 0.6F;
            this.legBrShin.xRot += Math.max(0.0F, a) * 0.5F;
            this.legBlShin.xRot += Math.max(0.0F, b) * 0.5F;
            this.legBrFoot.xRot -= a + Math.max(0.0F, a) * 0.5F;
            this.legBlFoot.xRot -= b + Math.max(0.0F, b) * 0.5F;
            this.body.zRot += Mth.sin(swing) * 0.03F * amt;
        }
        if (death > 0.0F) {
            this.body.y += death * 9.0F;
            this.neck1.xRot += death * 0.7F;
            this.head.xRot += death * 0.3F;
            this.jaw.xRot += death * 0.5F;
            this.legFl.zRot -= death * 0.6F;
            this.legFr.zRot += death * 0.6F;
            this.legBl.zRot -= death * 0.6F;
            this.legBr.zRot += death * 0.6F;
            this.wingL.zRot += death * 0.5F;
            this.wingR.zRot -= death * 0.5F;
            this.tail1.xRot -= death * 0.15F;
        }
"""
DRAGON_PARTS = ["body", "neck1", "neck2", "neck3", "head", "jaw", "tail1", "tail2", "tail3", "tail4", "tail5",
                "leg_fl", "leg_fr", "leg_fl_fore", "leg_fr_fore", "leg_fl_foot", "leg_fr_foot",
                "leg_bl", "leg_br", "leg_bl_shin", "leg_br_shin", "leg_bl_foot", "leg_br_foot",
                "wing_l", "wing_r", "wing_l_mid", "wing_r_mid", "wing_l_tip", "wing_r_tip"]
DRAGON_POSE_GROUND = {"wing_l": (-1.5708, -1.5708, -0.45), "wing_l_mid": (0, 0, 2.85), "wing_l_tip": (0, 0, -2.7),
                      "wing_r": (-1.5708, 1.5708, 0.45), "wing_r_mid": (0, 0, -2.85), "wing_r_tip": (0, 0, 2.7)}
DRAGON_POSE_FLY = {"wing_l": (0, 0, -0.3), "wing_r": (0, 0, 0.3), "wing_l_mid": (0, 0, 0.15), "wing_r_mid": (0, 0, -0.15),
                   "wing_l_tip": (0, 0, 0.2), "wing_r_tip": (0, 0, -0.2), "leg_fl": (0.9, 0, 0), "leg_fr": (0.9, 0, 0),
                   "leg_bl": (1.0, 0, 0), "leg_br": (1.0, 0, 0), "tail1": (0.12, 0, 0)}
