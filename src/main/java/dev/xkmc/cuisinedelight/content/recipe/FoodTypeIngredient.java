package dev.xkmc.cuisinedelight.content.recipe;

import dev.xkmc.cuisinedelight.content.logic.FoodType;
import dev.xkmc.cuisinedelight.content.logic.IngredientConfig;
import dev.xkmc.cuisinedelight.init.CuisineDelight;
import dev.xkmc.cuisinedelight.init.registrate.CDMisc;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.crafting.ICustomIngredient;
import net.neoforged.neoforge.common.crafting.IngredientType;

import java.util.stream.Stream;

public record FoodTypeIngredient(FoodType foodType) implements ICustomIngredient {

	public static Ingredient of(FoodType type){
		return new FoodTypeIngredient(type).toVanilla();
	}

	public Stream<ItemStack> getItems() {
		return Stream.of(foodType.getDisplay());
	}

	public boolean isSimple() {
		return false;
	}

	public IngredientType<?> getType() {
		return CDMisc.ING_FOOD_TYPE.get();
	}

	public boolean test(ItemStack stack) {
		IngredientConfig config = CuisineDelight.INGREDIENT.getMerged();
		var entry = config.getEntry(stack);
		if (entry == null) return false;
		return entry.type == foodType;
	}

}
