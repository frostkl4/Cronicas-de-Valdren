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
 * Modelo gerado por tools/gen/build_v2.py (geometria em tools/gen/v2_bosses.py e v2_creatures.py);
 * o arquivo tools/models/frost_wraith.bbmodel abre no Blockbench (formato Modded Entity).
 */
public class FrostWraithModel extends HierarchicalModel<FrostWraith> {
    public static final ModelLayerLocation LAYER =
            new ModelLayerLocation(new ResourceLocation(Valdren.MODID, "frost_wraith"), "main");

    private final ModelPart root;
    private final ModelPart torso;
    private final ModelPart hood;
    private final ModelPart hoodTip;
    private final ModelPart hoodTip2;
    private final ModelPart hoodTip3;
    private final ModelPart hoodTip4;
    private final ModelPart skirt;
    private final ModelPart skirt2;
    private final ModelPart tatter0;
    private final ModelPart tatter1;
    private final ModelPart tatter2;
    private final ModelPart tatter3;
    private final ModelPart tatter4;
    private final ModelPart tatter5;
    private final ModelPart tatter6;
    private final ModelPart tatter7;
    private final ModelPart armR;
    private final ModelPart sleeveR;
    private final ModelPart armL;
    private final ModelPart sleeveL;
    private final ModelPart orbit;
    private final ModelPart crystal0;
    private final ModelPart crystal1;
    private final ModelPart crystal2;

