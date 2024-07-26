package dev.xkmc.cuisinedelight.content.recipe;

import dev.xkmc.cuisinedelight.init.registrate.CDMisc;
import dev.xkmc.l2serial.serialization.marker.SerialClass;

@SerialClass
public class PlateCuisineRecipe extends BaseCuisineRecipe<PlateCuisineRecipe> {

	public PlateCuisineRecipe() {
		super(CDMisc.PLATE_CUISINE.get());
	}

}
