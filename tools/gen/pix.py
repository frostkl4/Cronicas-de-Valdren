"""Utilidades de pixel art: cores, rampas de paleta e ruido deterministico."""
import math


def hexc(h, a=255):
    h = h.lstrip("#")
    return (int(h[0:2], 16), int(h[2:4], 16), int(h[4:6], 16), a)


def ramp(*hexes):
    return [hexc(h) for h in hexes]


def pick(r, t):
    """Escolhe uma cor da rampa (0 = mais escuro, 1 = mais claro)."""
    t = max(0.0, min(0.9999, t))
    return r[int(t * len(r))]


def pick_dither(r, t, x, y):
    """Como pick, mas com dithering ordenado 2x2 nas transicoes (visual pixel art)."""
    n = len(r)
    f = max(0.0, min(0.9999, t)) * n
    idx = int(f)
    frac = f - idx
    bayer = (0.25, 0.75, 1.0, 0.5)[(x & 1) + 2 * (y & 1)]
    if frac > 0.62 * bayer + 0.25 and idx < n - 1:
        idx += 1
    return r[idx]


def h01(*args):
    """Hash deterministico -> [0, 1)."""
    n = 2166136261
    for a in args:
        n ^= (int(a) * 16777619) & 0xFFFFFFFF
        n = (n * 16777619) & 0xFFFFFFFF
        n ^= n >> 13
    n = (n * 0x5bd1e995) & 0xFFFFFFFF
    n ^= n >> 15
    return (n & 0xFFFFFF) / float(0x1000000)


def vnoise(x, y, z=0.0, s=4.0, seed=0):
    """Ruido de valor suave 3D."""
    x, y, z = x / s, y / s, z / s
    xi, yi, zi = math.floor(x), math.floor(y), math.floor(z)
    xf, yf, zf = x - xi, y - yi, z - zi

    def f(t):
        return t * t * (3 - 2 * t)

    u, v, w = f(xf), f(yf), f(zf)
    acc = 0.0
    for dx in (0, 1):
        for dy in (0, 1):
            for dz in (0, 1):
                wgt = (u if dx else 1 - u) * (v if dy else 1 - v) * (w if dz else 1 - w)
                acc += wgt * h01(xi + dx, yi + dy, zi + dz, seed)
    return acc


def fbm(x, y, z=0.0, s=6.0, seed=0, oct=3):
    a, tot, amp = 0.0, 0.0, 1.0
    for o in range(oct):
        a += amp * vnoise(x, y, z, s / (2 ** o), seed + o * 31)
        tot += amp
        amp *= 0.5
    return a / tot


def mix(a, b, t):
    t = max(0.0, min(1.0, t))
    return tuple(int(a[k] + (b[k] - a[k]) * t) for k in range(len(a)))


def shade(c, f):
    return (min(255, int(c[0] * f)), min(255, int(c[1] * f)), min(255, int(c[2] * f)), c[3] if len(c) > 3 else 255)


def clamp01(t):
    return max(0.0, min(1.0, t))
