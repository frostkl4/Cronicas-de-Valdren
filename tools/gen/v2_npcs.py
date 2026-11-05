"""NPCs v2 (Bram, Iara, Orvyn, Ravena) e o Saqueador Carmesim: modelos humanoides com
roupas, cabelo e acessórios em 3D. Todos usam os 7 ossos do HumanoidModel com peças filhas."""
from boxmodel import Part, Model
from pix import ramp, hexc
from stylepaint import M
from v2_common import (skeleton, mirror_part, lr, humanoid_roots, GOLD, SILVER, LEATHER, LEATHER_D, WOOD, IRON,
                       VOID, BONE, SKIN_TAN, SKIN_BROWN, SKIN_PALE)

W = hexc("#f4f0e8")
EYE_BROWN = hexc("#3a2214")
EYE_GREEN = hexc("#2f5a2a")
EYE_BLUE = hexc("#2c4f7a")


def copy_side(src, dst):
    for c in src.cubes:
        dst.cube(-(c.o[0] + c.s[0]), c.o[1], c.o[2], *c.s, c.mat, inflate=c.inflate, tag=dict(c.tag))


def finish(name, parts, w=128, h=128):
    m = Model(name, w, h, humanoid_roots(*parts))
    m.pack()
    return m


# ===================================================================== BRAM
BEARD = ramp("#2a1008", "#3f190d", "#5a2614", "#76331b", "#934324", "#ad5730", "#c46d40")
SHIRT = ramp("#22080a", "#36100f", "#4e1915", "#68231c", "#822f24", "#9b3d2e", "#b44e3a")
APRON = ramp("#1e1209", "#2c1b0e", "#3d2614", "#51331b", "#664223", "#7c532d")
PANTS = ramp("#121014", "#1c1920", "#27232c", "#332e39", "#403a47")
BRASS = ramp("#3d2a10", "#64461a", "#8c6626", "#b38936", "#d9ae4f", "#f3d27f")
GLASS = hexc("#9fd8e8")

BRAM_MATS = {
    "skin": M(SKIN_TAN, "skin", 0.55, grad=0.12), "beard": M(BEARD, "hair", 0.55), "shirt": M(SHIRT, "cloth", 0.55),
    "shirt_d": M(SHIRT, "cloth", 0.35), "apron": M(APRON, "leather", 0.55), "leather": M(LEATHER, "leather", 0.5),
    "glove": M(LEATHER_D, "leather", 0.55), "pants": M(PANTS, "cloth", 0.55), "boot": M(LEATHER_D, "leather", 0.45),
    "iron": M(IRON, "metal", 0.55), "wood": M(WOOD, "wood", 0.55), "brass": M(BRASS, "trim", 0.55),
    "gold": M(GOLD, "trim", 0.6),
}


