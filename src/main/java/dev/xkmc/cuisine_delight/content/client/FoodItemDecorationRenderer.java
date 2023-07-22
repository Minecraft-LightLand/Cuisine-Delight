package dev.xkmc.cuisine_delight.content.client;

import dev.xkmc.cuisine_delight.content.item.PlateFoodItem;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.IItemDecorator;

public class FoodItemDecorationRenderer implements IItemDecorator {

	@Override
	public boolean render(GuiGraphics g, Font font, ItemStack stack, int x, int y) {
		var data = PlateFoodItem.getData(stack);
		if (data == null || data.size == 0) return false;
		String s = "" + data.size;
		g.pose().pushPose();
		g.pose().translate(0,0,250);
		g.drawString(font, s, x + 17 - font.width(s), y + 9, 0xffffff7f);
		g.pose().popPose();
		return true;
	}
}
