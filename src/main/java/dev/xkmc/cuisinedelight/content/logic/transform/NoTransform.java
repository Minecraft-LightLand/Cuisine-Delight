package dev.xkmc.cuisinedelight.content.logic.transform;

import net.minecraft.world.item.ItemStack;

public record NoTransform() implements CookTransform {

	@Override
	public ItemStack renderStack(ItemStack stack) {
		return stack;
	}

}
