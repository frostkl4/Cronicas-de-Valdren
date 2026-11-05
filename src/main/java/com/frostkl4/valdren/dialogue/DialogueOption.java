package com.frostkl4.valdren.dialogue;

import net.minecraft.network.chat.Component;

/** Uma resposta na lista da direita. highlight = texto amarelo (missão nova, troca etc.). */
public record DialogueOption(int action, Component label, boolean highlight) {
}
