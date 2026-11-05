"""Modelos e pinturas das criaturas: Lobo Sombrio, Espectro Gelido e Vorthrax (dragao)."""
import math

from boxmodel import Part, Model, Cube
from pix import ramp, pick, pick_dither, h01, vnoise, fbm, hexc, mix, clamp01

# ============================================================ LOBO SOMBRIO

FUR = ramp("#0b0912", "#13101c", "#1c1727", "#262034", "#322a45", "#43385c", "#584a78")
MANE = ramp("#15111f", "#211b30", "#2e2643", "#3d3358", "#51456f", "#695a8c")
SPIKE = ramp("#1d1626", "#2f2440", "#4a3a62", "#6e5c8c", "#9886b4", "#c2b4dc")
SNOUT = ramp("#09070d", "#110e18", "#1a1524", "#251e33")
WOLF_EYE = hexc("#e65cff")
WOLF_EYE2 = hexc("#ffd0ff")


def wolf_model():
    body = Part("body", (0, 12, 0))
    body.cube(-4.5, -4.5, -7, 9, 9, 8, "fur")
    body.cube(-3.5, -3.5, 1, 7, 7, 8, "fur")
    body.cube(-1, -5.5, 3, 2, 2, 2, "spike")
    body.cube(-0.5, -5, 6, 1, 2, 2, "spike")
    mane = body.add(Part("mane", (0, -4, -4)))
    mane.cube(-5.5, -2, -4, 11, 4, 8, "mane")
    mane.cube(-1, -5, -2, 2, 3, 3, "spike")
    mane.cube(-4, -4, -1, 2, 2, 3, "spike")
    mane.cube(2, -4, -1, 2, 2, 3, "spike")
    mane.cube(-1, -3.5, 3, 2, 2, 3, "spike")
    mane.cube(-6, 1, -3, 1, 4, 5, "mane")
    mane.cube(5, 1, -3, 1, 4, 5, "mane")

    head = body.add(Part("head", (0, -2.5, -7)))
    head.cube(-3.5, -4, -6, 7, 7, 7, "skull")
    head.cube(-2, -1, -10, 4, 3, 4, "snout")
    head.cube(-1, -1.4, -10.4, 2, 1, 1, "nose")
    head.cube(-3.5, -7, -3, 2, 3, 2, "ear")
    head.cube(1.5, -7, -3, 2, 3, 2, "ear")
    head.cube(-3.8, -5, -1, 1, 2, 4, "spike")
    head.cube(2.8, -5, -1, 1, 2, 4, "spike")
    head.cube(-4.5, -1, -4, 2, 4, 4, "mane")
    head.cube(2.5, -1, -4, 2, 4, 4, "mane")
    head.cube(-1.8, 2, -9.6, 1, 1, 1, "teeth")
    head.cube(0.8, 2, -9.6, 1, 1, 1, "teeth")
    jaw = head.add(Part("jaw", (0, 2, -6)))
    jaw.cube(-1.5, 0, -4, 3, 1, 4, "snout")

    def leg(name, x, z, back):
        p = body.add(Part(name, (x, 2, z)))
        if back:
            p.cube(-1.5, -1.5, -2, 3, 6, 4, "fur")
            p.cube(-1, 4, 0, 2, 5, 2, "leg")
            p.cube(-1.5, 9, -1.5, 3, 1, 3, "paw")
        else:
            p.cube(-1.5, -1, -1.5, 3, 7, 3, "fur")
            p.cube(-1, 6, -1, 2, 3, 2, "leg")
            p.cube(-1.5, 9, -2.5, 3, 1, 3, "paw")
        return p

    leg("leg_fl", 2.6, -4.5, False)
    leg("leg_fr", -2.6, -4.5, False)
    leg("leg_bl", 2.3, 6.5, True)
    leg("leg_br", -2.3, 6.5, True)

    tail = body.add(Part("tail", (0, -2, 8.5), (-0.55, 0, 0)))
    tail.cube(-1.5, -1.5, 0, 3, 3, 6, "mane")
    tip = tail.add(Part("tail_tip", (0, 0, 6), (0.25, 0, 0)))
    tip.cube(-1, -1, 0, 2, 2, 5, "mane")
    tip.cube(-0.5, -0.5, 5, 1, 1, 2, "spike")
    m = Model("shadow_wolf", 128, 64, [body])
    m.pack()
    return m


