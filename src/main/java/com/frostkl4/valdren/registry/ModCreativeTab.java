package com.frostkl4.valdren.registry;

import com.frostkl4.valdren.Valdren;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public final class ModCreativeTab {
    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Valdren.MODID);

    public static final RegistryObject<CreativeModeTab> MAIN = TABS.register("main", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.valdren"))
            .icon(() -> new ItemStack(ModItems.STORM_EDGE.get()))
            .displayItems((params, output) -> {
                for (RegistryObject<Item> entry : ModItems.ITEMS.getEntries()) {
                    Item item = entry.get();
                    if (item == ModItems.FROST_SHARD.get() || item == ModItems.STORM_ORB.get()) {
                        continue;
                    }
                    output.accept(item);
                }
            })
            .build());

    private ModCreativeTab() {
    }
}
