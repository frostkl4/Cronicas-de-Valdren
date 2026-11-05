"""Criaturas v2: Lobo Sombrio (anatomia de lobo com pernas digitígradas e cristais de sombra)
e Espectro Gélido (capuz alto com ponta enrolada, mangas largas, saia em camadas)."""
import math

from boxmodel import Part, Model
from pix import ramp, hexc, h01
from stylepaint import M
from v2_common import VOID

# ============================================================== LOBO SOMBRIO
FUR = ramp("#08060c", "#0f0b16", "#171120", "#20182c", "#2b203a", "#382a4b", "#48375f", "#5c4878")
MANE = ramp("#130f1b", "#1d1729", "#282038", "#352b4a", "#45395f", "#584a78", "#6e5e93")
CRYS = ramp("#1a0f2a", "#2c1a48", "#47296f", "#6b3ea0", "#9a63d6", "#c99cff", "#efe0ff")
WCLAW = ramp("#5e5868", "#8c8696", "#b8b3c0", "#e2dee8")
INNER = ramp("#2a0e1e", "#3e1530", "#561e40")
EYEW = hexc("#ff7ae8")
EYEW2 = hexc("#ffe0fa")

WOLF_MATS = {
    "fur": M(FUR, "fur", 0.55, grad=0.25), "fur_d": M(FUR, "fur", 0.38, grad=0.25), "mane": M(MANE, "fur", 0.58),
    "crys": M(CRYS, "along", 0.5, axis="-y", glow=True), "crys_dim": M(CRYS, "along", 0.35, axis="-y"),
    "claw": M(WCLAW, "along", 0.5, axis="y"), "inner": M(INNER, "flat", 0.5), "snout": M(FUR, "fur", 0.3),
    "teeth": M(ramp("#8e8676", "#bdb4a0", "#e2dac6", "#f7f1e2"), "flat", 0.7, bevel=False),
    "smoke": M(MANE, "fur", 0.45, noise=0.1),
}


