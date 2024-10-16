package dev.xkmc.cuisinedelight.init.data;

import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.DataIngredient;
import dev.xkmc.cuisinedelight.content.logic.FoodType;
import dev.xkmc.cuisinedelight.content.recipe.FoodTypeIngredient;
import dev.xkmc.cuisinedelight.content.recipe.PlateCuisineBuilder;
import dev.xkmc.cuisinedelight.init.registrate.CDItems;
import dev.xkmc.cuisinedelight.init.registrate.PlateFood;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.Tags;
import vectorwing.farmersdelight.common.registry.ModItems;

import java.util.function.BiFunction;

public class RecipeGen {

	public static void genRecipe(RegistrateRecipeProvider pvd) {
		unlock(pvd, ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, CDItems.SKILLET.get())::unlockedBy, ModItems.SKILLET.get())
				.requires(ModItems.SKILLET.get()).requires(Tags.Items.BRICKS).save(pvd);
		pvd.stonecutting(DataIngredient.tag(ItemTags.PLANKS), RecipeCategory.MISC, CDItems.PLATE, 16);
		pvd.stonecutting(DataIngredient.items(Items.IRON_INGOT), RecipeCategory.MISC, CDItems.SPATULA, 1);

		unlock(pvd, new PlateCuisineBuilder(PlateFood.SUSPICIOUS_MIX.item.get(), 0, 0)::unlockedBy, CDItems.SKILLET.get())
				.save(pvd);

		// fried rice
		{
			unlock(pvd, new PlateCuisineBuilder(PlateFood.FRIED_RICE.item.get(), 0, 0)::unlockedBy, CDItems.SKILLET.get())
					.addAtLeast(Ingredient.of(ModItems.RICE.get()), 0.75, 2, 0.1)
					.save(pvd);

			unlock(pvd, new PlateCuisineBuilder(PlateFood.MIXED_FRIED_RICE.item.get(), 0, 0)::unlockedBy, CDItems.SKILLET.get())
					.addAtLeast(Ingredient.of(ModItems.RICE.get()), 0.4, 1, 0.1)
					.save(pvd);

			unlock(pvd, new PlateCuisineBuilder(PlateFood.SEAFOOD_FRIED_RICE.item.get(), 0, 0)::unlockedBy, CDItems.SKILLET.get())
					.addAtLeast(Ingredient.of(ModItems.RICE.get()), 0.4, 1, 0.1)
					.addAtLeast(FoodTypeIngredient.of(FoodType.SEAFOOD), 0.3, 1, 0.1)
					.save(pvd);


			unlock(pvd, new PlateCuisineBuilder(PlateFood.MEAT_FRIED_RICE.item.get(), 0, 0)::unlockedBy, CDItems.SKILLET.get())
					.addAtLeast(Ingredient.of(ModItems.RICE.get()), 0.4, 1, 0.1)
					.addAtLeast(FoodTypeIngredient.of(FoodType.MEAT), 0.3, 1, 0.1)
					.save(pvd);


			unlock(pvd, new PlateCuisineBuilder(PlateFood.VEGETABLE_FRIED_RICE.item.get(), 0, 0)::unlockedBy, CDItems.SKILLET.get())
					.addAtLeast(Ingredient.of(ModItems.RICE.get()), 0.4, 1, 0.1)
					.addAtLeast(FoodTypeIngredient.of(FoodType.VEG), 0.3, 1, 0.1)
					.save(pvd);

			unlock(pvd, new PlateCuisineBuilder(PlateFood.HAM_FRIED_RICE.item.get(), 0, 0)::unlockedBy, CDItems.SKILLET.get())
					.addAtLeast(Ingredient.of(ModItems.RICE.get()), 0.4, 2, 0.2)
					.addAtLeast(Ingredient.of(ModItems.HAM.get()), 0.2, 1, 0.2)
					.save(pvd);
		}

		// pasta
		{

			unlock(pvd, new PlateCuisineBuilder(PlateFood.FRIED_PASTA.item.get(), 0, 0)::unlockedBy, CDItems.SKILLET.get())
					.addAtLeast(Ingredient.of(ModItems.RAW_PASTA.get()), 0.75, 2, 0.1)
					.save(pvd);
			unlock(pvd, new PlateCuisineBuilder(PlateFood.MIXED_PASTA.item.get(), 0, 0)::unlockedBy, CDItems.SKILLET.get())
					.addAtLeast(Ingredient.of(ModItems.RAW_PASTA.get()), 0.4, 1, 0.1)
					.save(pvd);

			unlock(pvd, new PlateCuisineBuilder(PlateFood.MEAT_PASTA.item.get(), 0, 0)::unlockedBy, CDItems.SKILLET.get())
					.addAtLeast(Ingredient.of(ModItems.RAW_PASTA.get()), 0.4, 1, 0.1)
					.addAtLeast(FoodTypeIngredient.of(FoodType.MEAT), 0.3, 1, 0.1)
					.save(pvd);

			unlock(pvd, new PlateCuisineBuilder(PlateFood.SEAFOOD_PASTA.item.get(), 0, 0)::unlockedBy, CDItems.SKILLET.get())
					.addAtLeast(Ingredient.of(ModItems.RAW_PASTA.get()), 0.4, 1, 0.1)
					.addAtLeast(FoodTypeIngredient.of(FoodType.SEAFOOD), 0.3, 1, 0.1)
					.save(pvd);

			unlock(pvd, new PlateCuisineBuilder(PlateFood.VEGETABLE_PASTA.item.get(), 0, 0)::unlockedBy, CDItems.SKILLET.get())
					.addAtLeast(Ingredient.of(ModItems.RAW_PASTA.get()), 0.4, 1, 0.1)
					.addAtLeast(FoodTypeIngredient.of(FoodType.VEG), 0.3, 1, 0.1)
					.save(pvd);
		}

