package com.frostkl4.valdren.entity.boss;

import com.frostkl4.valdren.entity.projectile.StormOrb;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Vorthrax, o Dragão da Tempestade. Alterna entre luta no chão (mordida e rabada) e voo
 * (orbes de tempestade, chuva de raios marcados no chão e mergulho com impacto).
 * Abaixo de 50% de vida os ciclos ficam mais rápidos e caem mais raios.
 */
public class StormDragon extends Monster {
    private static final EntityDataAccessor<Boolean> FLYING =
            SynchedEntityData.defineId(StormDragon.class, EntityDataSerializers.BOOLEAN);
    private static final byte ROAR_EVENT = 61;

    private enum Phase { GROUND, TAKEOFF, FLY, DIVE }

    private record Strike(Vec3 pos, int delay) {
    }

    private final ServerBossEvent bossEvent = new ServerBossEvent(Component.translatable("entity.valdren.storm_dragon"),
            BossEvent.BossBarColor.BLUE, BossEvent.BossBarOverlay.PROGRESS);
    private final List<Strike> strikes = new ArrayList<>();
    private Phase phase = Phase.GROUND;
    private int phaseTimer = 200;
    private int biteCooldown;
    private int tailCooldown = 80;
    private int orbCooldown;
    private int orbsFired;
    private int stuckTicks;
    private Vec3 lastPos = Vec3.ZERO;
    private float circleAngle;
    private boolean enraged;
    private int roarTicks;