def _fur(r, c, streak=1.0):
    n = fbm(c.gp[0] * 1.3, c.gp[1] * 0.45, c.gp[2] * 1.3, s=3.0, seed=11)
    grain = h01(c.i, c.j, int(c.gp[0] * 3), int(c.gp[2] * 3)) * 0.12
    light = 0.62 - c.vfrac * 0.42
    if c.face == "top":
        light = 0.75
    elif c.face == "bottom":
        light = 0.12
    t = light + (n - 0.5) * 0.45 * streak + grain
    if c.edge == 0 and c.face != "bottom":
        t -= 0.12
    return pick_dither(r, t, c.i, c.j)


def wolf_painters():
    def fur(c):
        col = _fur(FUR, c)
        glow = None
        # veios violeta tenues ao longo do corpo
        vv = abs(fbm(c.gp[0] * 2, c.gp[1] * 2, c.gp[2] * 0.8, s=5, seed=4) - 0.5)
        if c.face in ("top", "east", "west") and vv < 0.018 and c.vfrac < 0.6:
            col = hexc("#7a4cb0")
            glow = (150, 70, 220, 150)
        return col, glow

    def mane(c):
        return _fur(MANE, c, 1.4)

    def skull(c):
        col = _fur(FUR, c)
        glow = None
        if c.face == "north" and c.j == 1 and c.i in (1, 2, 4, 5):
            col = hexc("#07050b")
        if c.face == "north" and c.j == 2 and c.i in (1, 2, 4, 5):
            col = WOLF_EYE2 if c.i in (2, 4) else WOLF_EYE
            glow = col
        if c.face in ("east", "west") and c.j == 2:
            front = c.i <= 0 if c.face == "east" else c.i >= c.fw - 1
            if front:
                col = WOLF_EYE
                glow = col
        return col, glow

    def snout(c):
        return _fur(SNOUT, c)

    def nose(c):
        return hexc("#050407") if c.edge else hexc("#1a1622")

    def ear(c):
        if c.face == "north" and 0 < c.i < c.fw - 1 and c.j > 0:
            return hexc("#3a1c3e")
        return _fur(MANE, c)

    def spike(c):
        t = 0.95 - c.vfrac * 0.7
        if c.face == "bottom":
            t = 0.2
        return pick(SPIKE, t + (h01(c.i, c.j, 7) - 0.5) * 0.1)

    def leg(c):
        return _fur(FUR, c, 0.8)

    def paw(c):
        if c.face == "north" and c.j == c.fh - 1 and c.i % 2 == 0:
            return hexc("#c9bcd8")
        return pick(SNOUT, 0.4 - c.vfrac * 0.3)

    def teeth(c):
        return hexc("#ddd5c4") if c.face != "top" else hexc("#a9a091")

    return {"fur": fur, "mane": mane, "skull": skull, "snout": snout, "nose": nose, "ear": ear,
            "spike": spike, "leg": leg, "paw": paw, "teeth": teeth}


WOLF_ANIM = """
        float swing = limbSwing * 0.6662F;
        float amt = Math.min(limbSwingAmount, 1.0F);
        this.legFl.xRot += Mth.cos(swing) * 1.2F * amt;
        this.legBr.xRot += Mth.cos(swing) * 1.2F * amt;
        this.legFr.xRot += Mth.cos(swing + Mth.PI) * 1.2F * amt;
        this.legBl.xRot += Mth.cos(swing + Mth.PI) * 1.2F * amt;
        this.head.yRot += netHeadYaw * Mth.DEG_TO_RAD;
        this.head.xRot += headPitch * Mth.DEG_TO_RAD;
        this.jaw.xRot += 0.05F + this.attackTime * 0.9F + Mth.sin(ageInTicks * 0.09F) * 0.04F;
        this.body.y += Mth.sin(ageInTicks * 0.1F) * 0.25F;
        this.mane.xRot += Mth.sin(ageInTicks * 0.1F) * 0.03F;
        this.tail.yRot += Mth.sin(ageInTicks * 0.2F) * 0.25F;
        this.tail.xRot += amt * 0.3F;
        this.tailTip.yRot += Mth.sin(ageInTicks * 0.2F - 0.8F) * 0.3F;
"""
WOLF_PARTS = ["body", "mane", "head", "jaw", "leg_fl", "leg_fr", "leg_bl", "leg_br", "tail", "tail_tip"]

# ========================================================== ESPECTRO GELIDO

