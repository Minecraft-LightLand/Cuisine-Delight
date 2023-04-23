package dev.xkmc.cuisine_delight.init;

import dev.xkmc.cuisine_delight.content.logic.IngredientConfig;
import dev.xkmc.l2library.serial.config.ConfigMerger;
import dev.xkmc.l2library.serial.network.BaseConfig;
import dev.xkmc.l2library.serial.network.PacketHandlerWithConfig;
import net.minecraft.resources.ResourceLocation;

import java.util.Locale;

public enum NetworkManager {
	INGREDIENT;

	public String getID() {
		return name().toLowerCase(Locale.ROOT);
	}

	public static final PacketHandlerWithConfig HANDLER = new PacketHandlerWithConfig(
			new ResourceLocation(CuisineDelight.MODID, "main"), 1, "cuisine_config"
	);

	public <T extends BaseConfig> T getMerged() {
		return HANDLER.getCachedConfig(getID());
	}

	public static void register() {
		HANDLER.addCachedConfig(INGREDIENT.getID(), new ConfigMerger<>(IngredientConfig.class));
	}
}