def bram_model():
    head, hat, body, ra, la, rl, ll = skeleton()
    head.cube(-4, -8, -4, 8, 8, 8, "skin", tag={"grid": {"north": [
        "........", "........", "........", "bb....bb", ".wE..Ew.", "...nn...", "........", "........"]},
        "pal": {"b": "beard", "w": W, "E": EYE_BROWN, "n": hexc("#8a5438")}})
    head.cube(-4.6, -5, -2, 1, 4, 5, "beard")
    head.cube(3.6, -5, -2, 1, 4, 5, "beard")
    head.cube(-4, -4, 3.6, 8, 3, 1, "beard", tag={"cut": "rag"})
    head.cube(-4.5, -7, -4.5, 9, 2, 9, "leather")
    for x in (-3.5, 0.5):
        head.cube(x, -7.5, -5, 3, 3, 1, "brass", tag={"grid": {"north": ["...", ".g.", "..."]}, "pal": {"g": GLASS}})
    beard = head.add(Part("beard", (0, -2, -4)))
    beard.cube(-4, -1, -1.5, 8, 5, 2, "beard", tag={"grid": {"north": [
        "bBBbbBBb", "bbbmmbbb", "bbbbbbbb", "Bbbbbbbb", " bbbbbb "]},
        "pal": {"b": "beard", "B": hexc("#5a2614"), "m": hexc("#2a0e08")}})
    braid = beard.add(Part("beard_braid", (0, 4, -0.5)))
    braid.cube(-1, 0, -1, 2, 4, 2, "beard")
    braid.cube(-1.5, 2, -1.5, 3, 1, 3, "iron")

    body.cube(-5, 0, -3, 10, 12, 6, "shirt", tag={"grid": {
        "north": ["...ssss...", "....ss....", "..........", "..........", "..........", "..........",
                  "..........", "..........", "..........", "..........", "..........", ".........."],
        "south": ["..........", ".l......l.", "..l....l..", "...l..l...", "....ll....", "....ll....",
                  "...l..l...", "..........", "..........", "..........", "..........", ".........."]},
        "pal": {"s": "skin", "l": "apron"}})
    body.cube(-3.5, -0.5, -3.4, 1, 2, 1, "apron")
    body.cube(2.5, -0.5, -3.4, 1, 2, 1, "apron")
    apron = body.add(Part("apron", (0, 1, -3.2)))
    apron.cube(-4, 0, -0.5, 8, 15, 1, "apron", tag={"grid": {"north": [
        "........", "........", "...ii...", "..iiii..", "...ii...", "...ww...", "...ww...", "pppppppp",
        "p......p", "p......p", "pppppppp", "........", "........", "........", "........"]},
        "pal": {"i": "iron", "w": "wood", "p": "leather"}, "cut": "rag"})
    body.cube(-5.5, 8, -3.5, 11, 2, 7, "leather", tag={"grid": {"north": [
        "....ggg....", "....g.g...."]}, "pal": {"g": "brass"}})
    body.cube(4.5, 9, -2, 2, 3, 3, "leather", tag={"rows": [(0, 1, "brass")]})
    ham = body.add(Part("hammer", (-5.5, 9, 0), (0, 0, 0.15)))
    ham.cube(-0.5, 0, -0.5, 1, 7, 1, "wood")
    ham.cube(-1.5, 6, -2, 3, 2, 4, "iron")

    ra.cube(-4, -2, -2.5, 5, 4, 5, "shirt", tag={"rows": [(3, 4, "shirt_d")]})
    ra.cube(-3.5, 2, -2, 4, 5, 4, "skin")
    ra.cube(-4, 7, -2.5, 5, 4, 5, "glove", tag={"rows": [(0, 1, "leather")]})
    copy_side(ra, la)
    rl.cube(-2.5, 0, -2.5, 5, 8, 5, "pants")
    rl.cube(-3, 8, -3.5, 6, 4, 6, "boot", tag={"rows": [(0, 1, "leather")]})
    rl.cube(-2.5, 10, -4.2, 5, 2, 1, "iron")
    copy_side(rl, ll)
    return finish("bram", (head, hat, body, ra, la, rl, ll))


BRAM_ANIM = """
        float walk = Math.min(limbSwingAmount, 1.0F);
        this.apron.xRot += Math.min(0.0F, Math.min(this.rightLeg.xRot, this.leftLeg.xRot)) * 0.6F;
        this.beard.xRot += -this.head.xRot * 0.3F + Mth.sin(ageInTicks * 0.08F) * 0.02F;
        this.beardBraid.xRot += Mth.sin(ageInTicks * 0.09F + 0.5F) * 0.08F + walk * 0.25F;
        this.hammer.zRot += Mth.cos(limbSwing * 0.6662F) * 0.25F * walk;
"""
BRAM_PARTS = ["beard", "beard_braid", "apron", "hammer"]

# ===================================================================== IARA
HAIR_G = ramp("#18151a", "#262228", "#38323a", "#4e4850", "#77727a", "#a7a3aa", "#d2cfd4")
SCARF = ramp("#0f2a19", "#163a23", "#1f4e2f", "#2a653c", "#377d4b", "#48965c", "#5fae70")
DRESS = ramp("#22190f", "#302316", "#40301e", "#523f28", "#664f33", "#7c6140")
SHAWL = ramp("#4a1a0e", "#6a2814", "#8a381d", "#a84b27", "#c46335", "#d97e48")
CREAM = ramp("#7d7464", "#9a907d", "#b6ad97", "#cfc7b0", "#e5dec8", "#f4efdd")
HERB = ramp("#1d3b16", "#2b5520", "#3d722c", "#55913c", "#73b052")

IARA_MATS = {
    "skin": M(SKIN_BROWN, "skin", 0.55, grad=0.12), "hair": M(HAIR_G, "hair", 0.45), "hair_l": M(HAIR_G, "hair", 0.62), "scarf": M(SCARF, "cloth", 0.55),
    "dress": M(DRESS, "cloth", 0.55), "shawl": M(SHAWL, "fur", 0.55), "cream": M(CREAM, "cloth", 0.55),
    "leather": M(LEATHER, "leather", 0.55), "herb": M(HERB, "fur", 0.55), "gold": M(GOLD, "trim", 0.6),
    "boot": M(LEATHER_D, "leather", 0.5),
}


