package dev.xkmc.cuisine_delight.content.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import dev.xkmc.cuisine_delight.content.block.CuisineSkilletBlockEntity;
import dev.xkmc.cuisine_delight.content.item.CuisineSkilletItem;
import dev.xkmc.cuisine_delight.content.logic.CookingData;
import dev.xkmc.cuisine_delight.content.logic.IngredientConfig;
import dev.xkmc.cuisine_delight.init.CDItems;
import dev.xkmc.l2library.util.Proxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import org.jetbrains.annotations.Nullable;

public class CookingOverlay implements IGuiOverlay {

	private static final double FACTOR = 3;
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
	public void render(ForgeGui gui, PoseStack poseStack, float partialTick, int screenWidth, int screenHeight) {
		if (Minecraft.getInstance().level == null) return;
		CookingData data = getData();
		if (data == null || data.contents.size() == 0) return;
		data.update(Minecraft.getInstance().level.getGameTime());
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
		x += 20;
		y = screenHeight / 2 - data.contents.size() * 10;
		RenderSystem.disableDepthTest();
		RenderSystem.disableTexture();
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		Tesselator tex = Tesselator.getInstance();
		BufferBuilder builder = tex.getBuilder();
		for (var entry : data.contents) {
			ItemStack food = entry.item;
			var config = IngredientConfig.get().getEntry(food);
			if (config != null) {
				int ix = x;
				int iy = y + 4;
				int val = (int) (data.lastActionTime - entry.startTime);
				int max = config.min_time;

				fillBar(builder, ix, iy, max, 2, val, 255, 192, 192);

				ix += max / FACTOR;
				val -= max;
				max = config.max_time - max;
				fillBar(builder, ix, iy, max, 2, val, 255, 255, 192);

				ix += max / FACTOR;
				val -= max;
				max = Math.min(MAX_EXTRA, Math.max(val, 0));
				fillBar(builder, ix, iy, max, 2, val, 0, 0, 0);

				ix = x;
				iy += 4;
				val = (int) (data.lastActionTime - entry.lastStirTime);
				max = config.stir_time;
				fillBar(builder, ix, iy, max, 2, val, 192, 255, 192);

				ix += max / FACTOR;
				val -= max;
				max = Math.min(MAX_EXTRA, Math.max(val, entry.maxStirTime - max));
				fillBar(builder, ix, iy, max, 2, val, 255, 128, 128);
			}
			y += 20;
		}
		RenderSystem.enableTexture();
		RenderSystem.enableDepthTest();
	}

	private static void fillBar(BufferBuilder builder, int x, int y, int w, int h, int w0, int r, int g, int b) {
		if (w <= 0) {
			return;
		}
		if (w0 >= w) {
			CommonDecoUtil.fillRect(builder, x, y, w / FACTOR, h, r, g, b, 255);
		} else if (w0 <= 0) {
			CommonDecoUtil.fillRect(builder, x, y, w / FACTOR, h, r / 2, g / 2, b / 2, 255);
		} else {
			CommonDecoUtil.fillRect(builder, x, y, w0 / FACTOR, h, r, g, b, 255);
			CommonDecoUtil.fillRect(builder, x + w0 / FACTOR, y, (w - w0) / FACTOR, h, r / 2, g / 2, b / 2, 255);
		}
	}

}