HOOD = ramp("#070b16", "#0d1424", "#141e35", "#1d2b48", "#283b5e", "#365079", "#4a6896")
MANTLE = ramp("#2e4762", "#43678a", "#5f8aae", "#86b2d0", "#b4d8ec", "#e2f4ff")
ROBE = ramp("#05070e", "#0a0f1c", "#111a2e", "#1a2843", "#25395c", "#334d77")
ICE = ramp("#2f7db0", "#4fa6d6", "#7fcdf0", "#b3eaff", "#e9fbff")
PALE = ramp("#3f5163", "#5f7488", "#8399ad", "#a9bccc", "#d0dde7")
FROST_EYE = hexc("#8ff6ff")


def wraith_model():
    torso = Part("torso", (0, 6, 0))
    torso.cube(-4.5, -10, -2.5, 9, 9, 5, "robe_up")
    torso.cube(-5.5, -10.5, -3, 11, 4, 6, "mantle")
    torso.cube(-5.5, -13, -1, 1, 3, 1, "ice")
    torso.cube(-4, -12.5, 1, 1, 2, 1, "ice")
    torso.cube(4.5, -13, -1, 1, 3, 1, "ice")
    torso.cube(3, -12.5, 1, 1, 2, 1, "ice")

    hood = torso.add(Part("hood", (0, -10, 0)))
    hood.cube(-4, -8, -4.5, 8, 8, 8, "hood_main")
    hood.cube(-4.5, -8.5, -5, 9, 2, 2, "hood")
    peak = hood.add(Part("hood_peak", (0, -8, 2), (0.95, 0, 0)))
    peak.cube(-2.5, -2, -2, 5, 2, 4, "hood")
    peak.cube(-1.5, -3, -1, 3, 1, 3, "hood")
    peak.cube(-0.5, -4, 0, 1, 1, 2, "hood")

    for side, x in (("r", -6), ("l", 6)):
        sgn = -1 if side == "r" else 1
        arm = torso.add(Part(f"arm_{side}", (x, -8, 0), (-0.35, 0, 0.18 * sgn)))
        arm.cube(-1.5, -1, -1.5, 3, 12, 3, "sleeve")
        arm.cube(-2, 7, -2, 4, 4, 4, "cuff")
        hand = arm.add(Part(f"hand_{side}", (0, 11, 0)))
        hand.cube(-1, 0, -1, 2, 2, 2, "skin")
        hand.cube(-1.2, 2, -1.2, 1, 3, 1, "claw")
        hand.cube(0.2, 2, -1.2, 1, 3, 1, "claw")
        hand.cube(-0.5, 2, 0.2, 1, 3, 1, "claw")

    skirt = torso.add(Part("skirt", (0, -1, 0)))
    skirt.cube(-5, 0, -3, 10, 6, 6, "robe_low")
    strips = [(-4, -3.1, 2, 7), (-1, -3.1, 2, 8), (2, -3.1, 2, 6),
              (-4, 2.1, 2, 6), (-1, 2.1, 2, 8), (2, 2.1, 2, 7)]
    for k, (x, z, w, ln) in enumerate(strips):
        tp = skirt.add(Part(f"tatter_{k}", (x + w / 2, 6, z + 0.5)))
        tp.cube(-w / 2, 0, -0.5, w, ln, 1, "tatter")
    for k, x in enumerate((-5.1, 4.1)):
        tp = skirt.add(Part(f"tatter_{6 + k}", (x + 0.5, 6, 0)))
        tp.cube(-0.5, 0, -2, 1, 7, 4, "tatter")

    orbit = torso.add(Part("orbit", (0, -4, 0)))
    for k in range(3):
        a = k * 2 * math.pi / 3
        cr = orbit.add(Part(f"crystal_{k}", (math.cos(a) * 9, 0, math.sin(a) * 9), (0.3, 0, 0.25)))
        cr.cube(-1, -2.5, -1, 2, 5, 2, "ice_glow")
        cr.cube(-0.5, -4, -0.5, 1, 2, 1, "ice_glow")
    m = Model("frost_wraith", 128, 64, [torso])
    m.pack()
    return m


def _cloth(r, c, top_light=0.7, folds=True):
    t = top_light - c.vfrac * 0.45
    if c.face == "top":
        t = top_light + 0.1
    elif c.face == "bottom":
        t = 0.1
    if folds and c.is_side:
        f = math.sin((c.gp[0] + c.gp[2]) * 1.7 + vnoise(c.gp[1], 0, 0, 3, 5) * 2)
        t += f * 0.08
    t += (fbm(c.gp[0], c.gp[1], c.gp[2], s=4, seed=21) - 0.5) * 0.3
    if c.edge == 0 and c.face != "bottom":
        t -= 0.08
    return pick_dither(r, t, c.i, c.j)


