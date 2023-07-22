package dev.xkmc.cuisinedelight.content.item;

import dev.xkmc.cuisinedelight.content.logic.CookedFoodData;
import dev.xkmc.cuisinedelight.init.registrate.CDItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class PlateFoodItem extends BaseFoodItem {

	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
		CookedFoodData food = getData(stack);
		super.finishUsingItem(stack, level, entity);
		if (food == null) return CDItems.PLATE.asStack();
		food.size--;
		if (food.size <= 0) return CDItems.PLATE.asStack();
		stack.setCount(1);
		setData(stack, food);
		return stack;
	}

	public PlateFoodItem(Properties properties) {
		super(properties);
	}

}
