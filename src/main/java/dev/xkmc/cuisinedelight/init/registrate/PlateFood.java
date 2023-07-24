package dev.xkmc.cuisinedelight.init.registrate;

import com.tterrag.registrate.util.entry.ItemEntry;
import dev.xkmc.cuisinedelight.content.item.PlateFoodItem;
import dev.xkmc.cuisinedelight.init.CuisineDelight;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

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
				.defaultModel().lang(toEnglishName(name())).register();
	}

	public static String toEnglishName(String internalName) {
		Set<String> SMALL_WORDS = Set.of("of", "the", "with");
		return Arrays.stream(internalName.toLowerCase(Locale.ROOT).split("_"))
				.map(e -> SMALL_WORDS.contains(e) ? e : StringUtils.capitalize(e))
				.collect(Collectors.joining(" "));
	}

	public static void register() {

	}

}
