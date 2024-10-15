package dev.xkmc.cuisinedelight.compat;

import com.illusivesoulworks.diet.platform.Services;
import dev.xkmc.cuisinedelight.events.FoodEatenEvent;
import dev.xkmc.cuisinedelight.init.data.CDConfig;
import net.minecraft.world.food.FoodProperties;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class DietEvents {

	@SubscribeEvent
	public static void foodEaten(FoodEatenEvent event) {
		if (!CDConfig.COMMON.enableDietCompat.get()) return;
		var cap = Services.CAPABILITY.get(event.player);
		if (cap.isEmpty()) return;
		FoodProperties prop = event.data.toFoodData();
		for (var ent : event.data.entries) {
			cap.get().consume(ent.getEatenStack(), prop.getNutrition(), prop.getSaturationModifier());
		}
	}

}
