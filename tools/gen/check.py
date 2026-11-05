"""Verificações do projeto (rode antes de enviar): itens, texturas, idiomas e JSONs."""
import glob
import json
import os
import re
import sys

ROOT = os.path.abspath(os.path.join(os.path.dirname(__file__), "..", ".."))
J = os.path.join(ROOT, "src/main/java/com/frostkl4/valdren")
R = os.path.join(ROOT, "src/main/resources")
A = os.path.join(R, "assets/valdren")
errors = []

java = {p: open(p, encoding="utf-8").read() for p in glob.glob(J + "/**/*.java", recursive=True)}
items_src = java[os.path.join(J, "registry/ModItems.java")]
items = re.findall(r'(?:ITEMS\.register|sword)\("([a-z_]+)"', items_src)
items = [i for i in items if not i.endswith("_")] + ["npc_" + n for n in ("bram", "iara", "orvyn", "ravena")]
ents = re.findall(r'ENTITIES\.register\("([a-z_]+)"', java[os.path.join(J, "registry/ModEntities.java")])

for f in glob.glob(R + "/**/*.json", recursive=True) + glob.glob(R + "/**/*.mcmeta", recursive=True):
    try:
        json.load(open(f, encoding="utf-8"))
    except Exception as e:
        errors.append(f"JSON inválido {f}: {e}")

langs = {n: json.load(open(os.path.join(A, f"lang/{n}.json"), encoding="utf-8")) for n in ("pt_br", "en_us")}
for n, lang in langs.items():
    for it in items:
        if f"item.valdren.{it}" not in lang:
            errors.append(f"[{n}] falta nome do item {it}")
    for e in ents:
        if f"entity.valdren.{e}" not in lang:
            errors.append(f"[{n}] falta nome da entidade {e}")
    for src in java.values():
        for key in re.findall(r'"(valdren\.[a-z0-9_.]+[a-z0-9_])"', src):
            if key not in lang and not key.endswith(".") and key.count(".") > 1:
                errors.append(f"[{n}] chave usada no Java e ausente: {key}")
    suffixes = ["name", "role", "greet.0", "greet.1", "greet.2", "greet.friend", "greet.ready", "opt.quests",
                "opt.story", "opt.affinity", "opt.trade", "opt.bye", "more", "decline", "abandon", "notyet",
                "noquest", "questlocked", "story.0", "story.1", "story.2", "story.3", "story.locked"] + \
               [f"affinity.{t}" for t in ("stranger", "known", "comrade", "friend", "confidant")]
    for npc in ("bram", "iara", "orvyn", "ravena"):
        for s in suffixes:
            if f"valdren.npc.{npc}.{s}" not in lang:
                errors.append(f"[{n}] NPC {npc} sem {s}")
    for q in re.findall(r'Quest\.(?:kill|collect)\("([a-z_]+)"', java[os.path.join(J, "quest/Quests.java")]):
        for s in ("title", "offer", "accept", "progress", "ready", "done"):
            if f"valdren.quest.{q}.{s}" not in lang:
                errors.append(f"[{n}] missão {q} sem {s}")
    pages = int(re.search(r"GUIDE_PAGES = (\d+)", java[os.path.join(J, "client/ClientHooks.java")]).group(1))
    for i in range(pages):
        if f"valdren.guide.page.{i}" not in lang:
            errors.append(f"[{n}] guia sem página {i}")
        else:
            page = re.sub("§.", "", lang[f"valdren.guide.page.{i}"])
            if len(page) > 260:
                errors.append(f"[{n}] página {i} do guia longa demais ({len(page)} letras)")

for it in items:
    m = os.path.join(A, f"models/item/{it}.json")
    if not os.path.exists(m):
        errors.append(f"falta modelo do item {it}")
        continue
    tex = json.load(open(m)).get("textures", {}).get("layer0")
    if tex and not os.path.exists(os.path.join(A, "textures", tex.split(":")[1] + ".png")):
        errors.append(f"falta textura {tex}")
for t in re.findall(r'"textures/entity/([a-z_/]+\.png)"', "\n".join(java.values())):
    if not os.path.exists(os.path.join(A, "textures/entity", t)):
        errors.append(f"falta textura de entidade {t}")
for npc in ("bram", "iara", "orvyn", "ravena"):
    if not os.path.exists(os.path.join(A, f"textures/entity/npc/{npc}.png")):
        errors.append(f"falta skin do NPC {npc}")
for s in re.findall(r'sword\("([a-z_]+)"', items_src):
    if not os.path.exists(os.path.join(R, f"data/valdren/capabilities/weapons/{s}.json")):
        errors.append(f"espada {s} sem arquivo do Epic Fight")

print("\n".join(errors) if errors else f"tudo certo: {len(items)} itens, {len(ents)} entidades, {len(langs['pt_br'])} textos")
sys.exit(1 if errors else 0)