def wolf_model():
    body = Part("body", (0, 11, 0))
    body.cube(-4, -5, -8, 8, 9, 8, "fur", tag={"rows": [(7, 9, "fur_d")]})
    body.cube(-3.5, -4, 0, 7, 7, 6, "fur", tag={"rows": [(5, 7, "fur_d")]})
    body.cube(-3.5, -4.5, 6, 7, 6, 5, "fur")
    for (z, h) in ((-6, 3), (-3, 4), (0.5, 3), (4, 2)):
        body.cube(-1, -5 - h + (1 if z > 0 else 0), z, 2, h, 2, "crys")
    body.cube(-3, -6.5, -4, 1, 2, 2, "crys_dim")
    body.cube(2, -6.5, -4, 1, 2, 2, "crys_dim")
    mane = body.add(Part("mane", (0, -5, -6)))
    mane.cube(-5, -1, -3, 10, 4, 7, "mane", tag={"cut": "rag"})
    mane.cube(-4.5, 2, -3.5, 9, 5, 2, "mane", tag={"cut": "rag2"})
    mane.cube(-5.5, 0, -1, 1, 4, 5, "mane", tag={"cut": "rag"})
    mane.cube(4.5, 0, -1, 1, 4, 5, "mane", tag={"cut": "rag"})
    neck = body.add(Part("neck", (0, -2, -8), (-0.2, 0, 0)))
    neck.cube(-2.5, -2.5, -4, 5, 5, 5, "mane")
    head = neck.add(Part("head", (0, -1, -4), (0.2, 0, 0)))
    head.cube(-3.5, -3.5, -5, 7, 6, 6, "fur", tag={"grid": {"north": [
        ".......", "ddddddd", "dEe.eEd", ".......", ".......", "......."]},
        "pal": {"d": hexc("#050308"), "E": EYEW2, "e": EYEW}, "glowchars": "Ee"})
    head.cube(-2, -1, -9.5, 4, 3, 5, "snout", tag={"rows": [(0, 1, "fur")]})
    head.cube(-1, -1.4, -10, 2, 1, 1, "inner")
    head.cube(-3.5, -4.2, -5.5, 7, 1, 2, "fur_d")
    head.cube(-4.5, 0, -3, 1, 3, 3, "mane")
    head.cube(3.5, 0, -3, 1, 3, 3, "mane")
    head.cube(-1.6, 2, -9.2, 1, 1, 1, "teeth")
    head.cube(0.6, 2, -9.2, 1, 1, 1, "teeth")
    for side, sx in (("r", -1), ("l", 1)):
        ear = head.add(Part(f"ear_{side}", (2.2 * sx, -3.5, -1.5), (-0.2, 0, 0.2 * sx)))
        ear.cube(-1, -4, -0.5, 2, 4, 1, "fur", tag={"grid": {"north": ["..", "ii", "ii", "ii"]}, "pal": {"i": "inner"}})
        ear.cube(-0.5, -5, -0.5, 1, 1, 1, "crys")
    jaw = head.add(Part("jaw", (0, 1.5, -5)))
    jaw.cube(-1.5, 0, -4.5, 3, 1, 5, "snout")
    jaw.cube(-1.4, -1, -4.2, 1, 1, 1, "teeth")
    jaw.cube(0.4, -1, -4.2, 1, 1, 1, "teeth")

    def paw(p, y, zf):
        p.cube(-1.5, y, zf, 3, 1, 3, "fur_d")
        for x in (-1.5, -0.5, 0.5):
            p.cube(x, y, zf - 0.8, 1, 1, 1, "claw")

    for side, sx in (("r", -1), ("l", 1)):
        fl = body.add(Part(f"leg_f{side}", (2.8 * sx, 2, -5)))
        fl.cube(-1.5, -1, -1.5, 3, 6, 3, "fur")
        flo = fl.add(Part(f"leg_f{side}_low", (0, 5, 0)))
        flo.cube(-1, 0, -1, 2, 5, 2, "fur_d")
        paw(flo, 5, -1.5)
        bl = body.add(Part(f"leg_b{side}", (2.6 * sx, 1, 8)))
        bl.cube(-1.5, -1.5, -2.5, 3, 6, 5, "fur")
        hock = bl.add(Part(f"leg_b{side}_hock", (0, 4, 1.5), (0.6, 0, 0)))
        hock.cube(-1, 0, -1, 2, 5, 2, "fur")
        meta = hock.add(Part(f"leg_b{side}_meta", (0, 4.5, 0), (-0.6, 0, 0)))
        meta.cube(-1, 0, -1, 2, 3, 2, "fur_d")
        paw(meta, 3, -1.5)

    tail = body.add(Part("tail", (0, -3, 11), (-0.45, 0, 0)))
    tail.cube(-1.5, -1.5, 0, 3, 3, 5, "mane")
    t2 = tail.add(Part("tail2", (0, 0, 5), (-0.15, 0, 0)))
    t2.cube(-1.5, -1.5, 0, 3, 3, 5, "mane")
    t3 = t2.add(Part("tail3", (0, 0, 5), (0.1, 0, 0)))
    t3.cube(-1, -1, 0, 2, 2, 4, "smoke", tag={"cut": "rag2"})
    t3.cube(-0.5, -0.5, 3, 1, 1, 2, "crys")
    m = Model("shadow_wolf", 128, 64, [body])
    m.pack()
    return m


