"""Pintura v2: estilo "Blockbench de fantasia" (faixas de cor limpas, bisel nas bordas dos cubos,
frisos com brilho, dobras de tecido, mechas de cabelo). Pouco ruído, contraste alto.

Cada cubo tem um material (cube.mat) e decorações opcionais em cube.tag:
  rows : [(y0, y1, mat)]          faixas horizontais (em texels a partir do topo do cubo)
  cols : [(x0, x1, mat, faces)]   faixas verticais nas faces indicadas (x a partir da esquerda da face)
  grid : {face: [linhas]}  +  pal : {char: mat ou cor}   desenho por caracteres ('.' = segue, ' ' = vazio)
  cut  : "open_front" (frente vazada), "rag" (barra rasgada), "rag2" (mais rasgada)
  emit : True  (o cubo inteiro brilha)
"""
import math

from pix import pick, pick_dither, h01, vnoise, mix

FACE_L = {"top": 0.17, "north": 0.0, "south": -0.1, "west": -0.04, "east": -0.07, "bottom": -0.3}


class M:
    def __init__(self, ramp, kind="cloth", base=0.55, grad=0.2, bevel=True, noise=0.03, glow=False,
                 folds=3, dither=True, axis=None):
        self.ramp, self.kind, self.base, self.grad = ramp, kind, base, grad
        self.bevel, self.noise, self.glow, self.folds, self.dither, self.axis = bevel, noise, glow, folds, dither, axis


def _coord(c):
    """Coordenada horizontal do texel no mundo do modelo (para dobras contínuas entre cubos)."""
    if c.face in ("north", "south"):
        return c.gp[0]
    if c.face in ("east", "west"):
        return c.gp[2]
    return c.gp[0] + c.gp[2]


def shade(m, c):
    t = m.base + FACE_L[c.face]
    if c.is_side:
        t += (0.5 - c.vfrac) * m.grad
    if m.bevel and c.fw >= 2 and c.fh >= 2:
        if c.is_side:
            if c.j == 0:
                t += 0.12
            elif c.j == c.fh - 1:
                t -= 0.12
            if c.i == 0 or c.i == c.fw - 1:
                t -= 0.05
        elif c.face == "top" and c.edge == 0:
            t -= 0.05
    k = m.kind
    if k == "cloth":
        if c.is_side and int(math.floor(_coord(c))) % m.folds == 0:
            t -= 0.08
    elif k in ("metal", "trim"):
        if c.is_side and c.j == 1 and c.fh > 3:
            t += 0.2 if k == "metal" else 0.25
        if c.is_side and c.j >= c.fh - 2:
            t -= 0.06
        if h01(c.i, c.j, int(c.gp[0] * 3), int(c.gp[2] * 3), 7) > 0.975:
            t += 0.35
    elif k == "fur":
        ph = (c.i * 5 + int(c.gp[1] * 0.5) * 3) % 4
        t += (0.08 if ph == 0 else (-0.06 if ph == 2 else 0.0))
    elif k == "hair":
        if c.is_side:
            ph = (int(math.floor(_coord(c))) * 3 + (c.j // 3)) % 4
            t += (0.11 if ph == 0 else (-0.07 if ph == 2 else 0.0))
        else:
            t += ((c.i + c.j) % 3 == 0) * 0.08
    elif k == "leather":
        if c.is_side and c.edge == 1 and c.fh > 4 and c.fw > 3 and (c.i + c.j) % 2 == 0:
            t += 0.16
    elif k == "scale":
        row = int(math.floor(c.gp[1] / 2.0))
        off = (row % 2) * 2
        ci = int(math.floor(_coord(c) + off)) % 4
        cj = int(math.floor(c.gp[1])) % 2
        if cj == 0 and ci in (1, 2):
            t += 0.12
        if cj == 1 and ci == 3:
            t -= 0.15
    elif k == "wood":
        if c.is_side and (c.j + int(_coord(c) * 0.3)) % 3 == 0:
            t -= 0.08
    elif k == "along":
        # bone/chifre: claro na ponta (eixo z do cubo, ou y se axis='y')
        ax = (m.axis or "z").lstrip("-")
        k_ = "xyz".index(ax)
        f = (c.lp[k_] - c.cube.o[k_]) / max(1, c.cube.s[k_])
        if (m.axis or "").startswith("-"):
            f = 1.0 - f
        t += f * 0.45 - 0.15
    t += (h01(c.i, c.j, int(c.gp[0] * 7), int(c.gp[1] * 5), int(c.gp[2] * 3)) - 0.5) * m.noise
    if m.dither:
        return pick_dither(m.ramp, t, c.i + int(c.gp[0]), c.j + int(c.gp[2]))
    return pick(m.ramp, t)


def _rows_mat(tag, c):
    for y0, y1, mn in tag.get("rows", ()):
        if c.is_side and y0 <= c.j < y1:
            return mn
        if c.face == "top" and y0 <= 0:
            return mn
        if c.face == "bottom" and y1 >= c.cube.s[1]:
            return mn
    return None


def _cols_mat(tag, c):
    for x0, x1, mn, faces in tag.get("cols", ()):
        if c.face in faces and x0 <= c.i < x1:
            return mn
    return None


def make_painter(mats, colors=None):
    colors = colors or {}

    def paint(c):
        tag = c.cube.tag
        fn = tag.get("fn")
        if fn is not None:
            r = fn(c)
            if r is not None:
                return r
        cut = tag.get("cut")
        if cut == "open_front" and c.face == "north" and c.edge >= 1 and c.j >= 1:
            return (0, 0, 0, 0)
        if cut in ("rag", "rag2") and c.is_side and c.j >= c.fh - (2 if cut == "rag" else 3):
            limit = 0.5 if cut == "rag" else 0.35
            depth = c.fh - c.j
            if h01(int(c.gp[0] * 4), int(c.gp[2] * 4), c.i, 17) > limit + depth * 0.12:
                return (0, 0, 0, 0)
        mn = c.cube.mat
        grid = tag.get("grid", {}).get(c.face)
        if grid is not None:
            row = grid[c.j] if c.j < len(grid) else ""
            ch = row[c.i] if c.i < len(row) else "."
            if ch == " ":
                return (0, 0, 0, 0)
            if ch != ".":
                v = tag["pal"][ch]
                if isinstance(v, tuple):
                    return (v, v) if tag.get("emit") or ch in tag.get("glowchars", "") else v
                mn = v
                m = mats[mn]
                col = shade(m, c)
                return (col, col) if (m.glow or ch in tag.get("glowchars", "")) else col
        mn = _cols_mat(tag, c) or _rows_mat(tag, c) or mn
        m = mats[mn]
        col = shade(m, c)
        if m.glow or tag.get("emit"):
            return col, col
        return col

    return paint
