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

import java.util.Map;
import java.util.WeakHashMap;

/**
 * Vorthrax (v4): dragão de gelo e raio. Modelo gerado por tools/gen/build_v4.py
 * (geometria em v4_dragon.py, pintura em v4_paint.py); tools/models/storm_dragon.bbmodel abre no Blockbench.
 *
 * Só visual: lê do dragão apenas o que ele já expõe (voando, rugido, golpe, dano, morte, olhar).
 */
public class StormDragonModel extends HierarchicalModel<StormDragon> {
    public static final ModelLayerLocation LAYER =
            new ModelLayerLocation(new ResourceLocation(Valdren.MODID, "storm_dragon"), "main");

    /** Ordem dos ossos da asa: úmero, antebraço, pulso, dedos 0-3, pontas dos dedos 0-3 (lado esquerdo). */
    private static final float[][] WING_GROUND = {
            {0.0F, -0.6F, -1.4F},
            {0.0F, -2.2F, -0.45F},
            {0.0F, 0.2F, 0.6F},
            {0.0F, 0.3F, 0.0F},
            {0.0F, 0.5F, 0.0F},
            {0.0F, 0.7F, 0.0F},
            {0.0F, 0.9F, 0.0F},
            {0.0F, 0.0F, 0.25F},
            {0.0F, 0.0F, 0.2F},
            {0.0F, 0.0F, 0.15F},
            {0.0F, 0.0F, 0.1F}
    };
    private static final float[][] WING_FLY = {
            {0.0F, 0.1F, -0.3F},
            {0.0F, -0.05F, 0.12F},
            {0.0F, 0.0F, 0.05F},
            {0.0F, 0.0F, 0.0F},
            {0.0F, 0.0F, 0.0F},
            {0.0F, 0.0F, 0.0F},
            {0.0F, 0.0F, 0.0F},
            {0.0F, 0.0F, 0.08F},
            {0.0F, 0.0F, 0.06F},
            {0.0F, 0.0F, 0.0F},
            {0.0F, 0.0F, 0.0F}
    };
    /** Transição suave entre pousado e voando, guardada por dragão (só no cliente). */
    private static final Map<StormDragon, float[]> FLIGHT = new WeakHashMap<>();

    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart neck1;
    private final ModelPart neck2;
    private final ModelPart neck3;
    private final ModelPart neck4;
    private final ModelPart neck5;
    private final ModelPart head;
    private final ModelPart jaw;
    private final ModelPart legFr;
    private final ModelPart legFrFore;
    private final ModelPart legFrHand;
    private final ModelPart legFl;
    private final ModelPart legFlFore;
    private final ModelPart legFlHand;
    private final ModelPart legBr;
    private final ModelPart legBrShin;
    private final ModelPart legBrMeta;
    private final ModelPart legBrFoot;
    private final ModelPart legBl;
    private final ModelPart legBlShin;
    private final ModelPart legBlMeta;
    private final ModelPart legBlFoot;
    private final ModelPart wingL;
    private final ModelPart wingLFore;
    private final ModelPart wingLWrist;
    private final ModelPart wingLF0;
    private final ModelPart wingLF0Tip;
    private final ModelPart wingLF1;
    private final ModelPart wingLF1Tip;
    private final ModelPart wingLF2;
    private final ModelPart wingLF2Tip;
    private final ModelPart wingLF3;
    private final ModelPart wingLF3Tip;
    private final ModelPart wingR;
    private final ModelPart wingRFore;
    private final ModelPart wingRWrist;
    private final ModelPart wingRF0;
    private final ModelPart wingRF0Tip;
    private final ModelPart wingRF1;
    private final ModelPart wingRF1Tip;
    private final ModelPart wingRF2;
    private final ModelPart wingRF2Tip;
    private final ModelPart wingRF3;
    private final ModelPart wingRF3Tip;
    private final ModelPart tail1;
    private final ModelPart tail2;
    private final ModelPart tail3;
    private final ModelPart tail4;
    private final ModelPart tail5;
    private final ModelPart tail6;
    private final ModelPart tail7;
    private final ModelPart tail8;
    private final ModelPart tail9;
    private final ModelPart tailTip;
    private final ModelPart[] wingBonesL;
    private final ModelPart[] wingBonesR;
    private final ModelPart[] neck;
    private final ModelPart[] tail;