    public FrostWraithModel(ModelPart root) {
        this.root = root;
        this.torso = root.getChild("torso");
        this.hood = root.getChild("torso").getChild("hood");
        this.hoodTip = root.getChild("torso").getChild("hood").getChild("hood_tip");
        this.hoodTip2 = root.getChild("torso").getChild("hood").getChild("hood_tip").getChild("hood_tip2");
        this.hoodTip3 = root.getChild("torso").getChild("hood").getChild("hood_tip").getChild("hood_tip2").getChild("hood_tip3");
        this.hoodTip4 = root.getChild("torso").getChild("hood").getChild("hood_tip").getChild("hood_tip2").getChild("hood_tip3").getChild("hood_tip4");
        this.skirt = root.getChild("torso").getChild("skirt");
        this.skirt2 = root.getChild("torso").getChild("skirt").getChild("skirt2");
        this.tatter0 = root.getChild("torso").getChild("skirt").getChild("skirt2").getChild("tatter_0");
        this.tatter1 = root.getChild("torso").getChild("skirt").getChild("skirt2").getChild("tatter_1");
        this.tatter2 = root.getChild("torso").getChild("skirt").getChild("skirt2").getChild("tatter_2");
        this.tatter3 = root.getChild("torso").getChild("skirt").getChild("skirt2").getChild("tatter_3");
        this.tatter4 = root.getChild("torso").getChild("skirt").getChild("skirt2").getChild("tatter_4");
        this.tatter5 = root.getChild("torso").getChild("skirt").getChild("skirt2").getChild("tatter_5");
        this.tatter6 = root.getChild("torso").getChild("skirt").getChild("skirt2").getChild("tatter_6");
        this.tatter7 = root.getChild("torso").getChild("skirt").getChild("skirt2").getChild("tatter_7");
        this.armR = root.getChild("torso").getChild("arm_r");
        this.sleeveR = root.getChild("torso").getChild("arm_r").getChild("sleeve_r");
        this.armL = root.getChild("torso").getChild("arm_l");
        this.sleeveL = root.getChild("torso").getChild("arm_l").getChild("sleeve_l");
        this.orbit = root.getChild("torso").getChild("orbit");
        this.crystal0 = root.getChild("torso").getChild("orbit").getChild("crystal_0");
        this.crystal1 = root.getChild("torso").getChild("orbit").getChild("crystal_1");
        this.crystal2 = root.getChild("torso").getChild("orbit").getChild("crystal_2");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        PartDefinition p_torso = root.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(64, 0).addBox(-4.0F, -10.0F, -2.5F, 8.0F, 9.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(82, 17).addBox(-6.0F, -11.0F, -3.5F, 12.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)).texOffs(0, 45).addBox(-5.5F, -14.0F, -2.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(8, 45).addBox(-4.0F, -13.0F, 1.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(12, 45).addBox(-6.0F, -13.0F, 0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(4, 45).addBox(4.5F, -14.0F, -2.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(16, 45).addBox(3.0F, -13.0F, 1.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(20, 45).addBox(5.0F, -13.0F, 0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_hood = p_torso.addOrReplaceChild("hood", CubeListBuilder.create().texOffs(36, 0).addBox(-3.5F, -7.0F, -3.5F, 7.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)).texOffs(0, 0).addBox(-4.5F, -8.0F, -4.5F, 9.0F, 8.0F, 9.0F, new CubeDeformation(0.0F)).texOffs(68, 38).addBox(-5.0F, -8.5F, -5.0F, 10.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(24, 45).addBox(-4.5F, -9.5F, -5.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(28, 45).addBox(-2.5F, -10.0F, -5.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(32, 45).addBox(-0.5F, -10.5F, -5.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(36, 45).addBox(1.5F, -10.0F, -5.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(40, 45).addBox(3.5F, -9.5F, -5.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -11.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_hood_tip = p_hood.addOrReplaceChild("hood_tip", CubeListBuilder.create().texOffs(0, 29).addBox(-3.0F, -3.0F, -3.0F, 6.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0F, 1.0F, -0.2F, 0.0F, 0.0F));
        PartDefinition p_hood_tip2 = p_hood_tip.addOrReplaceChild("hood_tip2", CubeListBuilder.create().texOffs(112, 29).addBox(-2.0F, -3.0F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, 0.5F, -0.55F, 0.0F, 0.0F));
        PartDefinition p_hood_tip3 = p_hood_tip2.addOrReplaceChild("hood_tip3", CubeListBuilder.create().texOffs(40, 38).addBox(-1.5F, -3.0F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, 0.3F, -0.75F, 0.0F, 0.0F));
        p_hood_tip3.addOrReplaceChild("hood_tip4", CubeListBuilder.create().texOffs(118, 38).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(44, 45).addBox(-0.5F, -3.5F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, -0.95F, 0.0F, 0.0F));
        PartDefinition p_skirt = p_torso.addOrReplaceChild("skirt", CubeListBuilder.create().texOffs(0, 17).addBox(-5.0F, 0.0F, -3.5F, 10.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_skirt2 = p_skirt.addOrReplaceChild("skirt2", CubeListBuilder.create().texOffs(90, 0).addBox(-5.5F, 0.0F, -4.0F, 11.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        p_skirt2.addOrReplaceChild("tatter_0", CubeListBuilder.create().texOffs(52, 38).addBox(-1.5F, 0.0F, -0.5F, 3.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(48, 45).addBox(-0.5F, 4.0F, -0.8F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.8803F, 4.0F, 1.2629F, 0.0F, 0.0F, 0.0F));
        p_skirt2.addOrReplaceChild("tatter_1", CubeListBuilder.create().texOffs(24, 29).addBox(-1.5F, 0.0F, -0.5F, 3.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.6073F, 4.0F, 3.0488F, 0.0F, 0.0F, 0.0F));
        p_skirt2.addOrReplaceChild("tatter_2", CubeListBuilder.create().texOffs(96, 29).addBox(-1.5F, 0.0F, -0.5F, 3.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(52, 45).addBox(-0.5F, 6.0F, -0.8F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.6073F, 4.0F, 3.0488F, 0.0F, 0.0F, 0.0F));
        p_skirt2.addOrReplaceChild("tatter_3", CubeListBuilder.create().texOffs(0, 38).addBox(-1.5F, 0.0F, -0.5F, 3.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.8803F, 4.0F, 1.2629F, 0.0F, 0.0F, 0.0F));
        p_skirt2.addOrReplaceChild("tatter_4", CubeListBuilder.create().texOffs(60, 38).addBox(-1.5F, 0.0F, -0.5F, 3.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(56, 45).addBox(-0.5F, 4.0F, -0.8F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.8803F, 4.0F, -1.2629F, 0.0F, 0.0F, 0.0F));
        p_skirt2.addOrReplaceChild("tatter_5", CubeListBuilder.create().texOffs(32, 29).addBox(-1.5F, 0.0F, -0.5F, 3.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.6073F, 4.0F, -3.0488F, 0.0F, 0.0F, 0.0F));
        p_skirt2.addOrReplaceChild("tatter_6", CubeListBuilder.create().texOffs(104, 29).addBox(-1.5F, 0.0F, -0.5F, 3.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(60, 45).addBox(-0.5F, 6.0F, -0.8F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.6073F, 4.0F, -3.0488F, 0.0F, 0.0F, 0.0F));
        p_skirt2.addOrReplaceChild("tatter_7", CubeListBuilder.create().texOffs(8, 38).addBox(-1.5F, 0.0F, -0.5F, 3.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.8803F, 4.0F, -1.2629F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_arm_r = p_torso.addOrReplaceChild("arm_r", CubeListBuilder.create().texOffs(72, 29).addBox(-1.5F, -1.0F, -1.5F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.5F, -9.0F, 0.0F, -0.3F, 0.0F, -0.2F));
        p_arm_r.addOrReplaceChild("sleeve_r", CubeListBuilder.create().texOffs(34, 17).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(40, 29).addBox(-2.0F, 0.5F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        p_arm_r.addOrReplaceChild("hand_r", CubeListBuilder.create().texOffs(94, 38).addBox(-1.2F, 0.0F, -1.2F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(98, 38).addBox(0.2F, 0.0F, -1.2F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(102, 38).addBox(-0.5F, 0.0F, 0.2F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_arm_l = p_torso.addOrReplaceChild("arm_l", CubeListBuilder.create().texOffs(84, 29).addBox(-1.5F, -1.0F, -1.5F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.5F, -9.0F, 0.0F, -0.3F, 0.0F, 0.2F));
        p_arm_l.addOrReplaceChild("sleeve_l", CubeListBuilder.create().texOffs(58, 17).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(56, 29).addBox(-2.0F, 0.5F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        p_arm_l.addOrReplaceChild("hand_l", CubeListBuilder.create().texOffs(106, 38).addBox(-1.2F, 0.0F, -1.2F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(110, 38).addBox(0.2F, 0.0F, -1.2F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(114, 38).addBox(-0.5F, 0.0F, 0.2F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_orbit = p_torso.addOrReplaceChild("orbit", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -4.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        p_orbit.addOrReplaceChild("crystal_0", CubeListBuilder.create().texOffs(16, 38).addBox(-1.0F, -2.5F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(64, 45).addBox(-0.5F, -4.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(76, 45).addBox(-0.5F, 2.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.0F, 0.0F, 0.0F, 0.3F, 0.0F, 0.25F));
        p_orbit.addOrReplaceChild("crystal_1", CubeListBuilder.create().texOffs(24, 38).addBox(-1.0F, -2.5F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(68, 45).addBox(-0.5F, -4.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(80, 45).addBox(-0.5F, 2.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 0.0F, 8.6603F, 0.3F, 0.0F, 0.25F));
        p_orbit.addOrReplaceChild("crystal_2", CubeListBuilder.create().texOffs(32, 38).addBox(-1.0F, -2.5F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(72, 45).addBox(-0.5F, -4.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(84, 45).addBox(-0.5F, 2.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 0.0F, -8.6603F, 0.3F, 0.0F, 0.25F));
        return LayerDefinition.create(mesh, 128, 128);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    @Override
    public void setupAnim(FrostWraith entity, float limbSwing, float limbSwingAmount, float ageInTicks,
                          float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);

        float partial = ageInTicks - entity.tickCount;
        float hurt = entity.hurtTime > 0 ? (entity.hurtTime - partial) / 10.0F : 0.0F;
        float bob = Mth.sin(ageInTicks * 0.09F);
        float walk = Math.min(limbSwingAmount, 1.0F);
        boolean casting = entity.isCasting();
        this.torso.y += bob * 1.2F;
        this.torso.xRot += walk * 0.15F - hurt * 0.2F;
        this.hood.yRot += netHeadYaw * Mth.DEG_TO_RAD * 0.8F;
        this.hood.xRot += headPitch * Mth.DEG_TO_RAD * 0.8F - hurt * 0.4F;
        this.hoodTip.xRot += Mth.sin(ageInTicks * 0.07F) * 0.05F - walk * 0.15F;
        this.hoodTip2.xRot += Mth.sin(ageInTicks * 0.07F - 0.6F) * 0.08F - walk * 0.15F;
        this.hoodTip3.xRot += Mth.sin(ageInTicks * 0.07F - 1.2F) * 0.1F - walk * 0.15F;
        this.hoodTip4.xRot += Mth.sin(ageInTicks * 0.07F - 1.8F) * 0.12F;
        float reach = this.attackTime > 0.0F ? Mth.sin(this.attackTime * Mth.PI) : 0.0F;
        float lift = casting ? 1.0F + Mth.sin(ageInTicks * 0.3F) * 0.1F : 0.0F;
        this.armR.xRot += Mth.sin(ageInTicks * 0.07F) * 0.1F - reach * 1.2F - lift;
        this.armL.xRot += Mth.sin(ageInTicks * 0.07F + 1.6F) * 0.1F - reach * 1.2F - lift;
        this.armR.zRot += casting ? -0.35F : 0.0F;
        this.armL.zRot += casting ? 0.35F : 0.0F;
        this.sleeveR.xRot += walk * 0.3F + Mth.sin(ageInTicks * 0.1F) * 0.05F;
        this.sleeveL.xRot += walk * 0.3F + Mth.sin(ageInTicks * 0.1F + 1.0F) * 0.05F;
        this.skirt.xRot += walk * 0.2F;
        this.skirt2.xRot += walk * 0.2F + Mth.sin(ageInTicks * 0.08F) * 0.03F;
        float sway = Mth.sin(ageInTicks * 0.12F) * 0.12F;
        this.tatter0.xRot += sway + walk * 0.4F;
        this.tatter1.xRot += -sway * 0.8F + walk * 0.3F;
        this.tatter2.zRot += sway * 0.9F;
        this.tatter3.xRot += sway * 0.7F + walk * 0.4F;
        this.tatter4.xRot += -sway + walk * 0.3F;
        this.tatter5.zRot += -sway * 0.9F;
        this.tatter6.xRot += sway * 0.8F + walk * 0.4F;
        this.tatter7.zRot += sway * 0.7F;
        float spin = casting ? 0.15F : 0.06F;
        this.orbit.yRot += ageInTicks * spin;
        this.crystal0.y += Mth.sin(ageInTicks * 0.15F) * 1.5F;
        this.crystal1.y += Mth.sin(ageInTicks * 0.15F + 2.1F) * 1.5F;
        this.crystal2.y += Mth.sin(ageInTicks * 0.15F + 4.2F) * 1.5F;
    }
}
