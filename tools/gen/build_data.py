"""Gera os JSONs do mod: idiomas, modelos de item, receitas, loot, spawns e compatibilidade Epic Fight."""
import json
import os

import lang_en
import lang_pt

ROOT = os.path.abspath(os.path.join(os.path.dirname(__file__), "..", ".."))
RES = os.path.join(ROOT, "src/main/resources")
A = os.path.join(RES, "assets/valdren")
D = os.path.join(RES, "data/valdren")

SWORDS = {
    # nome: (tipo Epic Fight, impacto, golpes máx, perfuração de armadura)
    "frost_blade": ("epicfight:longsword", 2.2, 2, 0.0),
    "storm_edge": ("epicfight:longsword", 2.5, 2, 10.0),
    "crimson_fang": ("epicfight:sword", 1.0, 1, 0.0),
    "verdant_blade": ("epicfight:sword", 1.0, 1, 0.0),
    "dusk_blade": ("epicfight:tachi", 2.0, 2, 0.0),
    "ancestral_flame": ("epicfight:sword", 1.2, 1, 5.0),
    "sentinel_greatsword": ("epicfight:greatsword", 4.5, 4, 15.0),
    "moonlight_katana": ("epicfight:uchigatana", 0.8, 1, 0.0),
}
FLAT_ITEMS = ["echo_fragment", "shadow_essence", "frost_crystal", "storm_scale", "guide_book", "storm_horn",
              "broken_seal", "frost_shard", "storm_orb", "npc_bram", "npc_iara", "npc_orvyn", "npc_ravena"]
EGGS = ["shadow_wolf", "frost_wraith", "crimson_raider", "lightless_knight", "storm_dragon"]


def write(path, obj):
    os.makedirs(os.path.dirname(path), exist_ok=True)
    with open(path, "w", encoding="utf-8") as f:
        json.dump(obj, f, ensure_ascii=False, indent=2)
        f.write("\n")


def item(name):
    return {"item": name}


def count(n, mn, mx):
    return {"function": "minecraft:set_count", "count": {"type": "minecraft:uniform", "min": mn, "max": mx}}


def looting(mx):
    return {"function": "minecraft:looting_enchant", "count": {"type": "minecraft:uniform", "min": 0, "max": mx}}


def entry(name, functions=None):
    e = {"type": "minecraft:item", "name": name}
    if functions:
        e["functions"] = functions
    return e


def pool(entries, rolls=1, conditions=None):
    p = {"rolls": rolls, "bonus_rolls": 0, "entries": entries}
    if conditions:
        p["conditions"] = conditions
    return p


def chance(c, per_looting):
    return [{"condition": "minecraft:killed_by_player"},
            {"condition": "minecraft:random_chance_with_looting", "chance": c, "looting_multiplier": per_looting}]


