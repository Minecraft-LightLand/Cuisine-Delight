package dev.xkmc.cuisine_delight.init;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.client.event.RegisterItemDecorationsEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class L2WeaponryClient {

	@OnlyIn(Dist.CLIENT)
	public static void onCtorClient(IEventBus bus, IEventBus eventBus) {
		bus.register(L2WeaponryClient.class);
	}

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void onParticleRegistryEvent(RegisterParticleProvidersEvent event) {

	}

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void clientSetup(FMLClientSetupEvent event) {
	}

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void registerItemDecoration(RegisterItemDecorationsEvent event) {
	}

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void onResourceReload(RegisterClientReloadListenersEvent event) {

	}

}