def iara_model():
    head, hat, body, ra, la, rl, ll = skeleton()
    head.cube(-4, -8, -4, 8, 8, 8, "skin", tag={"grid": {"north": [
        "........", "........", "hh....hh", "h.bb.bbh", "h.wEwE.h", ".r....r.", "...ss...", "...mm..."]},
        "pal": {"h": "hair", "b": hexc("#2e2a2c"), "w": W, "E": EYE_BROWN, "r": hexc("#c27a5c"),
                "s": hexc("#7a4a32"), "m": hexc("#8a3a30")}})
    head.cube(-4.4, -5, -3.5, 1, 3, 3, "hair_l")
    head.cube(3.4, -5, -3.5, 1, 3, 3, "hair_l")
    head.cube(-4.5, -8.6, -4.5, 9, 4, 9, "scarf", tag={"rows": [(3, 4, "scarf")]})
    head.cube(-3.5, -9.2, -3.5, 7, 1, 7, "scarf")
    head.cube(3.4, -8.4, -3.5, 2, 2, 1, "gold", tag={"grid": {"north": ["yY", "Yy"]},
                                                     "pal": {"y": hexc("#f2c84a"), "Y": hexc("#d99a2a")}})
    knot = head.add(Part("scarf_knot", (0, -6, 4.6)))
    knot.cube(-1.5, -1, -0.5, 3, 2, 2, "scarf")
    tail = knot.add(Part("scarf_tail", (0, 1, 0.5), (0.15, 0, 0)))
    tail.cube(-2, 0, 0, 2, 5, 1, "scarf", tag={"cut": "rag"})
    tail.cube(0.2, 0, 0, 2, 4, 1, "scarf", tag={"cut": "rag"})
    head.cube(-2, -3, 3.8, 4, 3, 1, "hair")

    body.cube(-4, 0, -2.5, 8, 12, 5, "dress")
    shawl = body.add(Part("shawl", (0, 0, 0)))
    shawl.cube(-5, -0.5, -3, 10, 5, 6, "shawl", tag={"rows": [(4, 5, "shawl")], "cut": "rag"})
    shawl.cube(-2, 4.5, -3.2, 4, 2, 1, "shawl")
    shawl.cube(-0.5, 4, -3.6, 1, 1, 1, "gold")
    apron = body.add(Part("apron", (0, 7, -2.8)))
    apron.cube(-3.5, 0, -0.5, 7, 10, 1, "cream", tag={"cut": "rag"})
    apron.cube(-2.5, 3, -1.3, 5, 3, 1, "cream", tag={"rows": [(0, 1, "leather")]})
    apron.cube(-2, 1.5, -1.4, 1, 2, 1, "herb")
    apron.cube(0.5, 1, -1.4, 1, 3, 1, "herb")
    apron.cube(1.6, 2, -1.4, 1, 1, 1, "gold")
    skirt = body.add(Part("skirt", (0, 10, 0)))
    skirt.cube(-5, 0, -3, 10, 11, 6, "dress", tag={"rows": [(10, 11, "cream")]})
    sat = body.add(Part("satchel", (4.2, 8, 0), (0, 0, -0.1)))
    sat.cube(0, 0, -2, 2, 4, 4, "leather", tag={"rows": [(0, 1, "leather")]})
    sat.cube(-0.5, -1, -0.5, 1, 1, 1, "gold")
    body.cube(-4.2, -0.3, -2.8, 8, 1, 1, "leather")

    ra.cube(-3, -2, -2, 4, 9, 4, "dress")
    ra.cube(-3.5, 6, -2.5, 5, 2, 5, "cream", tag={"cut": "rag"})
    ra.cube(-2.5, 8, -1.5, 3, 3, 3, "skin")
    copy_side(ra, la)
    rl.cube(-2, 9, -2.5, 4, 3, 5, "boot", tag={"rows": [(0, 1, "leather")]})
    copy_side(rl, ll)
    return finish("iara", (head, hat, body, ra, la, rl, ll))


