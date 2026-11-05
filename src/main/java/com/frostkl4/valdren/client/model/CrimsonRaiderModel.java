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
 * O arquivo tools/models/crimson_raider_model.bbmodel abre no Blockbench.
 */
public class CrimsonRaiderModel<T extends LivingEntity> extends HumanoidModel<T> {
    public static final ModelLayerLocation LAYER =
            new ModelLayerLocation(new ResourceLocation(Valdren.MODID, "crimson_raider_model"), "main");

    private final ModelPart hoodTip;
    private final ModelPart hoodTip2;
    private final ModelPart hoodTip3;
    private final ModelPart scarfTail;
    private final ModelPart cloak;
    private final ModelPart pauldron;
    private final ModelPart drape;

    public CrimsonRaiderModel(ModelPart root) {
        super(root);
        this.hoodTip = root.getChild("head").getChild("hood_tip");
        this.hoodTip2 = root.getChild("head").getChild("hood_tip").getChild("hood_tip2");
        this.hoodTip3 = root.getChild("head").getChild("hood_tip").getChild("hood_tip2").getChild("hood_tip3");
        this.scarfTail = root.getChild("body").getChild("scarf_tail");
        this.cloak = root.getChild("body").getChild("cloak");
        this.pauldron = root.getChild("right_arm").getChild("pauldron");
        this.drape = root.getChild("left_arm").getChild("drape");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        PartDefinition p_head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(36, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(0, 0).addBox(-4.5F, -8.5F, -4.5F, 9.0F, 9.0F, 9.0F, new CubeDeformation(0.0F)).texOffs(62, 43).addBox(-4.8F, -9.0F, -5.0F, 10.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_hood_tip = p_head.addOrReplaceChild("hood_tip", CubeListBuilder.create().texOffs(0, 43).addBox(-2.5F, -1.0F, 0.0F, 5.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.5F, 3.0F, -0.3F, 0.0F, 0.0F));
        PartDefinition p_hood_tip2 = p_hood_tip.addOrReplaceChild("hood_tip2", CubeListBuilder.create().texOffs(48, 43).addBox(-1.5F, -1.0F, 0.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 4.0F, -0.5F, 0.0F, 0.0F));
        p_hood_tip2.addOrReplaceChild("hood_tip3", CubeListBuilder.create().texOffs(86, 43).addBox(-1.0F, -0.5F, 0.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 4.0F, -0.45F, 0.0F, 0.0F));
        root.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(68, 0).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(62, 34).addBox(-4.5F, -1.0F, -2.5F, 9.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(90, 34).addBox(-4.5F, 8.0F, -2.5F, 9.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(104, 43).addBox(-4.0F, 9.0F, -3.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(96, 43).addBox(1.5F, 9.0F, -3.4F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(18, 43).addBox(3.8F, 7.0F, -1.0F, 1.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        p_body.addOrReplaceChild("scarf_tail", CubeListBuilder.create().texOffs(24, 43).addBox(-1.0F, 0.0F, -0.5F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 1.0F, -2.6F, 0.0F, 0.0F, -0.1F));
        p_body.addOrReplaceChild("cloak", CubeListBuilder.create().texOffs(32, 18).addBox(-4.5F, 0.0F, 0.0F, 9.0F, 14.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, 2.3F, 0.08F, 0.0F, 0.0F));
        PartDefinition p_right_arm = root.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(92, 0).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(22, 34).addBox(-3.5F, 5.0F, -2.5F, 5.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 2.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        p_right_arm.addOrReplaceChild("pauldron", CubeListBuilder.create().texOffs(0, 34).addBox(-3.5F, -1.5F, -3.0F, 5.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(30, 43).addBox(-3.0F, -2.5F, -2.5F, 4.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -2.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_left_arm = root.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(108, 0).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(42, 34).addBox(-1.5F, 5.0F, -2.5F, 5.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, 2.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        p_left_arm.addOrReplaceChild("drape", CubeListBuilder.create().texOffs(52, 18).addBox(-2.0F, -0.5F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -2.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        root.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(72, 18).addBox(-2.5F, 7.0F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.9F, 12.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        root.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(16, 18).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(92, 18).addBox(-2.5F, 7.0F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.9F, 12.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 128, 128);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks,
                          float netHeadYaw, float headPitch) {
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        this.hoodTip.resetPose();
        this.hoodTip2.resetPose();
        this.hoodTip3.resetPose();
        this.scarfTail.resetPose();
        this.cloak.resetPose();
        this.pauldron.resetPose();
        this.drape.resetPose();

        float partial = ageInTicks - entity.tickCount;
        float hurt = entity.hurtTime > 0 ? (entity.hurtTime - partial) / 10.0F : 0.0F;
        float walk = Math.min(limbSwingAmount, 1.0F);
        this.hoodTip.xRot += -this.head.xRot * 0.4F + walk * 0.25F + Mth.sin(ageInTicks * 0.08F) * 0.04F;
        this.hoodTip2.xRot += walk * 0.2F + Mth.sin(ageInTicks * 0.08F - 0.7F) * 0.06F;
        this.hoodTip3.xRot += walk * 0.2F + Mth.sin(ageInTicks * 0.08F - 1.4F) * 0.08F;
        this.cloak.xRot += walk * 0.6F + Mth.sin(ageInTicks * 0.07F) * 0.04F;
        this.scarfTail.xRot += -walk * 0.3F + Mth.sin(ageInTicks * 0.1F) * 0.06F;
        this.head.xRot -= hurt * 0.4F;
    }
}
