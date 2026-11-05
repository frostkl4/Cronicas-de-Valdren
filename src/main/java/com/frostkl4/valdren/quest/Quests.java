package com.frostkl4.valdren.quest;

import com.frostkl4.valdren.entity.npc.NpcType;
import com.frostkl4.valdren.registry.ModItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Todas as missões. A ordem importa: o NPC oferece a primeira que o jogador pode pegar,
 * então as missões únicas vêm antes das repetíveis.
 */
public final class Quests {
    private static final int DAY = 24000;
    private static final Map<String, Quest> BY_ID = new LinkedHashMap<>();
    private static final Map<NpcType, List<Quest>> BY_NPC = new EnumMap<>(NpcType.class);

    static {
        // ----------------------------------------------------------- Bram
        add(Quest.kill("bram_wolves", NpcType.BRAM, "valdren:shadow_wolf", 5).affinity(15).xp(60)
                .reward(() -> new ItemStack(Items.EMERALD, 12))
                .reward(() -> new ItemStack(ModItems.ECHO_FRAGMENT.get(), 2)));
        add(Quest.kill("bram_raiders", NpcType.BRAM, "valdren:crimson_raider", 6).needs(10).affinity(20).xp(120)
                .reward(() -> new ItemStack(ModItems.CRIMSON_FANG.get())));
        add(Quest.collect("bram_iron", NpcType.BRAM, "minecraft:iron_ingot", 16).repeat(DAY).affinity(6).xp(30)
                .reward(() -> new ItemStack(Items.EMERALD, 8)));
        add(Quest.collect("bram_coal", NpcType.BRAM, "minecraft:coal", 24).repeat(DAY).affinity(4).xp(20)
                .reward(() -> new ItemStack(Items.EMERALD, 5)));

        // ----------------------------------------------------------- Iara
        add(Quest.collect("iara_wheat", NpcType.IARA, "minecraft:wheat", 20).affinity(10).xp(30)
                .reward(() -> new ItemStack(Items.BREAD, 6))
                .reward(() -> new ItemStack(Items.HONEY_BOTTLE, 2)));
        add(Quest.collect("iara_crystals", NpcType.IARA, "valdren:frost_crystal", 4).needs(10).affinity(20).xp(100)
                .reward(() -> new ItemStack(ModItems.VERDANT_BLADE.get())));
        add(Quest.collect("iara_berries", NpcType.IARA, "minecraft:sweet_berries", 16).repeat(DAY).affinity(5).xp(20)
                .reward(() -> new ItemStack(Items.EMERALD, 4))
                .reward(() -> new ItemStack(Items.GOLDEN_CARROT, 4)));
        add(Quest.collect("iara_mushrooms", NpcType.IARA, "minecraft:brown_mushroom", 8).repeat(DAY).affinity(5).xp(20)
                .reward(() -> PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.HEALING))
                .reward(() -> PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.HEALING)));

        // --------------------------------------------------------- Ravena
        add(Quest.kill("ravena_zombies", NpcType.RAVENA, "minecraft:zombie", 10).affinity(10).xp(50)
                .reward(() -> new ItemStack(Items.ARROW, 32))
                .reward(() -> new ItemStack(Items.EMERALD, 6)));
        add(Quest.kill("ravena_wraiths", NpcType.RAVENA, "valdren:frost_wraith", 4).needs(10).affinity(20).xp(120)
                .reward(() -> new ItemStack(ModItems.FROST_BLADE.get())));
        add(Quest.kill("ravena_knight", NpcType.RAVENA, "valdren:lightless_knight", 1).needs(30).affinity(35).xp(400)
                .start(() -> new ItemStack(ModItems.BROKEN_SEAL.get()))
                .reward(() -> new ItemStack(ModItems.MOONLIGHT_KATANA.get()))
                .reward(() -> new ItemStack(Items.DIAMOND, 4)));
        add(Quest.kill("ravena_hunt", NpcType.RAVENA, "valdren:shadow_wolf", 8).repeat(DAY).affinity(6).xp(60)
                .reward(() -> new ItemStack(Items.EMERALD, 8))
                .reward(() -> new ItemStack(Items.COOKED_BEEF, 8)));

        // ---------------------------------------------------------- Orvyn
        add(Quest.collect("orvyn_echoes", NpcType.ORVYN, "valdren:echo_fragment", 6).affinity(15).xp(80)
                .reward(() -> new ItemStack(ModItems.DUSK_BLADE.get())));
        add(Quest.kill("orvyn_dragon", NpcType.ORVYN, "valdren:storm_dragon", 1).needs(30).affinity(40).xp(600)
                .start(() -> new ItemStack(ModItems.STORM_HORN.get()))
                .reward(() -> new ItemStack(ModItems.ANCESTRAL_FLAME.get()))
                .reward(() -> new ItemStack(Items.DIAMOND, 8))
                .reward(() -> new ItemStack(Items.EXPERIENCE_BOTTLE, 16)));
        add(Quest.collect("orvyn_essence", NpcType.ORVYN, "valdren:shadow_essence", 10).repeat(DAY).affinity(6).xp(60)
                .reward(() -> new ItemStack(Items.EMERALD, 10))
                .reward(() -> new ItemStack(Items.EXPERIENCE_BOTTLE, 4)));
        add(Quest.collect("orvyn_books", NpcType.ORVYN, "minecraft:book", 6).repeat(DAY).affinity(5).xp(30)
                .reward(() -> new ItemStack(Items.EMERALD, 6))
                .reward(() -> new ItemStack(Items.LAPIS_LAZULI, 8)));
    }

    private Quests() {
    }

    private static void add(Quest q) {
        BY_ID.put(q.id, q);
        BY_NPC.computeIfAbsent(q.npc, k -> new ArrayList<>()).add(q);
    }

    @Nullable
    public static Quest get(String id) {
        return BY_ID.get(id);
    }

    public static List<Quest> forNpc(NpcType npc) {
        return BY_NPC.getOrDefault(npc, Collections.emptyList());
    }

    public static Iterable<Quest> all() {
        return BY_ID.values();
    }
}
