package dev.xkmc.cuisinedelight.content.logic;

import dev.xkmc.cuisinedelight.content.logic.transform.ItemStageTransform;
import dev.xkmc.cuisinedelight.content.logic.transform.Stage;
import dev.xkmc.cuisinedelight.init.data.CDConfig;
import dev.xkmc.l2library.serial.SerialClass;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import vectorwing.farmersdelight.common.registry.ModEffects;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
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
		this.score = Math.round(goodness * 100);
		this.size = size;
		this.total = size;

		this.nutrition = size == 0 ? 0 : Math.round(goodness * nutrition / size);
		this.glowstone = data.glowstone;
		this.redstone = data.redstone;
	}

	public FoodProperties toFoodData() {
		if (score < 60 || total == 0) return BAD;
		double mult = 1 + (types.size() - 1) * CDConfig.COMMON.varietyBonus.get();
		if (score == 100) {
			mult += CDConfig.COMMON.perfectionBonus.get();
		}
		var ans = new FoodProperties.Builder().nutrition((int) (mult * 4)).saturationMod(nutrition * 0.1f);
		if (score == 100) {
			ans.fast().alwaysEat();
		}
		if (types.contains(FoodType.MEAT)) {
			ans.meat();
		}
		Map<MobEffect, EffectData> map = new LinkedHashMap<>();
		for (var e : entries) {
			e.addMobEffects(map, total);
		}
		if (score == 100 && types.size() > 1) {
			ans.effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT.get(),
					CDConfig.COMMON.nourishmentDuration.get() * types.size()), 1);
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

		public ItemStack getEatenStack() {
			var handler = CookTransformConfig.get(stack);
			if (handler instanceof ItemStageTransform t) {
				if (t.stage() != Stage.RAW) {
					ItemStack ans = t.next().getDefaultInstance();
					ans.setCount(stack.getCount());
					ans.setTag(stack.getTag());
					return ans;
				}
			}
			return stack;
		}

	}

	private record EffectData(int level, float duration) {

	}

}
