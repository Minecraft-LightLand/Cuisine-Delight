package dev.xkmc.cuisinedelight.content.logic.transform;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public record FluidTransform(int color) implements CookTransform {

	@Override
	public ItemStack renderStack(Stage stage, ItemStack stack) {
		return stack;
	}

	@Override
	public boolean isFluid() {
		return true;
	}

}
