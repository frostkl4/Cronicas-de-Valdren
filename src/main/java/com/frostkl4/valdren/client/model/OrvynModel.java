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
 * Modelo humanoide gerado por tools/gen/build_v2.py (geometria em tools/gen/v2_npcs.py).
 * Usa os ossos do HumanoidModel (andar, atacar, segurar itens) e acrescenta peças próprias
 * (roupas, cabelo, acessórios) que têm animação extra em setupAnim.
 * O arquivo tools/models/orvyn.bbmodel abre no Blockbench.
 */
public class OrvynModel<T extends LivingEntity> extends HumanoidModel<T> {
    public static final ModelLayerLocation LAYER =
            new ModelLayerLocation(new ResourceLocation(Valdren.MODID, "orvyn"), "main");

    private final ModelPart hoodTip;
    private final ModelPart hoodTip2;
    private final ModelPart hoodTip3;
    private final ModelPart hoodTip4;
    private final ModelPart beard;
    private final ModelPart beard2;
    private final ModelPart beard3;
    private final ModelPart robe;
    private final ModelPart tome;
    private final ModelPart scrolls;
    private final ModelPart sleeveR;
    private final ModelPart sleeveL;

    public OrvynModel(ModelPart root) {
        super(root);
        this.hoodTip = root.getChild("head").getChild("hood_tip");
        this.hoodTip2 = root.getChild("head").getChild("hood_tip").getChild("hood_tip2");
        this.hoodTip3 = root.getChild("head").getChild("hood_tip").getChild("hood_tip2").getChild("hood_tip3");
        this.hoodTip4 = root.getChild("head").getChild("hood_tip").getChild("hood_tip2").getChild("hood_tip3").getChild("hood_tip4");
        this.beard = root.getChild("head").getChild("beard");
        this.beard2 = root.getChild("head").getChild("beard").getChild("beard2");
        this.beard3 = root.getChild("head").getChild("beard").getChild("beard2").getChild("beard3");
        this.robe = root.getChild("body").getChild("robe");
        this.tome = root.getChild("body").getChild("tome");
        this.scrolls = root.getChild("body").getChild("scrolls");
        this.sleeveR = root.getChild("right_arm").getChild("sleeve_r");
        this.sleeveL = root.getChild("left_arm").getChild("sleeve_l");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        PartDefinition p_head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(96, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(112, 40).addBox(-3.6F, -4.8F, -4.6F, 7.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 0).addBox(-4.6F, -8.6F, -4.6F, 9.0F, 9.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_hood_tip = p_head.addOrReplaceChild("hood_tip", CubeListBuilder.create().texOffs(78, 30).addBox(-3.0F, -3.0F, -2.5F, 6.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.6F, 2.0F, -0.45F, 0.0F, 0.0F));
        PartDefinition p_hood_tip2 = p_hood_tip.addOrReplaceChild("hood_tip2", CubeListBuilder.create().texOffs(46, 40).addBox(-2.0F, -3.0F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, 0.5F, -0.6F, 0.0F, 0.0F));
        PartDefinition p_hood_tip3 = p_hood_tip2.addOrReplaceChild("hood_tip3", CubeListBuilder.create().texOffs(76, 40).addBox(-1.5F, -3.0F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, 0.2F, -0.8F, 0.0F, 0.0F));
        p_hood_tip3.addOrReplaceChild("hood_tip4", CubeListBuilder.create().texOffs(0, 48).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(22, 48).addBox(-0.5F, -3.5F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, -0.9F, 0.0F, 0.0F));
        PartDefinition p_beard = p_head.addOrReplaceChild("beard", CubeListBuilder.create().texOffs(100, 30).addBox(-3.5F, -1.0F, -1.0F, 7.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, -4.2F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_beard2 = p_beard.addOrReplaceChild("beard2", CubeListBuilder.create().texOffs(62, 40).addBox(-2.5F, 0.0F, -1.0F, 5.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        p_beard2.addOrReplaceChild("beard3", CubeListBuilder.create().texOffs(8, 48).addBox(-1.5F, 0.0F, -0.8F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(30, 48).addBox(-1.0F, 2.0F, -1.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        root.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(68, 0).addBox(-4.5F, 0.0F, -2.5F, 9.0F, 12.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(0, 30).addBox(-5.5F, -0.5F, -3.0F, 11.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(16, 48).addBox(-1.0F, 2.0F, -3.4F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(46, 30).addBox(-5.0F, 9.0F, -3.0F, 10.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        p_body.addOrReplaceChild("robe", CubeListBuilder.create().texOffs(36, 0).addBox(-5.0F, 0.0F, -3.0F, 10.0F, 12.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 11.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        p_body.addOrReplaceChild("tome", CubeListBuilder.create().texOffs(34, 30).addBox(-1.5F, 0.0F, -2.5F, 2.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(36, 40).addBox(-1.0F, 0.5F, -2.5F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.2F, 10.0F, -1.0F, 0.0F, 0.0F, 0.12F));
        p_body.addOrReplaceChild("scrolls", CubeListBuilder.create().texOffs(48, 18).addBox(-1.0F, -5.0F, 0.0F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(26, 48).addBox(-0.5F, -6.5F, 0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 3.0F, 0.0F, 0.0F, 0.6F));
        PartDefinition p_right_arm = root.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(56, 18).addBox(-3.5F, -2.0F, -2.5F, 5.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(88, 40).addBox(-2.5F, 8.0F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 2.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        p_right_arm.addOrReplaceChild("sleeve_r", CubeListBuilder.create().texOffs(0, 18).addBox(-3.5F, 0.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 4.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_left_arm = root.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(76, 18).addBox(-1.5F, -2.0F, -2.5F, 5.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(100, 40).addBox(-0.5F, 8.0F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, 2.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        p_left_arm.addOrReplaceChild("sleeve_l", CubeListBuilder.create().texOffs(24, 18).addBox(-2.5F, 0.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 4.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        root.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 40).addBox(-2.0F, 9.0F, -2.5F, 4.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.9F, 12.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        root.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(18, 40).addBox(-2.0F, 9.0F, -2.5F, 4.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.9F, 12.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 128, 128);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks,
                          float netHeadYaw, float headPitch) {
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        this.hoodTip.resetPose();
        this.hoodTip2.resetPose();
        this.hoodTip3.resetPose();
        this.hoodTip4.resetPose();
        this.beard.resetPose();
        this.beard2.resetPose();
        this.beard3.resetPose();
        this.robe.resetPose();
        this.tome.resetPose();
        this.scrolls.resetPose();
        this.sleeveR.resetPose();
        this.sleeveL.resetPose();

        float walk = Math.min(limbSwingAmount, 1.0F);
        this.robe.xRot += (this.rightLeg.xRot + this.leftLeg.xRot) * 0.12F;
        this.robe.zRot += Mth.sin(limbSwing * 0.6662F) * 0.05F * walk;
        this.hoodTip.xRot += Mth.sin(ageInTicks * 0.06F) * 0.04F - walk * 0.1F;
        this.hoodTip2.xRot += Mth.sin(ageInTicks * 0.06F - 0.6F) * 0.06F - walk * 0.12F;
        this.hoodTip3.xRot += Mth.sin(ageInTicks * 0.06F - 1.2F) * 0.08F - walk * 0.15F;
        this.hoodTip4.xRot += Mth.sin(ageInTicks * 0.06F - 1.8F) * 0.1F;
        this.beard.xRot += -this.head.xRot * 0.3F;
        this.beard2.xRot += Mth.sin(ageInTicks * 0.07F) * 0.04F + walk * 0.1F;
        this.beard3.xRot += Mth.sin(ageInTicks * 0.07F - 0.7F) * 0.06F + walk * 0.12F;
        this.sleeveR.xRot += walk * 0.2F + Mth.sin(ageInTicks * 0.05F) * 0.03F;
        this.sleeveL.xRot += walk * 0.2F + Mth.sin(ageInTicks * 0.05F + 1.0F) * 0.03F;
        this.tome.zRot += Mth.cos(limbSwing * 0.6662F) * 0.15F * walk;
    }
}
