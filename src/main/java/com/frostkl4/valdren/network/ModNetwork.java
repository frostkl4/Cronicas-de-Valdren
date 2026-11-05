package com.frostkl4.valdren.network;

import com.frostkl4.valdren.Valdren;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public final class ModNetwork {
    private static final String VERSION = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            Valdren.id("main"), () -> VERSION, VERSION::equals, VERSION::equals);

    private ModNetwork() {
    }

    public static void register() {
        int id = 0;
        CHANNEL.messageBuilder(DialoguePacket.class, id++, NetworkDirection.PLAY_TO_CLIENT)
                .encoder(DialoguePacket::encode)
                .decoder(DialoguePacket::decode)
                .consumerMainThread(DialoguePacket::handle)
                .add();
        CHANNEL.messageBuilder(DialogueActionPacket.class, id++, NetworkDirection.PLAY_TO_SERVER)
                .encoder(DialogueActionPacket::encode)
                .decoder(DialogueActionPacket::decode)
                .consumerMainThread(DialogueActionPacket::handle)
                .add();
    }

    public static void sendTo(ServerPlayer player, Object message) {
        CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

    public static void sendToServer(Object message) {
        CHANNEL.sendToServer(message);
    }
}
