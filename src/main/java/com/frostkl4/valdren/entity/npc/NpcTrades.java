package com.frostkl4.valdren.entity.npc;

import com.frostkl4.valdren.quest.Affinity;
import com.frostkl4.valdren.registry.ModItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.ItemLike;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/** Trocas de cada NPC. Algumas só aparecem com mais afinidade; amigos ganham desconto. */
public final class NpcTrades {
    private record Trade(int minAffinity, Supplier<ItemStack> costA, Supplier<ItemStack> costB,
                         Supplier<ItemStack> result, int maxUses) {
    }

    private static final Map<NpcType, List<Trade>> TRADES = new EnumMap<>(NpcType.class);

    static {
        // Bram: compra ferro, vende equipamento e lâminas forjadas
        add(NpcType.BRAM, 0, s(Items.IRON_INGOT, 6), null, s(Items.EMERALD, 1), 16);
        add(NpcType.BRAM, 0, s(Items.EMERALD, 3), null, s(Items.SHIELD, 1), 4);
        add(NpcType.BRAM, 0, s(Items.EMERALD, 9), null, s(Items.IRON_CHESTPLATE, 1), 2);
        add(NpcType.BRAM, 10, s(Items.EMERALD, 10), s(ModItems.FROST_CRYSTAL.get(), 2), s(ModItems.FROST_BLADE.get(), 1), 1);
        add(NpcType.BRAM, 10, s(Items.EMERALD, 14), s(ModItems.SHADOW_ESSENCE.get(), 4), s(ModItems.CRIMSON_FANG.get(), 1), 1);
        add(NpcType.BRAM, 60, s(Items.EMERALD, 20), s(Items.DIAMOND, 1), s(ModItems.ANCESTRAL_FLAME.get(), 1), 1);

        // Iara: compra colheita, vende comida e poções
        add(NpcType.IARA, 0, s(Items.WHEAT, 20), null, s(Items.EMERALD, 1), 16);
        add(NpcType.IARA, 0, s(Items.EMERALD, 2), null, s(Items.BREAD, 6), 12);
        add(NpcType.IARA, 0, s(Items.EMERALD, 3), null, s(Items.HONEY_BOTTLE, 4), 8);
        add(NpcType.IARA, 0, s(Items.EMERALD, 4), null, () -> PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.HEALING), 6);
        add(NpcType.IARA, 10, s(Items.EMERALD, 6), null, s(Items.GOLDEN_APPLE, 1), 4);
        add(NpcType.IARA, 30, s(Items.EMERALD, 12), s(ModItems.FROST_CRYSTAL.get(), 3), s(ModItems.VERDANT_BLADE.get(), 1), 1);

        // Ravena: compra restos de monstro, vende arcos, flechas e o Selo Partido
        add(NpcType.RAVENA, 0, s(Items.ROTTEN_FLESH, 24), null, s(Items.EMERALD, 1), 16);
        add(NpcType.RAVENA, 0, s(Items.EMERALD, 2), null, s(Items.ARROW, 16), 12);
        add(NpcType.RAVENA, 0, s(Items.EMERALD, 6), null, s(Items.BOW, 1), 3);
        add(NpcType.RAVENA, 10, s(Items.EMERALD, 5), null, s(Items.SPECTRAL_ARROW, 8), 8);
        add(NpcType.RAVENA, 10, s(Items.EMERALD, 16), s(ModItems.ECHO_FRAGMENT.get(), 2), s(ModItems.BROKEN_SEAL.get(), 1), 1);
        add(NpcType.RAVENA, 30, s(Items.EMERALD, 12), s(ModItems.FROST_CRYSTAL.get(), 4), s(ModItems.FROST_BLADE.get(), 1), 1);

        // Orvyn: compra fragmentos de eco, vende saber e o Chifre da Tempestade
        add(NpcType.ORVYN, 0, s(Items.EMERALD, 1), null, s(ModItems.GUIDE_BOOK.get(), 1), 4);
        add(NpcType.ORVYN, 0, s(ModItems.ECHO_FRAGMENT.get(), 3), null, s(Items.EMERALD, 5), 16);
        add(NpcType.ORVYN, 0, s(Items.EMERALD, 2), null, s(Items.LAPIS_LAZULI, 6), 12);
        add(NpcType.ORVYN, 10, s(Items.EMERALD, 6), null, s(Items.EXPERIENCE_BOTTLE, 4), 8);
        add(NpcType.ORVYN, 30, s(Items.EMERALD, 32), s(ModItems.ECHO_FRAGMENT.get(), 4), s(ModItems.STORM_HORN.get(), 1), 1);
        add(NpcType.ORVYN, 60, s(Items.EMERALD, 16), s(ModItems.ECHO_FRAGMENT.get(), 6), s(ModItems.DUSK_BLADE.get(), 1), 1);
    }

    private NpcTrades() {
    }

    private static Supplier<ItemStack> s(ItemLike item, int count) {
        return () -> new ItemStack(item, count);
    }

    private static void add(NpcType npc, int minAffinity, Supplier<ItemStack> a, Supplier<ItemStack> b,
                            Supplier<ItemStack> result, int maxUses) {
        TRADES.computeIfAbsent(npc, k -> new ArrayList<>())
                .add(new Trade(minAffinity, a, b == null ? () -> ItemStack.EMPTY : b, result, maxUses));
    }

    public static MerchantOffers build(NpcType npc, int affinity) {
        MerchantOffers offers = new MerchantOffers();
        int tier = Affinity.tier(affinity);
        for (Trade t : TRADES.getOrDefault(npc, List.of())) {
            if (affinity < t.minAffinity()) {
                continue;
            }
            ItemStack a = t.costA().get();
            MerchantOffer offer = new MerchantOffer(a, t.costB().get(), t.result().get(), t.maxUses(), 2, 0.05F);
            if (tier >= 2 && a.is(Items.EMERALD) && a.getCount() > 2) {
                int off = tier >= 3 ? Math.max(1, a.getCount() / 4) : Math.max(1, a.getCount() / 8);
                offer.setSpecialPriceDiff(-Math.min(off, a.getCount() - 1));
            }
            offers.add(offer);
        }
        return offers;
    }
}
