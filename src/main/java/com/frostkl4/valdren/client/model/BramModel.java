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
 * O arquivo tools/models/bram.bbmodel abre no Blockbench.
 */
public class BramModel<T extends LivingEntity> extends HumanoidModel<T> {
    public static final ModelLayerLocation LAYER =
            new ModelLayerLocation(new ResourceLocation(Valdren.MODID, "bram"), "main");

    private final ModelPart beard;
    private final ModelPart beardBraid;
    private final ModelPart apron;
    private final ModelPart hammer;

    public BramModel(ModelPart root) {
        super(root);
        this.beard = root.getChild("head").getChild("beard");
        this.beardBraid = root.getChild("head").getChild("beard").getChild("beard_braid");
        this.apron = root.getChild("body").getChild("apron");
        this.hammer = root.getChild("body").getChild("hammer");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        PartDefinition p_head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(112, 29).addBox(-4.6F, -5.0F, -2.0F, 1.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(0, 38).addBox(3.6F, -5.0F, -2.0F, 1.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(68, 38).addBox(-4.0F, -4.0F, 3.6F, 8.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 18).addBox(-4.5F, -7.0F, -4.5F, 9.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)).texOffs(98, 38).addBox(-3.5F, -7.5F, -5.0F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(106, 38).addBox(0.5F, -7.5F, -5.0F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_beard = p_head.addOrReplaceChild("beard", CubeListBuilder.create().texOffs(16, 38).addBox(-4.0F, -1.0F, -1.5F, 8.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, -4.0F, 0.0F, 0.0F, 0.0F));
        p_beard.addOrReplaceChild("beard_braid", CubeListBuilder.create().texOffs(60, 38).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(86, 38).addBox(-1.5F, 2.0F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.0F, -0.5F, 0.0F, 0.0F, 0.0F));
        root.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, 0.0F, -3.0F, 10.0F, 12.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(12, 47).addBox(-3.5F, -0.5F, -3.4F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(16, 47).addBox(2.5F, -0.5F, -3.4F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(84, 18).addBox(-5.5F, 8.0F, -3.5F, 11.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)).texOffs(50, 38).addBox(4.5F, 9.0F, -2.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        p_body.addOrReplaceChild("apron", CubeListBuilder.create().texOffs(64, 0).addBox(-4.0F, 0.0F, -0.5F, 8.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, -3.2F, 0.0F, 0.0F, 0.0F));
        p_body.addOrReplaceChild("hammer", CubeListBuilder.create().texOffs(12, 38).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(36, 38).addBox(-1.5F, 6.0F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.5F, 9.0F, 0.0F, 0.0F, 0.0F, 0.15F));
        root.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(0, 29).addBox(-4.0F, -2.0F, -2.5F, 5.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(80, 29).addBox(-3.5F, 2.0F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(20, 29).addBox(-4.0F, 7.0F, -2.5F, 5.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 2.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        root.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(40, 29).addBox(-1.0F, -2.0F, -2.5F, 5.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(96, 29).addBox(-0.5F, 2.0F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(60, 29).addBox(-1.0F, 7.0F, -2.5F, 5.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, 2.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        root.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(82, 0).addBox(-2.5F, 0.0F, -2.5F, 5.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(36, 18).addBox(-3.0F, 8.0F, -3.5F, 6.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(114, 38).addBox(-2.5F, 10.0F, -4.2F, 5.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.9F, 12.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        root.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(102, 0).addBox(-2.5F, 0.0F, -2.5F, 5.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(60, 18).addBox(-3.0F, 8.0F, -3.5F, 6.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(0, 47).addBox(-2.5F, 10.0F, -4.2F, 5.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.9F, 12.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 128, 128);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks,
                          float netHeadYaw, float headPitch) {
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        this.beard.resetPose();
        this.beardBraid.resetPose();
        this.apron.resetPose();
        this.hammer.resetPose();

        float walk = Math.min(limbSwingAmount, 1.0F);
        this.apron.xRot += Math.min(0.0F, Math.min(this.rightLeg.xRot, this.leftLeg.xRot)) * 0.6F;
        this.beard.xRot += -this.head.xRot * 0.3F + Mth.sin(ageInTicks * 0.08F) * 0.02F;
        this.beardBraid.xRot += Mth.sin(ageInTicks * 0.09F + 0.5F) * 0.08F + walk * 0.25F;
        this.hammer.zRot += Mth.cos(limbSwing * 0.6662F) * 0.25F * walk;
    }
}
