package dev.xkmc.cuisinedelight.content.recipe;

import dev.xkmc.cuisinedelight.content.logic.FoodType;
import dev.xkmc.cuisinedelight.content.logic.IngredientConfig;
import dev.xkmc.cuisinedelight.init.CuisineDelight;
import dev.xkmc.l2library.serial.ingredients.BaseIngredient;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

@SerialClass
public class FoodTypeIngredient extends BaseIngredient<FoodTypeIngredient> {

	public static final Serializer<FoodTypeIngredient> INSTANCE =
			new Serializer<>(FoodTypeIngredient.class, new ResourceLocation(CuisineDelight.MODID, "food_type"));

	@SerialClass.SerialField
	private FoodType foodType;

	/**
	 * @deprecated
	 */
	@Deprecated
	public FoodTypeIngredient() {
	}

	public FoodTypeIngredient(FoodType foodType) {
		super(foodType.getDisplay());
		this.foodType = foodType;
	}

	@Override
	protected FoodTypeIngredient validate() {
		return new FoodTypeIngredient(foodType);
	}

	@Override
	public boolean test(ItemStack stack) {
		IngredientConfig config = CuisineDelight.INGREDIENT.getMerged();
		var entry = config.getEntry(stack);
		if (entry == null) return false;
		return entry.type == foodType;
	}

	@Override
	public Serializer<FoodTypeIngredient> getSerializer() {
		return INSTANCE;
	}
}
