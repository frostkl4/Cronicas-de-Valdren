package com.frostkl4.valdren.quest;

import com.frostkl4.valdren.entity.npc.NpcType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * Uma missão de NPC. KILL = derrotar criaturas; COLLECT = trazer itens (entregues na conclusão).
 * Textos ficam no arquivo de idioma: valdren.quest.&lt;id&gt;.title/offer/accept/progress/ready/done.
 */
public final class Quest {
    public enum Kind { KILL, COLLECT }

    public final String id;
    public final NpcType npc;
    public final Kind kind;
    public final ResourceLocation target;
    public final int amount;
    int minAffinity;
    int cooldown;
    int affinityReward = 5;
    int xpReward;
    private final List<Supplier<ItemStack>> rewards = new ArrayList<>();
    @Nullable
    private Supplier<ItemStack> startItem;

    private Quest(String id, NpcType npc, Kind kind, String target, int amount) {
        this.id = id;
        this.npc = npc;
        this.kind = kind;
        this.target = new ResourceLocation(target);
        this.amount = amount;
    }

    public static Quest kill(String id, NpcType npc, String entity, int amount) {
        return new Quest(id, npc, Kind.KILL, entity, amount);
    }

    public static Quest collect(String id, NpcType npc, String item, int amount) {
        return new Quest(id, npc, Kind.COLLECT, item, amount);
    }

    public Quest needs(int affinity) {
        this.minAffinity = affinity;
        return this;
    }

    /** Missão repetível: volta a ficar disponível depois de N ticks (24000 = 1 dia). */
    public Quest repeat(int ticks) {
        this.cooldown = ticks;
        return this;
    }

    public Quest affinity(int amount) {
        this.affinityReward = amount;
        return this;
    }

    public Quest xp(int amount) {
        this.xpReward = amount;
        return this;
    }

    public Quest reward(Supplier<ItemStack> stack) {
        this.rewards.add(stack);
        return this;
    }

    public Quest start(Supplier<ItemStack> stack) {
        this.startItem = stack;
        return this;
    }

    public boolean repeatable() {
        return cooldown > 0;
    }

    public int minAffinity() {
        return minAffinity;
    }

    public int affinityReward() {
        return affinityReward;
    }

    public int xpReward() {
        return xpReward;
    }

    public String key(String suffix) {
        return "valdren.quest." + id + "." + suffix;
    }

    public Component title() {
        return Component.translatable(key("title"));
    }

    public Component targetName() {
        if (kind == Kind.KILL) {
            EntityType<?> type = ForgeRegistries.ENTITY_TYPES.getValue(target);
            return type != null ? type.getDescription() : Component.literal(target.toString());
        }
        Item item = ForgeRegistries.ITEMS.getValue(target);
        return item != null ? item.getDescription() : Component.literal(target.toString());
    }

    public Component objective() {
        return Component.translatable(kind == Kind.KILL ? "valdren.objective.kill" : "valdren.objective.collect",
                amount, targetName());
    }

    public Component rewardText() {
        MutableComponent list = Component.empty();
        int n = 0;
        for (ItemStack stack : makeRewards()) {
            if (n++ > 0) {
                list.append(", ");
            }
            if (stack.getCount() > 1) {
                list.append(stack.getCount() + "x ");
            }
            list.append(stack.getHoverName());
        }
        if (xpReward > 0) {
            if (n > 0) {
                list.append(", ");
            }
            list.append(xpReward + " XP");
        }
        return Component.translatable("valdren.dialogue.reward", list);
    }

    public List<ItemStack> makeRewards() {
        List<ItemStack> out = new ArrayList<>();
        for (Supplier<ItemStack> s : rewards) {
            out.add(s.get());
        }
        return out;
    }

    @Nullable
    public ItemStack makeStartItem() {
        return startItem == null ? null : startItem.get();
    }

    public boolean matchesEntity(EntityType<?> type) {
        return kind == Kind.KILL && target.equals(ForgeRegistries.ENTITY_TYPES.getKey(type));
    }

    public int countItems(Player player) {
        Item item = ForgeRegistries.ITEMS.getValue(target);
        if (item == null) {
            return 0;
        }
        int n = 0;
        for (ItemStack s : player.getInventory().items) {
            if (s.is(item)) {
                n += s.getCount();
            }
        }
        return n;
    }

    public void removeItems(Player player) {
        Item item = ForgeRegistries.ITEMS.getValue(target);
        if (item == null) {
            return;
        }
        int left = amount;
        for (ItemStack s : player.getInventory().items) {
            if (left <= 0) {
                break;
            }
            if (s.is(item)) {
                int take = Math.min(left, s.getCount());
                s.shrink(take);
                left -= take;
            }
        }
        player.getInventory().setChanged();
    }
}
