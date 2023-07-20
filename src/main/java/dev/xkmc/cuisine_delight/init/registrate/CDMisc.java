package dev.xkmc.cuisine_delight.init.registrate;

import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import dev.xkmc.cuisine_delight.content.recipe.BaseCuisineRecipe;
import dev.xkmc.cuisine_delight.content.recipe.CuisineRecipeContainer;
import dev.xkmc.cuisine_delight.content.recipe.PlateCuisineRecipe;
import dev.xkmc.cuisine_delight.init.CuisineDelight;
import dev.xkmc.cuisine_delight.init.data.CopySkilletFunction;
import dev.xkmc.l2library.serial.recipe.BaseRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CDMisc {

	public static DeferredRegister<LootItemFunctionType> REGISTER_LOOT_ITEM_FUNC =
			DeferredRegister.create(Registries.LOOT_FUNCTION_TYPE, CuisineDelight.MODID);

	public static RegistryObject<LootItemFunctionType> LFT_COPY_SKILLET = REGISTER_LOOT_ITEM_FUNC
			.register("copy_skillet", () -> new LootItemFunctionType(new CopySkilletFunction.Serializer()));

	public static RegistryEntry<RecipeType<BaseCuisineRecipe<?>>> RT_CUISINE = CuisineDelight.REGISTRATE
			.recipe("cuisine");

	public static RegistryEntry<BaseRecipe.RecType<PlateCuisineRecipe, BaseCuisineRecipe<?>, CuisineRecipeContainer>> PLATE_CUISINE =
			reg("plate_cuisine", () -> new BaseRecipe.RecType<>(PlateCuisineRecipe.class, RT_CUISINE));

	private static <A extends RecipeSerializer<?>> RegistryEntry<A> reg(String id, NonNullSupplier<A> sup) {
		return CuisineDelight.REGISTRATE.simple(id, ForgeRegistries.Keys.RECIPE_SERIALIZERS, sup);
	}

	public static void register(IEventBus bus) {
		REGISTER_LOOT_ITEM_FUNC.register(bus);
	}

}
