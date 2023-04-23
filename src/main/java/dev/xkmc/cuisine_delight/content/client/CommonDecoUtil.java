package dev.xkmc.cuisine_delight.content.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;

public class CommonDecoUtil {

	/**
	 * Draw with the WorldRenderer
	 */
	public static void fillRect(BufferBuilder buffer, double x, double y, double w, double h, double z, int color, int a) {
		int r = (color >> 16) & 0xff;
		int g = (color >> 8) & 0xff;
		int b = color & 0xff;
		RenderSystem.disableDepthTest();
		RenderSystem.disableTexture();
		RenderSystem.setShader(GameRenderer::getPositionColorShader);
		buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
		buffer.vertex(x, y, z).color(r, g, b, a).endVertex();
		buffer.vertex(x, y + h, z).color(r, g, b, a).endVertex();
		buffer.vertex(x + w, y + h, z).color(r, g, b, a).endVertex();
		buffer.vertex(x + w, y, z).color(r, g, b, a).endVertex();
		BufferUploader.drawWithShader(buffer.end());
	}

	public static void drawText(int x, int y, Font font, int col, String s, float blitOffset) {
		PoseStack pose = new PoseStack();
		pose.translate(0, 0, blitOffset + 202);
		MultiBufferSource.BufferSource buffer = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());
		font.drawInBatch(s, x, y, col, true, pose.last().pose(), buffer,
				false, 0, 0xF000F0);
		buffer.endBatch();
	}


}
