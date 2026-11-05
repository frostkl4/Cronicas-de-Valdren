package com.frostkl4.valdren.network;

import com.frostkl4.valdren.dialogue.DialogueManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/** Cliente -> servidor: o jogador escolheu uma resposta. */
public record DialogueActionPacket(int entityId, int action) {

    public static void encode(DialogueActionPacket p, FriendlyByteBuf buf) {
        buf.writeVarInt(p.entityId);
        buf.writeVarInt(p.action);
    }

    public static DialogueActionPacket decode(FriendlyByteBuf buf) {
        return new DialogueActionPacket(buf.readVarInt(), buf.readVarInt());
    }

    public static void handle(DialogueActionPacket p, Supplier<NetworkEvent.Context> ctx) {
        ServerPlayer sender = ctx.get().getSender();
        if (sender != null) {
            DialogueManager.handle(sender, p.entityId, p.action);
        }
        ctx.get().setPacketHandled(true);
    }
}
