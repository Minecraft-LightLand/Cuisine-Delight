package dev.xkmc.cuisinedelight.content.recipe;

import dev.xkmc.cuisinedelight.content.logic.CookedFoodData;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CuisineRecipeContainer extends SimpleContainer implements RecipeInput {

	public final List<ItemStack> list = new ArrayList<>();

	public CuisineRecipeContainer(CookedFoodData foodData) {
		Map<Item, Integer> map = new LinkedHashMap<>();
		List<ItemStack> special = new ArrayList<>();
		for (var ent : foodData.entries()) {
			if (ent.stack().isComponentsPatchEmpty()) {
				map.compute(ent.stack().getItem(), (x, old) -> (old == null ? 0 : old) + ent.itemSize());
			} else {
				ItemStack copy = ent.stack().copy();
				copy.setCount(ent.itemSize());
				special.add(copy);
			}
		}
		map.forEach((k, v) -> list.add(new ItemStack(k, v)));
		list.addAll(special);
	}

	@Override
	public int size() {
		return list.size();
	}

}
