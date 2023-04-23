package dev.xkmc.cuisine_delight.content.logic;

import dev.xkmc.l2library.serial.SerialClass;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@SerialClass
public class CookedFoodData {

	@SerialClass.SerialField
	public int size, nutrition, score;

	public ArrayList<Entry> entries = new ArrayList<>();

	@Deprecated
	public CookedFoodData() {

	}

	public CookedFoodData(CookingData data) {
		int size = 0;
		int nutrition = 0;
		float penalty = 0;
		for (var e : data.contents) {
			var config = IngredientConfig.get().getEntry(e.item);
			if (config == null) continue;
			boolean raw = e.startTime + config.min_time > data.lastActionTime;
			boolean overcooked = e.startTime + config.max_time < data.lastActionTime;
			boolean burnt = e.maxStirTime > config.stir_time;
			float badness = 0;
			if (raw) badness += config.raw_penalty;
			if (overcooked || burnt) badness += config.overcook_penalty;
			penalty += config.size * badness;
			size += config.size;
			nutrition += config.nutrition * config.size;
			entries.add(new Entry(e.item, config.size, burnt, raw, overcooked));
		}
		float goodness = size == 0 ? 0 : Mth.clamp(1 - penalty / size, 0, 1);
		this.score = Math.round(goodness * 100);
		this.size = size;
		this.nutrition = size == 0 ? 0 : Math.round(goodness * nutrition / size);
	}

	@Nullable
	public FoodProperties toFoodData() {
		if (score < 60 || size == 0) return null;
		var ans = new FoodProperties.Builder().nutrition(4).saturationMod(nutrition * 0.1f);
		if (score == 100) {
			ans.fast();
		}
		Map<MobEffect, Float> map = new HashMap<>();
		for (var e : entries) {
			e.addMobEffects(map, size);
		}
		map.forEach((k, v) -> ans.effect(() -> new MobEffectInstance(k, Math.round(v)), 1));
		return ans.build();
	}

	public record Entry(ItemStack stack, int size, boolean burnt, boolean raw, boolean overcooked) {

		public void addMobEffects(Map<MobEffect, Float> map, int divisor) {
			var config = IngredientConfig.get().getEntry(stack);
			if (config == null) return;
			if (burnt || raw || overcooked) return;
			for (var e : config.effects) {
				map.compute(e.effect(), (k, v) -> (v == null ? 0 : v) + e.time() * size / divisor);
			}
		}

	}

}
