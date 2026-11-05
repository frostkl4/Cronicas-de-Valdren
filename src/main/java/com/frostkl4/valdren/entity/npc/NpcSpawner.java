package com.frostkl4.valdren.entity.npc;

import com.frostkl4.valdren.Valdren;
import com.frostkl4.valdren.registry.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Optional;

/**
 * Faz os viajantes aparecerem nas vilas no horário deles. A cada 10 segundos, se um jogador
 * estiver numa vila, cada NPC que está "de serviço" e ainda não está lá chega perto do sino.
 * Quando o horário acaba, o próprio NPC vai embora (ValdrenNpc#customServerAiStep).
 */
@Mod.EventBusSubscriber(modid = Valdren.MODID)
public final class NpcSpawner {
    private NpcSpawner() {
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || !(event.player instanceof ServerPlayer player)) {
            return;
        }
        if (player.tickCount % 200 != 37 || player.isSpectator()) {
            return;
        }
        ServerLevel level = player.serverLevel();
        if (level.dimension() != Level.OVERWORLD) {
            return;
        }
        BlockPos pos = player.blockPosition();
        if (!level.isVillage(pos)) {
            return;
        }
        Optional<BlockPos> bell = level.getPoiManager()
                .findClosest(h -> h.is(PoiTypes.MEETING), pos, 48, PoiManager.Occupancy.ANY);
        BlockPos center = bell.orElse(pos);
        long time = level.getDayTime();
        AABB area = new AABB(center).inflate(96.0D);
        for (NpcType type : NpcType.values()) {
            if (!type.isPresentAt(time)) {
                continue;
            }
            boolean here = !level.getEntitiesOfClass(ValdrenNpc.class, area, e -> e.getNpcType() == type).isEmpty();
            if (!here) {
                spawnNear(level, type, center);
            }
        }
    }

    private static void spawnNear(ServerLevel level, NpcType type, BlockPos center) {
        RandomSource r = level.getRandom();
        for (int attempt = 0; attempt < 16; attempt++) {
            int dx = r.nextInt(19) - 9;
            int dz = r.nextInt(19) - 9;
            if (Math.abs(dx) < 3 && Math.abs(dz) < 3) {
                continue;
            }
            BlockPos top = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, center.offset(dx, 0, dz));
            if (Math.abs(top.getY() - center.getY()) > 6) {
                continue;
            }
            BlockState below = level.getBlockState(top.below());
            if (!below.isFaceSturdy(level, top.below(), Direction.UP) || !level.getFluidState(top).isEmpty()) {
                continue;
            }
            ValdrenNpc npc = ModEntities.NPC.get().create(level);
            if (npc == null) {
                return;
            }
            npc.moveTo(top.getX() + 0.5D, top.getY(), top.getZ() + 0.5D, r.nextFloat() * 360.0F, 0.0F);
            if (!level.noCollision(npc)) {
                continue;
            }
            npc.setup(type, center, false);
            level.addFreshEntity(npc);
            npc.arrivalEffect();
            return;
        }
    }
}
