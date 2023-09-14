package dev.xkmc.cuisinedelight.content.recipe;

import dev.xkmc.cuisinedelight.content.logic.FoodType;
import dev.xkmc.cuisinedelight.content.logic.IngredientConfig;
import dev.xkmc.cuisinedelight.init.CuisineDelight;
import dev.xkmc.cuisinedelight.init.data.NetworkManager;
import dev.xkmc.l2library.base.ingredients.BaseIngredient;
import dev.xkmc.l2library.serial.SerialClass;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

@SerialClass
public class FoodTypeIngredient extends BaseIngredient<FoodTypeIngredient> {

	public static final Serializer<FoodTypeIngredient> INSTANCE =
			new Serializer<>(FoodTypeIngredient.class, new ResourceLocation(CuisineDelight.MODID, "food_type"));

	@SerialClass.SerialField
	public FoodType foodType;

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
		IngredientConfig config = NetworkManager.INGREDIENT.getMerged();
		var entry = config.getEntry(stack);
		if (entry == null) return false;
		return entry.type == foodType;
	}

	@Override
	public Serializer<FoodTypeIngredient> getSerializer() {
		return INSTANCE;
	}

	@Override
	public ItemStack[] getItems() {
		return IngredientConfig.get().getAll(foodType);
	}

}
