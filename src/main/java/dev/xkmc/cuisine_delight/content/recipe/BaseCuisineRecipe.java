package dev.xkmc.cuisine_delight.content.recipe;

import dev.xkmc.cuisine_delight.content.item.BaseFoodItem;
import dev.xkmc.cuisine_delight.content.logic.CookedFoodData;
import dev.xkmc.cuisine_delight.init.CDMisc;
import dev.xkmc.l2library.base.recipe.BaseRecipe;
import dev.xkmc.l2library.serial.SerialClass;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ArrayList;

@SerialClass
public class BaseCuisineRecipe<R extends BaseCuisineRecipe<R>> extends BaseRecipe<R, BaseCuisineRecipe<?>, CuisineRecipeContainer> {

	public static ItemStack findBestMatch(Level level, CookedFoodData food) {
		CuisineRecipeContainer inv = new CuisineRecipeContainer(food);
		var list = level.getRecipeManager().getRecipesFor(CDMisc.RT_CUISINE.get(), inv, level);
		BaseCuisineRecipe<?> ans = null;
		double score = -1;
		for (var r : list) {
			double s = r.getScore(inv);
			if (s > score) {
				score = s;
				ans = r;
			}
		}
		assert ans != null;
		score -= ans.getScoreOffset();
		ItemStack result = ans.assemble(inv);
		food.nutrition *= 1 + ans.saturationBonusModifier * score;
		BaseFoodItem.setData(result, food);
		return result;
	}

	@SerialClass.SerialField
	public final ArrayList<CuisineRecipeMatch> list = new ArrayList<>();

	@SerialClass.SerialField
	public double saturationBonus, saturationBonusModifier;

	@SerialClass.SerialField
	public Item holderItem;

	public BaseCuisineRecipe(ResourceLocation id, RecType<R, BaseCuisineRecipe<?>, CuisineRecipeContainer> fac) {
		super(id, fac);
	}

	@Override
	public boolean matches(CuisineRecipeContainer cont, Level level) {
		var inputs = new ArrayList<>(cont.list);
		for (var match : list) {
			if (match.reduce(inputs) == 0) {
				return false;
			}
		}
		return true;
	}

	public double getScore(CuisineRecipeContainer cont) {
		var inputs = new ArrayList<>(cont.list);
		double ans = 0;
		for (var match : list) {
			ans += match.reduce(inputs);
		}
		return ans;
	}

	@Override
	public ItemStack assemble(CuisineRecipeContainer cont) {
		return holderItem.getDefaultInstance();
	}

	@Override
	public boolean canCraftInDimensions(int i, int i1) {
		return false;
	}

	@Override
	public ItemStack getResultItem() {
		if (holderItem instanceof BaseFoodItem food) {
			return food.displayStack(this);
		}
		return holderItem.getDefaultInstance();
	}

	private double getScoreOffset() {
		double ans = 0;
		for (var e : list) {
			ans += e.score();
		}
		return ans;
	}

	public double getMinSaturationBonus() {
		return saturationBonus;
	}

	public double getMaxSaturationBonus() {
		double ans = saturationBonus;
		for (var e : list) {
			ans += saturationBonusModifier * e.bonus();
		}
		return ans;
	}
}
