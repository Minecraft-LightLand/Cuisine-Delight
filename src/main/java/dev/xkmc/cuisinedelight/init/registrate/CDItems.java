package dev.xkmc.cuisinedelight.init.registrate;

import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import dev.xkmc.cuisinedelight.content.client.SkilletBEWLR;
import dev.xkmc.cuisinedelight.content.item.CuisineSkilletItem;
import dev.xkmc.cuisinedelight.content.item.PlateItem;
import dev.xkmc.cuisinedelight.content.item.SpatulaItem;
import dev.xkmc.cuisinedelight.content.logic.CookedFoodData;
import dev.xkmc.cuisinedelight.content.logic.CookingData;
import dev.xkmc.cuisinedelight.content.logic.JEIDisplayInfo;
import dev.xkmc.cuisinedelight.init.CuisineDelight;
import dev.xkmc.cuisinedelight.init.data.TagGen;
import dev.xkmc.l2core.init.reg.registrate.SimpleEntry;
import dev.xkmc.l2core.init.reg.simple.DCReg;
import dev.xkmc.l2core.init.reg.simple.DCVal;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Unit;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ModelFile;

@MethodsReturnNonnullByDefault
public class CDItems {

	public static final ItemEntry<Item> FRIED_EGG;

	public static final SimpleEntry<CreativeModeTab> TAB;

	public static final ItemEntry<CuisineSkilletItem> SKILLET;
	public static final ItemEntry<SpatulaItem> SPATULA;
	public static final ItemEntry<PlateItem> PLATE;

	private static final DCReg DC = DCReg.of(CuisineDelight.REG);
	public static final DCVal<CookingData.Record> COOKING = DC.reg("cooking", CookingData.Record.class, true);
	public static final DCVal<CookedFoodData> COOKED = DC.reg("cooked", CookedFoodData.class, true);
	public static final DCVal<JEIDisplayInfo> DISPLAY = DC.reg("display", JEIDisplayInfo.class, true);
	public static final DCVal<Unit> INGREDIENT = DC.unit("ingredient");

	static {
		FRIED_EGG = CuisineDelight.REGISTRATE.item("fried_egg", Item::new).register();

		TAB = CuisineDelight.REGISTRATE.buildModCreativeTab("cuisine", "Cuisine Delight",
				e -> e.icon(CDItems.SKILLET::asStack));

		SKILLET = CuisineDelight.REGISTRATE.item("cuisine_skillet", p -> new CuisineSkilletItem(CDBlocks.SKILLET.get(), p.stacksTo(1)))
				.model((ctx, pvd) -> pvd.getBuilder(ctx.getName()).parent(new ModelFile.UncheckedModelFile("builtin/entity")))
				.clientExtension(() -> () -> SkilletBEWLR.EXTENSIONS)
				.tag(ItemTags.MINING_ENCHANTABLE, ItemTags.FIRE_ASPECT_ENCHANTABLE)
				.setData(ProviderType.LANG, NonNullBiConsumer.noop()).register();

		SPATULA = CuisineDelight.REGISTRATE.item("spatula", p -> new SpatulaItem(p.stacksTo(1)))
				.tag(TagGen.UTENSILS, ItemTags.MINING_LOOT_ENCHANTABLE)
				.model((ctx, pvd) -> pvd.handheld(ctx)).defaultLang().register();

		PLATE = CuisineDelight.REGISTRATE.item("plate", PlateItem::new)
				.tag(TagGen.UTENSILS).defaultModel().defaultLang().register();
		PlateFood.register();
	}

	public static void register() {
	}

}
