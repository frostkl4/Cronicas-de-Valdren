package com.frostkl4.valdren.client.model;

import com.frostkl4.valdren.Valdren;
import com.frostkl4.valdren.entity.boss.StormDragon;
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
 * o arquivo tools/models/storm_dragon.bbmodel abre no Blockbench (formato Modded Entity).
 */
public class StormDragonModel extends HierarchicalModel<StormDragon> {
    public static final ModelLayerLocation LAYER =
            new ModelLayerLocation(new ResourceLocation(Valdren.MODID, "storm_dragon"), "main");

    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart neck1;
    private final ModelPart neck2;
    private final ModelPart neck3;
    private final ModelPart head;
    private final ModelPart jaw;
    private final ModelPart tail1;
    private final ModelPart tail2;
    private final ModelPart tail3;
    private final ModelPart tail4;
    private final ModelPart tail5;
    private final ModelPart legFr;
    private final ModelPart legFrFore;
    private final ModelPart legFrFoot;
    private final ModelPart legBr;
    private final ModelPart legBrShin;
    private final ModelPart legBrFoot;
    private final ModelPart legFl;
    private final ModelPart legFlFore;
    private final ModelPart legFlFoot;
    private final ModelPart legBl;
    private final ModelPart legBlShin;
    private final ModelPart legBlFoot;
    private final ModelPart wingR;
    private final ModelPart wingRMid;
    private final ModelPart wingRTip;
    private final ModelPart wingL;
    private final ModelPart wingLMid;
    private final ModelPart wingLTip;

