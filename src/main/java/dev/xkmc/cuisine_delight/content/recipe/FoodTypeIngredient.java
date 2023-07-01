package dev.xkmc.cuisine_delight.content.recipe;

import dev.xkmc.cuisine_delight.content.logic.FoodType;
import dev.xkmc.cuisine_delight.content.logic.IngredientConfig;
import dev.xkmc.cuisine_delight.init.CuisineDelight;
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
	protected FoodTypeIngredient() {
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
