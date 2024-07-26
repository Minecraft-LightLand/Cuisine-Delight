package dev.xkmc.cuisinedelight.init;

import com.tterrag.registrate.providers.ProviderType;
import dev.xkmc.cuisinedelight.content.logic.CookTransformConfig;
import dev.xkmc.cuisinedelight.content.logic.IngredientConfig;
import dev.xkmc.cuisinedelight.init.data.CDConfig;
import dev.xkmc.cuisinedelight.init.data.CDConfigGen;
import dev.xkmc.cuisinedelight.init.data.LangData;
import dev.xkmc.cuisinedelight.init.data.RecipeGen;
import dev.xkmc.cuisinedelight.init.registrate.CDBlocks;
import dev.xkmc.cuisinedelight.init.registrate.CDItems;
import dev.xkmc.cuisinedelight.init.registrate.CDMisc;
import dev.xkmc.l2core.init.reg.registrate.L2Registrate;
import dev.xkmc.l2core.init.reg.simple.Reg;
import dev.xkmc.l2core.serial.config.ConfigTypeEntry;
import dev.xkmc.l2core.serial.config.PacketHandlerWithConfig;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.data.loading.DatagenModLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vectorwing.farmersdelight.FarmersDelight;
import vectorwing.farmersdelight.common.event.CommonModBusEvents;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(CuisineDelight.MODID)
@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = CuisineDelight.MODID)
public class CuisineDelight {

	public static final String MODID = "cuisinedelight";
	public static final Logger LOGGER = LogManager.getLogger();
	public static final Reg REG = new Reg(MODID);
	public static final L2Registrate REGISTRATE = new L2Registrate(MODID);

	public static final PacketHandlerWithConfig HANDLER = new PacketHandlerWithConfig(MODID, 1);

	public static final ConfigTypeEntry<IngredientConfig> INGREDIENT =
			new ConfigTypeEntry<>(HANDLER, "ingredient", IngredientConfig.class);
	public static final ConfigTypeEntry<CookTransformConfig> TRANSFORM =
			new ConfigTypeEntry<>(HANDLER, "transform", CookTransformConfig.class);

	public CuisineDelight() {
		CDItems.register();
		CDBlocks.register();
		CDMisc.register();
		CDConfig.init();
		REGISTRATE.addDataGenerator(ProviderType.RECIPE, RecipeGen::genRecipe);
		REGISTRATE.addDataGenerator(ProviderType.LANG, LangData::genLang);
		//TODO if (ModList.get().isLoaded(SOLCarrot.MOD_ID)) MinecraftForge.EVENT_BUS.register(SolCarrotEvents.class);
		//TODO if (ModList.get().isLoaded(SOLSweetPotato.MOD_ID)) MinecraftForge.EVENT_BUS.register(SolApplePieEvents.class);
		//TODO if (ModList.get().isLoaded("diet")) MinecraftForge.EVENT_BUS.register(DietEvents.class);
		if (DatagenModLoader.isRunningDataGen()){
			ModList.get().getModContainerById(FarmersDelight.MODID)
					.orElseThrow().getEventBus().unregister(CommonModBusEvents.class);
		}
	}

	public static ResourceLocation loc(String id) {
		return ResourceLocation.fromNamespaceAndPath(MODID, id);
	}

	@SubscribeEvent
	public static void setup(final FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
		});
	}

	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		event.getGenerator().addProvider(event.includeServer(), new CDConfigGen(event.getGenerator(), event.getLookupProvider()));
	}

}
