package com.frostkl4.valdren.client.model;

import com.frostkl4.valdren.Valdren;
import com.frostkl4.valdren.entity.mob.ShadowWolf;
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
 * o arquivo tools/models/shadow_wolf.bbmodel abre no Blockbench (formato Modded Entity).
 */
public class ShadowWolfModel extends HierarchicalModel<ShadowWolf> {
    public static final ModelLayerLocation LAYER =
            new ModelLayerLocation(new ResourceLocation(Valdren.MODID, "shadow_wolf"), "main");

    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart mane;
    private final ModelPart head;
    private final ModelPart jaw;
    private final ModelPart legFl;
    private final ModelPart legFr;
    private final ModelPart legBl;
    private final ModelPart legBr;
    private final ModelPart tail;
    private final ModelPart tailTip;

    public ShadowWolfModel(ModelPart root) {
        this.root = root;
        this.body = root.getChild("body");
        this.mane = root.getChild("body").getChild("mane");
        this.head = root.getChild("body").getChild("head");
        this.jaw = root.getChild("body").getChild("head").getChild("jaw");
        this.legFl = root.getChild("body").getChild("leg_fl");
        this.legFr = root.getChild("body").getChild("leg_fr");
        this.legBl = root.getChild("body").getChild("leg_bl");
        this.legBr = root.getChild("body").getChild("leg_br");
        this.tail = root.getChild("body").getChild("tail");
        this.tailTip = root.getChild("body").getChild("tail").getChild("tail_tip");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        PartDefinition p_body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.5F, -4.5F, -7.0F, 9.0F, 9.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(34, 0).addBox(-3.5F, -3.5F, 1.0F, 7.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(110, 38).addBox(-1.0F, -5.5F, 3.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(118, 38).addBox(-0.5F, -5.0F, 6.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 12.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        p_body.addOrReplaceChild("mane", CubeListBuilder.create().texOffs(0, 17).addBox(-5.5F, -2.0F, -4.0F, 11.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(82, 29).addBox(-1.0F, -5.0F, -2.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(0, 38).addBox(-4.0F, -4.0F, -1.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(10, 38).addBox(2.0F, -4.0F, -1.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(20, 38).addBox(-1.0F, -3.5F, 3.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(108, 17).addBox(-6.0F, 1.0F, -3.0F, 1.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(0, 29).addBox(5.0F, 1.0F, -3.0F, 1.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.0F, -4.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_head = p_body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(64, 0).addBox(-3.5F, -4.0F, -6.0F, 7.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)).texOffs(36, 29).addBox(-2.0F, -1.0F, -10.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(6, 43).addBox(-1.0F, -1.4F, -10.4F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(30, 38).addBox(-3.5F, -7.0F, -3.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(38, 38).addBox(1.5F, -7.0F, -3.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(92, 29).addBox(-3.8F, -5.0F, -1.0F, 1.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(102, 29).addBox(2.8F, -5.0F, -1.0F, 1.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(12, 29).addBox(-4.5F, -1.0F, -4.0F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(24, 29).addBox(2.5F, -1.0F, -4.0F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(12, 43).addBox(-1.8F, 2.0F, -9.6F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(16, 43).addBox(0.8F, 2.0F, -9.6F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.5F, -7.0F, 0.0F, 0.0F, 0.0F));
        p_head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(112, 29).addBox(-1.5F, 0.0F, -4.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, -6.0F, 0.0F, 0.0F, 0.0F));
        p_body.addOrReplaceChild("leg_fl", CubeListBuilder.create().texOffs(66, 17).addBox(-1.5F, -1.0F, -1.5F, 3.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(46, 38).addBox(-1.0F, 6.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(62, 38).addBox(-1.5F, 9.0F, -2.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.6F, 2.0F, -4.5F, 0.0F, 0.0F, 0.0F));
        p_body.addOrReplaceChild("leg_fr", CubeListBuilder.create().texOffs(78, 17).addBox(-1.5F, -1.0F, -1.5F, 3.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(54, 38).addBox(-1.0F, 6.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(74, 38).addBox(-1.5F, 9.0F, -2.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.6F, 2.0F, -4.5F, 0.0F, 0.0F, 0.0F));
        p_body.addOrReplaceChild("leg_bl", CubeListBuilder.create().texOffs(38, 17).addBox(-1.5F, -1.5F, -2.0F, 3.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(66, 29).addBox(-1.0F, 4.0F, 0.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(86, 38).addBox(-1.5F, 9.0F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.3F, 2.0F, 6.5F, 0.0F, 0.0F, 0.0F));
        p_body.addOrReplaceChild("leg_br", CubeListBuilder.create().texOffs(52, 17).addBox(-1.5F, -1.5F, -2.0F, 3.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(74, 29).addBox(-1.0F, 4.0F, 0.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(98, 38).addBox(-1.5F, 9.0F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.3F, 2.0F, 6.5F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_tail = p_body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(90, 17).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, 8.5F, -0.55F, 0.0F, 0.0F));
        p_tail.addOrReplaceChild("tail_tip", CubeListBuilder.create().texOffs(52, 29).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(0, 43).addBox(-0.5F, -0.5F, 5.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 6.0F, 0.25F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 128, 64);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    @Override
    public void setupAnim(ShadowWolf entity, float limbSwing, float limbSwingAmount, float ageInTicks,
                          float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);

        float swing = limbSwing * 0.6662F;
        float amt = Math.min(limbSwingAmount, 1.0F);
        this.legFl.xRot += Mth.cos(swing) * 1.2F * amt;
        this.legBr.xRot += Mth.cos(swing) * 1.2F * amt;
        this.legFr.xRot += Mth.cos(swing + Mth.PI) * 1.2F * amt;
        this.legBl.xRot += Mth.cos(swing + Mth.PI) * 1.2F * amt;
        this.head.yRot += netHeadYaw * Mth.DEG_TO_RAD;
        this.head.xRot += headPitch * Mth.DEG_TO_RAD;
        this.jaw.xRot += 0.05F + this.attackTime * 0.9F + Mth.sin(ageInTicks * 0.09F) * 0.04F;
        this.body.y += Mth.sin(ageInTicks * 0.1F) * 0.25F;
        this.mane.xRot += Mth.sin(ageInTicks * 0.1F) * 0.03F;
        this.tail.yRot += Mth.sin(ageInTicks * 0.2F) * 0.25F;
        this.tail.xRot += amt * 0.3F;
        this.tailTip.yRot += Mth.sin(ageInTicks * 0.2F - 0.8F) * 0.3F;
    }
}
