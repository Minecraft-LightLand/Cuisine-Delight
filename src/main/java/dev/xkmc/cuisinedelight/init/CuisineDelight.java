package dev.xkmc.cuisinedelight.init;

import com.cazsius.solcarrot.SOLCarrot;
import com.tarinoita.solsweetpotato.SOLSweetPotato;
import com.tterrag.registrate.providers.ProviderType;
import dev.xkmc.cuisinedelight.compat.DietEvents;
import dev.xkmc.cuisinedelight.compat.SolApplePieEvents;
import dev.xkmc.cuisinedelight.compat.SolCarrotEvents;
import dev.xkmc.cuisinedelight.content.logic.IngredientConfig;
import dev.xkmc.cuisinedelight.content.recipe.FoodTypeIngredient;
import dev.xkmc.cuisinedelight.init.data.CDConfig;
import dev.xkmc.cuisinedelight.init.data.CDConfigGen;
import dev.xkmc.cuisinedelight.init.data.LangData;
import dev.xkmc.cuisinedelight.init.data.RecipeGen;
import dev.xkmc.cuisinedelight.init.registrate.CDBlocks;
import dev.xkmc.cuisinedelight.init.registrate.CDItems;
import dev.xkmc.cuisinedelight.init.registrate.CDMisc;
import dev.xkmc.l2library.base.L2Registrate;
import dev.xkmc.l2library.serial.config.ConfigTypeEntry;
import dev.xkmc.l2library.serial.config.PacketHandlerWithConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(CuisineDelight.MODID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = CuisineDelight.MODID)
public class CuisineDelight {

	public static final String MODID = "cuisinedelight";
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
		if (ModList.get().isLoaded(SOLCarrot.MOD_ID)) MinecraftForge.EVENT_BUS.register(SolCarrotEvents.class);
		if (ModList.get().isLoaded(SOLSweetPotato.MOD_ID)) MinecraftForge.EVENT_BUS.register(SolApplePieEvents.class);
		if (ModList.get().isLoaded("diet")) MinecraftForge.EVENT_BUS.register(DietEvents.class);

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

	@SubscribeEvent
	public static void registerRecipeSerializers(RegisterEvent event) {
		if (event.getRegistryKey().equals(ForgeRegistries.Keys.RECIPE_SERIALIZERS)) {
			CraftingHelper.register(FoodTypeIngredient.INSTANCE.id(), FoodTypeIngredient.INSTANCE);
		}
	}

}
