package dev.xkmc.cuisinedelight.init.registrate;

import com.tterrag.registrate.util.entry.ItemEntry;
import dev.xkmc.cuisinedelight.content.item.PlateFoodItem;
import dev.xkmc.cuisinedelight.init.CuisineDelight;

import java.util.Locale;

public enum PlateFood {
	FRIED_RICE, MEAT_PLATTER, MEAT_WITH_SEAFOOD, MEAT_WITH_VEGETABLES,
	PASTA, SEAFOOD_PASTA, SEAFOOD_PLATTER, SEAFOOD_WITH_VEGETABLES,
	SUSPICIOUS_MIX, VEGETABLE_PASTA, VEGETABLE_PLATTER;

	public final ItemEntry<PlateFoodItem> item;

	PlateFood() {
		item = CuisineDelight.REGISTRATE.item(name().toLowerCase(Locale.ROOT),
						p -> new PlateFoodItem(p.stacksTo(1)))
				.defaultModel().defaultLang().register();
	}

	public static void register() {

	}

}