IARA_ANIM = """
        float walk = Math.min(limbSwingAmount, 1.0F);
        this.skirt.xRot += (this.rightLeg.xRot + this.leftLeg.xRot) * 0.1F;
        this.skirt.zRot += Mth.sin(limbSwing * 0.6662F) * 0.04F * walk;
        this.apron.xRot += Math.min(0.0F, Math.min(this.rightLeg.xRot, this.leftLeg.xRot)) * 0.5F;
        this.scarfTail.xRot += Mth.sin(ageInTicks * 0.08F) * 0.08F + walk * 0.35F;
        this.satchel.zRot += Mth.cos(limbSwing * 0.6662F) * 0.12F * walk;
        this.shawl.y += Mth.sin(ageInTicks * 0.06F) * 0.1F;
"""
IARA_PARTS = ["scarf_knot", "scarf_tail", "shawl", "apron", "skirt", "satchel"]

# ==================================================================== ORVYN
ROBE = ramp("#060a16", "#0b1226", "#111b38", "#18264c", "#213262", "#2b4079", "#375192")
TEAL = ramp("#082424", "#0d3535", "#144a4a", "#1c6060", "#267878", "#349191")
WHITE_B = ramp("#7b7f88", "#9ea3ac", "#bfc4cb", "#dadee3", "#eef1f4", "#ffffff")
TOME = ramp("#2a0f0b", "#3e1812", "#55221a", "#6e2e22", "#88402e")

ORVYN_MATS = {
    "skin": M(SKIN_PALE, "skin", 0.55, grad=0.12), "robe": M(ROBE, "cloth", 0.58), "robe_d": M(ROBE, "cloth", 0.4),
    "teal": M(TEAL, "cloth", 0.55), "silver": M(SILVER, "trim", 0.6), "beard": M(WHITE_B, "hair", 0.62),
    "gold": M(GOLD, "trim", 0.6), "leather": M(LEATHER, "leather", 0.5), "tome": M(TOME, "leather", 0.55),
    "paper": M(BONE, "flat", 0.75, bevel=False), "void": M(VOID, "flat", 0.4, bevel=False),
    "star": M(ramp("#6a8fbf", "#9fc0ea", "#d6e8ff", "#ffffff"), "trim", 0.7, glow=True),
}


