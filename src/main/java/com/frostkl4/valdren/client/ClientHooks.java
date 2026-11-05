package com.frostkl4.valdren.client;

import com.frostkl4.valdren.network.DialoguePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.BookViewScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;

/** Pontos de entrada só do cliente (chamados via DistExecutor). */
public final class ClientHooks {
    /** Quantidade de páginas do guia (valdren.guide.page.0 ... N-1 no arquivo de idioma). */
    public static final int GUIDE_PAGES = 16;

    private ClientHooks() {
    }

    public static void openGuide() {
        Minecraft.getInstance().setScreen(new BookViewScreen(new BookViewScreen.BookAccess() {
            @Override
            public int getPageCount() {
                return GUIDE_PAGES;
            }

            @Override
            public FormattedText getPageRaw(int index) {
                return Component.translatable("valdren.guide.page." + index);
            }
        }));
    }

    public static void handleDialogue(DialoguePacket packet) {
        Minecraft mc = Minecraft.getInstance();
        if (packet.entityId() < 0) {
            if (mc.screen instanceof DialogueScreen) {
                mc.setScreen(null);
            }
            return;
        }
        if (mc.screen instanceof DialogueScreen screen && screen.entityId() == packet.entityId()) {
            screen.update(packet);
        } else {
            mc.setScreen(new DialogueScreen(packet));
        }
    }
}
