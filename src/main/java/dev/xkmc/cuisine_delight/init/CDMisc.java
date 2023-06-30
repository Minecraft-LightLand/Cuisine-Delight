package dev.xkmc.cuisine_delight.init;

import dev.xkmc.cuisine_delight.content.recipe.BaseCuisineRecipe;
import dev.xkmc.cuisine_delight.content.recipe.CuisineRecipeContainer;
import dev.xkmc.cuisine_delight.content.recipe.PlateCuisineRecipe;
import dev.xkmc.cuisine_delight.init.data.CopySkilletFunction;
import dev.xkmc.l2library.base.recipe.BaseRecipe;
import dev.xkmc.l2library.repack.registrate.util.entry.RegistryEntry;
import dev.xkmc.l2library.repack.registrate.util.nullness.NonNullSupplier;
import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CDMisc {

	public static DeferredRegister<LootItemFunctionType> REGISTER_LOOT_ITEM_FUNC =
			DeferredRegister.create(Registry.LOOT_FUNCTION_REGISTRY, CuisineDelight.MODID);

	public static DeferredRegister<RecipeType<?>> REGISTER_RECIPE_TYPE =
			DeferredRegister.create(Registry.RECIPE_TYPE_REGISTRY, CuisineDelight.MODID);

	public static RegistryObject<LootItemFunctionType> LFT_COPY_SKILLET = REGISTER_LOOT_ITEM_FUNC
			.register("copy_skillet", () -> new LootItemFunctionType(new CopySkilletFunction.Serializer()));

	public static RegistryObject<RecipeType<BaseCuisineRecipe<?>>> RT_CUISINE = CuisineDelight.REGISTRATE
			.recipe(REGISTER_RECIPE_TYPE, "cuisine");

	public static RegistryEntry<BaseRecipe.RecType<PlateCuisineRecipe, BaseCuisineRecipe<?>, CuisineRecipeContainer>> PLATE_CUISINE =
			reg("plate_cuisine", () -> new BaseRecipe.RecType<>(PlateCuisineRecipe.class, RT_CUISINE));

	private static <A extends RecipeSerializer<?>> RegistryEntry<A> reg(String id, NonNullSupplier<A> sup) {
		return CuisineDelight.REGISTRATE.simple(id, ForgeRegistries.Keys.RECIPE_SERIALIZERS, sup);
	}

	public static void register(IEventBus bus) {
		REGISTER_LOOT_ITEM_FUNC.register(bus);
		REGISTER_RECIPE_TYPE.register(bus);
	}

}
