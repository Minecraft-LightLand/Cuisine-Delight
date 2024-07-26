package dev.xkmc.cuisinedelight.compat;

/*
import com.github.alexthe666.alexsmobs.AlexsMobs;
import com.github.alexthe666.alexsmobs.item.AMItemRegistry;
import com.ncpbails.alexsdelight.AlexsDelight;
import com.ncpbails.alexsdelight.item.ModItems;
import dev.xkmc.cuisinedelight.content.logic.CookTransformConfig;
import dev.xkmc.cuisinedelight.content.logic.FoodType;
import dev.xkmc.cuisinedelight.content.logic.IngredientConfig;
import dev.xkmc.cuisinedelight.content.logic.transform.Stage;
import dev.xkmc.cuisinedelight.init.CuisineDelight;
import dev.xkmc.l2library.serial.config.ConfigDataProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.fml.ModList;

public class FoodAlex {

	public static void add(ConfigDataProvider.Collector map) {
		if (ModList.get().isLoaded(AlexsMobs.MODID)) {
			map.add(CuisineDelight.INGREDIENT, new ResourceLocation(AlexsMobs.MODID, "all"), IngredientConfig.build(
					IngredientConfig.get(Ingredient.of(
									AMItemRegistry.MOOSE_RIBS.get(),
									AMItemRegistry.KANGAROO_MEAT.get()
							), FoodType.MEAT,
							240, 300, 60, 0.3f, 0.5f, 3, 10),
					IngredientConfig.get(Ingredient.of(
									AMItemRegistry.BLOBFISH.get(),
									AMItemRegistry.LOBSTER_TAIL.get()
							), FoodType.SEAFOOD,
							180, 240, 40, 0.3f, 0.5f, 3, 12),
					IngredientConfig.get(Ingredient.of(
									AMItemRegistry.RAW_CATFISH.get(),
									AMItemRegistry.FLYING_FISH.get(),
									AMItemRegistry.COSMIC_COD.get()), FoodType.SEAFOOD,
							180, 240, 40, 0.3f, 0.5f, 3, 12),
					IngredientConfig.get(Ingredient.of(AMItemRegistry.GONGYLIDIA.get()), FoodType.VEG,
							60, 240, 60, 0.3f, 0.3f, 1, 5),
					IngredientConfig.get(Ingredient.of(AMItemRegistry.RAINBOW_JELLY.get()), FoodType.NONE,
							60, 240, 60, 0.3f, 0.3f, 1, 5)
			));

			map.add(CuisineDelight.TRANSFORM, new ResourceLocation(AlexsMobs.MODID, "cook"), new CookTransformConfig()
					.item(AMItemRegistry.RAW_CATFISH.get(), AMItemRegistry.COOKED_CATFISH.get(), Stage.COOKED)
					.item(AMItemRegistry.LOBSTER_TAIL.get(), AMItemRegistry.COOKED_LOBSTER_TAIL.get(), Stage.COOKED)
					.item(AMItemRegistry.KANGAROO_MEAT.get(), AMItemRegistry.COOKED_KANGAROO_MEAT.get(), Stage.COOKED)
					.item(AMItemRegistry.MOOSE_RIBS.get(), AMItemRegistry.COOKED_MOOSE_RIBS.get(), Stage.COOKED)
			);
		}

		if (ModList.get().isLoaded(AlexsDelight.MOD_ID)) {

			map.add(CuisineDelight.INGREDIENT, new ResourceLocation(AlexsDelight.MOD_ID, "all"), IngredientConfig.build(
					IngredientConfig.get(Ingredient.of(
									ModItems.RAW_BISON.get(),
									ModItems.RAW_BUNFUNGUS.get()
							), FoodType.MEAT,
							240, 300, 60, 0.3f, 0.5f, 3, 10),
					IngredientConfig.get(Ingredient.of(
									ModItems.LOOSE_MOOSE_RIB.get(),
									ModItems.KANGAROO_SHANK.get(),
									ModItems.BISON_MINCE.get(),
									ModItems.RAW_BUNFUNGUS_DRUMSTICK.get(),
									AMItemRegistry.CENTIPEDE_LEG.get()
							), FoodType.MEAT,
							180, 240, 60, 0.3f, 0.5f, 2, 10),
					IngredientConfig.get(Ingredient.of(ModItems.RAW_CATFISH_SLICE.get()), FoodType.SEAFOOD,
							60, 180, 40, 0.5f, 0.5f, 2, 12)
			));

			map.add(CuisineDelight.TRANSFORM, new ResourceLocation(AlexsDelight.MOD_ID, "cook"), new CookTransformConfig()
					.item(ModItems.RAW_BISON.get(), ModItems.COOKED_BISON.get(), Stage.COOKED)
					.item(ModItems.RAW_BUNFUNGUS.get(), ModItems.COOKED_BUNFUNGUS.get(), Stage.COOKED)
					.item(ModItems.LOOSE_MOOSE_RIB.get(), ModItems.COOKED_LOOSE_MOOSE_RIB.get(), Stage.COOKED)
					.item(ModItems.KANGAROO_SHANK.get(), ModItems.COOKED_KANGAROO_SHANK.get(), Stage.COOKED)
					.item(ModItems.BISON_MINCE.get(), ModItems.BISON_PATTY.get(), Stage.COOKED)
					.item(ModItems.RAW_BUNFUNGUS_DRUMSTICK.get(), ModItems.COOKED_BUNFUNGUS_DRUMSTICK.get(), Stage.COOKED)
			);
		}
	}

}
*/