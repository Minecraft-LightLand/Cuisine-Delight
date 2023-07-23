package dev.xkmc.cuisinedelight.init.registrate;

import com.tterrag.registrate.util.entry.ItemEntry;
import dev.xkmc.cuisinedelight.content.item.PlateFoodItem;
import dev.xkmc.cuisinedelight.init.CuisineDelight;

import java.util.Locale;

public enum PlateFood {
	FRIED_RICE, MIXED_FRIED_RICE,
	MEAT_WITH_SEAFOOD, SEAFOOD_FRIED_RICE,
	MEAT_PASTA, MEAT_PLATTER, MEAT_WITH_VEGETABLES,
	SEAFOOD_PASTA, SEAFOOD_PLATTER, SEAFOOD_WITH_VEGETABLES,
	SUSPICIOUS_MIX, HAM_FRIED_RICE,
	MIXED_PASTA, VEGETABLE_PASTA, VEGETABLE_PLATTER;

	public final ItemEntry<PlateFoodItem> item;

	PlateFood() {
		item = CuisineDelight.REGISTRATE.item(name().toLowerCase(Locale.ROOT),
						p -> new PlateFoodItem(p.stacksTo(1)))
				.defaultModel().defaultLang().register();
	}

	public static void register() {

	}

}
