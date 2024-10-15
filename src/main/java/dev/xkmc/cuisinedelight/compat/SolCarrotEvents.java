package dev.xkmc.cuisinedelight.compat;

import com.cazsius.solcarrot.api.SOLCarrotAPI;
import com.cazsius.solcarrot.tracking.FoodList;
import dev.xkmc.cuisinedelight.events.FoodEatenEvent;
import dev.xkmc.cuisinedelight.init.data.CDConfig;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class SolCarrotEvents {

	@SubscribeEvent
	public static void foodEaten(FoodEatenEvent event) {
		if (!CDConfig.COMMON.enableSoLCompat.get()) return;
		var cap = FoodList.get(event.player);
		boolean shouldUpdate = false;
		for (var ent : event.data.entries) {
			shouldUpdate |= cap.addFood(ent.getEatenStack().getItem());
		}
		if (shouldUpdate) {
			SOLCarrotAPI.syncFoodList(event.player);
		}
	}

}
