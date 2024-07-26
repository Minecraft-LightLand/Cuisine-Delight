package dev.xkmc.cuisinedelight.events;

import dev.xkmc.cuisinedelight.content.item.CuisineSkilletItem;
import dev.xkmc.cuisinedelight.content.logic.IngredientConfig;
import dev.xkmc.cuisinedelight.init.CuisineDelight;
import dev.xkmc.cuisinedelight.init.data.TagGen;
import dev.xkmc.cuisinedelight.init.registrate.CDItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.util.TriState;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.GAME, modid = CuisineDelight.MODID)
public class SkilletUseEventListeners {

	private static boolean noBlockUse(Player player) {
		return player.getMainHandItem().is(CDItems.SKILLET.get()) ||
				player.getOffhandItem().is(CDItems.SKILLET.get()) &&
						CuisineSkilletItem.canUse(player.getOffhandItem(), player, player.level()) &&
						IngredientConfig.get().getEntry(player.getMainHandItem()) != null;
	}

	private static boolean useOffHand(Player player) {
		if (player.getOffhandItem().is(CDItems.SKILLET.get())) {
			return CuisineSkilletItem.canUse(player.getOffhandItem(), player, player.level()) &&
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
			event.setUseBlock(TriState.FALSE);
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
