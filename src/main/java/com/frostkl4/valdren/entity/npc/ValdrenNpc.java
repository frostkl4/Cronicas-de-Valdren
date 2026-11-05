package com.frostkl4.valdren.entity.npc;

import com.frostkl4.valdren.dialogue.DialogueManager;
import com.frostkl4.valdren.quest.PlayerData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsRestrictionGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.npc.Npc;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.UUID;

/**
 * NPC de Valdren. O tipo (Bram, Iara, Orvyn, Ravena) define nome, falas, missões, trocas,
 * textura e horário. Não leva dano de jogadores nem de monstros.
 */
public class ValdrenNpc extends PathfinderMob implements Merchant, Npc {
    private static final EntityDataAccessor<String> TYPE =
            SynchedEntityData.defineId(ValdrenNpc.class, EntityDataSerializers.STRING);

    @Nullable
    private Player tradingPlayer;
    @Nullable
    private MerchantOffers offers;
    @Nullable
    private UUID talkingTo;
    private int talkTicks;
    /** Colocado com o item de chamado: fica sempre, sem horário. */
    private boolean fixed;
    @Nullable
    private BlockPos home;

    public ValdrenNpc(EntityType<? extends ValdrenNpc> type, Level level) {
        super(type, level);
        this.setPersistenceRequired();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 40.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.5D)
                .add(Attributes.FOLLOW_RANGE, 24.0D);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(TYPE, NpcType.BRAM.id);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new TalkGoal());
        this.goalSelector.addGoal(2, new MoveTowardsRestrictionGoal(this, 0.55D));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 0.4D));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
    }

    public void setup(NpcType type, BlockPos home, boolean fixed) {
        this.entityData.set(TYPE, type.id);
        this.fixed = fixed;
        this.setHome(home);
        this.setCustomName(Component.translatable(type.key("name")));
    }

    private void setHome(@Nullable BlockPos pos) {
        this.home = pos;
        if (pos != null) {
            this.restrictTo(pos, fixed ? 6 : 16);
        }
    }

    public NpcType getNpcType() {
        return NpcType.byId(this.entityData.get(TYPE));
    }

    public boolean isFixed() {
        return fixed;
    }

    // ---------------------------------------------------------------- conversa
    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (hand != InteractionHand.MAIN_HAND) {
            return InteractionResult.PASS;
        }
        if (!this.level().isClientSide && player instanceof ServerPlayer sp) {
            if (this.tradingPlayer != null && this.tradingPlayer != player) {
                return InteractionResult.CONSUME;
            }
            DialogueManager.open(sp, this);
        }
        return InteractionResult.sidedSuccess(this.level().isClientSide);
    }

    public void startTalking(Player player) {
        this.talkingTo = player.getUUID();
        this.talkTicks = 600;
        this.getNavigation().stop();
    }

    public void stopTalking() {
        this.talkingTo = null;
        this.talkTicks = 0;
    }

    @Nullable
    private Player talkPartner() {
        if (this.tradingPlayer != null) {
            return this.tradingPlayer;
        }
        if (this.talkingTo == null || this.talkTicks <= 0) {
            return null;
        }
        return this.level().getPlayerByUUID(this.talkingTo);
    }

    public void openTradeFor(ServerPlayer player) {
        this.offers = NpcTrades.build(getNpcType(), PlayerData.affinity(player, getNpcType()));
        this.setTradingPlayer(player);
        this.openTradingScreen(player, this.getDisplayName(), 1);
    }

    // ---------------------------------------------------------- horário/vinda
    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        if (this.talkTicks > 0) {
            this.talkTicks--;
        }
        if (this.tickCount % 40 == 0 && !fixed && this.tradingPlayer == null && this.talkTicks <= 0
                && !getNpcType().isPresentAt(this.level().getDayTime())) {
            departureEffect();
            this.discard();
        }
    }

    public void arrivalEffect() {
        if (this.level() instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.CLOUD, getX(), getY() + 1.0D, getZ(), 14, 0.3D, 0.6D, 0.3D, 0.01D);
            server.playSound(null, blockPosition(), SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.NEUTRAL, 0.8F, 1.2F);
        }
    }

    private void departureEffect() {
        if (this.level() instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.POOF, getX(), getY() + 1.0D, getZ(), 16, 0.3D, 0.6D, 0.3D, 0.02D);
        }
    }

    // ------------------------------------------------------------ invulnerável
    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)
                || (source.getEntity() instanceof Player p && p.getAbilities().instabuild && p.isShiftKeyDown())) {
            return super.hurt(source, amount);
        }
        return false;
    }

    @Override
    public boolean removeWhenFarAway(double distance) {
        return false;
    }

    @Override
    public boolean canBeLeashed(Player player) {
        return false;
    }

    @Override
    public boolean isPushable() {
        return talkPartner() == null && super.isPushable();
    }

    // ----------------------------------------------------------------- dados
    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putString("NpcType", getNpcType().id);
        tag.putBoolean("Fixed", fixed);
        if (home != null) {
            tag.put("Home", NbtUtils.writeBlockPos(home));
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.entityData.set(TYPE, NpcType.byId(tag.getString("NpcType")).id);
        this.fixed = tag.getBoolean("Fixed");
        if (tag.contains("Home", Tag.TAG_COMPOUND)) {
            setHome(NbtUtils.readBlockPos(tag.getCompound("Home")));
        }
    }

    // --------------------------------------------------------------- Merchant
    @Override
    public void setTradingPlayer(@Nullable Player player) {
        this.tradingPlayer = player;
    }

    @Nullable
    @Override
    public Player getTradingPlayer() {
        return this.tradingPlayer;
    }

    @Override
    public MerchantOffers getOffers() {
        if (this.offers == null) {
            this.offers = new MerchantOffers();
        }
        return this.offers;
    }

    @Override
    public void overrideOffers(MerchantOffers offers) {
        this.offers = offers;
    }

    @Override
    public void notifyTrade(MerchantOffer offer) {
        offer.increaseUses();
        this.playSound(SoundEvents.VILLAGER_YES, 0.6F, 1.1F);
        if (this.tradingPlayer instanceof ServerPlayer sp) {
            DialogueManager.onTrade(sp, this);
        }
    }

    @Override
    public void notifyTradeUpdated(ItemStack stack) {
    }

    @Override
    public int getVillagerXp() {
        return 0;
    }

    @Override
    public void overrideXp(int xp) {
    }

    @Override
    public boolean showProgressBar() {
        return false;
    }

    @Override
    public SoundEvent getNotifyTradeSound() {
        return SoundEvents.VILLAGER_YES;
    }

    @Override
    public boolean isClientSide() {
        return this.level().isClientSide;
    }

    /** Para e olha para quem está conversando ou negociando. */
    private class TalkGoal extends Goal {
        TalkGoal() {
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
        }

        @Override
        public boolean canUse() {
            return talkPartner() != null;
        }

        @Override
        public void start() {
            getNavigation().stop();
        }

        @Override
        public void tick() {
            Player p = talkPartner();
            if (p != null) {
                getNavigation().stop();
                getLookControl().setLookAt(p, 30.0F, 30.0F);
            }
        }
    }
}
