package com.frostkl4.valdren.client.render;

import com.frostkl4.valdren.Valdren;
import com.frostkl4.valdren.client.model.FrostWraithModel;
import com.frostkl4.valdren.client.model.ShadowWolfModel;
import com.frostkl4.valdren.client.model.StormDragonModel;
import com.frostkl4.valdren.entity.boss.StormDragon;
import com.frostkl4.valdren.entity.mob.FrostWraith;
import com.frostkl4.valdren.entity.mob.ShadowWolf;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

/** Renderizadores das criaturas com modelo próprio. */
public final class CreatureRenderers {
    private CreatureRenderers() {
    }

    public static class Wolf extends MobRenderer<ShadowWolf, ShadowWolfModel> {
        private static final ResourceLocation TEX = Valdren.id("textures/entity/shadow_wolf.png");

        public Wolf(EntityRendererProvider.Context ctx) {
            super(ctx, new ShadowWolfModel(ctx.bakeLayer(ShadowWolfModel.LAYER)), 0.6F);
            this.addLayer(new GlowLayer<>(this, Valdren.id("textures/entity/shadow_wolf_glow.png")));
        }

        @Override
        public ResourceLocation getTextureLocation(ShadowWolf entity) {
            return TEX;
        }
    }

    public static class Wraith extends MobRenderer<FrostWraith, FrostWraithModel> {
        private static final ResourceLocation TEX = Valdren.id("textures/entity/frost_wraith.png");

        public Wraith(EntityRendererProvider.Context ctx) {
            super(ctx, new FrostWraithModel(ctx.bakeLayer(FrostWraithModel.LAYER)), 0.5F);
            this.addLayer(new GlowLayer<>(this, Valdren.id("textures/entity/frost_wraith_glow.png")));
        }

        @Override
        public ResourceLocation getTextureLocation(FrostWraith entity) {
            return TEX;
        }
    }

    public static class Dragon extends MobRenderer<StormDragon, StormDragonModel> {
        private static final ResourceLocation TEX = Valdren.id("textures/entity/storm_dragon.png");

        public Dragon(EntityRendererProvider.Context ctx) {
            super(ctx, new StormDragonModel(ctx.bakeLayer(StormDragonModel.LAYER)), 3.0F);
            this.addLayer(new GlowLayer<>(this, Valdren.id("textures/entity/storm_dragon_glow.png")));
        }

        /** Sem o tombo de lado padrão ao morrer: a queda do dragão é animada no próprio modelo. */
        @Override
        protected void setupRotations(StormDragon entity, PoseStack poseStack, float ageInTicks, float rotationYaw,
                                      float partialTick) {
            poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - rotationYaw));
        }

        @Override
        public ResourceLocation getTextureLocation(StormDragon entity) {
            return TEX;
        }
    }
}
