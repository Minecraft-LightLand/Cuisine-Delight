package dev.xkmc.cuisinedelight.compat;


import dev.xkmc.cuisinedelight.content.recipe.BaseCuisineRecipe;
import dev.xkmc.cuisinedelight.init.CuisineDelight;
import dev.xkmc.cuisinedelight.init.registrate.CDMisc;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;

@JeiPlugin
public class JEICompat implements IModPlugin {

	public static final ResourceLocation ID = CuisineDelight.loc("main");

	public final CuisineRecipeCategory LOOT = new CuisineRecipeCategory();


	@Override
	public ResourceLocation getPluginUid() {
		return ID;
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) {
		IGuiHelper helper = registration.getJeiHelpers().getGuiHelper();
		registration.addRecipeCategories(LOOT.init(helper));
	}

	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		registration.addRecipes(LOOT.getRecipeType(), Minecraft.getInstance().level.getRecipeManager()
				.getAllRecipesFor(CDMisc.RT_CUISINE.get())
				.stream().<BaseCuisineRecipe<?>>map(RecipeHolder::value).toList());
	}

}
