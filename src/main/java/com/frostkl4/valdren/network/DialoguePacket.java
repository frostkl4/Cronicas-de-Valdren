package com.frostkl4.valdren.network;

import com.frostkl4.valdren.client.ClientHooks;
import com.frostkl4.valdren.dialogue.DialogueOption;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/** Servidor -> cliente: o que o NPC fala e quais respostas aparecem. entityId -1 fecha a tela. */
public record DialoguePacket(int entityId, String npc, Component text, Component extra, int affinity,
                             List<DialogueOption> options) {

    public static void encode(DialoguePacket p, FriendlyByteBuf buf) {
        buf.writeVarInt(p.entityId);
        buf.writeUtf(p.npc);
        buf.writeComponent(p.text);
        buf.writeComponent(p.extra);
        buf.writeVarInt(p.affinity);
        buf.writeVarInt(p.options.size());
        for (DialogueOption o : p.options) {
            buf.writeVarInt(o.action());
            buf.writeComponent(o.label());
            buf.writeBoolean(o.highlight());
        }
    }

    public static DialoguePacket decode(FriendlyByteBuf buf) {
        int id = buf.readVarInt();
        String npc = buf.readUtf();
        Component text = buf.readComponent();
        Component extra = buf.readComponent();
        int affinity = buf.readVarInt();
        int n = buf.readVarInt();
        List<DialogueOption> options = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            options.add(new DialogueOption(buf.readVarInt(), buf.readComponent(), buf.readBoolean()));
        }
        return new DialoguePacket(id, npc, text, extra, affinity, options);
    }

    public static void handle(DialoguePacket p, Supplier<NetworkEvent.Context> ctx) {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientHooks.handleDialogue(p));
        ctx.get().setPacketHandled(true);
    }
}
