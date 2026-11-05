"""Gera as skins 64x64 (formato de jogador) do Saqueador e do Cavaleiro usadas quando o Epic Fight desenha esses mobs."""
import os

from PIL import Image

from boxmodel import Cube, Part, Model
import skins

ROOT = os.path.abspath(os.path.join(os.path.dirname(__file__), "..", ".."))
TEX = os.path.join(ROOT, "src/main/resources/assets/valdren/textures/entity")
PREV = os.path.join(ROOT, "docs/preview")


def player_model():
    def cube(part, name, x, y, z, w, h, d, infl=0.0):
        c = Cube(x, y, z, w, h, d, inflate=infl)
        c.uv = skins.PARTS[name][:2]
        part.cubes.append(c)

    head = Part("head", (0, 0, 0)); cube(head, "head", -4, -8, -4, 8, 8, 8); cube(head, "hat", -4, -8, -4, 8, 8, 8, 0.5)
    body = Part("body", (0, 0, 0)); cube(body, "body", -4, 0, -2, 8, 12, 4); cube(body, "jacket", -4, 0, -2, 8, 12, 4, 0.25)
    ra = Part("ra", (-5, 2, 0)); cube(ra, "rarm", -3, -2, -2, 4, 12, 4); cube(ra, "rsleeve", -3, -2, -2, 4, 12, 4, 0.25)
    la = Part("la", (5, 2, 0)); cube(la, "larm", -1, -2, -2, 4, 12, 4); cube(la, "lsleeve", -1, -2, -2, 4, 12, 4, 0.25)
    rl = Part("rl", (-1.9, 12, 0)); cube(rl, "rleg", -2, 0, -2, 4, 12, 4); cube(rl, "rpants", -2, 0, -2, 4, 12, 4, 0.25)
    ll = Part("ll", (1.9, 12, 0)); cube(ll, "lleg", -2, 0, -2, 4, 12, 4); cube(ll, "lpants", -2, 0, -2, 4, 12, 4, 0.25)
    return Model("player", 64, 64, [head, body, ra, la, rl, ll])


def main():
    os.makedirs(os.path.join(TEX, "npc"), exist_ok=True)
    os.makedirs(PREV, exist_ok=True)
    pm = player_model()
    # Só as skins no formato de jogador usadas pelo Epic Fight (os NPCs agora têm modelo próprio, ver build_v2.py)
    jobs = [("crimson_raider", skins.raider(), None)]
    kspec, kglow = skins.knight()
    jobs.append(("lightless_knight", kspec, kglow))
    sheet = Image.new("RGB", (240 * len(jobs), 480), (34, 36, 44))
    for k, (name, spec, glow) in enumerate(jobs):
        img, gimg = skins.paint(spec, seed=k + 1, glow_spec=glow)
        img.save(os.path.join(TEX, name + ".png"))
        # (o Epic Fight não usa camada de brilho)
        sheet.paste(pm.preview(img, size=240, yaw=25, pitch=8), (240 * k, 0))
        sheet.paste(pm.preview(img, size=240, yaw=200, pitch=8), (240 * k, 240))
        print("ok", name)
    sheet.save(os.path.join(PREV, "epicfight_skins.png"))


if __name__ == "__main__":
    main()
