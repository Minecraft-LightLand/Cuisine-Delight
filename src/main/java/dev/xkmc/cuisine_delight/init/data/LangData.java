package dev.xkmc.cuisine_delight.init.data;

import dev.xkmc.cuisine_delight.init.CuisineDelight;
import dev.xkmc.l2library.repack.registrate.providers.RegistrateLangProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;

import javax.annotation.Nullable;
import java.util.Locale;

public enum LangData {
	SERVE_SIZE("tooltip.size", "Serve size: %s/%s", 2, ChatFormatting.GRAY),
	SCORE("tooltip.score", "Quality: %s%%", 1, ChatFormatting.AQUA),
	BAD_FOOD("tooltip.bad", "Inedible", 0, ChatFormatting.RED),
	SHIFT("tooltip.shift", "Press Shift for details", 0, ChatFormatting.DARK_GRAY),
	BAD_BURNT("tooltip.burnt", "Ingredient %s is burnt", 1, ChatFormatting.DARK_RED),
	BAD_RAW("tooltip.raw", "Ingredient %s is a bit raw", 1, ChatFormatting.DARK_RED),
	BAD_OVERCOOKED("tooltip.overcooked", "Ingredient %s is overcooked", 1, ChatFormatting.DARK_RED),
	GOOD("tooltip.good", "Ingredient %s is cooked well", 1, ChatFormatting.DARK_GREEN),

	;

	private final String key, def;
	private final int arg;
	private final ChatFormatting format;


	LangData(String key, String def, int arg, @Nullable ChatFormatting format) {
		this.key = CuisineDelight.MODID + "." + key;
		this.def = def;
		this.arg = arg;
		this.format = format;
	}

	public static String asId(String name) {
		return name.toLowerCase(Locale.ROOT);
	}

	public static MutableComponent getTranslate(String s) {
		return Component.translatable(CuisineDelight.MODID + "." + s);
	}

	public MutableComponent get(Object... args) {
		if (args.length != arg)
			throw new IllegalArgumentException("for " + name() + ": expect " + arg + " parameters, got " + args.length);
		MutableComponent ans = MutableComponent.create(new TranslatableContents(key, args));
		if (format != null) {
			return ans.withStyle(format);
		}
		return ans;
	}

	public static void genLang(RegistrateLangProvider pvd) {
		for (LangData lang : LangData.values()) {
			pvd.add(lang.key, lang.def);
		}
		pvd.add("itemGroup." + CuisineDelight.MODID + ".general", "Cuisine Delight");
	}


}
