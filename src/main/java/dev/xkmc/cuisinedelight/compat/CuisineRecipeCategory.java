package dev.xkmc.cuisinedelight.compat;

import dev.xkmc.cuisinedelight.content.item.BaseFoodItem;
import dev.xkmc.cuisinedelight.content.recipe.BaseCuisineRecipe;
import dev.xkmc.cuisinedelight.content.recipe.CuisineRecipeMatch;
import dev.xkmc.cuisinedelight.content.recipe.FoodTypeIngredient;
import dev.xkmc.cuisinedelight.init.CuisineDelight;
import dev.xkmc.cuisinedelight.init.data.LangData;
import dev.xkmc.cuisinedelight.init.registrate.CDItems;
import dev.xkmc.l2core.compat.jei.BaseRecipeCategory;
import dev.xkmc.l2serial.util.Wrappers;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotView;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;

import java.util.function.Consumer;

public class CuisineRecipeCategory extends BaseRecipeCategory<BaseCuisineRecipe<?>, CuisineRecipeCategory> {

	private IGuiHelper guiHelper;

	public CuisineRecipeCategory() {
		super(CuisineDelight.loc("cuisine"), Wrappers.cast(BaseCuisineRecipe.class));
	}

	public CuisineRecipeCategory init(IGuiHelper guiHelper) {
		background = guiHelper.createBlankDrawable(116, 54);
		icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, CDItems.SKILLET.asStack());
		this.guiHelper = guiHelper;
		return this;
	}

	@Override
	public Component getTitle() {
		return LangData.JEI_TITLE.get();
	}

	@Override
	public void draw(BaseCuisineRecipe<?> recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
		int recipeWidth = this.getWidth();
		int recipeHeight = this.getHeight();
		IDrawableStatic recipeArrow = this.guiHelper.getRecipeArrow();
		recipeArrow.draw(guiGraphics, 61, (54 - recipeArrow.getHeight()) / 2);
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, BaseCuisineRecipe<?> recipe, IFocusGroup focuses) {
		for (int index = 0; index < 9; index++) {
			int x = index % 3;
			int y = index / 3;
			var b = builder.addSlot(RecipeIngredientRole.INPUT, x * 18 + 1, y * 18 + 1).setStandardSlotBackground();
			if (index < recipe.list.size()) {
				CuisineRecipeMatch e = recipe.list.get(index);
				b.addIngredients(e.ingredient()).addRichTooltipCallback((view, t) -> ingredientTooltip(e, view, t::add));
			}
		}
		builder.addSlot(RecipeIngredientRole.OUTPUT, 95, 19).setOutputSlotBackground()
				.addItemStack(BaseFoodItem.setResultDisplay(recipe, recipe.holderItem.getDefaultInstance()));
	}

	private void ingredientTooltip(CuisineRecipeMatch ingredient, IRecipeSlotView view, Consumer<FormattedText> list) {
		if (ingredient.ingredient().getCustomIngredient() instanceof FoodTypeIngredient type) {
			list.accept(LangData.JEI_FOOD_TYPE.get(type.foodType().get().withStyle(ChatFormatting.GOLD)));
		}
		int min = (int) Math.round(ingredient.min() * 100);
		int max = (int) Math.round(Math.min(ingredient.max(), 1) * 100);
		String val = max >= 100 ? min + "%+" : min + "%-" + max + "%";
		list.accept(LangData.JEI_INGREDIENT_AMOUNT.get(Component.literal(val).withStyle(ChatFormatting.AQUA)));
	}

}