def orvyn_model():
    head, hat, body, ra, la, rl, ll = skeleton()
    head.cube(-4, -8, -4, 8, 8, 8, "skin", tag={"grid": {"north": [
        "........", "........", "........", "WW....WW", ".wE..Ew.", "...nn...", "........", "........"]},
        "pal": {"W": "beard", "w": W, "E": EYE_BLUE, "n": hexc("#a07a66")}})
    head.cube(-3.6, -4.8, -4.6, 7, 3, 1, "gold", tag={"grid": {"north": ["ggg.ggg", "g g.g g", "ggg.ggg"]},
                                                       "pal": {"g": "gold"}})
    head.cube(-4.6, -8.6, -4.6, 9, 9, 9, "robe", tag={"cut": "open_front", "rows": [(8, 9, "silver")],
                                                       "grid": {"north": ["sssssssss"]}, "pal": {"s": "silver"}})
    t1 = head.add(Part("hood_tip", (0, -8.6, 2), (-0.45, 0, 0)))
    t1.cube(-3, -3, -2.5, 6, 3, 5, "robe")
    t2 = t1.add(Part("hood_tip2", (0, -3, 0.5), (-0.6, 0, 0)))
    t2.cube(-2, -3, -2, 4, 3, 4, "robe")
    t3 = t2.add(Part("hood_tip3", (0, -3, 0.2), (-0.8, 0, 0)))
    t3.cube(-1.5, -3, -1.5, 3, 3, 3, "robe")
    t4 = t3.add(Part("hood_tip4", (0, -3, 0), (-0.9, 0, 0)))
    t4.cube(-1, -2, -1, 2, 2, 2, "robe", tag={"rows": [(0, 1, "silver")]})
    t4.cube(-0.5, -3.5, -0.5, 1, 2, 1, "star")
    beard = head.add(Part("beard", (0, -2, -4.2)))
    beard.cube(-3.5, -1, -1, 7, 6, 2, "beard", tag={"grid": {"north": [
        "WWbbbWW", "bbbmbbb", "bbbbbbb", "bbbbbbb", "bbbbbbb", " bbbbb "]},
        "pal": {"W": "beard", "b": "beard", "m": hexc("#5b5e66")}})
    b2 = beard.add(Part("beard2", (0, 5, 0)))
    b2.cube(-2.5, 0, -1, 5, 4, 2, "beard")
    b3 = b2.add(Part("beard3", (0, 4, 0)))
    b3.cube(-1.5, 0, -0.8, 3, 3, 1, "beard")
    b3.cube(-1, 2, -1, 2, 1, 1, "gold")

    body.cube(-4.5, 0, -2.5, 9, 12, 5, "robe", tag={"cols": [(4, 5, "silver", ("north",))]})
    body.cube(-5.5, -0.5, -3, 11, 4, 6, "teal", tag={"rows": [(3, 4, "silver")]})
    body.cube(-1, 2, -3.4, 2, 2, 1, "gold")
    body.cube(-5, 9, -3, 10, 2, 6, "leather", tag={"grid": {"north": ["....gg....", "....gg...."]},
                                                   "pal": {"g": "gold"}})
    robe = body.add(Part("robe", (0, 11, 0)))
    robe.cube(-5, 0, -3, 10, 12, 6, "robe", tag={"rows": [(11, 12, "silver")], "cols": [(4, 6, "teal", ("north",))],
                                                 "fn": _stars})
    tome = body.add(Part("tome", (-5.2, 10, -1), (0, 0, 0.12)))
    tome.cube(-1.5, 0, -2.5, 2, 5, 4, "tome", tag={"rows": [(0, 1, "gold"), (4, 5, "gold")]})
    tome.cube(-1, 0.5, -2.5, 1, 4, 4, "paper")
    scr = body.add(Part("scrolls", (0, 2, 3), (0, 0, 0.6)))
    scr.cube(-1, -5, 0, 2, 10, 2, "leather", tag={"rows": [(0, 1, "gold"), (9, 10, "gold")]})
    scr.cube(-0.5, -6.5, 0.5, 1, 2, 1, "paper")

    ra.cube(-3.5, -2, -2.5, 5, 6, 5, "robe")
    sl = ra.add(Part("sleeve_r", (-1, 4, 0)))
    sl.cube(-3.5, 0, -3, 6, 6, 6, "robe", tag={"rows": [(5, 6, "teal")], "cut": "rag"})
    ra.cube(-2.5, 8, -1.5, 3, 3, 3, "skin")
    for c in ra.cubes:
        la.cube(-(c.o[0] + c.s[0]), c.o[1], c.o[2], *c.s, c.mat, tag=dict(c.tag))
    mirror_part(sl, lr, la)
    rl.cube(-2, 9, -2.5, 4, 3, 5, "leather", tag={"rows": [(0, 1, "gold")]})
    copy_side(rl, ll)
    return finish("orvyn", (head, hat, body, ra, la, rl, ll))


def _stars(c):
    from pix import h01
    if c.is_side and c.j < c.fh - 1 and h01(int(c.gp[0] * 2), int(c.gp[1] * 2), int(c.gp[2] * 2), 77) > 0.965:
        col = hexc("#cfe2ff")
        return col, col
    return None


ORVYN_ANIM = """
        float walk = Math.min(limbSwingAmount, 1.0F);
        this.robe.xRot += (this.rightLeg.xRot + this.leftLeg.xRot) * 0.12F;
        this.robe.zRot += Mth.sin(limbSwing * 0.6662F) * 0.05F * walk;
        this.hoodTip.xRot += Mth.sin(ageInTicks * 0.06F) * 0.04F - walk * 0.1F;
        this.hoodTip2.xRot += Mth.sin(ageInTicks * 0.06F - 0.6F) * 0.06F - walk * 0.12F;
        this.hoodTip3.xRot += Mth.sin(ageInTicks * 0.06F - 1.2F) * 0.08F - walk * 0.15F;
        this.hoodTip4.xRot += Mth.sin(ageInTicks * 0.06F - 1.8F) * 0.1F;
        this.beard.xRot += -this.head.xRot * 0.3F;
        this.beard2.xRot += Mth.sin(ageInTicks * 0.07F) * 0.04F + walk * 0.1F;
        this.beard3.xRot += Mth.sin(ageInTicks * 0.07F - 0.7F) * 0.06F + walk * 0.12F;
        this.sleeveR.xRot += walk * 0.2F + Mth.sin(ageInTicks * 0.05F) * 0.03F;
        this.sleeveL.xRot += walk * 0.2F + Mth.sin(ageInTicks * 0.05F + 1.0F) * 0.03F;
        this.tome.zRot += Mth.cos(limbSwing * 0.6662F) * 0.15F * walk;
"""
ORVYN_PARTS = ["hood_tip", "hood_tip2", "hood_tip3", "hood_tip4", "beard", "beard2", "beard3", "robe", "tome",
               "scrolls", "sleeve_r", "sleeve_l"]

