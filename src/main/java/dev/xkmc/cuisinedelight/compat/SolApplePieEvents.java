package dev.xkmc.cuisinedelight.compat;

import com.tarinoita.solsweetpotato.api.SOLSweetPotatoAPI;
import com.tarinoita.solsweetpotato.tracking.FoodList;
import dev.xkmc.cuisinedelight.events.FoodEatenEvent;
import dev.xkmc.cuisinedelight.init.data.CDConfig;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class SolApplePieEvents {

	@SubscribeEvent
	public static void foodEaten(FoodEatenEvent event) {
		if (!CDConfig.COMMON.enableSoLCompat.get()) return;
		var cap = FoodList.get(event.player);
		boolean shouldUpdate = false;
		for (var ent : event.data.entries) {
			cap.addFood(ent.getEatenStack().getItem());
		}
		SOLSweetPotatoAPI.syncFoodList(event.player);
	}

}
