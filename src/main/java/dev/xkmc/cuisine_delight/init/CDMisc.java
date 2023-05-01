package dev.xkmc.cuisine_delight.init;

import dev.xkmc.cuisine_delight.init.data.CopySkilletFunction;
import net.minecraft.core.Registry;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class CDMisc {

	public static DeferredRegister<LootItemFunctionType> REGISTER_LOOT_ITEM_FUNC =
			DeferredRegister.create(Registry.LOOT_FUNCTION_REGISTRY, CuisineDelight.MODID);

	public static RegistryObject<LootItemFunctionType> LFT_COPY_SKILLET = REGISTER_LOOT_ITEM_FUNC
			.register("copy_skillet", () -> new LootItemFunctionType(new CopySkilletFunction.Serializer()));

	public static void register(IEventBus bus) {
		REGISTER_LOOT_ITEM_FUNC.register(bus);
	}

}