WOLF_ANIM = """
        float partial = ageInTicks - entity.tickCount;
        float hurt = entity.hurtTime > 0 ? (entity.hurtTime - partial) / 10.0F : 0.0F;
        float amt = Math.min(limbSwingAmount, 1.0F);
        float run = Math.max(0.0F, Math.min(1.0F, (limbSwingAmount - 0.4F) * 2.0F));
        float swing = limbSwing * 0.6662F;
        float front = Mth.cos(swing) * 1.1F * amt;
        float back = Mth.cos(swing + Mth.PI * (1.0F - run * 0.6F)) * 1.1F * amt;
        this.legFl.xRot += front;
        this.legFr.xRot += Mth.cos(swing + Mth.PI * (1.0F - run * 0.85F)) * 1.1F * amt;
        this.legBl.xRot += back;
        this.legBr.xRot += Mth.cos(swing + Mth.PI * (0.15F + run * 0.2F)) * 1.1F * amt;
        this.legFlLow.xRot += Math.max(0.0F, front) * 0.6F;
        this.legFrLow.xRot += Math.max(0.0F, -front) * 0.6F;
        this.body.xRot += Mth.sin(swing * 2.0F) * 0.05F * run;
        this.body.y += Mth.sin(ageInTicks * 0.1F) * 0.25F + Math.abs(Mth.sin(swing)) * -0.8F * run;
        if (!entity.onGround()) {
            this.legFl.xRot -= 0.9F;
            this.legFr.xRot -= 0.9F;
            this.legBl.xRot += 0.9F;
            this.legBr.xRot += 0.9F;
            this.tail.xRot += 0.4F;
        }
        this.neck.yRot += netHeadYaw * Mth.DEG_TO_RAD * 0.4F;
        this.head.yRot += netHeadYaw * Mth.DEG_TO_RAD * 0.6F;
        this.head.xRot += headPitch * Mth.DEG_TO_RAD - hurt * 0.5F;
        this.jaw.xRot += 0.05F + this.attackTime * 1.0F + Mth.sin(ageInTicks * 0.09F) * 0.04F + hurt * 0.4F;
        this.earR.zRot += Mth.sin(ageInTicks * 0.13F) > 0.95F ? -0.3F : 0.0F;
        this.earL.zRot += Mth.sin(ageInTicks * 0.11F + 2.0F) > 0.95F ? 0.3F : 0.0F;
        this.earR.xRot -= run * 0.5F;
        this.earL.xRot -= run * 0.5F;
        this.mane.xRot += Mth.sin(ageInTicks * 0.1F) * 0.03F;
        this.tail.xRot += amt * 0.45F;
        this.tail.yRot += Mth.sin(ageInTicks * 0.15F) * 0.2F * (1.0F - amt);
        this.tail2.yRot += Mth.sin(ageInTicks * 0.15F - 0.7F) * 0.25F;
        this.tail3.yRot += Mth.sin(ageInTicks * 0.15F - 1.4F) * 0.3F;
        this.tail2.xRot += amt * 0.2F;
"""
WOLF_PARTS = ["body", "mane", "neck", "head", "jaw", "ear_r", "ear_l", "leg_fr", "leg_fl", "leg_fr_low", "leg_fl_low",
              "leg_br", "leg_bl", "tail", "tail2", "tail3"]

# ============================================================ ESPECTRO GÉLIDO
HOOD = ramp("#050913", "#0a1120", "#101a31", "#172443", "#203158", "#2b3f70", "#39518b")
ROBE = ramp("#03050b", "#070b17", "#0d1426", "#151f38", "#1f2d4e", "#2a3c66")
FROST = ramp("#284766", "#3c6489", "#5888ad", "#80b0cf", "#addaee", "#dcf3ff", "#ffffff")
ICE = ramp("#1f6a9c", "#3592c7", "#5fbde6", "#93dcf7", "#c8f1ff", "#f2fdff")
SPECT = ramp("#2d6c8c", "#4796b8", "#6fc4e0", "#a6e8f8", "#e0faff")
EYEF = hexc("#a6fbff")

WRAITH_MATS = {
    "hood": M(HOOD, "cloth", 0.58), "robe": M(ROBE, "cloth", 0.6, grad=0.3), "robe_l": M(HOOD, "cloth", 0.5),
    "frost": M(FROST, "fur", 0.6), "ice": M(ICE, "along", 0.55, axis="-y", glow=True),
    "ice_d": M(ICE, "metal", 0.45), "void": M(VOID, "flat", 0.4, bevel=False),
    "spect": M(SPECT, "along", 0.55, axis="y", glow=True), "silver": M(FROST, "trim", 0.7),
}