# =================================================================== RAVENA
HAIR_K = ramp("#050407", "#0b090f", "#141119", "#1e1a25", "#2b2634", "#3c3647")
ARMOR_G = ramp("#0f150d", "#172014", "#212c1c", "#2c3a25", "#3a4b31", "#4b5f3f")
FUR_G = ramp("#2f2e33", "#47454c", "#615f67", "#7e7c84", "#9e9ca4", "#c2c0c7")
RED = ramp("#3a0a0c", "#5a1214", "#7c1c1d", "#9e2a27", "#c03d35")
FLETCH = ramp("#8c8c90", "#b5b5b9", "#dcdcdf", "#ffffff")

RAVENA_MATS = {
    "skin": M(SKIN_TAN, "skin", 0.6, grad=0.12), "hair": M(HAIR_K, "hair", 0.5), "armor": M(ARMOR_G, "leather", 0.55),
    "fur": M(FUR_G, "fur", 0.55), "leather": M(LEATHER, "leather", 0.55), "leather_d": M(LEATHER_D, "leather", 0.5),
    "red": M(RED, "cloth", 0.55), "wood": M(WOOD, "wood", 0.6), "fletch": M(FLETCH, "flat", 0.6),
    "iron": M(IRON, "metal", 0.6), "gold": M(GOLD, "trim", 0.6), "pants": M(HAIR_K, "cloth", 0.6), "boot": M(LEATHER, "leather", 0.45, noise=0.0),
}


def ravena_model():
    head, hat, body, ra, la, rl, ll = skeleton()
    head.cube(-4, -8, -4, 8, 8, 8, "skin", tag={"grid": {"north": [
        "........", "........", "........", ".bb..bb.", ".wE..Ew.", "pp....pp", "........", "...mm..."]},
        "pal": {"b": hexc("#120f16"), "w": W, "E": EYE_GREEN, "p": hexc("#7a1f22"), "m": hexc("#7a3a34")}})
    head.cube(-4.5, -8.5, -4.5, 9, 5, 9, "hair", tag={"cut": "open_front", "grid": {"north": [
        "hhhhhhhhh", "hhhhh hhh", "hh     hh", "h       h", "h       h"]}, "pal": {"h": "hair"}})
    head.cube(-4.5, -3.5, -1, 1, 3, 5, "hair")
    head.cube(3.5, -3.5, -1, 1, 3, 5, "hair")
    head.cube(3.6, -9.5, -1.5, 1, 3, 2, "red", tag={"rows": [(0, 1, "fletch")]})
    br = head.add(Part("braid", (0, -4, 4.5), (0.35, 0, 0)))
    br.cube(-1, 0, -1, 2, 4, 2, "hair")
    br2 = br.add(Part("braid2", (0, 4, 0), (-0.1, 0, 0)))
    br2.cube(-1, 0, -1, 2, 4, 2, "hair")
    br3 = br2.add(Part("braid3", (0, 4, 0), (-0.1, 0, 0)))
    br3.cube(-0.5, 0, -0.5, 1, 3, 1, "hair")
    br3.cube(-1, 0, -1, 2, 1, 2, "red")

    body.cube(-4, 0, -2, 8, 12, 4, "armor", tag={"grid": {"north": [
        "........", "........", "s.......", ".s......", "..s.....", "...s....", "....s...", ".....s..",
        "........", "........", "........", "........"]}, "pal": {"s": "leather"}})
    mantle = body.add(Part("mantle", (0, 0, 0)))
    mantle.cube(-5.5, -1, -3, 11, 4, 6, "fur", tag={"cut": "rag"})
    mantle.cube(-1, 1, -3.4, 2, 2, 1, "iron")
    q = body.add(Part("quiver", (2, 1, 2.3), (0, 0, -0.4)))
    q.cube(-1.5, -3, 0, 3, 10, 3, "leather", tag={"rows": [(0, 1, "gold"), (9, 10, "gold")]})
    for k, x in enumerate((-1, 0, 1)):
        q.cube(x - 0.5, -6 + (k % 2), 1, 1, 3, 1, "fletch", tag={"rows": [(2, 3, "wood")]})
    body.cube(-4.5, 8, -2.5, 9, 2, 5, "leather_d", tag={"grid": {"north": ["...ggg...", "...g.g..."]},
                                                       "pal": {"g": "iron"}})
    body.cube(-3.5, 9.5, -3, 1, 4, 1, "leather")
    ff = body.add(Part("flap_f", (0, 10, -2.3)))
    ff.cube(-3.5, 0, -0.5, 7, 4, 1, "armor", tag={"rows": [(3, 4, "leather")]})
    fb = body.add(Part("flap_b", (0, 10, 2.3)))
    fb.cube(-3.5, 0, -0.5, 7, 4, 1, "armor", tag={"rows": [(3, 4, "leather")]})

    ra.cube(-3, -2, -2, 4, 7, 4, "armor")
    ra.cube(-3.5, 5, -2.5, 5, 5, 5, "leather_d", tag={"rows": [(0, 1, "iron"), (4, 5, "iron")]})
    ra.cube(-2.5, 10, -1.5, 3, 2, 3, "skin")
    copy_side(ra, la)
    rl.cube(-2, 0, -2, 4, 7, 4, "pants")
    rl.cube(-2.3, 6, -2.3, 4, 6, 4, "boot", inflate=0.3, tag={"rows": [(0, 1, "leather_d")]})
    rl.cube(-2.5, 10.5, -3, 5, 1, 1, "leather_d")
    copy_side(rl, ll)
    return finish("ravena", (head, hat, body, ra, la, rl, ll))


