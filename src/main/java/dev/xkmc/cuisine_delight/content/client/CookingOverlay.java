package dev.xkmc.cuisine_delight.content.client;

import dev.xkmc.cuisine_delight.content.block.CuisineSkilletBlockEntity;
import dev.xkmc.cuisine_delight.content.item.CuisineSkilletItem;
import dev.xkmc.cuisine_delight.content.logic.CookingData;
import dev.xkmc.cuisine_delight.content.logic.IngredientConfig;
import dev.xkmc.cuisine_delight.init.registrate.CDItems;
import dev.xkmc.l2library.util.Proxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import org.jetbrains.annotations.Nullable;

public class CookingOverlay implements IGuiOverlay {

	private static final float FACTOR = 3;
	private static final int MAX_EXTRA = 60;

	@Nullable
	private static CookingData getHandData() {
		LocalPlayer player = Proxy.getClientPlayer();
		ItemStack mainStack = player.getMainHandItem();
		ItemStack offStack = player.getOffhandItem();
		ItemStack stack;
		if (!mainStack.isEmpty() && mainStack.is(CDItems.SKILLET.get())) stack = mainStack;
		else if (!offStack.isEmpty() && offStack.is(CDItems.SKILLET.get())) stack = offStack;
		else return null;
		return CuisineSkilletItem.getData(stack);
	}

	@Nullable
	private static CookingData getBlockData() {
		HitResult result = Minecraft.getInstance().hitResult;
		if (result == null || result.getType() != HitResult.Type.BLOCK) return null;
		BlockPos pos = ((BlockHitResult) result).getBlockPos();
		if (Proxy.getClientWorld().getBlockEntity(pos) instanceof CuisineSkilletBlockEntity be) {
			return be.cookingData;
		}
		return null;
	}

	@Nullable
	public static CookingData getData() {
		CookingData itemData = getHandData();
		if (itemData != null) return itemData;
		return getBlockData();
	}

	@Override
	public void render(ForgeGui gui, GuiGraphics g, float partialTick, int screenWidth, int screenHeight) {
		if (Minecraft.getInstance().level == null) return;
		CookingData data = getData();
		if (data == null || data.contents.size() == 0) return;
		data.update(Minecraft.getInstance().level.getGameTime());
		int y = screenHeight / 2 - data.contents.size() * 10;
		int x = 8;
		Font font = Minecraft.getInstance().font;
		for (var entry : data.contents) {
			ItemStack food = entry.item;
			g.renderItem(food, x, y + 2);
			g.renderItemDecorations(font, food, x, y + 2);
			y += 20;
		}
		x += 20;
		y = screenHeight / 2 - data.contents.size() * 10;
		for (var entry : data.contents) {
			ItemStack food = entry.item;
			var config = IngredientConfig.get().getEntry(food);
			if (config != null) {
				int ix = x;
				int iy = y + 4;
				int val = (int) (data.lastActionTime - entry.startTime);
				int max = config.min_time;

				fillBar(g, ix, iy, max, 2, val, 255, 192, 192);

				ix += max / FACTOR;
				val -= max;
				max = config.max_time - max;
				fillBar(g, ix, iy, max, 2, val, 255, 255, 192);

				ix += max / FACTOR;
				val -= max;
				max = Math.min(MAX_EXTRA, Math.max(val, 0));
				fillBar(g, ix, iy, max, 2, val, 0, 0, 0);

				ix = x;
				iy += 4;
				val = (int) (data.lastActionTime - entry.lastStirTime);
				max = config.stir_time;
				fillBar(g, ix, iy, max, 2, val, 192, 255, 192);

				ix += max / FACTOR;
				val -= max;
				max = Math.min(MAX_EXTRA, Math.max(val, entry.maxStirTime - max));
				fillBar(g, ix, iy, max, 2, val, 255, 128, 128);
			}
			y += 20;
		}
	}

	private static void fillBar(GuiGraphics gui, int x, int y, int w, int h, int w0, int r, int g, int b) {
		if (w <= 0) {
			return;
		}
		if (w0 >= w) {
			CommonDecoUtil.fillRect(gui, x, y, w / FACTOR, h, color(r, g, b, 255));
		} else if (w0 <= 0) {
			CommonDecoUtil.fillRect(gui, x, y, w / FACTOR, h, color(r / 2, g / 2, b / 2, 255));
		} else {
			CommonDecoUtil.fillRect(gui, x, y, w0 / FACTOR, h, color(r, g, b, 255));
			CommonDecoUtil.fillRect(gui, x + w0 / FACTOR, y, (w - w0) / FACTOR, h, color(r / 2, g / 2, b / 2, 255));
		}
	}

	public static int color(int r, int g, int b, int a) {
		return a << 24 | r << 16 | g << 8 | b;
	}

}
