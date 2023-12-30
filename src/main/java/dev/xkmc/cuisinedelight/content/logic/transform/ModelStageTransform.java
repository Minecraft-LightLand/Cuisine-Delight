package dev.xkmc.cuisinedelight.content.logic.transform;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public record ModelStageTransform(Stage stage, ResourceLocation next) implements CookTransform {

	@Override
	public ItemStack renderStack(ItemStack stack) {
		return stack;
	}

}