def wraith_painters():
    def hood_main(c):
        if c.face == "north":
            if c.edge >= 1 and c.j >= 2:
                # interior escuro do capuz com os olhos
                d = (c.j - 2) / max(1, c.fh - 3)
                col = mix(hexc("#0a1222"), hexc("#020308"), 1 - d * 0.6)
                if c.j == 4 and c.i in (2, 5):
                    return FROST_EYE, FROST_EYE
                if c.j == 4 and c.i in (1, 6):
                    return hexc("#3fb7d8"), (60, 180, 220, 180)
                return col
            return pick(HOOD, 0.62 + h01(c.i, c.j) * 0.2)
        return _cloth(HOOD, c, 0.75)

    def hood(c):
        if c.face == "north" and c.j == c.fh - 1:
            return pick(MANTLE, 0.7 + h01(c.i) * 0.2)
        return _cloth(HOOD, c, 0.8)

    def mantle(c):
        t = 0.85 - c.vfrac * 0.55 + (fbm(c.gp[0] * 2, c.gp[1] * 2, c.gp[2] * 2, s=3, seed=9) - .5) * 0.5
        if c.face == "bottom":
            t = 0.25
        col = pick_dither(MANTLE, t, c.i, c.j)
        if c.is_side and c.j == c.fh - 1 and h01(c.i, 3) > 0.45:
            return pick(ICE, 0.8)
        return col

    def robe_up(c):
        col = _cloth(ROBE, c, 0.8)
        if c.face == "north" and c.i in (3, 4, 5) and c.j >= 4:
            # faixa bordada com runa
            if c.i == 4 and c.j in (5, 7):
                return hexc("#7fe8ff"), (110, 230, 255, 200)
            return hexc("#1b3150")
        return col

    def robe_low(c):
        col = _cloth(ROBE, c, 0.55)
        if c.face == "north" and c.i in (4, 5):
            if c.j in (1, 4):
                return hexc("#6fdcf8"), (100, 220, 255, 170)
            return hexc("#162a46")
        return col

    def tatter(c):
        t = 0.5 - c.vfrac * 0.5 + (h01(c.i, c.j, 3) - .5) * 0.15
        # pontas rasgadas: alguns texels do fim ficam transparentes
        if c.is_side and c.j >= c.fh - 2 and h01(c.i, c.j, int(c.gp[0] * 5), int(c.gp[2] * 5)) > 0.55:
            return (0, 0, 0, 0)
        if c.is_side and c.j == c.fh - 3 and h01(c.i, 77) > 0.7:
            return pick(ICE, 0.6)
        return pick_dither(ROBE, t, c.i, c.j)

    def sleeve(c):
        return _cloth(ROBE, c, 0.65)

    def cuff(c):
        if c.is_side and c.j >= c.fh - 1 and h01(c.i, c.j, 9) > 0.5:
            return (0, 0, 0, 0)
        if c.is_side and c.j == 0:
            return pick(MANTLE, 0.6)
        return _cloth(HOOD, c, 0.6)

    def skin(c):
        return pick(PALE, 0.65 - c.vfrac * 0.3 + h01(c.i, c.j) * 0.1)

    def claw(c):
        if c.vfrac > 0.6:
            return pick(ICE, 0.75)
        return hexc("#1b2433")

    def ice(c):
        t = 0.9 - c.vfrac * 0.4 + (h01(c.i, c.j, 5) - 0.5) * 0.2
        return pick(ICE, t)

    def ice_glow(c):
        t = 0.95 - c.vfrac * 0.35 + (h01(c.i, c.j, 8) - 0.5) * 0.2
        col = pick(ICE, t)
        return col, (col[0], col[1], col[2], 150)

    return {"hood_main": hood_main, "hood": hood, "mantle": mantle, "robe_up": robe_up,
            "robe_low": robe_low, "tatter": tatter, "sleeve": sleeve, "cuff": cuff, "skin": skin,
            "claw": claw, "ice": ice, "ice_glow": ice_glow}


