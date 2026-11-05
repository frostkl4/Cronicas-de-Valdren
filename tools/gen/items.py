"""Ícones 32x32 dos itens: 8 espadas, materiais, itens de chefe e cartas de chamado dos NPCs.

Espadas: desenhadas ao longo da diagonal x + y = 31 (empunhadura embaixo à esquerda, ponta em cima
à direita, como no Minecraft). Para cada pixel:  s = x - y (ao longo da lâmina) e  q = x + y - 31
(de lado; q < 0 é o lado iluminado).
"""
import math

from PIL import Image, ImageDraw

from pix import ramp, pick, h01, fbm, hexc, mix

N = 32
OUTLINE = hexc("#0c0a10")


def new():
    return Image.new("RGBA", (N, N), (0, 0, 0, 0))


def outline(img, color=OUTLINE):
    px = img.load()
    solid = [[px[x, y][3] > 0 for y in range(N)] for x in range(N)]
    for x in range(N):
        for y in range(N):
            if solid[x][y]:
                continue
            for dx, dy in ((1, 0), (-1, 0), (0, 1), (0, -1)):
                nx, ny = x + dx, y + dy
                if 0 <= nx < N and 0 <= ny < N and solid[nx][ny]:
                    px[x, y] = color
                    break
    return img


# ------------------------------------------------------------------ espadas
S0, S1 = -25, 27  # pomo -> ponta


def u_of(x, y):
    return ((x - y) - S0) / float(S1 - S0)


def q_of(x, y):
    return x + y - 31


def sword(spec):
    """spec: dict com funções blade(u, q, x, y) etc. Cada uma devolve cor ou None."""
    img = new()
    px = img.load()
    for x in range(N):
        for y in range(N):
            u, q = u_of(x, y), q_of(x, y)
            if u < -0.02 or u > 1.02:
                continue
            col = None
            for key in ("deco", "pommel", "grip", "guard", "blade"):
                fn = spec.get(key)
                if fn is None:
                    continue
                col = fn(u, q, x, y)
                if col is not None:
                    break
            if col is not None:
                px[x, y] = col
    outline(img, spec.get("outline", OUTLINE))
    return img


def band(u, a, b):
    return a <= u < b


def blade_basic(r, edge, fuller=None, width=lambda u: 2, start=0.32, tip=0.9, curve=None, light_edge=None):
    """Lâmina com lado claro/escuro, fio e sulco central; afina na ponta."""
    def f(u, q, x, y):
        if u < start or u > 1.0:
            return None
        c = curve(u) if curve else 0
        qq = q - c
        w = width(u)
        if u > tip:
            w = w * max(0.0, (1.0 - u) / (1.0 - tip))
        if abs(qq) > w + 0.01:
            return None
        if qq <= -w + 0.5:
            return light_edge or pick(r, 0.98)
        if qq >= w - 0.5 and w >= 1:
            return edge
        t = 0.62 - qq * 0.16 + (u - 0.6) * 0.15
        if fuller is not None and abs(qq) < 0.5 and start + 0.04 < u < tip - 0.04:
            return fuller
        return pick(r, t)
    return f


def guard_bar(r, half=4, u0=0.28, u1=0.32, gem=None):
    def f(u, q, x, y):
        if not band(u, u0, u1) or abs(q) > half:
            return None
        if gem is not None and abs(q) <= 0.5:
            return gem
        return pick(r, 0.7 - q * 0.08 - (u - u0) * 3)
    return f


def grip_wrap(r, u0=0.09, u1=0.28, half=1, alt=None):
    def f(u, q, x, y):
        if not band(u, u0, u1) or abs(q) > half:
            return None
        k = int((u - u0) * 52)
        if alt is not None and k % 2 == 0:
            return alt
        return pick(r, 0.6 - q * 0.2 + (k % 2) * 0.12)
    return f


def pommel_round(r, u0=0.0, u1=0.09, half=1.5, gem=None):
    def f(u, q, x, y):
        if not band(u, u0, u1) or abs(q) > half:
            return None
        if gem is not None and abs(q) <= 0.5 and band(u, u0 + 0.02, u1 - 0.02):
            return gem
        return pick(r, 0.75 - q * 0.15)
    return f


STEEL = ramp("#2d3340", "#4a5363", "#6c7889", "#95a2b4", "#c3ccd8", "#eef3f8")
DARK = ramp("#120f18", "#1d1826", "#2a2436", "#3a3249", "#4d445f")
GOLD = ramp("#5a3b0e", "#86591a", "#b17f2a", "#d6a640", "#f2cf6a")
SILVER = ramp("#4a4f5a", "#6f7684", "#98a0ae", "#c4cad4", "#eceff3")


