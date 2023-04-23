package dev.xkmc.cuisine_delight.content.client;

import dev.xkmc.cuisine_delight.content.item.PlateFoodItem;
import net.minecraft.client.gui.Font;
import net.minecraft.world.item.ItemStack;

public class FoodItemDecorationRenderer {

	public static boolean renderCount(Font font, ItemStack stack, int x, int y, float blitOffset) {
		var data = PlateFoodItem.getData(stack);
		if (data == null || data.size == 0) return false;
		String s = "" + data.size;
		CommonDecoUtil.drawText(x + 17 - font.width(s), y + 9, font, 0xffffff7f, s, blitOffset);
		return true;
	}


}
