package dev.xkmc.cuisine_delight.content.recipe;

import dev.xkmc.cuisine_delight.init.CDMisc;
import dev.xkmc.l2library.serial.SerialClass;
import net.minecraft.resources.ResourceLocation;

@SerialClass
public class PlateCuisineRecipe extends BaseCuisineRecipe<PlateCuisineRecipe> {

	public PlateCuisineRecipe(ResourceLocation id) {
		super(id, CDMisc.PLATE_CUISINE.get());
	}

}
