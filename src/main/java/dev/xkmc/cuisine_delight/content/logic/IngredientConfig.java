package dev.xkmc.cuisine_delight.content.logic;

import dev.xkmc.cuisine_delight.init.NetworkManager;
import dev.xkmc.l2library.serial.SerialClass;
import dev.xkmc.l2library.serial.config.CollectType;
import dev.xkmc.l2library.serial.config.ConfigCollect;
import dev.xkmc.l2library.serial.network.BaseConfig;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

import java.util.*;

@SerialClass
public class IngredientConfig extends BaseConfig {

	public static IngredientConfig get() {
		return NetworkManager.INGREDIENT.getMerged();
	}

	@SerialClass.SerialField
	@ConfigCollect(CollectType.COLLECT)
	public ArrayList<IngredientEntry> entries = new ArrayList<>();

	private final HashMap<Item, IngredientEntry> cache = new HashMap<>();
	private final HashSet<Item> empty = new HashSet<>();

	@Nullable
	public IngredientEntry getEntry(ItemStack stack) {
		if (stack.getTag() == null) {
			if (empty.contains(stack.getItem())) {
				return null;
			}
			if (cache.containsKey(stack.getItem())) {
				return cache.get(stack.getItem());
			}
		}
		for (var e : entries) {
			if (e.ingredient.test(stack)) {
				if (stack.getTag() == null) {
					cache.put(stack.getItem(), e);
				}
				return e;
			}
		}
		if (stack.getTag() == null) {
			empty.add(stack.getItem());
		}
		return null;
	}

	@SerialClass
	public static class IngredientEntry {

		@SerialClass.SerialField
		public Ingredient ingredient;
		@SerialClass.SerialField
		public int min_time, max_time, stir_time;
		@SerialClass.SerialField
		public float raw_penalty, overcook_penalty;
		@SerialClass.SerialField
		public int size, nutrition;
		@SerialClass.SerialField
		public ArrayList<EffectEntry> effects = new ArrayList<>();

	}

	public record EffectEntry(MobEffect effect, int time) {
	}

	public static IngredientEntry get(Ingredient ingredient, int min_time, int max_time, int stir_time, float raw_penalty, float overcook_penalty, int size, int nutrition, EffectEntry... effects) {
		var ans = new IngredientEntry();
		ans.ingredient = ingredient;
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
