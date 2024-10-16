package dev.xkmc.cuisinedelight.content.client;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.FastColor;
import org.joml.Matrix4f;

public class CommonDecoUtil {

	private static final int Z = -1, PIE_SIZE = 50;

	public static void fillRect(GuiGraphics g, float x, float y, float w, float h, int col) {
		Matrix4f matrix4f = g.pose().last().pose();
		float f3 = (float) FastColor.ARGB32.alpha(col) / 255.0F;
		float f = (float) FastColor.ARGB32.red(col) / 255.0F;
		float f1 = (float) FastColor.ARGB32.green(col) / 255.0F;
		float f2 = (float) FastColor.ARGB32.blue(col) / 255.0F;
		VertexConsumer vertexconsumer = g.bufferSource().getBuffer(RenderType.gui());
		vertexconsumer.addVertex(matrix4f, x, y, Z).setColor(f, f1, f2, f3);
		vertexconsumer.addVertex(matrix4f, x, y + h, Z).setColor(f, f1, f2, f3);
		vertexconsumer.addVertex(matrix4f, x + w, y + h, Z).setColor(f, f1, f2, f3);
		vertexconsumer.addVertex(matrix4f, x + w, y, Z).setColor(f, f1, f2, f3);
		g.flush();
	}

}
