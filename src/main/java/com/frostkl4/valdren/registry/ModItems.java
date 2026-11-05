package com.frostkl4.valdren.registry;

import com.frostkl4.valdren.Valdren;
import com.frostkl4.valdren.entity.npc.NpcType;
import com.frostkl4.valdren.item.BossSummonItem;
import com.frostkl4.valdren.item.GuideBookItem;
import com.frostkl4.valdren.item.LoreItem;
import com.frostkl4.valdren.item.NpcSummonItem;
import com.frostkl4.valdren.item.SwordEffect;
import com.frostkl4.valdren.item.ValdrenSwordItem;
import com.frostkl4.valdren.item.ValdrenTiers;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.EnumMap;
import java.util.Map;

public final class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Valdren.MODID);

    // ------------------------------------------------------------------ espadas
    public static final RegistryObject<Item> FROST_BLADE = sword("frost_blade", ValdrenTiers.RELIC, 3, -2.4F, SwordEffect.FROST, Rarity.RARE);
    public static final RegistryObject<Item> STORM_EDGE = sword("storm_edge", ValdrenTiers.MYTHIC, 3, -2.4F, SwordEffect.STORM, Rarity.EPIC);
    public static final RegistryObject<Item> CRIMSON_FANG = sword("crimson_fang", ValdrenTiers.RELIC, 2, -2.2F, SwordEffect.LIFESTEAL, Rarity.RARE);
    public static final RegistryObject<Item> VERDANT_BLADE = sword("verdant_blade", ValdrenTiers.RELIC, 2, -2.4F, SwordEffect.VERDANT, Rarity.RARE);
    public static final RegistryObject<Item> DUSK_BLADE = sword("dusk_blade", ValdrenTiers.RELIC, 3, -2.5F, SwordEffect.DUSK, Rarity.RARE);
    public static final RegistryObject<Item> ANCESTRAL_FLAME = sword("ancestral_flame", ValdrenTiers.RELIC, 3, -2.4F, SwordEffect.FLAME, Rarity.EPIC);
    public static final RegistryObject<Item> SENTINEL_GREATSWORD = sword("sentinel_greatsword", ValdrenTiers.MYTHIC, 6, -3.1F, SwordEffect.SENTINEL, Rarity.EPIC);
    public static final RegistryObject<Item> MOONLIGHT_KATANA = sword("moonlight_katana", ValdrenTiers.MYTHIC, 2, -2.2F, SwordEffect.MOON, Rarity.EPIC);

    // ---------------------------------------------------------------- materiais
    public static final RegistryObject<Item> ECHO_FRAGMENT = ITEMS.register("echo_fragment", () -> new LoreItem(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> SHADOW_ESSENCE = ITEMS.register("shadow_essence", () -> new LoreItem(new Item.Properties()));
    public static final RegistryObject<Item> FROST_CRYSTAL = ITEMS.register("frost_crystal", () -> new LoreItem(new Item.Properties()));
    public static final RegistryObject<Item> STORM_SCALE = ITEMS.register("storm_scale", () -> new LoreItem(new Item.Properties().rarity(Rarity.RARE).fireResistant()));

    // ------------------------------------------------------------ guia e chefes
    public static final RegistryObject<Item> GUIDE_BOOK = ITEMS.register("guide_book", () -> new GuideBookItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> STORM_HORN = ITEMS.register("storm_horn",
            () -> new BossSummonItem(ModEntities.STORM_DRAGON, true, new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> BROKEN_SEAL = ITEMS.register("broken_seal",
            () -> new BossSummonItem(ModEntities.LIGHTLESS_KNIGHT, false, new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));

    // ----------------------------------------------------- projeteis (sem aba)
    public static final RegistryObject<Item> FROST_SHARD = ITEMS.register("frost_shard", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STORM_ORB = ITEMS.register("storm_orb", () -> new Item(new Item.Properties()));

    // --------------------------------------------------------------- ovos e NPCs
    public static final RegistryObject<Item> SHADOW_WOLF_EGG = ITEMS.register("shadow_wolf_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.SHADOW_WOLF, 0x15111F, 0xB45CFF, new Item.Properties()));
    public static final RegistryObject<Item> FROST_WRAITH_EGG = ITEMS.register("frost_wraith_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.FROST_WRAITH, 0x141E35, 0x8FF6FF, new Item.Properties()));
    public static final RegistryObject<Item> CRIMSON_RAIDER_EGG = ITEMS.register("crimson_raider_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.CRIMSON_RAIDER, 0x6E1F1F, 0x9AA0AC, new Item.Properties()));
    public static final RegistryObject<Item> LIGHTLESS_KNIGHT_EGG = ITEMS.register("lightless_knight_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.LIGHTLESS_KNIGHT, 0x1A1A22, 0x9B6BFF, new Item.Properties()));
    public static final RegistryObject<Item> STORM_DRAGON_EGG = ITEMS.register("storm_dragon_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.STORM_DRAGON, 0x1F2733, 0x74F2FF, new Item.Properties()));

    public static final Map<NpcType, RegistryObject<Item>> NPC_CALLS = new EnumMap<>(NpcType.class);

    static {
        for (NpcType type : NpcType.values()) {
            NPC_CALLS.put(type, ITEMS.register("npc_" + type.id,
                    () -> new NpcSummonItem(type, new Item.Properties().stacksTo(16))));
        }
    }

    private ModItems() {
    }

    private static RegistryObject<Item> sword(String name, ValdrenTiers tier, int dmg, float speed, SwordEffect effect, Rarity rarity) {
        return ITEMS.register(name, () -> new ValdrenSwordItem(tier, dmg, speed, effect,
                new Item.Properties().rarity(rarity).fireResistant()));
    }
}