def wraith_model():
    torso = Part("torso", (0, 6, 0))
    torso.cube(-4, -10, -2.5, 8, 9, 5, "robe_l", tag={"cols": [(3, 5, "frost", ("north",))]})
    torso.cube(-6, -11, -3.5, 12, 3, 7, "frost", tag={"cut": "rag"})
    for k, (x, z, h) in enumerate(((-5.5, -2, 3), (-4, 1.5, 2), (-6, 0.5, 2), (4.5, -2, 3), (3, 1.5, 2), (5, 0.5, 2))):
        torso.cube(x, -11 - h, z, 1, h, 1, "ice")
    hood = torso.add(Part("hood", (0, -11, 0)))
    hood.cube(-3.5, -7, -3.5, 7, 7, 7, "void", tag={"grid": {"north": [
        ".......", ".......", ".......", ".E...E.", ".e...e.", ".......", "......."]},
        "pal": {"E": hexc("#e8feff"), "e": EYEF}, "glowchars": "Ee"})
    hood.cube(-4.5, -8, -4.5, 9, 8, 9, "hood", tag={"cut": "open_front", "rows": [(7, 8, "silver")]})
    hood.cube(-5, -8.5, -5, 10, 2, 3, "hood", tag={"rows": [(1, 2, "silver")]})
    for x in (-4, -2, 0, 2, 4):
        hood.cube(x - 0.5, -10.5 + abs(x) * 0.25, -5, 1, 2, 1, "ice")
    t1 = hood.add(Part("hood_tip", (0, -8, 1), (-0.2, 0, 0)))
    t1.cube(-3, -3, -3, 6, 3, 6, "hood")
    t2 = t1.add(Part("hood_tip2", (0, -3, 0.5), (-0.55, 0, 0)))
    t2.cube(-2, -3, -2, 4, 3, 4, "hood")
    t3 = t2.add(Part("hood_tip3", (0, -3, 0.3), (-0.75, 0, 0)))
    t3.cube(-1.5, -3, -1.5, 3, 3, 3, "hood", tag={"rows": [(2, 3, "silver")]})
    t4 = t3.add(Part("hood_tip4", (0, -3, 0), (-0.95, 0, 0)))
    t4.cube(-1, -2, -1, 2, 2, 2, "hood")
    t4.cube(-0.5, -3.5, -0.5, 1, 2, 1, "ice")

    skirt = torso.add(Part("skirt", (0, -1, 0)))
    skirt.cube(-5, 0, -3.5, 10, 5, 7, "robe", tag={"cols": [(4, 6, "frost", ("north",))]})
    tier = skirt.add(Part("skirt2", (0, 5, 0)))
    tier.cube(-5.5, 0, -4, 11, 4, 8, "robe", tag={"rows": [(3, 4, "silver")]})
    for k in range(8):
        a = (k + 0.5) * 2 * math.pi / 8
        x, z = math.cos(a) * 4.2, math.sin(a) * 3.3
        tp = tier.add(Part(f"tatter_{k}", (x, 4, z)))
        ln = 5 + (k * 3) % 4
        tp.cube(-1.5, 0, -0.5, 3, ln, 1, "robe", tag={"cut": "rag2"})
        if k % 2 == 0:
            tp.cube(-0.5, ln - 1, -0.8, 1, 2, 1, "ice")

    for side, sx in (("r", -1), ("l", 1)):
        arm = torso.add(Part(f"arm_{side}", (6.5 * sx, -9, 0), (-0.3, 0, 0.2 * sx)))
        arm.cube(-1.5, -1, -1.5, 3, 5, 3, "robe_l")
        bell = arm.add(Part(f"sleeve_{side}", (0, 3, 0)))
        bell.cube(-3, 0, -3, 6, 6, 6, "hood", tag={"rows": [(5, 6, "silver")], "cut": "rag"})
        bell.cube(-2, 0.5, -2, 4, 4, 4, "void")
        hand = arm.add(Part(f"hand_{side}", (0, 8, 0)))
        for x, z in ((-1.2, -1.2), (0.2, -1.2), (-0.5, 0.2)):
            hand.cube(x, 0, z, 1, 4, 1, "spect")

    orbit = torso.add(Part("orbit", (0, -4, 0)))
    for k in range(3):
        a = k * 2 * math.pi / 3
        cr = orbit.add(Part(f"crystal_{k}", (math.cos(a) * 10, 0, math.sin(a) * 10), (0.3, 0, 0.25)))
        cr.cube(-1, -2.5, -1, 2, 5, 2, "ice")
        cr.cube(-0.5, -4, -0.5, 1, 2, 1, "ice")
        cr.cube(-0.5, 2.5, -0.5, 1, 1, 1, "ice")
    m = Model("frost_wraith", 128, 128, [torso])
    m.pack()
    return m


