package com.frostkl4.valdren.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;

import java.util.function.Supplier;

/** Item que invoca um chefe (Chifre da Tempestade, Selo Partido). */
public class BossSummonItem extends LoreItem {
    private final Supplier<? extends EntityType<? extends Mob>> type;
    private final boolean needsSky;

    public BossSummonItem(Supplier<? extends EntityType<? extends Mob>> type, boolean needsSky, Properties props) {
        super(props);
        this.type = type;
        this.needsSky = needsSky;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!(level instanceof ServerLevel server)) {
            return InteractionResultHolder.success(stack);
        }
        EntityType<? extends Mob> entityType = type.get();
        Vec3 look = player.getLookAngle();
        Vec3 flat = new Vec3(look.x, 0.0D, look.z);
        flat = flat.lengthSqr() < 1.0E-4D ? new Vec3(0, 0, 1) : flat.normalize();
        BlockPos base = BlockPos.containing(player.position().add(flat.scale(needsSky ? 12.0D : 5.0D)));
        BlockPos pos = server.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, base);
        if (needsSky && (!server.canSeeSky(pos) || Math.abs(pos.getY() - player.getY()) > 12)) {
            player.displayClientMessage(Component.translatable("valdren.summon.need_sky")
                    .withStyle(ChatFormatting.RED), true);
            return InteractionResultHolder.fail(stack);
        }
        if (!needsSky) {
            pos = player.blockPosition().offset((int) Math.round(flat.x * 4), 0, (int) Math.round(flat.z * 4));
        }
        boolean already = !server.getEntitiesOfClass(Mob.class, player.getBoundingBox().inflate(80.0D),
                e -> e.getType() == entityType && e.isAlive()).isEmpty();
        if (already) {
            player.displayClientMessage(Component.translatable("valdren.summon.already")
                    .withStyle(ChatFormatting.RED), true);
            return InteractionResultHolder.fail(stack);
        }
        Mob mob = entityType.create(server);
        if (mob == null) {
            return InteractionResultHolder.fail(stack);
        }
        mob.moveTo(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, player.getYRot() + 180.0F, 0.0F);
        mob.finalizeSpawn(server, server.getCurrentDifficultyAt(pos), MobSpawnType.MOB_SUMMONED, null, null);
        mob.setTarget(player);
        server.addFreshEntity(mob);

        LightningBolt bolt = EntityType.LIGHTNING_BOLT.create(server);
        if (bolt != null) {
            bolt.moveTo(Vec3.atBottomCenterOf(pos));
            bolt.setVisualOnly(true);
            server.addFreshEntity(bolt);
        }
        server.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, mob.getX(), mob.getY() + 1.0D, mob.getZ(),
                60, 1.2D, 1.0D, 1.2D, 0.05D);
        server.playSound(null, pos, SoundEvents.WITHER_SPAWN, SoundSource.HOSTILE, 0.8F, 0.7F);
        Component msg = Component.translatable(this.getDescriptionId() + ".summon").withStyle(ChatFormatting.DARK_PURPLE);
        for (ServerPlayer p : server.players()) {
            if (p.distanceToSqr(mob) < 120.0D * 120.0D) {
                p.sendSystemMessage(msg);
            }
        }
        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }
        player.getCooldowns().addCooldown(this, 100);
        return InteractionResultHolder.consume(stack);
    }
}