		// platter and mix
		{
			unlock(pvd, new PlateCuisineBuilder(PlateFood.MEAT_PLATTER.item.get(), 0, 0)::unlockedBy, CDItems.SKILLET.get())
					.addAtLeast(FoodTypeIngredient.of(FoodType.MEAT), 0.75, 1, 0.1)
					.save(pvd);

			unlock(pvd, new PlateCuisineBuilder(PlateFood.SEAFOOD_PLATTER.item.get(), 0, 0)::unlockedBy, CDItems.SKILLET.get())
					.addAtLeast(FoodTypeIngredient.of(FoodType.SEAFOOD), 0.75, 1, 0.1)
					.save(pvd);

			unlock(pvd, new PlateCuisineBuilder(PlateFood.VEGETABLE_PLATTER.item.get(), 0, 0)::unlockedBy, CDItems.SKILLET.get())
					.addAtLeast(FoodTypeIngredient.of(FoodType.VEG), 0.75, 1, 0.1)
					.save(pvd);

			unlock(pvd, new PlateCuisineBuilder(PlateFood.MEAT_WITH_SEAFOOD.item.get(), 0, 0)::unlockedBy, CDItems.SKILLET.get())
					.addAtLeast(FoodTypeIngredient.of(FoodType.MEAT), 0.35, 1, 0.1)
					.addAtLeast(FoodTypeIngredient.of(FoodType.SEAFOOD), 0.35, 1, 0.1)
					.save(pvd);

			unlock(pvd, new PlateCuisineBuilder(PlateFood.MEAT_WITH_VEGETABLES.item.get(), 0, 0)::unlockedBy, CDItems.SKILLET.get())
					.addAtLeast(FoodTypeIngredient.of(FoodType.MEAT), 0.35, 1, 0.1)
					.addAtLeast(FoodTypeIngredient.of(FoodType.VEG), 0.35, 1, 0.1)
					.save(pvd);

			unlock(pvd, new PlateCuisineBuilder(PlateFood.SEAFOOD_WITH_VEGETABLES.item.get(), 0, 0)::unlockedBy, CDItems.SKILLET.get())
					.addAtLeast(FoodTypeIngredient.of(FoodType.SEAFOOD), 0.35, 1, 0.1)
					.addAtLeast(FoodTypeIngredient.of(FoodType.VEG), 0.35, 1, 0.1)
					.save(pvd);
		}

		// misc
		{

			unlock(pvd, new PlateCuisineBuilder(PlateFood.FRIED_MEAT_AND_MELON.item.get(), 0, 0)::unlockedBy, CDItems.SKILLET.get())
					.addAtLeast(FoodTypeIngredient.of(FoodType.MEAT), 0.4, 1, 0.1)
					.addAtLeast(Ingredient.of(Items.MELON_SLICE), 0.2, 2, 0.1)
					.save(pvd);

			unlock(pvd, new PlateCuisineBuilder(PlateFood.FRIED_MUSHROOM.item.get(), 0, 0)::unlockedBy, CDItems.SKILLET.get())
					.addAtLeast(Ingredient.of(Tags.Items.MUSHROOMS), 0.75, 1, 0.1)
					.save(pvd);

			unlock(pvd, new PlateCuisineBuilder(PlateFood.SCRAMBLED_EGG_AND_TOMATO.item.get(), 0, 0)::unlockedBy, CDItems.SKILLET.get())
					.addAtLeast(Ingredient.of(Tags.Items.EGGS), 0.3, 1, 0.1)
					.addAtLeast(Ingredient.of(ModItems.TOMATO.get()), 0.3, 2, 0.1)
					.save(pvd);
		}
	}

	public static <T> T unlock(RegistrateRecipeProvider pvd, BiFunction<String, Criterion<InventoryChangeTrigger.TriggerInstance>, T> func, Item item) {
		return func.apply("has_" + pvd.safeName(item), DataIngredient.items(item).getCriterion(pvd));
	}

}
