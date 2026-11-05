package com.frostkl4.valdren.entity.npc;

import com.frostkl4.valdren.Valdren;
import net.minecraft.resources.ResourceLocation;

/**
 * Os viajantes de Valdren. Cada um aparece nas vilas numa faixa de horário
 * (ticks do dia: 0 = 6h, 6000 = meio-dia, 12000 = 18h, 18000 = meia-noite).
 */
public enum NpcType {
    /** Bram Martelo-Fundo, ferreiro. Manhã até o meio da tarde. */
    BRAM("bram", 0, 9000, new int[]{0, 0, 10, 30}),
    /** Dona Iara, herbalista. Tarde. */
    IARA("iara", 5500, 12500, new int[]{0, 0, 10, 30}),
    /** Mestre Orvyn, cronista de Valdren. Entardecer e começo da noite. */
    ORVYN("orvyn", 11500, 17500, new int[]{0, 10, 30, 60}),
    /** Ravena, caçadora de feras. Noite inteira. */
    RAVENA("ravena", 13000, 23500, new int[]{0, 0, 10, 30});

    public final String id;
    private final int start;
    private final int end;
    private final int[] storyAffinity;

    NpcType(String id, int start, int end, int[] storyAffinity) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.storyAffinity = storyAffinity;
    }

    public boolean isPresentAt(long dayTime) {
        int t = (int) Math.floorMod(dayTime, 24000L);
        return start <= end ? t >= start && t < end : t >= start || t < end;
    }

    public int storyCount() {
        return storyAffinity.length;
    }

    public int storyAffinity(int index) {
        return storyAffinity[index];
    }

    public String key(String suffix) {
        return "valdren.npc." + id + "." + suffix;
    }

    public ResourceLocation texture() {
        return new ResourceLocation(Valdren.MODID, "textures/entity/npc/" + id + ".png");
    }

    public static NpcType byId(String id) {
        for (NpcType t : values()) {
            if (t.id.equals(id)) {
                return t;
            }
        }
        return BRAM;
    }
}
