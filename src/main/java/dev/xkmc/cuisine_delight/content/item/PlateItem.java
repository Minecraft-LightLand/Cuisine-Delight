package dev.xkmc.cuisine_delight.content.item;

import dev.xkmc.cuisine_delight.content.logic.CookedFoodData;
import dev.xkmc.cuisine_delight.content.logic.CookingData;
import dev.xkmc.cuisine_delight.init.CDItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class PlateItem extends Item {


	public PlateItem(Properties pProperties) {
		super(pProperties);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack plateStack = player.getItemInHand(hand);
		InteractionHand otherHand = hand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
		ItemStack skilletStack = player.getItemInHand(otherHand);
		if (!skilletStack.is(CDItems.SKILLET.get())) {
			return InteractionResultHolder.pass(plateStack);
		}
		CookingData data = CuisineSkilletItem.getData(skilletStack);
		if (data == null) {
			return InteractionResultHolder.pass(plateStack);
		}
		if (!level.isClientSide()) {
			CuisineSkilletItem.setData(skilletStack, null);
			data.update(level.getGameTime());
			CookedFoodData food = new CookedFoodData(data);
			ItemStack foodStack = CDItems.FOOD.asStack();
			FoodItem.setData(foodStack, food);
			plateStack.shrink(1);
			player.getInventory().placeItemBackInInventory(foodStack);
		}
		return InteractionResultHolder.success(plateStack);
	}
}
