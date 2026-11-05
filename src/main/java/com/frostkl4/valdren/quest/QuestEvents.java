package com.frostkl4.valdren.quest;

import com.frostkl4.valdren.Valdren;
import com.frostkl4.valdren.dialogue.DialogueManager;
import com.frostkl4.valdren.entity.boss.LightlessKnight;
import com.frostkl4.valdren.entity.boss.StormDragon;
import com.frostkl4.valdren.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.LinkedHashSet;
import java.util.Set;

@Mod.EventBusSubscriber(modid = Valdren.MODID)
public final class QuestEvents {
    private QuestEvents() {
    }

    /** Conta abates para as missões. Chefes contam para todo mundo que estava perto. */
    @SubscribeEvent
    public static void onDeath(LivingDeathEvent event) {
        LivingEntity dead = event.getEntity();
        if (!(dead.level() instanceof ServerLevel level)) {
            return;
        }
        Set<ServerPlayer> credited = new LinkedHashSet<>();
        Entity killer = event.getSource().getEntity();
        if (killer instanceof ServerPlayer sp) {
            credited.add(sp);
        }
        if (dead instanceof StormDragon || dead instanceof LightlessKnight) {
            for (ServerPlayer p : level.players()) {
                if (p.distanceToSqr(dead) < 64.0D * 64.0D) {
                    credited.add(p);
                }
            }
        }
        for (ServerPlayer p : credited) {
            DialogueManager.onKill(p, dead.getType());
        }
    }

    /** Na primeira vez que o jogador entra, ganha o guia. */
    @SubscribeEvent
    public static void onLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player && !PlayerData.gotGuide(player)) {
            PlayerData.setGotGuide(player);
            ItemStack book = new ItemStack(ModItems.GUIDE_BOOK.get());
            if (!player.getInventory().add(book)) {
                player.drop(book, false);
            }
            player.sendSystemMessage(Component.translatable("valdren.welcome").withStyle(ChatFormatting.GOLD));
        }
    }
}
