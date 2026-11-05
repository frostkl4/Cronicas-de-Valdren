package com.frostkl4.valdren.registry;

import com.frostkl4.valdren.Valdren;
import com.frostkl4.valdren.entity.boss.LightlessKnight;
import com.frostkl4.valdren.entity.boss.StormDragon;
import com.frostkl4.valdren.entity.mob.CrimsonRaider;
import com.frostkl4.valdren.entity.mob.FrostWraith;
import com.frostkl4.valdren.entity.mob.ShadowWolf;
import com.frostkl4.valdren.entity.npc.ValdrenNpc;
import com.frostkl4.valdren.entity.projectile.FrostShard;
import com.frostkl4.valdren.entity.projectile.StormOrb;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Valdren.MODID);

    public static final RegistryObject<EntityType<ValdrenNpc>> NPC = ENTITIES.register("npc",
            () -> EntityType.Builder.<ValdrenNpc>of(ValdrenNpc::new, MobCategory.MISC)
                    .sized(0.6F, 1.95F).clientTrackingRange(10).build("npc"));

    public static final RegistryObject<EntityType<ShadowWolf>> SHADOW_WOLF = ENTITIES.register("shadow_wolf",
            () -> EntityType.Builder.<ShadowWolf>of(ShadowWolf::new, MobCategory.MONSTER)
                    .sized(0.9F, 1.1F).clientTrackingRange(10).build("shadow_wolf"));

    public static final RegistryObject<EntityType<FrostWraith>> FROST_WRAITH = ENTITIES.register("frost_wraith",
            () -> EntityType.Builder.<FrostWraith>of(FrostWraith::new, MobCategory.MONSTER)
                    .sized(0.8F, 2.3F).clientTrackingRange(10).build("frost_wraith"));

    public static final RegistryObject<EntityType<CrimsonRaider>> CRIMSON_RAIDER = ENTITIES.register("crimson_raider",
            () -> EntityType.Builder.<CrimsonRaider>of(CrimsonRaider::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.95F).clientTrackingRange(10).build("crimson_raider"));

    public static final RegistryObject<EntityType<LightlessKnight>> LIGHTLESS_KNIGHT = ENTITIES.register("lightless_knight",
            () -> EntityType.Builder.<LightlessKnight>of(LightlessKnight::new, MobCategory.MONSTER)
                    .sized(0.7F, 2.0F).fireImmune().clientTrackingRange(12).build("lightless_knight"));

    public static final RegistryObject<EntityType<StormDragon>> STORM_DRAGON = ENTITIES.register("storm_dragon",
            () -> EntityType.Builder.<StormDragon>of(StormDragon::new, MobCategory.MONSTER)
                    .sized(4.5F, 3.2F).fireImmune().clientTrackingRange(16).build("storm_dragon"));

    public static final RegistryObject<EntityType<FrostShard>> FROST_SHARD = ENTITIES.register("frost_shard",
            () -> EntityType.Builder.<FrostShard>of(FrostShard::new, MobCategory.MISC)
                    .sized(0.3F, 0.3F).clientTrackingRange(6).updateInterval(10).build("frost_shard"));

    public static final RegistryObject<EntityType<StormOrb>> STORM_ORB = ENTITIES.register("storm_orb",
            () -> EntityType.Builder.<StormOrb>of(StormOrb::new, MobCategory.MISC)
                    .sized(1.0F, 1.0F).clientTrackingRange(8).updateInterval(10).build("storm_orb"));

    private ModEntities() {
    }

    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(NPC.get(), ValdrenNpc.createAttributes().build());
        event.put(SHADOW_WOLF.get(), ShadowWolf.createAttributes().build());
        event.put(FROST_WRAITH.get(), FrostWraith.createAttributes().build());
        event.put(CRIMSON_RAIDER.get(), CrimsonRaider.createAttributes().build());
        event.put(LIGHTLESS_KNIGHT.get(), LightlessKnight.createAttributes().build());
        event.put(STORM_DRAGON.get(), StormDragon.createAttributes().build());
    }

    public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {
        event.register(SHADOW_WOLF.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(FROST_WRAITH.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(CRIMSON_RAIDER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
    }
}
