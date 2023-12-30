package dev.xkmc.cuisinedelight.init.data;

import dev.xkmc.cuisinedelight.content.logic.CookTransformConfig;
import dev.xkmc.cuisinedelight.content.logic.IngredientConfig;
import dev.xkmc.cuisinedelight.init.CuisineDelight;
import dev.xkmc.l2library.serial.config.ConfigMerger;
import dev.xkmc.l2library.serial.network.BaseConfig;

import java.util.Locale;

public enum NetworkManager {
	INGREDIENT, TRANSFORM;

	public String getID() {
		return name().toLowerCase(Locale.ROOT);
	}

	public <T extends BaseConfig> T getMerged() {
		return CuisineDelight.HANDLER.getCachedConfig(getID());
	}

	public static void register() {
		CuisineDelight.HANDLER.addCachedConfig(INGREDIENT.getID(), new ConfigMerger<>(IngredientConfig.class));
		CuisineDelight.HANDLER.addCachedConfig(TRANSFORM.getID(), new ConfigMerger<>(CookTransformConfig.class));
	}
}
