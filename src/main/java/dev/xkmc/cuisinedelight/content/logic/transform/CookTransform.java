package dev.xkmc.cuisinedelight.content.logic.transform;

import net.minecraft.world.item.ItemStack;

public interface CookTransform {

	ItemStack renderStack(ItemStack stack);

	default int lightAdjust(int itemLight, boolean overcooked, boolean burnt) {
		if (burnt) itemLight = half(itemLight);
		if (overcooked) itemLight = half(itemLight);
		return itemLight;
	}

	private static int half(int light) {
		int a = (light >> 16) & 0xFFFF;
		int b = light & 0xFFFF;
		return a >> 1 << 16 | b >> 1;
	}

}
