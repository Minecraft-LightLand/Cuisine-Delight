package dev.xkmc.cuisine_delight.content;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.xkmc.cuisine_delight.content.item.CuisineSkilletItem;
import dev.xkmc.cuisine_delight.content.logic.CookingData;
import dev.xkmc.l2library.util.Proxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class CookingOverlay implements IGuiOverlay {

	@Override
	public void render(ForgeGui gui, PoseStack poseStack, float partialTick, int screenWidth, int screenHeight) {
		if (Minecraft.getInstance().level == null) return;
		LocalPlayer player = Proxy.getClientPlayer();
		ItemStack mainStack = player.getMainHandItem();
		ItemStack offStack = player.getOffhandItem();
		ItemStack stack;
		if (!mainStack.isEmpty() && mainStack.getItem() instanceof CuisineSkilletItem) stack = mainStack;
		else if (!offStack.isEmpty() && offStack.getItem() instanceof CuisineSkilletItem) stack = offStack;
		else return;
		CookingData data = CuisineSkilletItem.getData(stack);
		if (data == null || data.contents.size() == 0) return;
		int y = screenHeight / 2 - data.contents.size() * 10;
		int x = 8;
		Font font = Minecraft.getInstance().font;
		ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();
		for (var entry : data.contents) {
			ItemStack food = entry.item;
			renderer.renderAndDecorateItem(food, x, y + 2);
			renderer.renderGuiItemDecorations(font, food, x, y + 2);
			y += 20;
		}
	}

}
