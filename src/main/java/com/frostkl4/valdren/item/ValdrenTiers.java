package com.frostkl4.valdren.item;

import com.frostkl4.valdren.registry.ModItems;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

/** Materiais das espadas de Valdren. */
public enum ValdrenTiers implements Tier {
    /** Relíquias de vila: forjadas por Bram ou encontradas em missões. */
    RELIC(4, 1800, 9.0F, 4.0F, 18, () -> Ingredient.of(ModItems.ECHO_FRAGMENT.get())),
    /** Armas de chefe: feitas com escamas de tempestade. */
    MYTHIC(4, 2600, 10.0F, 5.0F, 22, () -> Ingredient.of(ModItems.STORM_SCALE.get()));

    private final int level;
    private final int uses;
    private final float speed;
    private final float damage;
    private final int enchantability;
    private final Supplier<Ingredient> repair;

    ValdrenTiers(int level, int uses, float speed, float damage, int enchantability, Supplier<Ingredient> repair) {
        this.level = level;
        this.uses = uses;
        this.speed = speed;
        this.damage = damage;
        this.enchantability = enchantability;
        this.repair = repair;
    }

    @Override
    public int getUses() {
        return uses;
    }

    @Override
    public float getSpeed() {
        return speed;
    }

    @Override
    public float getAttackDamageBonus() {
        return damage;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public int getEnchantmentValue() {
        return enchantability;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return repair.get();
    }
}
