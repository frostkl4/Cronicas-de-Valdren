package com.frostkl4.valdren.item;

import com.frostkl4.valdren.entity.npc.NpcType;
import com.frostkl4.valdren.entity.npc.ValdrenNpc;
import com.frostkl4.valdren.registry.ModEntities;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Coloca um NPC fixo no lugar clicado (fica sempre ali, ignora o horário).
 * Útil para testar ou para montar uma vila própria.
 */
public class NpcSummonItem extends Item {
    private final NpcType npc;

    public NpcSummonItem(NpcType npc, Properties props) {
        super(props);
        this.npc = npc;
    }

    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        Level level = ctx.getLevel();
        if (!(level instanceof ServerLevel server)) {
            return InteractionResult.SUCCESS;
        }
        BlockPos pos = ctx.getClickedPos().relative(ctx.getClickedFace());
        ValdrenNpc entity = ModEntities.NPC.get().create(server);
        if (entity == null) {
            return InteractionResult.FAIL;
        }
        entity.moveTo(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, ctx.getRotation() + 180.0F, 0.0F);
        entity.setup(npc, pos, true);
        server.addFreshEntity(entity);
        entity.arrivalEffect();
        Player player = ctx.getPlayer();
        if (player == null || !player.getAbilities().instabuild) {
            ctx.getItemInHand().shrink(1);
        }
        return InteractionResult.CONSUME;
    }

    @Override
    public Component getName(ItemStack stack) {
        return Component.translatable("item.valdren.npc_call", Component.translatable(npc.key("name")));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable(npc.key("role")).withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("item.valdren.npc_call.lore").withStyle(ChatFormatting.DARK_GRAY));
    }
}
