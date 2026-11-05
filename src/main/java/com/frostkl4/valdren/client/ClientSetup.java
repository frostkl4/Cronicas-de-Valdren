package com.frostkl4.valdren.client;

import com.frostkl4.valdren.Valdren;
import com.frostkl4.valdren.client.model.FrostWraithModel;
import com.frostkl4.valdren.client.model.ShadowWolfModel;
import com.frostkl4.valdren.client.model.StormDragonModel;
import com.frostkl4.valdren.client.render.CreatureRenderers;
import com.frostkl4.valdren.client.render.NpcRenderer;
import com.frostkl4.valdren.client.render.ValdrenHumanoidRenderer;
import com.frostkl4.valdren.registry.ModEntities;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Valdren.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ClientSetup {
    private ClientSetup() {
    }

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ShadowWolfModel.LAYER, ShadowWolfModel::createBodyLayer);
        event.registerLayerDefinition(FrostWraithModel.LAYER, FrostWraithModel::createBodyLayer);
        event.registerLayerDefinition(StormDragonModel.LAYER, StormDragonModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.NPC.get(), NpcRenderer::new);
        event.registerEntityRenderer(ModEntities.SHADOW_WOLF.get(), CreatureRenderers.Wolf::new);
        event.registerEntityRenderer(ModEntities.FROST_WRAITH.get(), CreatureRenderers.Wraith::new);
        event.registerEntityRenderer(ModEntities.STORM_DRAGON.get(), CreatureRenderers.Dragon::new);
        event.registerEntityRenderer(ModEntities.CRIMSON_RAIDER.get(), ctx -> new ValdrenHumanoidRenderer<>(ctx,
                Valdren.id("textures/entity/crimson_raider.png"), null, 1.0F));
        event.registerEntityRenderer(ModEntities.LIGHTLESS_KNIGHT.get(), ctx -> new ValdrenHumanoidRenderer<>(ctx,
                Valdren.id("textures/entity/lightless_knight.png"),
                Valdren.id("textures/entity/lightless_knight_glow.png"), 1.12F));
        event.registerEntityRenderer(ModEntities.FROST_SHARD.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(ModEntities.STORM_ORB.get(), ctx -> new ThrownItemRenderer<>(ctx, 2.5F, true));
    }
}
