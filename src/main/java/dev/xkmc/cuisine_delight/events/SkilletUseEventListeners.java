package dev.xkmc.cuisine_delight.events;

import dev.xkmc.cuisine_delight.content.item.CuisineSkilletItem;
import dev.xkmc.cuisine_delight.content.logic.IngredientConfig;
import dev.xkmc.cuisine_delight.init.CDItems;
import dev.xkmc.cuisine_delight.init.CuisineDelight;
import dev.xkmc.cuisine_delight.init.data.TagGen;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = CuisineDelight.MODID)
public class SkilletUseEventListeners {

	private static boolean noBlockUse(Player player) {
		return player.getMainHandItem().is(CDItems.SKILLET.get()) ||
				player.getOffhandItem().is(CDItems.SKILLET.get()) &&
						CuisineSkilletItem.canUse(player.getOffhandItem(), player, player.level) &&
						IngredientConfig.get().getEntry(player.getMainHandItem()) != null;
	}

	private static boolean useOffHand(Player player) {
		if (player.getOffhandItem().is(CDItems.SKILLET.get())) {
			return CuisineSkilletItem.canUse(player.getOffhandItem(), player, player.level) &&
					IngredientConfig.get().getEntry(player.getMainHandItem()) != null;
		}
		if (player.getMainHandItem().is(CDItems.SKILLET.get())) {
			return player.getOffhandItem().is(TagGen.UTENSILS);
		}
		return false;
	}

	@SubscribeEvent
	public static void onItemRightClick(PlayerInteractEvent.RightClickItem event) {
		if (event.getHand() == InteractionHand.MAIN_HAND && useOffHand(event.getEntity())) {
			event.setCancellationResult(InteractionResult.PASS);
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public static void onBlockRightClick(PlayerInteractEvent.RightClickBlock event) {
		if (noBlockUse(event.getEntity())) {
			event.setUseBlock(Event.Result.DENY);
			if (useOffHand(event.getEntity())) {
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public static void onEntityRightClick(PlayerInteractEvent.EntityInteract event) {
		if (noBlockUse(event.getEntity())) {
			event.setCanceled(true);
		}
	}

}
