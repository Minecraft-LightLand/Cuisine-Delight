package dev.xkmc.cuisinedelight.content.logic.transform;

import net.minecraft.world.item.ItemStack;

public record NoTransform() implements CookTransform {

	@Override
	public ItemStack renderStack(Stage stage, ItemStack stack) {
		return stack;
	}

}
