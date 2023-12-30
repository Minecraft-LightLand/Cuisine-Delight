package dev.xkmc.cuisinedelight.init.data;

import cn.foggyhillside.ends_delight.EndsDelight;
import cn.foggyhillside.ends_delight.registry.ItemRegistry;
import com.scouter.oceansdelight.OceansDelight;
import com.scouter.oceansdelight.items.ODItems;
import dev.xkmc.cuisinedelight.content.logic.CookTransformConfig;
import dev.xkmc.cuisinedelight.content.logic.FoodType;
import dev.xkmc.cuisinedelight.content.logic.IngredientConfig;
import dev.xkmc.cuisinedelight.content.logic.transform.Stage;
import dev.xkmc.cuisinedelight.init.CuisineDelight;
import dev.xkmc.l2library.serial.config.ConfigDataProvider;
import dev.xkmc.twilightdelight.init.TwilightDelight;
import dev.xkmc.twilightdelight.init.registrate.TDEffects;
import dev.xkmc.twilightdelight.init.registrate.delight.DelightFood;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fml.ModList;
import twilightforest.TwilightForestMod;
import twilightforest.init.TFItems;
import umpaz.nethersdelight.NethersDelight;
import umpaz.nethersdelight.common.registry.NDItems;
import vectorwing.farmersdelight.FarmersDelight;
import vectorwing.farmersdelight.common.registry.ModItems;

public class CDConfigGen extends ConfigDataProvider {

	public CDConfigGen(DataGenerator generator) {
		super(generator, "Cuisine Delight Config");
	}

