package com.frostkl4.valdren.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;

import javax.annotation.Nullable;

/**
 * Renderizador dos inimigos humanoides (Saqueador Carmesim, Cavaleiro Sem Luz).
 * Com Epic Fight instalado, ele é trocado pelo renderizador animado do Epic Fight
 * (preset de zumbi, ver data/valdren/epicfight_mobpatch).
 */
public class ValdrenHumanoidRenderer<T extends Mob> extends HumanoidMobRenderer<T, PlayerModel<T>> {
    private final ResourceLocation texture;
    private final float scale;

    public ValdrenHumanoidRenderer(EntityRendererProvider.Context ctx, ResourceLocation texture,
                                   @Nullable ResourceLocation glow, float scale) {
        super(ctx, new PlayerModel<>(ctx.bakeLayer(ModelLayers.PLAYER), false), 0.5F * scale);
        this.texture = texture;
        this.scale = scale;
        if (glow != null) {
            this.addLayer(new GlowLayer<>(this, glow));
        }
    }

    @Override
    protected void scale(T entity, PoseStack poseStack, float partialTick) {
        if (scale != 1.0F) {
            poseStack.scale(scale, scale, scale);
        }
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return texture;
    }
}
