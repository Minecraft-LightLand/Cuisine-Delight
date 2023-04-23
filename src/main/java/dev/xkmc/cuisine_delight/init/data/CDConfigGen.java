package dev.xkmc.cuisine_delight.init.data;

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
				IngredientConfig.get(Ingredient.of(Items.CHICKEN, Items.PORKCHOP, Items.RABBIT),
						180, 240, 80, 1f, 0.5f, 2, 10),
				IngredientConfig.get(Ingredient.of(Items.MUTTON, Items.BEEF),
						120, 180, 80, 0.5f, 0.5f, 2, 10),
				IngredientConfig.get(Ingredient.of(Items.COD, Items.SALMON),
						60, 120, 80, 0.5f, 0.5f, 1, 15),
				IngredientConfig.get(Ingredient.of(Items.POTATO, Items.BEETROOT, Items.CARROT),
						240, 360, 60, 0.5f, 0.2f, 1, 5),
				IngredientConfig.get(Ingredient.of(Items.GOLDEN_CARROT),
						240, 360, 60, 0.5f, 0.2f, 1, 25),
				IngredientConfig.get(Ingredient.of(Items.DANDELION),
						60, 80, 40, 0, 0, 1, 0,
						new IngredientConfig.EffectEntry(MobEffects.SATURATION, 7)),
				IngredientConfig.get(Ingredient.of(Items.GOLDEN_APPLE),
						120, 140, 40, 0, 0, 1, 5,
						new IngredientConfig.EffectEntry(MobEffects.REGENERATION, 200),
						new IngredientConfig.EffectEntry(MobEffects.SATURATION, 8)
				),
				IngredientConfig.get(Ingredient.of(Items.ENCHANTED_GOLDEN_APPLE),
						120, 140, 40, 0, 0, 1, 5,
						new IngredientConfig.EffectEntry(MobEffects.REGENERATION, 800),
						new IngredientConfig.EffectEntry(MobEffects.DAMAGE_RESISTANCE, 6000),
						new IngredientConfig.EffectEntry(MobEffects.FIRE_RESISTANCE, 6000),
						new IngredientConfig.EffectEntry(MobEffects.SATURATION, 32)
				)
		));
	}

}