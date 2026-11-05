package com.frostkl4.valdren.dialogue;

import com.frostkl4.valdren.entity.npc.NpcType;
import com.frostkl4.valdren.entity.npc.ValdrenNpc;
import com.frostkl4.valdren.network.DialoguePacket;
import com.frostkl4.valdren.network.ModNetwork;
import com.frostkl4.valdren.quest.Affinity;
import com.frostkl4.valdren.quest.PlayerData;
import com.frostkl4.valdren.quest.Quest;
import com.frostkl4.valdren.quest.Quests;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Lógica das conversas (lado do servidor). O cliente só mostra o que chega em DialoguePacket
 * e devolve o número da opção escolhida em DialogueActionPacket.
 */
public final class DialogueManager {
    public static final int MAIN = 0;
    public static final int QUESTS = 1;
    public static final int STORY = 2;
    public static final int AFFINITY = 3;
    public static final int TRADE = 4;
    public static final int BYE = 5;
    public static final int ACCEPT = 6;
    public static final int DECLINE = 7;
    public static final int TURN_IN = 8;
    public static final int ABANDON = 9;

    private DialogueManager() {
    }

    // ------------------------------------------------------------- entrada
    public static void open(ServerPlayer player, ValdrenNpc npc) {
        NpcType t = npc.getNpcType();
        npc.startTalking(player);
        long day = player.level().getDayTime() / 24000L;
        if (PlayerData.lastTalkDay(player, t) != day) {
            PlayerData.setLastTalkDay(player, t, day);
            PlayerData.addAffinity(player, t, 2);
        }
        send(player, npc, greeting(player, t), Component.empty(), mainOptions(player, t));
    }

    public static void handle(ServerPlayer player, int entityId, int action) {
        Entity e = player.level().getEntity(entityId);
        if (!(e instanceof ValdrenNpc npc) || !npc.isAlive() || player.distanceToSqr(npc) > 100.0D) {
            close(player);
            return;
        }
        NpcType t = npc.getNpcType();
        if (action == BYE) {
            npc.stopTalking();
            return;
        }
        npc.startTalking(player);
        switch (action) {
            case QUESTS -> questMenu(player, npc);
            case STORY -> story(player, npc);
            case AFFINITY -> affinity(player, npc);
            case TRADE -> {
                npc.stopTalking();
                npc.openTradeFor(player);
            }
            case ACCEPT -> accept(player, npc);
            case DECLINE -> send(player, npc, tr(t.key("decline")), Component.empty(), mainOptions(player, t));
            case TURN_IN -> turnIn(player, npc);
            case ABANDON -> {
                Quest q = PlayerData.activeQuest(player, t);
                if (q != null) {
                    PlayerData.clearQuest(player, t);
                    send(player, npc, tr(t.key("abandon")), Component.empty(), mainOptions(player, t));
                } else {
                    send(player, npc, tr(t.key("more")), Component.empty(), mainOptions(player, t));
                }
            }
            default -> send(player, npc, tr(t.key("more")), Component.empty(), mainOptions(player, t));
        }
    }

    // ------------------------------------------------------------- menus
    private static Component greeting(ServerPlayer player, NpcType t) {
        Quest active = PlayerData.activeQuest(player, t);
        if (active != null && progressOf(player, active) >= active.amount) {
            return tr(t.key("greet.ready"));
        }
        int aff = PlayerData.affinity(player, t);
        if (Affinity.tier(aff) >= 3) {
            return Component.translatable(t.key("greet.friend"), player.getDisplayName());
        }
        return tr(t.key("greet." + player.getRandom().nextInt(3)));
    }

    private static List<DialogueOption> mainOptions(ServerPlayer player, NpcType t) {
        return List.of(
                new DialogueOption(QUESTS, tr(t.key("opt.quests")), hasNews(player, t)),
                new DialogueOption(STORY, tr(t.key("opt.story")), false),
                new DialogueOption(AFFINITY, tr(t.key("opt.affinity")), false),
                new DialogueOption(TRADE, tr(t.key("opt.trade")), true),
                new DialogueOption(BYE, tr(t.key("opt.bye")), false));
    }

    private static boolean hasNews(ServerPlayer player, NpcType t) {
        Quest active = PlayerData.activeQuest(player, t);
        if (active != null) {
            return progressOf(player, active) >= active.amount;
        }
        return offerFor(player, t) != null;
    }

