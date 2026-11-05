package com.frostkl4.valdren.client.model;

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
 * Modelo humanoide gerado por tools/gen/build_v2.py (geometria em tools/gen/v2_bosses.py).
 * Usa os ossos do HumanoidModel (andar, atacar, segurar itens) e acrescenta peças próprias
 * (roupas, cabelo, acessórios) que têm animação extra em setupAnim.
 * O arquivo tools/models/lightless_knight_model.bbmodel abre no Blockbench.
 */
public class LightlessKnightModel<T extends LivingEntity> extends HumanoidModel<T> {
    public static final ModelLayerLocation LAYER =
            new ModelLayerLocation(new ResourceLocation(Valdren.MODID, "lightless_knight_model"), "main");

    private final ModelPart hornR;
    private final ModelPart hornL;
    private final ModelPart halo;
    private final ModelPart tassetF;
    private final ModelPart tassetB;
    private final ModelPart tassetR;
    private final ModelPart tassetL;
    private final ModelPart cape;
    private final ModelPart capeLower;
    private final ModelPart pauldronR;
    private final ModelPart pauldronL;

    public LightlessKnightModel(ModelPart root) {
        super(root);
        this.hornR = root.getChild("head").getChild("horn_r");
        this.hornL = root.getChild("head").getChild("horn_l");
        this.halo = root.getChild("head").getChild("halo");
        this.tassetF = root.getChild("body").getChild("tasset_f");
        this.tassetB = root.getChild("body").getChild("tasset_b");
        this.tassetR = root.getChild("body").getChild("tasset_r");
        this.tassetL = root.getChild("body").getChild("tasset_l");
        this.cape = root.getChild("body").getChild("cape");
        this.capeLower = root.getChild("body").getChild("cape").getChild("cape_lower");
        this.pauldronR = root.getChild("right_arm").getChild("pauldron_r");
        this.pauldronL = root.getChild("left_arm").getChild("pauldron_l");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        PartDefinition p_head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.5F, -9.0F, -4.5F, 9.0F, 9.0F, 9.0F, new CubeDeformation(0.0F)).texOffs(68, 61).addBox(-5.0F, -8.0F, -5.2F, 10.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(68, 0).addBox(-1.0F, -12.0F, -4.5F, 2.0F, 3.0F, 10.0F, new CubeDeformation(0.0F)).texOffs(108, 53).addBox(-0.5F, -13.0F, -2.5F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_horn_r = p_head.addOrReplaceChild("horn_r", CubeListBuilder.create().texOffs(0, 67).addBox(-4.0F, -1.0F, -1.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.5F, -6.5F, 0.0F, 0.0F, 0.15F, 0.35F));
        PartDefinition p_horn_r2 = p_horn_r.addOrReplaceChild("horn_r2", CubeListBuilder.create().texOffs(24, 67).addBox(-3.0F, -1.0F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7F));
        p_horn_r2.addOrReplaceChild("horn_r3", CubeListBuilder.create().texOffs(82, 71).addBox(-3.0F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.6F));
        PartDefinition p_horn_l = p_head.addOrReplaceChild("horn_l", CubeListBuilder.create().texOffs(12, 67).addBox(0.0F, -1.0F, -1.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.5F, -6.5F, 0.0F, 0.0F, -0.15F, -0.35F));
        PartDefinition p_horn_l2 = p_horn_l.addOrReplaceChild("horn_l2", CubeListBuilder.create().texOffs(34, 67).addBox(0.0F, -1.0F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.7F));
        p_horn_l2.addOrReplaceChild("horn_l3", CubeListBuilder.create().texOffs(90, 71).addBox(0.0F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.6F));
        p_head.addOrReplaceChild("halo", CubeListBuilder.create().texOffs(52, 67).addBox(6.0F, -1.0F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(58, 67).addBox(5.7165F, 0.9721F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(64, 67).addBox(4.8888F, 2.7845F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(70, 67).addBox(3.584F, 4.2902F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(76, 67).addBox(1.9079F, 5.3674F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(82, 67).addBox(-0.0038F, 5.9288F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(88, 67).addBox(-1.9962F, 5.9288F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(94, 67).addBox(-3.9079F, 5.3674F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(100, 67).addBox(-5.584F, 4.2902F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(106, 67).addBox(-6.8888F, 2.7845F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(112, 67).addBox(-7.7165F, 0.9721F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(118, 67).addBox(-8.0F, -1.0F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 71).addBox(-7.7165F, -2.9721F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(6, 71).addBox(-6.8888F, -4.7845F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(12, 71).addBox(-5.584F, -6.2902F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(18, 71).addBox(-3.9079F, -7.3674F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(24, 71).addBox(-1.9962F, -7.9288F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(30, 71).addBox(-0.0038F, -7.9288F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(36, 71).addBox(1.9079F, -7.3674F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(42, 71).addBox(3.584F, -6.2902F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(48, 71).addBox(4.8888F, -4.7845F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(54, 71).addBox(5.7165F, -2.9721F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(60, 71).addBox(-1.0F, -1.0F, 0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, 5.6F, 0.0F, 0.0F, 0.0F));
        root.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(36, 0).addBox(-5.0F, -0.5F, -3.0F, 10.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(20, 42).addBox(-4.5F, -2.0F, -3.5F, 9.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)).texOffs(32, 53).addBox(-4.5F, 7.5F, -2.5F, 9.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(0, 53).addBox(-5.0F, 10.5F, -3.0F, 10.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        p_body.addOrReplaceChild("tasset_f", CubeListBuilder.create().texOffs(0, 61).addBox(-4.5F, 0.0F, -0.5F, 9.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 12.0F, -3.0F, 0.0F, 0.0F, 0.0F));
        p_body.addOrReplaceChild("tasset_b", CubeListBuilder.create().texOffs(20, 61).addBox(-4.5F, 0.0F, -0.5F, 9.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 12.0F, 3.0F, 0.0F, 0.0F, 0.0F));
        p_body.addOrReplaceChild("tasset_r", CubeListBuilder.create().texOffs(92, 42).addBox(-0.5F, 0.0F, -2.5F, 1.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.6F, 12.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        p_body.addOrReplaceChild("tasset_l", CubeListBuilder.create().texOffs(104, 42).addBox(-0.5F, 0.0F, -2.5F, 1.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.6F, 12.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_cape = p_body.addOrReplaceChild("cape", CubeListBuilder.create().texOffs(92, 0).addBox(-5.5F, 0.0F, 0.0F, 11.0F, 12.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 3.4F, 0.1F, 0.0F, 0.0F));
        p_cape.addOrReplaceChild("cape_lower", CubeListBuilder.create().texOffs(80, 30).addBox(-5.5F, 0.0F, 0.0F, 11.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 12.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_right_arm = root.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(108, 18).addBox(-4.0F, -2.0F, -2.5F, 5.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(60, 18).addBox(-4.5F, 5.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 2.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        p_right_arm.addOrReplaceChild("pauldron_r", CubeListBuilder.create().texOffs(0, 18).addBox(-4.5F, -2.5F, -4.0F, 7.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(20, 30).addBox(-5.0F, 0.5F, -4.5F, 6.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)).texOffs(66, 71).addBox(-3.0F, -4.5F, -2.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(44, 67).addBox(-3.0F, -5.5F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(70, 71).addBox(-3.0F, -4.5F, 1.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -2.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_left_arm = root.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(0, 30).addBox(-1.0F, -2.0F, -2.5F, 5.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(84, 18).addBox(-1.5F, 5.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, 2.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        p_left_arm.addOrReplaceChild("pauldron_l", CubeListBuilder.create().texOffs(30, 18).addBox(-2.5F, -2.5F, -4.0F, 7.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(50, 30).addBox(-1.0F, 0.5F, -4.5F, 6.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)).texOffs(74, 71).addBox(2.0F, -4.5F, -2.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(48, 67).addBox(2.0F, -5.5F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(78, 71).addBox(2.0F, -4.5F, 1.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -2.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        root.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(104, 30).addBox(-2.5F, 0.0F, -2.5F, 5.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(40, 61).addBox(-2.5F, 5.0F, -3.4F, 5.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(52, 42).addBox(-2.5F, 6.0F, -2.5F, 5.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(60, 53).addBox(-3.0F, 10.0F, -3.5F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(92, 61).addBox(-2.5F, 10.0F, -5.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.9F, 12.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        root.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 42).addBox(-2.5F, 0.0F, -2.5F, 5.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(54, 61).addBox(-2.5F, 5.0F, -3.4F, 5.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(72, 42).addBox(-2.5F, 6.0F, -2.5F, 5.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(84, 53).addBox(-3.0F, 10.0F, -3.5F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(106, 61).addBox(-2.5F, 10.0F, -5.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.9F, 12.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 128, 128);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks,
                          float netHeadYaw, float headPitch) {
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        this.hornR.resetPose();
        this.hornL.resetPose();
        this.halo.resetPose();
        this.tassetF.resetPose();
        this.tassetB.resetPose();
        this.tassetR.resetPose();
        this.tassetL.resetPose();
        this.cape.resetPose();
        this.capeLower.resetPose();
        this.pauldronR.resetPose();
        this.pauldronL.resetPose();

        float partial = ageInTicks - entity.tickCount;
        float hurt = entity.hurtTime > 0 ? (entity.hurtTime - partial) / 10.0F : 0.0F;
        float walk = Math.min(limbSwingAmount, 1.0F);
        double speed = entity.getDeltaMovement().horizontalDistance();
        float dash = (float) Math.min(1.0D, Math.max(0.0D, speed - 0.2D) * 2.0D);
        this.cape.xRot += walk * 0.7F + dash * 0.8F + Mth.sin(ageInTicks * 0.06F) * 0.04F;
        this.capeLower.xRot += walk * 0.35F + dash * 0.4F + Mth.sin(ageInTicks * 0.08F + 1.0F) * 0.06F;
        this.tassetF.xRot += Math.min(0.0F, Math.min(this.rightLeg.xRot, this.leftLeg.xRot)) * 0.8F;
        this.tassetB.xRot += Math.max(0.0F, Math.max(this.rightLeg.xRot, this.leftLeg.xRot)) * 0.8F;
        this.tassetR.zRot += walk * 0.12F;
        this.tassetL.zRot -= walk * 0.12F;
        this.halo.zRot += ageInTicks * 0.025F;
        this.halo.y += Mth.sin(ageInTicks * 0.1F) * 0.35F;
        this.head.xRot -= hurt * 0.45F;
        this.body.xRot -= hurt * 0.12F;
        this.rightArm.xRot -= 0.12F;
        if (this.attackTime > 0.0F) {
            this.leftArm.xRot = this.rightArm.xRot;
            this.leftArm.yRot = 0.45F + this.rightArm.yRot;
            this.leftArm.zRot = 0.0F;
        }
    }
}
