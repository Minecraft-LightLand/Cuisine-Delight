package dev.xkmc.cuisine_delight.content.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import dev.xkmc.cuisine_delight.init.CuisineDelight;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.joml.Matrix4f;

import java.util.Locale;

public record PieRenderer(GuiGraphics g, int x, int y) {

	public enum Texture {
		PIE_GREEN, PIE_YELLOW, PIE_RED, NEEDLE_RED, NEEDLE_BLACK, COOK, FLIP;

		public ResourceLocation getID() {
			return new ResourceLocation(CuisineDelight.MODID, "textures/gui/overlay/" + name().toLowerCase(Locale.ROOT) + ".png");
		}
	}


	private static final int Z = -1;

	public void fillPie(float a0, float a1, Texture type) {
		a0 += 0.75f;
		a1 += 0.75f;
		RenderSystem.setShaderTexture(0, type.getID());
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		Matrix4f matrix4f = g.pose().last().pose();
		BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
		bufferbuilder.begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION_TEX);
		bufferbuilder.vertex(matrix4f, x, y, Z).uv(0.5f, 0.5f).endVertex();
		for (int i = Mth.ceil(a1 * 4 + 0.5f); i >= Mth.floor(a0 * 4 + 0.5f); i--) {
			double a = Mth.clamp(i * 0.25f - 0.125f, a0, a1) * 2 * Math.PI;
			float cos = (float) Math.cos(a);
			float sin = (float) Math.sin(a);
			float r;
			if (Math.abs(cos) > Math.abs(sin)) {
				r = Math.abs(0.5f / cos);
			} else {
				r = Math.abs(0.5f / sin);
			}
			bufferbuilder.vertex(matrix4f, x + 16 * r * cos, y + 16 * r * sin, Z).uv(0.5f + r * cos, 0.5f + r * sin).endVertex();
		}
		BufferUploader.drawWithShader(bufferbuilder.end());
	}

	public void drawNeedle(Texture needle, float a) {
		g.pose().pushPose();
		g.pose().translate(x, y, 0);
		g.pose().mulPose(Axis.ZP.rotationDegrees(a * 360));
		g.blit(needle.getID(), -8, -8, 16, 16, 0, 0, 32, 32, 32, 32);
		g.pose().popPose();
	}

	public void drawIcon(Texture icon) {
		g.blit(icon.getID(), x - 8, y - 8, 16, 16, 0, 0, 32, 32, 32, 32);
	}


}
