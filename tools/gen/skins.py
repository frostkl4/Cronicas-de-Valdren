"""Skins 64x64 (formato de jogador, com camada externa) dos NPCs e inimigos humanoides.

Cada personagem e uma lista de "camadas" por parte do corpo. Os detalhes importantes
(rostos, frente do corpo) sao desenhados com grades de caracteres; o resto e pintado
proceduralmente (tecido com dobras, couro, metal, pelo) a partir de rampas de cor.
"""
from PIL import Image

from pix import ramp, pick, pick_dither, h01, fbm, hexc


def sh(text):
    """Hash estavel de texto (o hash() do Python muda a cada execucao)."""
    return sum((k + 1) * ord(ch) for k, ch in enumerate(text))

PARTS = {
    "head": (0, 0, 8, 8, 8), "hat": (32, 0, 8, 8, 8),
    "body": (16, 16, 8, 12, 4), "jacket": (16, 32, 8, 12, 4),
    "rarm": (40, 16, 4, 12, 4), "rsleeve": (40, 32, 4, 12, 4),
    "larm": (32, 48, 4, 12, 4), "lsleeve": (48, 48, 4, 12, 4),
    "rleg": (0, 16, 4, 12, 4), "rpants": (0, 32, 4, 12, 4),
    "lleg": (16, 48, 4, 12, 4), "lpants": (0, 48, 4, 12, 4),
}
FACE_LIGHT = {"front": 0.0, "west": -0.06, "east": -0.06, "back": -0.1, "top": 0.1, "bottom": -0.25}


def regions(part):
    u, v, w, h, d = PARTS[part]
    return {
        "top": (u + d, v, w, d), "bottom": (u + d + w, v, w, d),
        "west": (u, v + d, d, h), "front": (u + d, v + d, w, h),
        "east": (u + d + w, v + d, d, h), "back": (u + 2 * d + w, v + d, w, h),
    }


class P:
    """Contexto de pintura de um texel."""
    __slots__ = ("part", "face", "i", "j", "w", "h", "seed")

    def __init__(self, part, face, i, j, w, h, seed):
        self.part, self.face, self.i, self.j, self.w, self.h, self.seed = part, face, i, j, w, h, seed

    @property
    def lt(self):
        return FACE_LIGHT[self.face]

    @property
    def vf(self):
        return (self.j + 0.5) / self.h if self.face not in ("top", "bottom") else (0.0 if self.face == "top" else 1.0)

    @property
    def edge(self):
        return min(self.i, self.j, self.w - 1 - self.i, self.h - 1 - self.j)


