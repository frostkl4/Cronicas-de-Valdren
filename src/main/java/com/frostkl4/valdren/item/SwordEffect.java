package com.frostkl4.valdren.item;

import net.minecraft.ChatFormatting;

/** Efeito especial de cada espada ao acertar. */
public enum SwordEffect {
    FROST(ChatFormatting.AQUA),
    STORM(ChatFormatting.DARK_AQUA),
    LIFESTEAL(ChatFormatting.RED),
    VERDANT(ChatFormatting.GREEN),
    DUSK(ChatFormatting.LIGHT_PURPLE),
    FLAME(ChatFormatting.GOLD),
    SENTINEL(ChatFormatting.YELLOW),
    MOON(ChatFormatting.BLUE);

    public final ChatFormatting color;

    SwordEffect(ChatFormatting color) {
        this.color = color;
    }
}
