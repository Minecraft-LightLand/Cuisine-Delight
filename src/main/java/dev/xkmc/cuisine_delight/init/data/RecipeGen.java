package dev.xkmc.cuisine_delight.init.data;

import dev.xkmc.cuisine_delight.init.CDItems;
import dev.xkmc.l2library.repack.registrate.providers.RegistrateRecipeProvider;
import dev.xkmc.l2library.repack.registrate.util.DataIngredient;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
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
		unlock(pvd, ShapelessRecipeBuilder.shapeless(CDItems.SKILLET.get())::unlockedBy, ModItems.SKILLET.get())
				.requires(ModItems.SKILLET.get()).requires(Tags.Items.INGOTS_BRICK).save(pvd);
		pvd.stonecutting(DataIngredient.items(Items.WHITE_GLAZED_TERRACOTTA), CDItems.PLATE, 16);
	}

	public static <T> T unlock(RegistrateRecipeProvider pvd, BiFunction<String, InventoryChangeTrigger.TriggerInstance, T> func, Item item) {
		return func.apply("has_" + pvd.safeName(item), DataIngredient.items(item).getCritereon(pvd));
	}

}
