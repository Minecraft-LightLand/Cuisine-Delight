package dev.xkmc.cuisinedelight.content.logic;

import dev.xkmc.cuisinedelight.init.CuisineDelight;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.Locale;
import java.util.function.Supplier;

public enum FoodType {
	NONE(0, "Seasoning", ChatFormatting.GRAY, () -> Items.SUGAR),
	MEAT(1, "Meat", ChatFormatting.LIGHT_PURPLE, () -> Items.BEEF),
	VEG(1, "Vegetable", ChatFormatting.GREEN, () -> Items.CARROT),
	CARB(1, "Staple", ChatFormatting.YELLOW, () -> Items.POTATO),
	SEAFOOD(1, "Sea Food", ChatFormatting.AQUA, () -> Items.SALMON);

	public final int bonus;
	public final String def;
	public final ChatFormatting format;

	private final Supplier<Item> icon;

	FoodType(int bonus, String def, ChatFormatting format, Supplier<Item> icon) {
		this.bonus = bonus;
		this.def = def;
		this.format = format;
		this.icon = icon;
	}

	public String getID() {
		return CuisineDelight.MODID + ".food_type." + name().toLowerCase(Locale.ROOT);
	}

	public MutableComponent get() {
		return Component.translatable(getID());
	}

	public ItemStack getDisplay() {
		return icon.get().getDefaultInstance();
	}

}