def frost_blade():
    ICE = ramp("#2a6f9e", "#3f93c4", "#64b8e2", "#93d8f6", "#c6efff", "#f2fdff")

    def deco(u, q, x, y):
        # pontas de cristal saindo do fio escuro
        if 0.45 < u < 0.85 and q in (3, 4, 5) and int(u * 40) % 5 == 0 and q - 3 <= (0.85 - u) * 8:
            return pick(ICE, 0.8 if q == 3 else 0.55)
        return None

    def guard(u, q, x, y):
        if band(u, 0.25, 0.33) and abs(q) <= 5:
            if abs(q) >= 4 and u > 0.29:
                return pick(ICE, 0.9)
            return pick(ICE, 0.55 - q * 0.06)
        return None

    return sword({
        "deco": deco,
        "blade": blade_basic(ICE, pick(ICE, 0.35), fuller=pick(ICE, 0.45), width=lambda u: 3 if u < 0.62 else 2),
        "guard": guard,
        "grip": grip_wrap(ramp("#0e1a2e", "#18294a", "#233a66", "#2f4c80"), alt=pick(SILVER, 0.7)),
        "pommel": pommel_round(ICE, gem=hexc("#e8fbff")),
    })


def storm_edge():
    BL = ramp("#1f2a3a", "#324257", "#4b6078", "#6d86a0", "#9ab2c8", "#d2e2ee")

    def vein(u, q, x, y):
        if 0.36 < u < 0.92:
            zig = round(math.sin(u * 46) * 0.9)
            if q == zig:
                return hexc("#7ff4ff")
            if q == zig - 1 and h01(x, y) > 0.6:
                return hexc("#d9ffff")
        return None

    def guard(u, q, x, y):
        if band(u, 0.24, 0.33) and abs(q) <= 6:
            if abs(q) >= 5 and u < 0.285:
                return None
            return pick(GOLD, 0.75 - abs(q) * 0.06)
        return None

    return sword({
        "deco": vein,
        "blade": blade_basic(BL, pick(BL, 0.25), width=lambda u: 3 if u < 0.7 else 2),
        "guard": guard,
        "grip": grip_wrap(DARK, alt=pick(GOLD, 0.5)),
        "pommel": pommel_round(GOLD, gem=hexc("#7ff4ff")),
    })


def crimson_fang():
    RED = ramp("#3a0a0e", "#5c1016", "#82191e", "#a8262a", "#cf3d3a", "#f07a6a")
    BONE = ramp("#6b6256", "#8f8573", "#b3a894", "#d6ccb6", "#f0e8d6")

    def guard(u, q, x, y):
        if band(u, 0.26, 0.32) and -5 <= q <= 4:
            return pick(BONE, 0.75 - q * 0.08)
        if band(u, 0.2, 0.26) and q in (-5, -4):
            return pick(BONE, 0.5)
        return None

    return sword({
        "blade": blade_basic(RED, pick(RED, 0.15), width=lambda u: 3 if u < 0.6 else 2,
                             curve=lambda u: round(-((u - 0.32) ** 2) * 6), light_edge=hexc("#ffc7b8")),
        "guard": guard,
        "grip": grip_wrap(ramp("#140608", "#240b0e", "#361217", "#4a1a20")),
        "pommel": pommel_round(BONE, half=1),
    })


def verdant_blade():
    GR = ramp("#123a20", "#1d5a2e", "#2c7d3e", "#45a052", "#6fc36c", "#b8ec9a")
    WOOD = ramp("#2e1d10", "#452c18", "#5f3e22", "#7a522e", "#94683c")

    def vine(u, q, x, y):
        if 0.33 < u < 0.88:
            k = math.sin(u * 30)
            if q == round(k * 3.2):
                if int(u * 100) % 13 == 0:
                    return hexc("#f6a6c8")
                return pick(ramp("#1e4a18", "#2f6a22", "#4a8a2e"), 0.5 + k * 0.3)
        return None

    def guard(u, q, x, y):
        if band(u, 0.25, 0.32) and abs(q) <= 5:
            if abs(q) == 5 and u > 0.28:
                return pick(GR, 0.7)
            return pick(WOOD, 0.65 - q * 0.08)
        return None

    return sword({
        "deco": vine,
        "blade": blade_basic(GR, pick(GR, 0.3), width=lambda u: 3 if u < 0.65 else 2),
        "guard": guard,
        "grip": grip_wrap(WOOD, alt=pick(GR, 0.4)),
        "pommel": pommel_round(WOOD, gem=hexc("#f6a6c8")),
    })


