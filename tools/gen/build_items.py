"""Gera os ícones dos itens e uma folha de prévia."""
import os

from PIL import Image

import items

ROOT = os.path.abspath(os.path.join(os.path.dirname(__file__), "..", ".."))
TEX = os.path.join(ROOT, "src/main/resources/assets/valdren/textures/item")
PREV = os.path.join(ROOT, "docs/preview")


def main():
    os.makedirs(TEX, exist_ok=True)
    os.makedirs(PREV, exist_ok=True)
    allitems = list(items.SWORDS.items()) + list(items.OTHER.items())
    cols = 7
    rows = (len(allitems) + cols - 1) // cols
    sheet = Image.new("RGBA", (cols * 144, rows * 144), (30, 30, 38, 255))
    for k, (name, fn) in enumerate(allitems):
        img = fn()
        img.save(os.path.join(TEX, name + ".png"))
        big = img.resize((128, 128), Image.NEAREST)
        sheet.alpha_composite(big, ((k % cols) * 144 + 8, (k // cols) * 144 + 8))
    sheet.save(os.path.join(PREV, "items.png"))
    print("ok", len(allitems), "itens")


if __name__ == "__main__":
    main()
