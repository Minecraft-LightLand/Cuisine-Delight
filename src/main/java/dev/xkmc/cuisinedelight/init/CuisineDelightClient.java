package dev.xkmc.cuisinedelight.init;

import dev.xkmc.cuisinedelight.content.client.CookingOverlay;
import dev.xkmc.cuisinedelight.content.client.FoodItemDecorationRenderer;
import dev.xkmc.cuisinedelight.content.client.SkilletBEWLR;
import dev.xkmc.cuisinedelight.init.registrate.PlateFood;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

@EventBusSubscriber(value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD, modid = CuisineDelight.MODID)
public class CuisineDelightClient {

	public static final ModelResourceLocation SKILLET_MODEL = new ModelResourceLocation(
			CuisineDelight.loc("item/cuisine_skillet_base"), "standalone");

	@SubscribeEvent
	public static void onParticleRegistryEvent(RegisterParticleProvidersEvent event) {

	}

	@SubscribeEvent
	public static void clientSetup(FMLClientSetupEvent event) {
	}

	@SubscribeEvent
	public static void registerItemDecoration(RegisterItemDecorationsEvent event) {
		for (var e : PlateFood.values()) {
			event.register(e.item.get(), new FoodItemDecorationRenderer());
		}
	}

	@SubscribeEvent
	public static void onResourceReload(RegisterClientReloadListenersEvent event) {
		event.registerReloadListener(SkilletBEWLR.INSTANCE.get());
	}

	@SubscribeEvent
	public static void onOverlayRegister(RegisterGuiLayersEvent event) {
		event.registerAbove(VanillaGuiLayers.CROSSHAIR, CuisineDelight.loc("cooking"), new CookingOverlay());
	}


	@SubscribeEvent
	public static void onModelRegister(ModelEvent.RegisterAdditional event) {
		event.register(SKILLET_MODEL);
	}

	@SubscribeEvent
	public static void onModelBake(ModelEvent.BakingCompleted event) {
	}

}
