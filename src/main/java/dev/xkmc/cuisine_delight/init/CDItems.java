package dev.xkmc.cuisine_delight.init;

import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import dev.xkmc.cuisine_delight.content.item.CuisineSkilletItem;
import dev.xkmc.cuisine_delight.content.item.PlateFoodItem;
import dev.xkmc.cuisine_delight.content.item.PlateItem;
import dev.xkmc.cuisine_delight.content.item.SpatulaItem;
import dev.xkmc.cuisine_delight.init.data.TagGen;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.client.model.generators.ModelFile;

@MethodsReturnNonnullByDefault
public class CDItems {

	public static final RegistryEntry<CreativeModeTab> TAB =
			CuisineDelight.REGISTRATE.buildModCreativeTab("cuisine", "Cuisine Delight",
					e -> e.icon(CDItems.SKILLET::asStack));

	public static final ItemEntry<CuisineSkilletItem> SKILLET;
	public static final ItemEntry<SpatulaItem> SPATULA;
	public static final ItemEntry<PlateItem> PLATE;
	public static final ItemEntry<PlateFoodItem> PLATE_FOOD;

	static {

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