WRAITH_ANIM = """
        float bob = Mth.sin(ageInTicks * 0.09F);
        this.torso.y += bob * 1.2F;
        this.hood.yRot += netHeadYaw * Mth.DEG_TO_RAD * 0.8F;
        this.hood.xRot += headPitch * Mth.DEG_TO_RAD * 0.8F;
        float reach = this.attackTime > 0 ? Mth.sin(this.attackTime * Mth.PI) : 0.0F;
        boolean casting = entity.isCasting();
        float lift = casting ? 0.9F : 0.0F;
        this.armR.xRot += Mth.sin(ageInTicks * 0.07F) * 0.12F - reach * 1.3F - lift;
        this.armL.xRot += Mth.sin(ageInTicks * 0.07F + 1.6F) * 0.12F - reach * 1.3F - lift;
        this.armR.zRot += casting ? -0.35F : 0.0F;
        this.armL.zRot += casting ? 0.35F : 0.0F;
        float sway = Mth.sin(ageInTicks * 0.12F) * 0.12F + limbSwingAmount * 0.5F;
        this.tatter0.xRot += sway;
        this.tatter1.xRot += sway * 1.2F;
        this.tatter2.xRot += sway * 0.9F;
        this.tatter3.xRot += -sway * 0.4F;
        this.tatter4.xRot += -sway * 0.5F;
        this.tatter5.xRot += -sway * 0.45F;
        this.tatter6.zRot += sway * 0.6F;
        this.tatter7.zRot += -sway * 0.6F;
        this.skirt.xRot += limbSwingAmount * 0.25F;
        this.orbit.yRot += ageInTicks * 0.06F;
        this.crystal0.y += Mth.sin(ageInTicks * 0.15F) * 1.5F;
        this.crystal1.y += Mth.sin(ageInTicks * 0.15F + 2.1F) * 1.5F;
        this.crystal2.y += Mth.sin(ageInTicks * 0.15F + 4.2F) * 1.5F;
"""
WRAITH_PARTS = ["torso", "hood", "arm_r", "arm_l", "skirt", "orbit", "crystal_0", "crystal_1", "crystal_2"] + \
               [f"tatter_{k}" for k in range(8)]

# ============================================================== VORTHRAX

SCALE = ramp("#0b0e13", "#12171f", "#1a212c", "#232d3b", "#2e3b4d", "#3c4d63", "#506680", "#6c87a5")
BELLY = ramp("#1f2631", "#2c3644", "#3d4a5b", "#536276", "#6d7f95", "#8b9db2")
HORN = ramp("#2d2a28", "#48433d", "#6b6358", "#948a7a", "#bdb29e", "#e0d7c2")
MEMB = ramp("#070f1a", "#0c1828", "#122339", "#1a304c", "#234063", "#2e527c")
VEIN = hexc("#74f2ff")
VEIN_CORE = hexc("#e2ffff")
DRAGON_EYE = hexc("#9ff8ff")


