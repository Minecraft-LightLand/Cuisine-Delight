package dev.xkmc.cuisine_delight.init;

import dev.xkmc.cuisine_delight.content.client.CookingOverlay;
import dev.xkmc.cuisine_delight.content.client.FoodItemDecorationRenderer;
import dev.xkmc.cuisine_delight.content.client.SkilletBEWLR;
import dev.xkmc.cuisine_delight.init.registrate.PlateFood;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD, modid = CuisineDelight.MODID)
public class CuisineDelightClient {

	public static final ModelResourceLocation SKILLET_MODEL = new ModelResourceLocation(
			new ResourceLocation(CuisineDelight.MODID, "cuisine_skillet_base"), "inventory");

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
	public static void onOverlayRegister(RegisterGuiOverlaysEvent event) {
		event.registerAbove(VanillaGuiOverlay.CROSSHAIR.id(), "cooking", new CookingOverlay());
	}


	@SubscribeEvent
	public static void onModelRegister(ModelEvent.RegisterAdditional event) {
		event.register(SKILLET_MODEL);
	}

	@SubscribeEvent
	public static void onModelBake(ModelEvent.BakingCompleted event) {
	}

}
