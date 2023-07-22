package dev.xkmc.cuisinedelight.init.data;

import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.DataIngredient;
import dev.xkmc.cuisinedelight.content.logic.FoodType;
import dev.xkmc.cuisinedelight.content.recipe.FoodTypeIngredient;
import dev.xkmc.cuisinedelight.content.recipe.PlateCuisineBuilder;
import dev.xkmc.cuisinedelight.init.registrate.CDItems;
import dev.xkmc.cuisinedelight.init.CuisineDelight;
import dev.xkmc.cuisinedelight.init.registrate.PlateFood;
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

		unlock(pvd, new PlateCuisineBuilder(PlateFood.SUSPICIOUS_MIX.item.get(), 0, 0)::unlockedBy, CDItems.SKILLET.get())
				.save(pvd, new ResourceLocation(CuisineDelight.MODID, "empty"));
		unlock(pvd, new PlateCuisineBuilder(PlateFood.MEAT_PASTA.item.get(), 0, 0)::unlockedBy, CDItems.SKILLET.get())
				.addAtLeast(new FoodTypeIngredient(FoodType.MEAT), 0.6, 1, 0.1)
				.save(pvd, new ResourceLocation(CuisineDelight.MODID, "meat_pasta"));
	}

	public static <T> T unlock(RegistrateRecipeProvider pvd, BiFunction<String, InventoryChangeTrigger.TriggerInstance, T> func, Item item) {
		return func.apply("has_" + pvd.safeName(item), DataIngredient.items(item).getCritereon(pvd));
	}

}