def dragon_model():
    body = Part("body", (0, 9, 0))
    body.cube(-6, -5, -10, 12, 10, 20, "scales")
    body.cube(-7, -4, -9, 14, 7, 7, "scales")
    body.cube(-5, 4, -9, 10, 2, 17, "belly")
    for k, z in enumerate((-8, -4, 0, 4, 8)):
        hgt = 3 if k % 2 == 0 else 2
        body.cube(-1, -5 - hgt, z, 2, hgt, 3, "spine")

    neck1 = body.add(Part("neck1", (0, -2, -10), (-0.5, 0, 0)))
    neck1.cube(-3.5, -3.5, -8, 7, 7, 9, "scales")
    neck1.cube(-1, -6, -6, 2, 3, 3, "spine")
    neck1.cube(-1, -6, -2, 2, 3, 3, "spine")
    neck1.cube(-3, 3, -7, 6, 1, 7, "belly")
    neck2 = neck1.add(Part("neck2", (0, 0, -7), (0.25, 0, 0)))
    neck2.cube(-3, -3, -7, 6, 6, 8, "scales")
    neck2.cube(-1, -5.5, -5, 2, 3, 3, "spine")
    neck2.cube(-2.5, 2.5, -6, 5, 1, 6, "belly")

    head = neck2.add(Part("head", (0, 0, -7), (0.3, 0, 0)))
    head.cube(-4, -4, -7, 8, 7, 8, "skull")
    head.cube(-3, -2.5, -14, 6, 4, 7, "scales")
    head.cube(-4.5, -5, -8, 3, 2, 5, "plate")
    head.cube(1.5, -5, -8, 3, 2, 5, "plate")
    head.cube(-2.5, -3.5, -13, 1, 1, 2, "horn")
    head.cube(1.5, -3.5, -13, 1, 1, 2, "horn")
    head.cube(-2.5, 1.5, -13.5, 5, 1, 6, "teeth")
    head.cube(-5.5, -2, -2, 1, 3, 4, "spine")
    head.cube(4.5, -2, -2, 1, 3, 4, "spine")
    for side, x, yr in (("l", 3, 0.25), ("r", -3, -0.25)):
        hp = head.add(Part(f"horn_{side}", (x, -4, -2), (0.6, yr, 0)))
        hp.cube(-1, -1, 0, 2, 2, 8, "horn")
        ht = hp.add(Part(f"horn_{side}_tip", (0, 0, 8), (0.35, 0, 0)))
        ht.cube(-0.5, -0.5, 0, 1, 1, 5, "horn")
    jaw = head.add(Part("jaw", (0, 3, -6), (0.12, 0, 0)))
    jaw.cube(-3, 0, -8, 6, 2, 8, "scales")
    jaw.cube(-2.5, -1, -7.5, 5, 1, 6, "teeth")

    tail1 = body.add(Part("tail1", (0, -2, 10), (-0.12, 0, 0)))
    tail1.cube(-4, -3, -1, 8, 7, 11, "scales")
    tail1.cube(-1, -5.5, 2, 2, 3, 3, "spine")
    tail1.cube(-1, -5.5, 6, 2, 3, 3, "spine")
    tail2 = tail1.add(Part("tail2", (0, 0.5, 10), (-0.08, 0, 0)))
    tail2.cube(-3, -2.5, 0, 6, 5, 11, "scales")
    tail2.cube(-1, -4.5, 4, 2, 2, 3, "spine")
    tail3 = tail2.add(Part("tail3", (0, 0.5, 11)))
    tail3.cube(-2, -1.5, 0, 4, 3, 11, "scales")
    tail3.cube(-0.5, -3, 4, 1, 2, 3, "spine")
    tail4 = tail3.add(Part("tail4", (0, 0, 11)))
    tail4.cube(-1, -1, 0, 2, 2, 10, "scales")
    tail4.cube(-0.5, -3.5, 5, 1, 7, 6, "fin")

    def leg(name, x, z, back):
        sgn = 1 if x > 0 else -1
        if back:
            p = body.add(Part(name, (x, 0, z)))
            p.cube(-2.5, -2, -3, 5, 9, 7, "scales")
            shin = p.add(Part(name + "_shin", (0, 7, 2)))
            shin.cube(-1.5, 0, -1.5, 3, 6, 3, "scales")
            foot = shin.add(Part(name + "_foot", (0, 6, 0)))
        else:
            p = body.add(Part(name, (x, 1, z)))
            p.cube(-2, -1, -2.5, 4, 8, 5, "scales")
            shin = p.add(Part(name + "_shin", (0, 7, 0.5)))
            shin.cube(-1.5, 0, -1.5, 3, 6, 3, "scales")
            foot = shin.add(Part(name + "_foot", (0, 6, 0)))
        foot.cube(-2, 0, -4, 4, 2, 5, "scales")
        for cx in (-2, -0.5, 1):
            foot.cube(cx, 1, -5, 1, 1, 1, "claw")
        foot.cube(-0.5, 1, 1, 1, 1, 1, "claw")
        return p

    leg("leg_fl", 6.5, -6, False)
    leg("leg_fr", -6.5, -6, False)
    leg("leg_bl", 6, 7, True)
    leg("leg_br", -6, 7, True)

    for side, sgn in (("l", 1), ("r", -1)):
        w = body.add(Part(f"wing_{side}", (6 * sgn, -4, -6)))
        x0 = 0 if sgn > 0 else -18
        w.cube(x0, -1, -1, 18, 2, 2, "bone")
        w.cube(x0, 0, 0, 18, 1, 20, "membrane", tag={"seg": 0, "sgn": sgn})
        w.cube((17 if sgn > 0 else -19), -2, -1.5, 2, 2, 2, "claw")
        t = w.add(Part(f"wing_{side}_tip", (18 * sgn, 0, 0)))
        x1 = 0 if sgn > 0 else -16
        t.cube(x1, -0.5, -0.5, 16, 1, 1, "bone")
        t.cube(x1, 0, 0, 16, 1, 17, "membrane", tag={"seg": 1, "sgn": sgn})
    m = Model("storm_dragon", 256, 128, [body])
    m.pack()
    return m


def _lightning(x, y, seed, width=0.012, density=0.55):
    """Veio de raio: linha fina onde o ruido cruza 0.5, so em algumas regioes."""
    mask = vnoise(x, y, 0, s=11, seed=seed + 99)
    if mask < density:
        return False
    v = fbm(x, y, 0, s=8, seed=seed, oct=2)
    return abs(v - 0.5) < width


