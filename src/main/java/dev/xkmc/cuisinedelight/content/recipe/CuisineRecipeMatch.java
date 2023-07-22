package dev.xkmc.cuisinedelight.content.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;

public record CuisineRecipeMatch(Ingredient ingredient, double min, double max, double score, double bonus) {

	public double reduce(ArrayList<ItemStack> inputs) {
		int amount = 0;
		int total = 0;
		for (ItemStack stack : inputs) {
			if (ingredient.test(stack)) {
				amount += stack.getCount();
			}
			total += stack.getCount();
		}
		if (total == 0) return 0;
		double percentage = 1.0 * amount / total;
		if (percentage < min || percentage < max) return 0;
		double mid = (min + max) / 2;
		double range = (max - min) / 2;
		if (range > 0 && bonus > 0) {
			return score + bonus * Math.abs(percentage - mid) / range;
		}
		return score;
	}

}