def dusk_blade():
    DK = ramp("#0d0a14", "#17111f", "#22192e", "#2f2341", "#3e2f56")

    def guard(u, q, x, y):
        if band(u, 0.26, 0.32) and abs(q) <= 5:
            if abs(q) >= 4 and u < 0.285:
                return None
            return pick(SILVER, 0.75 - abs(q) * 0.08)
        return None

    return sword({
        "blade": blade_basic(DK, hexc("#c08cff"), width=lambda u: 2 if u < 0.8 else 1,
                             curve=lambda u: round(-((u - 0.32) ** 2) * 3), light_edge=hexc("#e9d6ff")),
        "guard": guard,
        "grip": grip_wrap(ramp("#1c1028", "#2b183e", "#3c2256", "#4f2d70"), u1=0.27, alt=pick(SILVER, 0.6)),
        "pommel": pommel_round(SILVER, half=1, gem=hexc("#c08cff")),
    })


def ancestral_flame():
    FL = ramp("#5a1206", "#8a2008", "#c03a0a", "#e8641a", "#ff9a30", "#ffd56a", "#fff4c0")

    def blade(u, q, x, y):
        if u < 0.32 or u > 1.0:
            return None
        w = 3.0 + math.sin(u * 28) * 1.1
        if u > 0.86:
            w *= max(0, (1.0 - u) / 0.14)
        off = round(math.sin(u * 14) * 0.8)
        qq = q - off
        if abs(qq) > w:
            return None
        core = 1.0 - abs(qq) / max(w, 0.1)
        return pick(FL, 0.25 + core * 0.55 + u * 0.25)

    def guard(u, q, x, y):
        if band(u, 0.26, 0.32) and abs(q) <= 5:
            if abs(q) <= 0.5:
                return hexc("#ff9a30")
            return pick(ramp("#1a1414", "#2c2222", "#433434", "#5c4848"), 0.7 - abs(q) * 0.1)
        return None

    return sword({
        "blade": blade, "guard": guard,
        "grip": grip_wrap(ramp("#200808", "#360c0c", "#4e1212", "#661a18")),
        "pommel": pommel_round(ramp("#1a1414", "#2c2222", "#433434", "#5c4848"), gem=hexc("#ffd56a")),
    })


def sentinel_greatsword():
    def runes(u, q, x, y):
        if 0.42 < u < 0.86 and q == 0 and int(u * 52) % 3 != 0:
            return hexc("#79e9ff") if int(u * 52) % 6 < 3 else hexc("#3aa9d0")
        return None

    def guard(u, q, x, y):
        if band(u, 0.3, 0.37) and abs(q) <= 7:
            if abs(q) >= 6:
                return pick(GOLD, 0.6)
            return pick(ramp("#1c1f27", "#2c313c", "#424956", "#5c6574"), 0.7 - abs(q) * 0.06)
        return None

    return sword({
        "deco": runes,
        "blade": blade_basic(STEEL, pick(STEEL, 0.2), fuller=pick(STEEL, 0.3), width=lambda u: 4 if u < 0.8 else 3,
                             start=0.36, tip=0.88),
        "guard": guard,
        "grip": grip_wrap(ramp("#151216", "#231d22", "#332a30", "#45383f"), u0=0.08, u1=0.3, alt=pick(GOLD, 0.45)),
        "pommel": pommel_round(GOLD, u1=0.08, half=2, gem=hexc("#79e9ff")),
    })


def moonlight_katana():
    MOON = ramp("#3d4a62", "#5d6e8e", "#8698b8", "#b4c4de", "#dbe6f6", "#f8fbff")

    def tsuba(u, q, x, y):
        if band(u, 0.295, 0.335) and abs(q) <= 4:
            return pick(GOLD, 0.8 - abs(q) * 0.1)
        return None

    def grip(u, q, x, y):
        if not band(u, 0.06, 0.3) or abs(q) > 1:
            return None
        k = int((u - 0.06) * 52)
        if (k + q) % 2 == 0:
            return hexc("#e9edf4")
        return hexc("#1a2140")

    return sword({
        "blade": blade_basic(MOON, pick(MOON, 0.3), width=lambda u: 1.5 if u < 0.7 else 1, start=0.335, tip=0.93,
                             curve=lambda u: round(-((u - 0.34) ** 2) * 4)),
        "guard": tsuba, "grip": grip,
        "pommel": pommel_round(GOLD, u0=0.03, u1=0.06, half=1),
    })


SWORDS = {"frost_blade": frost_blade, "storm_edge": storm_edge, "crimson_fang": crimson_fang,
          "verdant_blade": verdant_blade, "dusk_blade": dusk_blade, "ancestral_flame": ancestral_flame,
          "sentinel_greatsword": sentinel_greatsword, "moonlight_katana": moonlight_katana}