def dragon_painters():
    def scales(c):
        # escamas em fileiras deslocadas
        row = c.j // 2
        off = (row % 2) * 2
        col_ = (c.i + off) // 4
        ci = (c.i + off) % 4
        cj = c.j % 2
        light = 0.68 - c.vfrac * 0.45
        if c.face == "top":
            light = 0.78
        elif c.face == "bottom":
            light = 0.18
        t = light + (fbm(c.gp[0], c.gp[1], c.gp[2], s=6, seed=3) - 0.5) * 0.35
        t += (h01(row, col_, int(c.gp[0]), 5) - 0.5) * 0.12
        if cj == 0 and ci in (1, 2):
            t += 0.13
        if cj == 1 and ci == 3:
            t -= 0.16
        if c.edge == 0 and c.face != "bottom":
            t -= 0.08
        col = pick(SCALE, t)
        if c.face in ("top", "east", "west") and _lightning(c.gp[0] * 0.6 + c.gp[2], c.gp[1] + c.gp[2] * 0.3, 13, 0.01, 0.62):
            return VEIN, VEIN
        return col

    def skull(c):
        col = scales(c)
        if c.face in ("east", "west") and c.j in (2, 3):
            front = c.i in (0, 1, 2) if c.face == "east" else c.i in (c.fw - 1, c.fw - 2, c.fw - 3)
            if front:
                e = DRAGON_EYE if c.j == 2 else hexc("#3bc8e6")
                return e, e
        if c.face == "north" and c.j == 0:
            return pick(SCALE, 0.55)
        return col

    def belly(c):
        t = 0.75 - c.vfrac * 0.3 + (h01(c.i, c.j, 2) - .5) * 0.1
        if c.face == "bottom":
            t = 0.55 + ((c.j % 3) == 0) * -0.25 + (h01(c.i, c.j) - .5) * 0.1
        if c.is_side and (c.i % 3) == 0:
            t -= 0.2
        return pick(BELLY, t)

    def spine(c):
        t = 0.9 - c.vfrac * 0.6
        col = pick(SCALE, 0.3 + t * 0.5)
        if c.is_side and c.j == 0 or c.face == "top":
            return VEIN, (130, 245, 255, 230)
        return col

    def plate(c):
        return pick(SCALE, 0.7 - c.vfrac * 0.3 + h01(c.i, c.j) * 0.1)

    def horn(c):
        cz = (c.lp[2] - c.cube.o[2]) / max(1, c.cube.s[2])
        t = 0.25 + cz * 0.7 + (h01(c.i, c.j, 4) - .5) * 0.1
        if c.face == "bottom":
            t -= 0.2
        return pick(HORN, t)

    def teeth(c):
        if c.is_side and c.i % 2 == 1:
            return hexc("#2a2420")
        return hexc("#e6dfcb") if c.face != "top" else hexc("#b9b19e")

    def claw(c):
        return pick(HORN, 0.15 + c.vfrac * 0.3)

    def bone(c):
        t = 0.6 - c.vfrac * 0.35 + (h01(c.i, c.j) - .5) * 0.12
        return pick(SCALE, t)

    def membrane(c):
        sgn = c.cube.tag.get("sgn", 1)
        seg = c.cube.tag.get("seg", 0)
        # coordenadas no plano da asa (x ao longo do osso, z para tras)
        ax = abs(c.gp[0]) - 6 + seg * 18
        az = c.gp[2] + 6
        if not (c.face in ("top", "bottom")):
            return pick(SCALE, 0.25)
        t = 0.55 - az * 0.012 + (fbm(ax, az, seg, s=5, seed=31) - 0.5) * 0.4
        if c.face == "bottom":
            t -= 0.15
        # "dedos" da asa: linhas que saem da borda dianteira
        fingers = (ax % 9) < 1.0 and az > 1
        if fingers:
            return pick(SCALE, 0.35), None
        # borda traseira recortada
        if c.j <= 1 and h01(c.i, seg, 41) > 0.5:
            return pick(MEMB, 0.05)
        col = pick_dither(MEMB, t, c.i, c.j)
        if _lightning(ax * 0.8, az * 0.8, 51 + seg, 0.016, 0.42):
            core = h01(c.i, c.j, 3) > 0.6
            g = VEIN_CORE if core else VEIN
            return g, g
        return col

    def fin(c):
        t = 0.9 - c.vfrac * 0.3
        col = pick(MEMB, t)
        if c.is_side and (c.j + c.i) % 3 == 0:
            return VEIN, VEIN
        return col, (40, 160, 200, 120)

    return {"scales": scales, "skull": skull, "belly": belly, "spine": spine, "plate": plate,
            "horn": horn, "teeth": teeth, "claw": claw, "bone": bone, "membrane": membrane, "fin": fin}