    private static void questMenu(ServerPlayer player, ValdrenNpc npc) {
        NpcType t = npc.getNpcType();
        Quest active = PlayerData.activeQuest(player, t);
        if (active != null) {
            int prog = progressOf(player, active);
            if (prog >= active.amount) {
                send(player, npc, tr(active.key("ready")), active.rewardText(), List.of(
                        new DialogueOption(TURN_IN, tr("valdren.dialogue.turn_in"), true),
                        new DialogueOption(MAIN, tr("valdren.dialogue.back"), false)));
            } else {
                Component extra = Component.translatable("valdren.dialogue.progress", active.title(),
                        Math.min(prog, active.amount), active.amount);
                send(player, npc, tr(active.key("progress")), extra, List.of(
                        new DialogueOption(MAIN, tr("valdren.dialogue.back"), false),
                        new DialogueOption(ABANDON, tr("valdren.dialogue.abandon"), false)));
            }
            return;
        }
        Quest offer = offerFor(player, t);
        if (offer == null) {
            String line = anyLocked(player, t) ? "questlocked" : "noquest";
            send(player, npc, tr(t.key(line)), Component.empty(), List.of(
                    new DialogueOption(MAIN, tr("valdren.dialogue.back"), false)));
            return;
        }
        Component extra = Component.translatable("valdren.dialogue.objective", offer.objective())
                .append("  ").append(offer.rewardText());
        send(player, npc, tr(offer.key("offer")), extra, List.of(
                new DialogueOption(ACCEPT, tr("valdren.dialogue.accept"), true),
                new DialogueOption(DECLINE, tr("valdren.dialogue.decline"), false)));
    }

    private static void accept(ServerPlayer player, ValdrenNpc npc) {
        NpcType t = npc.getNpcType();
        if (PlayerData.activeQuest(player, t) != null) {
            questMenu(player, npc);
            return;
        }
        Quest offer = offerFor(player, t);
        if (offer == null) {
            questMenu(player, npc);
            return;
        }
        PlayerData.startQuest(player, offer);
        ItemStack start = offer.makeStartItem();
        if (start != null) {
            give(player, start);
        }
        player.level().playSound(null, npc.blockPosition(), SoundEvents.BOOK_PAGE_TURN, SoundSource.NEUTRAL, 1.0F, 1.0F);
        player.sendSystemMessage(Component.translatable("valdren.quest.started", offer.title())
                .withStyle(ChatFormatting.YELLOW));
        send(player, npc, tr(offer.key("accept")),
                Component.translatable("valdren.dialogue.objective", offer.objective()), mainOptions(player, t));
    }