	@Override
	public void add(Collector map) {
		// vanilla
		{
			map.add(CuisineDelight.INGREDIENT, new ResourceLocation(CuisineDelight.MODID, "meat"), IngredientConfig.build(
					IngredientConfig.get(Ingredient.of(Items.MUTTON, Items.PORKCHOP, Items.BEEF), FoodType.MEAT,
							180, 240, 80, 0.5f, 0.5f, 3, 10),
					IngredientConfig.get(Ingredient.of(Items.PORKCHOP), FoodType.MEAT,
							240, 360, 80, 0.5f, 0.3f, 3, 10),
					IngredientConfig.get(Ingredient.of(Items.CHICKEN, Items.RABBIT), FoodType.MEAT,
							180, 240, 80, 1f, 0.3f, 2, 8),
					IngredientConfig.get(Ingredient.of(Items.EGG), FoodType.MEAT,
							60, 240, 80, 0.2f, 0.2f, 1, 8),
					IngredientConfig.get(Ingredient.of(Items.COD, Items.SALMON, Items.TROPICAL_FISH), FoodType.SEAFOOD,
							60, 120, 40, 0.5f, 0.5f, 2, 12),
					IngredientConfig.get(Ingredient.of(Items.PUFFERFISH), FoodType.SEAFOOD,
							60, 120, 40, 0.5f, 0.5f, 1, 5,
							new IngredientConfig.EffectEntry(MobEffects.CONFUSION, 0, 100),
							new IngredientConfig.EffectEntry(MobEffects.HUNGER, 0, 100),
							new IngredientConfig.EffectEntry(MobEffects.POISON, 0, 100)
					)
			));

			map.add(CuisineDelight.INGREDIENT, new ResourceLocation(CuisineDelight.MODID, "vege"), IngredientConfig.build(
					IngredientConfig.get(Ingredient.of(Items.POTATO), FoodType.CARB,
							180, 360, 60, 0.5f, 0.3f, 1, 8),
					IngredientConfig.get(Ingredient.of(Items.BEETROOT, Items.CARROT), FoodType.VEG,
							240, 360, 60, 0.2f, 0.2f, 1, 5),
					IngredientConfig.get(Ingredient.of(Tags.Items.MUSHROOMS), FoodType.VEG,
							60, 360, 60, 0.2f, 0.2f, 1, 4),
					IngredientConfig.get(Ingredient.of(Items.KELP), FoodType.VEG,
							120, 180, 60, 0.3f, 0.3f, 1, 3),
					IngredientConfig.get(Ingredient.of(Items.APPLE, Items.MELON_SLICE, Items.SWEET_BERRIES, Items.GLOW_BERRIES, Items.CHORUS_FRUIT), FoodType.VEG,
							0, 80, 60, 0, 0.3f, 1, 4),
					IngredientConfig.get(Ingredient.of(Items.GOLDEN_CARROT), FoodType.VEG,
							240, 360, 40, 0.5f, 0.2f, 1, 20),
					IngredientConfig.get(Ingredient.of(Items.GOLDEN_APPLE), FoodType.VEG,
							0, 80, 60, 0, 0.3f, 1, 10,
							new IngredientConfig.EffectEntry(MobEffects.REGENERATION, 1, 100),
							new IngredientConfig.EffectEntry(MobEffects.SATURATION, 0, 8)
					),
					IngredientConfig.get(Ingredient.of(Items.ENCHANTED_GOLDEN_APPLE), FoodType.VEG,
							0, 80, 60, 0, 0.3f, 1, 10,
							new IngredientConfig.EffectEntry(MobEffects.REGENERATION, 1, 400),
							new IngredientConfig.EffectEntry(MobEffects.DAMAGE_RESISTANCE, 0, 6000),
							new IngredientConfig.EffectEntry(MobEffects.FIRE_RESISTANCE, 0, 6000),
							new IngredientConfig.EffectEntry(MobEffects.SATURATION, 0, 32)
					)
			));


			map.add(CuisineDelight.INGREDIENT, new ResourceLocation(CuisineDelight.MODID, "misc"), IngredientConfig.build(
					IngredientConfig.get(Ingredient.of(Items.DANDELION), FoodType.NONE,
							60, 80, 40, 0, 0, 0, 0,
							new IngredientConfig.EffectEntry(MobEffects.SATURATION, 0, 7)),
					IngredientConfig.get(Ingredient.of(Items.SUGAR, Items.HONEY_BOTTLE), FoodType.NONE,
							0, 300, 80, 0, 0, 1, 1)
			));

			map.add(CuisineDelight.TRANSFORM, new ResourceLocation(CuisineDelight.MODID, "meat"), new CookTransformConfig()
					.item(Items.MUTTON, Items.COOKED_MUTTON, Stage.COOKED)
					.item(Items.BEEF, Items.COOKED_BEEF, Stage.COOKED)
					.item(Items.PORKCHOP, Items.COOKED_PORKCHOP, Stage.COOKED)
					.item(Items.CHICKEN, Items.COOKED_CHICKEN, Stage.COOKED)
					.item(Items.RABBIT, Items.COOKED_RABBIT, Stage.COOKED)
					.item(Items.COD, Items.COOKED_COD, Stage.COOKED)
					.item(Items.SALMON, Items.COOKED_SALMON, Stage.COOKED)
			);

			map.add(CuisineDelight.TRANSFORM, new ResourceLocation(CuisineDelight.MODID, "other"), new CookTransformConfig()
					.item(Items.POTATO, Items.BAKED_POTATO, Stage.COOKED)
					.item(Items.KELP, Items.DRIED_KELP, Stage.COOKED)
			);
		}
		// farmer's delight
		{
			map.add(CuisineDelight.INGREDIENT, new ResourceLocation(FarmersDelight.MODID, "vege"), IngredientConfig.build(
					IngredientConfig.get(Ingredient.of(ModItems.RICE.get(), ModItems.RAW_PASTA.get(), ModItems.WHEAT_DOUGH.get()), FoodType.CARB,
							300, 360, 80, 0.7f, 0.5f, 2, 8),
					IngredientConfig.get(Ingredient.of(ModItems.TOMATO.get(), ModItems.CABBAGE.get(), ModItems.ONION.get(), ModItems.PUMPKIN_SLICE.get()), FoodType.VEG,
							0, 360, 60, 0, 0.2f, 2, 5),
					IngredientConfig.get(Ingredient.of(ModItems.CABBAGE_LEAF.get()), FoodType.VEG,
							0, 240, 60, 0, 0.2f, 1, 5),
					IngredientConfig.get(Ingredient.of(ModItems.MILK_BOTTLE.get()), FoodType.NONE,
							0, 360, 60, 0, 0, 1, 1)
			));

			map.add(CuisineDelight.INGREDIENT, new ResourceLocation(FarmersDelight.MODID, "meat"), IngredientConfig.build(
					IngredientConfig.get(Ingredient.of(ModItems.HAM.get()), FoodType.MEAT,
							240, 360, 80, 0.5f, 0.5f, 3, 10),
					IngredientConfig.get(Ingredient.of(ModItems.MINCED_BEEF.get(), ModItems.MUTTON_CHOPS.get()), FoodType.MEAT,
							120, 240, 80, 0.5f, 0.5f, 2, 10),
					IngredientConfig.get(Ingredient.of(ModItems.BACON.get()), FoodType.MEAT,
							120, 240, 80, 1f, 0.5f, 2, 10),
					IngredientConfig.get(Ingredient.of(ModItems.CHICKEN_CUTS.get()), FoodType.MEAT,
							120, 240, 80, 1f, 0.5f, 1, 8),
					IngredientConfig.get(Ingredient.of(ModItems.COD_SLICE.get(), ModItems.SALMON_SLICE.get()), FoodType.SEAFOOD,
							60, 120, 40, 0.5f, 0.5f, 1, 12)
			));

			map.add(CuisineDelight.TRANSFORM, new ResourceLocation(FarmersDelight.MODID, "meat"), new CookTransformConfig()
					.item(ModItems.BACON.get(), ModItems.COOKED_BACON.get(), Stage.COOKED)
					.item(ModItems.CHICKEN_CUTS.get(), ModItems.COOKED_CHICKEN_CUTS.get(), Stage.COOKED)
					.item(ModItems.COD_SLICE.get(), ModItems.COOKED_COD_SLICE.get(), Stage.COOKED)
					.item(ModItems.SALMON_SLICE.get(), ModItems.COOKED_SALMON_SLICE.get(), Stage.COOKED)
					.item(ModItems.MINCED_BEEF.get(), ModItems.BEEF_PATTY.get(), Stage.COOKED)
					.item(ModItems.MUTTON_CHOPS.get(), ModItems.COOKED_MUTTON_CHOPS.get(), Stage.COOKED)
					.item(ModItems.HAM.get(), ModItems.SMOKED_HAM.get(), Stage.COOKED)
			);

		}

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
		}

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
	}

}