"""Prévia do Vorthrax v4 com textura, fundo escuro e brilho (bloom aproximado das partes emissivas)."""
import sys
import time

from PIL import Image, ImageChops, ImageDraw, ImageFilter, ImageFont

import v4_dragon as D
from v4_paint import painter

KEY = (255, 0, 255)


def backdrop(size):
    bg = Image.new("RGB", (size, size))
    px = bg.load()
    c = size / 2
    for y in range(size):
        for x in range(size):
            d = min(1.0, (((x - c) ** 2 + (y - c * 0.8) ** 2) ** 0.5) / (size * 0.75))
            px[x, y] = (int(36 - 26 * d), int(52 - 36 * d), int(62 - 42 * d))
    return bg


def render(m, tex, gfull, size, yaw, pitch, pose, scale=None, glow=True):
    im = m.preview(tex, size=size, yaw=yaw, pitch=pitch, pose=pose, bg=KEY, lit=True, scale=scale)
    mask = Image.eval(ImageChops.difference(im, Image.new("RGB", im.size, KEY)).convert("L"),
                      lambda v: 255 if v > 6 else 0)
    out = backdrop(size)
    out.paste(im, (0, 0), mask)
    if glow:
        g = m.preview(gfull, size=size, yaw=yaw, pitch=pitch, pose=pose, bg=(0, 0, 0), lit="flat", scale=scale)
        out = ImageChops.add(out, g.filter(ImageFilter.GaussianBlur(size / 90)))
        out = ImageChops.add(out, Image.eval(g.filter(ImageFilter.GaussianBlur(size / 30)), lambda v: int(v * 0.45)))
        out = ImageChops.lighter(out, g)
    return out


def label(img, text):
    d = ImageDraw.Draw(img)
    try:
        f = ImageFont.truetype("/usr/share/fonts/truetype/dejavu/DejaVuSans.ttf", 22)
    except Exception:
        f = ImageFont.load_default()
    d.text((16, 12), text, fill=(210, 222, 232), font=f)
    return img


def textures():
    m = D.dragon_v4()
    tex, glow = m.paint(painter, glow=True)
    gfull = Image.new("RGBA", tex.size, (0, 0, 0, 255))
    gfull.paste(glow, (0, 0), glow)
    return m, tex, glow, gfull


def main(out, hero_out=None):
    t0 = time.time()
    m, tex, glow, gfull = textures()
    S = 600
    views = [("3/4 frente", 32, 14, D.POSE_GROUND), ("perfil", 90, 4, D.POSE_GROUND),
             ("3/4 trás", 150, 16, D.POSE_GROUND), ("frente", -8, 6, D.POSE_GROUND),
             ("voo", -28, 26, D.POSE_FLY), ("voo (de baixo)", 20, -35, D.POSE_FLY)]
    sheet = Image.new("RGB", (S * 3, S * 2))
    for k, (name, yaw, pitch, pose) in enumerate(views):
        sheet.paste(label(render(m, tex, gfull, S, yaw, pitch, pose), name), ((k % 3) * S, (k // 3) * S))
    sheet.save(out)
    if hero_out:
        render(m, tex, gfull, 900, -35, 8, D.POSE_GROUND).save(hero_out)
    print(f"{sum(1 for _ in m.cubes())} cubos, {sum(1 for _ in m.parts())} ossos, {time.time() - t0:.0f}s")


if __name__ == "__main__":
    main(sys.argv[1], sys.argv[2] if len(sys.argv) > 2 else None)
