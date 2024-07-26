package dev.xkmc.cuisinedelight.content.logic;

import dev.xkmc.cuisinedelight.init.CuisineDelight;
import dev.xkmc.l2core.serial.config.BaseConfig;
import dev.xkmc.l2core.serial.config.CollectType;
import dev.xkmc.l2core.serial.config.ConfigCollect;
import dev.xkmc.l2serial.serialization.marker.SerialClass;
import dev.xkmc.l2serial.serialization.marker.SerialField;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

import java.util.*;

@SerialClass
public class IngredientConfig extends BaseConfig {

	public static IngredientConfig get() {
		return CuisineDelight.INGREDIENT.getMerged();
	}

	@SerialField
	@ConfigCollect(CollectType.COLLECT)
	public ArrayList<IngredientEntry> entries = new ArrayList<>();

	private final HashMap<Item, IngredientEntry> cache = new HashMap<>();
	private final HashSet<Item> empty = new HashSet<>();

	@Nullable
	public IngredientEntry getEntry(ItemStack stack) {
		if (stack.isComponentsPatchEmpty()) {
			if (empty.contains(stack.getItem())) {
				return null;
			}
			if (cache.containsKey(stack.getItem())) {
				return cache.get(stack.getItem());
			}
		}
		for (var e : entries) {
			if (e.ingredient.test(stack)) {
				if (stack.isComponentsPatchEmpty()) {
					cache.put(stack.getItem(), e);
				}
				return e;
			}
		}
		if (stack.isComponentsPatchEmpty()) {
			empty.add(stack.getItem());
		}
		return null;
	}

	@SerialClass
	public static class IngredientEntry {

		@SerialField
		public Ingredient ingredient;
		@SerialField
		public FoodType type;
		@SerialField
		public int min_time, max_time, stir_time;
		@SerialField
		public float raw_penalty, overcook_penalty;
		@SerialField
		public int size, nutrition;
		@SerialField
		public ArrayList<EffectEntry> effects = new ArrayList<>();

	}

	public record EffectEntry(Holder<MobEffect> effect, int level, int time) {
	}

	public static IngredientEntry get(Ingredient ingredient, FoodType type, int min_time, int max_time, int stir_time, float raw_penalty, float overcook_penalty, int size, int nutrition, EffectEntry... effects) {
		var ans = new IngredientEntry();
		ans.ingredient = ingredient;
		ans.type = type;
		ans.min_time = min_time;
		ans.max_time = max_time;
		ans.stir_time = stir_time;
		ans.raw_penalty = raw_penalty;
		ans.overcook_penalty = overcook_penalty;
		ans.size = size;
		ans.nutrition = nutrition;
		ans.effects.addAll(Arrays.asList(effects));
		return ans;
	}

	public static IngredientConfig build(IngredientEntry... entries) {
		var ans = new IngredientConfig();
		Collections.addAll(ans.entries, entries);
		return ans;
	}

}
