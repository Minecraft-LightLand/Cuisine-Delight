package dev.xkmc.cuisinedelight.init.registrate;

import dev.xkmc.cuisinedelight.content.recipe.BaseCuisineRecipe;
import dev.xkmc.cuisinedelight.content.recipe.CuisineRecipeContainer;
import dev.xkmc.cuisinedelight.content.recipe.FoodTypeIngredient;
import dev.xkmc.cuisinedelight.content.recipe.PlateCuisineRecipe;
import dev.xkmc.cuisinedelight.init.CuisineDelight;
import dev.xkmc.cuisinedelight.init.data.CopySkilletFunction;
import dev.xkmc.l2core.init.reg.simple.IngReg;
import dev.xkmc.l2core.init.reg.simple.IngVal;
import dev.xkmc.l2core.init.reg.simple.SR;
import dev.xkmc.l2core.init.reg.simple.Val;
import dev.xkmc.l2core.serial.recipe.BaseRecipe;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;

public class CDMisc {

	private static final IngReg ING_REG = IngReg.of(CuisineDelight.REG);
	public static final IngVal<FoodTypeIngredient> ING_FOOD_TYPE = ING_REG.reg("food_type", FoodTypeIngredient.class);

	private static final SR<LootItemFunctionType<?>> LIF = SR.of(CuisineDelight.REG, Registries.LOOT_FUNCTION_TYPE);
	public static Val<LootItemFunctionType<CopySkilletFunction>> LFT_COPY_SKILLET = LIF
			.reg("copy_skillet", () -> new LootItemFunctionType<>(CopySkilletFunction.CODEC));


	private static final SR<RecipeType<?>> RT = SR.of(CuisineDelight.REG, BuiltInRegistries.RECIPE_TYPE);
	private static final SR<RecipeSerializer<?>> RS = SR.of(CuisineDelight.REG, BuiltInRegistries.RECIPE_SERIALIZER);

	public static Val<RecipeType<BaseCuisineRecipe<?>>> RT_CUISINE = RT.reg("cuisine", RecipeType::simple);

	public static final Val<BaseRecipe.RecType<PlateCuisineRecipe, BaseCuisineRecipe<?>, CuisineRecipeContainer>> PLATE_CUISINE =
			RS.reg("plate_cuisine", () -> new BaseRecipe.RecType<>(PlateCuisineRecipe.class, RT_CUISINE));

	public static void register() {
	}

}
