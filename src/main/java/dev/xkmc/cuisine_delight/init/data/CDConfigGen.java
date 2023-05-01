package dev.xkmc.cuisine_delight.init.data;

import dev.xkmc.cuisine_delight.content.logic.FoodType;
import dev.xkmc.cuisine_delight.content.logic.IngredientConfig;
import dev.xkmc.cuisine_delight.init.CuisineDelight;
import dev.xkmc.cuisine_delight.init.NetworkManager;
import dev.xkmc.l2library.serial.network.BaseConfig;
import dev.xkmc.l2library.serial.network.ConfigDataProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.Map;

public class CDConfigGen extends ConfigDataProvider {

	public CDConfigGen(DataGenerator generator) {
		super(generator, "data/" + CuisineDelight.MODID + "/cuisine_config/", "Cuisine Delight Config");
	}

	@Override
	public void add(Map<String, BaseConfig> map) {
		map.put(NetworkManager.INGREDIENT.getID() + "/default", IngredientConfig.build(
				IngredientConfig.get(Ingredient.of(Items.CHICKEN, Items.PORKCHOP, Items.RABBIT), FoodType.MEAT,
						180, 240, 80, 1f, 0.5f, 3, 10),
				IngredientConfig.get(Ingredient.of(Items.MUTTON, Items.BEEF), FoodType.MEAT,
						120, 180, 80, 0.5f, 0.5f, 3, 10),
				IngredientConfig.get(Ingredient.of(Items.COD, Items.SALMON), FoodType.MEAT,
						60, 120, 80, 0.5f, 0.5f, 2, 15),
				IngredientConfig.get(Ingredient.of(Items.POTATO), FoodType.CARB,
						240, 360, 60, 0.5f, 0.2f, 2, 5),
				IngredientConfig.get(Ingredient.of(Items.BEETROOT, Items.CARROT), FoodType.VEG,
						240, 360, 60, 0.5f, 0.2f, 1, 5),
				IngredientConfig.get(Ingredient.of(Items.GOLDEN_CARROT), FoodType.VEG,
						240, 360, 60, 0.5f, 0.2f, 1, 25),
				IngredientConfig.get(Ingredient.of(Items.DANDELION), FoodType.NONE,
						60, 80, 40, 0, 0, 0, 0,
						new IngredientConfig.EffectEntry(MobEffects.SATURATION, 0, 7)),
				IngredientConfig.get(Ingredient.of(Items.GOLDEN_APPLE), FoodType.VEG,
						120, 140, 40, 0, 0, 1, 5,
						new IngredientConfig.EffectEntry(MobEffects.REGENERATION, 1, 100),
						new IngredientConfig.EffectEntry(MobEffects.SATURATION, 0, 8)
				),
				IngredientConfig.get(Ingredient.of(Items.ENCHANTED_GOLDEN_APPLE), FoodType.VEG,
						120, 140, 40, 0, 0, 1, 5,
						new IngredientConfig.EffectEntry(MobEffects.REGENERATION, 1, 400),
						new IngredientConfig.EffectEntry(MobEffects.DAMAGE_RESISTANCE, 0, 6000),
						new IngredientConfig.EffectEntry(MobEffects.FIRE_RESISTANCE, 0, 6000),
						new IngredientConfig.EffectEntry(MobEffects.SATURATION, 0, 32)
				)
		));
	}

}