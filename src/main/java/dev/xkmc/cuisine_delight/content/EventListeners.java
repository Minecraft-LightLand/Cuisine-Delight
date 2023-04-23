package dev.xkmc.cuisine_delight.content;

import dev.xkmc.cuisine_delight.content.logic.IngredientConfig;
import dev.xkmc.cuisine_delight.init.CDItems;
import dev.xkmc.cuisine_delight.init.CuisineDelight;
import dev.xkmc.cuisine_delight.init.data.LangData;
import dev.xkmc.cuisine_delight.init.data.TagGen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = CuisineDelight.MODID)
public class EventListeners {

	private static boolean noBlockUse(Player player) {
		return player.getMainHandItem().is(CDItems.SKILLET.get()) ||
				player.getOffhandItem().is(CDItems.SKILLET.get()) &&
						IngredientConfig.get().getEntry(player.getMainHandItem()) != null;
	}

	private static boolean useOffHand(Player player) {
		if (player.getOffhandItem().is(CDItems.SKILLET.get())) {
			return IngredientConfig.get().getEntry(player.getMainHandItem()) != null;
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

	@SubscribeEvent
	public static void onTooltip(ItemTooltipEvent event) {
		ItemStack stack = event.getItemStack();
		if (stack.isEmpty()) return;
		var config = IngredientConfig.get().getEntry(stack);
		if (config == null) return;
		if (Screen.hasShiftDown()) {
			event.getToolTip().add(LangData.INFO_SIZE.get(config.size));
			event.getToolTip().add(LangData.INFO_NUTRITION.get(config.nutrition));
			event.getToolTip().add(LangData.INFO_MIN_TIME.get(config.min_time / 20));
			event.getToolTip().add(LangData.INFO_MAX_TIME.get(config.max_time / 20));
			event.getToolTip().add(LangData.INFO_STIR_TIME.get(config.stir_time / 20));
			event.getToolTip().add(LangData.INFO_RAW_PENALTY.get(Math.round(config.raw_penalty * 100)));
			event.getToolTip().add(LangData.INFO_OVERCOOK_PENALTY.get(Math.round(config.overcook_penalty * 100)));
			for (var e : config.effects) {
				MobEffectInstance mobeffectinstance = new MobEffectInstance(e.effect(), e.time());
				MutableComponent mutablecomponent = Component.translatable(mobeffectinstance.getDescriptionId());
				MobEffect mobeffect = mobeffectinstance.getEffect();
				if (mobeffectinstance.getDuration() > 20) {
					mutablecomponent = Component.translatable("potion.withDuration", mutablecomponent, MobEffectUtil.formatDuration(mobeffectinstance, 1));
				}
				event.getToolTip().add(mutablecomponent.withStyle(mobeffect.getCategory().getTooltipFormatting()));
			}
		} else {
			event.getToolTip().add(LangData.SHIFT.get());
		}
	}

}
