package com.frostkl4.valdren.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Locale;

/**
 * Espada de Valdren. Funciona sozinha e também com o Epic Fight: o moveset vem do arquivo
 * data/valdren/capabilities/weapons/&lt;nome&gt;.json, e o efeito especial roda em hurtEnemy,
 * que o Epic Fight também chama nos golpes dele.
 */
public class ValdrenSwordItem extends SwordItem {
    private final SwordEffect effect;

    public ValdrenSwordItem(Tier tier, int damage, float speed, SwordEffect effect, Properties props) {
        super(tier, damage, speed, props);
        this.effect = effect;
    }

    public SwordEffect getEffect() {
        return effect;
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker.level() instanceof ServerLevel level) {
            applyEffect(level, target, attacker);
        }
        return super.hurtEnemy(stack, target, attacker);
    }

    private void applyEffect(ServerLevel level, LivingEntity target, LivingEntity attacker) {
        switch (effect) {
            case FROST -> {
                target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 1), attacker);
                target.setTicksFrozen(Math.min(target.getTicksRequiredToFreeze() + 40, target.getTicksFrozen() + 70));
                burst(level, target, ParticleTypes.SNOWFLAKE, 14);
            }
            case STORM -> {
                if (attacker.getRandom().nextFloat() < 0.35F) {
                    extraDamage(level, target, attacker, 3.0F);
                    burst(level, target, ParticleTypes.ELECTRIC_SPARK, 20);
                    List<LivingEntity> near = level.getEntitiesOfClass(LivingEntity.class,
                            target.getBoundingBox().inflate(4.0D),
                            e -> e != attacker && e != target && e.isAlive() && e instanceof Enemy);
                    int chained = 0;
                    for (LivingEntity e : near) {
                        if (chained++ >= 2) {
                            break;
                        }
                        line(level, target, e);
                        extraDamage(level, e, attacker, 4.0F);
                    }
                    level.playSound(null, target.blockPosition(), SoundEvents.LIGHTNING_BOLT_IMPACT,
                            SoundSource.PLAYERS, 0.5F, 1.7F);
                }
            }
            case LIFESTEAL -> {
                attacker.heal(1.5F);
                burst(level, target, ParticleTypes.CRIMSON_SPORE, 10);
                burst(level, attacker, ParticleTypes.HEART, 1);
            }
            case VERDANT -> {
                target.addEffect(new MobEffectInstance(MobEffects.POISON, 80, 0), attacker);
                burst(level, target, ParticleTypes.COMPOSTER, 12);
            }
            case DUSK -> {
                target.addEffect(new MobEffectInstance(MobEffects.WITHER, 60, 0), attacker);
                burst(level, target, ParticleTypes.WITCH, 12);
            }
            case FLAME -> {
                target.setSecondsOnFire(5);
                burst(level, target, ParticleTypes.FLAME, 14);
            }
            case SENTINEL -> {
                target.knockback(0.9D, attacker.getX() - target.getX(), attacker.getZ() - target.getZ());
                target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 60, 0), attacker);
                burst(level, target, ParticleTypes.ENCHANTED_HIT, 16);
            }
            case MOON -> {
                if (level.isNight()) {
                    extraDamage(level, target, attacker, 4.0F);
                    burst(level, target, ParticleTypes.END_ROD, 14);
                } else {
                    burst(level, target, ParticleTypes.END_ROD, 4);
                }
            }
        }
    }

    private static void extraDamage(ServerLevel level, LivingEntity target, LivingEntity attacker, float amount) {
        if (!target.isAlive()) {
            return;
        }
        target.invulnerableTime = 0;
        target.hurt(level.damageSources().indirectMagic(attacker, attacker), amount);
    }

    private static void burst(ServerLevel level, Entity e, ParticleOptions type, int count) {
        level.sendParticles(type, e.getX(), e.getY() + e.getBbHeight() * 0.6D, e.getZ(), count,
                e.getBbWidth() * 0.4D, e.getBbHeight() * 0.3D, e.getBbWidth() * 0.4D, 0.05D);
    }

    private static void line(ServerLevel level, Entity a, Entity b) {
        Vec3 from = a.position().add(0, a.getBbHeight() * 0.6D, 0);
        Vec3 to = b.position().add(0, b.getBbHeight() * 0.6D, 0);
        for (int i = 0; i <= 10; i++) {
            Vec3 p = from.lerp(to, i / 10.0D);
            level.sendParticles(ParticleTypes.ELECTRIC_SPARK, p.x, p.y, p.z, 1, 0.05D, 0.05D, 0.05D, 0.0D);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable(this.getDescriptionId() + ".lore")
                .withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
        tooltip.add(Component.translatable("valdren.effect." + effect.name().toLowerCase(Locale.ROOT))
                .withStyle(effect.color));
        super.appendHoverText(stack, level, tooltip, flag);
    }
}
