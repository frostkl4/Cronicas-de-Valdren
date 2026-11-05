"""Esboço da forma (blockout) do Vorthrax v3: cinza, sem textura, para aprovar a silhueta."""
import sys

from PIL import Image, ImageDraw, ImageFont

from boxmodel import Model
import v3_dragon as D

CLAY = {"scale": (150, 152, 158), "scale_d": (124, 126, 133), "plate": (184, 186, 192), "horn": (206, 199, 183),
        "claw": (92, 92, 98), "teeth": (236, 233, 226), "eye": (120, 240, 255), "crys": (140, 226, 246),
        "memb": (96, 102, 116), "bone": (140, 142, 150), "spine": (134, 136, 144), "dark": (30, 30, 35),
        "tongue": (150, 92, 96)}
BG = (52, 56, 68)


def painter(c):
    return CLAY.get(c.cube.mat, (150, 150, 150))


def label(img, text):
    d = ImageDraw.Draw(img)
    try:
        f = ImageFont.truetype("/usr/share/fonts/truetype/dejavu/DejaVuSans.ttf", 22)
    except Exception:
        f = ImageFont.load_default()
    d.text((16, 12), text, fill=(220, 222, 230), font=f)
    return img


def find(part, name):
    if part.name == name:
        return part
    for ch in part.children:
        r = find(ch, name)
        if r:
            return r
    return None


def main(out):
    m = D.dragon_v3()
    tex, _ = m.paint(painter)
    n = sum(1 for _ in m.cubes())
    bones = sum(1 for _ in m.parts())
    S = 640
    views = [("3/4 frente", 32, 16, D.POSE_GROUND), ("perfil", 90, 4, D.POSE_GROUND),
             ("3/4 trás", 150, 18, D.POSE_GROUND), ("de cima", 20, 62, D.POSE_GROUND),
             ("voo", -28, 26, D.POSE_FLY), ("frente baixa", -12, 2, D.POSE_GROUND)]
    sheet = Image.new("RGB", (S * 3, S * 2 + S), BG)
    for k, (name, yaw, pitch, pose) in enumerate(views):
        im = m.preview(tex, size=S, yaw=yaw, pitch=pitch, pose=pose, bg=BG, lit=True)
        sheet.paste(label(im, name), ((k % 3) * S, (k // 3) * S))
    body = m.roots[0]
    head = find(body, "head")
    hm = Model("head", m.tw, m.th, [head])
    sheet.paste(label(hm.preview(tex, size=S, yaw=35, pitch=12, bg=BG, lit=True), "close: cabeça"), (0, 2 * S))
    sheet.paste(label(hm.preview(tex, size=S, yaw=95, pitch=5, bg=BG, lit=True), "close: cabeça (perfil)"), (S, 2 * S))
    wing = find(body, "wing_l")
    wm = Model("wing", m.tw, m.th, [wing])
    sheet.paste(label(wm.preview(tex, size=S, yaw=180, pitch=75, bg=BG, lit=True), "close: asa aberta (de cima)"),
                (2 * S, 2 * S))
    sheet.save(out)
    print(f"{n} cubos, {bones} ossos")


if __name__ == "__main__":
    main(sys.argv[1] if len(sys.argv) > 1 else "/tmp/blockout.png")
