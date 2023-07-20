package dev.xkmc.cuisine_delight.init;

import com.tterrag.registrate.providers.ProviderType;
import dev.xkmc.cuisine_delight.content.logic.IngredientConfig;
import dev.xkmc.cuisine_delight.init.data.CDConfig;
import dev.xkmc.cuisine_delight.init.data.CDConfigGen;
import dev.xkmc.cuisine_delight.init.data.LangData;
import dev.xkmc.cuisine_delight.init.data.RecipeGen;
import dev.xkmc.cuisine_delight.init.registrate.CDBlocks;
import dev.xkmc.cuisine_delight.init.registrate.CDItems;
import dev.xkmc.cuisine_delight.init.registrate.CDMisc;
import dev.xkmc.l2library.base.L2Registrate;
import dev.xkmc.l2library.serial.config.ConfigTypeEntry;
import dev.xkmc.l2library.serial.config.PacketHandlerWithConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(CuisineDelight.MODID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = CuisineDelight.MODID)
public class CuisineDelight {

	public static final String MODID = "cuisine_delight";
	public static final Logger LOGGER = LogManager.getLogger();
	public static final L2Registrate REGISTRATE = new L2Registrate(MODID);

	public static final PacketHandlerWithConfig HANDLER = new PacketHandlerWithConfig(
			new ResourceLocation(CuisineDelight.MODID, "main"), 1
	);

	public static final ConfigTypeEntry<IngredientConfig> INGREDIENT =
			new ConfigTypeEntry<>(HANDLER, "ingredient", IngredientConfig.class);

	public CuisineDelight() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		CDItems.register();
		CDBlocks.register();
		CDMisc.register(bus);
		CDConfig.init();
		REGISTRATE.addDataGenerator(ProviderType.RECIPE, RecipeGen::genRecipe);
		REGISTRATE.addDataGenerator(ProviderType.LANG, LangData::genLang);
	}

	@SubscribeEvent
	public static void setup(final FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
		});
	}

	@SubscribeEvent
	public static void modifyAttributes(EntityAttributeModificationEvent event) {
	}

	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		event.getGenerator().addProvider(event.includeServer(), new CDConfigGen(event.getGenerator()));
	}

}
