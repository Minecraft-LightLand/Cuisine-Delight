package dev.xkmc.cuisine_delight.init.data;

import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.DataIngredient;
import dev.xkmc.cuisine_delight.content.logic.FoodType;
import dev.xkmc.cuisine_delight.content.recipe.FoodTypeIngredient;
import dev.xkmc.cuisine_delight.content.recipe.PlateCuisineBuilder;
import dev.xkmc.cuisine_delight.init.CDItems;
import dev.xkmc.cuisine_delight.init.CuisineDelight;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITagManager;
import vectorwing.farmersdelight.common.registry.ModItems;

import java.util.Objects;
import java.util.function.BiFunction;

public class RecipeGen {

	public static void genRecipe(RegistrateRecipeProvider pvd) {
		ITagManager<Item> manager = Objects.requireNonNull(ForgeRegistries.ITEMS.tags());
		unlock(pvd, ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, CDItems.SKILLET.get())::unlockedBy, ModItems.SKILLET.get())
				.requires(ModItems.SKILLET.get()).requires(Tags.Items.INGOTS_BRICK).save(pvd);
		pvd.stonecutting(DataIngredient.items(Items.WHITE_GLAZED_TERRACOTTA), RecipeCategory.MISC, CDItems.PLATE, 16);

		unlock(pvd, new PlateCuisineBuilder(CDItems.PLATE_FOOD.get(), 0, 0)::unlockedBy, CDItems.SKILLET.get())
				.save(pvd, new ResourceLocation(CuisineDelight.MODID, "empty"));
		unlock(pvd, new PlateCuisineBuilder(CDItems.PLATE_FOOD.get(), 0, 0)::unlockedBy, CDItems.SKILLET.get())
				.addAtLeast(new FoodTypeIngredient(FoodType.MEAT), 0.6, 1, 0.1)
				.save(pvd, new ResourceLocation(CuisineDelight.MODID, "meat_plate"));
	}

	public static <T> T unlock(RegistrateRecipeProvider pvd, BiFunction<String, InventoryChangeTrigger.TriggerInstance, T> func, Item item) {
		return func.apply("has_" + pvd.safeName(item), DataIngredient.items(item).getCritereon(pvd));
	}

}
