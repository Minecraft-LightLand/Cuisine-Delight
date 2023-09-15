package dev.xkmc.cuisinedelight.events;

import dev.xkmc.cuisinedelight.content.logic.CookedFoodData;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.Event;

public class FoodEatenEvent extends Event {

	public final Player player;
	public final CookedFoodData data;

	public FoodEatenEvent(Player player, CookedFoodData data) {
		this.player = player;
		this.data = data;
	}

}