DRAGON_ANIM = """
        boolean flying = entity.isFlying();
        float t = ageInTicks;
        float yaw = netHeadYaw * Mth.DEG_TO_RAD;
        float pitch = headPitch * Mth.DEG_TO_RAD;
        this.neck1.yRot += yaw * 0.3F;
        this.neck2.yRot += yaw * 0.3F;
        this.head.yRot += yaw * 0.4F;
        this.neck1.xRot += pitch * 0.25F;
        this.head.xRot += pitch * 0.5F;
        this.body.y += Mth.sin(t * 0.08F) * 0.35F;
        this.neck1.xRot += Mth.sin(t * 0.08F) * 0.03F;
        float roar = entity.getRoarAmount(ageInTicks - entity.tickCount);
        this.jaw.xRot += 0.05F + this.attackTime * 1.1F + roar * 0.8F;
        this.head.xRot -= roar * 0.35F;
        float sw = Mth.sin(t * 0.07F);
        this.tail1.yRot += sw * 0.10F;
        this.tail2.yRot += Mth.sin(t * 0.07F - 0.6F) * 0.14F;
        this.tail3.yRot += Mth.sin(t * 0.07F - 1.2F) * 0.18F;
        this.tail4.yRot += Mth.sin(t * 0.07F - 1.8F) * 0.22F;
        if (flying) {
            float flap = Mth.sin(t * 0.3F);
            float flap2 = Mth.sin(t * 0.3F - 0.9F);
            this.wingL.zRot += -flap * 0.6F;
            this.wingR.zRot += flap * 0.6F;
            this.wingLTip.zRot += -flap2 * 0.5F;
            this.wingRTip.zRot += flap2 * 0.5F;
            this.body.y += flap * 0.8F;
            this.legFl.xRot += 0.9F;
            this.legFr.xRot += 0.9F;
            this.legBl.xRot += 1.1F;
            this.legBr.xRot += 1.1F;
            this.legBlShin.xRot += 0.6F;
            this.legBrShin.xRot += 0.6F;
            this.tail1.xRot += 0.12F;
            this.tail2.xRot += 0.05F;
        } else {
            this.wingL.xRot += -1.5708F;
            this.wingL.yRot += -1.5708F;
            this.wingL.zRot += -0.5F;
            this.wingLTip.zRot += 2.9F;
            this.wingR.xRot += -1.5708F;
            this.wingR.yRot += 1.5708F;
            this.wingR.zRot += 0.5F;
            this.wingRTip.zRot += -2.9F;
            float swing = limbSwing * 0.45F;
            float amt = Math.min(limbSwingAmount, 1.0F);
            this.legFl.xRot += Mth.cos(swing) * 0.6F * amt;
            this.legBr.xRot += Mth.cos(swing) * 0.6F * amt;
            this.legFr.xRot += Mth.cos(swing + Mth.PI) * 0.6F * amt;
            this.legBl.xRot += Mth.cos(swing + Mth.PI) * 0.6F * amt;
            this.wingL.zRot += Mth.sin(t * 0.05F) * 0.04F;
            this.wingR.zRot -= Mth.sin(t * 0.05F) * 0.04F;
        }
"""
DRAGON_PARTS = ["body", "neck1", "neck2", "head", "jaw", "tail1", "tail2", "tail3", "tail4",
                "leg_fl", "leg_fr", "leg_bl", "leg_br", "leg_bl_shin", "leg_br_shin",
                "wing_l", "wing_r", "wing_l_tip", "wing_r_tip"]

DRAGON_POSE_GROUND = {"wing_l": (-1.5708, -1.5708, -0.5), "wing_l_tip": (0, 0, 2.9),
                      "wing_r": (-1.5708, 1.5708, 0.5), "wing_r_tip": (0, 0, -2.9)}
DRAGON_POSE_FLY = {"wing_l": (0, 0, -0.35), "wing_r": (0, 0, 0.35), "wing_l_tip": (0, 0, -0.2),
                   "wing_r_tip": (0, 0, 0.2), "leg_fl": (0.9, 0, 0), "leg_fr": (0.9, 0, 0),
                   "leg_bl": (1.1, 0, 0), "leg_br": (1.1, 0, 0), "tail1": (0.12, 0, 0)}
