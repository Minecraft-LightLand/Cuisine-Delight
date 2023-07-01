package dev.xkmc.cuisine_delight.content.logic;

import dev.xkmc.cuisine_delight.init.data.CDConfig;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@SerialClass
public class CookedFoodData {

	public static final FoodProperties BAD = new FoodProperties.Builder().nutrition(0).saturationMod(0).build();

	@SerialClass.SerialField
	public int total, size, nutrition, score, glowstone, redstone;

	@SerialClass.SerialField
	public HashSet<FoodType> types = new HashSet<>();

	@SerialClass.SerialField
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
			int itemSize = config.size * e.item.getCount();
			penalty += itemSize * badness;
			size += itemSize;
			nutrition += config.nutrition * itemSize;
			entries.add(new Entry(e.item, itemSize, burnt, raw, overcooked));
			types.add(config.type);
		}
		float goodness = size == 0 ? 0 : Mth.clamp(1 - penalty / size, 0, 1);
		this.score = Math.round(goodness * 100);
		this.size = size;
		this.total = size;
		double mult = 1 + (types.size() - 1) * CDConfig.COMMON.varietyBonus.get();
		if (penalty == 0) {
			mult += CDConfig.COMMON.perfectionBonus.get();
		}
		this.nutrition = size == 0 ? 0 : (int) Math.round(mult * goodness * nutrition / size);
		this.glowstone = data.glowstone;
		this.redstone = data.redstone;
	}

	public FoodProperties toFoodData() {
		if (score < 60 || total == 0) return BAD;
		var ans = new FoodProperties.Builder().nutrition(4).saturationMod(nutrition * 0.1f);
		if (score == 100) {
			ans.fast().alwaysEat();
		}
		if (types.contains(FoodType.MEAT)) {
			ans.meat();
		}
		Map<MobEffect, EffectData> map = new HashMap<>();
		for (var e : entries) {
			e.addMobEffects(map, total);
		}
		map.forEach((k, v) -> ans.effect(() -> new MobEffectInstance(k, Math.round(v.duration), v.level()), 1));
		return ans.build();
	}

	public record Entry(ItemStack stack, int itemSize, boolean burnt, boolean raw, boolean overcooked) {

		public void addMobEffects(Map<MobEffect, EffectData> map, int divisor) {
			var config = IngredientConfig.get().getEntry(stack);
			if (config == null) return;
			if (burnt || raw || overcooked) return;
			for (var e : config.effects) {
				map.compute(e.effect(), (k, v) -> {
					float ans = 1.0f * e.time() * stack.getCount() / divisor;
					if (v != null) {
						if (v.level > e.level()) return v;
						if (v.level == e.level())
							return new EffectData(v.level, v.duration + ans);
					}
					return new EffectData(e.level(), ans);
				});
			}
		}

	}

	private record EffectData(int level, float duration) {

	}

}
