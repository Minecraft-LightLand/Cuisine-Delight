package dev.xkmc.cuisinedelight.content.recipe;

import dev.xkmc.cuisinedelight.content.item.BaseFoodItem;
import dev.xkmc.cuisinedelight.init.registrate.CDMisc;
import dev.xkmc.l2core.serial.recipe.BaseRecipeBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.Objects;

public class PlateCuisineBuilder extends BaseRecipeBuilder<PlateCuisineBuilder, PlateCuisineRecipe, BaseCuisineRecipe<?>, CuisineRecipeContainer> {

	private double maxBonus = 0;
	private double scoreBonus = 0;

	public PlateCuisineBuilder(BaseFoodItem result, double minBonus, double maxBonus) {
		super(CDMisc.PLATE_CUISINE.get(), result);
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
	public void save(RecipeOutput pvd, ResourceLocation id) {
		if (scoreBonus > 0) {
			recipe.saturationBonusModifier = maxBonus / scoreBonus;
		}
		super.save(pvd, id);
	}

	public void save(RecipeOutput pvd) {
		save(pvd, Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(recipe.holderItem)));
	}

}