    public StormDragon(EntityType<? extends StormDragon> type, Level level) {
        super(type, level);
        this.xpReward = 600;
        this.setPersistenceRequired();
        this.setMaxUpStep(1.5F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 520.0D)
                .add(Attributes.ATTACK_DAMAGE, 14.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.28D)
                .add(Attributes.ARMOR, 14.0D)
                .add(Attributes.ARMOR_TOUGHNESS, 6.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                .add(Attributes.FOLLOW_RANGE, 80.0D);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(FLYING, false);
    }

    public boolean isFlying() {
        return this.entityData.get(FLYING);
    }

    private void setFlying(boolean flying) {
        this.entityData.set(FLYING, flying);
        this.setNoGravity(flying);
    }

    @Override
    protected void registerGoals() {
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, false));
    }

    // ------------------------------------------------------------- rugido
    public float getRoarAmount(float partialTick) {
        if (roarTicks <= 0) {
            return 0.0F;
        }
        float t = (roarTicks - partialTick) / 30.0F;
        return Mth.clamp(Mth.sin(t * Mth.PI), 0.0F, 1.0F);
    }

    private void roar() {
        this.level().broadcastEntityEvent(this, ROAR_EVENT);
        this.playSound(SoundEvents.ENDER_DRAGON_GROWL, 3.0F, 1.2F);
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == ROAR_EVENT) {
            this.roarTicks = 30;
        } else {
            super.handleEntityEvent(id);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            if (roarTicks > 0) {
                roarTicks--;
            }
            if (this.random.nextInt(3) == 0) {
                this.level().addParticle(ParticleTypes.ELECTRIC_SPARK, getRandomX(1.2D), getRandomY(), getRandomZ(1.2D),
                        0.0D, 0.0D, 0.0D);
            }
        }
    }

    // --------------------------------------------------------------- IA
    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        ServerLevel level = (ServerLevel) this.level();
        this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
        tickStrikes(level);

        if (!enraged && this.getHealth() < this.getMaxHealth() * 0.5F) {
            enraged = true;
            roar();
            for (ServerPlayer p : level.players()) {
                if (p.distanceToSqr(this) < 80.0D * 80.0D) {
                    p.sendSystemMessage(Component.translatable("entity.valdren.storm_dragon.enrage")
                            .withStyle(ChatFormatting.AQUA, ChatFormatting.ITALIC));
                }
            }
        }

        LivingEntity target = this.getTarget();
        if (target == null || !target.isAlive()) {
            if (isFlying()) {
                Vec3 v = this.getDeltaMovement();
                this.setDeltaMovement(v.x * 0.8D, -0.25D, v.z * 0.8D);
                if (this.onGround() || this.verticalCollisionBelow) {
                    setFlying(false);
                    phase = Phase.GROUND;
                }
            }
            return;
        }

        switch (phase) {
            case GROUND -> groundTick(level, target);
            case TAKEOFF -> takeoffTick();
            case FLY -> flyTick(level, target);
            case DIVE -> diveTick(level, target);
        }
    }

    private void groundTick(ServerLevel level, LivingEntity target) {
        if (isFlying()) {
            setFlying(false);
        }
        double dist = this.distanceTo(target);
        this.getLookControl().setLookAt(target, 10.0F, 10.0F);
        if (dist > 5.0D) {
            this.getNavigation().moveTo(target, 1.0D);
        } else {
            this.getNavigation().stop();
            faceTowards(target.position(), 8.0F);
        }
        if (--biteCooldown <= 0 && dist < 6.0D) {
            this.swing(InteractionHand.MAIN_HAND);
            this.doHurtTarget(target);
            biteCooldown = enraged ? 18 : 26;
        }
        if (--tailCooldown <= 0 && dist < 8.0D) {
            tailSweep(level);
            tailCooldown = enraged ? 80 : 120;
        }
        // preso ou longe demais: levanta voo
        if (this.tickCount % 40 == 0) {
            stuckTicks = (this.position().distanceToSqr(lastPos) < 1.0D && dist > 7.0D) ? stuckTicks + 40 : 0;
            lastPos = this.position();
        }
        if (--phaseTimer <= 0 || dist > 20.0D || stuckTicks >= 80) {
            phase = Phase.TAKEOFF;
            phaseTimer = 30;
            stuckTicks = 0;
            roar();
        }
    }

    private void takeoffTick() {
        setFlying(true);
        this.getNavigation().stop();
        Vec3 v = this.getDeltaMovement();
        this.setDeltaMovement(v.x * 0.6D, 0.38D, v.z * 0.6D);
        if (this.tickCount % 10 == 0) {
            this.playSound(SoundEvents.ENDER_DRAGON_FLAP, 2.0F, 0.9F);
        }
        if (--phaseTimer <= 0) {
            phase = Phase.FLY;
            phaseTimer = enraged ? 260 : 220;
            orbCooldown = 30;
            orbsFired = 0;
        }
    }

    private void flyTick(ServerLevel level, LivingEntity target) {
        setFlying(true);
        this.getNavigation().stop();
        circleAngle += enraged ? 0.035F : 0.028F;
        double ground = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, target.blockPosition()).getY();
        Vec3 desired = new Vec3(target.getX() + Math.cos(circleAngle) * 14.0D,
                Math.max(target.getY(), ground) + 10.0D + Math.sin(this.tickCount * 0.05D) * 1.5D,
                target.getZ() + Math.sin(circleAngle) * 14.0D);
        flyToward(desired, 0.09D, 0.9D);
        faceTowards(target.position(), 12.0F);
        if (this.tickCount % 14 == 0) {
            this.playSound(SoundEvents.ENDER_DRAGON_FLAP, 2.0F, 0.9F);
        }
        if (--orbCooldown <= 0 && this.hasLineOfSight(target)) {
            shootOrb(level, target);
            orbCooldown = enraged ? 32 : 48;
            if (++orbsFired >= 3) {
                callStorm(target);
                orbsFired = 0;
            }
        }
        if (--phaseTimer <= 0) {
            phase = Phase.DIVE;
            phaseTimer = 80;
            roar();
        }
    }

    private void diveTick(ServerLevel level, LivingEntity target) {
        setFlying(true);
        Vec3 desired = target.position();
        flyToward(desired, 0.2D, 1.6D);
        faceTowards(target.position(), 15.0F);
        boolean landed = this.onGround() || this.verticalCollisionBelow
                || (this.getY() - target.getY() < 1.5D && this.distanceToSqr(target) < 16.0D);
        if (landed || --phaseTimer <= 0) {
            slam(level);
            setFlying(false);
            phase = Phase.GROUND;
            phaseTimer = enraged ? 180 : 240;
        }
    }

    private void flyToward(Vec3 desired, double accel, double max) {
        Vec3 diff = desired.subtract(this.position());
        if (diff.lengthSqr() < 1.0E-4D) {
            return;
        }
        Vec3 v = this.getDeltaMovement().scale(0.9D).add(diff.normalize().scale(accel));
        if (v.length() > max) {
            v = v.normalize().scale(max);
        }
        this.setDeltaMovement(v);
        this.hasImpulse = true;
    }

    private void faceTowards(Vec3 point, float maxTurn) {
        double dx = point.x - this.getX();
        double dz = point.z - this.getZ();
        float yaw = (float) (Mth.atan2(dz, dx) * (180.0D / Math.PI)) - 90.0F;
        float rot = Mth.approachDegrees(this.getYRot(), yaw, maxTurn);
        this.setYRot(rot);
        this.yBodyRot = rot;
        this.yHeadRot = rot;
    }

    // ---------------------------------------------------------- ataques
    private void shootOrb(ServerLevel level, LivingEntity target) {
        Vec3 look = Vec3.directionFromRotation(0.0F, this.getYRot());
        Vec3 mouth = this.position().add(look.scale(4.5D)).add(0.0D, 2.2D, 0.0D);
        Vec3 dir = target.position().add(0.0D, target.getBbHeight() * 0.5D, 0.0D).subtract(mouth);
        StormOrb orb = new StormOrb(level, this, dir.x, dir.y, dir.z);
        orb.setPos(mouth.x, mouth.y, mouth.z);
        level.addFreshEntity(orb);
        this.swing(InteractionHand.MAIN_HAND);
        this.playSound(SoundEvents.TRIDENT_THUNDER, 1.5F, 1.4F);
    }

    private void callStorm(LivingEntity target) {
        int count = enraged ? 6 : 3;
        strikes.add(new Strike(target.position(), 24));
        for (int i = 1; i < count; i++) {
            double a = this.random.nextDouble() * Math.PI * 2.0D;
            double r = 2.0D + this.random.nextDouble() * 4.0D;
            strikes.add(new Strike(target.position().add(Math.cos(a) * r, 0.0D, Math.sin(a) * r), 24 + i * 6));
        }
        roar();
    }

    private void tickStrikes(ServerLevel level) {
        Iterator<Strike> it = strikes.iterator();
        List<Strike> next = new ArrayList<>();
        while (it.hasNext()) {
            Strike s = it.next();
            it.remove();
            int d = s.delay() - 1;
            BlockPos ground = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BlockPos.containing(s.pos()));
            Vec3 at = new Vec3(s.pos().x, ground.getY(), s.pos().z);
            if (d > 0) {
                if (d % 3 == 0) {
                    for (int k = 0; k < 10; k++) {
                        double a = k * Math.PI / 5.0D;
                        level.sendParticles(ParticleTypes.ELECTRIC_SPARK, at.x + Math.cos(a) * 1.6D, at.y + 0.15D,
                                at.z + Math.sin(a) * 1.6D, 1, 0.0D, 0.05D, 0.0D, 0.0D);
                    }
                }
                next.add(new Strike(s.pos(), d));
                continue;
            }
            LightningBolt bolt = EntityType.LIGHTNING_BOLT.create(level);
            if (bolt != null) {
                bolt.moveTo(at);
                bolt.setVisualOnly(true);
                level.addFreshEntity(bolt);
            }
            for (LivingEntity e : level.getEntitiesOfClass(LivingEntity.class,
                    new net.minecraft.world.phys.AABB(at, at).inflate(2.2D, 3.0D, 2.2D))) {
                if (e != this) {
                    e.hurt(this.damageSources().indirectMagic(this, this), enraged ? 10.0F : 8.0F);
                }
            }
        }
        strikes.addAll(next);
    }

    private void tailSweep(ServerLevel level) {
        for (LivingEntity e : level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(3.5D, 1.0D, 3.5D))) {
            if (e == this) {
                continue;
            }
            e.hurt(this.damageSources().mobAttack(this), enraged ? 11.0F : 9.0F);
            Vec3 push = e.position().subtract(this.position()).normalize().scale(1.6D);
            e.push(push.x, 0.5D, push.z);
            e.hurtMarked = true;
        }
        level.sendParticles(ParticleTypes.SWEEP_ATTACK, getX(), getY() + 1.0D, getZ(), 8, 3.0D, 0.3D, 3.0D, 0.0D);
        this.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 2.0F, 0.5F);
    }

    private void slam(ServerLevel level) {
        for (LivingEntity e : level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(4.5D, 1.5D, 4.5D))) {
            if (e == this) {
                continue;
            }
            e.hurt(this.damageSources().mobAttack(this), enraged ? 14.0F : 11.0F);
            Vec3 push = e.position().subtract(this.position()).normalize().scale(1.3D);
            e.push(push.x, 0.7D, push.z);
            e.hurtMarked = true;
        }
        level.sendParticles(ParticleTypes.EXPLOSION, getX(), getY() + 0.5D, getZ(), 8, 3.0D, 0.4D, 3.0D, 0.0D);
        level.sendParticles(ParticleTypes.ELECTRIC_SPARK, getX(), getY() + 0.5D, getZ(), 60, 4.0D, 0.5D, 4.0D, 0.3D);
        this.playSound(SoundEvents.GENERIC_EXPLODE, 2.0F, 0.6F);
    }

    // --------------------------------------------------------- defesas
    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.is(DamageTypes.LIGHTNING_BOLT) || source.is(DamageTypes.IN_WALL) || source.is(DamageTypes.CRAMMING)
                || source.is(DamageTypeTags.IS_FALL) || source.is(DamageTypes.FLY_INTO_WALL)) {
            return false;
        }
        if (source.getEntity() == this) {
            return false;
        }
        return super.hurt(source, amount);
    }

    @Override
    public boolean causeFallDamage(float distance, float multiplier, DamageSource source) {
        return false;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    protected void doPush(net.minecraft.world.entity.Entity entity) {
    }

    @Override
    public boolean removeWhenFarAway(double distance) {
        return false;
    }

    // ------------------------------------------------------------ dados
    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("Enraged", enraged);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.enraged = tag.getBoolean("Enraged");
        if (this.hasCustomName()) {
            this.bossEvent.setName(this.getDisplayName());
        }
    }

    @Override
    public void setCustomName(@Nullable Component name) {
        super.setCustomName(name);
        this.bossEvent.setName(this.getDisplayName());
    }

    @Override
    public void startSeenByPlayer(ServerPlayer player) {
        super.startSeenByPlayer(player);
        this.bossEvent.addPlayer(player);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer player) {
        super.stopSeenByPlayer(player);
        this.bossEvent.removePlayer(player);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENDER_DRAGON_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENDER_DRAGON_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENDER_DRAGON_DEATH;
    }

    @Override
    protected float getSoundVolume() {
        return 2.5F;
    }

    @Override
    public int getAmbientSoundInterval() {
        return 240;
    }
}
