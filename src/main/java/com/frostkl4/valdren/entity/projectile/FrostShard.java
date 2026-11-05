package com.frostkl4.valdren.entity.projectile;

import com.frostkl4.valdren.entity.mob.FrostWraith;
import com.frostkl4.valdren.registry.ModEntities;
import com.frostkl4.valdren.registry.ModItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

/** Estilhaço de gelo do Espectro Gélido: dano, lentidão e congelamento. */
public class FrostShard extends ThrowableItemProjectile {
    public FrostShard(EntityType<? extends FrostShard> type, Level level) {
        super(type, level);
    }

    public FrostShard(Level level, LivingEntity owner) {
        super(ModEntities.FROST_SHARD.get(), owner, level);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.FROST_SHARD.get();
    }

    @Override
    protected float getGravity() {
        return 0.01F;
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        Entity hit = result.getEntity();
        if (hit instanceof FrostWraith || hit == this.getOwner()) {
            return;
        }
        hit.hurt(this.damageSources().thrown(this, this.getOwner()), 5.0F);
        if (hit instanceof LivingEntity living) {
            living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 1), this.getOwner());
            living.setTicksFrozen(Math.min(living.getTicksRequiredToFreeze() + 40, living.getTicksFrozen() + 80));
        }
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (this.level() instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.SNOWFLAKE, getX(), getY(), getZ(), 12, 0.2D, 0.2D, 0.2D, 0.05D);
            this.discard();
        }
    }
}
