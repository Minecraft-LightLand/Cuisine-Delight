package dev.xkmc.cuisine_delight.init;

import dev.xkmc.cuisine_delight.content.item.CuisineSkilletItem;
import dev.xkmc.cuisine_delight.content.item.PlateFoodItem;
import dev.xkmc.cuisine_delight.content.item.PlateItem;
import dev.xkmc.cuisine_delight.content.item.SpatulaItem;
import dev.xkmc.cuisine_delight.init.data.TagGen;
import dev.xkmc.l2library.repack.registrate.providers.ProviderType;
import dev.xkmc.l2library.repack.registrate.util.entry.ItemEntry;
import dev.xkmc.l2library.repack.registrate.util.nullness.NonNullBiConsumer;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.client.model.generators.ModelFile;

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

	public static final Tab TAB_GENERAL = new Tab("general", () -> Items.EGG);

	public static final ItemEntry<CuisineSkilletItem> SKILLET;
	public static final ItemEntry<SpatulaItem> SPATULA;
	public static final ItemEntry<PlateItem> PLATE;
	public static final ItemEntry<PlateFoodItem> PLATE_FOOD;

	static {
		CuisineDelight.REGISTRATE.creativeModeTab(() -> TAB_GENERAL);
		SKILLET = CuisineDelight.REGISTRATE.item("cuisine_skillet", p -> new CuisineSkilletItem(CDBlocks.SKILLET.get(), p.stacksTo(1)))
				.model((ctx, pvd) -> pvd.getBuilder(ctx.getName()).parent(new ModelFile.UncheckedModelFile("builtin/entity")))
				.setData(ProviderType.LANG, NonNullBiConsumer.noop()).register();

		SPATULA = CuisineDelight.REGISTRATE.item("spatula", p -> new SpatulaItem(p.stacksTo(1)))
				.tag(TagGen.UTENSILS).defaultModel().defaultLang().register();
		PLATE = CuisineDelight.REGISTRATE.item("plate", PlateItem::new)
				.tag(TagGen.UTENSILS).defaultModel().defaultLang().register();
		PLATE_FOOD = CuisineDelight.REGISTRATE.item("plate_food", p -> new PlateFoodItem(p.stacksTo(1)))
				.defaultModel().defaultLang().register();
	}

	public static void register() {
	}

}