def main():
    pt, en = lang_pt.build(), lang_en.build()
    missing = sorted(set(pt) ^ set(en))
    if missing:
        raise SystemExit("chaves diferentes entre pt_br e en_us: " + ", ".join(missing))
    write(os.path.join(A, "lang/pt_br.json"), dict(sorted(pt.items())))
    write(os.path.join(A, "lang/en_us.json"), dict(sorted(en.items())))

    for s in SWORDS:
        write(os.path.join(A, f"models/item/{s}.json"),
              {"parent": "minecraft:item/handheld", "textures": {"layer0": f"valdren:item/{s}"}})
    for s in FLAT_ITEMS:
        write(os.path.join(A, f"models/item/{s}.json"),
              {"parent": "minecraft:item/generated", "textures": {"layer0": f"valdren:item/{s}"}})
    for e in EGGS:
        write(os.path.join(A, f"models/item/{e}_spawn_egg.json"), {"parent": "minecraft:item/template_spawn_egg"})

    # Epic Fight: moveset das espadas (só é lido com o Epic Fight instalado)
    for s, (kind, impact, strikes, neg) in SWORDS.items():
        common = {"impact": impact, "max_strikes": strikes}
        if neg:
            common["armor_negation"] = neg
        write(os.path.join(D, f"capabilities/weapons/{s}.json"), {"attributes": {"common": common}, "type": kind})
    # Epic Fight: inimigos humanoides usam o preset de zumbi (golpes animados com a arma na mão)
    for mob in ("crimson_raider", "lightless_knight"):
        write(os.path.join(D, f"epicfight_mobpatch/{mob}.json"), {"preset": "minecraft:zombie"})

    # receitas
    write(os.path.join(D, "recipes/frost_blade.json"), {
        "type": "minecraft:crafting_shaped", "category": "equipment",
        "pattern": [" C ", " D ", " S "],
        "key": {"C": item("valdren:frost_crystal"), "D": item("minecraft:diamond"), "S": item("minecraft:stick")},
        "result": {"item": "valdren:frost_blade"}})
    write(os.path.join(D, "recipes/crimson_fang.json"), {
        "type": "minecraft:crafting_shaped", "category": "equipment",
        "pattern": [" E ", " R ", " S "],
        "key": {"E": item("valdren:shadow_essence"), "R": item("minecraft:redstone_block"), "S": item("minecraft:stick")},
        "result": {"item": "valdren:crimson_fang"}})
    write(os.path.join(D, "recipes/broken_seal.json"), {
        "type": "minecraft:crafting_shapeless", "category": "misc",
        "ingredients": [item("valdren:echo_fragment")] * 4 + [item("valdren:shadow_essence")] * 2 + [item("minecraft:iron_ingot")],
        "result": {"item": "valdren:broken_seal"}})
    write(os.path.join(D, "recipes/guide_book.json"), {
        "type": "minecraft:crafting_shapeless", "category": "misc",
        "ingredients": [item("minecraft:book"), item("minecraft:feather")],
        "result": {"item": "valdren:guide_book"}})

    # loot
    echo = lambda c: pool([entry("valdren:echo_fragment")], conditions=chance(c, 0.03))
    write(os.path.join(D, "loot_tables/entities/shadow_wolf.json"), {"type": "minecraft:entity", "pools": [
        pool([entry("valdren:shadow_essence", [count(1, 0, 2), looting(1)])]), echo(0.15)]})
    write(os.path.join(D, "loot_tables/entities/frost_wraith.json"), {"type": "minecraft:entity", "pools": [
        pool([entry("valdren:frost_crystal", [count(1, 0, 2), looting(1)])]), echo(0.15)]})
    write(os.path.join(D, "loot_tables/entities/crimson_raider.json"), {"type": "minecraft:entity", "pools": [
        pool([entry("minecraft:emerald", [count(1, 0, 2), looting(1)])]), echo(0.25)]})
    write(os.path.join(D, "loot_tables/entities/lightless_knight.json"), {"type": "minecraft:entity", "pools": [
        pool([entry("valdren:sentinel_greatsword")]),
        pool([entry("valdren:echo_fragment", [count(1, 4, 6)])]),
        pool([entry("minecraft:diamond", [count(1, 2, 4)])])]})
    write(os.path.join(D, "loot_tables/entities/storm_dragon.json"), {"type": "minecraft:entity", "pools": [
        pool([entry("valdren:storm_edge")]),
        pool([entry("valdren:storm_scale", [count(1, 6, 10)])]),
        pool([entry("minecraft:diamond", [count(1, 4, 8)])])]})

    # spawns naturais (Forge biome modifiers)
    spawns = [
        ("shadow_wolf_forest", "#minecraft:is_forest", "valdren:shadow_wolf", 25, 1, 3),
        ("shadow_wolf_taiga", "#minecraft:is_taiga", "valdren:shadow_wolf", 25, 1, 3),
        ("frost_wraith_snowy", "#forge:is_snowy", "valdren:frost_wraith", 30, 1, 2),
        ("crimson_raider_plains", "#forge:is_plains", "valdren:crimson_raider", 18, 1, 3),
        ("crimson_raider_savanna", "#minecraft:is_savanna", "valdren:crimson_raider", 18, 1, 3),
    ]
    for name, biomes, mob, weight, mn, mx in spawns:
        write(os.path.join(D, f"forge/biome_modifier/{name}.json"), {
            "type": "forge:add_spawns", "biomes": biomes,
            "spawners": {"type": mob, "weight": weight, "minCount": mn, "maxCount": mx}})
    print("ok:", len(pt), "textos por idioma")


if __name__ == "__main__":
    main()