WRAITH_ANIM = """
        float partial = ageInTicks - entity.tickCount;
        float hurt = entity.hurtTime > 0 ? (entity.hurtTime - partial) / 10.0F : 0.0F;
        float bob = Mth.sin(ageInTicks * 0.09F);
        float walk = Math.min(limbSwingAmount, 1.0F);
        boolean casting = entity.isCasting();
        this.torso.y += bob * 1.2F;
        this.torso.xRot += walk * 0.15F - hurt * 0.2F;
        this.hood.yRot += netHeadYaw * Mth.DEG_TO_RAD * 0.8F;
        this.hood.xRot += headPitch * Mth.DEG_TO_RAD * 0.8F - hurt * 0.4F;
        this.hoodTip.xRot += Mth.sin(ageInTicks * 0.07F) * 0.05F - walk * 0.15F;
        this.hoodTip2.xRot += Mth.sin(ageInTicks * 0.07F - 0.6F) * 0.08F - walk * 0.15F;
        this.hoodTip3.xRot += Mth.sin(ageInTicks * 0.07F - 1.2F) * 0.1F - walk * 0.15F;
        this.hoodTip4.xRot += Mth.sin(ageInTicks * 0.07F - 1.8F) * 0.12F;
        float reach = this.attackTime > 0.0F ? Mth.sin(this.attackTime * Mth.PI) : 0.0F;
        float lift = casting ? 1.0F + Mth.sin(ageInTicks * 0.3F) * 0.1F : 0.0F;
        this.armR.xRot += Mth.sin(ageInTicks * 0.07F) * 0.1F - reach * 1.2F - lift;
        this.armL.xRot += Mth.sin(ageInTicks * 0.07F + 1.6F) * 0.1F - reach * 1.2F - lift;
        this.armR.zRot += casting ? -0.35F : 0.0F;
        this.armL.zRot += casting ? 0.35F : 0.0F;
        this.sleeveR.xRot += walk * 0.3F + Mth.sin(ageInTicks * 0.1F) * 0.05F;
        this.sleeveL.xRot += walk * 0.3F + Mth.sin(ageInTicks * 0.1F + 1.0F) * 0.05F;
        this.skirt.xRot += walk * 0.2F;
        this.skirt2.xRot += walk * 0.2F + Mth.sin(ageInTicks * 0.08F) * 0.03F;
        float sway = Mth.sin(ageInTicks * 0.12F) * 0.12F;
        this.tatter0.xRot += sway + walk * 0.4F;
        this.tatter1.xRot += -sway * 0.8F + walk * 0.3F;
        this.tatter2.zRot += sway * 0.9F;
        this.tatter3.xRot += sway * 0.7F + walk * 0.4F;
        this.tatter4.xRot += -sway + walk * 0.3F;
        this.tatter5.zRot += -sway * 0.9F;
        this.tatter6.xRot += sway * 0.8F + walk * 0.4F;
        this.tatter7.zRot += sway * 0.7F;
        float spin = casting ? 0.15F : 0.06F;
        this.orbit.yRot += ageInTicks * spin;
        this.crystal0.y += Mth.sin(ageInTicks * 0.15F) * 1.5F;
        this.crystal1.y += Mth.sin(ageInTicks * 0.15F + 2.1F) * 1.5F;
        this.crystal2.y += Mth.sin(ageInTicks * 0.15F + 4.2F) * 1.5F;
"""
WRAITH_PARTS = ["torso", "hood", "hood_tip", "hood_tip2", "hood_tip3", "hood_tip4", "skirt", "skirt2", "arm_r", "arm_l",
                "sleeve_r", "sleeve_l", "orbit", "crystal_0", "crystal_1", "crystal_2"] + [f"tatter_{k}" for k in range(8)]
