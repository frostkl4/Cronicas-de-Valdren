package com.frostkl4.valdren.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;

import javax.annotation.Nullable;

/**
 * Renderizador dos inimigos humanoides (Saqueador Carmesim, Cavaleiro Sem Luz) com modelo 3D próprio.
 *
 * Duas texturas:
 *  - modelTexture: a do modelo novo, usada quando este renderizador desenha;
 *  - skinTexture: no formato de skin de jogador, entregue ao Epic Fight, que desenha esses mobs
 *    com o esqueleto animado dele e pede a textura fora do nosso render().
 */
public class ValdrenHumanoidRenderer<T extends Mob> extends HumanoidMobRenderer<T, HumanoidModel<T>> {
    private final ResourceLocation modelTexture;
    private final ResourceLocation skinTexture;
    private final float scale;
    private boolean customPass;

    public ValdrenHumanoidRenderer(EntityRendererProvider.Context ctx, HumanoidModel<T> model,
                                   ResourceLocation modelTexture, ResourceLocation skinTexture,
                                   @Nullable ResourceLocation glow, float scale) {
        super(ctx, model, 0.5F * scale);
        this.modelTexture = modelTexture;
        this.skinTexture = skinTexture;
        this.scale = scale;
        if (glow != null) {
            this.addLayer(new GlowLayer<>(this, glow));
        }
    }

    @Override
    public void render(T entity, float entityYaw, float partialTick, PoseStack poseStack,
                       MultiBufferSource buffer, int packedLight) {
        customPass = true;
        try {
            super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
        } finally {
            customPass = false;
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
        return customPass ? modelTexture : skinTexture;
    }
}