    private static void turnIn(ServerPlayer player, ValdrenNpc npc) {
        NpcType t = npc.getNpcType();
        Quest q = PlayerData.activeQuest(player, t);
        if (q == null) {
            send(player, npc, tr(t.key("more")), Component.empty(), mainOptions(player, t));
            return;
        }
        if (progressOf(player, q) < q.amount) {
            send(player, npc, tr(t.key("notyet")), Component.translatable("valdren.dialogue.progress",
                    q.title(), progressOf(player, q), q.amount), mainOptions(player, t));
            return;
        }
        if (q.kind == Quest.Kind.COLLECT) {
            q.removeItems(player);
        }
        for (ItemStack s : q.makeRewards()) {
            give(player, s);
        }
        if (q.xpReward() > 0) {
            player.giveExperiencePoints(q.xpReward());
        }
        PlayerData.clearQuest(player, t);
        PlayerData.markDone(player, q, player.level().getGameTime());
        PlayerData.addAffinity(player, t, q.affinityReward());
        player.level().playSound(null, npc.blockPosition(), SoundEvents.PLAYER_LEVELUP, SoundSource.NEUTRAL, 0.7F, 1.4F);
        if (player.level() instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.HAPPY_VILLAGER, npc.getX(), npc.getY() + 1.6D, npc.getZ(),
                    12, 0.4D, 0.4D, 0.4D, 0.0D);
        }
        player.sendSystemMessage(Component.translatable("valdren.quest.completed", q.title())
                .withStyle(ChatFormatting.GREEN));
        send(player, npc, tr(q.key("done")), q.rewardText(), mainOptions(player, t));
    }

    private static void story(ServerPlayer player, ValdrenNpc npc) {
        NpcType t = npc.getNpcType();
        int count = t.storyCount();
        int idx = Math.floorMod(PlayerData.nextStory(player, t), count);
        int aff = PlayerData.affinity(player, t);
        if (aff < t.storyAffinity(idx)) {
            PlayerData.setNextStory(player, t, 0);
            send(player, npc, tr(t.key("story.locked")), Component.translatable("valdren.dialogue.story_locked",
                    Affinity.tierName(t.storyAffinity(idx))), mainOptions(player, t));
            return;
        }
        PlayerData.setNextStory(player, t, (idx + 1) % count);
        if (PlayerData.markHeard(player, t, idx)) {
            PlayerData.addAffinity(player, t, 2);
        }
        send(player, npc, tr(t.key("story." + idx)), Component.empty(), mainOptions(player, t));
    }

    private static void affinity(ServerPlayer player, ValdrenNpc npc) {
        NpcType t = npc.getNpcType();
        int aff = PlayerData.affinity(player, t);
        int next = Affinity.nextThreshold(aff);
        Component extra = next < 0
                ? Component.translatable("valdren.dialogue.affinity_max", Affinity.tierName(aff))
                : Component.translatable("valdren.dialogue.affinity", aff, next, Affinity.nextTierName(aff));
        send(player, npc, tr(t.key("affinity." + Affinity.tierId(aff))), extra, mainOptions(player, t));
    }

    // ------------------------------------------------------------- missões
    @Nullable
    private static Quest offerFor(ServerPlayer player, NpcType t) {
        long now = player.level().getGameTime();
        int aff = PlayerData.affinity(player, t);
        for (Quest q : Quests.forNpc(t)) {
            if (!q.repeatable() && PlayerData.timesDone(player, q) > 0) {
                continue;
            }
            if (q.repeatable() && PlayerData.onCooldown(player, q, now)) {
                continue;
            }
            if (aff < q.minAffinity()) {
                continue;
            }
            return q;
        }
        return null;
    }

    private static boolean anyLocked(ServerPlayer player, NpcType t) {
        int aff = PlayerData.affinity(player, t);
        for (Quest q : Quests.forNpc(t)) {
            if (!q.repeatable() && PlayerData.timesDone(player, q) == 0 && aff < q.minAffinity()) {
                return true;
            }
        }
        return false;
    }

    private static int progressOf(ServerPlayer player, Quest q) {
        if (q.kind == Quest.Kind.KILL) {
            return PlayerData.progress(player, q.npc);
        }
        return Math.min(q.countItems(player), q.amount);
    }

    public static void onKill(ServerPlayer player, EntityType<?> type) {
        for (NpcType t : NpcType.values()) {
            Quest q = PlayerData.activeQuest(player, t);
            if (q == null || !q.matchesEntity(type)) {
                continue;
            }
            int prog = PlayerData.progress(player, t);
            if (prog >= q.amount) {
                continue;
            }
            prog++;
            PlayerData.setProgress(player, t, prog);
            if (prog >= q.amount) {
                player.sendSystemMessage(Component.translatable("valdren.quest.ready", q.title(),
                        Component.translatable(t.key("name"))).withStyle(ChatFormatting.GREEN));
                player.level().playSound(null, player.blockPosition(), SoundEvents.NOTE_BLOCK_BELL.value(),
                        SoundSource.PLAYERS, 0.8F, 1.2F);
            } else {
                player.displayClientMessage(Component.translatable("valdren.quest.progress_bar", q.title(),
                        prog, q.amount).withStyle(ChatFormatting.YELLOW), true);
            }
        }
    }

    public static void onTrade(ServerPlayer player, ValdrenNpc npc) {
        if (player.getRandom().nextInt(4) == 0) {
            PlayerData.addAffinity(player, npc.getNpcType(), 1);
        }
    }

    // ------------------------------------------------------------- envio
    private static void send(ServerPlayer player, ValdrenNpc npc, Component text, Component extra,
                             List<DialogueOption> options) {
        NpcType t = npc.getNpcType();
        ModNetwork.sendTo(player, new DialoguePacket(npc.getId(), t.id, text, extra,
                PlayerData.affinity(player, t), options));
    }

    public static void close(ServerPlayer player) {
        ModNetwork.sendTo(player, new DialoguePacket(-1, "", Component.empty(), Component.empty(), 0, List.of()));
    }

    private static void give(ServerPlayer player, ItemStack stack) {
        if (!player.getInventory().add(stack)) {
            player.drop(stack, false);
        }
    }

    private static Component tr(String key) {
        return Component.translatable(key);
    }
}
