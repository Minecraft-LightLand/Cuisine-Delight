package dev.xkmc.cuisinedelight.init.registrate;

import com.tterrag.registrate.util.entry.ItemEntry;
import dev.xkmc.cuisinedelight.content.item.PlateFoodItem;
import dev.xkmc.cuisinedelight.init.CuisineDelight;

import java.util.Locale;

public enum PlateFood {
	SUSPICIOUS_MIX, FRIED_RICE, HAM_FRIED_RICE, MEAT_PASTA, SEAFOOD_FRIED_RICE, VEGETABLE_PASTA;;

	public final ItemEntry<PlateFoodItem> item;

	PlateFood() {
		item = CuisineDelight.REGISTRATE.item(name().toLowerCase(Locale.ROOT),
						p -> new PlateFoodItem(p.stacksTo(1)))
				.defaultModel().defaultLang().register();
	}

	public static void register() {

	}

}
