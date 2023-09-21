package dev.xkmc.cuisinedelight.compat;

import dev.xkmc.cuisinedelight.content.item.BaseFoodItem;
import dev.xkmc.cuisinedelight.content.recipe.BaseCuisineRecipe;
import dev.xkmc.cuisinedelight.content.recipe.CuisineRecipeMatch;
import dev.xkmc.cuisinedelight.content.recipe.FoodTypeIngredient;
import dev.xkmc.cuisinedelight.init.CuisineDelight;
import dev.xkmc.cuisinedelight.init.data.LangData;
import dev.xkmc.cuisinedelight.init.registrate.CDItems;
import dev.xkmc.l2library.serial.recipe.BaseRecipeCategory;
import dev.xkmc.l2serial.util.Wrappers;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.common.Constants;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class CuisineRecipeCategory extends BaseRecipeCategory<BaseCuisineRecipe<?>, CuisineRecipeCategory> {

	public CuisineRecipeCategory() {
		super(new ResourceLocation(CuisineDelight.MODID, "cuisine"), Wrappers.cast(BaseCuisineRecipe.class));
	}

	public CuisineRecipeCategory init(IGuiHelper guiHelper) {
		ResourceLocation location = Constants.RECIPE_GUI_VANILLA;
		background = guiHelper.createDrawable(location, 0, 60, 116, 54);
		icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, CDItems.SKILLET.asStack());
		return this;
	}

	@Override
	public Component getTitle() {
		return LangData.JEI_TITLE.get();
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, BaseCuisineRecipe<?> recipe, IFocusGroup focuses) {
		int index = 0;
		for (CuisineRecipeMatch e : recipe.list) {
			int x = index % 3;
			int y = index / 3;
			builder.addSlot(RecipeIngredientRole.INPUT, x * 18 + 1, y * 18 + 1)
					.addIngredients(e.ingredient()).addTooltipCallback((view, list) -> ingredientTooltip(e, view, list));
			index++;
		}
		builder.addSlot(RecipeIngredientRole.OUTPUT, 95, 19)
				.addItemStack(BaseFoodItem.setResultDisplay(recipe, recipe.holderItem.getDefaultInstance()));
	}

	private void ingredientTooltip(CuisineRecipeMatch ingredient, IRecipeSlotView view, List<Component> list) {
		if (ingredient.ingredient() instanceof FoodTypeIngredient type) {
			list.add(LangData.JEI_FOOD_TYPE.get(type.foodType.get().withStyle(ChatFormatting.GOLD)));
		}
		int min = (int) Math.round(ingredient.min() * 100);
		int max = (int) Math.round(Math.min(ingredient.max(), 1) * 100);
		String val = max >= 100 ? min + "%+" : min + "%-" + max + "%";
		list.add(LangData.JEI_INGREDIENT_AMOUNT.get(Component.literal(val).withStyle(ChatFormatting.AQUA)));
	}

}
