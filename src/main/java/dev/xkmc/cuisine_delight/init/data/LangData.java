package dev.xkmc.cuisine_delight.init.data;

import com.tterrag.registrate.providers.RegistrateLangProvider;
import dev.xkmc.cuisine_delight.content.logic.FoodType;
import dev.xkmc.cuisine_delight.init.CuisineDelight;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import javax.annotation.Nullable;
import java.util.Locale;

public enum LangData {
	SERVE_SIZE("tooltip.size", "Serve size: %s", 1, ChatFormatting.GRAY),
	SCORE("tooltip.score", "Quality: %s%%", 1, ChatFormatting.AQUA),
	BAD_FOOD("tooltip.bad", "Inedible", 0, ChatFormatting.RED),
	SHIFT("tooltip.shift", "Press Shift for culinary details", 0, ChatFormatting.GRAY),
	BAD_BURNT("tooltip.burnt", "Ingredient %s is burnt", 1, ChatFormatting.DARK_RED),
	BAD_RAW("tooltip.raw", "Ingredient %s is a bit raw", 1, ChatFormatting.DARK_RED),
	BAD_OVERCOOKED("tooltip.overcooked", "Ingredient %s is overcooked", 1, ChatFormatting.DARK_RED),
	GOOD("tooltip.good", "Ingredient %s is cooked well", 1, ChatFormatting.DARK_GREEN),
	INFO_MIN_TIME("info.min_time", "Minimum cooking time: %s sec", 1, ChatFormatting.AQUA),
	INFO_MAX_TIME("info.max_time", "Maximum cooking time: %s sec", 1, ChatFormatting.AQUA),
	INFO_STIR_TIME("info.stir_time", "Maximum stir interval: %s sec", 1, ChatFormatting.AQUA),
	INFO_SIZE("info.size", "Serve size: %s serve", 1, ChatFormatting.GREEN),
	INFO_NUTRITION("info.nutrition", "Nutrition: %s per serve", 1, ChatFormatting.GREEN),
	INFO_RAW_PENALTY("info.raw_penalty", "Raw serve penalty: -%s%%", 1, ChatFormatting.RED),
	INFO_OVERCOOK_PENALTY("info.overcook_penalty", "Overcook penalty: -%s%%", 1, ChatFormatting.RED),
	INFO_DISPLAY("info.display", "Saturation Bonus: %sx~%sx", 2, ChatFormatting.AQUA),
	MSG_NO_HEAT("msg.no_heat", "Can't cook, as skillet is not near fire source or enchanted with fire aspect.", 0, ChatFormatting.RED),
	MSG_FULL("msg.full", "The skillet is full.", 0, ChatFormatting.RED),
	MSG_NOT_INGREDIENT("msg.not_ingredient", "You cannot cook this.", 0, ChatFormatting.RED),
	MSG_PLACE_HELP("msg.place_help", "Press shift for placing.", 0, null),
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
		MutableComponent ans = Component.translatable(key, args);
		if (format != null) {
			return ans.withStyle(format);
		}
		return ans;
	}

	public static void genLang(RegistrateLangProvider pvd) {
		for (LangData lang : LangData.values()) {
			pvd.add(lang.key, lang.def);
		}
		for (FoodType type : FoodType.values()) {
			pvd.add(type.getID(), type.def);
		}
	}


}