RAVENA_ANIM = """
        float walk = Math.min(limbSwingAmount, 1.0F);
        this.braid.xRot += -this.head.xRot * 0.5F + Mth.sin(ageInTicks * 0.09F) * 0.05F + walk * 0.4F;
        this.braid2.xRot += Mth.sin(ageInTicks * 0.09F - 0.6F) * 0.08F + walk * 0.2F;
        this.braid3.xRot += Mth.sin(ageInTicks * 0.09F - 1.2F) * 0.1F + walk * 0.2F;
        this.braid.zRot += Mth.sin(limbSwing * 0.6662F) * 0.1F * walk;
        this.flapF.xRot += Math.min(0.0F, Math.min(this.rightLeg.xRot, this.leftLeg.xRot)) * 0.8F;
        this.flapB.xRot += Math.max(0.0F, Math.max(this.rightLeg.xRot, this.leftLeg.xRot)) * 0.8F;
        this.quiver.zRot += Mth.cos(limbSwing * 0.6662F) * 0.06F * walk;
"""
RAVENA_PARTS = ["braid", "braid2", "braid3", "mantle", "quiver", "flap_f", "flap_b"]

# ========================================================= SAQUEADOR CARMESIM
CRIMSON = ramp("#1a0405", "#2a0708", "#3f0c0c", "#561212", "#701a18", "#8c2621", "#a8362c")
MASK = ramp("#08070a", "#100e13", "#19161d", "#231f28", "#2f2a35")

RAIDER_MATS = {
    "skin": M(SKIN_TAN, "skin", 0.4), "crimson": M(CRIMSON, "cloth", 0.58), "crimson_d": M(CRIMSON, "cloth", 0.38),
    "mask": M(MASK, "cloth", 0.55), "leather": M(LEATHER, "leather", 0.55), "leather_d": M(LEATHER_D, "leather", 0.5),
    "iron": M(IRON, "metal", 0.55), "gold": M(GOLD, "trim", 0.55), "void": M(VOID, "flat", 0.45, bevel=False),
    "pants": M(MASK, "cloth", 0.5),
}


