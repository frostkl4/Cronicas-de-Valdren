package com.frostkl4.valdren.item;

import com.frostkl4.valdren.client.ClientHooks;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;

/** O guia do mod: abre a tela de livro do próprio Minecraft com as páginas de Valdren. */
public class GuideBookItem extends LoreItem {
    public GuideBookItem(Properties props) {
        super(props);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (level.isClientSide) {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> ClientHooks::openGuide);
        } else {
            level.playSound(null, player.blockPosition(), SoundEvents.BOOK_PAGE_TURN,
                    player.getSoundSource(), 1.0F, 1.0F);
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }
}
