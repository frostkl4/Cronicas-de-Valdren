package com.frostkl4.valdren.quest;

import com.frostkl4.valdren.entity.npc.NpcType;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;

/**
 * Progresso do jogador (afinidade, missões, histórias). Fica no NBT persistente do jogador,
 * que o Forge copia ao renascer, então nada se perde ao morrer.
 */
public final class PlayerData {
    private static final String KEY = "valdren";

    private PlayerData() {
    }

    public static CompoundTag root(Player player) {
        CompoundTag data = player.getPersistentData();
        CompoundTag persisted;
        if (data.contains(Player.PERSISTED_NBT_TAG, Tag.TAG_COMPOUND)) {
            persisted = data.getCompound(Player.PERSISTED_NBT_TAG);
        } else {
            persisted = new CompoundTag();
            data.put(Player.PERSISTED_NBT_TAG, persisted);
        }
        if (!persisted.contains(KEY, Tag.TAG_COMPOUND)) {
            persisted.put(KEY, new CompoundTag());
        }
        return persisted.getCompound(KEY);
    }

    private static CompoundTag sub(Player player, String name) {
        CompoundTag r = root(player);
        if (!r.contains(name, Tag.TAG_COMPOUND)) {
            r.put(name, new CompoundTag());
        }
        return r.getCompound(name);
    }

    // ---------------------------------------------------------------- afinidade
    public static int affinity(Player player, NpcType npc) {
        return sub(player, "aff").getInt(npc.id);
    }

    public static void addAffinity(ServerPlayer player, NpcType npc, int amount) {
        if (amount == 0) {
            return;
        }
        int before = affinity(player, npc);
        int after = Math.max(0, Math.min(Affinity.MAX, before + amount));
        sub(player, "aff").putInt(npc.id, after);
        if (Affinity.tier(after) > Affinity.tier(before)) {
            player.sendSystemMessage(Component.translatable("valdren.affinity.up",
                    Component.translatable(npc.key("name")), Affinity.tierName(after)).withStyle(ChatFormatting.GOLD));
            player.level().playSound(null, player.blockPosition(), SoundEvents.PLAYER_LEVELUP, SoundSource.PLAYERS, 0.6F, 1.3F);
        }
    }

    // ------------------------------------------------------------------ missões
    @Nullable
    public static Quest activeQuest(Player player, NpcType npc) {
        CompoundTag active = sub(player, "active");
        if (!active.contains(npc.id, Tag.TAG_COMPOUND)) {
            return null;
        }
        return Quests.get(active.getCompound(npc.id).getString("id"));
    }

    public static int progress(Player player, NpcType npc) {
        CompoundTag active = sub(player, "active");
        return active.contains(npc.id, Tag.TAG_COMPOUND) ? active.getCompound(npc.id).getInt("p") : 0;
    }

    public static void setProgress(Player player, NpcType npc, int value) {
        CompoundTag active = sub(player, "active");
        if (active.contains(npc.id, Tag.TAG_COMPOUND)) {
            active.getCompound(npc.id).putInt("p", value);
        }
    }

    public static void startQuest(Player player, Quest quest) {
        CompoundTag q = new CompoundTag();
        q.putString("id", quest.id);
        q.putInt("p", 0);
        sub(player, "active").put(quest.npc.id, q);
    }

    public static void clearQuest(Player player, NpcType npc) {
        sub(player, "active").remove(npc.id);
    }

    public static int timesDone(Player player, Quest quest) {
        return sub(player, "done").getInt(quest.id);
    }

    public static void markDone(Player player, Quest quest, long gameTime) {
        CompoundTag done = sub(player, "done");
        done.putInt(quest.id, done.getInt(quest.id) + 1);
        if (quest.repeatable()) {
            sub(player, "cool").putLong(quest.id, gameTime + quest.cooldown);
        }
    }

    public static boolean onCooldown(Player player, Quest quest, long gameTime) {
        return sub(player, "cool").getLong(quest.id) > gameTime;
    }

    // ---------------------------------------------------------------- histórias
    public static int nextStory(Player player, NpcType npc) {
        return sub(player, "story").getInt(npc.id);
    }

    public static void setNextStory(Player player, NpcType npc, int index) {
        sub(player, "story").putInt(npc.id, index);
    }

    /** Marca a história como ouvida; devolve true se for a primeira vez. */
    public static boolean markHeard(Player player, NpcType npc, int index) {
        CompoundTag heard = sub(player, "heard");
        int mask = heard.getInt(npc.id);
        int bit = 1 << index;
        if ((mask & bit) != 0) {
            return false;
        }
        heard.putInt(npc.id, mask | bit);
        return true;
    }

    // ------------------------------------------------------------------ outros
    public static long lastTalkDay(Player player, NpcType npc) {
        CompoundTag talk = sub(player, "talk");
        return talk.contains(npc.id) ? talk.getLong(npc.id) : -1L;
    }

    public static void setLastTalkDay(Player player, NpcType npc, long day) {
        sub(player, "talk").putLong(npc.id, day);
    }

    public static boolean gotGuide(Player player) {
        return root(player).getBoolean("guide");
    }

    public static void setGotGuide(Player player) {
        root(player).putBoolean("guide", true);
    }
}
