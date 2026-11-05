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
 * O arquivo tools/models/ravena.bbmodel abre no Blockbench.
 */
public class RavenaModel<T extends LivingEntity> extends HumanoidModel<T> {
    public static final ModelLayerLocation LAYER =
            new ModelLayerLocation(new ResourceLocation(Valdren.MODID, "ravena"), "main");

    private final ModelPart braid;
    private final ModelPart braid2;
    private final ModelPart braid3;
    private final ModelPart mantle;
    private final ModelPart quiver;
    private final ModelPart flapF;
    private final ModelPart flapB;

    public RavenaModel(ModelPart root) {
        super(root);
        this.braid = root.getChild("head").getChild("braid");
        this.braid2 = root.getChild("head").getChild("braid").getChild("braid2");
        this.braid3 = root.getChild("head").getChild("braid").getChild("braid2").getChild("braid3");
        this.mantle = root.getChild("body").getChild("mantle");
        this.quiver = root.getChild("body").getChild("quiver");
        this.flapF = root.getChild("body").getChild("flap_f");
        this.flapB = root.getChild("body").getChild("flap_b");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        PartDefinition p_head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(56, 0).addBox(-4.5F, -8.5F, -4.5F, 9.0F, 5.0F, 9.0F, new CubeDeformation(0.0F)).texOffs(32, 27).addBox(-4.5F, -3.5F, -1.0F, 1.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(44, 27).addBox(3.5F, -3.5F, -1.0F, 1.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(40, 37).addBox(3.6F, -9.5F, -1.5F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_braid = p_head.addOrReplaceChild("braid", CubeListBuilder.create().texOffs(84, 27).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.0F, 4.5F, 0.35F, 0.0F, 0.0F));
        PartDefinition p_braid2 = p_braid.addOrReplaceChild("braid2", CubeListBuilder.create().texOffs(92, 27).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.0F, 0.0F, -0.1F, 0.0F, 0.0F));
        p_braid2.addOrReplaceChild("braid3", CubeListBuilder.create().texOffs(50, 37).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(66, 37).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.0F, 0.0F, -0.1F, 0.0F, 0.0F));
        root.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(56, 27).addBox(-4.5F, 8.0F, -2.5F, 9.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(46, 37).addBox(-3.5F, 9.5F, -3.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        p_body.addOrReplaceChild("mantle", CubeListBuilder.create().texOffs(48, 16).addBox(-5.5F, -1.0F, -3.0F, 11.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(74, 37).addBox(-1.0F, 1.0F, -3.4F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        p_body.addOrReplaceChild("quiver", CubeListBuilder.create().texOffs(92, 0).addBox(-1.5F, -3.0F, 0.0F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(54, 37).addBox(-1.5F, -6.0F, 1.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(58, 37).addBox(-0.5F, -5.0F, 1.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(62, 37).addBox(0.5F, -6.0F, 1.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 1.0F, 2.3F, 0.0F, 0.0F, -0.4F));
        p_body.addOrReplaceChild("flap_f", CubeListBuilder.create().texOffs(100, 27).addBox(-3.5F, 0.0F, -0.5F, 7.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 10.0F, -2.3F, 0.0F, 0.0F, 0.0F));
        p_body.addOrReplaceChild("flap_b", CubeListBuilder.create().texOffs(0, 37).addBox(-3.5F, 0.0F, -0.5F, 7.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 10.0F, 2.3F, 0.0F, 0.0F, 0.0F));
        root.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(104, 0).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(82, 16).addBox(-3.5F, 5.0F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(16, 37).addBox(-2.5F, 10.0F, -1.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 2.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        root.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(102, 16).addBox(-1.5F, 5.0F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(28, 37).addBox(-0.5F, 10.0F, -1.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, 2.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        root.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(16, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(0, 27).addBox(-2.3F, 6.0F, -2.3F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.3F)).texOffs(80, 37).addBox(-2.5F, 10.5F, -3.0F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.9F, 12.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        root.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(32, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(16, 27).addBox(-1.7F, 6.0F, -2.3F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.3F)).texOffs(92, 37).addBox(-2.5F, 10.5F, -3.0F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.9F, 12.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 128, 128);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks,
                          float netHeadYaw, float headPitch) {
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        this.braid.resetPose();
        this.braid2.resetPose();
        this.braid3.resetPose();
        this.mantle.resetPose();
        this.quiver.resetPose();
        this.flapF.resetPose();
        this.flapB.resetPose();

        float walk = Math.min(limbSwingAmount, 1.0F);
        this.braid.xRot += -this.head.xRot * 0.5F + Mth.sin(ageInTicks * 0.09F) * 0.05F + walk * 0.4F;
        this.braid2.xRot += Mth.sin(ageInTicks * 0.09F - 0.6F) * 0.08F + walk * 0.2F;
        this.braid3.xRot += Mth.sin(ageInTicks * 0.09F - 1.2F) * 0.1F + walk * 0.2F;
        this.braid.zRot += Mth.sin(limbSwing * 0.6662F) * 0.1F * walk;
        this.flapF.xRot += Math.min(0.0F, Math.min(this.rightLeg.xRot, this.leftLeg.xRot)) * 0.8F;
        this.flapB.xRot += Math.max(0.0F, Math.max(this.rightLeg.xRot, this.leftLeg.xRot)) * 0.8F;
        this.quiver.zRot += Mth.cos(limbSwing * 0.6662F) * 0.06F * walk;
    }
}
