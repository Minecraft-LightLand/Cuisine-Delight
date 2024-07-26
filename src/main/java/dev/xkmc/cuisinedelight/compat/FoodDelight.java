package dev.xkmc.cuisinedelight.compat;

import cn.foggyhillside.ends_delight.EndsDelight;
import cn.foggyhillside.ends_delight.registry.ItemRegistry;
import com.sammy.minersdelight.MinersDelightMod;
import com.sammy.minersdelight.setup.MDItems;
import com.scouter.oceansdelight.OceansDelight;
import com.scouter.oceansdelight.items.ODItems;
import dev.xkmc.cuisinedelight.content.logic.CookTransformConfig;
import dev.xkmc.cuisinedelight.content.logic.FoodType;
import dev.xkmc.cuisinedelight.content.logic.IngredientConfig;
import dev.xkmc.cuisinedelight.content.logic.transform.Stage;
import dev.xkmc.cuisinedelight.init.CuisineDelight;
import dev.xkmc.l2library.serial.config.ConfigDataProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.fml.ModList;
import umpaz.nethersdelight.NethersDelight;
import umpaz.nethersdelight.common.registry.NDItems;
import vectorwing.farmersdelight.FarmersDelight;

public class FoodDelight {

	public static void add(ConfigDataProvider.Collector map) {

		// ends delight
		if (ModList.get().isLoaded(EndsDelight.MODID)) {
			map.add(CuisineDelight.INGREDIENT, new ResourceLocation(EndsDelight.MODID, "all"), IngredientConfig.build(
					IngredientConfig.get(Ingredient.of(ItemRegistry.DragonLeg.get(), ItemRegistry.RawDragonMeat.get()), FoodType.MEAT,
							240, 360, 80, 1, 0.5f, 3, 12),
					IngredientConfig.get(Ingredient.of(ItemRegistry.RawDragonMeatCuts.get()), FoodType.MEAT,
							180, 300, 80, 1, 0.5f, 2, 12),
					IngredientConfig.get(Ingredient.of(ItemRegistry.LiquidDragonEgg.get()), FoodType.MEAT,
							180, 240, 80, 1, 0.5f, 2, 15),
					IngredientConfig.get(Ingredient.of(ItemRegistry.ShulkerMeat.get()), FoodType.MEAT,
							180, 240, 80, 1, 0.5f, 2, 8,
							new IngredientConfig.EffectEntry(MobEffects.LEVITATION, 0, 80)),
					IngredientConfig.get(Ingredient.of(ItemRegistry.ShulkerMeatSlice.get()), FoodType.MEAT,
							120, 180, 80, 1, 0.5f, 1, 8,
							new IngredientConfig.EffectEntry(MobEffects.LEVITATION, 0, 40)),
					IngredientConfig.get(Ingredient.of(ItemRegistry.RawEnderMiteMeat.get()), FoodType.MEAT,
							180, 300, 80, 1, 0.5f, 1, 8),
					IngredientConfig.get(Ingredient.of(ItemRegistry.ChorusSucculent.get()), FoodType.VEG,
							0, 120, 60, 0.3f, 0.3f, 1, 3),
					IngredientConfig.get(Ingredient.of(ItemRegistry.ChorusFruitGrain.get()), FoodType.VEG,
							0, 120, 60, 0.3f, 0.3f, 1, 2)
			));

			map.add(CuisineDelight.TRANSFORM, new ResourceLocation(EndsDelight.MODID, "meat"), new CookTransformConfig()
					.item(ItemRegistry.RawDragonMeatCuts.get(), ItemRegistry.RoastedDragonMeatCuts.get(), Stage.COOKED)
					.item(ItemRegistry.RawDragonMeat.get(), ItemRegistry.RoastedDragonMeat.get(), Stage.COOKED)
					.item(ItemRegistry.DragonLeg.get(), ItemRegistry.SmokedDragonLeg.get(), Stage.COOKED)
					.item(ItemRegistry.RawEnderMiteMeat.get(), ItemRegistry.DriedEnderMiteMeat.get(), Stage.COOKED)
					.item(ItemRegistry.ShulkerMeat.get(), ItemRegistry.RoastedShulkerMeat.get(), Stage.COOKED)
					.item(ItemRegistry.ShulkerMeatSlice.get(), ItemRegistry.RoastedShulkerMeatSlice.get(), Stage.COOKED)
					.item(ItemRegistry.LiquidDragonEgg.get(), ItemRegistry.FriedDragonEgg.get(), Stage.COOKED)
			);

		}

		// nether delight
		if (ModList.get().isLoaded(NethersDelight.MODID)) {
			map.add(CuisineDelight.INGREDIENT, new ResourceLocation(NethersDelight.MODID, "all"), IngredientConfig.build(
					IngredientConfig.get(Ingredient.of(NDItems.HOGLIN_LOIN.get()), FoodType.MEAT,
							180, 300, 80, 1, 0.5f, 3, 12),
					IngredientConfig.get(Ingredient.of(NDItems.HOGLIN_EAR.get()), FoodType.MEAT,
							120, 300, 80, 1, 0.5f, 1, 10),
					IngredientConfig.get(Ingredient.of(NDItems.STRIDER_SLICE.get()), FoodType.MEAT,
							180, 300, 80, 1, 0.5f, 3, 10,
							new IngredientConfig.EffectEntry(MobEffects.FIRE_RESISTANCE, 0, 400)),
					IngredientConfig.get(Ingredient.of(NDItems.GROUND_STRIDER.get()), FoodType.MEAT,
							120, 300, 80, 1, 0.5f, 2, 10,
							new IngredientConfig.EffectEntry(MobEffects.FIRE_RESISTANCE, 0, 300)),
					IngredientConfig.get(Ingredient.of(NDItems.PROPELPEARL.get()), FoodType.VEG,
							0, 300, 80, 0, 0, 1, 6)
			));

			map.add(CuisineDelight.TRANSFORM, new ResourceLocation(NethersDelight.MODID, "meat"), new CookTransformConfig()
					.item(NDItems.HOGLIN_LOIN.get(), NDItems.HOGLIN_SIRLOIN.get(), Stage.COOKED)
			);
		}

		// ocean delight
		if (ModList.get().isLoaded(OceansDelight.MODID)) {
			map.add(CuisineDelight.INGREDIENT, new ResourceLocation(OceansDelight.MODID, "all"), IngredientConfig.build(
					IngredientConfig.get(Ingredient.of(ODItems.TENTACLES.get()), FoodType.SEAFOOD,
							60, 120, 40, 0.3f, 0.3f, 2, 12),
					IngredientConfig.get(Ingredient.of(ODItems.CUT_TENTACLES.get()), FoodType.SEAFOOD,
							60, 120, 40, 0.3f, 0.3f, 1, 12),
					IngredientConfig.get(Ingredient.of(ODItems.GUARDIAN_TAIL.get()), FoodType.SEAFOOD,
							90, 150, 40, 0.3f, 0.3f, 2, 15),
					IngredientConfig.get(Ingredient.of(ODItems.ELDER_GUARDIAN_SLAB.get()), FoodType.SEAFOOD,
							90, 150, 60, 0.3f, 0.3f, 9, 20),
					IngredientConfig.get(Ingredient.of(ODItems.ELDER_GUARDIAN_SLICE.get()), FoodType.SEAFOOD,
							60, 120, 40, 0.3f, 0.3f, 1, 20),
					IngredientConfig.get(Ingredient.of(ODItems.FUGU_SLICE.get()), FoodType.SEAFOOD,
							60, 120, 40, 0.3f, 0.3f, 1, 15)
			));

			map.add(CuisineDelight.TRANSFORM, new ResourceLocation(OceansDelight.MODID, "meat"), new CookTransformConfig()
					.item(ODItems.ELDER_GUARDIAN_SLICE.get(), ODItems.COOKED_ELDER_GUARDIAN_SLICE.get(), Stage.COOKED)
					.item(ODItems.GUARDIAN_TAIL.get(), ODItems.COOKED_GUARDIAN_TAIL.get(), Stage.COOKED)
			);
		}

		// miners delight
		if (ModList.get().isLoaded(MinersDelightMod.MODID)) {
			map.add(CuisineDelight.INGREDIENT, new ResourceLocation(MinersDelightMod.MODID, "all"), IngredientConfig.build(
					IngredientConfig.get(Ingredient.of(MDItems.SILVERFISH_EGGS.get()), FoodType.MEAT,
							60, 180, 60, 0.8f, 0.3f, 1, 5),
					IngredientConfig.get(Ingredient.of(MDItems.BAT_WING.get()), FoodType.MEAT,
							180, 300, 60, 0.8f, 0.3f, 1, 5),
					IngredientConfig.get(Ingredient.of(MDItems.ARTHROPOD.get()), FoodType.MEAT,
							60, 120, 60, 0.8f, 0.3f, 2, 5),
					IngredientConfig.get(Ingredient.of(MDItems.SQUID.get(), MDItems.GLOW_SQUID.get()), FoodType.SEAFOOD,
							120, 180, 40, 0.3f, 0.5f, 3, 12),
					IngredientConfig.get(Ingredient.of(MDItems.TENTACLES.get()), FoodType.SEAFOOD,
							60, 120, 40, 0.3f, 0.5f, 2, 12),
					IngredientConfig.get(Ingredient.of(MDItems.CAVE_CARROT.get()), FoodType.VEG,
							240, 360, 60, 0.2f, 0.2f, 1, 8),
					IngredientConfig.get(Ingredient.of(MDItems.MOSS.get()), FoodType.VEG,
							60, 120, 60, 0.2f, 0.2f, 1, 3)
			));

			map.add(CuisineDelight.TRANSFORM, new ResourceLocation(MinersDelightMod.MODID, "cook"), new CookTransformConfig()
					.item(MDItems.BAT_WING.get(), MDItems.SMOKED_BAT_WING.get(), Stage.COOKED)
					.item(MDItems.ARTHROPOD.get(), MDItems.COOKED_ARTHROPOD.get(), Stage.COOKED)
					.item(MDItems.SQUID.get(), MDItems.BAKED_SQUID.get(), Stage.COOKED)
					.item(MDItems.GLOW_SQUID.get(), MDItems.BAKED_SQUID.get(), Stage.COOKED)
					.item(MDItems.TENTACLES.get(), MDItems.BAKED_TENTACLES.get(), Stage.COOKED)
					.item(MDItems.CAVE_CARROT.get(), MDItems.BAKED_CAVE_CARROT.get(), Stage.COOKED)
			);
		}

	}
}
