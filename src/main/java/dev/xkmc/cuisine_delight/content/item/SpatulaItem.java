package dev.xkmc.cuisine_delight.content.item;

import dev.xkmc.cuisine_delight.content.logic.CookingData;
import dev.xkmc.cuisine_delight.init.CDItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SpatulaItem extends Item {


	public SpatulaItem(Properties pProperties) {
		super(pProperties);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack spatulaStack = player.getItemInHand(hand);
		InteractionHand otherHand = hand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
		ItemStack skilletStack = player.getItemInHand(otherHand);
		if (!skilletStack.is(CDItems.SKILLET.get())) {
			return InteractionResultHolder.pass(spatulaStack);
		}
		if (!level.isClientSide()) {
			CookingData data = CuisineSkilletItem.getData(skilletStack);
			if (data != null) {
				data.stir(level.getGameTime());
				CuisineSkilletItem.setData(skilletStack, data);
				player.getCooldowns().addCooldown(this, 20);//TODO animation time
				player.getCooldowns().addCooldown(CDItems.SKILLET.get(), 20);//TODO animation time
			}
		}
		return InteractionResultHolder.success(spatulaStack);
	}
}
