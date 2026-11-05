package com.frostkl4.valdren.client.render;

import com.frostkl4.valdren.client.model.BramModel;
import com.frostkl4.valdren.client.model.IaraModel;
import com.frostkl4.valdren.client.model.OrvynModel;
import com.frostkl4.valdren.client.model.RavenaModel;
import com.frostkl4.valdren.entity.npc.NpcType;
import com.frostkl4.valdren.entity.npc.ValdrenNpc;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;

import java.util.EnumMap;
import java.util.Map;

/**
 * Cada viajante tem modelo próprio (roupas, cabelo e acessórios em 3D). O renderizador troca
 * o modelo conforme o tipo do NPC antes de desenhar; textura em textures/entity/npc/&lt;id&gt;.png.
 */
public class NpcRenderer extends HumanoidMobRenderer<ValdrenNpc, HumanoidModel<ValdrenNpc>> {
    private final Map<NpcType, HumanoidModel<ValdrenNpc>> models = new EnumMap<>(NpcType.class);

    public NpcRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new BramModel<ValdrenNpc>(ctx.bakeLayer(BramModel.LAYER)), 0.5F);
        models.put(NpcType.BRAM, this.model);
        models.put(NpcType.IARA, new IaraModel<ValdrenNpc>(ctx.bakeLayer(IaraModel.LAYER)));
        models.put(NpcType.ORVYN, new OrvynModel<ValdrenNpc>(ctx.bakeLayer(OrvynModel.LAYER)));
        models.put(NpcType.RAVENA, new RavenaModel<ValdrenNpc>(ctx.bakeLayer(RavenaModel.LAYER)));
        this.addLayer(new NpcGlowLayer(this));
    }

    @Override
    public void render(ValdrenNpc entity, float entityYaw, float partialTick, PoseStack poseStack,
                       MultiBufferSource buffer, int packedLight) {
        HumanoidModel<ValdrenNpc> m = models.get(entity.getNpcType());
        if (m != null) {
            this.model = m;
        }
        super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(ValdrenNpc entity) {
        return entity.getNpcType().texture();
    }
}
