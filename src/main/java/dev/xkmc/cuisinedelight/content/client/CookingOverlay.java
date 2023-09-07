package dev.xkmc.cuisinedelight.content.client;

import dev.xkmc.cuisinedelight.content.block.CuisineSkilletBlockEntity;
import dev.xkmc.cuisinedelight.content.item.CuisineSkilletItem;
import dev.xkmc.cuisinedelight.content.logic.CookingData;
import dev.xkmc.cuisinedelight.content.logic.IngredientConfig;
import dev.xkmc.cuisinedelight.init.data.CDConfig;
import dev.xkmc.cuisinedelight.init.registrate.CDItems;
import dev.xkmc.l2library.util.Proxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import org.jetbrains.annotations.Nullable;

public class CookingOverlay implements IGuiOverlay {

	private static final float MAX_TIME = 400, STIR_TIME = 100, R = 9;

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
		float scale = (float) (double) CDConfig.CLIENT.uiScale.get();
		screenHeight /= scale;
		g.pose().pushPose();
		g.pose().scale(scale, scale, scale);
		data.update(Minecraft.getInstance().level.getGameTime());
		int y = screenHeight / 2 - data.contents.size() * 10;
		int x = 8;
		Font font = Minecraft.getInstance().font;
		for (var entry : data.contents) {
			ItemStack food = entry.getItem();
			g.renderItem(food, x, y + 2);
			g.renderItemDecorations(font, food, x, y + 2);
			y += 20;
		}
		x += 20;
		y = screenHeight / 2 - data.contents.size() * 10;
		for (var entry : data.contents) {
			ItemStack food = entry.getItem();
			var config = IngredientConfig.get().getEntry(food);
			if (config != null) {
				PieRenderer cook = new PieRenderer(g, x + 8, y + 12);
				float min = config.min_time / MAX_TIME;
				float max = config.max_time / MAX_TIME;
				cook.fillPie(0, min, PieRenderer.Texture.PIE_GREEN);
				cook.fillPie(min, max, PieRenderer.Texture.PIE_YELLOW);
				cook.fillPie(max, 1, PieRenderer.Texture.PIE_RED);

				float cook_needle = Mth.clamp(entry.getDuration(data, partialTick) / MAX_TIME, 0f, 1f);
				cook.drawNeedle(PieRenderer.Texture.NEEDLE_BLACK, cook_needle);
				cook.drawIcon(PieRenderer.Texture.COOK);

				PieRenderer flip = new PieRenderer(g, x + 28, y + 12);
				float thr = config.stir_time / STIR_TIME;
				flip.fillPie(0, thr, PieRenderer.Texture.PIE_GREEN);
				flip.fillPie(thr, 1, PieRenderer.Texture.PIE_RED);

				float stir_current = Mth.clamp(entry.timeSinceStir(data, partialTick) / STIR_TIME, 0f, 1f);
				float stir_max = Mth.clamp(Math.max(stir_current, entry.getMaxStirTime(data) / STIR_TIME), 0f, 1f);
				flip.drawNeedle(PieRenderer.Texture.NEEDLE_BLACK, stir_current);
				flip.drawNeedle(PieRenderer.Texture.NEEDLE_RED, stir_max + 0.5f);
				flip.drawIcon(PieRenderer.Texture.FLIP);
			}
			y += 20;
		}
		g.pose().popPose();
		;
	}
}
