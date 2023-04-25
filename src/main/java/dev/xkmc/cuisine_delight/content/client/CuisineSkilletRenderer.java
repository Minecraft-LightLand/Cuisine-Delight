package dev.xkmc.cuisine_delight.content.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import dev.xkmc.cuisine_delight.content.block.CuisineSkilletBlockEntity;
import dev.xkmc.cuisine_delight.content.logic.CookingData;
import dev.xkmc.l2library.util.Proxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;

public class CuisineSkilletRenderer implements BlockEntityRenderer<CuisineSkilletBlockEntity> {

	public static void renderItem(float time, CookingData data, PoseStack poseStack, MultiBufferSource buffer, int light, int overlay) {
		ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();
		int i = 1;
		float fly = time * (1 - time) * 4;
		poseStack.translate(0, 0, 29 / 64f - fly * 16 / 32f);
		for (var entry : data.contents) {
			poseStack.translate(0, 0, -(fly * 4 + 1) / 32f);
			renderer.renderStatic(entry.item, ItemTransforms.TransformType.GROUND, light, overlay, poseStack, buffer, i++);
		}
	}

	public CuisineSkilletRenderer(BlockEntityRendererProvider.Context dispatcher) {
	}

	@Override
	public void render(CuisineSkilletBlockEntity be, float pTick, PoseStack poseStack, MultiBufferSource buffer, int light, int overlay) {
		CookingData data = be.cookingData;
		if (data.contents.size() > 0) {
			data.update(Proxy.getClientWorld().getGameTime());
			poseStack.pushPose();
			poseStack.translate(0.5, 0.5, 0.5);
			poseStack.mulPose(Vector3f.XP.rotationDegrees(90));
			float time = be.getStirPercent(pTick);
			renderItem(time, data, poseStack, buffer, light, overlay);
			poseStack.popPose();
		}
	}
}
