package dev.xkmc.cuisinedelight.content.logic;

import dev.xkmc.cuisinedelight.init.CuisineDelight;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.Locale;

public enum FoodType {
	NONE(0, "Seasoning", ChatFormatting.GRAY),
	MEAT(1, "Meat", ChatFormatting.LIGHT_PURPLE),
	VEG(1, "Vegetable", ChatFormatting.GREEN),
	CARB(1, "Staple", ChatFormatting.YELLOW),
	SEAFOOD(1, "Sea Food", ChatFormatting.AQUA);

	public final int bonus;
	public final String def;
	public final ChatFormatting format;

	FoodType(int bonus, String def, ChatFormatting format) {
		this.bonus = bonus;
		this.def = def;
		this.format = format;
	}

	public String getID() {
		return CuisineDelight.MODID + ".food_type." + name().toLowerCase(Locale.ROOT);
	}

	public MutableComponent get() {
		return Component.translatable(getID());
	}

	public ItemStack getDisplay() {
		return Items.BOWL.getDefaultInstance();//TODO
	}
}
