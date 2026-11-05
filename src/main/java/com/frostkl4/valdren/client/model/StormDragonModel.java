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
 * Modelo gerado por tools/gen/build_models.py. A geometria vem de tools/gen/creatures.py;
 * o arquivo tools/models/storm_dragon.bbmodel abre no Blockbench (formato Modded Entity).
 */
public class StormDragonModel extends HierarchicalModel<StormDragon> {
    public static final ModelLayerLocation LAYER =
            new ModelLayerLocation(new ResourceLocation(Valdren.MODID, "storm_dragon"), "main");

    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart neck1;
    private final ModelPart neck2;
    private final ModelPart head;
    private final ModelPart jaw;
    private final ModelPart tail1;
    private final ModelPart tail2;
    private final ModelPart tail3;
    private final ModelPart tail4;
    private final ModelPart legFl;
    private final ModelPart legFr;
    private final ModelPart legBl;
    private final ModelPart legBlShin;
    private final ModelPart legBr;
    private final ModelPart legBrShin;
    private final ModelPart wingL;
    private final ModelPart wingLTip;
    private final ModelPart wingR;
    private final ModelPart wingRTip;

    public StormDragonModel(ModelPart root) {
        this.root = root;
        this.body = root.getChild("body");
        this.neck1 = root.getChild("body").getChild("neck1");
        this.neck2 = root.getChild("body").getChild("neck1").getChild("neck2");
        this.head = root.getChild("body").getChild("neck1").getChild("neck2").getChild("head");
        this.jaw = root.getChild("body").getChild("neck1").getChild("neck2").getChild("head").getChild("jaw");
        this.tail1 = root.getChild("body").getChild("tail1");
        this.tail2 = root.getChild("body").getChild("tail1").getChild("tail2");
        this.tail3 = root.getChild("body").getChild("tail1").getChild("tail2").getChild("tail3");
        this.tail4 = root.getChild("body").getChild("tail1").getChild("tail2").getChild("tail3").getChild("tail4");
        this.legFl = root.getChild("body").getChild("leg_fl");
        this.legFr = root.getChild("body").getChild("leg_fr");
        this.legBl = root.getChild("body").getChild("leg_bl");
        this.legBlShin = root.getChild("body").getChild("leg_bl").getChild("leg_bl_shin");
        this.legBr = root.getChild("body").getChild("leg_br");
        this.legBrShin = root.getChild("body").getChild("leg_br").getChild("leg_br_shin");
        this.wingL = root.getChild("body").getChild("wing_l");
        this.wingLTip = root.getChild("body").getChild("wing_l").getChild("wing_l_tip");
        this.wingR = root.getChild("body").getChild("wing_r");
        this.wingRTip = root.getChild("body").getChild("wing_r").getChild("wing_r_tip");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        PartDefinition p_body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -5.0F, -10.0F, 12.0F, 10.0F, 20.0F, new CubeDeformation(0.0F)).texOffs(146, 49).addBox(-7.0F, -4.0F, -9.0F, 14.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)).texOffs(0, 30).addBox(-5.0F, 4.0F, -9.0F, 10.0F, 2.0F, 17.0F, new CubeDeformation(0.0F)).texOffs(214, 78).addBox(-1.0F, -8.0F, -8.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(40, 85).addBox(-1.0F, -7.0F, -4.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(224, 78).addBox(-1.0F, -8.0F, 0.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(50, 85).addBox(-1.0F, -7.0F, 4.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(234, 78).addBox(-1.0F, -8.0F, 8.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 9.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_neck1 = p_body.addOrReplaceChild("neck1", CubeListBuilder.create().texOffs(34, 49).addBox(-3.5F, -3.5F, -8.0F, 7.0F, 7.0F, 9.0F, new CubeDeformation(0.0F)).texOffs(244, 78).addBox(-1.0F, -6.0F, -6.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(0, 85).addBox(-1.0F, -6.0F, -2.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(216, 65).addBox(-3.0F, 3.0F, -7.0F, 6.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, -10.0F, -0.5F, 0.0F, 0.0F));
        PartDefinition p_neck2 = p_neck1.addOrReplaceChild("neck2", CubeListBuilder.create().texOffs(218, 49).addBox(-3.0F, -3.0F, -7.0F, 6.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(10, 85).addBox(-1.0F, -5.5F, -5.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(0, 78).addBox(-2.5F, 2.5F, -6.0F, 5.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -7.0F, 0.25F, 0.0F, 0.0F));
        PartDefinition p_head = p_neck2.addOrReplaceChild("head", CubeListBuilder.create().texOffs(114, 49).addBox(-4.0F, -4.0F, -7.0F, 8.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(74, 65).addBox(-3.0F, -2.5F, -14.0F, 6.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)).texOffs(138, 78).addBox(-4.5F, -5.0F, -8.0F, 3.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(154, 78).addBox(1.5F, -5.0F, -8.0F, 3.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(174, 85).addBox(-2.5F, -3.5F, -13.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(180, 85).addBox(1.5F, -3.5F, -13.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(22, 78).addBox(-2.5F, 1.5F, -13.5F, 5.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(170, 78).addBox(-5.5F, -2.0F, -2.0F, 1.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(180, 78).addBox(4.5F, -2.0F, -2.0F, 1.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -7.0F, 0.3F, 0.0F, 0.0F));
        PartDefinition p_horn_l = p_head.addOrReplaceChild("horn_l", CubeListBuilder.create().texOffs(128, 65).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -4.0F, -2.0F, 0.6F, 0.25F, 0.0F));
        p_horn_l.addOrReplaceChild("horn_l_tip", CubeListBuilder.create().texOffs(190, 78).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 8.0F, 0.35F, 0.0F, 0.0F));
        PartDefinition p_horn_r = p_head.addOrReplaceChild("horn_r", CubeListBuilder.create().texOffs(148, 65).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -4.0F, -2.0F, 0.6F, -0.25F, 0.0F));
        p_horn_r.addOrReplaceChild("horn_r_tip", CubeListBuilder.create().texOffs(202, 78).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 8.0F, 0.35F, 0.0F, 0.0F));
        p_head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(100, 65).addBox(-3.0F, 0.0F, -8.0F, 6.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(44, 78).addBox(-2.5F, -1.0F, -7.5F, 5.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.0F, -6.0F, 0.12F, 0.0F, 0.0F));
        PartDefinition p_tail1 = p_body.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(186, 30).addBox(-4.0F, -3.0F, -1.0F, 8.0F, 7.0F, 11.0F, new CubeDeformation(0.0F)).texOffs(20, 85).addBox(-1.0F, -5.5F, 2.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(30, 85).addBox(-1.0F, -5.5F, 6.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, 10.0F, -0.12F, 0.0F, 0.0F));
        PartDefinition p_tail2 = p_tail1.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(0, 49).addBox(-3.0F, -2.5F, 0.0F, 6.0F, 5.0F, 11.0F, new CubeDeformation(0.0F)).texOffs(60, 85).addBox(-1.0F, -4.5F, 4.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.5F, 10.0F, -0.08F, 0.0F, 0.0F));
        PartDefinition p_tail3 = p_tail2.addOrReplaceChild("tail3", CubeListBuilder.create().texOffs(188, 49).addBox(-2.0F, -1.5F, 0.0F, 4.0F, 3.0F, 11.0F, new CubeDeformation(0.0F)).texOffs(70, 85).addBox(-0.5F, -3.0F, 4.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.5F, 11.0F, 0.0F, 0.0F, 0.0F));
        p_tail3.addOrReplaceChild("tail4", CubeListBuilder.create().texOffs(50, 65).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)).texOffs(36, 65).addBox(-0.5F, -3.5F, 5.0F, 1.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 11.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_leg_fl = p_body.addOrReplaceChild("leg_fl", CubeListBuilder.create().texOffs(0, 65).addBox(-2.0F, -1.0F, -2.5F, 4.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.5F, 1.0F, -6.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_leg_fl_shin = p_leg_fl.addOrReplaceChild("leg_fl_shin", CubeListBuilder.create().texOffs(168, 65).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 7.0F, 0.5F, 0.0F, 0.0F, 0.0F));
        p_leg_fl_shin.addOrReplaceChild("leg_fl_foot", CubeListBuilder.create().texOffs(66, 78).addBox(-2.0F, 0.0F, -4.0F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(0, 91).addBox(-2.0F, 1.0F, -5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(4, 91).addBox(-0.5F, 1.0F, -5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(8, 91).addBox(1.0F, 1.0F, -5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(12, 91).addBox(-0.5F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_leg_fr = p_body.addOrReplaceChild("leg_fr", CubeListBuilder.create().texOffs(18, 65).addBox(-2.0F, -1.0F, -2.5F, 4.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.5F, 1.0F, -6.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_leg_fr_shin = p_leg_fr.addOrReplaceChild("leg_fr_shin", CubeListBuilder.create().texOffs(180, 65).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 7.0F, 0.5F, 0.0F, 0.0F, 0.0F));
        p_leg_fr_shin.addOrReplaceChild("leg_fr_foot", CubeListBuilder.create().texOffs(84, 78).addBox(-2.0F, 0.0F, -4.0F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(16, 91).addBox(-2.0F, 1.0F, -5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(20, 91).addBox(-0.5F, 1.0F, -5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(24, 91).addBox(1.0F, 1.0F, -5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(28, 91).addBox(-0.5F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_leg_bl = p_body.addOrReplaceChild("leg_bl", CubeListBuilder.create().texOffs(66, 49).addBox(-2.5F, -2.0F, -3.0F, 5.0F, 9.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0F, 0.0F, 7.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_leg_bl_shin = p_leg_bl.addOrReplaceChild("leg_bl_shin", CubeListBuilder.create().texOffs(192, 65).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 7.0F, 2.0F, 0.0F, 0.0F, 0.0F));
        p_leg_bl_shin.addOrReplaceChild("leg_bl_foot", CubeListBuilder.create().texOffs(102, 78).addBox(-2.0F, 0.0F, -4.0F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(32, 91).addBox(-2.0F, 1.0F, -5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(36, 91).addBox(-0.5F, 1.0F, -5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(40, 91).addBox(1.0F, 1.0F, -5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(44, 91).addBox(-0.5F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_leg_br = p_body.addOrReplaceChild("leg_br", CubeListBuilder.create().texOffs(90, 49).addBox(-2.5F, -2.0F, -3.0F, 5.0F, 9.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0F, 0.0F, 7.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_leg_br_shin = p_leg_br.addOrReplaceChild("leg_br_shin", CubeListBuilder.create().texOffs(204, 65).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 7.0F, 2.0F, 0.0F, 0.0F, 0.0F));
        p_leg_br_shin.addOrReplaceChild("leg_br_foot", CubeListBuilder.create().texOffs(120, 78).addBox(-2.0F, 0.0F, -4.0F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(48, 91).addBox(-2.0F, 1.0F, -5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(52, 91).addBox(-0.5F, 1.0F, -5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(56, 91).addBox(1.0F, 1.0F, -5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(60, 91).addBox(-0.5F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_wing_l = p_body.addOrReplaceChild("wing_l", CubeListBuilder.create().texOffs(78, 85).addBox(0.0F, -1.0F, -1.0F, 18.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(64, 0).addBox(0.0F, 0.0F, 0.0F, 18.0F, 1.0F, 20.0F, new CubeDeformation(0.0F)).texOffs(158, 85).addBox(17.0F, -2.0F, -1.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0F, -4.0F, -6.0F, 0.0F, 0.0F, 0.0F));
        p_wing_l.addOrReplaceChild("wing_l_tip", CubeListBuilder.create().texOffs(186, 85).addBox(0.0F, -0.5F, -0.5F, 16.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(54, 30).addBox(0.0F, 0.0F, 0.0F, 16.0F, 1.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(18.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_wing_r = p_body.addOrReplaceChild("wing_r", CubeListBuilder.create().texOffs(118, 85).addBox(-18.0F, -1.0F, -1.0F, 18.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(140, 0).addBox(-18.0F, 0.0F, 0.0F, 18.0F, 1.0F, 20.0F, new CubeDeformation(0.0F)).texOffs(166, 85).addBox(-19.0F, -2.0F, -1.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0F, -4.0F, -6.0F, 0.0F, 0.0F, 0.0F));
        p_wing_r.addOrReplaceChild("wing_r_tip", CubeListBuilder.create().texOffs(220, 85).addBox(-16.0F, -0.5F, -0.5F, 16.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(120, 30).addBox(-16.0F, 0.0F, 0.0F, 16.0F, 1.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-18.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 256, 128);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    @Override
    public void setupAnim(StormDragon entity, float limbSwing, float limbSwingAmount, float ageInTicks,
                          float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);

        boolean flying = entity.isFlying();
        float t = ageInTicks;
        float yaw = netHeadYaw * Mth.DEG_TO_RAD;
        float pitch = headPitch * Mth.DEG_TO_RAD;
        this.neck1.yRot += yaw * 0.3F;
        this.neck2.yRot += yaw * 0.3F;
        this.head.yRot += yaw * 0.4F;
        this.neck1.xRot += pitch * 0.25F;
        this.head.xRot += pitch * 0.5F;
        this.body.y += Mth.sin(t * 0.08F) * 0.35F;
        this.neck1.xRot += Mth.sin(t * 0.08F) * 0.03F;
        float roar = entity.getRoarAmount(ageInTicks - entity.tickCount);
        this.jaw.xRot += 0.05F + this.attackTime * 1.1F + roar * 0.8F;
        this.head.xRot -= roar * 0.35F;
        float sw = Mth.sin(t * 0.07F);
        this.tail1.yRot += sw * 0.10F;
        this.tail2.yRot += Mth.sin(t * 0.07F - 0.6F) * 0.14F;
        this.tail3.yRot += Mth.sin(t * 0.07F - 1.2F) * 0.18F;
        this.tail4.yRot += Mth.sin(t * 0.07F - 1.8F) * 0.22F;
        if (flying) {
            float flap = Mth.sin(t * 0.3F);
            float flap2 = Mth.sin(t * 0.3F - 0.9F);
            this.wingL.zRot += -flap * 0.6F;
            this.wingR.zRot += flap * 0.6F;
            this.wingLTip.zRot += -flap2 * 0.5F;
            this.wingRTip.zRot += flap2 * 0.5F;
            this.body.y += flap * 0.8F;
            this.legFl.xRot += 0.9F;
            this.legFr.xRot += 0.9F;
            this.legBl.xRot += 1.1F;
            this.legBr.xRot += 1.1F;
            this.legBlShin.xRot += 0.6F;
            this.legBrShin.xRot += 0.6F;
            this.tail1.xRot += 0.12F;
            this.tail2.xRot += 0.05F;
        } else {
            this.wingL.xRot += -1.5708F;
            this.wingL.yRot += -1.5708F;
            this.wingL.zRot += -0.5F;
            this.wingLTip.zRot += 2.9F;
            this.wingR.xRot += -1.5708F;
            this.wingR.yRot += 1.5708F;
            this.wingR.zRot += 0.5F;
            this.wingRTip.zRot += -2.9F;
            float swing = limbSwing * 0.45F;
            float amt = Math.min(limbSwingAmount, 1.0F);
            this.legFl.xRot += Mth.cos(swing) * 0.6F * amt;
            this.legBr.xRot += Mth.cos(swing) * 0.6F * amt;
            this.legFr.xRot += Mth.cos(swing + Mth.PI) * 0.6F * amt;
            this.legBl.xRot += Mth.cos(swing + Mth.PI) * 0.6F * amt;
            this.wingL.zRot += Mth.sin(t * 0.05F) * 0.04F;
            this.wingR.zRot -= Mth.sin(t * 0.05F) * 0.04F;
        }
    }
}
