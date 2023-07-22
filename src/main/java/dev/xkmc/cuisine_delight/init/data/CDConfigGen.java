package dev.xkmc.cuisine_delight.init.data;

import dev.xkmc.cuisine_delight.content.logic.FoodType;
import dev.xkmc.cuisine_delight.content.logic.IngredientConfig;
import dev.xkmc.cuisine_delight.init.CuisineDelight;
import dev.xkmc.l2library.serial.config.ConfigDataProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;
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
							0, 0, 80, 0, 0, 1, 1)
			));
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
							120, 240, 80, 1f, 0.5f, 2, 8),
					IngredientConfig.get(Ingredient.of(ModItems.COD_SLICE.get(), ModItems.SALMON_SLICE.get()), FoodType.SEAFOOD,
							60, 120, 40, 0.5f, 0.5f, 1, 12)
			));
		}
	}

}