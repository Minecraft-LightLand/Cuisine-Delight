package dev.xkmc.cuisine_delight.init;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(CuisineDelight.MODID)
public class CuisineDelight {

	public static final String MODID = "cuisine_delight";
	public static final Logger LOGGER = LogManager.getLogger();

	private static void registerRegistrates(IEventBus bus) {
		CDItems.register();
	}

	private static void registerForgeEvents() {
		LWConfig.init();
	}

	private static void registerModBusEvents(IEventBus bus) {
		bus.addListener(CuisineDelight::setup);
		bus.addListener(CuisineDelight::modifyAttributes);
		bus.addListener(EventPriority.LOWEST, CuisineDelight::gatherData);
	}

	public CuisineDelight() {
		FMLJavaModLoadingContext ctx = FMLJavaModLoadingContext.get();
		IEventBus bus = ctx.getModEventBus();
		registerModBusEvents(bus);
		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> L2WeaponryClient.onCtorClient(bus, MinecraftForge.EVENT_BUS));
		registerRegistrates(bus);
		registerForgeEvents();
	}

	private static void setup(final FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
		});
	}

	private static void modifyAttributes(EntityAttributeModificationEvent event) {
	}

	public static void gatherData(GatherDataEvent event) {
	}

}
