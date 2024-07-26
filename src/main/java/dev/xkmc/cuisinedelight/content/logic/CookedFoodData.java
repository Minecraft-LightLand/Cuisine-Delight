package dev.xkmc.cuisinedelight.content.logic;

import dev.xkmc.cuisinedelight.content.logic.transform.ItemStageTransform;
import dev.xkmc.cuisinedelight.content.logic.transform.Stage;
import dev.xkmc.cuisinedelight.init.data.CDConfig;
import dev.xkmc.l2serial.serialization.marker.SerialClass;
import net.minecraft.core.Holder;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import vectorwing.farmersdelight.common.registry.ModEffects;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;

@SerialClass
public record CookedFoodData(int total, int size, int nutrition, int score,
							 LinkedHashSet<FoodType> types, ArrayList<Entry> entries) {

	public static final FoodProperties BAD = new FoodProperties.Builder().nutrition(0).saturationModifier(0).build();

	public static CookedFoodData of(CookingData data) {
		LinkedHashSet<FoodType> types = new LinkedHashSet<>();
		ArrayList<Entry> entries = new ArrayList<>();
		int size = 0;
		int nutrition = 0;
		float penalty = 0;
		for (var e : data.contents) {
			var config = IngredientConfig.get().getEntry(e.getItem());
			if (config == null) continue;
			boolean raw = config.min_time > e.getDuration(data, 0);
			boolean overcooked = config.max_time < e.getDuration(data, 0);
			boolean burnt = e.getMaxStirTime(data) > config.stir_time;
			float badness = 0;
			if (raw) badness += config.raw_penalty;
			if (overcooked || burnt) badness += config.overcook_penalty;
			int itemSize = config.size * e.getItem().getCount();
			penalty += itemSize * badness;
			size += itemSize;
			nutrition += config.nutrition * itemSize;
			entries.add(new Entry(e.getItem(), itemSize, burnt, raw, overcooked));
			if (config.type != FoodType.NONE)
				types.add(config.type);
		}
		float goodness = size == 0 ? 0 : Mth.clamp(1 - penalty / size, 0, 1);
		return new CookedFoodData(size, size,
				size == 0 ? 0 : Math.round(goodness * nutrition / size),
				Math.round(goodness * 100),
				types, entries);
	}

	public FoodProperties toFoodData() {
		if (score < 60 || total == 0) return BAD;
		double mult = 1 + (types.size() - 1) * CDConfig.SERVER.varietyBonus.get();
		if (score == 100) {
			mult += CDConfig.SERVER.perfectionBonus.get();
		}
		var ans = new FoodProperties.Builder().nutrition((int) (mult * CDConfig.SERVER.baseServe.get()))
				.saturationModifier((float) (nutrition * CDConfig.SERVER.baseNutrition.get()));
		if (score == 100) {
			ans.fast().alwaysEdible();
		}
		Map<MobEffect, EffectData> map = new LinkedHashMap<>();
		for (var e : entries) {
			e.addMobEffects(map, total);
		}
		if (score == 100 && types.size() > 1) {
			ans.effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT,
					CDConfig.SERVER.nourishmentDuration.get() * types.size()), 1);
		}
		map.forEach((k, v) -> ans.effect(() -> new MobEffectInstance(v.holder, Math.round(v.duration), v.level()), 1));
		return ans.build();
	}

	public CookedFoodData saturationBonus(double v) {
		return new CookedFoodData(total, size, (int) (nutrition * v), score, types, entries);
	}

	public CookedFoodData shrink() {
		return new CookedFoodData(total, size - 1, nutrition, score, types, entries);
	}

	public record Entry(ItemStack stack, int itemSize, boolean burnt, boolean raw, boolean overcooked) {

		private void addMobEffects(Map<MobEffect, EffectData> map, int divisor) {
			var config = IngredientConfig.get().getEntry(stack);
			if (config == null) return;
			if (burnt || raw || overcooked) return;
			for (var e : config.effects) {
				map.compute(e.effect().value(), (k, v) -> {
					float ans = 1.0f * e.time() * stack.getCount() / divisor;
					if (v != null) {
						if (v.level > e.level()) return v;
						if (v.level == e.level())
							return new EffectData(e.effect(), v.level, v.duration + ans);
					}
					return new EffectData(e.effect(), e.level(), ans);
				});
			}
		}

		public ItemStack getEatenStack() {
			var handler = CookTransformConfig.get(stack);
			if (handler instanceof ItemStageTransform t) {
				if (t.stage() != Stage.RAW) {
					ItemStack ans = t.next().getDefaultInstance();
					ans.setCount(stack.getCount());
					ans.applyComponents(stack.getComponentsPatch());
					return ans;
				}
			}
			return stack;
		}

	}

	private record EffectData(Holder<MobEffect> holder, int level, float duration) {

	}

}
