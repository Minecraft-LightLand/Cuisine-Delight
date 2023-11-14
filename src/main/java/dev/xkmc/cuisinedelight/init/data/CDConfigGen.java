package dev.xkmc.cuisinedelight.init.data;

import cn.foggyhillside.endsdelight.EndsDelight;
import cn.foggyhillside.endsdelight.registry.ItemRegistry;
import dev.xkmc.cuisinedelight.content.logic.FoodType;
import dev.xkmc.cuisinedelight.content.logic.IngredientConfig;
import dev.xkmc.cuisinedelight.init.CuisineDelight;
import dev.xkmc.l2library.serial.network.BaseConfig;
import dev.xkmc.l2library.serial.network.ConfigDataProvider;
import dev.xkmc.twilightdelight.init.TwilightDelight;
import dev.xkmc.twilightdelight.init.registrate.TDEffects;
import dev.xkmc.twilightdelight.init.registrate.delight.DelightFood;
import net.minecraft.data.DataGenerator;
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

import java.util.Map;

public class CDConfigGen extends ConfigDataProvider {
	public CDConfigGen(DataGenerator generator) {
		super(generator, "data/", "Cuisine Delight Config");
	}

	@Override
	public void add(Map<String, BaseConfig> map) {
		// vanilla
		{
			map.put(CuisineDelight.MODID + "/cuisinedelight_config/" + NetworkManager.INGREDIENT.getID() + "/meat", IngredientConfig.build(
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

			map.put(CuisineDelight.MODID + "/cuisinedelight_config/" + NetworkManager.INGREDIENT.getID() + "/vege", IngredientConfig.build(
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


			map.put(CuisineDelight.MODID + "/cuisinedelight_config/" + NetworkManager.INGREDIENT.getID() + "/misc", IngredientConfig.build(
					IngredientConfig.get(Ingredient.of(Items.DANDELION), FoodType.NONE,
							60, 80, 40, 0, 0, 0, 0,
							new IngredientConfig.EffectEntry(MobEffects.SATURATION, 0, 7)),
					IngredientConfig.get(Ingredient.of(Items.SUGAR, Items.HONEY_BOTTLE), FoodType.NONE,
							0, 300, 80, 0, 0, 1, 1)
			));
		}
		// farmer's delight
		{

			map.put(FarmersDelight.MODID + "/cuisinedelight_config/" + NetworkManager.INGREDIENT.getID() + "/vege", IngredientConfig.build(
					IngredientConfig.get(Ingredient.of(ModItems.RICE.get(), ModItems.RAW_PASTA.get(), ModItems.WHEAT_DOUGH.get()), FoodType.CARB,
							300, 360, 80, 0.7f, 0.5f, 2, 8),
					IngredientConfig.get(Ingredient.of(ModItems.TOMATO.get(), ModItems.CABBAGE.get(), ModItems.ONION.get(), ModItems.PUMPKIN_SLICE.get()), FoodType.VEG,
							0, 360, 60, 0, 0.2f, 2, 5),
					IngredientConfig.get(Ingredient.of(ModItems.CABBAGE_LEAF.get()), FoodType.VEG,
							0, 240, 60, 0, 0.2f, 1, 5),
					IngredientConfig.get(Ingredient.of(ModItems.MILK_BOTTLE.get()), FoodType.NONE,
							0, 360, 60, 0, 0, 1, 1)
			));

			map.put(FarmersDelight.MODID + "/cuisinedelight_config/" + NetworkManager.INGREDIENT.getID() + "/meat", IngredientConfig.build(
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
		}

		// twilight forest
		if (ModList.get().isLoaded(TwilightForestMod.ID)) {
			map.put(TwilightForestMod.ID + "/cuisinedelight_config/" + NetworkManager.INGREDIENT.getID() + "/meat", IngredientConfig.build(
					IngredientConfig.get(Ingredient.of(TFItems.RAW_MEEF.get()), FoodType.MEAT,
							240, 360, 80, 0.5f, 0.5f, 3, 12),
					IngredientConfig.get(Ingredient.of(TFItems.RAW_VENISON.get()), FoodType.MEAT,
							240, 360, 80, 0.5f, 0.5f, 3, 10),
					IngredientConfig.get(Ingredient.of(TFItems.HYDRA_CHOP.get()), FoodType.MEAT,
							0, 360, 80, 0.5f, 0.5f, 6, 30),
					IngredientConfig.get(Ingredient.of(TFItems.EXPERIMENT_115.get()), FoodType.MEAT,
							0, 360, 80, 0.5f, 0.5f, 1, 8)
			));

			map.put(TwilightForestMod.ID + "/cuisinedelight_config/" + NetworkManager.INGREDIENT.getID() + "/veges", IngredientConfig.build(
					IngredientConfig.get(Ingredient.of(TFItems.TORCHBERRIES.get()), FoodType.VEG,
							0, 60, 40, 0.5f, 0.5f, 1, 4,
							new IngredientConfig.EffectEntry(MobEffects.GLOWING, 0, 1200)),
					IngredientConfig.get(Ingredient.of(TFItems.LIVEROOT.get()), FoodType.VEG,
							300, 360, 40, 0.5f, 0.5f, 1, 1,
							new IngredientConfig.EffectEntry(MobEffects.DAMAGE_RESISTANCE, 0, 1200))
			));

		}

		// twilight delight
		if (ModList.get().isLoaded(TwilightDelight.MODID)) {
			map.put(TwilightDelight.MODID + "/cuisinedelight_config/" + NetworkManager.INGREDIENT.getID() + "/meat", IngredientConfig.build(
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


			map.put(TwilightDelight.MODID + "/cuisinedelight_config/" + NetworkManager.INGREDIENT.getID() + "/veges", IngredientConfig.build(
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
		}

		// ends delight
		if (ModList.get().isLoaded(EndsDelight.MOD_ID)) {
			map.put(EndsDelight.MOD_ID + "/cuisinedelight_config/" + NetworkManager.INGREDIENT.getID() + "/all", IngredientConfig.build(
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
		}
	}

}