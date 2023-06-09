package dev.xkmc.cuisine_delight.content.recipe;

import dev.xkmc.cuisine_delight.content.item.PlateFoodItem;
import dev.xkmc.cuisine_delight.init.CDMisc;
import dev.xkmc.l2library.base.recipe.BaseRecipeBuilder;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Consumer;

public class PlateCuisineBuilder extends BaseRecipeBuilder<PlateCuisineBuilder, PlateCuisineRecipe, BaseCuisineRecipe<?>, CuisineRecipeContainer> {

	private double maxBonus = 0;
	private double scoreBonus = 0;

	public PlateCuisineBuilder(PlateFoodItem result, double minBonus, double maxBonus) {
		super(CDMisc.PLATE_CUISINE.get());
		recipe.holderItem = result;
		recipe.saturationBonus = minBonus;
		this.maxBonus = maxBonus;
	}

	public PlateCuisineBuilder add(Ingredient ingredient, double min, double max, double score, double bonus) {
		recipe.list.add(new CuisineRecipeMatch(ingredient, min, max, score, bonus));
		scoreBonus += bonus;
		return this;
	}

	public PlateCuisineBuilder addAtLeast(Ingredient ingredient, double min, double score, double bonus) {
		return add(ingredient, min, 2 - min, score, bonus);
	}

	@Override
	public void save(Consumer<FinishedRecipe> pvd, ResourceLocation id) {
		if (scoreBonus > 0) {
			recipe.saturationBonusModifier = maxBonus / scoreBonus;
		}
		super.save(pvd, id);
	}

}