# --------------------------------------------------------------- formas
def shape(draw_fn, r, light=0.35, noise=0.12, seed=1):
    """Desenha uma forma (máscara) e sombreia com luz vindo de cima à esquerda."""
    mask = Image.new("L", (N, N), 0)
    draw_fn(ImageDraw.Draw(mask))
    img = new()
    mp, px = mask.load(), img.load()
    for x in range(N):
        for y in range(N):
            if mp[x, y] == 0:
                continue
            t = 0.55 - (x + y - 31) / 62.0 * light * 2
            left = x > 0 and mp[x - 1, y] == 0
            up = y > 0 and mp[x, y - 1] == 0
            right = x < N - 1 and mp[x + 1, y] == 0
            down = y < N - 1 and mp[x, y + 1] == 0
            if left or up:
                t += 0.25
            if right or down:
                t -= 0.2
            t += (h01(x, y, seed) - 0.5) * noise
            px[x, y] = pick(r, t)
    return img


def over(base, top):
    base.alpha_composite(top)
    return base


def echo_fragment():
    R = ramp("#1a2a3a", "#1f4a5a", "#2a7280", "#3fa3a8", "#6fd3c8", "#c8fff0")
    img = shape(lambda d: d.polygon([(16, 2), (23, 11), (21, 25), (14, 30), (9, 20), (10, 9)], fill=255), R, seed=3)
    px = img.load()
    for x in range(N):
        for y in range(N):
            if px[x, y][3] and abs((x - 15) * 0.8 + (y - 15) * 0.25) < 1.2 and 6 < y < 26:
                px[x, y] = hexc("#e9fff8")
            if px[x, y][3] and h01(x, y, 9) > 0.9:
                px[x, y] = hexc("#b08cff")
    return outline(img)


def shadow_essence():
    R = ramp("#0b0812", "#170f24", "#24163a", "#361f56", "#4c2a78", "#7a4cb8", "#c29cff")
    img = shape(lambda d: d.ellipse((7, 9, 25, 27), fill=255), R, seed=4)
    px = img.load()
    for x in range(N):
        for y in range(N):
            if not px[x, y][3]:
                continue
            dx, dy = x - 15, y - 17
            a = math.atan2(dy, dx) + math.hypot(dx, dy) * 0.45
            if math.sin(a * 3) > 0.75:
                px[x, y] = pick(R, 0.85)
    d = ImageDraw.Draw(img)
    for k, (x, y) in enumerate(((14, 6), (17, 4), (12, 3), (19, 7))):
        d.point((x, y), fill=pick(R, 0.5 + k * 0.1))
    return outline(img)


def frost_crystal():
    R = ramp("#245a86", "#3a83b4", "#5eaedc", "#8fd4f4", "#c4ecff", "#f4fdff")
    base = shape(lambda d: d.polygon([(14, 4), (19, 9), (18, 27), (12, 27), (10, 10)], fill=255), R, seed=5)
    a = shape(lambda d: d.polygon([(6, 12), (10, 15), (11, 27), (6, 27), (4, 16)], fill=255), R, seed=6)
    b = shape(lambda d: d.polygon([(23, 13), (27, 17), (25, 27), (20, 27), (20, 17)], fill=255), R, seed=7)
    img = over(over(a, b), base)
    d = ImageDraw.Draw(img)
    d.rectangle((4, 27, 27, 28), fill=hexc("#5a6a7a"))
    return outline(img)


def storm_scale():
    R = ramp("#0e131b", "#18202c", "#232e3e", "#2f3d52", "#41536c", "#5a7090")
    img = shape(lambda d: d.polygon([(16, 3), (26, 10), (25, 21), (16, 29), (7, 21), (6, 10)], fill=255), R, seed=8)
    px = img.load()
    pts = [(16, 6), (14, 11), (18, 15), (14, 20), (17, 25)]
    d = ImageDraw.Draw(img)
    for a, b in zip(pts, pts[1:]):
        d.line([a, b], fill=hexc("#74f2ff"))
    for x in range(N):
        for y in range(N):
            if px[x, y][3] and px[x, y][:3] != (0x74, 0xf2, 0xff) and (y - 3) % 6 == 0:
                px[x, y] = pick(R, 0.75)
    return outline(img)


