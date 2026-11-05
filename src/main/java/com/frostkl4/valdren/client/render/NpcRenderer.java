package com.frostkl4.valdren.client.render;

import com.frostkl4.valdren.entity.npc.ValdrenNpc;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;

/** NPCs usam o corpo de jogador (skin 64x64 com camada externa). */
public class NpcRenderer extends HumanoidMobRenderer<ValdrenNpc, PlayerModel<ValdrenNpc>> {
    public NpcRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new PlayerModel<>(ctx.bakeLayer(ModelLayers.PLAYER), false), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(ValdrenNpc entity) {
        return entity.getNpcType().texture();
    }
}
