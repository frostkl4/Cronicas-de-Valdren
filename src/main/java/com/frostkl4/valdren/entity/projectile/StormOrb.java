package com.frostkl4.valdren.entity.projectile;

import com.frostkl4.valdren.entity.boss.StormDragon;
import com.frostkl4.valdren.registry.ModEntities;
import com.frostkl4.valdren.registry.ModItems;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

/** Orbe de tempestade do Vorthrax: explode em um raio (visual) com dano em área, sem fogo. */
public class StormOrb extends Fireball {
    public StormOrb(EntityType<? extends StormOrb> type, Level level) {
        super(type, level);
    }

    public StormOrb(Level level, LivingEntity shooter, double dx, double dy, double dz) {
        super(ModEntities.STORM_ORB.get(), shooter, dx, dy, dz, level);
        this.setItem(new ItemStack(ModItems.STORM_ORB.get()));
    }

    @Override
    protected boolean shouldBurn() {
        return false;
    }

    @Override
    protected ParticleOptions getTrailParticle() {
        return ParticleTypes.ELECTRIC_SPARK;
    }

    @Override
    public boolean isPickable() {
        return false;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        return false;
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        Entity hit = result.getEntity();
        if (hit instanceof StormDragon || hit == this.getOwner()) {
            return;
        }
        hit.hurt(this.damageSources().indirectMagic(this, this.getOwner()), 9.0F);
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!(this.level() instanceof ServerLevel server)) {
            return;
        }
        LightningBolt bolt = EntityType.LIGHTNING_BOLT.create(server);
        if (bolt != null) {
            bolt.moveTo(this.position());
            bolt.setVisualOnly(true);
            server.addFreshEntity(bolt);
        }
        for (LivingEntity e : server.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(2.5D))) {
            if (!(e instanceof StormDragon)) {
                e.hurt(this.damageSources().indirectMagic(this, this.getOwner()), 5.0F);
            }
        }
        server.sendParticles(ParticleTypes.ELECTRIC_SPARK, getX(), getY(), getZ(), 30, 1.0D, 1.0D, 1.0D, 0.2D);
        this.discard();
    }
}
