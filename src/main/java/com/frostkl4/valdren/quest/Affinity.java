package com.frostkl4.valdren.quest;

import net.minecraft.network.chat.Component;

/** Níveis de afinidade com cada NPC. */
public final class Affinity {
    public static final int MAX = 150;
    private static final int[] THRESHOLDS = {0, 10, 30, 60, 100};
    private static final String[] NAMES = {"stranger", "known", "comrade", "friend", "confidant"};

    private Affinity() {
    }

    public static int tier(int points) {
        int t = 0;
        for (int i = 0; i < THRESHOLDS.length; i++) {
            if (points >= THRESHOLDS[i]) {
                t = i;
            }
        }
        return t;
    }

    public static String tierId(int points) {
        return NAMES[tier(points)];
    }

    public static Component tierName(int points) {
        return Component.translatable("valdren.affinity." + tierId(points));
    }

    /** Próximo limite de pontos, ou -1 se já está no máximo. */
    public static int nextThreshold(int points) {
        int t = tier(points);
        return t + 1 < THRESHOLDS.length ? THRESHOLDS[t + 1] : -1;
    }

    public static Component nextTierName(int points) {
        int t = tier(points);
        return Component.translatable("valdren.affinity." + NAMES[Math.min(t + 1, NAMES.length - 1)]);
    }
}
