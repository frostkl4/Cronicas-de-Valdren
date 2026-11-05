package com.frostkl4.valdren.client.render;

import com.frostkl4.valdren.Valdren;
import com.frostkl4.valdren.entity.npc.NpcType;
import com.frostkl4.valdren.entity.npc.ValdrenNpc;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

import java.util.EnumMap;
import java.util.Map;

/** Partes que brilham no escuro de cada NPC (ex.: as estrelas bordadas no manto do Orvyn). */
public class NpcGlowLayer extends RenderLayer<ValdrenNpc, HumanoidModel<ValdrenNpc>> {
    private static final Map<NpcType, ResourceLocation> GLOW = new EnumMap<>(NpcType.class);

    static {
        GLOW.put(NpcType.ORVYN, Valdren.id("textures/entity/npc/orvyn_glow.png"));
    }

    public NpcGlowLayer(RenderLayerParent<ValdrenNpc, HumanoidModel<ValdrenNpc>> parent) {
        super(parent);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, ValdrenNpc entity,
                       float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks,
                       float netHeadYaw, float headPitch) {
        ResourceLocation tex = GLOW.get(entity.getNpcType());
        if (tex == null || entity.isInvisible()) {
            return;
        }
        VertexConsumer vc = buffer.getBuffer(RenderType.eyes(tex));
        this.getParentModel().renderToBuffer(poseStack, vc, 15728640, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }
}
