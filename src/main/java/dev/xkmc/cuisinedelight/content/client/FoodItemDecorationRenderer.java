package dev.xkmc.cuisinedelight.content.client;

import dev.xkmc.cuisinedelight.content.item.BaseFoodItem;
import net.minecraft.client.gui.Font;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.IItemDecorator;

public class FoodItemDecorationRenderer implements IItemDecorator {

	public boolean render(Font font, ItemStack stack, int x, int y, float blitOffset) {
		var data = BaseFoodItem.getData(stack);
		if (data == null || data.size == 0) return false;
		String s = "" + data.size;
		CommonDecoUtil.drawText(x + 17 - font.width(s), y + 9, font, 0xffffff7f, s, blitOffset);
		return true;
	}

}
