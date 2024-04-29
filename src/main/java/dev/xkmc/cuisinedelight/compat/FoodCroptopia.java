package dev.xkmc.cuisinedelight.compat;

import com.epherical.croptopia.register.Content;
import dev.xkmc.cuisinedelight.content.logic.CookTransformConfig;
import dev.xkmc.cuisinedelight.content.logic.FoodType;
import dev.xkmc.cuisinedelight.content.logic.IngredientConfig;
import dev.xkmc.cuisinedelight.content.logic.transform.Stage;
import dev.xkmc.cuisinedelight.init.CuisineDelight;
import dev.xkmc.l2library.serial.config.ConfigDataProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.fml.ModList;
import vectorwing.farmersdelight.FarmersDelight;

public class FoodCroptopia {

	public static void add(ConfigDataProvider.Collector map) {

		// croptopia
		if (ModList.get().isLoaded("croptopia")) {
			map.add(CuisineDelight.INGREDIENT, new ResourceLocation("croptopia", "all"), IngredientConfig.build(
					IngredientConfig.get(Ingredient.of(
									Content.CELERY, Content.CHILE_PEPPER, Content.GREENONION,
									Content.GINGER, Content.GARLIC, Content.ONION
							), FoodType.VEG,
							60, 300, 60, 0.2f, 0.3f, 1, 3),
					IngredientConfig.get(Ingredient.of(
									Content.SPINACH, Content.CABBAGE, Content.CAULIFLOWER, Content.BROCCOLI, Content.GREENBEAN,
									Content.LETTUCE, Content.LEEK, Content.KALE, Content.BASIL, Content.ASPARAGUS, Content.ARTICHOKE,
									Content.CUCUMBER, Content.EGGPLANT, Content.ZUCCHINI, Content.BELLPEPPER, Content.SQUASH,
									Content.TOMATO, Content.TOMATILLO, Content.RUTABAGA, Content.RADISH, Content.TURNIP,
									Content.SEA_LETTUCE
							), FoodType.VEG,
							120, 240, 60, 0.2f, 0.3f, 2, 8),
					IngredientConfig.get(Ingredient.of(
									Content.YAM, Content.SWEETPOTATO, Content.RICE, Content.BLACKBEAN, Content.CORN, Content.BARLEY,
									Content.PEANUT, Content.AVOCADO
							), FoodType.CARB,
							180, 300, 60, 0.2f, 0.3f, 2, 8),
					IngredientConfig.get(Ingredient.of(
									Content.CLAM, Content.CRAB, Content.CALAMARI, Content.GLOWING_CALAMARI,
									Content.ANCHOVY, Content.OYSTER, Content.ROE, Content.SHRIMP
							), FoodType.SEAFOOD,
							120, 180, 40, 0.2f, 0.5f, 3, 12),
					IngredientConfig.get(Ingredient.of(Content.TUNA), FoodType.CARB,
							120, 180, 40, 0.2f, 0.5f, 5, 12),
					IngredientConfig.get(Ingredient.of(Content.RAW_BACON, Content.FROG_LEGS), FoodType.MEAT,
							120, 240, 80, 1f, 0.5f, 2, 10)
			));
			map.add(CuisineDelight.TRANSFORM, new ResourceLocation("croptopia", "cook"), new CookTransformConfig()
					.item(Content.FROG_LEGS, Content.FRIED_FROG_LEGS, Stage.COOKED)
					.item(Content.CLAM.asItem(), Content.STEAMED_CLAMS, Stage.COOKED)
					.item(Content.SHRIMP.asItem(), Content.COOKED_SHRIMP.asItem(), Stage.COOKED)
					.item(Content.TUNA.asItem(), Content.COOKED_TUNA.asItem(), Stage.COOKED)
					.item(Content.ANCHOVY.asItem(), Content.COOKED_ANCHOVY.asItem(), Stage.COOKED)
					.item(Content.RAW_BACON.asItem(), Content.COOKED_BACON.asItem(), Stage.COOKED)
			);
		}

	}
}
