package com.frostkl4.valdren;

import com.frostkl4.valdren.network.ModNetwork;
import com.frostkl4.valdren.registry.ModCreativeTab;
import com.frostkl4.valdren.registry.ModEntities;
import com.frostkl4.valdren.registry.ModItems;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

/**
 * Crônicas de Valdren: NPCs com falas e missões, criaturas, chefes, espadas e um guia.
 * Compatível com Epic Fight (os arquivos em data/valdren/capabilities e epicfight_mobpatch
 * só são lidos quando o Epic Fight está instalado).
 */
@Mod(Valdren.MODID)
public class Valdren {
    public static final String MODID = "valdren";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Valdren() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.ITEMS.register(bus);
        ModEntities.ENTITIES.register(bus);
        ModCreativeTab.TABS.register(bus);
        bus.addListener(this::commonSetup);
        bus.addListener(ModEntities::registerAttributes);
        bus.addListener(ModEntities::registerSpawnPlacements);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(ModNetwork::register);
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MODID, path);
    }
}
