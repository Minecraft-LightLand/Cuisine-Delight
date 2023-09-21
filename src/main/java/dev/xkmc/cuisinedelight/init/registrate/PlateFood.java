package dev.xkmc.cuisinedelight.init.registrate;

import dev.xkmc.cuisinedelight.content.item.BaseFoodItem;
import dev.xkmc.cuisinedelight.init.CuisineDelight;
import dev.xkmc.l2library.repack.registrate.util.entry.ItemEntry;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

public enum PlateFood {
	SUSPICIOUS_MIX, HAM_FRIED_RICE,
	FRIED_RICE, MIXED_FRIED_RICE,
	MEAT_WITH_SEAFOOD, MEAT_WITH_VEGETABLES, SEAFOOD_WITH_VEGETABLES,
	FRIED_PASTA, MIXED_PASTA,
	MEAT_FRIED_RICE, MEAT_PASTA, MEAT_PLATTER,
	SEAFOOD_FRIED_RICE, SEAFOOD_PASTA, SEAFOOD_PLATTER,
	VEGETABLE_FRIED_RICE, VEGETABLE_PASTA, VEGETABLE_PLATTER,
	FRIED_MUSHROOM, FRIED_MEAT_AND_MELON, SCRAMBLED_EGG_AND_TOMATO;

	public final ItemEntry<BaseFoodItem> item;

	PlateFood() {
		item = CuisineDelight.REGISTRATE.item(name().toLowerCase(Locale.ROOT),
						p -> new BaseFoodItem(p.stacksTo(1).craftRemainder(CDItems.PLATE.get())))
				.defaultModel().lang(toEnglishName(name())).register();
	}

	public static String toEnglishName(String internalName) {
		Set<String> SMALL_WORDS = Set.of("of", "the", "with", "and");
		return Arrays.stream(internalName.toLowerCase(Locale.ROOT).split("_"))
				.map(e -> SMALL_WORDS.contains(e) ? e : StringUtils.capitalize(e))
				.collect(Collectors.joining(" "));
	}

	public static void register() {

	}

}