# ------------------------------------------------------------ materiais
def cloth(r, base=0.6, folds=0.09, grad=0.3):
    def f(p):
        t = base + p.lt - p.vf * grad
        if folds and p.face not in ("top", "bottom"):
            t += folds * (1 if (p.i + (p.j // 4)) % 3 == 0 else 0) - folds * 0.5 * ((p.i + 1) % 3 == 0)
        t += (fbm(p.i * 3 + sh(p.part) % 17, p.j * 2, p.seed, s=3, seed=7) - 0.5) * 0.22
        return pick_dither(r, t, p.i, p.j)
    return f


def leather(r, base=0.55):
    def f(p):
        t = base + p.lt - p.vf * 0.15 + (h01(p.i, p.j, p.seed, sh(p.face) % 9) - 0.5) * 0.18
        if p.edge == 0 and p.face not in ("top", "bottom"):
            t -= 0.12
        return pick(r, t)
    return f


def metal(r, base=0.6):
    def f(p):
        t = base + p.lt - p.vf * 0.25
        if p.face not in ("top", "bottom") and p.j % 4 == 0:
            t += 0.18
        if p.face not in ("top", "bottom") and p.j % 4 == 3:
            t -= 0.12
        if p.edge == 0:
            t -= 0.1
        t += (h01(p.i, p.j, p.seed) - 0.5) * 0.08
        return pick(r, t)
    return f


def fur(r, base=0.6):
    def f(p):
        t = base + p.lt - p.vf * 0.2 + (fbm(p.i * 2.5, p.j * 1.2, p.seed, s=2.2, seed=3) - 0.5) * 0.5
        return pick_dither(r, t, p.i, p.j)
    return f


def flat(c):
    return lambda p: c


def grid(rows, pal, fallback=None):
    """Desenho por caracteres. '.' usa o fallback; ' ' = transparente."""
    def f(p):
        if p.j >= len(rows) or p.i >= len(rows[p.j]):
            return fallback(p) if fallback else None
        ch = rows[p.j][p.i]
        if ch == ".":
            return fallback(p) if fallback else None
        if ch == " ":
            return (0, 0, 0, 0)
        c = pal[ch]
        return c(p) if callable(c) else c
    return f


def by_face(default=None, **faces):
    def f(p):
        g = faces.get(p.face, default)
        return g(p) if g else None
    return f


def rows_band(bands, default=None):
    """bands: lista de (j0, j1, painter) aplicada nas laterais/frente/costas."""
    def f(p):
        if p.face not in ("top", "bottom"):
            for j0, j1, g in bands:
                if j0 <= p.j < j1:
                    return g(p)
        return default(p) if default else None
    return f


def paint(spec, seed=1, glow_spec=None):
    img = Image.new("RGBA", (64, 64), (0, 0, 0, 0))
    gimg = Image.new("RGBA", (64, 64), (0, 0, 0, 0))
    px, gx = img.load(), gimg.load()
    for target, s, out in ((spec, seed, px), (glow_spec or {}, seed, gx)):
        for part, painter in target.items():
            for face, (u, v, w, h) in regions(part).items():
                for j in range(h):
                    for i in range(w):
                        col = painter(P(part, face, i, j, w, h, s))
                        if col is not None:
                            out[u + i, v + j] = tuple(int(a) for a in (col if len(col) == 4 else (*col, 255)))
    return img, (gimg if glow_spec else None)


# =============================================================== BRAM
def bram():
    SK = ramp("#5e3524", "#7d4a31", "#9c6241", "#b97a52", "#cf9468", "#e2ae84")
    BEARD = ramp("#3b1a10", "#55261a", "#723524", "#8f4630", "#a8583b")
    SHIRT = ramp("#2a0f0d", "#3f1714", "#58201b", "#712b22", "#8a3a2c", "#a04b38")
    APRON = ramp("#241509", "#33200f", "#462d17", "#5b3c20", "#704c2a", "#86603a")
    PANTS = ramp("#16120f", "#221c17", "#2f2720", "#3d332a", "#4c4034")
    BOOT = ramp("#0e0a08", "#1a120d", "#271b13", "#35251a")
    IRON = ramp("#3a3d44", "#575c66", "#7a808c", "#a2a8b3", "#c9ced6")
    pal = {"s": pick(SK, 0.6), "S": pick(SK, 0.35), "h": pick(SK, 0.85), "b": pick(BEARD, 0.5),
           "B": pick(BEARD, 0.25), "d": pick(BEARD, 0.75), "w": hexc("#e8e2d6"), "e": hexc("#2a1a10"),
           "k": pick(SK, 0.15), "m": hexc("#4a1f17"), "o": pick(BEARD, 0.05)}
    face = grid([
        "SsshhssS",
        "ssssssss",
        "sssssss.",
        "obbssbbo",
        "swesswes",
        "bsskksb.",
        "bdbmmbdb",
        "BbbbbbbB",
    ], pal, lambda p: pick(SK, 0.55))
    head_side = grid([
        "ssssssss",
        "ssssssss",
        "BBssssss",
        "BBBsssss",
        "BBBBssss",
        "BbBBbsss",
        "BBbBBbss",
        "BBBBBBbs",
    ], pal)
    head = by_face(default=lambda p: pick(SK, 0.5 + p.lt + h01(p.i, p.j) * 0.15),
                   front=face, west=lambda p: head_side(P(p.part, p.face, 7 - p.i, p.j, 8, 8, 1)),
                   east=head_side,
                   top=lambda p: pick(SK, 0.75 + h01(p.i, p.j) * 0.15),
                   back=lambda p: pick(BEARD, 0.3) if p.j >= 5 else pick(SK, 0.45 + h01(p.i, p.j) * 0.1))
    beard3d = by_face(front=grid([
        "        ",
        "        ",
        "        ",
        "        ",
        "        ",
        "        ",
        " bd  db ",
        "BbbbbbbB",
    ], pal), bottom=lambda p: pick(BEARD, 0.3) if p.j >= 5 else (0, 0, 0, 0))
    shirt = cloth(SHIRT, 0.6)
    apron = leather(APRON, 0.58)
    body_front = grid([
        "..a..a..",
        "..a..a..",
        ".aaaaaa.",
        ".aaaaaa.",
        ".aaaaaa.",
        ".aaPPaa.",
        "LLLiLLLL",
        ".aaaaaa.",
        ".aaaaaa.",
        ".aaaaaa.",
        ".aaaaaa.",
        "..aaaa..",
    ], {"a": apron, "L": leather(BOOT, 0.6), "i": pick(IRON, 0.8), "P": pick(APRON, 0.2)}, shirt)
    body = by_face(default=shirt, front=body_front,
                   back=grid([
                       "........", "........", "..a..a..", "...aa...", "...aa...", "..a..a..",
                       "LLLLLLLL", "........", "........", "........", "........", "........",
                   ], {"a": pick(APRON, 0.4), "L": leather(BOOT, 0.6)}, shirt))
    arm = rows_band([(0, 4, shirt), (4, 4 + 5, lambda p: pick(SK, 0.55 + p.lt + h01(p.i, p.j) * 0.12)),
                     (9, 12, leather(APRON, 0.35))],
                    default=lambda p: shirt(p) if p.face == "top" else pick(APRON, 0.3))
    leg = rows_band([(0, 8, cloth(PANTS, 0.6, folds=0.05)), (8, 11, leather(BOOT, 0.6)),
                     (11, 12, lambda p: pick(IRON, 0.5) if p.face == "front" else pick(BOOT, 0.2))],
                    default=lambda p: pick(PANTS, 0.4))
    apron3d = by_face(front=grid([
        "        ", "        ", " aaaaaa ", " aaaaaa ", " aaaaaa ", " aaaaaa ",
        "        ", " aaaaaa ", " aaaaaa ", " aaaaaa ", " aaaaaa ", "  aaaa  ",
    ], {"a": leather(APRON, 0.66)}))
    return {"head": head, "hat": beard3d, "body": body, "jacket": apron3d,
            "rarm": arm, "larm": arm, "rleg": leg, "lleg": leg}


# =============================================================== IARA
def iara():
    SK = ramp("#4a2a1c", "#663a27", "#7f4c33", "#9a6142", "#b27752", "#c98f68")
    HAIR = ramp("#141012", "#221b1d", "#33292b", "#4a3d3d", "#8c8584", "#b7b1ad")
    SCARF = ramp("#14301f", "#1d432b", "#285a39", "#347149", "#43885a", "#5aa06e")
    DRESS = ramp("#2a2015", "#3a2c1c", "#4c3a25", "#5f4a30", "#735b3c", "#896e4b")
    APRON = ramp("#8c8272", "#a99f8c", "#c2b8a3", "#d6cdb8", "#e8e0cc")
    BOOT = ramp("#1a100a", "#2a1a10", "#3a2617", "#4b331f")
    pal = {"s": pick(SK, 0.6), "S": pick(SK, 0.35), "h": pick(HAIR, 0.3), "H": pick(HAIR, 0.1),
           "g": pick(HAIR, 0.85), "c": pick(SCARF, 0.6), "C": pick(SCARF, 0.3), "w": hexc("#f0ebe0"),
           "e": hexc("#3a2414"), "l": hexc("#8a3a30"), "k": pick(SK, 0.2), "r": pick(SK, 0.8),
           "y": hexc("#f0c73a"), "Y": hexc("#c48a1a")}
    face = grid([
        "cCccccCc",
        "hgcccchH",
        "hssssssh",
        "hSkssksh",
        "swessews",
        "srsssrss",
        "sssllsss",
        "SsssssSS",
    ], pal)
    side = grid([
        "cccccccc", "ccCccccc", "hhhhhhsh", "hhhghhsS", "hhhhhssS", "hhhhssss", "Hhhhssss", "HHhsssSS",
    ], pal)
    head = by_face(default=lambda p: pick(HAIR, 0.3),
                   front=face, west=lambda p: side(P(p.part, p.face, 7 - p.i, p.j, 8, 8, 1)), east=side,
                   top=lambda p: pick(SCARF, 0.55 + (h01(p.i, p.j) - .5) * 0.2),
                   back=grid(["cccccccc", "cccCCccc", "hhhhhhhh", "hhgHHghh", "hhHHHHhh",
                              "hhhhhhhh", "Hhhhhhhh", "HHHhhHHH"], pal))
    hat = by_face(front=grid(["c      c", "        ", "        ", "        ", "        ", "        ", "        ", "        "], pal),
                  back=grid(["        ", "  cCCc  ", "   CC   ", "        ", "  hhhh  ", "  hgHh  ", "  hhhh  ", "        "], pal),
                  east=grid(["  y     ", " yYy    ", "  y     ", "        ", "        ", "        ", "        ", "        "], pal),
                  top=lambda p: pick(SCARF, 0.65) if 1 <= p.i <= 6 and 1 <= p.j <= 6 else None)
    dress = cloth(DRESS, 0.6)
    ap = cloth(APRON, 0.65, folds=0.06)
    body_front = grid([
        "...ss...",
        "..s..s..",
        "b.....b.",
        ".b...b..",
        "..b.b...",
        "aaaBaaaa",
        "aaaaaaaa",
        "aaggaaaa",
        "aaggaaaa",
        "aaaaaaaa",
        "aaaaaaaa",
        "aaaaaaaa",
    ], {"s": pick(SK, 0.55), "a": ap, "b": pick(BOOT, 0.7), "B": hexc("#c48a1a"),
        "g": lambda p: hexc("#4f9a46") if (p.i + p.j) % 2 else hexc("#2f6a34")}, dress)
    body = by_face(default=dress, front=body_front,
                   back=grid(["........", "........", ".b......", "..b.....", "...b....", "aaaaaaaa",
                              "........", "........", "........", "........", "........", "........"],
                             {"a": pick(APRON, 0.5), "b": pick(BOOT, 0.7)}, dress))
    arm = rows_band([(0, 9, dress), (9, 10, pick_band(APRON, 0.7)),
                     (10, 12, lambda p: pick(SK, 0.55 + p.lt))],
                    default=lambda p: dress(p) if p.face == "top" else pick(SK, 0.4))
    leg = rows_band([(0, 9, cloth(DRESS, 0.5, folds=0.05)), (9, 12, leather(BOOT, 0.6))],
                    default=lambda p: pick(DRESS, 0.4))
    skirt = by_face(front=grid(["        ", "        ", "        ", "        ", "        ", "        ",
                                "        ", "        ", "dddddddd", "dddddddd", "d d dd d", "        "],
                               {"d": cloth(DRESS, 0.55)}),
                    back=grid(["        ", "        ", "        ", "        ", "        ", "        ",
                               "        ", "        ", "dddddddd", "dddddddd", " dd d dd", "        "],
                              {"d": cloth(DRESS, 0.45)}))
    return {"head": head, "hat": hat, "body": body, "jacket": skirt,
            "rarm": arm, "larm": arm, "rleg": leg, "lleg": leg}


def pick_band(r, t):
    return lambda p: pick(r, t + p.lt)


# ============================================================== ORVYN
def orvyn():
    SK = ramp("#6e4a3a", "#8c6250", "#a87c68", "#c39883", "#d8b29e", "#ead0c0")
    BEARD = ramp("#7f828a", "#a3a7ae", "#c4c8ce", "#dfe2e6", "#f3f5f7")
    ROBE = ramp("#070b18", "#0c1428", "#132040", "#1b2d58", "#253c70", "#304c88")
    TRIM = ramp("#5a6270", "#8a94a4", "#b8c2d0", "#e0e8f0")
    GOLD = ramp("#6a4a12", "#9a6e1c", "#c99a2e", "#e8c25a")
    pal = {"s": pick(SK, 0.6), "S": pick(SK, 0.35), "b": pick(BEARD, 0.6), "B": pick(BEARD, 0.3),
           "W": pick(BEARD, 0.9), "g": pick(GOLD, 0.7), "G": pick(GOLD, 0.35), "e": hexc("#1f2a44"),
           "w": hexc("#eef2f6"), "r": pick(ROBE, 0.5), "R": pick(ROBE, 0.25), "k": pick(SK, 0.2)}
    face = grid([
        "RrrrrrrR",
        "rbsssbsr",
        "bBssssBb",
        "WWsssWWs",
        "gweggewg",
        "sssksssb",
        "bWbbbbWb",
        "bbWbbWbb",
    ], pal)
    side = grid(["rrrrrrrr", "rrrrrrrr", "rrbbbssr", "rrbbBsss", "rrbbbsss", "rrbbbsss", "rrbbbbss", "rrrbbbbs"], pal)
    head = by_face(default=lambda p: pick(ROBE, 0.4), front=face,
                   west=lambda p: side(P(p.part, p.face, 7 - p.i, p.j, 8, 8, 1)), east=side,
                   top=cloth(ROBE, 0.6, folds=0), back=cloth(ROBE, 0.5))
    hood = by_face(front=grid(["rRrrrrRr", "r      r", "r      r", "R      R", "        ", "        ",
                               " bWbbWb ", "  bbbb  "], pal),
                   west=cloth(ROBE, 0.5), east=cloth(ROBE, 0.5), back=cloth(ROBE, 0.45),
                   top=cloth(ROBE, 0.62, folds=0),
                   bottom=lambda p: pick(BEARD, 0.5) if p.j >= 5 else (0, 0, 0, 0))
    robe = cloth(ROBE, 0.62, folds=0.1)

    def stars(base):
        def f(p):
            if p.face in ("front", "back", "west", "east") and h01(p.i, p.j, sh(p.part) % 13, sh(p.face) % 7) > 0.965:
                return pick(TRIM, 0.9)
            return base(p)
        return f

    body_front = grid([
        "TbbbbbbT",
        "TbbbbbbT",
        "TbbWbbbT",
        "TbbbbbbT",
        "T.bbbb.T",
        "T......T",
        "GGGgGGGG",
        "T......T",
        "T......T",
        "T......T",
        "T......T",
        "TTTTTTTT",
    ], {"T": lambda p: pick(TRIM, 0.6 + p.lt), "b": lambda p: pick(BEARD, 0.55 + (h01(p.i, p.j) - 0.5) * 0.3),
        "W": pick(BEARD, 0.9), "G": pick(GOLD, 0.5), "g": pick(GOLD, 0.9)}, stars(robe))
    body = by_face(default=stars(robe), front=body_front,
                   back=rows_band([(6, 7, lambda p: pick(GOLD, 0.45))], default=stars(robe)))
    arm = rows_band([(0, 10, stars(robe)), (10, 11, lambda p: pick(TRIM, 0.6 + p.lt)),
                     (11, 12, lambda p: pick(SK, 0.55 + p.lt))],
                    default=lambda p: robe(p) if p.face == "top" else pick(SK, 0.4))
    leg = rows_band([(0, 10, stars(cloth(ROBE, 0.5, folds=0.1))), (10, 11, lambda p: pick(TRIM, 0.55 + p.lt)),
                     (11, 12, lambda p: pick(GOLD, 0.3) if p.face == "front" else pick(SK, 0.4))],
                    default=lambda p: pick(ROBE, 0.3))
    sleeve = rows_band([(7, 11, cloth(ROBE, 0.55)), (11, 12, lambda p: pick(TRIM, 0.7) if p.i % 2 == 0 else (0, 0, 0, 0))])
    robe3d = by_face(front=grid(["        ", "        ", "        ", "        ", "        ", "        ",
                                 "        ", " rrrrrr ", " rrrrrr ", "rrrrrrrr", "rrrrrrrr", "tttttttt"],
                                {"r": cloth(ROBE, 0.55), "t": lambda p: pick(TRIM, 0.7)}),
                     back=grid(["        ", "        ", "        ", "        ", "        ", "        ",
                                "        ", "rrrrrrrr", "rrrrrrrr", "rrrrrrrr", "rrrrrrrr", "tttttttt"],
                               {"r": cloth(ROBE, 0.45), "t": lambda p: pick(TRIM, 0.6)}))
    return {"head": head, "hat": hood, "body": body, "jacket": robe3d, "rarm": arm, "larm": arm,
            "rsleeve": sleeve, "lsleeve": sleeve, "rleg": leg, "lleg": leg}


# ============================================================= RAVENA
def ravena():
    SK = ramp("#5c3a2c", "#7a4e3b", "#97654d", "#b27e62", "#c99878", "#dcb091")
    HAIR = ramp("#08070a", "#121016", "#1d1a22", "#2a2631", "#3a3544")
    LEATHER = ramp("#141a12", "#1e271b", "#293424", "#35432f", "#43543b", "#536848")
    FUR = ramp("#3b3a3c", "#56545a", "#727077", "#908e95", "#b0aeb4", "#cfcdd2")
    BROWN = ramp("#1e130c", "#2e1d12", "#3f2919", "#523622", "#66442c")
    pal = {"s": pick(SK, 0.6), "S": pick(SK, 0.35), "h": pick(HAIR, 0.5), "H": pick(HAIR, 0.15),
           "q": pick(HAIR, 0.85), "w": hexc("#ece6dc"), "e": hexc("#3d5a2a"), "p": hexc("#7a1f22"),
           "k": pick(SK, 0.2), "l": hexc("#8a4a40")}
    face = grid([
        "hhhhhhhh",
        "hqhhhhhh",
        "hhsssshh",
        "hHssssHh",
        "hwesswe.",
        "hppsspph",
        "ssssssss",
        "SsslsssS",
    ], pal, lambda p: pick(SK, 0.6))
    side = grid(["hhhhhhhh", "hhhhhqhh", "hhhhhhhs", "hhhhhhhs", "hhhhhhss", "hhhhhsss", "Hhhhhsss", "HHhhhsSS"], pal)
    head = by_face(default=lambda p: pick(HAIR, 0.45), front=face,
                   west=lambda p: side(P(p.part, p.face, 7 - p.i, p.j, 8, 8, 1)), east=side,
                   top=lambda p: pick(HAIR, 0.55 + ((p.i + p.j) % 3 == 0) * 0.3),
                   back=lambda p: pick(HAIR, 0.3 + ((p.j + (p.i in (3, 4))) % 2) * 0.35))
    hat = by_face(back=grid(["        ", "        ", "        ", "        ", "   hh   ", "   qh   ", "   hh   ", "   Hh   "], pal))
    armor = leather(LEATHER, 0.6)
    fur_p = fur(FUR, 0.6)
    body_front = grid([
        "ffffffff",
        "ff.ff.ff",
        "b.....b.",
        ".b...b..",
        "..b.b...",
        "...b....",
        "BBBBGBBB",
        "........",
        "....b...",
        "........",
        "........",
        "........",
    ], {"f": fur_p, "b": pick(BROWN, 0.75), "B": leather(BROWN, 0.5), "G": hexc("#b89a5a")}, armor)
    body = by_face(default=armor, front=body_front,
                   back=grid(["ffffffff", "ffffffff", ".q......", "..q.....", "...q....", "....q...",
                              "BBBBBBBB", "........", "........", "........", "........", "........"],
                             {"f": fur_p, "q": pick(BROWN, 0.85), "B": leather(BROWN, 0.5)}, armor))
    arm = rows_band([(0, 3, fur_p), (3, 7, lambda p: pick(SK, 0.55 + p.lt + h01(p.i, p.j) * 0.1)),
                     (7, 11, leather(BROWN, 0.55)), (11, 12, lambda p: pick(SK, 0.5 + p.lt))],
                    default=lambda p: fur_p(p) if p.face == "top" else pick(SK, 0.45))
    leg = rows_band([(0, 6, cloth(LEATHER, 0.45, folds=0.05)), (6, 12, leather(BROWN, 0.5))],
                    default=lambda p: pick(BROWN, 0.3))
    mantle = by_face(front=grid(["ffffffff", "ff    ff", "        ", "        ", "        ", "        ",
                                 "        ", "        ", "        ", "        ", "        ", "        "], {"f": fur(FUR, 0.7)}),
                     back=grid(["ffffffff", "ffffffff", "f ffff f", "  f  f  ", "        ", "        ",
                                "        ", "        ", "        ", "        ", "        ", "        "], {"f": fur(FUR, 0.62)}),
                     top=fur(FUR, 0.75),
                     west=grid(["ffff", "ffff", " ff ", "    ", "    ", "    ", "    ", "    ", "    ", "    ", "    ", "    "], {"f": fur(FUR, 0.6)}),
                     east=grid(["ffff", "ffff", " ff ", "    ", "    ", "    ", "    ", "    ", "    ", "    ", "    ", "    "], {"f": fur(FUR, 0.6)}))
    return {"head": head, "hat": hat, "body": body, "jacket": mantle, "rarm": arm, "larm": arm,
            "rleg": leg, "lleg": leg}


# ===================================================== SAQUEADOR CARMESIM
def raider():
    RED = ramp("#1e0606", "#2e0909", "#440e0e", "#5c1414", "#761c1a", "#922822")
    MASK = ramp("#0b0a0c", "#141216", "#1e1b21", "#29252c")
    LEATHER = ramp("#1a120c", "#281b12", "#382619", "#4a3322", "#5d412c")
    IRON = ramp("#2c2f35", "#454951", "#646a74", "#8a909b", "#b3b9c3")
    SK = ramp("#5c3a2c", "#7a4e3b", "#97654d", "#b27e62")
    pal = {"r": pick(RED, 0.55), "R": pick(RED, 0.25), "d": hexc("#08080a"), "m": pick(MASK, 0.6),
           "M": pick(MASK, 0.3), "y": hexc("#d8c08a"), "s": pick(SK, 0.55)}
    face = grid([
        "rrrrrrrr",
        "rRddddRr",
        "rddddddr",
        "rddddddr",
        "rdyddydr",
        "rmmmmmmr",
        "RmMmmMmR",
        "RmmmmmmR",
    ], pal)
    head = by_face(default=cloth(RED, 0.55, folds=0), front=face,
                   back=cloth(RED, 0.5), top=cloth(RED, 0.62, folds=0))
    hood = by_face(front=grid(["rrrrrrrr", "r      r", "R      R", "R      R", "        ", "        ", "        ", "        "],
                              {"r": cloth(RED, 0.6, folds=0), "R": cloth(RED, 0.35, folds=0)}),
                   west=cloth(RED, 0.55), east=cloth(RED, 0.55), back=cloth(RED, 0.5), top=cloth(RED, 0.65, folds=0))
    armor = leather(LEATHER, 0.55)
    body_front = grid([
        "mmmmmmmm",
        "....mm..",
        "........",
        "..b.....",
        "...b....",
        "....b...",
        "BBBBGBBB",
        ".p....p.",
        "........",
        "........",
        "........",
        "........",
    ], {"m": pick(MASK, 0.5), "b": pick(LEATHER, 0.85), "B": pick(LEATHER, 0.25), "G": pick(IRON, 0.8),
        "p": pick(LEATHER, 0.75)}, armor)
    body = by_face(default=cloth(RED, 0.5), front=body_front)
    rarm = rows_band([(0, 4, metal(IRON, 0.6)), (4, 9, cloth(RED, 0.45)), (9, 12, leather(LEATHER, 0.4))],
                     default=lambda p: pick(IRON, 0.6) if p.face == "top" else pick(LEATHER, 0.3))
    larm = rows_band([(0, 9, cloth(RED, 0.45)), (9, 12, leather(LEATHER, 0.4))],
                     default=lambda p: pick(RED, 0.5) if p.face == "top" else pick(LEATHER, 0.3))
    leg = rows_band([(0, 7, cloth(MASK, 0.6, folds=0.05)), (7, 12, leather(LEATHER, 0.5))],
                    default=lambda p: pick(LEATHER, 0.2))
    cloak = by_face(back=grid(["rrrrrrrr", "rrrrrrrr", "rrrrrrrr", "rrrrrrrr", "rrrrrrrr", "rrrrrrrr",
                               "rrrrrrrr", "rrrrrrrr", "rrrrrrrr", "rrrrrrrr", "r rr rrr", " r  r r "],
                              {"r": cloth(RED, 0.5, folds=0.12)}),
                    west=grid(["rr  ", "rr  ", "r   ", "r   ", "r   ", "r   ", "    ", "    ", "    ", "    ", "    ", "    "],
                              {"r": cloth(RED, 0.45)}),
                    east=grid(["  rr", "  rr", "   r", "   r", "   r", "   r", "    ", "    ", "    ", "    ", "    ", "    "],
                              {"r": cloth(RED, 0.45)}))
    pauldron = by_face(top=metal(IRON, 0.75), front=rows_band([(0, 3, metal(IRON, 0.65))]),
                       west=rows_band([(0, 3, metal(IRON, 0.6))]), east=rows_band([(0, 3, metal(IRON, 0.6))]),
                       back=rows_band([(0, 3, metal(IRON, 0.55))]))
    return {"head": head, "hat": hood, "body": body, "jacket": cloak, "rarm": rarm, "larm": larm,
            "rsleeve": pauldron, "rleg": leg, "lleg": leg}


# ===================================================== CAVALEIRO SEM LUZ
def knight():
    STEEL = ramp("#0b0c10", "#13151b", "#1c1f27", "#272b35", "#343a47", "#46505f", "#5e6a7c")
    VIOLET = hexc("#b47cff")
    VIOLET2 = hexc("#e6d2ff")
    CAPE = ramp("#0c0712", "#160c20", "#211331", "#2d1b43", "#3a2456")
    GOLD = ramp("#3d2a10", "#5e4219", "#84602a", "#a8803e")
    pal = {"a": metal(STEEL, 0.6), "A": metal(STEEL, 0.35), "v": VIOLET, "V": VIOLET2, "d": hexc("#040406"),
           "g": pick(GOLD, 0.7)}
    helm = grid([
        "AaaaaaaA",
        "aaagaaaa",
        "aaaaaaaa",
        "AddddddA",
        "dVvddvVd",
        "aaadaaaa",
        "aadadada",
        "AaaaaaaA",
    ], pal)
    head = by_face(default=metal(STEEL, 0.55), front=helm, top=metal(STEEL, 0.7))
    crest = by_face(top=lambda p: pick(CAPE, 0.6 + h01(p.i, p.j) * 0.3) if p.i in (3, 4) else None,
                    front=grid(["   vv   ", "        ", "        ", "        ", "        ", "        ", "        ", "        "],
                               {"v": pick(CAPE, 0.7)}),
                    back=grid(["   cc   ", "   cc   ", "   cc   ", "    c   ", "        ", "        ", "        ", "        "],
                              {"c": pick(CAPE, 0.6)}))
    chest = grid([
        "AaaaaaaA",
        "aaaaaaaa",
        "aAaggaAa",
        "aaavvaaa",
        "aaaVvaaa",
        "aaavaaaa",
        "AAAAgAAA",
        "aaaaaaaa",
        "aAaaaaAa",
        "aaaaaaaa",
        "AaaaaaaA",
        "AAAAAAAA",
    ], pal)
    body = by_face(default=metal(STEEL, 0.5), front=chest)
    arm = by_face(default=metal(STEEL, 0.5),
                  front=grid(["aaaa", "AaaA", "aaaa", "aaaa", "AAAA", "aaaa", "aaaa", "aaaa", "AaaA", "aaaa", "dAAd", "dddd"], pal))
    leg = by_face(default=metal(STEEL, 0.5),
                  front=grid(["aaaa", "aaaa", "AaaA", "aaaa", "aAAa", "agga", "aaaa", "aaaa", "AaaA", "aaaa", "aaaa", "AAAA"], pal))
    cape = by_face(back=grid(["cccccccc", "cccccccc", "cccccccc", "cccccccc", "cccccccc", "cccccccc",
                              "cccccccc", "cccccccc", "c cccc c", "c cc  c ", "  c   c ", "        "],
                             {"c": cloth(CAPE, 0.6, folds=0.12)}),
                   top=lambda p: pick(CAPE, 0.6) if p.j >= 2 else None)
    pauld = by_face(top=metal(STEEL, 0.75),
                    front=rows_band([(0, 4, metal(STEEL, 0.62))]), back=rows_band([(0, 4, metal(STEEL, 0.55))]),
                    west=rows_band([(0, 4, metal(STEEL, 0.6))]), east=rows_band([(0, 4, metal(STEEL, 0.6))]))
    spec = {"head": head, "hat": crest, "body": body, "jacket": cape, "rarm": arm, "larm": arm,
            "rsleeve": pauld, "lsleeve": pauld, "rleg": leg, "lleg": leg}
    glow = {
        "head": by_face(front=grid(["        ", "        ", "        ", "        ", " Vv  vV ", "        ", "        ", "        "],
                                   {"v": VIOLET, "V": VIOLET2})),
        "body": by_face(front=grid(["        ", "        ", "        ", "   vv   ", "   Vv   ", "   v    ",
                                    "        ", "        ", "        ", "        ", "        ", "        "],
                                   {"v": VIOLET, "V": VIOLET2})),
    }
    return spec, glow


ALL = {"npc/bram": bram, "npc/iara": iara, "npc/orvyn": orvyn, "npc/ravena": ravena,
       "crimson_raider": raider}
