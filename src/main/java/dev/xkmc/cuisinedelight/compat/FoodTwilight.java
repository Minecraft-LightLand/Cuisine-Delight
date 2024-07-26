package dev.xkmc.cuisinedelight.compat;

/*
import dev.xkmc.cuisinedelight.content.logic.CookTransformConfig;
import dev.xkmc.cuisinedelight.content.logic.FoodType;
import dev.xkmc.cuisinedelight.content.logic.IngredientConfig;
import dev.xkmc.cuisinedelight.content.logic.transform.Stage;
import dev.xkmc.cuisinedelight.init.CuisineDelight;
import dev.xkmc.l2library.serial.config.ConfigDataProvider;
import dev.xkmc.twilightdelight.init.TwilightDelight;
import dev.xkmc.twilightdelight.init.registrate.TDEffects;
import dev.xkmc.twilightdelight.init.registrate.delight.DelightFood;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.fml.ModList;
import twilightforest.TwilightForestMod;
import twilightforest.init.TFItems;

public class FoodTwilight {

	public static void add(ConfigDataProvider.Collector map) {

		// twilight forest
		if (ModList.get().isLoaded(TwilightForestMod.ID)) {
			map.add(CuisineDelight.INGREDIENT, new ResourceLocation(TwilightForestMod.ID, "meat"), IngredientConfig.build(
					IngredientConfig.get(Ingredient.of(TFItems.RAW_MEEF.get()), FoodType.MEAT,
							240, 360, 80, 0.5f, 0.5f, 3, 12),
					IngredientConfig.get(Ingredient.of(TFItems.RAW_VENISON.get()), FoodType.MEAT,
							240, 360, 80, 0.5f, 0.5f, 3, 10),
					IngredientConfig.get(Ingredient.of(TFItems.HYDRA_CHOP.get()), FoodType.MEAT,
							0, 360, 80, 0.5f, 0.5f, 6, 30),
					IngredientConfig.get(Ingredient.of(TFItems.EXPERIMENT_115.get()), FoodType.MEAT,
							0, 360, 80, 0.5f, 0.5f, 1, 8)
			));

			map.add(CuisineDelight.INGREDIENT, new ResourceLocation(TwilightForestMod.ID, "veges"), IngredientConfig.build(
					IngredientConfig.get(Ingredient.of(TFItems.TORCHBERRIES.get()), FoodType.VEG,
							0, 60, 40, 0.5f, 0.5f, 1, 4,
							new IngredientConfig.EffectEntry(MobEffects.GLOWING, 0, 1200)),
					IngredientConfig.get(Ingredient.of(TFItems.LIVEROOT.get()), FoodType.VEG,
							300, 360, 40, 0.5f, 0.5f, 1, 1,
							new IngredientConfig.EffectEntry(MobEffects.DAMAGE_RESISTANCE, 0, 1200))
			));


			map.add(CuisineDelight.TRANSFORM, new ResourceLocation(TwilightForestMod.ID, "meat"), new CookTransformConfig()
					.item(TFItems.RAW_MEEF.get(), TFItems.COOKED_MEEF.get(), Stage.COOKED)
					.item(TFItems.RAW_VENISON.get(), TFItems.COOKED_VENISON.get(), Stage.COOKED)
			);

		}

		// twilight delight
		if (ModList.get().isLoaded(TwilightDelight.MODID)) {
			map.add(CuisineDelight.INGREDIENT, new ResourceLocation(TwilightDelight.MODID, "meat"), IngredientConfig.build(
					IngredientConfig.get(Ingredient.of(DelightFood.RAW_INSECT.item.get()), FoodType.MEAT,
							240, 360, 80, 1, 0.5f, 2, 8),
					IngredientConfig.get(Ingredient.of(DelightFood.RAW_TOMAHAWK_SMEAK.item.get()), FoodType.MEAT,
							240, 360, 80, 0.5f, 0.5f, 6, 12),
					IngredientConfig.get(Ingredient.of(DelightFood.RAW_MEEF_SLICE.item.get()), FoodType.MEAT,
							180, 300, 80, 0.5f, 0.5f, 2, 12),
					IngredientConfig.get(Ingredient.of(DelightFood.RAW_VENISON_RIB.item.get()), FoodType.MEAT,
							180, 300, 80, 0.5f, 0.5f, 2, 10),
					IngredientConfig.get(Ingredient.of(DelightFood.HYDRA_PIECE.item.get()), FoodType.MEAT,
							180, 300, 80, 0.5f, 0.5f, 3, 30),
					IngredientConfig.get(Ingredient.of(DelightFood.EXPERIMENT_113.item.get()), FoodType.MEAT,
							180, 300, 80, 0.5f, 0.5f, 2, 8,
							new IngredientConfig.EffectEntry(TDEffects.TEMPORAL_SADNESS.get(), 0, 60)),
					IngredientConfig.get(Ingredient.of(DelightFood.EXPERIMENT_110.item.get()), FoodType.MEAT,
							180, 300, 80, 0.5f, 0.5f, 3, 8,
							new IngredientConfig.EffectEntry(MobEffects.HEALTH_BOOST, 4, 2400),
							new IngredientConfig.EffectEntry(MobEffects.NIGHT_VISION, 0, 2400),
							new IngredientConfig.EffectEntry(MobEffects.CONFUSION, 0, 2400),
							new IngredientConfig.EffectEntry(MobEffects.POISON, 0, 2400),
							new IngredientConfig.EffectEntry(MobEffects.BLINDNESS, 0, 1200),
							new IngredientConfig.EffectEntry(TDEffects.TEMPORAL_SADNESS.get(), 0, 100)
					)
			));


			map.add(CuisineDelight.INGREDIENT, new ResourceLocation(TwilightDelight.MODID, "veges"), IngredientConfig.build(
					IngredientConfig.get(Ingredient.of(TFItems.STEELEAF_INGOT.get()), FoodType.VEG,
							300, 360, 40, 0.5f, 0.5f, 1, 1,
							new IngredientConfig.EffectEntry(TDEffects.POISON_RANGE.get(), 0, 1200)),
					IngredientConfig.get(Ingredient.of(TFItems.ICE_BOMB.get()), FoodType.NONE,
							0, 360, 40, 0.5f, 0.5f, 1, 1,
							new IngredientConfig.EffectEntry(TDEffects.FROZEN_RANGE.get(), 0, 1200)),
					IngredientConfig.get(Ingredient.of(TFItems.FIERY_BLOOD.get(), TFItems.FIERY_TEARS.get()), FoodType.NONE,
							0, 360, 80, 0.5f, 0.5f, 1, 1,
							new IngredientConfig.EffectEntry(TDEffects.FIRE_RANGE.get(), 0, 1200))
			));

			map.add(CuisineDelight.TRANSFORM, new ResourceLocation(TwilightDelight.MODID, "meat"), new CookTransformConfig()
					.item(DelightFood.RAW_INSECT.item.get(), DelightFood.COOKED_INSECT.item.get(), Stage.COOKED)
					.item(DelightFood.RAW_MEEF_SLICE.item.get(), DelightFood.COOKED_MEEF_SLICE.item.get(), Stage.COOKED)
					.item(DelightFood.RAW_VENISON_RIB.item.get(), DelightFood.COOKED_VENISON_RIB.item.get(), Stage.COOKED)
					.item(DelightFood.RAW_TOMAHAWK_SMEAK.item.get(), DelightFood.COOKED_TOMAHAWK_SMEAK.item.get(), Stage.COOKED)
			);

			map.add(CuisineDelight.TRANSFORM, new ResourceLocation(TwilightDelight.MODID, "fluids"), new CookTransformConfig()
					.fluid(TFItems.FIERY_BLOOD.get(), 0xFF5C0A0B)
					.fluid(TFItems.FIERY_TEARS.get(), 0xFF5C0A0B)
			);
		}

	}
}
*/