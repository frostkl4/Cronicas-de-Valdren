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
 * O arquivo tools/models/iara.bbmodel abre no Blockbench.
 */
public class IaraModel<T extends LivingEntity> extends HumanoidModel<T> {
    public static final ModelLayerLocation LAYER =
            new ModelLayerLocation(new ResourceLocation(Valdren.MODID, "iara"), "main");

    private final ModelPart scarfKnot;
    private final ModelPart scarfTail;
    private final ModelPart shawl;
    private final ModelPart apron;
    private final ModelPart skirt;
    private final ModelPart satchel;

    public IaraModel(ModelPart root) {
        super(root);
        this.scarfKnot = root.getChild("head").getChild("scarf_knot");
        this.scarfTail = root.getChild("head").getChild("scarf_knot").getChild("scarf_tail");
        this.shawl = root.getChild("body").getChild("shawl");
        this.apron = root.getChild("body").getChild("apron");
        this.skirt = root.getChild("body").getChild("skirt");
        this.satchel = root.getChild("body").getChild("satchel");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        PartDefinition p_head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(58, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(94, 30).addBox(-4.4F, -5.0F, -3.5F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(102, 30).addBox(3.4F, -5.0F, -3.5F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(90, 0).addBox(-4.5F, -8.6F, -4.5F, 9.0F, 4.0F, 9.0F, new CubeDeformation(0.0F)).texOffs(80, 17).addBox(-3.5F, -9.2F, -3.5F, 7.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)).texOffs(46, 38).addBox(3.4F, -8.4F, -3.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(12, 38).addBox(-2.0F, -3.0F, 3.8F, 4.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_scarf_knot = p_head.addOrReplaceChild("scarf_knot", CubeListBuilder.create().texOffs(22, 38).addBox(-1.5F, -1.0F, -0.5F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -6.0F, 4.6F, 0.0F, 0.0F, 0.0F));
        p_scarf_knot.addOrReplaceChild("scarf_tail", CubeListBuilder.create().texOffs(110, 30).addBox(-2.0F, 0.0F, 0.0F, 2.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(116, 30).addBox(0.2F, 0.0F, 0.0F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 0.5F, 0.15F, 0.0F, 0.0F));
        root.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, 0.0F, -2.5F, 8.0F, 12.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(56, 38).addBox(-4.2F, -0.3F, -2.8F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        p_body.addOrReplaceChild("shawl", CubeListBuilder.create().texOffs(32, 17).addBox(-5.0F, -0.5F, -3.0F, 10.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(36, 38).addBox(-2.0F, 4.5F, -3.2F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(74, 38).addBox(-0.5F, 4.0F, -3.6F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        p_body.addOrReplaceChild("apron", CubeListBuilder.create().texOffs(64, 17).addBox(-3.5F, 0.0F, -0.5F, 7.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 38).addBox(-2.5F, 3.0F, -1.3F, 5.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(52, 38).addBox(-2.0F, 1.5F, -1.4F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(32, 38).addBox(0.5F, 1.0F, -1.4F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(78, 38).addBox(1.6F, 2.0F, -1.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 7.0F, -2.8F, 0.0F, 0.0F, 0.0F));
        p_body.addOrReplaceChild("skirt", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, 0.0F, -3.0F, 10.0F, 11.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 10.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        p_body.addOrReplaceChild("satchel", CubeListBuilder.create().texOffs(18, 30).addBox(0.0F, 0.0F, -2.0F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(82, 38).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.2F, 8.0F, 0.0F, 0.0F, 0.0F, -0.1F));
        root.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(0, 17).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(30, 30).addBox(-3.5F, 6.0F, -2.5F, 5.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(70, 30).addBox(-2.5F, 8.0F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 2.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        root.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(16, 17).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(50, 30).addBox(-1.5F, 6.0F, -2.5F, 5.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(82, 30).addBox(-0.5F, 8.0F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, 2.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        root.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(108, 17).addBox(-2.0F, 9.0F, -2.5F, 4.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.9F, 12.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        root.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 30).addBox(-2.0F, 9.0F, -2.5F, 4.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.9F, 12.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 128, 128);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks,
                          float netHeadYaw, float headPitch) {
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        this.scarfKnot.resetPose();
        this.scarfTail.resetPose();
        this.shawl.resetPose();
        this.apron.resetPose();
        this.skirt.resetPose();
        this.satchel.resetPose();

        float walk = Math.min(limbSwingAmount, 1.0F);
        this.skirt.xRot += (this.rightLeg.xRot + this.leftLeg.xRot) * 0.1F;
        this.skirt.zRot += Mth.sin(limbSwing * 0.6662F) * 0.04F * walk;
        this.apron.xRot += Math.min(0.0F, Math.min(this.rightLeg.xRot, this.leftLeg.xRot)) * 0.5F;
        this.scarfTail.xRot += Mth.sin(ageInTicks * 0.08F) * 0.08F + walk * 0.35F;
        this.satchel.zRot += Mth.cos(limbSwing * 0.6662F) * 0.12F * walk;
        this.shawl.y += Mth.sin(ageInTicks * 0.06F) * 0.1F;
    }
}
