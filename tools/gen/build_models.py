"""Gera modelos Java, texturas, previas e arquivos .bbmodel das criaturas."""
import os
import sys

from boxmodel import save_bbmodel
import creatures

ROOT = os.path.abspath(os.path.join(os.path.dirname(__file__), "..", ".."))
JAVA = os.path.join(ROOT, "src/main/java/com/frostkl4/valdren/client/model")
TEX = os.path.join(ROOT, "src/main/resources/assets/valdren/textures/entity")
BB = os.path.join(ROOT, "tools/models")
PREV = os.path.join(ROOT, "docs/preview")

TEMPLATE = """package com.frostkl4.valdren.client.model;

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
 * Modelo gerado por tools/gen/build_models.py. A geometria vem de tools/gen/creatures.py;
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


def build(model, painters, cls, entity, pkg, parts, anim, poses):
    tex, glow = model.paint(painters, glow=True)
    os.makedirs(TEX, exist_ok=True)
    tex.save(os.path.join(TEX, model.name + ".png"))
    if glow is not None and glow.getbbox():
        glow.save(os.path.join(TEX, model.name + "_glow.png"))
    fields, assign = model.java_fields(set(parts))
    src = TEMPLATE.format(pkg=pkg, entity=entity, cls=cls, name=model.name, fields=fields,
                          assign=assign, layer=model.java_layer(), anim=anim.rstrip())
    os.makedirs(JAVA, exist_ok=True)
    with open(os.path.join(JAVA, cls + ".java"), "w") as f:
        f.write(src)
    os.makedirs(BB, exist_ok=True)
    save_bbmodel(model, tex, os.path.join(BB, model.name + ".bbmodel"))
    os.makedirs(PREV, exist_ok=True)
    from PIL import Image
    imgs = []
    for k, (yaw, pitch, pose) in enumerate(poses):
        imgs.append(model.preview(tex, size=360, yaw=yaw, pitch=pitch, pose=pose))
    sheet = Image.new("RGB", (360 * len(imgs), 360))
    for k, im in enumerate(imgs):
        sheet.paste(im, (360 * k, 0))
    sheet.save(os.path.join(PREV, model.name + ".png"))
    print("ok", model.name)


def main():
    build(creatures.wolf_model(), creatures.wolf_painters(), "ShadowWolfModel", "ShadowWolf", "mob",
          creatures.WOLF_PARTS, creatures.WOLF_ANIM,
          [(35, 18, None), (-40, 15, None), (150, 20, None)])
    build(creatures.wraith_model(), creatures.wraith_painters(), "FrostWraithModel", "FrostWraith", "mob",
          creatures.WRAITH_PARTS, creatures.WRAITH_ANIM,
          [(20, 10, None), (-45, 12, None), (160, 15, None)])
    build(creatures.dragon_model(), creatures.dragon_painters(), "StormDragonModel", "StormDragon", "boss",
          creatures.DRAGON_PARTS, creatures.DRAGON_ANIM,
          [(35, 22, creatures.DRAGON_POSE_GROUND), (-30, 30, creatures.DRAGON_POSE_FLY),
           (150, 25, creatures.DRAGON_POSE_GROUND)])


if __name__ == "__main__":
    sys.exit(main())
