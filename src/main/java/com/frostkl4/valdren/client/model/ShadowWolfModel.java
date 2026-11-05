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
 * Modelo gerado por tools/gen/build_v2.py (geometria em tools/gen/v2_bosses.py e v2_creatures.py);
 * o arquivo tools/models/shadow_wolf.bbmodel abre no Blockbench (formato Modded Entity).
 */
public class ShadowWolfModel extends HierarchicalModel<ShadowWolf> {
    public static final ModelLayerLocation LAYER =
            new ModelLayerLocation(new ResourceLocation(Valdren.MODID, "shadow_wolf"), "main");

    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart mane;
    private final ModelPart neck;
    private final ModelPart head;
    private final ModelPart earR;
    private final ModelPart earL;
    private final ModelPart jaw;
    private final ModelPart legFr;
    private final ModelPart legFrLow;
    private final ModelPart legBr;
    private final ModelPart legFl;
    private final ModelPart legFlLow;
    private final ModelPart legBl;
    private final ModelPart tail;
    private final ModelPart tail2;
    private final ModelPart tail3;

    public ShadowWolfModel(ModelPart root) {
        this.root = root;
        this.body = root.getChild("body");
        this.mane = root.getChild("body").getChild("mane");
        this.neck = root.getChild("body").getChild("neck");
        this.head = root.getChild("body").getChild("neck").getChild("head");
        this.earR = root.getChild("body").getChild("neck").getChild("head").getChild("ear_r");
        this.earL = root.getChild("body").getChild("neck").getChild("head").getChild("ear_l");
        this.jaw = root.getChild("body").getChild("neck").getChild("head").getChild("jaw");
        this.legFr = root.getChild("body").getChild("leg_fr");
        this.legFrLow = root.getChild("body").getChild("leg_fr").getChild("leg_fr_low");
        this.legBr = root.getChild("body").getChild("leg_br");
        this.legFl = root.getChild("body").getChild("leg_fl");
        this.legFlLow = root.getChild("body").getChild("leg_fl").getChild("leg_fl_low");
        this.legBl = root.getChild("body").getChild("leg_bl");
        this.tail = root.getChild("body").getChild("tail");
        this.tail2 = root.getChild("body").getChild("tail").getChild("tail2");
        this.tail3 = root.getChild("body").getChild("tail").getChild("tail2").getChild("tail3");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        PartDefinition p_body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -5.0F, -8.0F, 8.0F, 9.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(32, 0).addBox(-3.5F, -4.0F, 0.0F, 7.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(0, 17).addBox(-3.5F, -4.5F, 6.0F, 7.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(36, 36).addBox(-1.0F, -8.0F, -6.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(12, 36).addBox(-1.0F, -9.0F, -3.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(44, 36).addBox(-1.0F, -7.0F, 0.5F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 42).addBox(-1.0F, -6.0F, 4.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(8, 42).addBox(-3.0F, -6.5F, -4.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(14, 42).addBox(2.0F, -6.5F, -4.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 11.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        p_body.addOrReplaceChild("mane", CubeListBuilder.create().texOffs(84, 0).addBox(-5.0F, -1.0F, -3.0F, 10.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)).texOffs(50, 28).addBox(-4.5F, 2.0F, -3.5F, 9.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(76, 17).addBox(-5.5F, 0.0F, -1.0F, 1.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(88, 17).addBox(4.5F, 0.0F, -1.0F, 1.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, -6.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_neck = p_body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(56, 17).addBox(-2.5F, -2.5F, -4.0F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, -8.0F, -0.2F, 0.0F, 0.0F));
        PartDefinition p_head = p_neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(58, 0).addBox(-3.5F, -3.5F, -5.0F, 7.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(0, 28).addBox(-2.0F, -1.0F, -9.5F, 4.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(44, 42).addBox(-1.0F, -1.4F, -10.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(20, 42).addBox(-3.5F, -4.2F, -5.5F, 7.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(20, 36).addBox(-4.5F, 0.0F, -3.0F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(28, 36).addBox(3.5F, 0.0F, -3.0F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(50, 42).addBox(-1.6F, 2.0F, -9.2F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(54, 42).addBox(0.6F, 2.0F, -9.2F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, -4.0F, 0.2F, 0.0F, 0.0F));
        p_head.addOrReplaceChild("ear_r", CubeListBuilder.create().texOffs(68, 36).addBox(-1.0F, -4.0F, -0.5F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(58, 42).addBox(-0.5F, -5.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.2F, -3.5F, -1.5F, -0.2F, 0.0F, -0.2F));
        p_head.addOrReplaceChild("ear_l", CubeListBuilder.create().texOffs(74, 36).addBox(-1.0F, -4.0F, -0.5F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(62, 42).addBox(-0.5F, -5.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.2F, -3.5F, -1.5F, -0.2F, 0.0F, 0.2F));
        p_head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(104, 28).addBox(-1.5F, 0.0F, -4.5F, 3.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(66, 42).addBox(-1.4F, -1.0F, -4.2F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(70, 42).addBox(0.4F, -1.0F, -4.2F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.5F, -5.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_leg_fr = p_body.addOrReplaceChild("leg_fr", CubeListBuilder.create().texOffs(100, 17).addBox(-1.5F, -1.0F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.8F, 2.0F, -5.0F, 0.0F, 0.0F, 0.0F));
        p_leg_fr.addOrReplaceChild("leg_fr_low", CubeListBuilder.create().texOffs(72, 28).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(80, 36).addBox(-1.5F, 5.0F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(74, 42).addBox(-1.5F, 5.0F, -2.3F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(78, 42).addBox(-0.5F, 5.0F, -2.3F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(82, 42).addBox(0.5F, 5.0F, -2.3F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_leg_br = p_body.addOrReplaceChild("leg_br", CubeListBuilder.create().texOffs(24, 17).addBox(-1.5F, -1.5F, -2.5F, 3.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.6F, 1.0F, 8.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_leg_br_hock = p_leg_br.addOrReplaceChild("leg_br_hock", CubeListBuilder.create().texOffs(80, 28).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.0F, 1.5F, 0.6F, 0.0F, 0.0F));
        p_leg_br_hock.addOrReplaceChild("leg_br_meta", CubeListBuilder.create().texOffs(52, 36).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(92, 36).addBox(-1.5F, 3.0F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(86, 42).addBox(-1.5F, 3.0F, -2.3F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(90, 42).addBox(-0.5F, 3.0F, -2.3F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(94, 42).addBox(0.5F, 3.0F, -2.3F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.5F, 0.0F, -0.6F, 0.0F, 0.0F));
        PartDefinition p_leg_fl = p_body.addOrReplaceChild("leg_fl", CubeListBuilder.create().texOffs(112, 17).addBox(-1.5F, -1.0F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.8F, 2.0F, -5.0F, 0.0F, 0.0F, 0.0F));
        p_leg_fl.addOrReplaceChild("leg_fl_low", CubeListBuilder.create().texOffs(88, 28).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(104, 36).addBox(-1.5F, 5.0F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(98, 42).addBox(-1.5F, 5.0F, -2.3F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(102, 42).addBox(-0.5F, 5.0F, -2.3F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(106, 42).addBox(0.5F, 5.0F, -2.3F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_leg_bl = p_body.addOrReplaceChild("leg_bl", CubeListBuilder.create().texOffs(40, 17).addBox(-1.5F, -1.5F, -2.5F, 3.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.6F, 1.0F, 8.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_leg_bl_hock = p_leg_bl.addOrReplaceChild("leg_bl_hock", CubeListBuilder.create().texOffs(96, 28).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.0F, 1.5F, 0.6F, 0.0F, 0.0F));
        p_leg_bl_hock.addOrReplaceChild("leg_bl_meta", CubeListBuilder.create().texOffs(60, 36).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(116, 36).addBox(-1.5F, 3.0F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(110, 42).addBox(-1.5F, 3.0F, -2.3F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(114, 42).addBox(-0.5F, 3.0F, -2.3F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(118, 42).addBox(0.5F, 3.0F, -2.3F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.5F, 0.0F, -0.6F, 0.0F, 0.0F));
        PartDefinition p_tail = p_body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(18, 28).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, 11.0F, -0.45F, 0.0F, 0.0F));
        PartDefinition p_tail2 = p_tail.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(34, 28).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 5.0F, -0.15F, 0.0F, 0.0F));
        p_tail2.addOrReplaceChild("tail3", CubeListBuilder.create().texOffs(0, 36).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(38, 42).addBox(-0.5F, -0.5F, 3.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 5.0F, 0.1F, 0.0F, 0.0F));
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

        float partial = ageInTicks - entity.tickCount;
        float hurt = entity.hurtTime > 0 ? (entity.hurtTime - partial) / 10.0F : 0.0F;
        float amt = Math.min(limbSwingAmount, 1.0F);
        float run = Math.max(0.0F, Math.min(1.0F, (limbSwingAmount - 0.4F) * 2.0F));
        float swing = limbSwing * 0.6662F;
        float front = Mth.cos(swing) * 1.1F * amt;
        float back = Mth.cos(swing + Mth.PI * (1.0F - run * 0.6F)) * 1.1F * amt;
        this.legFl.xRot += front;
        this.legFr.xRot += Mth.cos(swing + Mth.PI * (1.0F - run * 0.85F)) * 1.1F * amt;
        this.legBl.xRot += back;
        this.legBr.xRot += Mth.cos(swing + Mth.PI * (0.15F + run * 0.2F)) * 1.1F * amt;
        this.legFlLow.xRot += Math.max(0.0F, front) * 0.6F;
        this.legFrLow.xRot += Math.max(0.0F, -front) * 0.6F;
        this.body.xRot += Mth.sin(swing * 2.0F) * 0.05F * run;
        this.body.y += Mth.sin(ageInTicks * 0.1F) * 0.25F + Math.abs(Mth.sin(swing)) * -0.8F * run;
        if (!entity.onGround()) {
            this.legFl.xRot -= 0.9F;
            this.legFr.xRot -= 0.9F;
            this.legBl.xRot += 0.9F;
            this.legBr.xRot += 0.9F;
            this.tail.xRot += 0.4F;
        }
        this.neck.yRot += netHeadYaw * Mth.DEG_TO_RAD * 0.4F;
        this.head.yRot += netHeadYaw * Mth.DEG_TO_RAD * 0.6F;
        this.head.xRot += headPitch * Mth.DEG_TO_RAD - hurt * 0.5F;
        this.jaw.xRot += 0.05F + this.attackTime * 1.0F + Mth.sin(ageInTicks * 0.09F) * 0.04F + hurt * 0.4F;
        this.earR.zRot += Mth.sin(ageInTicks * 0.13F) > 0.95F ? -0.3F : 0.0F;
        this.earL.zRot += Mth.sin(ageInTicks * 0.11F + 2.0F) > 0.95F ? 0.3F : 0.0F;
        this.earR.xRot -= run * 0.5F;
        this.earL.xRot -= run * 0.5F;
        this.mane.xRot += Mth.sin(ageInTicks * 0.1F) * 0.03F;
        this.tail.xRot += amt * 0.45F;
        this.tail.yRot += Mth.sin(ageInTicks * 0.15F) * 0.2F * (1.0F - amt);
        this.tail2.yRot += Mth.sin(ageInTicks * 0.15F - 0.7F) * 0.25F;
        this.tail3.yRot += Mth.sin(ageInTicks * 0.15F - 1.4F) * 0.3F;
        this.tail2.xRot += amt * 0.2F;
    }
}