    public StormDragonModel(ModelPart root) {
        this.root = root;
        this.body = root.getChild("body");
        this.neck1 = root.getChild("body").getChild("neck1");
        this.neck2 = root.getChild("body").getChild("neck1").getChild("neck2");
        this.neck3 = root.getChild("body").getChild("neck1").getChild("neck2").getChild("neck3");
        this.head = root.getChild("body").getChild("neck1").getChild("neck2").getChild("neck3").getChild("head");
        this.jaw = root.getChild("body").getChild("neck1").getChild("neck2").getChild("neck3").getChild("head").getChild("jaw");
        this.tail1 = root.getChild("body").getChild("tail1");
        this.tail2 = root.getChild("body").getChild("tail1").getChild("tail2");
        this.tail3 = root.getChild("body").getChild("tail1").getChild("tail2").getChild("tail3");
        this.tail4 = root.getChild("body").getChild("tail1").getChild("tail2").getChild("tail3").getChild("tail4");
        this.tail5 = root.getChild("body").getChild("tail1").getChild("tail2").getChild("tail3").getChild("tail4").getChild("tail5");
        this.legFr = root.getChild("body").getChild("leg_fr");
        this.legFrFore = root.getChild("body").getChild("leg_fr").getChild("leg_fr_fore");
        this.legFrFoot = root.getChild("body").getChild("leg_fr").getChild("leg_fr_fore").getChild("leg_fr_foot");
        this.legBr = root.getChild("body").getChild("leg_br");
        this.legBrShin = root.getChild("body").getChild("leg_br").getChild("leg_br_shin");
        this.legBrFoot = root.getChild("body").getChild("leg_br").getChild("leg_br_shin").getChild("leg_br_foot");
        this.legFl = root.getChild("body").getChild("leg_fl");
        this.legFlFore = root.getChild("body").getChild("leg_fl").getChild("leg_fl_fore");
        this.legFlFoot = root.getChild("body").getChild("leg_fl").getChild("leg_fl_fore").getChild("leg_fl_foot");
        this.legBl = root.getChild("body").getChild("leg_bl");
        this.legBlShin = root.getChild("body").getChild("leg_bl").getChild("leg_bl_shin");
        this.legBlFoot = root.getChild("body").getChild("leg_bl").getChild("leg_bl_shin").getChild("leg_bl_foot");
        this.wingR = root.getChild("body").getChild("wing_r");
        this.wingRMid = root.getChild("body").getChild("wing_r").getChild("wing_r_mid");
        this.wingRTip = root.getChild("body").getChild("wing_r").getChild("wing_r_mid").getChild("wing_r_tip");
        this.wingL = root.getChild("body").getChild("wing_l");
        this.wingLMid = root.getChild("body").getChild("wing_l").getChild("wing_l_mid");
        this.wingLTip = root.getChild("body").getChild("wing_l").getChild("wing_l_mid").getChild("wing_l_tip");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        PartDefinition p_body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-11.0F, -10.0F, -20.0F, 22.0F, 20.0F, 22.0F, new CubeDeformation(0.0F)).texOffs(188, 0).addBox(-10.0F, -9.0F, 2.0F, 20.0F, 17.0F, 16.0F, new CubeDeformation(0.0F)).texOffs(58, 72).addBox(-9.0F, -9.0F, 18.0F, 18.0F, 15.0F, 10.0F, new CubeDeformation(0.0F)).texOffs(88, 0).addBox(-8.0F, 8.0F, -18.0F, 16.0F, 2.0F, 34.0F, new CubeDeformation(0.0F)).texOffs(340, 100).addBox(-12.0F, -12.0F, -16.0F, 7.0F, 6.0F, 10.0F, new CubeDeformation(0.0F)).texOffs(374, 100).addBox(5.0F, -12.0F, -16.0F, 7.0F, 6.0F, 10.0F, new CubeDeformation(0.0F)).texOffs(216, 138).addBox(-1.5F, -14.0F, -18.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(36, 138).addBox(-1.5F, -15.0F, -11.0F, 3.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(230, 138).addBox(-1.5F, -14.0F, -4.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(50, 138).addBox(-1.5F, -15.0F, 3.0F, 3.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(244, 138).addBox(-1.5F, -14.0F, 10.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(64, 138).addBox(-1.5F, -15.0F, 17.0F, 3.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(258, 138).addBox(-1.5F, -14.0F, 23.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_neck1 = p_body.addOrReplaceChild("neck1", CubeListBuilder.create().texOffs(428, 42).addBox(-7.0F, -7.0F, -14.0F, 14.0F, 13.0F, 15.0F, new CubeDeformation(0.0F)).texOffs(164, 123).addBox(-5.0F, 6.0F, -13.0F, 10.0F, 1.0F, 13.0F, new CubeDeformation(0.0F)).texOffs(326, 138).addBox(-1.5F, -10.0F, -12.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(272, 138).addBox(-1.5F, -11.0F, -6.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, -19.0F, -1.0F, 0.0F, 0.0F));
        PartDefinition p_neck2 = p_neck1.addOrReplaceChild("neck2", CubeListBuilder.create().texOffs(114, 72).addBox(-6.0F, -6.0F, -13.0F, 12.0F, 11.0F, 14.0F, new CubeDeformation(0.0F)).texOffs(374, 123).addBox(-4.0F, 5.0F, -12.0F, 8.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)).texOffs(340, 138).addBox(-1.5F, -9.0F, -11.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(286, 138).addBox(-1.5F, -10.0F, -5.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, -13.0F, 0.3F, 0.0F, 0.0F));
        PartDefinition p_neck3 = p_neck2.addOrReplaceChild("neck3", CubeListBuilder.create().texOffs(110, 100).addBox(-5.0F, -5.0F, -12.0F, 10.0F, 9.0F, 13.0F, new CubeDeformation(0.0F)).texOffs(414, 123).addBox(-3.5F, 4.0F, -11.0F, 7.0F, 1.0F, 11.0F, new CubeDeformation(0.0F)).texOffs(368, 138).addBox(-1.0F, -8.0F, -9.0F, 2.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -12.0F, 0.35F, 0.0F, 0.0F));
        PartDefinition p_head = p_neck3.addOrReplaceChild("head", CubeListBuilder.create().texOffs(364, 42).addBox(-8.0F, -8.0F, -14.0F, 16.0F, 13.0F, 16.0F, new CubeDeformation(0.0F)).texOffs(0, 100).addBox(-6.0F, -5.0F, -28.0F, 12.0F, 8.0F, 15.0F, new CubeDeformation(0.0F)).texOffs(206, 147).addBox(-5.0F, -6.0F, -27.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(218, 147).addBox(2.0F, -6.0F, -27.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(254, 123).addBox(-9.0F, -10.0F, -16.0F, 6.0F, 4.0F, 10.0F, new CubeDeformation(0.0F)).texOffs(286, 123).addBox(3.0F, -10.0F, -16.0F, 6.0F, 4.0F, 10.0F, new CubeDeformation(0.0F)).texOffs(198, 138).addBox(-1.5F, -7.0F, -27.5F, 3.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(120, 123).addBox(-10.5F, -4.0F, -8.0F, 3.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(142, 123).addBox(7.5F, -4.0F, -8.0F, 3.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(408, 100).addBox(-5.5F, 3.0F, -27.0F, 11.0F, 2.0F, 13.0F, new CubeDeformation(0.0F)).texOffs(66, 147).addBox(-5.0F, -10.0F, -11.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(76, 147).addBox(3.0F, -10.0F, -11.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -11.0F, 0.38F, 0.0F, 0.0F));
        PartDefinition p_horn_r = p_head.addOrReplaceChild("horn_r", CubeListBuilder.create().texOffs(318, 123).addBox(-2.0F, -2.0F, 0.0F, 4.0F, 4.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0F, -8.0F, -6.0F, 0.55F, -0.3F, 0.0F));
        PartDefinition p_horn_r2 = p_horn_r.addOrReplaceChild("horn_r2", CubeListBuilder.create().texOffs(450, 123).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 9.0F, 0.4F, 0.0F, 0.0F));
        PartDefinition p_horn_r3 = p_horn_r2.addOrReplaceChild("horn_r3", CubeListBuilder.create().texOffs(0, 138).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 8.0F, 0.45F, 0.0F, 0.0F));
        p_horn_r3.addOrReplaceChild("horn_r4", CubeListBuilder.create().texOffs(230, 147).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 6.5F, 0.35F, 0.0F, 0.0F));
        PartDefinition p_horn_l = p_head.addOrReplaceChild("horn_l", CubeListBuilder.create().texOffs(346, 123).addBox(-2.0F, -2.0F, 0.0F, 4.0F, 4.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0F, -8.0F, -6.0F, 0.55F, 0.3F, 0.0F));
        PartDefinition p_horn_l2 = p_horn_l.addOrReplaceChild("horn_l2", CubeListBuilder.create().texOffs(474, 123).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 9.0F, 0.4F, 0.0F, 0.0F));
        PartDefinition p_horn_l3 = p_horn_l2.addOrReplaceChild("horn_l3", CubeListBuilder.create().texOffs(18, 138).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 8.0F, 0.45F, 0.0F, 0.0F));
        p_horn_l3.addOrReplaceChild("horn_l4", CubeListBuilder.create().texOffs(240, 147).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 6.5F, 0.35F, 0.0F, 0.0F));
        p_head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(54, 100).addBox(-5.5F, 0.0F, -16.0F, 11.0F, 5.0F, 17.0F, new CubeDeformation(0.0F)).texOffs(210, 123).addBox(-5.0F, -2.0F, -15.5F, 10.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)).texOffs(380, 138).addBox(-1.0F, 4.0F, -14.0F, 2.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.0F, -12.0F, 0.1F, 0.0F, 0.0F));
        PartDefinition p_tail1 = p_body.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(0, 42).addBox(-7.0F, -6.0F, -2.0F, 14.0F, 12.0F, 18.0F, new CubeDeformation(0.0F)).texOffs(354, 138).addBox(-1.5F, -9.0F, 2.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(300, 138).addBox(-1.5F, -10.0F, 9.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, 28.0F, -0.15F, 0.0F, 0.0F));
        PartDefinition p_tail2 = p_tail1.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(0, 72).addBox(-5.5F, -5.0F, 0.0F, 11.0F, 10.0F, 18.0F, new CubeDeformation(0.0F)).texOffs(392, 138).addBox(-1.0F, -8.0F, 2.0F, 2.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(314, 138).addBox(-1.0F, -9.0F, 9.0F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.5F, 16.0F, -0.1F, 0.0F, 0.0F));
        PartDefinition p_tail3 = p_tail2.addOrReplaceChild("tail3", CubeListBuilder.create().texOffs(166, 72).addBox(-4.0F, -3.5F, 0.0F, 8.0F, 7.0F, 18.0F, new CubeDeformation(0.0F)).texOffs(54, 147).addBox(-1.0F, -5.5F, 4.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.5F, 17.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_tail4 = p_tail3.addOrReplaceChild("tail4", CubeListBuilder.create().texOffs(156, 100).addBox(-2.5F, -2.5F, 0.0F, 5.0F, 5.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 17.0F, 0.0F, 0.0F, 0.0F));
        p_tail4.addOrReplaceChild("tail5", CubeListBuilder.create().texOffs(250, 100).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 14.0F, new CubeDeformation(0.0F)).texOffs(290, 72).addBox(-0.5F, -7.0F, 3.0F, 1.0F, 14.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 15.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_leg_fr = p_body.addOrReplaceChild("leg_fr", CubeListBuilder.create().texOffs(218, 72).addBox(-4.0F, -3.0F, -5.0F, 8.0F, 15.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-12.0F, -1.0F, -12.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_leg_fr_fore = p_leg_fr.addOrReplaceChild("leg_fr_fore", CubeListBuilder.create().texOffs(198, 100).addBox(-3.0F, 0.0F, -3.5F, 6.0F, 11.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 11.0F, 1.0F, 0.0F, 0.0F, 0.0F));
        p_leg_fr_fore.addOrReplaceChild("leg_fr_foot", CubeListBuilder.create().texOffs(456, 100).addBox(-4.5F, 2.0F, -8.0F, 9.0F, 4.0F, 11.0F, new CubeDeformation(0.0F)).texOffs(86, 147).addBox(-4.0F, 3.0F, -11.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(96, 147).addBox(-1.0F, 3.0F, -11.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(106, 147).addBox(2.0F, 3.0F, -11.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(346, 147).addBox(-1.0F, 3.0F, 3.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 10.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_leg_br = p_body.addOrReplaceChild("leg_br", CubeListBuilder.create().texOffs(64, 42).addBox(-5.0F, -4.0F, -7.0F, 10.0F, 17.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-11.0F, -1.0F, 20.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_leg_br_shin = p_leg_br.addOrReplaceChild("leg_br_shin", CubeListBuilder.create().texOffs(284, 100).addBox(-3.5F, 0.0F, -3.0F, 7.0F, 10.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 12.0F, 3.0F, 0.0F, 0.0F, 0.0F));
        p_leg_br_shin.addOrReplaceChild("leg_br_foot", CubeListBuilder.create().texOffs(0, 123).addBox(-4.5F, 2.0F, -8.0F, 9.0F, 4.0F, 11.0F, new CubeDeformation(0.0F)).texOffs(116, 147).addBox(-4.0F, 3.0F, -11.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(126, 147).addBox(-1.0F, 3.0F, -11.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(136, 147).addBox(2.0F, 3.0F, -11.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(354, 147).addBox(-1.0F, 3.0F, 3.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 9.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_leg_fl = p_body.addOrReplaceChild("leg_fl", CubeListBuilder.create().texOffs(254, 72).addBox(-4.0F, -3.0F, -5.0F, 8.0F, 15.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(12.0F, -1.0F, -12.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_leg_fl_fore = p_leg_fl.addOrReplaceChild("leg_fl_fore", CubeListBuilder.create().texOffs(224, 100).addBox(-3.0F, 0.0F, -3.5F, 6.0F, 11.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 11.0F, 1.0F, 0.0F, 0.0F, 0.0F));
        p_leg_fl_fore.addOrReplaceChild("leg_fl_foot", CubeListBuilder.create().texOffs(40, 123).addBox(-4.5F, 2.0F, -8.0F, 9.0F, 4.0F, 11.0F, new CubeDeformation(0.0F)).texOffs(146, 147).addBox(-4.0F, 3.0F, -11.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(156, 147).addBox(-1.0F, 3.0F, -11.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(166, 147).addBox(2.0F, 3.0F, -11.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(362, 147).addBox(-1.0F, 3.0F, 3.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 10.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_leg_bl = p_body.addOrReplaceChild("leg_bl", CubeListBuilder.create().texOffs(110, 42).addBox(-5.0F, -4.0F, -7.0F, 10.0F, 17.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(11.0F, -1.0F, 20.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_leg_bl_shin = p_leg_bl.addOrReplaceChild("leg_bl_shin", CubeListBuilder.create().texOffs(312, 100).addBox(-3.5F, 0.0F, -3.0F, 7.0F, 10.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 12.0F, 3.0F, 0.0F, 0.0F, 0.0F));
        p_leg_bl_shin.addOrReplaceChild("leg_bl_foot", CubeListBuilder.create().texOffs(80, 123).addBox(-4.5F, 2.0F, -8.0F, 9.0F, 4.0F, 11.0F, new CubeDeformation(0.0F)).texOffs(176, 147).addBox(-4.0F, 3.0F, -11.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(186, 147).addBox(-1.0F, 3.0F, -11.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(196, 147).addBox(2.0F, 3.0F, -11.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(370, 147).addBox(-1.0F, 3.0F, 3.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 9.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_wing_r = p_body.addOrReplaceChild("wing_r", CubeListBuilder.create().texOffs(78, 138).addBox(-26.0F, -2.0F, -2.0F, 26.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(260, 0).addBox(-26.0F, 0.0F, 0.0F, 26.0F, 1.0F, 30.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-10.0F, -11.0F, -12.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_wing_r_mid = p_wing_r.addOrReplaceChild("wing_r_mid", CubeListBuilder.create().texOffs(428, 138).addBox(-24.0F, -1.5F, -1.5F, 24.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(156, 42).addBox(-24.0F, 0.0F, 0.0F, 24.0F, 1.0F, 28.0F, new CubeDeformation(0.0F)).texOffs(404, 138).addBox(-2.0F, -4.0F, -2.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-26.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        p_wing_r_mid.addOrReplaceChild("wing_r_tip", CubeListBuilder.create().texOffs(250, 147).addBox(-22.0F, -1.0F, -1.0F, 22.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(312, 72).addBox(-22.0F, 0.0F, 0.0F, 22.0F, 1.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-24.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_wing_l = p_body.addOrReplaceChild("wing_l", CubeListBuilder.create().texOffs(138, 138).addBox(0.0F, -2.0F, -2.0F, 26.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(372, 0).addBox(0.0F, 0.0F, 0.0F, 26.0F, 1.0F, 30.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.0F, -11.0F, -12.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_wing_l_mid = p_wing_l.addOrReplaceChild("wing_l_mid", CubeListBuilder.create().texOffs(0, 147).addBox(0.0F, -1.5F, -1.5F, 24.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(260, 42).addBox(0.0F, 0.0F, 0.0F, 24.0F, 1.0F, 28.0F, new CubeDeformation(0.0F)).texOffs(416, 138).addBox(-1.0F, -4.0F, -2.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(26.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        p_wing_l_mid.addOrReplaceChild("wing_l_tip", CubeListBuilder.create().texOffs(298, 147).addBox(0.0F, -1.0F, -1.0F, 22.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(400, 72).addBox(0.0F, 0.0F, 0.0F, 22.0F, 1.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(24.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 512, 256);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    @Override
    public void setupAnim(StormDragon entity, float limbSwing, float limbSwingAmount, float ageInTicks,
                          float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);

        float t = ageInTicks;
        float partial = ageInTicks - entity.tickCount;
        boolean flying = entity.isFlying();
        float yaw = netHeadYaw * Mth.DEG_TO_RAD;
        float pitch = headPitch * Mth.DEG_TO_RAD;
        float hurt = entity.hurtTime > 0 ? (entity.hurtTime - partial) / 10.0F : 0.0F;
        float death = entity.deathTime > 0 ? Math.min(1.0F, (entity.deathTime + partial) / 20.0F) : 0.0F;
        float roar = entity.getRoarAmount(partial);
        float breathe = Mth.sin(t * 0.07F);
        this.neck1.yRot += yaw * 0.25F;
        this.neck2.yRot += yaw * 0.25F;
        this.neck3.yRot += yaw * 0.25F;
        this.head.yRot += yaw * 0.25F;
        this.neck1.xRot += pitch * 0.2F + breathe * 0.03F - roar * 0.35F + hurt * 0.2F;
        this.neck3.xRot += roar * 0.15F;
        this.head.xRot += pitch * 0.4F + roar * 0.2F - hurt * 0.35F;
        this.jaw.xRot += 0.05F + this.attackTime * 1.0F + roar * 0.9F + hurt * 0.4F + breathe * 0.02F;
        this.body.y += breathe * 0.6F;
        this.tail1.yRot += Mth.sin(t * 0.06F) * 0.06F;
        this.tail2.yRot += Mth.sin(t * 0.06F - 0.5F) * 0.09F;
        this.tail3.yRot += Mth.sin(t * 0.06F - 1.0F) * 0.12F;
        this.tail4.yRot += Mth.sin(t * 0.06F - 1.5F) * 0.15F;
        this.tail5.yRot += Mth.sin(t * 0.06F - 2.0F) * 0.18F;
        this.tail3.xRot += Mth.sin(t * 0.05F) * 0.04F;
        if (flying) {
            float f = t * 0.28F;
            this.wingL.zRot += -Mth.sin(f) * 0.65F + 0.05F;
            this.wingR.zRot += Mth.sin(f) * 0.65F - 0.05F;
            this.wingLMid.zRot += -Mth.sin(f - 0.7F) * 0.35F;
            this.wingRMid.zRot += Mth.sin(f - 0.7F) * 0.35F;
            this.wingLTip.zRot += -Mth.sin(f - 1.4F) * 0.4F;
            this.wingRTip.zRot += Mth.sin(f - 1.4F) * 0.4F;
            this.wingL.yRot += Mth.cos(f) * 0.12F;
            this.wingR.yRot -= Mth.cos(f) * 0.12F;
            this.body.y += Mth.sin(f) * 1.5F;
            this.legFl.xRot += 0.9F;
            this.legFr.xRot += 0.9F;
            this.legFlFore.xRot += 0.6F;
            this.legFrFore.xRot += 0.6F;
            this.legBl.xRot += 1.0F;
            this.legBr.xRot += 1.0F;
            this.legBlShin.xRot += 0.5F;
            this.legBrShin.xRot += 0.5F;
            this.legBlFoot.xRot += 0.6F;
            this.legBrFoot.xRot += 0.6F;
            this.tail1.xRot += 0.12F;
            this.neck1.xRot += 0.15F;
        } else {
            this.wingL.xRot += -1.5708F;
            this.wingL.yRot += -1.5708F;
            this.wingL.zRot += -0.45F + breathe * 0.03F;
            this.wingLMid.zRot += 2.85F;
            this.wingLTip.zRot += -2.7F;
            this.wingR.xRot += -1.5708F;
            this.wingR.yRot += 1.5708F;
            this.wingR.zRot += 0.45F - breathe * 0.03F;
            this.wingRMid.zRot += -2.85F;
            this.wingRTip.zRot += 2.7F;
            float swing = limbSwing * 0.35F;
            float amt = Math.min(limbSwingAmount, 1.0F);
            float a = Mth.cos(swing) * 0.5F * amt;
            float b = Mth.cos(swing + Mth.PI) * 0.5F * amt;
            this.legFl.xRot += a;
            this.legBr.xRot += a;
            this.legFr.xRot += b;
            this.legBl.xRot += b;
            this.legFlFore.xRot += Math.max(0.0F, -a) * 0.6F;
            this.legFrFore.xRot += Math.max(0.0F, -b) * 0.6F;
            this.legFlFoot.xRot -= a + Math.max(0.0F, -a) * 0.6F;
            this.legFrFoot.xRot -= b + Math.max(0.0F, -b) * 0.6F;
            this.legBrShin.xRot += Math.max(0.0F, a) * 0.5F;
            this.legBlShin.xRot += Math.max(0.0F, b) * 0.5F;
            this.legBrFoot.xRot -= a + Math.max(0.0F, a) * 0.5F;
            this.legBlFoot.xRot -= b + Math.max(0.0F, b) * 0.5F;
            this.body.zRot += Mth.sin(swing) * 0.03F * amt;
        }
        if (death > 0.0F) {
            this.body.y += death * 9.0F;
            this.neck1.xRot += death * 0.7F;
            this.head.xRot += death * 0.3F;
            this.jaw.xRot += death * 0.5F;
            this.legFl.zRot -= death * 0.6F;
            this.legFr.zRot += death * 0.6F;
            this.legBl.zRot -= death * 0.6F;
            this.legBr.zRot += death * 0.6F;
            this.wingL.zRot += death * 0.5F;
            this.wingR.zRot -= death * 0.5F;
            this.tail1.xRot -= death * 0.15F;
        }
    }
}
