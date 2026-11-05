"""Reformulação visual v2: gera modelos Java, texturas, prévias e .bbmodel de todos os personagens.

  python3 build_v2.py           (gera tudo)
"""
import os
import sys

from PIL import Image

from boxmodel import save_bbmodel, camel
from stylepaint import make_painter
import v2_bosses as B
import v2_creatures as C
import v2_npcs as N

ROOT = os.path.abspath(os.path.join(os.path.dirname(__file__), "..", ".."))
JAVA = os.path.join(ROOT, "src/main/java/com/frostkl4/valdren/client/model")
TEX = os.path.join(ROOT, "src/main/resources/assets/valdren/textures/entity")
BB = os.path.join(ROOT, "tools/models")
PREV = os.path.join(ROOT, "docs/preview")

CREATURE_TEMPLATE = """package com.frostkl4.valdren.client.model;

import com.frostkl4.valdren.Valdren;
import com.frostkl4.valdren.entity.{pkg}.{entity};
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

/**
 * Modelo gerado por tools/gen/build_v2.py (geometria em tools/gen/v2_bosses.py e v2_creatures.py);
 * o arquivo tools/models/{name}.bbmodel abre no Blockbench (formato Modded Entity).
 */
public class {cls} extends HierarchicalModel<{entity}> {{
    public static final ModelLayerLocation LAYER =
            new ModelLayerLocation(new ResourceLocation(Valdren.MODID, "{name}"), "main");

    private final ModelPart root;
{fields}

    public {cls}(ModelPart root) {{
        this.root = root;
{assign}
    }}

    public static LayerDefinition createBodyLayer() {{
{layer}
    }}

    @Override
    public ModelPart root() {{
        return this.root;
    }}

    @Override
    public void setupAnim({entity} entity, float limbSwing, float limbSwingAmount, float ageInTicks,
                          float netHeadYaw, float headPitch) {{
        this.root().getAllParts().forEach(ModelPart::resetPose);
{anim}
    }}
}}
"""

HUMANOID_TEMPLATE = """package com.frostkl4.valdren.client.model;

import com.frostkl4.valdren.Valdren;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;

/**
 * Modelo humanoide gerado por tools/gen/build_v2.py (geometria em tools/gen/{source}).
 * Usa os ossos do HumanoidModel (andar, atacar, segurar itens) e acrescenta peças próprias
 * (roupas, cabelo, acessórios) que têm animação extra em setupAnim.
 * O arquivo tools/models/{name}.bbmodel abre no Blockbench.
 */
public class {cls}<T extends LivingEntity> extends HumanoidModel<T> {{
    public static final ModelLayerLocation LAYER =
            new ModelLayerLocation(new ResourceLocation(Valdren.MODID, "{name}"), "main");

{fields}

    public {cls}(ModelPart root) {{
        super(root);
{assign}
    }}

    public static LayerDefinition createBodyLayer() {{
{layer}
    }}

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks,
                          float netHeadYaw, float headPitch) {{
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
{reset}
{anim}
    }}
}}
"""


def emit(model, mats, cls, parts, anim, poses, humanoid, entity=None, pkg=None, source="", tex_dir=TEX,
         tex_name=None):
    tex, glow = model.paint(make_painter(mats), glow=True)
    tname = tex_name or model.name
    os.makedirs(os.path.dirname(os.path.join(tex_dir, tname)), exist_ok=True)
    tex.save(os.path.join(tex_dir, tname + ".png"))
    gpath = os.path.join(tex_dir, tname + "_glow.png")
    if glow is not None and glow.getbbox():
        glow.save(gpath)
    elif os.path.exists(gpath):
        os.remove(gpath)
    fields, assign = model.java_fields(set(parts))
    if humanoid:
        reset = "\n".join(f"        this.{camel(p)}.resetPose();" for p in parts)
        src = HUMANOID_TEMPLATE.format(cls=cls, name=model.name, fields=fields, assign=assign,
                                       layer=model.java_layer(), reset=reset, anim=anim.rstrip(), source=source)
    else:
        src = CREATURE_TEMPLATE.format(pkg=pkg, entity=entity, cls=cls, name=model.name, fields=fields,
                                       assign=assign, layer=model.java_layer(), anim=anim.rstrip())
    with open(os.path.join(JAVA, cls + ".java"), "w") as f:
        f.write(src)
    save_bbmodel(model, tex, os.path.join(BB, model.name + ".bbmodel"))
    size = 360
    sheet = Image.new("RGB", (size * len(poses), size))
    for k, (yaw, pitch, pose) in enumerate(poses):
        sheet.paste(model.preview(tex, size=size, yaw=yaw, pitch=pitch, pose=pose), (size * k, 0))
    sheet.save(os.path.join(PREV, model.name + ".png"))
    n = sum(1 for _ in model.cubes())
    print(f"ok {model.name}: {n} cubos, textura {model.tw}x{model.th}")
    return sheet


def main(only=None):
    for d in (JAVA, BB, PREV):
        os.makedirs(d, exist_ok=True)
    hum = [(25, 10, None), (-40, 12, None), (200, 10, None)]
    jobs = {
        "knight": lambda: emit(B.knight_model(), B.KNIGHT_MATS, "LightlessKnightModel", B.KNIGHT_PARTS, B.KNIGHT_ANIM,
                               hum, True, source="v2_bosses.py"),
        # o dragão agora é o v4 (build_v4.py); o v2 continua em v2_bosses.py só como histórico
        "dragon": lambda: __import__("build_v4").main(),
        "bram": lambda: emit(N.bram_model(), N.BRAM_MATS, "BramModel", N.BRAM_PARTS, N.BRAM_ANIM, hum, True,
                             source="v2_npcs.py", tex_name="npc/bram"),
        "iara": lambda: emit(N.iara_model(), N.IARA_MATS, "IaraModel", N.IARA_PARTS, N.IARA_ANIM, hum, True,
                             source="v2_npcs.py", tex_name="npc/iara"),
        "orvyn": lambda: emit(N.orvyn_model(), N.ORVYN_MATS, "OrvynModel", N.ORVYN_PARTS, N.ORVYN_ANIM, hum, True,
                              source="v2_npcs.py", tex_name="npc/orvyn"),
        "ravena": lambda: emit(N.ravena_model(), N.RAVENA_MATS, "RavenaModel", N.RAVENA_PARTS, N.RAVENA_ANIM, hum,
                               True, source="v2_npcs.py", tex_name="npc/ravena"),
        "raider": lambda: emit(N.raider_model(), N.RAIDER_MATS, "CrimsonRaiderModel", N.RAIDER_PARTS, N.RAIDER_ANIM,
                               hum, True, source="v2_npcs.py"),
        "wolf": lambda: emit(C.wolf_model(), C.WOLF_MATS, "ShadowWolfModel", C.WOLF_PARTS, C.WOLF_ANIM,
                             [(35, 15, None), (-50, 12, None), (160, 18, None)], False, entity="ShadowWolf", pkg="mob"),
        "wraith": lambda: emit(C.wraith_model(), C.WRAITH_MATS, "FrostWraithModel", C.WRAITH_PARTS, C.WRAITH_ANIM,
                               [(20, 8, None), (-45, 12, None), (160, 12, None)], False, entity="FrostWraith", pkg="mob"),
    }
    for k, fn in jobs.items():
        if only and k not in only:
            continue
        fn()


if __name__ == "__main__":
    main(sys.argv[1:] or None)
