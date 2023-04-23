package dev.xkmc.cuisine_delight.init;

import dev.xkmc.cuisine_delight.content.item.CuisineSkilletItem;
import dev.xkmc.cuisine_delight.content.item.FoodItem;
import dev.xkmc.cuisine_delight.content.item.PlateItem;
import dev.xkmc.cuisine_delight.content.item.SpatulaItem;
import dev.xkmc.cuisine_delight.init.data.TagGen;
import dev.xkmc.l2library.repack.registrate.util.entry.ItemEntry;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.client.model.generators.ModelFile;
import vectorwing.farmersdelight.FarmersDelight;

import java.util.function.Supplier;

@MethodsReturnNonnullByDefault
public class CDItems {

	public static class Tab extends CreativeModeTab {

		private final Supplier<Item> icon;

		public Tab(String id, Supplier<Item> icon) {
			super(CuisineDelight.MODID + "." + id);
			this.icon = icon;
		}

		@Override
		public ItemStack makeIcon() {
			return icon.get().getDefaultInstance();
		}
	}

	public static final Tab TAB_GENERATED = new Tab("general", () -> Items.EGG);

	public static final ItemEntry<CuisineSkilletItem> SKILLET;
	public static final ItemEntry<SpatulaItem> SPATULA;
	public static final ItemEntry<PlateItem> PLATE;
	public static final ItemEntry<FoodItem> FOOD;

	static {
		CuisineDelight.REGISTRATE.creativeModeTab(() -> TAB_GENERATED);
		SKILLET = CuisineDelight.REGISTRATE.item("cuisine_skillet", p -> new CuisineSkilletItem(p.stacksTo(1)))
				.model((ctx, pvd) -> {
					pvd.getBuilder(ctx.getName()).parent(new ModelFile.UncheckedModelFile("builtin/entity"));
					pvd.getBuilder(ctx.getName() + "_base").parent(new ModelFile.UncheckedModelFile(
							new ResourceLocation(FarmersDelight.MODID, "item/skillet_cooking")));
				})
				.defaultLang().register();

		SPATULA = CuisineDelight.REGISTRATE.item("spatula", p -> new SpatulaItem(p.stacksTo(1)))
				.tag(TagGen.UTENSILS).defaultModel().defaultLang().register();
		PLATE = CuisineDelight.REGISTRATE.item("plate", PlateItem::new)
				.tag(TagGen.UTENSILS).defaultModel().defaultLang().register();
		FOOD = CuisineDelight.REGISTRATE.item("food", p -> new FoodItem(p.stacksTo(1)))
				.defaultModel().defaultLang().register();
	}

	public static void register() {
	}

}