def guide_book():
    COVER = ramp("#2a160c", "#3d2112", "#542e19", "#6c3d22", "#86502d", "#a06838")
    img = shape(lambda d: d.polygon([(6, 5), (24, 3), (27, 25), (9, 28)], fill=255), COVER, seed=9)
    d = ImageDraw.Draw(img)
    d.line([(9, 28), (27, 25)], fill=hexc("#efe4c8"))
    d.line([(10, 29), (27, 26)], fill=hexc("#cbbd9c"))
    for (x, y) in ((7, 6), (24, 4), (26, 24), (9, 27)):
        d.point((x, y), fill=hexc("#e0b04a"))
    d.ellipse((13, 11, 20, 18), outline=hexc("#d6a640"))
    d.point((16, 14), fill=hexc("#7ff4ff"))
    d.point((17, 14), fill=hexc("#7ff4ff"))
    d.line([(16, 19), (17, 22)], fill=hexc("#d6a640"))
    return outline(img)


def storm_horn():
    BONE = ramp("#4d463c", "#70675a", "#978c7a", "#bdb19c", "#e0d6c2", "#f6efe0")
    img = new()
    d = ImageDraw.Draw(img)
    pts = []
    for k in range(24):
        t = k / 23.0
        x = 6 + t * 20
        y = 25 - math.sin(t * math.pi * 0.9) * 16 - t * 4
        pts.append((x, y, 3.2 * (1 - t) + 0.6))
    for (x, y, r) in pts:
        d.ellipse((x - r, y - r, x + r, y + r), fill=(255, 255, 255, 255))
    px = img.load()
    for x in range(N):
        for y in range(N):
            if px[x, y][3]:
                t = 0.55 - (x + y - 31) / 62.0 * 0.7 + (h01(x, y, 2) - .5) * 0.1
                px[x, y] = pick(BONE, t)
    for k in (5, 11, 17):
        x, y, r = pts[k]
        d.line([(x - r, y + r * 0.3), (x + r * 0.2, y - r)], fill=hexc("#5fd8f0"))
    return outline(img)


def broken_seal():
    R = ramp("#120f16", "#1e1a24", "#2b2534", "#3a3246", "#4c425c")
    img = shape(lambda d: d.ellipse((5, 5, 27, 27), fill=255), R, seed=11)
    d = ImageDraw.Draw(img)
    d.ellipse((9, 9, 23, 23), outline=pick(R, 0.85))
    crack = [(16, 5), (15, 10), (18, 14), (14, 18), (17, 22), (16, 27)]
    d.line(crack, fill=hexc("#b47cff"))
    d.point((16, 14), fill=hexc("#f0e2ff"))
    return outline(img)


def frost_shard():
    R = ramp("#3a83b4", "#5eaedc", "#8fd4f4", "#c4ecff", "#f4fdff")
    img = shape(lambda d: d.polygon([(26, 5), (21, 16), (8, 26), (12, 15)], fill=255), R, seed=12)
    return outline(img, hexc("#1a3a5a"))


def storm_orb():
    img = new()
    px = img.load()
    for x in range(N):
        for y in range(N):
            d = math.hypot(x - 15.5, y - 15.5)
            if d < 10:
                t = 1 - d / 10.0
                px[x, y] = mix(hexc("#1a6a9a"), hexc("#effeff"), t * t + 0.15)
            elif d < 13 and h01(x, y, 3) > 0.8:
                px[x, y] = hexc("#74f2ff")
    return img


def npc_letter(seal):
    PAPER = ramp("#8a7a5a", "#a8977a", "#c4b494", "#dccdb0", "#efe4cc")
    img = shape(lambda d: d.polygon([(4, 9), (28, 7), (28, 23), (4, 25)], fill=255), PAPER, light=0.2, seed=13)
    d = ImageDraw.Draw(img)
    for yy in (12, 15, 18):
        d.line([(8, yy), (21, yy - 1)], fill=hexc("#7a6a4a"))
    sealc = hexc(seal)
    d.ellipse((19, 16, 27, 24), fill=sealc)
    d.point((22, 18), fill=mix(sealc, hexc("#ffffff"), 0.5))
    d.point((23, 19), fill=mix(sealc, hexc("#000000"), 0.4))
    return outline(img)


OTHER = {
    "echo_fragment": echo_fragment, "shadow_essence": shadow_essence, "frost_crystal": frost_crystal,
    "storm_scale": storm_scale, "guide_book": guide_book, "storm_horn": storm_horn,
    "broken_seal": broken_seal, "frost_shard": frost_shard, "storm_orb": storm_orb,
    "npc_bram": lambda: npc_letter("#a8302a"), "npc_iara": lambda: npc_letter("#3f8a4a"),
    "npc_orvyn": lambda: npc_letter("#2f5aa8"), "npc_ravena": lambda: npc_letter("#3a4a2a"),
}
