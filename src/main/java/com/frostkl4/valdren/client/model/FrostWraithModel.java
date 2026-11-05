package com.frostkl4.valdren.client.model;

import com.frostkl4.valdren.Valdren;
import com.frostkl4.valdren.entity.mob.FrostWraith;
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
 * o arquivo tools/models/frost_wraith.bbmodel abre no Blockbench (formato Modded Entity).
 */
public class FrostWraithModel extends HierarchicalModel<FrostWraith> {
    public static final ModelLayerLocation LAYER =
            new ModelLayerLocation(new ResourceLocation(Valdren.MODID, "frost_wraith"), "main");

    private final ModelPart root;
    private final ModelPart torso;
    private final ModelPart hood;
    private final ModelPart armR;
    private final ModelPart armL;
    private final ModelPart skirt;
    private final ModelPart tatter0;
    private final ModelPart tatter1;
    private final ModelPart tatter2;
    private final ModelPart tatter3;
    private final ModelPart tatter4;
    private final ModelPart tatter5;
    private final ModelPart tatter6;
    private final ModelPart tatter7;
    private final ModelPart orbit;
    private final ModelPart crystal0;
    private final ModelPart crystal1;
    private final ModelPart crystal2;

    public FrostWraithModel(ModelPart root) {
        this.root = root;
        this.torso = root.getChild("torso");
        this.hood = root.getChild("torso").getChild("hood");
        this.armR = root.getChild("torso").getChild("arm_r");
        this.armL = root.getChild("torso").getChild("arm_l");
        this.skirt = root.getChild("torso").getChild("skirt");
        this.tatter0 = root.getChild("torso").getChild("skirt").getChild("tatter_0");
        this.tatter1 = root.getChild("torso").getChild("skirt").getChild("tatter_1");
        this.tatter2 = root.getChild("torso").getChild("skirt").getChild("tatter_2");
        this.tatter3 = root.getChild("torso").getChild("skirt").getChild("tatter_3");
        this.tatter4 = root.getChild("torso").getChild("skirt").getChild("tatter_4");
        this.tatter5 = root.getChild("torso").getChild("skirt").getChild("tatter_5");
        this.tatter6 = root.getChild("torso").getChild("skirt").getChild("tatter_6");
        this.tatter7 = root.getChild("torso").getChild("skirt").getChild("tatter_7");
        this.orbit = root.getChild("torso").getChild("orbit");
        this.crystal0 = root.getChild("torso").getChild("orbit").getChild("crystal_0");
        this.crystal1 = root.getChild("torso").getChild("orbit").getChild("crystal_1");
        this.crystal2 = root.getChild("torso").getChild("orbit").getChild("crystal_2");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        PartDefinition p_torso = root.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(56, 0).addBox(-4.5F, -10.0F, -2.5F, 9.0F, 9.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(10, 16).addBox(-5.5F, -10.5F, -3.0F, 11.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(80, 27).addBox(-5.5F, -13.0F, -1.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(118, 27).addBox(-4.0F, -12.5F, 1.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(84, 27).addBox(4.5F, -13.0F, -1.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(122, 27).addBox(3.0F, -12.5F, 1.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_hood = p_torso.addOrReplaceChild("hood", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.5F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(30, 27).addBox(-4.5F, -8.5F, -5.0F, 9.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -10.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        p_hood.addOrReplaceChild("hood_peak", CubeListBuilder.create().texOffs(12, 27).addBox(-2.5F, -2.0F, -2.0F, 5.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(52, 27).addBox(-1.5F, -3.0F, -1.0F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(112, 27).addBox(-0.5F, -4.0F, 0.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0F, 2.0F, 0.95F, 0.0F, 0.0F));
        PartDefinition p_arm_r = p_torso.addOrReplaceChild("arm_r", CubeListBuilder.create().texOffs(32, 0).addBox(-1.5F, -1.0F, -1.5F, 3.0F, 12.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(56, 16).addBox(-2.0F, 7.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0F, -8.0F, 0.0F, -0.35F, 0.0F, -0.18F));
        p_arm_r.addOrReplaceChild("hand_r", CubeListBuilder.create().texOffs(64, 27).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(88, 27).addBox(-1.2F, 2.0F, -1.2F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(92, 27).addBox(0.2F, 2.0F, -1.2F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(96, 27).addBox(-0.5F, 2.0F, 0.2F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 11.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_arm_l = p_torso.addOrReplaceChild("arm_l", CubeListBuilder.create().texOffs(44, 0).addBox(-1.5F, -1.0F, -1.5F, 3.0F, 12.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(72, 16).addBox(-2.0F, 7.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0F, -8.0F, 0.0F, -0.35F, 0.0F, 0.18F));
        p_arm_l.addOrReplaceChild("hand_l", CubeListBuilder.create().texOffs(72, 27).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(100, 27).addBox(-1.2F, 2.0F, -1.2F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(104, 27).addBox(0.2F, 2.0F, -1.2F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(108, 27).addBox(-0.5F, 2.0F, 0.2F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 11.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_skirt = p_torso.addOrReplaceChild("skirt", CubeListBuilder.create().texOffs(84, 0).addBox(-5.0F, 0.0F, -3.0F, 10.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        p_skirt.addOrReplaceChild("tatter_0", CubeListBuilder.create().texOffs(88, 16).addBox(-1.0F, 0.0F, -0.5F, 2.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 6.0F, -2.6F, 0.0F, 0.0F, 0.0F));
        p_skirt.addOrReplaceChild("tatter_1", CubeListBuilder.create().texOffs(44, 16).addBox(-1.0F, 0.0F, -0.5F, 2.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.0F, -2.6F, 0.0F, 0.0F, 0.0F));
        p_skirt.addOrReplaceChild("tatter_2", CubeListBuilder.create().texOffs(0, 27).addBox(-1.0F, 0.0F, -0.5F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 6.0F, -2.6F, 0.0F, 0.0F, 0.0F));
        p_skirt.addOrReplaceChild("tatter_3", CubeListBuilder.create().texOffs(6, 27).addBox(-1.0F, 0.0F, -0.5F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 6.0F, 2.6F, 0.0F, 0.0F, 0.0F));
        p_skirt.addOrReplaceChild("tatter_4", CubeListBuilder.create().texOffs(50, 16).addBox(-1.0F, 0.0F, -0.5F, 2.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.0F, 2.6F, 0.0F, 0.0F, 0.0F));
        p_skirt.addOrReplaceChild("tatter_5", CubeListBuilder.create().texOffs(94, 16).addBox(-1.0F, 0.0F, -0.5F, 2.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 6.0F, 2.6F, 0.0F, 0.0F, 0.0F));
        p_skirt.addOrReplaceChild("tatter_6", CubeListBuilder.create().texOffs(116, 0).addBox(-0.5F, 0.0F, -2.0F, 1.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.6F, 6.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        p_skirt.addOrReplaceChild("tatter_7", CubeListBuilder.create().texOffs(0, 16).addBox(-0.5F, 0.0F, -2.0F, 1.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.6F, 6.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_orbit = p_torso.addOrReplaceChild("orbit", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -4.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        p_orbit.addOrReplaceChild("crystal_0", CubeListBuilder.create().texOffs(100, 16).addBox(-1.0F, -2.5F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 34).addBox(-0.5F, -4.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.0F, 0.0F, 0.0F, 0.3F, 0.0F, 0.25F));
        p_orbit.addOrReplaceChild("crystal_1", CubeListBuilder.create().texOffs(108, 16).addBox(-1.0F, -2.5F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(4, 34).addBox(-0.5F, -4.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.5F, 0.0F, 7.7942F, 0.3F, 0.0F, 0.25F));
        p_orbit.addOrReplaceChild("crystal_2", CubeListBuilder.create().texOffs(116, 16).addBox(-1.0F, -2.5F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(8, 34).addBox(-0.5F, -4.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.5F, 0.0F, -7.7942F, 0.3F, 0.0F, 0.25F));
        return LayerDefinition.create(mesh, 128, 64);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    @Override
    public void setupAnim(FrostWraith entity, float limbSwing, float limbSwingAmount, float ageInTicks,
                          float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);

        float bob = Mth.sin(ageInTicks * 0.09F);
        this.torso.y += bob * 1.2F;
        this.hood.yRot += netHeadYaw * Mth.DEG_TO_RAD * 0.8F;
        this.hood.xRot += headPitch * Mth.DEG_TO_RAD * 0.8F;
        float reach = this.attackTime > 0 ? Mth.sin(this.attackTime * Mth.PI) : 0.0F;
        boolean casting = entity.isCasting();
        float lift = casting ? 0.9F : 0.0F;
        this.armR.xRot += Mth.sin(ageInTicks * 0.07F) * 0.12F - reach * 1.3F - lift;
        this.armL.xRot += Mth.sin(ageInTicks * 0.07F + 1.6F) * 0.12F - reach * 1.3F - lift;
        this.armR.zRot += casting ? -0.35F : 0.0F;
        this.armL.zRot += casting ? 0.35F : 0.0F;
        float sway = Mth.sin(ageInTicks * 0.12F) * 0.12F + limbSwingAmount * 0.5F;
        this.tatter0.xRot += sway;
        this.tatter1.xRot += sway * 1.2F;
        this.tatter2.xRot += sway * 0.9F;
        this.tatter3.xRot += -sway * 0.4F;
        this.tatter4.xRot += -sway * 0.5F;
        this.tatter5.xRot += -sway * 0.45F;
        this.tatter6.zRot += sway * 0.6F;
        this.tatter7.zRot += -sway * 0.6F;
        this.skirt.xRot += limbSwingAmount * 0.25F;
        this.orbit.yRot += ageInTicks * 0.06F;
        this.crystal0.y += Mth.sin(ageInTicks * 0.15F) * 1.5F;
        this.crystal1.y += Mth.sin(ageInTicks * 0.15F + 2.1F) * 1.5F;
        this.crystal2.y += Mth.sin(ageInTicks * 0.15F + 4.2F) * 1.5F;
    }
}
