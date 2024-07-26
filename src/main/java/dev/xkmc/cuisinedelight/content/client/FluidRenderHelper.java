package dev.xkmc.cuisinedelight.content.client;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.xkmc.cuisinedelight.content.logic.transform.FluidTransform;
import net.minecraft.client.renderer.MultiBufferSource;

public class FluidRenderHelper {

	int count, alpha, red, green, blue;

	public void addFluid(FluidTransform fluid) {
		int color = fluid.color();
		int ca = color >> 24 & 0xff;
		int cr = color >> 16 & 0xff;
		int cg = color >> 8 & 0xff;
		int cb = color & 0xff;
		alpha += ca;
		red += cr * ca;
		green += cg * ca;
		blue += cb * ca;
		count++;

	}

	private int getColor() {
		if (alpha == 0 || count == 0) return 0;
		int ca = alpha / count & 0xff;
		int cr = red / alpha & 0xff;
		int cg = green / alpha & 0xff;
		int cb = blue / alpha & 0xff;
		return ca << 24 | cr << 16 | cg << 8 | cb;
	}

	public void render(PoseStack poseStack, MultiBufferSource buffer, int light) {
		if (count == 0) return;
		poseStack.pushPose();
		poseStack.translate(-0.5, -0.5, -0.5);
		FluidRenderer.renderWaterBox(2 / 16f, 1 / 16f, 2 / 16f, 14 / 16f, 1.3f / 16f, 14 / 16f,
				buffer, poseStack, light, getColor());
		poseStack.popPose();
	}
}