    public StormDragonModel(ModelPart root) {
        this.root = root;
        this.body = root.getChild("body");
        this.neck1 = root.getChild("body").getChild("neck1");
        this.neck2 = root.getChild("body").getChild("neck1").getChild("neck2");
        this.neck3 = root.getChild("body").getChild("neck1").getChild("neck2").getChild("neck3");
        this.neck4 = root.getChild("body").getChild("neck1").getChild("neck2").getChild("neck3").getChild("neck4");
        this.neck5 = root.getChild("body").getChild("neck1").getChild("neck2").getChild("neck3").getChild("neck4").getChild("neck5");
        this.head = root.getChild("body").getChild("neck1").getChild("neck2").getChild("neck3").getChild("neck4").getChild("neck5").getChild("head");
        this.jaw = root.getChild("body").getChild("neck1").getChild("neck2").getChild("neck3").getChild("neck4").getChild("neck5").getChild("head").getChild("jaw");
        this.legFr = root.getChild("body").getChild("leg_fr");
        this.legFrFore = root.getChild("body").getChild("leg_fr").getChild("leg_fr_fore");
        this.legFrHand = root.getChild("body").getChild("leg_fr").getChild("leg_fr_fore").getChild("leg_fr_hand");
        this.legFl = root.getChild("body").getChild("leg_fl");
        this.legFlFore = root.getChild("body").getChild("leg_fl").getChild("leg_fl_fore");
        this.legFlHand = root.getChild("body").getChild("leg_fl").getChild("leg_fl_fore").getChild("leg_fl_hand");
        this.legBr = root.getChild("body").getChild("leg_br");
        this.legBrShin = root.getChild("body").getChild("leg_br").getChild("leg_br_shin");
        this.legBrMeta = root.getChild("body").getChild("leg_br").getChild("leg_br_shin").getChild("leg_br_meta");
        this.legBrFoot = root.getChild("body").getChild("leg_br").getChild("leg_br_shin").getChild("leg_br_meta").getChild("leg_br_foot");
        this.legBl = root.getChild("body").getChild("leg_bl");
        this.legBlShin = root.getChild("body").getChild("leg_bl").getChild("leg_bl_shin");
        this.legBlMeta = root.getChild("body").getChild("leg_bl").getChild("leg_bl_shin").getChild("leg_bl_meta");
        this.legBlFoot = root.getChild("body").getChild("leg_bl").getChild("leg_bl_shin").getChild("leg_bl_meta").getChild("leg_bl_foot");
        this.wingL = root.getChild("body").getChild("wing_l");
        this.wingLFore = root.getChild("body").getChild("wing_l").getChild("wing_l_fore");
        this.wingLWrist = root.getChild("body").getChild("wing_l").getChild("wing_l_fore").getChild("wing_l_wrist");
        this.wingLF0 = root.getChild("body").getChild("wing_l").getChild("wing_l_fore").getChild("wing_l_wrist").getChild("wing_l_f0");
        this.wingLF0Tip = root.getChild("body").getChild("wing_l").getChild("wing_l_fore").getChild("wing_l_wrist").getChild("wing_l_f0").getChild("wing_l_f0_tip");
        this.wingLF1 = root.getChild("body").getChild("wing_l").getChild("wing_l_fore").getChild("wing_l_wrist").getChild("wing_l_f1");
        this.wingLF1Tip = root.getChild("body").getChild("wing_l").getChild("wing_l_fore").getChild("wing_l_wrist").getChild("wing_l_f1").getChild("wing_l_f1_tip");
        this.wingLF2 = root.getChild("body").getChild("wing_l").getChild("wing_l_fore").getChild("wing_l_wrist").getChild("wing_l_f2");
        this.wingLF2Tip = root.getChild("body").getChild("wing_l").getChild("wing_l_fore").getChild("wing_l_wrist").getChild("wing_l_f2").getChild("wing_l_f2_tip");
        this.wingLF3 = root.getChild("body").getChild("wing_l").getChild("wing_l_fore").getChild("wing_l_wrist").getChild("wing_l_f3");
        this.wingLF3Tip = root.getChild("body").getChild("wing_l").getChild("wing_l_fore").getChild("wing_l_wrist").getChild("wing_l_f3").getChild("wing_l_f3_tip");
        this.wingR = root.getChild("body").getChild("wing_r");
        this.wingRFore = root.getChild("body").getChild("wing_r").getChild("wing_r_fore");
        this.wingRWrist = root.getChild("body").getChild("wing_r").getChild("wing_r_fore").getChild("wing_r_wrist");
        this.wingRF0 = root.getChild("body").getChild("wing_r").getChild("wing_r_fore").getChild("wing_r_wrist").getChild("wing_r_f0");
        this.wingRF0Tip = root.getChild("body").getChild("wing_r").getChild("wing_r_fore").getChild("wing_r_wrist").getChild("wing_r_f0").getChild("wing_r_f0_tip");
        this.wingRF1 = root.getChild("body").getChild("wing_r").getChild("wing_r_fore").getChild("wing_r_wrist").getChild("wing_r_f1");
        this.wingRF1Tip = root.getChild("body").getChild("wing_r").getChild("wing_r_fore").getChild("wing_r_wrist").getChild("wing_r_f1").getChild("wing_r_f1_tip");
        this.wingRF2 = root.getChild("body").getChild("wing_r").getChild("wing_r_fore").getChild("wing_r_wrist").getChild("wing_r_f2");
        this.wingRF2Tip = root.getChild("body").getChild("wing_r").getChild("wing_r_fore").getChild("wing_r_wrist").getChild("wing_r_f2").getChild("wing_r_f2_tip");
        this.wingRF3 = root.getChild("body").getChild("wing_r").getChild("wing_r_fore").getChild("wing_r_wrist").getChild("wing_r_f3");
        this.wingRF3Tip = root.getChild("body").getChild("wing_r").getChild("wing_r_fore").getChild("wing_r_wrist").getChild("wing_r_f3").getChild("wing_r_f3_tip");
        this.tail1 = root.getChild("body").getChild("tail1");
        this.tail2 = root.getChild("body").getChild("tail1").getChild("tail2");
        this.tail3 = root.getChild("body").getChild("tail1").getChild("tail2").getChild("tail3");
        this.tail4 = root.getChild("body").getChild("tail1").getChild("tail2").getChild("tail3").getChild("tail4");
        this.tail5 = root.getChild("body").getChild("tail1").getChild("tail2").getChild("tail3").getChild("tail4").getChild("tail5");
        this.tail6 = root.getChild("body").getChild("tail1").getChild("tail2").getChild("tail3").getChild("tail4").getChild("tail5").getChild("tail6");
        this.tail7 = root.getChild("body").getChild("tail1").getChild("tail2").getChild("tail3").getChild("tail4").getChild("tail5").getChild("tail6").getChild("tail7");
        this.tail8 = root.getChild("body").getChild("tail1").getChild("tail2").getChild("tail3").getChild("tail4").getChild("tail5").getChild("tail6").getChild("tail7").getChild("tail8");
        this.tail9 = root.getChild("body").getChild("tail1").getChild("tail2").getChild("tail3").getChild("tail4").getChild("tail5").getChild("tail6").getChild("tail7").getChild("tail8").getChild("tail9");
        this.tailTip = root.getChild("body").getChild("tail1").getChild("tail2").getChild("tail3").getChild("tail4").getChild("tail5").getChild("tail6").getChild("tail7").getChild("tail8").getChild("tail9").getChild("tail_tip");
        this.wingBonesL = new ModelPart[]{this.wingL, this.wingLFore, this.wingLWrist, this.wingLF0, this.wingLF1, this.wingLF2, this.wingLF3, this.wingLF0Tip, this.wingLF1Tip, this.wingLF2Tip, this.wingLF3Tip};
        this.wingBonesR = new ModelPart[]{this.wingR, this.wingRFore, this.wingRWrist, this.wingRF0, this.wingRF1, this.wingRF2, this.wingRF3, this.wingRF0Tip, this.wingRF1Tip, this.wingRF2Tip, this.wingRF3Tip};
        this.neck = new ModelPart[]{this.neck1, this.neck2, this.neck3, this.neck4, this.neck5};
        this.tail = new ModelPart[]{this.tail1, this.tail2, this.tail3, this.tail4, this.tail5, this.tail6, this.tail7, this.tail8, this.tail9};
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        PartDefinition p_body = root.addOrReplaceChild("body", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-8.0F, -6.0F, -16.0F, 16.0F, 11.0F, 17.0F, CubeDeformation.NONE)
                .texOffs(252, 94).addBox(-6.0F, -8.0F, -16.0F, 12.0F, 2.0F, 17.0F, CubeDeformation.NONE)
                .texOffs(310, 94).addBox(-6.0F, 5.0F, -16.0F, 12.0F, 2.0F, 17.0F, CubeDeformation.NONE)
                .texOffs(444, 177).addBox(-7.0F, 1.0F, -18.0F, 6.0F, 6.0F, 6.0F, CubeDeformation.NONE)
                .texOffs(468, 177).addBox(1.0F, 1.0F, -18.0F, 6.0F, 6.0F, 6.0F, CubeDeformation.NONE)
                .texOffs(366, 177).addBox(-2.0F, 5.0F, -19.0F, 4.0F, 3.0F, 9.0F, CubeDeformation.NONE)
                .texOffs(100, 150).addBox(-9.0F, -10.0F, -15.0F, 5.0F, 5.0F, 9.0F, CubeDeformation.NONE)
                .texOffs(128, 150).addBox(4.0F, -10.0F, -15.0F, 5.0F, 5.0F, 9.0F, CubeDeformation.NONE)
                .texOffs(272, 133).addBox(-6.0F, -9.5F, -14.0F, 12.0F, 2.0F, 12.0F, CubeDeformation.NONE)
                .texOffs(316, 150).addBox(-7.0F, -5.0F, -1.0F, 14.0F, 9.0F, 4.0F, CubeDeformation.NONE)
                .texOffs(154, 239).addBox(-5.0F, -7.0F, -1.0F, 10.0F, 2.0F, 4.0F, CubeDeformation.NONE)
                .texOffs(182, 239).addBox(-5.0F, 4.0F, -1.0F, 10.0F, 2.0F, 4.0F, CubeDeformation.NONE)
                .texOffs(368, 94).addBox(-6.0F, -4.0F, 2.0F, 12.0F, 7.0F, 12.0F, CubeDeformation.NONE)
                .texOffs(408, 133).addBox(-4.0F, -6.0F, 2.0F, 8.0F, 2.0F, 12.0F, CubeDeformation.NONE)
                .texOffs(448, 133).addBox(-4.0F, 3.0F, 2.0F, 8.0F, 2.0F, 12.0F, CubeDeformation.NONE)
                .texOffs(360, 52).addBox(-7.0F, -5.0F, 13.0F, 14.0F, 8.0F, 12.0F, CubeDeformation.NONE)
                .texOffs(320, 133).addBox(-5.0F, -7.0F, 13.0F, 10.0F, 2.0F, 12.0F, CubeDeformation.NONE)
                .texOffs(364, 133).addBox(-5.0F, 3.0F, 13.0F, 10.0F, 2.0F, 12.0F, CubeDeformation.NONE)
                .texOffs(238, 201).addBox(-5.0F, -8.5F, 15.0F, 10.0F, 2.0F, 8.0F, CubeDeformation.NONE)
                .texOffs(108, 246).addBox(-5.0F, 6.5F, -15.0F, 10.0F, 1.0F, 4.0F, CubeDeformation.NONE)
                .texOffs(136, 246).addBox(-5.0F, 6.5F, -11.0F, 10.0F, 1.0F, 4.0F, CubeDeformation.NONE)
                .texOffs(164, 246).addBox(-5.0F, 6.5F, -7.0F, 10.0F, 1.0F, 4.0F, CubeDeformation.NONE)
                .texOffs(192, 246).addBox(-5.0F, 6.5F, -3.0F, 10.0F, 1.0F, 4.0F, CubeDeformation.NONE)
                .texOffs(220, 246).addBox(-4.0F, 4.5F, 1.0F, 8.0F, 1.0F, 4.0F, CubeDeformation.NONE)
                .texOffs(244, 246).addBox(-4.0F, 4.5F, 5.0F, 8.0F, 1.0F, 4.0F, CubeDeformation.NONE)
                .texOffs(268, 246).addBox(-4.0F, 4.5F, 9.0F, 8.0F, 1.0F, 4.0F, CubeDeformation.NONE)
                .texOffs(292, 246).addBox(-4.0F, 4.5F, 13.0F, 8.0F, 1.0F, 4.0F, CubeDeformation.NONE)
                .texOffs(316, 246).addBox(-4.0F, 4.5F, 17.0F, 8.0F, 1.0F, 4.0F, CubeDeformation.NONE)
                .texOffs(340, 246).addBox(-4.0F, 4.5F, 21.0F, 8.0F, 1.0F, 4.0F, CubeDeformation.NONE)
                .texOffs(152, 222).addBox(-6.5F, -3.0F, 3.0F, 1.0F, 5.0F, 4.0F, CubeDeformation.NONE)
                .texOffs(162, 222).addBox(5.5F, -3.0F, 3.0F, 1.0F, 5.0F, 4.0F, CubeDeformation.NONE)
                .texOffs(172, 222).addBox(-6.5F, -3.0F, 8.0F, 1.0F, 5.0F, 4.0F, CubeDeformation.NONE)
                .texOffs(182, 222).addBox(5.5F, -3.0F, 8.0F, 1.0F, 5.0F, 4.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        addCrest0(p_body);
        addCrest1(p_body);
        addCrest2(p_body);
        addCrest3(p_body);
        addCrest4(p_body);
        addCrest5(p_body);
        addCrest6(p_body);
        addCrest7(p_body);
        addShardsR(p_body);
        addShardsL(p_body);
        addNeck1(p_body);
        addLegFr(p_body);
        addLegFl(p_body);
        addLegBr(p_body);
        addLegBl(p_body);
        addWingL(p_body);
        addWingR(p_body);
        addTail1(p_body);
        return LayerDefinition.create(mesh, 512, 1024);
    }

    private static void addCrest0(PartDefinition p_body) {
        p_body.addOrReplaceChild("crest_0", CubeListBuilder.create()
                .texOffs(350, 239).addBox(-1.5F, -3.0F, -1.5F, 3.0F, 3.0F, 3.0F, CubeDeformation.NONE)
                .texOffs(316, 257).addBox(-1.0F, -5.0F, -1.0F, 2.0F, 2.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(18, 268).addBox(-0.5F, -6.0F, -0.5F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, -9.5F, -13.0F, -0.35F, 0.0F, 0.0F));
    }

    private static void addCrest1(PartDefinition p_body) {
        p_body.addOrReplaceChild("crest_1", CubeListBuilder.create()
                .texOffs(362, 239).addBox(-1.5F, -3.0F, -1.5F, 3.0F, 3.0F, 3.0F, CubeDeformation.NONE)
                .texOffs(324, 257).addBox(-1.0F, -5.0F, -1.0F, 2.0F, 2.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(264, 265).addBox(-0.5F, -7.0F, -0.5F, 1.0F, 2.0F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, -9.5F, -8.0F, -0.35F, 0.0F, 0.0F));
    }

    private static void addCrest2(PartDefinition p_body) {
        p_body.addOrReplaceChild("crest_2", CubeListBuilder.create()
                .texOffs(374, 239).addBox(-1.5F, -3.0F, -1.5F, 3.0F, 3.0F, 3.0F, CubeDeformation.NONE)
                .texOffs(332, 257).addBox(-1.0F, -5.0F, -1.0F, 2.0F, 2.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(22, 268).addBox(-0.5F, -6.0F, -0.5F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, -8.0F, -3.0F, -0.35F, 0.0F, 0.0F));
    }

    private static void addCrest3(PartDefinition p_body) {
        p_body.addOrReplaceChild("crest_3", CubeListBuilder.create()
                .texOffs(340, 257).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(268, 265).addBox(-0.5F, -4.0F, -0.5F, 1.0F, 2.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(26, 268).addBox(-0.5F, -5.0F, -0.5F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, -6.0F, 2.0F, -0.35F, 0.0F, 0.0F));
    }

    private static void addCrest4(PartDefinition p_body) {
        p_body.addOrReplaceChild("crest_4", CubeListBuilder.create()
                .texOffs(348, 257).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(272, 265).addBox(-0.5F, -4.0F, -0.5F, 1.0F, 2.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(30, 268).addBox(-0.5F, -5.0F, -0.5F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, -6.0F, 6.0F, -0.35F, 0.0F, 0.0F));
    }

    private static void addCrest5(PartDefinition p_body) {
        p_body.addOrReplaceChild("crest_5", CubeListBuilder.create()
                .texOffs(356, 257).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(276, 265).addBox(-0.5F, -4.0F, -0.5F, 1.0F, 2.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(34, 268).addBox(-0.5F, -5.0F, -0.5F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, -6.0F, 10.0F, -0.35F, 0.0F, 0.0F));
    }

    private static void addCrest6(PartDefinition p_body) {
        p_body.addOrReplaceChild("crest_6", CubeListBuilder.create()
                .texOffs(192, 252).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(280, 265).addBox(-0.5F, -5.0F, -0.5F, 1.0F, 2.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(38, 268).addBox(-0.5F, -6.0F, -0.5F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, -8.5F, 15.0F, -0.35F, 0.0F, 0.0F));
    }

    private static void addCrest7(PartDefinition p_body) {
        p_body.addOrReplaceChild("crest_7", CubeListBuilder.create()
                .texOffs(364, 257).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(284, 265).addBox(-0.5F, -4.0F, -0.5F, 1.0F, 2.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(42, 268).addBox(-0.5F, -5.0F, -0.5F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, -8.5F, 20.0F, -0.35F, 0.0F, 0.0F));
    }

    private static void addShardsR(PartDefinition p_body) {
        PartDefinition p_shards_r = p_body.addOrReplaceChild("shards_r", CubeListBuilder.create()
                .texOffs(14, 239).addBox(-1.0F, -5.0F, -1.0F, 2.0F, 5.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(288, 265).addBox(-0.5F, -7.0F, -0.5F, 1.0F, 2.0F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-8.0F, -6.0F, -5.0F, 0.25F, 0.0F, -0.7F));
        p_shards_r.addOrReplaceChild("shards_r_b", CubeListBuilder.create()
                .texOffs(200, 252).addBox(-0.5F, -4.0F, -0.5F, 1.0F, 4.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(46, 268).addBox(-0.5F, -5.0F, -0.5F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-1.5F, 0.0F, 2.0F, -0.4F, 0.0F, -0.35F));
    }

    private static void addShardsL(PartDefinition p_body) {
        PartDefinition p_shards_l = p_body.addOrReplaceChild("shards_l", CubeListBuilder.create()
                .texOffs(22, 239).addBox(-1.0F, -5.0F, -1.0F, 2.0F, 5.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(292, 265).addBox(-0.5F, -7.0F, -0.5F, 1.0F, 2.0F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(8.0F, -6.0F, -5.0F, 0.25F, 0.0F, 0.7F));
        p_shards_l.addOrReplaceChild("shards_l_b", CubeListBuilder.create()
                .texOffs(204, 252).addBox(-0.5F, -4.0F, -0.5F, 1.0F, 4.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(50, 268).addBox(-0.5F, -5.0F, -0.5F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(1.5F, 0.0F, 2.0F, -0.4F, 0.0F, 0.35F));
    }

    private static void addNeck1(PartDefinition p_body) {
        PartDefinition p_neck1 = p_body.addOrReplaceChild("neck1", CubeListBuilder.create()
                .texOffs(36, 133).addBox(-4.5F, -2.5F, -9.0F, 9.0F, 5.0F, 10.0F, CubeDeformation.NONE)
                .texOffs(160, 177).addBox(-2.5F, -4.5F, -9.0F, 5.0F, 2.0F, 10.0F, CubeDeformation.NONE)
                .texOffs(190, 177).addBox(-2.5F, 2.5F, -9.0F, 5.0F, 2.0F, 10.0F, CubeDeformation.NONE)
                .texOffs(258, 212).addBox(-3.0F, 4.0F, -8.0F, 6.0F, 1.0F, 8.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, -4.0F, -15.0F, -0.95F, 0.0F, 0.0F));
        p_neck1.addOrReplaceChild("neck1_spike", CubeListBuilder.create()
                .texOffs(372, 257).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(296, 265).addBox(-0.5F, -4.0F, -0.5F, 1.0F, 2.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(54, 268).addBox(-0.5F, -5.0F, -0.5F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, -4.5F, -4.0F, -0.35F, 0.0F, 0.0F));
        PartDefinition p_neck2 = p_neck1.addOrReplaceChild("neck2", CubeListBuilder.create()
                .texOffs(0, 164).addBox(-4.0F, -2.0F, -8.0F, 8.0F, 4.0F, 9.0F, CubeDeformation.NONE)
                .texOffs(280, 189).addBox(-2.0F, -4.0F, -8.0F, 4.0F, 2.0F, 9.0F, CubeDeformation.NONE)
                .texOffs(306, 189).addBox(-2.0F, 2.0F, -8.0F, 4.0F, 2.0F, 9.0F, CubeDeformation.NONE)
                .texOffs(296, 222).addBox(-2.5F, 3.5F, -7.0F, 5.0F, 1.0F, 7.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, -8.0F, -0.35F, 0.0F, 0.0F));
        p_neck2.addOrReplaceChild("neck2_spike", CubeListBuilder.create()
                .texOffs(380, 257).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(300, 265).addBox(-0.5F, -4.0F, -0.5F, 1.0F, 2.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(58, 268).addBox(-0.5F, -5.0F, -0.5F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, -4.0F, -3.5F, -0.35F, 0.0F, 0.0F));
        PartDefinition p_neck3 = p_neck2.addOrReplaceChild("neck3", CubeListBuilder.create()
                .texOffs(34, 164).addBox(-4.0F, -2.0F, -8.0F, 8.0F, 4.0F, 9.0F, CubeDeformation.NONE)
                .texOffs(332, 189).addBox(-2.0F, -4.0F, -8.0F, 4.0F, 2.0F, 9.0F, CubeDeformation.NONE)
                .texOffs(358, 189).addBox(-2.0F, 2.0F, -8.0F, 4.0F, 2.0F, 9.0F, CubeDeformation.NONE)
                .texOffs(320, 222).addBox(-2.5F, 3.5F, -7.0F, 5.0F, 1.0F, 7.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, -7.0F, 0.15F, 0.0F, 0.0F));
        p_neck3.addOrReplaceChild("neck3_spike", CubeListBuilder.create()
                .texOffs(388, 257).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(304, 265).addBox(-0.5F, -4.0F, -0.5F, 1.0F, 2.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(62, 268).addBox(-0.5F, -5.0F, -0.5F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, -4.0F, -3.5F, -0.35F, 0.0F, 0.0F));
        PartDefinition p_neck4 = p_neck3.addOrReplaceChild("neck4", CubeListBuilder.create()
                .texOffs(0, 177).addBox(-3.5F, -1.5F, -8.0F, 7.0F, 3.0F, 9.0F, CubeDeformation.NONE)
                .texOffs(462, 189).addBox(-1.5F, -3.5F, -8.0F, 3.0F, 2.0F, 9.0F, CubeDeformation.NONE)
                .texOffs(486, 189).addBox(-1.5F, 1.5F, -8.0F, 3.0F, 2.0F, 9.0F, CubeDeformation.NONE)
                .texOffs(344, 222).addBox(-2.0F, 3.0F, -7.0F, 4.0F, 1.0F, 7.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, -7.0F, 0.45F, 0.0F, 0.0F));
        p_neck4.addOrReplaceChild("neck4_spike", CubeListBuilder.create()
                .texOffs(396, 257).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(308, 265).addBox(-0.5F, -4.0F, -0.5F, 1.0F, 2.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(66, 268).addBox(-0.5F, -5.0F, -0.5F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, -3.5F, -3.5F, -0.35F, 0.0F, 0.0F));
        PartDefinition p_neck5 = p_neck4.addOrReplaceChild("neck5", CubeListBuilder.create()
                .texOffs(166, 189).addBox(-3.5F, -1.5F, -7.0F, 7.0F, 3.0F, 8.0F, CubeDeformation.NONE)
                .texOffs(414, 201).addBox(-1.5F, -3.5F, -7.0F, 3.0F, 2.0F, 8.0F, CubeDeformation.NONE)
                .texOffs(436, 201).addBox(-1.5F, 1.5F, -7.0F, 3.0F, 2.0F, 8.0F, CubeDeformation.NONE)
                .texOffs(168, 231).addBox(-2.0F, 3.0F, -6.0F, 4.0F, 1.0F, 6.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, -7.0F, 0.45F, 0.0F, 0.0F));
        p_neck5.addOrReplaceChild("neck5_spike", CubeListBuilder.create()
                .texOffs(404, 257).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(312, 265).addBox(-0.5F, -4.0F, -0.5F, 1.0F, 2.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(70, 268).addBox(-0.5F, -5.0F, -0.5F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, -3.5F, -3.0F, -0.35F, 0.0F, 0.0F));
        PartDefinition p_head = p_neck5.addOrReplaceChild("head", CubeListBuilder.create()
                .texOffs(0, 150).addBox(-5.0F, -3.0F, -9.0F, 10.0F, 4.0F, 10.0F, CubeDeformation.NONE)
                .texOffs(32, 177).addBox(-3.0F, -5.0F, -9.0F, 6.0F, 2.0F, 10.0F, CubeDeformation.NONE)
                .texOffs(64, 177).addBox(-3.0F, 1.0F, -9.0F, 6.0F, 2.0F, 10.0F, CubeDeformation.NONE)
                .texOffs(228, 212).addBox(-4.0F, -6.5F, -7.0F, 8.0F, 2.0F, 7.0F, CubeDeformation.NONE)
                .texOffs(384, 212).addBox(-6.0F, -6.0F, -12.0F, 4.0F, 3.0F, 6.0F, CubeDeformation.NONE)
                .texOffs(404, 212).addBox(2.0F, -6.0F, -12.0F, 4.0F, 3.0F, 6.0F, CubeDeformation.NONE)
                .texOffs(412, 257).addBox(-6.0F, -3.5F, -11.0F, 1.0F, 1.0F, 3.0F, CubeDeformation.NONE)
                .texOffs(420, 257).addBox(5.0F, -3.5F, -11.0F, 1.0F, 1.0F, 3.0F, CubeDeformation.NONE)
                .texOffs(84, 212).addBox(-6.0F, -2.5F, -10.0F, 2.0F, 4.0F, 6.0F, CubeDeformation.NONE)
                .texOffs(100, 212).addBox(4.0F, -2.5F, -10.0F, 2.0F, 4.0F, 6.0F, CubeDeformation.NONE)
                .texOffs(68, 164).addBox(-4.0F, -3.0F, -18.0F, 8.0F, 4.0F, 9.0F, CubeDeformation.NONE)
                .texOffs(274, 201).addBox(-3.0F, -4.0F, -18.0F, 6.0F, 1.0F, 9.0F, CubeDeformation.NONE)
                .texOffs(304, 201).addBox(-3.0F, 1.0F, -18.0F, 6.0F, 1.0F, 9.0F, CubeDeformation.NONE)
                .texOffs(410, 222).addBox(-3.0F, -3.5F, -21.0F, 6.0F, 5.0F, 3.0F, CubeDeformation.NONE)
                .texOffs(386, 239).addBox(-2.0F, -3.0F, -23.0F, 4.0F, 4.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(134, 164).addBox(-1.0F, -5.0F, -21.0F, 2.0F, 1.0F, 12.0F, CubeDeformation.NONE)
                .texOffs(316, 265).addBox(-3.5F, 1.5F, -21.0F, 1.0F, 2.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(320, 265).addBox(2.5F, 1.5F, -21.0F, 1.0F, 2.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(88, 261).addBox(-3.5F, 1.5F, -19.0F, 1.0F, 3.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(92, 261).addBox(2.5F, 1.5F, -19.0F, 1.0F, 3.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(324, 265).addBox(-3.5F, 2.0F, -17.0F, 1.0F, 2.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(328, 265).addBox(2.5F, 2.0F, -17.0F, 1.0F, 2.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(96, 261).addBox(-3.5F, 2.0F, -15.0F, 1.0F, 3.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(100, 261).addBox(2.5F, 2.0F, -15.0F, 1.0F, 3.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(332, 265).addBox(-3.5F, 2.0F, -13.0F, 1.0F, 2.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(336, 265).addBox(2.5F, 2.0F, -13.0F, 1.0F, 2.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(104, 261).addBox(-3.5F, 2.0F, -11.0F, 1.0F, 3.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(108, 261).addBox(2.5F, 2.0F, -11.0F, 1.0F, 3.0F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, -6.0F, 0.25F, 0.0F, 0.0F));
        p_head.addOrReplaceChild("nose_spike", CubeListBuilder.create()
                .texOffs(312, 261).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 1.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(74, 268).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(78, 268).addBox(-0.5F, -3.0F, -0.5F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, -5.0F, -19.0F, -0.6F, 0.0F, 0.0F));
        p_head.addOrReplaceChild("crest_h0", CubeListBuilder.create()
                .texOffs(428, 257).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(82, 268).addBox(-0.5F, -3.0F, -0.5F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(86, 268).addBox(-0.5F, -4.0F, -0.5F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, -6.5F, -8.0F, -0.75F, 0.0F, 0.0F));
        p_head.addOrReplaceChild("crest_h1", CubeListBuilder.create()
                .texOffs(436, 257).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(340, 265).addBox(-0.5F, -4.0F, -0.5F, 1.0F, 2.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(90, 268).addBox(-0.5F, -5.0F, -0.5F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, -6.5F, -5.0F, -0.75F, 0.0F, 0.0F));
        p_head.addOrReplaceChild("crest_h2", CubeListBuilder.create()
                .texOffs(444, 257).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(344, 265).addBox(-0.5F, -4.0F, -0.5F, 1.0F, 2.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(94, 268).addBox(-0.5F, -5.0F, -0.5F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, -6.5F, -2.0F, -0.75F, 0.0F, 0.0F));
        PartDefinition p_horn_r = p_head.addOrReplaceChild("horn_r", CubeListBuilder.create()
                .texOffs(80, 222).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 6.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-3.5F, -6.0F, -3.0F, 0.55F, -0.28F, 0.0F));
        PartDefinition p_horn_r_2 = p_horn_r.addOrReplaceChild("horn_r_2", CubeListBuilder.create()
                .texOffs(98, 222).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 6.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, 5.5F, 0.12F, -0.04F, 0.0F));
        PartDefinition p_horn_r_3 = p_horn_r_2.addOrReplaceChild("horn_r_3", CubeListBuilder.create()
                .texOffs(72, 231).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 6.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, 5.5F, 0.12F, -0.04F, 0.0F));
        PartDefinition p_horn_r_4 = p_horn_r_3.addOrReplaceChild("horn_r_4", CubeListBuilder.create()
                .texOffs(392, 231).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 5.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, 5.5F, 0.14F, -0.02F, 0.0F));
        p_horn_r_4.addOrReplaceChild("horn_r_5", CubeListBuilder.create()
                .texOffs(398, 239).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 5.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, 4.5F, 0.16F, 0.0F, 0.0F));
        PartDefinition p_crown_r0 = p_head.addOrReplaceChild("crown_r0", CubeListBuilder.create()
                .texOffs(406, 231).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 5.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-5.0F, -4.0F, 0.0F, 0.35F, -0.55F, 0.0F));
        PartDefinition p_crown_r0_2 = p_crown_r0.addOrReplaceChild("crown_r0_2", CubeListBuilder.create()
                .texOffs(72, 252).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 4.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, 4.5F, 0.1F, -0.05F, 0.0F));
        p_crown_r0_2.addOrReplaceChild("crown_r0_3", CubeListBuilder.create()
                .texOffs(464, 261).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, 3.5F, 0.05F, 0.0F, 0.0F));
        PartDefinition p_crown_r1 = p_head.addOrReplaceChild("crown_r1", CubeListBuilder.create()
                .texOffs(420, 231).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 5.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-5.5F, -2.0F, -1.0F, 0.12F, -0.85F, 0.0F));
        PartDefinition p_crown_r1_2 = p_crown_r1.addOrReplaceChild("crown_r1_2", CubeListBuilder.create()
                .texOffs(82, 252).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 4.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, 4.5F, 0.1F, -0.05F, 0.0F));
        p_crown_r1_2.addOrReplaceChild("crown_r1_3", CubeListBuilder.create()
                .texOffs(470, 261).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, 3.5F, 0.05F, 0.0F, 0.0F));
        PartDefinition p_crown_r2 = p_head.addOrReplaceChild("crown_r2", CubeListBuilder.create()
                .texOffs(410, 239).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 4.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-5.0F, 0.0F, -2.0F, -0.08F, -1.1F, 0.0F));
        PartDefinition p_crown_r2_2 = p_crown_r2.addOrReplaceChild("crown_r2_2", CubeListBuilder.create()
                .texOffs(452, 257).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 3.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, 3.5F, 0.1F, -0.05F, 0.0F));
        p_crown_r2_2.addOrReplaceChild("crown_r2_3", CubeListBuilder.create()
                .texOffs(476, 261).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, 2.5F, 0.05F, 0.0F, 0.0F));
        PartDefinition p_crown_r3 = p_head.addOrReplaceChild("crown_r3", CubeListBuilder.create()
                .texOffs(434, 231).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 5.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-3.5F, -5.0F, 1.0F, 0.75F, -0.3F, 0.0F));
        PartDefinition p_crown_r3_2 = p_crown_r3.addOrReplaceChild("crown_r3_2", CubeListBuilder.create()
                .texOffs(92, 252).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 4.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, 4.5F, 0.1F, -0.05F, 0.0F));
        p_crown_r3_2.addOrReplaceChild("crown_r3_3", CubeListBuilder.create()
                .texOffs(482, 261).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, 3.5F, 0.05F, 0.0F, 0.0F));
        PartDefinition p_cheek_r0 = p_head.addOrReplaceChild("cheek_r0", CubeListBuilder.create()
                .texOffs(422, 239).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 4.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-5.5F, 0.0F, -6.0F, 0.1F, -1.25F, 0.0F));
        PartDefinition p_cheek_r0_2 = p_cheek_r0.addOrReplaceChild("cheek_r0_2", CubeListBuilder.create()
                .texOffs(460, 257).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 3.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, 3.5F, 0.0F, -0.15F, 0.0F));
        p_cheek_r0_2.addOrReplaceChild("cheek_r0_3", CubeListBuilder.create()
                .texOffs(488, 261).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, 2.5F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_cheek_r1 = p_head.addOrReplaceChild("cheek_r1", CubeListBuilder.create()
                .texOffs(434, 239).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 4.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-5.5F, 1.5F, -3.0F, -0.15F, -1.35F, 0.0F));
        PartDefinition p_cheek_r1_2 = p_cheek_r1.addOrReplaceChild("cheek_r1_2", CubeListBuilder.create()
                .texOffs(468, 257).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 3.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, 3.5F, 0.0F, -0.15F, 0.0F));
        p_cheek_r1_2.addOrReplaceChild("cheek_r1_3", CubeListBuilder.create()
                .texOffs(494, 261).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, 2.5F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_cheek_r2 = p_head.addOrReplaceChild("cheek_r2", CubeListBuilder.create()
                .texOffs(446, 239).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 4.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-5.5F, -1.5F, -8.5F, 0.25F, -1.1F, 0.0F));
        PartDefinition p_cheek_r2_2 = p_cheek_r2.addOrReplaceChild("cheek_r2_2", CubeListBuilder.create()
                .texOffs(476, 257).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 3.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, 3.5F, 0.0F, -0.15F, 0.0F));
        p_cheek_r2_2.addOrReplaceChild("cheek_r2_3", CubeListBuilder.create()
                .texOffs(500, 261).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, 2.5F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_horn_l = p_head.addOrReplaceChild("horn_l", CubeListBuilder.create()
                .texOffs(116, 222).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 6.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(3.5F, -6.0F, -3.0F, 0.55F, 0.28F, 0.0F));
        PartDefinition p_horn_l_2 = p_horn_l.addOrReplaceChild("horn_l_2", CubeListBuilder.create()
                .texOffs(134, 222).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 6.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, 5.5F, 0.12F, 0.04F, 0.0F));
        PartDefinition p_horn_l_3 = p_horn_l_2.addOrReplaceChild("horn_l_3", CubeListBuilder.create()
                .texOffs(88, 231).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 6.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, 5.5F, 0.12F, 0.04F, 0.0F));
        PartDefinition p_horn_l_4 = p_horn_l_3.addOrReplaceChild("horn_l_4", CubeListBuilder.create()
                .texOffs(448, 231).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 5.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, 5.5F, 0.14F, 0.02F, 0.0F));
        p_horn_l_4.addOrReplaceChild("horn_l_5", CubeListBuilder.create()
                .texOffs(458, 239).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 5.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, 4.5F, 0.16F, 0.0F, 0.0F));
        PartDefinition p_crown_l0 = p_head.addOrReplaceChild("crown_l0", CubeListBuilder.create()
                .texOffs(462, 231).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 5.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(5.0F, -4.0F, 0.0F, 0.35F, 0.55F, 0.0F));
        PartDefinition p_crown_l0_2 = p_crown_l0.addOrReplaceChild("crown_l0_2", CubeListBuilder.create()
                .texOffs(102, 252).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 4.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, 4.5F, 0.1F, 0.05F, 0.0F));
        p_crown_l0_2.addOrReplaceChild("crown_l0_3", CubeListBuilder.create()
                .texOffs(506, 261).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, 3.5F, 0.05F, 0.0F, 0.0F));
        PartDefinition p_crown_l1 = p_head.addOrReplaceChild("crown_l1", CubeListBuilder.create()
                .texOffs(476, 231).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 5.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(5.5F, -2.0F, -1.0F, 0.12F, 0.85F, 0.0F));
        PartDefinition p_crown_l1_2 = p_crown_l1.addOrReplaceChild("crown_l1_2", CubeListBuilder.create()
                .texOffs(112, 252).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 4.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, 4.5F, 0.1F, 0.05F, 0.0F));
        p_crown_l1_2.addOrReplaceChild("crown_l1_3", CubeListBuilder.create()
                .texOffs(0, 265).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, 3.5F, 0.05F, 0.0F, 0.0F));
        PartDefinition p_crown_l2 = p_head.addOrReplaceChild("crown_l2", CubeListBuilder.create()
                .texOffs(470, 239).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 4.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(5.0F, 0.0F, -2.0F, -0.08F, 1.1F, 0.0F));
        PartDefinition p_crown_l2_2 = p_crown_l2.addOrReplaceChild("crown_l2_2", CubeListBuilder.create()
                .texOffs(484, 257).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 3.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, 3.5F, 0.1F, 0.05F, 0.0F));
        p_crown_l2_2.addOrReplaceChild("crown_l2_3", CubeListBuilder.create()
                .texOffs(6, 265).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, 2.5F, 0.05F, 0.0F, 0.0F));
        PartDefinition p_crown_l3 = p_head.addOrReplaceChild("crown_l3", CubeListBuilder.create()
                .texOffs(490, 231).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 5.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(3.5F, -5.0F, 1.0F, 0.75F, 0.3F, 0.0F));
        PartDefinition p_crown_l3_2 = p_crown_l3.addOrReplaceChild("crown_l3_2", CubeListBuilder.create()
                .texOffs(122, 252).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 4.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, 4.5F, 0.1F, 0.05F, 0.0F));
        p_crown_l3_2.addOrReplaceChild("crown_l3_3", CubeListBuilder.create()
                .texOffs(12, 265).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, 3.5F, 0.05F, 0.0F, 0.0F));
        PartDefinition p_cheek_l0 = p_head.addOrReplaceChild("cheek_l0", CubeListBuilder.create()
                .texOffs(482, 239).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 4.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(5.5F, 0.0F, -6.0F, 0.1F, 1.25F, 0.0F));
        PartDefinition p_cheek_l0_2 = p_cheek_l0.addOrReplaceChild("cheek_l0_2", CubeListBuilder.create()
                .texOffs(492, 257).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 3.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, 3.5F, 0.0F, 0.15F, 0.0F));
        p_cheek_l0_2.addOrReplaceChild("cheek_l0_3", CubeListBuilder.create()
                .texOffs(18, 265).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, 2.5F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_cheek_l1 = p_head.addOrReplaceChild("cheek_l1", CubeListBuilder.create()
                .texOffs(494, 239).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 4.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(5.5F, 1.5F, -3.0F, -0.15F, 1.35F, 0.0F));
        PartDefinition p_cheek_l1_2 = p_cheek_l1.addOrReplaceChild("cheek_l1_2", CubeListBuilder.create()
                .texOffs(500, 257).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 3.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, 3.5F, 0.0F, 0.15F, 0.0F));
        p_cheek_l1_2.addOrReplaceChild("cheek_l1_3", CubeListBuilder.create()
                .texOffs(24, 265).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, 2.5F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_cheek_l2 = p_head.addOrReplaceChild("cheek_l2", CubeListBuilder.create()
                .texOffs(0, 246).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 4.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(5.5F, -1.5F, -8.5F, 0.25F, 1.1F, 0.0F));
        PartDefinition p_cheek_l2_2 = p_cheek_l2.addOrReplaceChild("cheek_l2_2", CubeListBuilder.create()
                .texOffs(0, 261).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 3.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, 3.5F, 0.0F, 0.15F, 0.0F));
        p_cheek_l2_2.addOrReplaceChild("cheek_l2_3", CubeListBuilder.create()
                .texOffs(30, 265).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, 2.5F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_jaw = p_head.addOrReplaceChild("jaw", CubeListBuilder.create()
                .texOffs(200, 150).addBox(-4.0F, 0.0F, -11.0F, 8.0F, 1.0F, 12.0F, CubeDeformation.NONE)
                .texOffs(352, 150).addBox(-3.0F, -1.0F, -11.0F, 6.0F, 1.0F, 12.0F, CubeDeformation.NONE)
                .texOffs(388, 150).addBox(-3.0F, 1.0F, -11.0F, 6.0F, 1.0F, 12.0F, CubeDeformation.NONE)
                .texOffs(364, 246).addBox(-3.0F, -1.0F, -13.0F, 6.0F, 3.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(380, 246).addBox(-2.0F, -1.0F, -15.0F, 4.0F, 3.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(362, 201).addBox(-2.0F, 2.0F, -12.0F, 4.0F, 1.0F, 9.0F, CubeDeformation.NONE)
                .texOffs(196, 189).addBox(-2.0F, -1.5F, -12.0F, 4.0F, 1.0F, 10.0F, CubeDeformation.NONE)
                .texOffs(348, 265).addBox(-3.0F, -3.0F, -13.0F, 1.0F, 2.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(352, 265).addBox(2.0F, -3.0F, -13.0F, 1.0F, 2.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(112, 261).addBox(-3.0F, -4.0F, -11.0F, 1.0F, 3.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(116, 261).addBox(2.0F, -4.0F, -11.0F, 1.0F, 3.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(356, 265).addBox(-3.0F, -3.0F, -9.0F, 1.0F, 2.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(360, 265).addBox(2.0F, -3.0F, -9.0F, 1.0F, 2.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(120, 261).addBox(-3.0F, -4.0F, -7.0F, 1.0F, 3.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(124, 261).addBox(2.0F, -4.0F, -7.0F, 1.0F, 3.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(364, 265).addBox(-3.0F, -3.0F, -5.0F, 1.0F, 2.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(368, 265).addBox(2.0F, -3.0F, -5.0F, 1.0F, 2.0F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 2.0F, -8.0F, 0.1F, 0.0F, 0.0F));
        PartDefinition p_jaw_spike_r = p_jaw.addOrReplaceChild("jaw_spike_r", CubeListBuilder.create()
                .texOffs(132, 252).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 3.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-3.5F, 1.5F, -2.0F, -0.6F, -0.5F, 0.0F));
        PartDefinition p_jaw_spike_r_2 = p_jaw_spike_r.addOrReplaceChild("jaw_spike_r_2", CubeListBuilder.create()
                .texOffs(8, 261).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 3.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, 2.5F, -0.2F, 0.0F, 0.0F));
        p_jaw_spike_r_2.addOrReplaceChild("jaw_spike_r_3", CubeListBuilder.create()
                .texOffs(36, 265).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, 2.5F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_jaw_spike_l = p_jaw.addOrReplaceChild("jaw_spike_l", CubeListBuilder.create()
                .texOffs(142, 252).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 3.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(3.5F, 1.5F, -2.0F, -0.6F, 0.5F, 0.0F));
        PartDefinition p_jaw_spike_l_2 = p_jaw_spike_l.addOrReplaceChild("jaw_spike_l_2", CubeListBuilder.create()
                .texOffs(16, 261).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 3.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, 2.5F, -0.2F, 0.0F, 0.0F));
        p_jaw_spike_l_2.addOrReplaceChild("jaw_spike_l_3", CubeListBuilder.create()
                .texOffs(42, 265).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, 2.5F, 0.0F, 0.0F, 0.0F));
    }

    private static void addLegFr(PartDefinition p_body) {
        PartDefinition p_leg_fr = p_body.addOrReplaceChild("leg_fr", CubeListBuilder.create()
                .texOffs(156, 150).addBox(-3.0F, -4.0F, -2.5F, 6.0F, 9.0F, 5.0F, CubeDeformation.NONE)
                .texOffs(140, 212).addBox(-2.0F, -4.0F, -3.5F, 4.0F, 9.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(150, 212).addBox(-2.0F, -4.0F, 2.5F, 4.0F, 9.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(286, 212).addBox(-3.5F, -5.0F, -3.0F, 7.0F, 3.0F, 6.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-8.0F, 10.3746F, -11.0F, 0.3F, 0.0F, 0.0F));
        PartDefinition p_leg_fr_fore = p_leg_fr.addOrReplaceChild("leg_fr_fore", CubeListBuilder.create()
                .texOffs(72, 189).addBox(-2.0F, 0.0F, -1.5F, 4.0F, 9.0F, 3.0F, CubeDeformation.NONE)
                .texOffs(180, 212).addBox(-1.0F, 0.0F, -2.5F, 2.0F, 9.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(186, 212).addBox(-1.0F, 0.0F, 1.5F, 2.0F, 9.0F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 5.0F, 0.0F, -0.6F, 0.0F, 0.0F));
        p_leg_fr_fore.addOrReplaceChild("leg_fr_elbow", CubeListBuilder.create()
                .texOffs(320, 261).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 1.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(98, 268).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(102, 268).addBox(-0.5F, -3.0F, -0.5F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 1.0F, 2.0F, -1.4F, 0.0F, 0.0F));
        PartDefinition p_leg_fr_hand = p_leg_fr_fore.addOrReplaceChild("leg_fr_hand", CubeListBuilder.create()
                .texOffs(366, 222).addBox(-2.5F, 0.0F, -3.5F, 5.0F, 2.0F, 6.0F, CubeDeformation.NONE)
                .texOffs(48, 265).addBox(-2.0F, 0.5F, -5.5F, 1.0F, 1.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(54, 265).addBox(-0.5F, 0.5F, -5.5F, 1.0F, 1.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(60, 265).addBox(1.0F, 0.5F, -5.5F, 1.0F, 1.0F, 2.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 8.5F, 0.0F, 0.3F, 0.0F, 0.0F));
        PartDefinition p_leg_fr_claw0 = p_leg_fr_hand.addOrReplaceChild("leg_fr_claw0", CubeListBuilder.create()
                .texOffs(66, 265).addBox(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 2.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-1.5F, 1.0F, -5.5F, 0.5F, -0.125F, 0.0F));
        p_leg_fr_claw0.addOrReplaceChild("leg_fr_claw0_2", CubeListBuilder.create()
                .texOffs(72, 265).addBox(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 2.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, -1.5F, 0.6F, 0.0F, 0.0F));
        PartDefinition p_leg_fr_claw1 = p_leg_fr_hand.addOrReplaceChild("leg_fr_claw1", CubeListBuilder.create()
                .texOffs(78, 265).addBox(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 2.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 1.0F, -5.5F, 0.5F, 0.0F, 0.0F));
        p_leg_fr_claw1.addOrReplaceChild("leg_fr_claw1_2", CubeListBuilder.create()
                .texOffs(84, 265).addBox(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 2.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, -1.5F, 0.6F, 0.0F, 0.0F));
        PartDefinition p_leg_fr_claw2 = p_leg_fr_hand.addOrReplaceChild("leg_fr_claw2", CubeListBuilder.create()
                .texOffs(90, 265).addBox(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 2.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(1.5F, 1.0F, -5.5F, 0.5F, 0.125F, 0.0F));
        p_leg_fr_claw2.addOrReplaceChild("leg_fr_claw2_2", CubeListBuilder.create()
                .texOffs(96, 265).addBox(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 2.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, -1.5F, 0.6F, 0.0F, 0.0F));
    }

    private static void addLegFl(PartDefinition p_body) {
        PartDefinition p_leg_fl = p_body.addOrReplaceChild("leg_fl", CubeListBuilder.create()
                .texOffs(178, 150).addBox(-3.0F, -4.0F, -2.5F, 6.0F, 9.0F, 5.0F, CubeDeformation.NONE)
                .texOffs(160, 212).addBox(-2.0F, -4.0F, -3.5F, 4.0F, 9.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(170, 212).addBox(-2.0F, -4.0F, 2.5F, 4.0F, 9.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(312, 212).addBox(-3.5F, -5.0F, -3.0F, 7.0F, 3.0F, 6.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(8.0F, 10.3746F, -11.0F, 0.3F, 0.0F, 0.0F));
        PartDefinition p_leg_fl_fore = p_leg_fl.addOrReplaceChild("leg_fl_fore", CubeListBuilder.create()
                .texOffs(86, 189).addBox(-2.0F, 0.0F, -1.5F, 4.0F, 9.0F, 3.0F, CubeDeformation.NONE)
                .texOffs(192, 212).addBox(-1.0F, 0.0F, -2.5F, 2.0F, 9.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(198, 212).addBox(-1.0F, 0.0F, 1.5F, 2.0F, 9.0F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 5.0F, 0.0F, -0.6F, 0.0F, 0.0F));
        p_leg_fl_fore.addOrReplaceChild("leg_fl_elbow", CubeListBuilder.create()
                .texOffs(328, 261).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 1.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(106, 268).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(110, 268).addBox(-0.5F, -3.0F, -0.5F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 1.0F, 2.0F, -1.4F, 0.0F, 0.0F));
        PartDefinition p_leg_fl_hand = p_leg_fl_fore.addOrReplaceChild("leg_fl_hand", CubeListBuilder.create()
                .texOffs(388, 222).addBox(-2.5F, 0.0F, -3.5F, 5.0F, 2.0F, 6.0F, CubeDeformation.NONE)
                .texOffs(102, 265).addBox(-2.0F, 0.5F, -5.5F, 1.0F, 1.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(108, 265).addBox(-0.5F, 0.5F, -5.5F, 1.0F, 1.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(114, 265).addBox(1.0F, 0.5F, -5.5F, 1.0F, 1.0F, 2.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 8.5F, 0.0F, 0.3F, 0.0F, 0.0F));
        PartDefinition p_leg_fl_claw0 = p_leg_fl_hand.addOrReplaceChild("leg_fl_claw0", CubeListBuilder.create()
                .texOffs(120, 265).addBox(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 2.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-1.5F, 1.0F, -5.5F, 0.5F, -0.125F, 0.0F));
        p_leg_fl_claw0.addOrReplaceChild("leg_fl_claw0_2", CubeListBuilder.create()
                .texOffs(126, 265).addBox(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 2.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, -1.5F, 0.6F, 0.0F, 0.0F));
        PartDefinition p_leg_fl_claw1 = p_leg_fl_hand.addOrReplaceChild("leg_fl_claw1", CubeListBuilder.create()
                .texOffs(132, 265).addBox(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 2.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 1.0F, -5.5F, 0.5F, 0.0F, 0.0F));
        p_leg_fl_claw1.addOrReplaceChild("leg_fl_claw1_2", CubeListBuilder.create()
                .texOffs(138, 265).addBox(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 2.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, -1.5F, 0.6F, 0.0F, 0.0F));
        PartDefinition p_leg_fl_claw2 = p_leg_fl_hand.addOrReplaceChild("leg_fl_claw2", CubeListBuilder.create()
                .texOffs(144, 265).addBox(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 2.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(1.5F, 1.0F, -5.5F, 0.5F, 0.125F, 0.0F));
        p_leg_fl_claw2.addOrReplaceChild("leg_fl_claw2_2", CubeListBuilder.create()
                .texOffs(150, 265).addBox(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 2.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, -1.5F, 0.6F, 0.0F, 0.0F));
    }

    private static void addLegBr(PartDefinition p_body) {
        PartDefinition p_leg_br = p_body.addOrReplaceChild("leg_br", CubeListBuilder.create()
                .texOffs(282, 114).addBox(-3.5F, -5.0F, -3.5F, 7.0F, 11.0F, 7.0F, CubeDeformation.NONE)
                .texOffs(442, 164).addBox(-1.5F, -5.0F, -5.5F, 3.0F, 11.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(452, 164).addBox(-1.5F, -5.0F, 3.5F, 3.0F, 11.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(310, 177).addBox(-3.0F, 4.0F, -4.5F, 6.0F, 4.0F, 8.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-7.5F, 2.1723F, 20.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_leg_br_shin = p_leg_br.addOrReplaceChild("leg_br_shin", CubeListBuilder.create()
                .texOffs(214, 201).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 9.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(204, 212).addBox(-1.0F, 0.0F, -2.0F, 2.0F, 9.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(210, 212).addBox(-1.0F, 0.0F, 1.0F, 2.0F, 9.0F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 8.0F, -2.0F, 0.55F, 0.0F, 0.0F));
        PartDefinition p_leg_br_meta = p_leg_br_shin.addOrReplaceChild("leg_br_meta", CubeListBuilder.create()
                .texOffs(116, 212).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 7.0F, 3.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 8.5F, 0.0F, -1.0F, 0.0F, 0.0F));
        p_leg_br_meta.addOrReplaceChild("leg_br_spur", CubeListBuilder.create()
                .texOffs(114, 268).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(118, 268).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(122, 268).addBox(-0.5F, -3.0F, -0.5F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 1.0F, 1.5F, -1.6F, 0.0F, 0.0F));
        PartDefinition p_leg_br_foot = p_leg_br_meta.addOrReplaceChild("leg_br_foot", CubeListBuilder.create()
                .texOffs(188, 231).addBox(-2.5F, 0.0F, -3.5F, 5.0F, 2.0F, 5.0F, CubeDeformation.NONE)
                .texOffs(156, 265).addBox(-2.0F, 0.5F, -5.5F, 1.0F, 1.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(162, 265).addBox(-0.5F, 0.5F, -5.5F, 1.0F, 1.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(168, 265).addBox(1.0F, 0.5F, -5.5F, 1.0F, 1.0F, 2.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 6.5F, 0.0F, 0.45F, 0.0F, 0.0F));
        PartDefinition p_leg_br_claw0 = p_leg_br_foot.addOrReplaceChild("leg_br_claw0", CubeListBuilder.create()
                .texOffs(174, 265).addBox(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 2.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-1.5F, 1.0F, -5.5F, 0.5F, -0.1F, 0.0F));
        p_leg_br_claw0.addOrReplaceChild("leg_br_claw0_2", CubeListBuilder.create()
                .texOffs(180, 265).addBox(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 2.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, -1.5F, 0.6F, 0.0F, 0.0F));
        PartDefinition p_leg_br_claw1 = p_leg_br_foot.addOrReplaceChild("leg_br_claw1", CubeListBuilder.create()
                .texOffs(186, 265).addBox(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 2.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 1.0F, -5.5F, 0.5F, 0.0F, 0.0F));
        p_leg_br_claw1.addOrReplaceChild("leg_br_claw1_2", CubeListBuilder.create()
                .texOffs(192, 265).addBox(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 2.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, -1.5F, 0.6F, 0.0F, 0.0F));
        PartDefinition p_leg_br_claw2 = p_leg_br_foot.addOrReplaceChild("leg_br_claw2", CubeListBuilder.create()
                .texOffs(198, 265).addBox(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 2.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(1.5F, 1.0F, -5.5F, 0.5F, 0.1F, 0.0F));
        p_leg_br_claw2.addOrReplaceChild("leg_br_claw2_2", CubeListBuilder.create()
                .texOffs(204, 265).addBox(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 2.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, -1.5F, 0.6F, 0.0F, 0.0F));
    }

    private static void addLegBl(PartDefinition p_body) {
        PartDefinition p_leg_bl = p_body.addOrReplaceChild("leg_bl", CubeListBuilder.create()
                .texOffs(310, 114).addBox(-3.5F, -5.0F, -3.5F, 7.0F, 11.0F, 7.0F, CubeDeformation.NONE)
                .texOffs(462, 164).addBox(-1.5F, -5.0F, -5.5F, 3.0F, 11.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(472, 164).addBox(-1.5F, -5.0F, 3.5F, 3.0F, 11.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(338, 177).addBox(-3.0F, 4.0F, -4.5F, 6.0F, 4.0F, 8.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(7.5F, 2.1723F, 20.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_leg_bl_shin = p_leg_bl.addOrReplaceChild("leg_bl_shin", CubeListBuilder.create()
                .texOffs(226, 201).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 9.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(216, 212).addBox(-1.0F, 0.0F, -2.0F, 2.0F, 9.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(222, 212).addBox(-1.0F, 0.0F, 1.0F, 2.0F, 9.0F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 8.0F, -2.0F, 0.55F, 0.0F, 0.0F));
        PartDefinition p_leg_bl_meta = p_leg_bl_shin.addOrReplaceChild("leg_bl_meta", CubeListBuilder.create()
                .texOffs(128, 212).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 7.0F, 3.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 8.5F, 0.0F, -1.0F, 0.0F, 0.0F));
        p_leg_bl_meta.addOrReplaceChild("leg_bl_spur", CubeListBuilder.create()
                .texOffs(126, 268).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(130, 268).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(134, 268).addBox(-0.5F, -3.0F, -0.5F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 1.0F, 1.5F, -1.6F, 0.0F, 0.0F));
        PartDefinition p_leg_bl_foot = p_leg_bl_meta.addOrReplaceChild("leg_bl_foot", CubeListBuilder.create()
                .texOffs(208, 231).addBox(-2.5F, 0.0F, -3.5F, 5.0F, 2.0F, 5.0F, CubeDeformation.NONE)
                .texOffs(210, 265).addBox(-2.0F, 0.5F, -5.5F, 1.0F, 1.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(216, 265).addBox(-0.5F, 0.5F, -5.5F, 1.0F, 1.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(222, 265).addBox(1.0F, 0.5F, -5.5F, 1.0F, 1.0F, 2.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 6.5F, 0.0F, 0.45F, 0.0F, 0.0F));
        PartDefinition p_leg_bl_claw0 = p_leg_bl_foot.addOrReplaceChild("leg_bl_claw0", CubeListBuilder.create()
                .texOffs(228, 265).addBox(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 2.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-1.5F, 1.0F, -5.5F, 0.5F, -0.1F, 0.0F));
        p_leg_bl_claw0.addOrReplaceChild("leg_bl_claw0_2", CubeListBuilder.create()
                .texOffs(234, 265).addBox(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 2.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, -1.5F, 0.6F, 0.0F, 0.0F));
        PartDefinition p_leg_bl_claw1 = p_leg_bl_foot.addOrReplaceChild("leg_bl_claw1", CubeListBuilder.create()
                .texOffs(240, 265).addBox(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 2.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 1.0F, -5.5F, 0.5F, 0.0F, 0.0F));
        p_leg_bl_claw1.addOrReplaceChild("leg_bl_claw1_2", CubeListBuilder.create()
                .texOffs(246, 265).addBox(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 2.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, -1.5F, 0.6F, 0.0F, 0.0F));
        PartDefinition p_leg_bl_claw2 = p_leg_bl_foot.addOrReplaceChild("leg_bl_claw2", CubeListBuilder.create()
                .texOffs(252, 265).addBox(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 2.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(1.5F, 1.0F, -5.5F, 0.5F, 0.1F, 0.0F));
        p_leg_bl_claw2.addOrReplaceChild("leg_bl_claw2_2", CubeListBuilder.create()
                .texOffs(258, 265).addBox(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 2.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, -1.5F, 0.6F, 0.0F, 0.0F));
    }

    private static void addWingL(PartDefinition p_body) {
        PartDefinition p_wing_l = p_body.addOrReplaceChild("wing_l", CubeListBuilder.create()
                .texOffs(0, 189).addBox(-2.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F, CubeDeformation.NONE)
                .texOffs(192, 222).addBox(2.0F, -2.0F, -2.0F, 22.0F, 4.0F, 4.0F, CubeDeformation.NONE)
                .texOffs(232, 261).addBox(4.0F, -3.0F, -1.0F, 18.0F, 1.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(66, 0).addBox(0.0F, -0.6F, 1.0F, 2.0F, 1.0F, 25.0F, CubeDeformation.NONE)
                .texOffs(120, 0).addBox(2.0F, -0.6F, 1.0F, 2.0F, 1.0F, 25.0F, CubeDeformation.NONE)
                .texOffs(100, 28).addBox(4.0F, -0.6F, 1.0F, 2.0F, 1.0F, 22.0F, CubeDeformation.NONE)
                .texOffs(412, 52).addBox(6.0F, -0.6F, 1.0F, 2.0F, 1.0F, 19.0F, CubeDeformation.NONE)
                .texOffs(454, 52).addBox(8.0F, -0.6F, 1.0F, 2.0F, 1.0F, 19.0F, CubeDeformation.NONE)
                .texOffs(0, 74).addBox(10.0F, -0.6F, 1.0F, 2.0F, 1.0F, 19.0F, CubeDeformation.NONE)
                .texOffs(42, 74).addBox(12.0F, -0.6F, 1.0F, 2.0F, 1.0F, 19.0F, CubeDeformation.NONE)
                .texOffs(84, 74).addBox(14.0F, -0.6F, 1.0F, 2.0F, 1.0F, 19.0F, CubeDeformation.NONE)
                .texOffs(126, 74).addBox(16.0F, -0.6F, 1.0F, 2.0F, 1.0F, 19.0F, CubeDeformation.NONE)
                .texOffs(184, 52).addBox(18.0F, -0.6F, 1.0F, 2.0F, 1.0F, 20.0F, CubeDeformation.NONE)
                .texOffs(0, 52).addBox(20.0F, -0.6F, 1.0F, 2.0F, 1.0F, 21.0F, CubeDeformation.NONE)
                .texOffs(148, 28).addBox(22.0F, -0.6F, 1.0F, 2.0F, 1.0F, 22.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(6.0F, -9.0F, -10.0F, 0.0F, 0.0F, 0.0F));
        p_wing_l.addOrReplaceChild("wing_l_hs0", CubeListBuilder.create()
                .texOffs(138, 268).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(142, 268).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(146, 268).addBox(-0.5F, -3.0F, -0.5F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(8.0F, -3.0F, 0.0F, -0.7F, 0.0F, 0.0F));
        p_wing_l.addOrReplaceChild("wing_l_hs1", CubeListBuilder.create()
                .texOffs(150, 268).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(154, 268).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(158, 268).addBox(-0.5F, -3.0F, -0.5F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(15.0F, -3.0F, 0.0F, -0.7F, 0.0F, 0.0F));
        PartDefinition p_wing_l_fore = p_wing_l.addOrReplaceChild("wing_l_fore", CubeListBuilder.create()
                .texOffs(44, 212).addBox(-2.5F, -2.5F, -2.5F, 5.0F, 5.0F, 5.0F, CubeDeformation.NONE)
                .texOffs(30, 239).addBox(2.0F, -1.5F, -1.5F, 28.0F, 3.0F, 3.0F, CubeDeformation.NONE)
                .texOffs(128, 261).addBox(4.0F, -2.5F, -1.0F, 24.0F, 1.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(0, 28).addBox(0.0F, -0.4F, 1.0F, 2.0F, 1.0F, 23.0F, CubeDeformation.NONE)
                .texOffs(196, 28).addBox(2.0F, -0.4F, 1.0F, 2.0F, 1.0F, 22.0F, CubeDeformation.NONE)
                .texOffs(228, 52).addBox(4.0F, -0.4F, 1.0F, 2.0F, 1.0F, 20.0F, CubeDeformation.NONE)
                .texOffs(168, 74).addBox(6.0F, -0.4F, 1.0F, 2.0F, 1.0F, 19.0F, CubeDeformation.NONE)
                .texOffs(416, 94).addBox(8.0F, -0.4F, 1.0F, 2.0F, 1.0F, 18.0F, CubeDeformation.NONE)
                .texOffs(210, 74).addBox(10.0F, -0.4F, 1.0F, 2.0F, 1.0F, 19.0F, CubeDeformation.NONE)
                .texOffs(456, 94).addBox(12.0F, -0.4F, 1.0F, 2.0F, 1.0F, 18.0F, CubeDeformation.NONE)
                .texOffs(0, 114).addBox(14.0F, -0.4F, 1.0F, 2.0F, 1.0F, 18.0F, CubeDeformation.NONE)
                .texOffs(252, 74).addBox(16.0F, -0.4F, 1.0F, 2.0F, 1.0F, 19.0F, CubeDeformation.NONE)
                .texOffs(40, 114).addBox(18.0F, -0.4F, 1.0F, 2.0F, 1.0F, 18.0F, CubeDeformation.NONE)
                .texOffs(294, 74).addBox(20.0F, -0.4F, 1.0F, 2.0F, 1.0F, 19.0F, CubeDeformation.NONE)
                .texOffs(46, 52).addBox(22.0F, -0.4F, 1.0F, 2.0F, 1.0F, 21.0F, CubeDeformation.NONE)
                .texOffs(244, 28).addBox(24.0F, -0.4F, 1.0F, 2.0F, 1.0F, 22.0F, CubeDeformation.NONE)
                .texOffs(282, 0).addBox(26.0F, -0.4F, 1.0F, 2.0F, 1.0F, 24.0F, CubeDeformation.NONE)
                .texOffs(334, 0).addBox(28.0F, -0.4F, 1.0F, 2.0F, 1.0F, 24.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(24.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        p_wing_l_fore.addOrReplaceChild("wing_l_elbow", CubeListBuilder.create()
                .texOffs(24, 261).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(162, 268).addBox(-0.5F, -3.0F, -0.5F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(166, 268).addBox(-0.5F, -4.0F, -0.5F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, -2.5F, 0.0F, -0.9F, 0.0F, 0.0F));
        PartDefinition p_wing_l_wrist = p_wing_l_fore.addOrReplaceChild("wing_l_wrist", CubeListBuilder.create()
                .texOffs(104, 231).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(30.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_wing_l_thumb = p_wing_l_wrist.addOrReplaceChild("wing_l_thumb", CubeListBuilder.create()
                .texOffs(152, 252).addBox(-1.0F, -1.0F, -3.0F, 2.0F, 2.0F, 3.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, -1.0F, -2.0F, -0.35F, -0.5F, 0.0F));
        PartDefinition p_wing_l_thumb_2 = p_wing_l_thumb.addOrReplaceChild("wing_l_thumb_2", CubeListBuilder.create()
                .texOffs(162, 252).addBox(-1.0F, -1.0F, -3.0F, 2.0F, 2.0F, 3.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, -2.5F, -0.3F, 0.0F, 0.0F));
        p_wing_l_thumb_2.addOrReplaceChild("wing_l_thumb_3", CubeListBuilder.create()
                .texOffs(32, 261).addBox(-0.5F, -0.5F, -3.0F, 1.0F, 1.0F, 3.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, -2.5F, -0.5F, 0.0F, 0.0F));
        PartDefinition p_wing_l_f0 = p_wing_l_wrist.addOrReplaceChild("wing_l_f0", CubeListBuilder.create()
                .texOffs(12, 246).addBox(0.0F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, CubeDeformation.NONE)
                .texOffs(364, 252).addBox(3.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(456, 265).addBox(2.0F, -0.6F, 0.5F, 2.0F, 1.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(336, 261).addBox(4.0F, -0.6F, 0.5F, 2.0F, 1.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(392, 246).addBox(6.0F, -0.6F, 0.5F, 2.0F, 1.0F, 4.0F, CubeDeformation.NONE)
                .texOffs(210, 239).addBox(8.0F, -0.6F, 0.5F, 2.0F, 1.0F, 5.0F, CubeDeformation.NONE)
                .texOffs(404, 246).addBox(10.0F, -0.6F, 0.5F, 2.0F, 1.0F, 4.0F, CubeDeformation.NONE)
                .texOffs(428, 222).addBox(12.0F, -0.6F, 0.5F, 2.0F, 1.0F, 7.0F, CubeDeformation.NONE)
                .texOffs(0, 201).addBox(14.0F, -0.6F, 0.5F, 2.0F, 1.0F, 10.0F, CubeDeformation.NONE)
                .texOffs(24, 201).addBox(16.0F, -0.6F, 0.5F, 2.0F, 1.0F, 10.0F, CubeDeformation.NONE)
                .texOffs(162, 164).addBox(18.0F, -0.6F, 0.5F, 2.0F, 1.0F, 12.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.5F, 0.0F, 0.0F, 0.0F, -0.05F, 0.0F));
        p_wing_l_f0.addOrReplaceChild("wing_l_f0_tip", CubeListBuilder.create()
                .texOffs(436, 252).addBox(-0.5F, -1.0F, -1.0F, 15.0F, 2.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(232, 257).addBox(14.0F, -1.0F, -1.0F, 4.0F, 2.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(440, 265).addBox(18.0F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(462, 265).addBox(21.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(48, 201).addBox(1.0F, -0.6F, 0.5F, 2.0F, 1.0F, 10.0F, CubeDeformation.NONE)
                .texOffs(144, 133).addBox(3.0F, -0.6F, 0.5F, 2.0F, 1.0F, 14.0F, CubeDeformation.NONE)
                .texOffs(176, 133).addBox(5.0F, -0.6F, 0.5F, 2.0F, 1.0F, 14.0F, CubeDeformation.NONE)
                .texOffs(378, 114).addBox(7.0F, -0.6F, 0.5F, 2.0F, 1.0F, 16.0F, CubeDeformation.NONE)
                .texOffs(414, 114).addBox(9.0F, -0.6F, 0.5F, 2.0F, 1.0F, 16.0F, CubeDeformation.NONE)
                .texOffs(416, 246).addBox(11.0F, -0.6F, 0.5F, 2.0F, 1.0F, 4.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(19.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_wing_l_f1 = p_wing_l_wrist.addOrReplaceChild("wing_l_f1", CubeListBuilder.create()
                .texOffs(24, 246).addBox(0.0F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, CubeDeformation.NONE)
                .texOffs(208, 252).addBox(3.0F, -1.0F, -1.0F, 18.0F, 2.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(468, 265).addBox(2.0F, -0.4F, 0.5F, 2.0F, 1.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(344, 261).addBox(4.0F, -0.4F, 0.5F, 2.0F, 1.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(428, 246).addBox(6.0F, -0.4F, 0.5F, 2.0F, 1.0F, 4.0F, CubeDeformation.NONE)
                .texOffs(224, 239).addBox(8.0F, -0.4F, 0.5F, 2.0F, 1.0F, 5.0F, CubeDeformation.NONE)
                .texOffs(446, 222).addBox(10.0F, -0.4F, 0.5F, 2.0F, 1.0F, 7.0F, CubeDeformation.NONE)
                .texOffs(464, 222).addBox(12.0F, -0.4F, 0.5F, 2.0F, 1.0F, 7.0F, CubeDeformation.NONE)
                .texOffs(424, 212).addBox(14.0F, -0.4F, 0.5F, 2.0F, 1.0F, 8.0F, CubeDeformation.NONE)
                .texOffs(444, 212).addBox(16.0F, -0.4F, 0.5F, 2.0F, 1.0F, 8.0F, CubeDeformation.NONE)
                .texOffs(190, 164).addBox(18.0F, -0.4F, 0.5F, 2.0F, 1.0F, 12.0F, CubeDeformation.NONE)
                .texOffs(40, 150).addBox(20.0F, -0.4F, 0.5F, 2.0F, 1.0F, 13.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.5F, 0.0F, 0.0F, 0.0F, -0.6F, 0.0F));
        p_wing_l_f1.addOrReplaceChild("wing_l_f1_tip", CubeListBuilder.create()
                .texOffs(288, 252).addBox(-0.5F, -1.0F, -1.0F, 17.0F, 2.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(380, 265).addBox(16.0F, -0.5F, -0.5F, 4.0F, 1.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(218, 164).addBox(1.0F, -0.4F, 0.5F, 2.0F, 1.0F, 12.0F, CubeDeformation.NONE)
                .texOffs(246, 164).addBox(3.0F, -0.4F, 0.5F, 2.0F, 1.0F, 12.0F, CubeDeformation.NONE)
                .texOffs(274, 164).addBox(5.0F, -0.4F, 0.5F, 2.0F, 1.0F, 12.0F, CubeDeformation.NONE)
                .texOffs(464, 212).addBox(7.0F, -0.4F, 0.5F, 2.0F, 1.0F, 8.0F, CubeDeformation.NONE)
                .texOffs(238, 239).addBox(9.0F, -0.4F, 0.5F, 2.0F, 1.0F, 5.0F, CubeDeformation.NONE)
                .texOffs(256, 257).addBox(11.0F, -0.4F, 0.5F, 2.0F, 1.0F, 3.0F, CubeDeformation.NONE)
                .texOffs(352, 261).addBox(13.0F, -0.4F, 0.5F, 2.0F, 1.0F, 2.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(21.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_wing_l_f2 = p_wing_l_wrist.addOrReplaceChild("wing_l_f2", CubeListBuilder.create()
                .texOffs(36, 246).addBox(0.0F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, CubeDeformation.NONE)
                .texOffs(470, 252).addBox(3.0F, -1.0F, -1.0F, 15.0F, 2.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(474, 265).addBox(2.0F, -0.6F, 0.5F, 2.0F, 1.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(360, 261).addBox(4.0F, -0.6F, 0.5F, 2.0F, 1.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(440, 246).addBox(6.0F, -0.6F, 0.5F, 2.0F, 1.0F, 4.0F, CubeDeformation.NONE)
                .texOffs(252, 239).addBox(8.0F, -0.6F, 0.5F, 2.0F, 1.0F, 5.0F, CubeDeformation.NONE)
                .texOffs(266, 239).addBox(10.0F, -0.6F, 0.5F, 2.0F, 1.0F, 5.0F, CubeDeformation.NONE)
                .texOffs(484, 212).addBox(12.0F, -0.6F, 0.5F, 2.0F, 1.0F, 8.0F, CubeDeformation.NONE)
                .texOffs(264, 231).addBox(14.0F, -0.6F, 0.5F, 2.0F, 1.0F, 6.0F, CubeDeformation.NONE)
                .texOffs(72, 201).addBox(16.0F, -0.6F, 0.5F, 2.0F, 1.0F, 10.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.5F, 0.0F, 0.0F, 0.0F, -1.15F, 0.0F));
        p_wing_l_f2.addOrReplaceChild("wing_l_f2_tip", CubeListBuilder.create()
                .texOffs(68, 257).addBox(-0.5F, -1.0F, -1.0F, 14.0F, 2.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(390, 265).addBox(13.0F, -0.5F, -0.5F, 4.0F, 1.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(392, 177).addBox(0.0F, -0.6F, 0.5F, 2.0F, 1.0F, 11.0F, CubeDeformation.NONE)
                .texOffs(458, 201).addBox(2.0F, -0.6F, 0.5F, 2.0F, 1.0F, 9.0F, CubeDeformation.NONE)
                .texOffs(280, 231).addBox(4.0F, -0.6F, 0.5F, 2.0F, 1.0F, 6.0F, CubeDeformation.NONE)
                .texOffs(452, 246).addBox(6.0F, -0.6F, 0.5F, 2.0F, 1.0F, 4.0F, CubeDeformation.NONE)
                .texOffs(368, 261).addBox(8.0F, -0.6F, 0.5F, 2.0F, 1.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(480, 265).addBox(10.0F, -0.6F, 0.5F, 2.0F, 1.0F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(18.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_wing_l_f3 = p_wing_l_wrist.addOrReplaceChild("wing_l_f3", CubeListBuilder.create()
                .texOffs(48, 246).addBox(0.0F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, CubeDeformation.NONE)
                .texOffs(132, 257).addBox(3.0F, -1.0F, -1.0F, 11.0F, 2.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(376, 261).addBox(2.0F, -0.4F, 0.5F, 2.0F, 1.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(384, 261).addBox(4.0F, -0.4F, 0.5F, 2.0F, 1.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(464, 246).addBox(6.0F, -0.4F, 0.5F, 2.0F, 1.0F, 4.0F, CubeDeformation.NONE)
                .texOffs(296, 231).addBox(8.0F, -0.4F, 0.5F, 2.0F, 1.0F, 6.0F, CubeDeformation.NONE)
                .texOffs(482, 222).addBox(10.0F, -0.4F, 0.5F, 2.0F, 1.0F, 7.0F, CubeDeformation.NONE)
                .texOffs(312, 231).addBox(12.0F, -0.4F, 0.5F, 2.0F, 1.0F, 6.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.5F, 0.0F, 0.0F, 0.0F, -1.7F, 0.0F));
        p_wing_l_f3.addOrReplaceChild("wing_l_f3_tip", CubeListBuilder.create()
                .texOffs(184, 257).addBox(-0.5F, -1.0F, -1.0F, 10.0F, 2.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(400, 265).addBox(9.0F, -0.5F, -0.5F, 4.0F, 1.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(476, 246).addBox(0.0F, -0.4F, 0.5F, 2.0F, 1.0F, 4.0F, CubeDeformation.NONE)
                .texOffs(266, 257).addBox(2.0F, -0.4F, 0.5F, 2.0F, 1.0F, 3.0F, CubeDeformation.NONE)
                .texOffs(392, 261).addBox(4.0F, -0.4F, 0.5F, 2.0F, 1.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(486, 265).addBox(6.0F, -0.4F, 0.5F, 2.0F, 1.0F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(14.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
    }

    private static void addWingR(PartDefinition p_body) {
        PartDefinition p_wing_r = p_body.addOrReplaceChild("wing_r", CubeListBuilder.create()
                .texOffs(24, 189).addBox(-4.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F, CubeDeformation.NONE)
                .texOffs(244, 222).addBox(-24.0F, -2.0F, -2.0F, 22.0F, 4.0F, 4.0F, CubeDeformation.NONE)
                .texOffs(272, 261).addBox(-22.0F, -3.0F, -1.0F, 18.0F, 1.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(174, 0).addBox(-2.0F, -0.6F, 1.0F, 2.0F, 1.0F, 25.0F, CubeDeformation.NONE)
                .texOffs(228, 0).addBox(-4.0F, -0.6F, 1.0F, 2.0F, 1.0F, 25.0F, CubeDeformation.NONE)
                .texOffs(292, 28).addBox(-6.0F, -0.6F, 1.0F, 2.0F, 1.0F, 22.0F, CubeDeformation.NONE)
                .texOffs(336, 74).addBox(-8.0F, -0.6F, 1.0F, 2.0F, 1.0F, 19.0F, CubeDeformation.NONE)
                .texOffs(378, 74).addBox(-10.0F, -0.6F, 1.0F, 2.0F, 1.0F, 19.0F, CubeDeformation.NONE)
                .texOffs(420, 74).addBox(-12.0F, -0.6F, 1.0F, 2.0F, 1.0F, 19.0F, CubeDeformation.NONE)
                .texOffs(462, 74).addBox(-14.0F, -0.6F, 1.0F, 2.0F, 1.0F, 19.0F, CubeDeformation.NONE)
                .texOffs(0, 94).addBox(-16.0F, -0.6F, 1.0F, 2.0F, 1.0F, 19.0F, CubeDeformation.NONE)
                .texOffs(42, 94).addBox(-18.0F, -0.6F, 1.0F, 2.0F, 1.0F, 19.0F, CubeDeformation.NONE)
                .texOffs(272, 52).addBox(-20.0F, -0.6F, 1.0F, 2.0F, 1.0F, 20.0F, CubeDeformation.NONE)
                .texOffs(92, 52).addBox(-22.0F, -0.6F, 1.0F, 2.0F, 1.0F, 21.0F, CubeDeformation.NONE)
                .texOffs(340, 28).addBox(-24.0F, -0.6F, 1.0F, 2.0F, 1.0F, 22.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-6.0F, -9.0F, -10.0F, 0.0F, 0.0F, 0.0F));
        p_wing_r.addOrReplaceChild("wing_r_hs0", CubeListBuilder.create()
                .texOffs(170, 268).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(174, 268).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(178, 268).addBox(-0.5F, -3.0F, -0.5F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-8.0F, -3.0F, 0.0F, -0.7F, 0.0F, 0.0F));
        p_wing_r.addOrReplaceChild("wing_r_hs1", CubeListBuilder.create()
                .texOffs(182, 268).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(186, 268).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(190, 268).addBox(-0.5F, -3.0F, -0.5F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-15.0F, -3.0F, 0.0F, -0.7F, 0.0F, 0.0F));
        PartDefinition p_wing_r_fore = p_wing_r.addOrReplaceChild("wing_r_fore", CubeListBuilder.create()
                .texOffs(64, 212).addBox(-2.5F, -2.5F, -2.5F, 5.0F, 5.0F, 5.0F, CubeDeformation.NONE)
                .texOffs(92, 239).addBox(-30.0F, -1.5F, -1.5F, 28.0F, 3.0F, 3.0F, CubeDeformation.NONE)
                .texOffs(180, 261).addBox(-28.0F, -2.5F, -1.0F, 24.0F, 1.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(50, 28).addBox(-2.0F, -0.4F, 1.0F, 2.0F, 1.0F, 23.0F, CubeDeformation.NONE)
                .texOffs(388, 28).addBox(-4.0F, -0.4F, 1.0F, 2.0F, 1.0F, 22.0F, CubeDeformation.NONE)
                .texOffs(316, 52).addBox(-6.0F, -0.4F, 1.0F, 2.0F, 1.0F, 20.0F, CubeDeformation.NONE)
                .texOffs(84, 94).addBox(-8.0F, -0.4F, 1.0F, 2.0F, 1.0F, 19.0F, CubeDeformation.NONE)
                .texOffs(80, 114).addBox(-10.0F, -0.4F, 1.0F, 2.0F, 1.0F, 18.0F, CubeDeformation.NONE)
                .texOffs(126, 94).addBox(-12.0F, -0.4F, 1.0F, 2.0F, 1.0F, 19.0F, CubeDeformation.NONE)
                .texOffs(120, 114).addBox(-14.0F, -0.4F, 1.0F, 2.0F, 1.0F, 18.0F, CubeDeformation.NONE)
                .texOffs(160, 114).addBox(-16.0F, -0.4F, 1.0F, 2.0F, 1.0F, 18.0F, CubeDeformation.NONE)
                .texOffs(168, 94).addBox(-18.0F, -0.4F, 1.0F, 2.0F, 1.0F, 19.0F, CubeDeformation.NONE)
                .texOffs(200, 114).addBox(-20.0F, -0.4F, 1.0F, 2.0F, 1.0F, 18.0F, CubeDeformation.NONE)
                .texOffs(210, 94).addBox(-22.0F, -0.4F, 1.0F, 2.0F, 1.0F, 19.0F, CubeDeformation.NONE)
                .texOffs(138, 52).addBox(-24.0F, -0.4F, 1.0F, 2.0F, 1.0F, 21.0F, CubeDeformation.NONE)
                .texOffs(436, 28).addBox(-26.0F, -0.4F, 1.0F, 2.0F, 1.0F, 22.0F, CubeDeformation.NONE)
                .texOffs(386, 0).addBox(-28.0F, -0.4F, 1.0F, 2.0F, 1.0F, 24.0F, CubeDeformation.NONE)
                .texOffs(438, 0).addBox(-30.0F, -0.4F, 1.0F, 2.0F, 1.0F, 24.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-24.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        p_wing_r_fore.addOrReplaceChild("wing_r_elbow", CubeListBuilder.create()
                .texOffs(40, 261).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(194, 268).addBox(-0.5F, -3.0F, -0.5F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(198, 268).addBox(-0.5F, -4.0F, -0.5F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, -2.5F, 0.0F, -0.9F, 0.0F, 0.0F));
        PartDefinition p_wing_r_wrist = p_wing_r_fore.addOrReplaceChild("wing_r_wrist", CubeListBuilder.create()
                .texOffs(120, 231).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-30.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_wing_r_thumb = p_wing_r_wrist.addOrReplaceChild("wing_r_thumb", CubeListBuilder.create()
                .texOffs(172, 252).addBox(-1.0F, -1.0F, -3.0F, 2.0F, 2.0F, 3.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, -1.0F, -2.0F, -0.35F, 0.5F, 0.0F));
        PartDefinition p_wing_r_thumb_2 = p_wing_r_thumb.addOrReplaceChild("wing_r_thumb_2", CubeListBuilder.create()
                .texOffs(182, 252).addBox(-1.0F, -1.0F, -3.0F, 2.0F, 2.0F, 3.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, -2.5F, -0.3F, 0.0F, 0.0F));
        p_wing_r_thumb_2.addOrReplaceChild("wing_r_thumb_3", CubeListBuilder.create()
                .texOffs(48, 261).addBox(-0.5F, -0.5F, -3.0F, 1.0F, 1.0F, 3.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, -2.5F, -0.5F, 0.0F, 0.0F));
        PartDefinition p_wing_r_f0 = p_wing_r_wrist.addOrReplaceChild("wing_r_f0", CubeListBuilder.create()
                .texOffs(60, 246).addBox(-3.0F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, CubeDeformation.NONE)
                .texOffs(400, 252).addBox(-19.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(492, 265).addBox(-4.0F, -0.6F, 0.5F, 2.0F, 1.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(400, 261).addBox(-6.0F, -0.6F, 0.5F, 2.0F, 1.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(488, 246).addBox(-8.0F, -0.6F, 0.5F, 2.0F, 1.0F, 4.0F, CubeDeformation.NONE)
                .texOffs(280, 239).addBox(-10.0F, -0.6F, 0.5F, 2.0F, 1.0F, 5.0F, CubeDeformation.NONE)
                .texOffs(500, 246).addBox(-12.0F, -0.6F, 0.5F, 2.0F, 1.0F, 4.0F, CubeDeformation.NONE)
                .texOffs(0, 231).addBox(-14.0F, -0.6F, 0.5F, 2.0F, 1.0F, 7.0F, CubeDeformation.NONE)
                .texOffs(96, 201).addBox(-16.0F, -0.6F, 0.5F, 2.0F, 1.0F, 10.0F, CubeDeformation.NONE)
                .texOffs(120, 201).addBox(-18.0F, -0.6F, 0.5F, 2.0F, 1.0F, 10.0F, CubeDeformation.NONE)
                .texOffs(302, 164).addBox(-20.0F, -0.6F, 0.5F, 2.0F, 1.0F, 12.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-0.5F, 0.0F, 0.0F, 0.0F, 0.05F, 0.0F));
        p_wing_r_f0.addOrReplaceChild("wing_r_f0_tip", CubeListBuilder.create()
                .texOffs(0, 257).addBox(-14.5F, -1.0F, -1.0F, 15.0F, 2.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(244, 257).addBox(-18.0F, -1.0F, -1.0F, 4.0F, 2.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(448, 265).addBox(-21.0F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(498, 265).addBox(-23.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(144, 201).addBox(-3.0F, -0.6F, 0.5F, 2.0F, 1.0F, 10.0F, CubeDeformation.NONE)
                .texOffs(208, 133).addBox(-5.0F, -0.6F, 0.5F, 2.0F, 1.0F, 14.0F, CubeDeformation.NONE)
                .texOffs(240, 133).addBox(-7.0F, -0.6F, 0.5F, 2.0F, 1.0F, 14.0F, CubeDeformation.NONE)
                .texOffs(450, 114).addBox(-9.0F, -0.6F, 0.5F, 2.0F, 1.0F, 16.0F, CubeDeformation.NONE)
                .texOffs(0, 133).addBox(-11.0F, -0.6F, 0.5F, 2.0F, 1.0F, 16.0F, CubeDeformation.NONE)
                .texOffs(0, 252).addBox(-13.0F, -0.6F, 0.5F, 2.0F, 1.0F, 4.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-19.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_wing_r_f1 = p_wing_r_wrist.addOrReplaceChild("wing_r_f1", CubeListBuilder.create()
                .texOffs(72, 246).addBox(-3.0F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, CubeDeformation.NONE)
                .texOffs(248, 252).addBox(-21.0F, -1.0F, -1.0F, 18.0F, 2.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(504, 265).addBox(-4.0F, -0.4F, 0.5F, 2.0F, 1.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(408, 261).addBox(-6.0F, -0.4F, 0.5F, 2.0F, 1.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(12, 252).addBox(-8.0F, -0.4F, 0.5F, 2.0F, 1.0F, 4.0F, CubeDeformation.NONE)
                .texOffs(294, 239).addBox(-10.0F, -0.4F, 0.5F, 2.0F, 1.0F, 5.0F, CubeDeformation.NONE)
                .texOffs(18, 231).addBox(-12.0F, -0.4F, 0.5F, 2.0F, 1.0F, 7.0F, CubeDeformation.NONE)
                .texOffs(36, 231).addBox(-14.0F, -0.4F, 0.5F, 2.0F, 1.0F, 7.0F, CubeDeformation.NONE)
                .texOffs(0, 222).addBox(-16.0F, -0.4F, 0.5F, 2.0F, 1.0F, 8.0F, CubeDeformation.NONE)
                .texOffs(20, 222).addBox(-18.0F, -0.4F, 0.5F, 2.0F, 1.0F, 8.0F, CubeDeformation.NONE)
                .texOffs(330, 164).addBox(-20.0F, -0.4F, 0.5F, 2.0F, 1.0F, 12.0F, CubeDeformation.NONE)
                .texOffs(70, 150).addBox(-22.0F, -0.4F, 0.5F, 2.0F, 1.0F, 13.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-0.5F, 0.0F, 0.0F, 0.0F, 0.6F, 0.0F));
        p_wing_r_f1.addOrReplaceChild("wing_r_f1_tip", CubeListBuilder.create()
                .texOffs(326, 252).addBox(-16.5F, -1.0F, -1.0F, 17.0F, 2.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(410, 265).addBox(-20.0F, -0.5F, -0.5F, 4.0F, 1.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(358, 164).addBox(-3.0F, -0.4F, 0.5F, 2.0F, 1.0F, 12.0F, CubeDeformation.NONE)
                .texOffs(386, 164).addBox(-5.0F, -0.4F, 0.5F, 2.0F, 1.0F, 12.0F, CubeDeformation.NONE)
                .texOffs(414, 164).addBox(-7.0F, -0.4F, 0.5F, 2.0F, 1.0F, 12.0F, CubeDeformation.NONE)
                .texOffs(40, 222).addBox(-9.0F, -0.4F, 0.5F, 2.0F, 1.0F, 8.0F, CubeDeformation.NONE)
                .texOffs(308, 239).addBox(-11.0F, -0.4F, 0.5F, 2.0F, 1.0F, 5.0F, CubeDeformation.NONE)
                .texOffs(276, 257).addBox(-13.0F, -0.4F, 0.5F, 2.0F, 1.0F, 3.0F, CubeDeformation.NONE)
                .texOffs(416, 261).addBox(-15.0F, -0.4F, 0.5F, 2.0F, 1.0F, 2.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-21.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_wing_r_f2 = p_wing_r_wrist.addOrReplaceChild("wing_r_f2", CubeListBuilder.create()
                .texOffs(84, 246).addBox(-3.0F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, CubeDeformation.NONE)
                .texOffs(34, 257).addBox(-18.0F, -1.0F, -1.0F, 15.0F, 2.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(0, 268).addBox(-4.0F, -0.6F, 0.5F, 2.0F, 1.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(424, 261).addBox(-6.0F, -0.6F, 0.5F, 2.0F, 1.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(24, 252).addBox(-8.0F, -0.6F, 0.5F, 2.0F, 1.0F, 4.0F, CubeDeformation.NONE)
                .texOffs(322, 239).addBox(-10.0F, -0.6F, 0.5F, 2.0F, 1.0F, 5.0F, CubeDeformation.NONE)
                .texOffs(336, 239).addBox(-12.0F, -0.6F, 0.5F, 2.0F, 1.0F, 5.0F, CubeDeformation.NONE)
                .texOffs(60, 222).addBox(-14.0F, -0.6F, 0.5F, 2.0F, 1.0F, 8.0F, CubeDeformation.NONE)
                .texOffs(328, 231).addBox(-16.0F, -0.6F, 0.5F, 2.0F, 1.0F, 6.0F, CubeDeformation.NONE)
                .texOffs(168, 201).addBox(-18.0F, -0.6F, 0.5F, 2.0F, 1.0F, 10.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-0.5F, 0.0F, 0.0F, 0.0F, 1.15F, 0.0F));
        p_wing_r_f2.addOrReplaceChild("wing_r_f2_tip", CubeListBuilder.create()
                .texOffs(100, 257).addBox(-13.5F, -1.0F, -1.0F, 14.0F, 2.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(420, 265).addBox(-17.0F, -0.5F, -0.5F, 4.0F, 1.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(418, 177).addBox(-2.0F, -0.6F, 0.5F, 2.0F, 1.0F, 11.0F, CubeDeformation.NONE)
                .texOffs(480, 201).addBox(-4.0F, -0.6F, 0.5F, 2.0F, 1.0F, 9.0F, CubeDeformation.NONE)
                .texOffs(344, 231).addBox(-6.0F, -0.6F, 0.5F, 2.0F, 1.0F, 6.0F, CubeDeformation.NONE)
                .texOffs(36, 252).addBox(-8.0F, -0.6F, 0.5F, 2.0F, 1.0F, 4.0F, CubeDeformation.NONE)
                .texOffs(432, 261).addBox(-10.0F, -0.6F, 0.5F, 2.0F, 1.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(6, 268).addBox(-12.0F, -0.6F, 0.5F, 2.0F, 1.0F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-18.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition p_wing_r_f3 = p_wing_r_wrist.addOrReplaceChild("wing_r_f3", CubeListBuilder.create()
                .texOffs(96, 246).addBox(-3.0F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, CubeDeformation.NONE)
                .texOffs(158, 257).addBox(-14.0F, -1.0F, -1.0F, 11.0F, 2.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(440, 261).addBox(-4.0F, -0.4F, 0.5F, 2.0F, 1.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(448, 261).addBox(-6.0F, -0.4F, 0.5F, 2.0F, 1.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(48, 252).addBox(-8.0F, -0.4F, 0.5F, 2.0F, 1.0F, 4.0F, CubeDeformation.NONE)
                .texOffs(360, 231).addBox(-10.0F, -0.4F, 0.5F, 2.0F, 1.0F, 6.0F, CubeDeformation.NONE)
                .texOffs(54, 231).addBox(-12.0F, -0.4F, 0.5F, 2.0F, 1.0F, 7.0F, CubeDeformation.NONE)
                .texOffs(376, 231).addBox(-14.0F, -0.4F, 0.5F, 2.0F, 1.0F, 6.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-0.5F, 0.0F, 0.0F, 0.0F, 1.7F, 0.0F));
        p_wing_r_f3.addOrReplaceChild("wing_r_f3_tip", CubeListBuilder.create()
                .texOffs(208, 257).addBox(-9.5F, -1.0F, -1.0F, 10.0F, 2.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(430, 265).addBox(-13.0F, -0.5F, -0.5F, 4.0F, 1.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(60, 252).addBox(-2.0F, -0.4F, 0.5F, 2.0F, 1.0F, 4.0F, CubeDeformation.NONE)
                .texOffs(286, 257).addBox(-4.0F, -0.4F, 0.5F, 2.0F, 1.0F, 3.0F, CubeDeformation.NONE)
                .texOffs(456, 261).addBox(-6.0F, -0.4F, 0.5F, 2.0F, 1.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(12, 268).addBox(-8.0F, -0.4F, 0.5F, 2.0F, 1.0F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-14.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
    }

    private static void addTail1(PartDefinition p_body) {
        PartDefinition p_tail1 = p_body.addOrReplaceChild("tail1", CubeListBuilder.create()
                .texOffs(240, 114).addBox(-4.5F, -3.0F, -1.0F, 9.0F, 6.0F, 12.0F, CubeDeformation.NONE)
                .texOffs(240, 150).addBox(-3.5F, -4.0F, -1.0F, 7.0F, 1.0F, 12.0F, CubeDeformation.NONE)
                .texOffs(278, 150).addBox(-3.5F, 3.0F, -1.0F, 7.0F, 1.0F, 12.0F, CubeDeformation.NONE)
                .texOffs(100, 189).addBox(-3.5F, 3.5F, 0.0F, 7.0F, 1.0F, 10.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, -2.0F, 24.0F, -0.2F, 0.0F, 0.0F));
        p_tail1.addOrReplaceChild("tail1_spike", CubeListBuilder.create()
                .texOffs(56, 261).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(372, 265).addBox(-0.5F, -4.0F, -0.5F, 1.0F, 2.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(202, 268).addBox(-0.5F, -5.0F, -0.5F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, -4.0F, 5.0F, -0.35F, 0.0F, 0.0F));
        PartDefinition p_tail2 = p_tail1.addOrReplaceChild("tail2", CubeListBuilder.create()
                .texOffs(338, 114).addBox(-4.0F, -2.5F, -1.0F, 8.0F, 5.0F, 12.0F, CubeDeformation.NONE)
                .texOffs(424, 150).addBox(-3.0F, -3.5F, -1.0F, 6.0F, 1.0F, 12.0F, CubeDeformation.NONE)
                .texOffs(460, 150).addBox(-3.0F, 2.5F, -1.0F, 6.0F, 1.0F, 12.0F, CubeDeformation.NONE)
                .texOffs(134, 189).addBox(-3.0F, 3.0F, 0.0F, 6.0F, 1.0F, 10.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, 10.0F, -0.08F, 0.05F, 0.0F));
        p_tail2.addOrReplaceChild("tail2_spike", CubeListBuilder.create()
                .texOffs(64, 261).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(376, 265).addBox(-0.5F, -4.0F, -0.5F, 1.0F, 2.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(206, 268).addBox(-0.5F, -5.0F, -0.5F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, -3.5F, 5.0F, -0.35F, 0.0F, 0.0F));
        PartDefinition p_tail3 = p_tail2.addOrReplaceChild("tail3", CubeListBuilder.create()
                .texOffs(74, 133).addBox(-3.5F, -2.0F, -1.0F, 7.0F, 4.0F, 11.0F, CubeDeformation.NONE)
                .texOffs(96, 177).addBox(-2.5F, -3.0F, -1.0F, 5.0F, 1.0F, 11.0F, CubeDeformation.NONE)
                .texOffs(128, 177).addBox(-2.5F, 2.0F, -1.0F, 5.0F, 1.0F, 11.0F, CubeDeformation.NONE)
                .texOffs(334, 201).addBox(-2.5F, 2.5F, 0.0F, 5.0F, 1.0F, 9.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, 10.0F, -0.02F, 0.07F, 0.0F));
        p_tail3.addOrReplaceChild("tail3_spike", CubeListBuilder.create()
                .texOffs(72, 261).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(210, 268).addBox(-0.5F, -3.0F, -0.5F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(214, 268).addBox(-0.5F, -4.0F, -0.5F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, -3.0F, 4.5F, -0.35F, 0.0F, 0.0F));
        PartDefinition p_tail4 = p_tail3.addOrReplaceChild("tail4", CubeListBuilder.create()
                .texOffs(110, 133).addBox(-3.0F, -2.0F, -1.0F, 6.0F, 4.0F, 11.0F, CubeDeformation.NONE)
                .texOffs(220, 177).addBox(-2.0F, -3.0F, -1.0F, 4.0F, 1.0F, 11.0F, CubeDeformation.NONE)
                .texOffs(250, 177).addBox(-2.0F, 2.0F, -1.0F, 4.0F, 1.0F, 11.0F, CubeDeformation.NONE)
                .texOffs(388, 201).addBox(-2.0F, 2.5F, 0.0F, 4.0F, 1.0F, 9.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, 9.0F, 0.03F, 0.07F, 0.0F));
        p_tail4.addOrReplaceChild("tail4_spike", CubeListBuilder.create()
                .texOffs(80, 261).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(218, 268).addBox(-0.5F, -3.0F, -0.5F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(222, 268).addBox(-0.5F, -4.0F, -0.5F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, -3.0F, 4.5F, -0.35F, 0.0F, 0.0F));
        PartDefinition p_tail5 = p_tail4.addOrReplaceChild("tail5", CubeListBuilder.create()
                .texOffs(102, 164).addBox(-3.0F, -1.5F, -1.0F, 6.0F, 3.0F, 10.0F, CubeDeformation.NONE)
                .texOffs(224, 189).addBox(-2.0F, -2.5F, -1.0F, 4.0F, 1.0F, 10.0F, CubeDeformation.NONE)
                .texOffs(252, 189).addBox(-2.0F, 1.5F, -1.0F, 4.0F, 1.0F, 10.0F, CubeDeformation.NONE)
                .texOffs(338, 212).addBox(-2.0F, 2.0F, 0.0F, 4.0F, 1.0F, 8.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, 9.0F, 0.06F, 0.05F, 0.0F));
        p_tail5.addOrReplaceChild("tail5_spike", CubeListBuilder.create()
                .texOffs(226, 268).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(230, 268).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(234, 268).addBox(-0.5F, -3.0F, -0.5F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, -2.5F, 4.0F, -0.35F, 0.0F, 0.0F));
        PartDefinition p_tail6 = p_tail5.addOrReplaceChild("tail6", CubeListBuilder.create()
                .texOffs(280, 177).addBox(-2.5F, -1.0F, -1.0F, 5.0F, 2.0F, 10.0F, CubeDeformation.NONE)
                .texOffs(384, 189).addBox(-1.5F, -2.0F, -1.0F, 3.0F, 1.0F, 10.0F, CubeDeformation.NONE)
                .texOffs(410, 189).addBox(-1.5F, 1.0F, -1.0F, 3.0F, 1.0F, 10.0F, CubeDeformation.NONE)
                .texOffs(362, 212).addBox(-1.5F, 1.5F, 0.0F, 3.0F, 1.0F, 8.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, 8.0F, 0.08F, 0.03F, 0.0F));
        p_tail6.addOrReplaceChild("tail6_spike", CubeListBuilder.create()
                .texOffs(238, 268).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(242, 268).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(246, 268).addBox(-0.5F, -3.0F, -0.5F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, -2.0F, 4.0F, -0.35F, 0.0F, 0.0F));
        PartDefinition p_tail7 = p_tail6.addOrReplaceChild("tail7", CubeListBuilder.create()
                .texOffs(436, 189).addBox(-2.0F, -1.0F, -1.0F, 4.0F, 2.0F, 9.0F, CubeDeformation.NONE)
                .texOffs(0, 212).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 1.0F, 9.0F, CubeDeformation.NONE)
                .texOffs(22, 212).addBox(-1.0F, 1.0F, -1.0F, 2.0F, 1.0F, 9.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, 8.0F, 0.1F, 0.0F, 0.0F));
        p_tail7.addOrReplaceChild("tail7_spike", CubeListBuilder.create()
                .texOffs(250, 268).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(254, 268).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(258, 268).addBox(-0.5F, -3.0F, -0.5F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, -2.0F, 3.5F, -0.35F, 0.0F, 0.0F));
        PartDefinition p_tail8 = p_tail7.addOrReplaceChild("tail8", CubeListBuilder.create()
                .texOffs(48, 189).addBox(-1.5F, -1.5F, -1.0F, 3.0F, 3.0F, 9.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, 7.0F, 0.12F, -0.03F, 0.0F));
        p_tail8.addOrReplaceChild("tail8_spike", CubeListBuilder.create()
                .texOffs(262, 268).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(266, 268).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(270, 268).addBox(-0.5F, -3.0F, -0.5F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, -1.5F, 3.5F, -0.35F, 0.0F, 0.0F));
        PartDefinition p_tail9 = p_tail8.addOrReplaceChild("tail9", CubeListBuilder.create()
                .texOffs(192, 201).addBox(-1.5F, -1.5F, -1.0F, 3.0F, 3.0F, 8.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, 7.0F, 0.12F, -0.05F, 0.0F));
        p_tail9.addOrReplaceChild("tail_tip", CubeListBuilder.create()
                .texOffs(0, 239).addBox(-1.5F, -1.5F, -0.5F, 3.0F, 3.0F, 4.0F, CubeDeformation.NONE)
                .texOffs(136, 231).addBox(-1.0F, -1.0F, 3.5F, 2.0F, 2.0F, 6.0F, CubeDeformation.NONE)
                .texOffs(152, 231).addBox(-0.5F, -0.5F, 9.5F, 1.0F, 1.0F, 7.0F, CubeDeformation.NONE)
                .texOffs(228, 231).addBox(-4.5F, -0.5F, 0.5F, 3.0F, 1.0F, 6.0F, CubeDeformation.NONE)
                .texOffs(296, 257).addBox(-6.5F, -0.5F, 1.5F, 2.0F, 1.0F, 3.0F, CubeDeformation.NONE)
                .texOffs(246, 231).addBox(1.5F, -0.5F, 0.5F, 3.0F, 1.0F, 6.0F, CubeDeformation.NONE)
                .texOffs(306, 257).addBox(4.5F, -0.5F, 1.5F, 2.0F, 1.0F, 3.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, 6.0F, 0.0F, 0.0F, 0.0F));
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    private static float flightBlend(StormDragon entity, float ageInTicks) {
        float target = entity.isFlying() ? 1.0F : 0.0F;
        float[] s = FLIGHT.computeIfAbsent(entity, e -> new float[]{target, ageInTicks});
        float dt = Mth.clamp(ageInTicks - s[1], 0.0F, 4.0F);
        s[1] = ageInTicks;
        s[0] += Mth.clamp(target - s[0], -dt * 0.07F, dt * 0.07F);
        return s[0];
    }

    private static void sym(ModelPart l, ModelPart r, float x, float y, float z) {
        l.xRot += x;
        l.yRot += y;
        l.zRot += z;
        r.xRot += x;
        r.yRot -= y;
        r.zRot -= z;
    }

    @Override
    public void setupAnim(StormDragon entity, float limbSwing, float limbSwingAmount, float ageInTicks,
                          float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        float t = ageInTicks;
        float partial = ageInTicks - entity.tickCount;
        float fly = flightBlend(entity, ageInTicks);
        fly = fly * fly * (3.0F - 2.0F * fly);
        float ground = 1.0F - fly;
        float yaw = netHeadYaw * Mth.DEG_TO_RAD;
        float pitch = headPitch * Mth.DEG_TO_RAD;
        float hurt = entity.hurtTime > 0 ? (entity.hurtTime - partial) / 10.0F : 0.0F;
        float death = entity.deathTime > 0 ? Math.min(1.0F, (entity.deathTime + partial) / 20.0F) : 0.0F;
        float roar = entity.getRoarAmount(partial);
        float bite = Mth.sin(this.attackTime * Mth.PI);
        float breathe = Mth.sin(t * 0.07F);
        float amt = Math.min(limbSwingAmount, 1.0F) * ground;
        float swing = limbSwing * 0.35F;
        float f = t * 0.3F;
        float flap = Mth.sin(f);

        // ---- asas: pose pousada (erguidas, caindo como manto) <-> asas abertas
        for (int i = 0; i < this.wingBonesL.length; i++) {
            sym(this.wingBonesL[i], this.wingBonesR[i], Mth.lerp(fly, WING_GROUND[i][0], WING_FLY[i][0]),
                    Mth.lerp(fly, WING_GROUND[i][1], WING_FLY[i][1]), Mth.lerp(fly, WING_GROUND[i][2], WING_FLY[i][2]));
        }
        // batida: o antebraço, o pulso e as pontas seguem com atraso
        sym(this.wingBonesL[0], this.wingBonesR[0], 0.0F, Mth.cos(f) * 0.12F * fly, flap * 0.7F * fly);
        sym(this.wingBonesL[1], this.wingBonesR[1], 0.0F, 0.0F, Mth.sin(f - 0.6F) * 0.35F * fly);
        sym(this.wingBonesL[2], this.wingBonesR[2], 0.0F, 0.0F, Mth.sin(f - 1.1F) * 0.25F * fly);
        for (int k = 0; k < 4; k++) {
            sym(this.wingBonesL[3 + k], this.wingBonesR[3 + k], 0.0F, Mth.sin(f - 1.2F) * 0.03F * k * fly, 0.0F);
            sym(this.wingBonesL[7 + k], this.wingBonesR[7 + k], 0.0F, 0.0F,
                    Mth.sin(f - 1.5F - k * 0.1F) * 0.15F * fly + Mth.sin(t * 0.05F - k * 0.4F) * 0.04F * ground);
        }
        // pousado: respiração e balanço ao andar
        sym(this.wingBonesL[0], this.wingBonesR[0], 0.0F, 0.0F, breathe * 0.025F * ground + Mth.sin(swing * 2.0F) * 0.04F * amt);
        sym(this.wingBonesL[1], this.wingBonesR[1], 0.0F, breathe * 0.03F * ground, 0.0F);
        // rugido no chão: as asas se abrem
        sym(this.wingBonesL[0], this.wingBonesR[0], 0.0F, roar * 0.3F * ground, -roar * 0.35F * ground);
        sym(this.wingBonesL[1], this.wingBonesR[1], 0.0F, roar * 0.9F * ground, 0.0F);
        for (int k = 0; k < 4; k++) {
            sym(this.wingBonesL[3 + k], this.wingBonesR[3 + k], 0.0F, -roar * 0.15F * k * ground, 0.0F);
        }
        this.body.y += -flap * 1.4F * fly + breathe * 0.5F * ground;

        // ---- pescoço em S, olhar e mordida
        for (int i = 0; i < this.neck.length; i++) {
            this.neck[i].yRot += yaw * 0.16F + Mth.sin(t * 0.045F - i * 0.5F) * 0.03F * ground;
            this.neck[i].xRot += Mth.sin(t * 0.07F - i * 0.6F) * 0.02F;
        }
        this.head.yRot += yaw * 0.2F;
        this.neck[0].yRot += Mth.sin(swing) * 0.05F * amt;
        this.neck[0].xRot += pitch * 0.15F - roar * 0.45F + hurt * 0.25F + bite * 0.35F + fly * 0.2F;
        this.neck[1].xRot += -roar * 0.1F;
        this.neck[2].xRot += bite * 0.1F;
        this.neck[3].xRot += roar * 0.15F;
        this.head.xRot += pitch * 0.35F - roar * 0.15F - hurt * 0.3F + bite * 0.1F - fly * 0.15F;
        this.jaw.xRot += 0.05F + (breathe * 0.5F + 0.5F) * 0.04F + bite * 0.85F + roar * 0.95F + hurt * 0.4F;

        // ---- cauda em onda
        for (int i = 0; i < this.tail.length; i++) {
            this.tail[i].yRot += Mth.sin(t * 0.08F - i * 0.45F) * (0.035F + 0.012F * i)
                    + Mth.sin(swing - i * 0.5F) * 0.04F * amt;
            this.tail[i].xRot += Mth.sin(t * 0.06F - i * 0.4F) * 0.015F + Mth.sin(f - 1.0F - i * 0.4F) * 0.03F * fly;
        }
        this.tail[0].xRot += 0.12F * fly;

        // ---- pernas: passada no chão, encolhidas no voo
        float a = Mth.cos(swing) * 0.5F * amt;
        float b = Mth.cos(swing + Mth.PI) * 0.5F * amt;
        this.legFl.xRot += a + 0.9F * fly;
        this.legFr.xRot += b + 0.9F * fly;
        this.legFlFore.xRot += Math.max(0.0F, -a) * 0.7F + 0.6F * fly;
        this.legFrFore.xRot += Math.max(0.0F, -b) * 0.7F + 0.6F * fly;
        this.legFlHand.xRot += -a * 0.6F + 0.5F * fly;
        this.legFrHand.xRot += -b * 0.6F + 0.5F * fly;
        this.legBr.xRot += a + 1.0F * fly;
        this.legBl.xRot += b + 1.0F * fly;
        this.legBrShin.xRot += Math.max(0.0F, a) * 0.5F + 0.4F * fly;
        this.legBlShin.xRot += Math.max(0.0F, b) * 0.5F + 0.4F * fly;
        this.legBrMeta.xRot += -Math.max(0.0F, a) * 0.3F + 0.6F * fly;
        this.legBlMeta.xRot += -Math.max(0.0F, b) * 0.3F + 0.6F * fly;
        this.legBrFoot.xRot += -a * 0.6F + 0.5F * fly;
        this.legBlFoot.xRot += -b * 0.6F + 0.5F * fly;
        this.body.zRot += Mth.sin(swing) * 0.03F * amt;

        // ---- dano: recua a cabeça e as asas tremem
        sym(this.wingBonesL[0], this.wingBonesR[0], 0.0F, 0.0F, -hurt * 0.15F);

        // ---- morte: desaba, pescoço cai, asas caem abertas
        if (death > 0.0F) {
            this.body.y += death * 8.0F * ground;
            this.legFl.zRot -= death * 0.5F;
            this.legFr.zRot += death * 0.5F;
            this.legBl.zRot -= death * 0.5F;
            this.legBr.zRot += death * 0.5F;
            this.neck[0].xRot += death * 0.9F;
            this.neck[1].xRot += death * 0.2F;
            this.head.xRot += death * 0.3F;
            this.jaw.xRot += death * 0.45F;
            sym(this.wingBonesL[0], this.wingBonesR[0], 0.0F, 0.0F, death * 0.9F);
            sym(this.wingBonesL[1], this.wingBonesR[1], 0.0F, death * 0.6F, 0.0F);
            this.tail[0].xRot -= death * 0.1F;
        }
    }
}
