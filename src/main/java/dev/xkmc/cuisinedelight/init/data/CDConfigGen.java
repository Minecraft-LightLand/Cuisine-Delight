package dev.xkmc.cuisinedelight.init.data;

import dev.xkmc.cuisinedelight.compat.FoodCroptopia;
import dev.xkmc.cuisinedelight.compat.FoodDelight;
import dev.xkmc.cuisinedelight.content.logic.CookTransformConfig;
import dev.xkmc.cuisinedelight.content.logic.FoodType;
import dev.xkmc.cuisinedelight.content.logic.IngredientConfig;
import dev.xkmc.cuisinedelight.content.logic.transform.Stage;
import dev.xkmc.cuisinedelight.init.CuisineDelight;
import dev.xkmc.cuisinedelight.init.registrate.CDItems;
import dev.xkmc.l2core.serial.config.ConfigDataProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.Tags;
import vectorwing.farmersdelight.FarmersDelight;

import java.util.concurrent.CompletableFuture;

import static vectorwing.farmersdelight.common.registry.ModItems.*;

public class CDConfigGen extends ConfigDataProvider {

	public CDConfigGen(DataGenerator generator, CompletableFuture<HolderLookup.Provider> pvd) {
		super(generator, pvd, "Cuisine Delight Config");
	}

	@Override
	public void add(Collector map) {
		// vanilla
		{
			map.add(CuisineDelight.INGREDIENT, CuisineDelight.loc("meat"), IngredientConfig.build(
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

			map.add(CuisineDelight.INGREDIENT, CuisineDelight.loc("vege"), IngredientConfig.build(
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


			map.add(CuisineDelight.INGREDIENT, CuisineDelight.loc("misc"), IngredientConfig.build(
					IngredientConfig.get(Ingredient.of(Items.DANDELION), FoodType.NONE,
							60, 80, 40, 0, 0, 0, 0,
							new IngredientConfig.EffectEntry(MobEffects.SATURATION, 0, 7)),
					IngredientConfig.get(Ingredient.of(Items.SUGAR, Items.HONEY_BOTTLE), FoodType.NONE,
							0, 300, 80, 0, 0, 1, 1)
			));

			map.add(CuisineDelight.TRANSFORM, CuisineDelight.loc("meat"), new CookTransformConfig()
					.item(Items.MUTTON, Items.COOKED_MUTTON, Stage.COOKED)
					.item(Items.BEEF, Items.COOKED_BEEF, Stage.COOKED)
					.item(Items.PORKCHOP, Items.COOKED_PORKCHOP, Stage.COOKED)
					.item(Items.CHICKEN, Items.COOKED_CHICKEN, Stage.COOKED)
					.item(Items.RABBIT, Items.COOKED_RABBIT, Stage.COOKED)
					.item(Items.COD, Items.COOKED_COD, Stage.COOKED)
					.item(Items.SALMON, Items.COOKED_SALMON, Stage.COOKED)
			);

			map.add(CuisineDelight.TRANSFORM, CuisineDelight.loc("other"), new CookTransformConfig()
					.item(Items.POTATO, Items.BAKED_POTATO, Stage.COOKED)
					.item(Items.KELP, Items.DRIED_KELP, Stage.COOKED)
					.item(Items.EGG, CDItems.FRIED_EGG.get(), Stage.RAW)
			);

			map.add(CuisineDelight.TRANSFORM, CuisineDelight.loc("fluids"), new CookTransformConfig()
					.fluid(Items.HONEY_BOTTLE, 0xFFFED32E)
			);
		}
		// farmer's delight
		{
			map.add(CuisineDelight.INGREDIENT, ResourceLocation.fromNamespaceAndPath(FarmersDelight.MODID, "vege"), IngredientConfig.build(
					IngredientConfig.get(Ingredient.of(RICE.get(), RAW_PASTA.get(), WHEAT_DOUGH.get()), FoodType.CARB,
							300, 360, 80, 0.7f, 0.5f, 2, 8),
					IngredientConfig.get(Ingredient.of(TOMATO.get(), CABBAGE.get(), ONION.get(), PUMPKIN_SLICE.get()), FoodType.VEG,
							0, 360, 60, 0, 0.2f, 2, 5),
					IngredientConfig.get(Ingredient.of(CABBAGE_LEAF.get()), FoodType.VEG,
							0, 240, 60, 0, 0.2f, 1, 5),
					IngredientConfig.get(Ingredient.of(MILK_BOTTLE.get()), FoodType.NONE,
							0, 360, 60, 0, 0, 1, 1)
			));

			map.add(CuisineDelight.INGREDIENT, ResourceLocation.fromNamespaceAndPath(FarmersDelight.MODID, "meat"), IngredientConfig.build(
					IngredientConfig.get(Ingredient.of(HAM.get()), FoodType.MEAT,
							240, 360, 80, 0.5f, 0.5f, 3, 10),
					IngredientConfig.get(Ingredient.of(MINCED_BEEF.get(), MUTTON_CHOPS.get()), FoodType.MEAT,
							120, 240, 80, 0.5f, 0.5f, 2, 10),
					IngredientConfig.get(Ingredient.of(BACON.get()), FoodType.MEAT,
							120, 240, 80, 1f, 0.5f, 2, 10),
					IngredientConfig.get(Ingredient.of(CHICKEN_CUTS.get()), FoodType.MEAT,
							120, 240, 80, 1f, 0.5f, 1, 8),
					IngredientConfig.get(Ingredient.of(COD_SLICE.get(), SALMON_SLICE.get()), FoodType.SEAFOOD,
							60, 120, 40, 0.5f, 0.5f, 1, 12)
			));

			map.add(CuisineDelight.TRANSFORM, ResourceLocation.fromNamespaceAndPath(FarmersDelight.MODID, "meat"), new CookTransformConfig()
					.item(BACON.get(), COOKED_BACON.get(), Stage.COOKED)
					.item(CHICKEN_CUTS.get(), COOKED_CHICKEN_CUTS.get(), Stage.COOKED)
					.item(COD_SLICE.get(), COOKED_COD_SLICE.get(), Stage.COOKED)
					.item(SALMON_SLICE.get(), COOKED_SALMON_SLICE.get(), Stage.COOKED)
					.item(MINCED_BEEF.get(), BEEF_PATTY.get(), Stage.COOKED)
					.item(MUTTON_CHOPS.get(), COOKED_MUTTON_CHOPS.get(), Stage.COOKED)
					.item(HAM.get(), SMOKED_HAM.get(), Stage.COOKED)
			);

			map.add(CuisineDelight.TRANSFORM, ResourceLocation.fromNamespaceAndPath(FarmersDelight.MODID, "fluids"), new CookTransformConfig()
					.fluid(MILK_BOTTLE.get(), 0xFFFFFFFF)
			);

		}
		;
		//TODO FoodAlex.add(map);
		FoodDelight.add(map);
		FoodCroptopia.add(map);
	}

}