def raider_model():
    head, hat, body, ra, la, rl, ll = skeleton()
    head.cube(-4, -8, -4, 8, 8, 8, "void", tag={"grid": {"north": [
        "dddddddd", "dddddddd", "dddddddd", "dddddddd", "dYdddYdd", "mmmmmmmm", "mMmmmMmm", "mmmmmmmm"]},
        "pal": {"d": "void", "Y": hexc("#f0c060"), "m": "mask", "M": hexc("#2f2a35")}, "glowchars": "Y"})
    head.cube(-4.5, -8.5, -4.5, 9, 9, 9, "crimson", tag={"cut": "open_front", "rows": [(8, 9, "crimson_d")]})
    head.cube(-4.8, -9, -5, 10, 2, 2, "crimson")
    hood = head.add(Part("hood_tip", (0, -8.5, 3), (-0.3, 0, 0)))
    hood.cube(-2.5, -1, 0, 5, 3, 4, "crimson")
    hood2 = hood.add(Part("hood_tip2", (0, 1, 4), (-0.5, 0, 0)))
    hood2.cube(-1.5, -1, 0, 3, 2, 4, "crimson")
    hood3 = hood2.add(Part("hood_tip3", (0, 0, 4), (-0.45, 0, 0)))
    hood3.cube(-1, -0.5, 0, 2, 1, 3, "crimson_d")

    body.cube(-4, 0, -2, 8, 12, 4, "leather", tag={"grid": {"north": [
        "........", "........", "b.......", ".b......", "..b.....", "...b....", "....b...", ".....b..",
        "........", "........", "........", "........"]}, "pal": {"b": "leather_d"}})
    body.cube(-4.5, -1, -2.5, 9, 3, 5, "crimson_d")
    st = body.add(Part("scarf_tail", (2, 1, -2.6), (0, 0, -0.1)))
    st.cube(-1, 0, -0.5, 2, 6, 1, "crimson_d", tag={"cut": "rag"})
    body.cube(-4.5, 8, -2.5, 9, 2, 5, "leather_d", tag={"grid": {"north": ["...ggg...", "...g.g..."]},
                                                       "pal": {"g": "iron"}})
    body.cube(-4, 9, -3.5, 2, 2, 1, "leather")
    body.cube(1.5, 9, -3.4, 3, 2, 1, "leather")
    body.cube(3.8, 7, -1, 1, 5, 2, "leather_d", tag={"rows": [(0, 1, "iron")]})
    cl = body.add(Part("cloak", (0, -0.5, 2.3), (0.08, 0, 0)))
    cl.cube(-4.5, 0, 0, 9, 14, 1, "crimson", tag={"cut": "rag2"})

    ra.cube(-3, -2, -2, 4, 12, 4, "mask")
    ra.cube(-3.5, 5, -2.5, 5, 4, 5, "leather_d", tag={"rows": [(0, 1, "leather")]})
    pa = ra.add(Part("pauldron", (-1, -2, 0)))
    pa.cube(-3.5, -1.5, -3, 5, 3, 6, "iron", tag={"rows": [(2, 3, "gold")]})
    pa.cube(-3, -2.5, -2.5, 4, 1, 5, "iron")
    la.cube(1 - 2, -2, -2, 4, 12, 4, "mask")
    la.cube(-1.5, 5, -2.5, 5, 4, 5, "leather_d", tag={"rows": [(0, 1, "leather")]})
    dr = la.add(Part("drape", (1, -2, 0)))
    dr.cube(-2, -0.5, -2.5, 5, 5, 5, "crimson", tag={"cut": "rag"})
    rl.cube(-2, 0, -2, 4, 12, 4, "pants")
    rl.cube(-2.5, 7, -2.5, 5, 5, 5, "leather_d", tag={"rows": [(0, 1, "leather")]})
    copy_side(rl, ll)
    return finish("crimson_raider_model", (head, hat, body, ra, la, rl, ll))


RAIDER_ANIM = """
        float partial = ageInTicks - entity.tickCount;
        float hurt = entity.hurtTime > 0 ? (entity.hurtTime - partial) / 10.0F : 0.0F;
        float walk = Math.min(limbSwingAmount, 1.0F);
        this.hoodTip.xRot += -this.head.xRot * 0.4F + walk * 0.25F + Mth.sin(ageInTicks * 0.08F) * 0.04F;
        this.hoodTip2.xRot += walk * 0.2F + Mth.sin(ageInTicks * 0.08F - 0.7F) * 0.06F;
        this.hoodTip3.xRot += walk * 0.2F + Mth.sin(ageInTicks * 0.08F - 1.4F) * 0.08F;
        this.cloak.xRot += walk * 0.6F + Mth.sin(ageInTicks * 0.07F) * 0.04F;
        this.scarfTail.xRot += -walk * 0.3F + Mth.sin(ageInTicks * 0.1F) * 0.06F;
        this.head.xRot -= hurt * 0.4F;
"""
RAIDER_PARTS = ["hood_tip", "hood_tip2", "hood_tip3", "scarf_tail", "cloak", "pauldron", "drape"]
