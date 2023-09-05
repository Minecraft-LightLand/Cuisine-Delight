package dev.xkmc.cuisinedelight.content.recipe;

import dev.xkmc.cuisinedelight.init.registrate.CDMisc;
import dev.xkmc.l2library.serial.SerialClass;
import net.minecraft.resources.ResourceLocation;

@SerialClass
public class PlateCuisineRecipe extends BaseCuisineRecipe<PlateCuisineRecipe> {

	public PlateCuisineRecipe(ResourceLocation id) {
		super(id, CDMisc.PLATE_CUISINE.get());
	}

}
