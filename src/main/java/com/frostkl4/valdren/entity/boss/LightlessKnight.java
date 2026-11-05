package com.frostkl4.valdren.entity.boss;

import com.frostkl4.valdren.entity.mob.CrimsonRaider;
import com.frostkl4.valdren.registry.ModEntities;
import com.frostkl4.valdren.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

/**
 * Cavaleiro Sem Luz: chefe humanoide com a Grande Espada do Sentinela.
 * Habilidades: Investida Sombria (avanço), Golpe do Eclipse (onda em área com escuridão)
 * e, abaixo de 50% de vida, Fúria (mais rápido e chama dois Saqueadores).
 * Com Epic Fight ele usa os golpes animados de espadão.
 */
public class LightlessKnight extends Monster {
    private final ServerBossEvent bossEvent = new ServerBossEvent(Component.translatable("entity.valdren.lightless_knight"),
            BossEvent.BossBarColor.PURPLE, BossEvent.BossBarOverlay.NOTCHED_10);
    private int dashCooldown = 100;
    private int dashTicks;
    private int slamCooldown = 160;
    private int slamWindup = -1;
    private boolean enraged;

    public LightlessKnight(EntityType<? extends LightlessKnight> type, Level level) {
        super(type, level);
        this.xpReward = 300;
        this.setPersistenceRequired();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 320.0D)
                .add(Attributes.ATTACK_DAMAGE, 8.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.ARMOR, 16.0D)
                .add(Attributes.ARMOR_TOUGHNESS, 6.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.85D)
                .add(Attributes.FOLLOW_RANGE, 48.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.15D, true));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 16.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, false));
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(ModItems.SENTINEL_GREATSWORD.get()));
        this.setDropChance(EquipmentSlot.MAINHAND, 0.0F);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason,
                                        @Nullable SpawnGroupData data, @Nullable CompoundTag tag) {
        SpawnGroupData result = super.finalizeSpawn(level, difficulty, reason, data, tag);
        this.populateDefaultEquipmentSlots(level.getRandom(), difficulty);
        return result;
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
        ServerLevel level = (ServerLevel) this.level();
        LivingEntity target = this.getTarget();

        if (!enraged && this.getHealth() < this.getMaxHealth() * 0.5F) {
            enrage(level);
        }
        if (target == null || !target.isAlive()) {
            slamWindup = -1;
            return;
        }

        // Investida Sombria
        if (dashTicks > 0) {
            dashTicks--;
            level.sendParticles(ParticleTypes.LARGE_SMOKE, getX(), getY() + 1.0D, getZ(), 3, 0.3D, 0.5D, 0.3D, 0.0D);
            if (this.getBoundingBox().inflate(0.8D).intersects(target.getBoundingBox())) {
                target.hurt(this.damageSources().mobAttack(this), enraged ? 12.0F : 9.0F);
                target.knockback(1.2D, this.getX() - target.getX(), this.getZ() - target.getZ());
                dashTicks = 0;
            }
        } else if (--dashCooldown <= 0) {
            double d = this.distanceToSqr(target);
            if (d > 16.0D && d < 400.0D && this.hasLineOfSight(target)) {
                Vec3 dir = target.position().subtract(this.position()).normalize();
                this.setDeltaMovement(dir.x * 1.5D, 0.25D, dir.z * 1.5D);
                this.hasImpulse = true;
                this.dashTicks = 14;
                level.playSound(null, blockPosition(), SoundEvents.WITHER_SHOOT, SoundSource.HOSTILE, 0.8F, 0.6F);
                dashCooldown = enraged ? 70 : 130;
            } else {
                dashCooldown = 20;
            }
        }

        // Golpe do Eclipse
        if (slamWindup > 0) {
            slamWindup--;
            this.getNavigation().stop();
            if (slamWindup % 4 == 0) {
                ring(level, 4.5D, ParticleTypes.SOUL_FIRE_FLAME);
            }
            if (slamWindup == 0) {
                slam(level);
                slamWindup = -1;
            }
        } else if (--slamCooldown <= 0 && this.distanceToSqr(target) < 36.0D) {
            slamWindup = 20;
            slamCooldown = enraged ? 150 : 230;
            level.playSound(null, blockPosition(), SoundEvents.RAVAGER_ROAR, SoundSource.HOSTILE, 1.0F, 0.6F);
        }
    }

    private void slam(ServerLevel level) {
        for (Player p : level.getEntitiesOfClass(Player.class, this.getBoundingBox().inflate(5.5D))) {
            p.hurt(this.damageSources().mobAttack(this), enraged ? 12.0F : 9.0F);
            p.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 80, 0), this);
            Vec3 push = p.position().subtract(this.position()).normalize().scale(1.1D);
            p.push(push.x, 0.45D, push.z);
            p.hurtMarked = true;
        }
        level.sendParticles(ParticleTypes.EXPLOSION, getX(), getY() + 0.5D, getZ(), 6, 2.0D, 0.3D, 2.0D, 0.0D);
        level.sendParticles(ParticleTypes.SCULK_SOUL, getX(), getY() + 0.5D, getZ(), 30, 2.5D, 0.3D, 2.5D, 0.05D);
        level.playSound(null, blockPosition(), SoundEvents.GENERIC_EXPLODE, SoundSource.HOSTILE, 1.0F, 0.7F);
    }

    private void ring(ServerLevel level, double radius, net.minecraft.core.particles.SimpleParticleType type) {
        for (int i = 0; i < 24; i++) {
            double a = i * Math.PI * 2.0D / 24.0D;
            level.sendParticles(type, getX() + Math.cos(a) * radius, getY() + 0.1D, getZ() + Math.sin(a) * radius,
                    1, 0.0D, 0.0D, 0.0D, 0.0D);
        }
    }

    private void enrage(ServerLevel level) {
        enraged = true;
        this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20 * 60 * 30, 0));
        this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 20 * 60 * 30, 0));
        level.playSound(null, blockPosition(), SoundEvents.WITHER_AMBIENT, SoundSource.HOSTILE, 1.2F, 0.6F);
        for (ServerPlayer p : level.players()) {
            if (p.distanceToSqr(this) < 48.0D * 48.0D) {
                p.sendSystemMessage(Component.translatable("entity.valdren.lightless_knight.enrage")
                        .withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.ITALIC));
            }
        }
        for (int i = 0; i < 2; i++) {
            CrimsonRaider raider = ModEntities.CRIMSON_RAIDER.get().create(level);
            if (raider != null) {
                double a = this.random.nextDouble() * Math.PI * 2.0D;
                raider.moveTo(getX() + Math.cos(a) * 3.0D, getY(), getZ() + Math.sin(a) * 3.0D, Mth.wrapDegrees((float) Math.toDegrees(a)), 0.0F);
                raider.finalizeSpawn(level, level.getCurrentDifficultyAt(blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
                raider.setTarget(this.getTarget());
                level.addFreshEntity(raider);
                level.sendParticles(ParticleTypes.LARGE_SMOKE, raider.getX(), raider.getY() + 1.0D, raider.getZ(), 20, 0.3D, 0.6D, 0.3D, 0.02D);
            }
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.level().isClientSide && this.random.nextInt(2) == 0) {
            this.level().addParticle(ParticleTypes.SMOKE, getRandomX(0.6D), getRandomY(), getRandomZ(0.6D), 0.0D, 0.02D, 0.0D);
        }
    }

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
    public boolean removeWhenFarAway(double distance) {
        return false;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.WITHER_SKELETON_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.WITHER_SKELETON_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.WITHER_SKELETON_DEATH;
    }

    @Override
    public float getVoicePitch() {
        return 0.6F;
    }
}
