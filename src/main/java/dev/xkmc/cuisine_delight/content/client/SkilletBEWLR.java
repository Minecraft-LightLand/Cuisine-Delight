package dev.xkmc.cuisine_delight.content.client;

import com.google.common.base.Suppliers;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import dev.xkmc.cuisine_delight.content.item.CuisineSkilletItem;
import dev.xkmc.cuisine_delight.content.logic.CookingData;
import dev.xkmc.cuisine_delight.init.CDItems;
import dev.xkmc.cuisine_delight.init.CuisineDelightClient;
import dev.xkmc.l2library.util.Proxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Supplier;

public class SkilletBEWLR extends BlockEntityWithoutLevelRenderer {

	public static final Supplier<BlockEntityWithoutLevelRenderer> INSTANCE = Suppliers.memoize(() ->
			new SkilletBEWLR(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels()));

	public static final IClientItemExtensions EXTENSIONS = new IClientItemExtensions() {

		@Override
		public BlockEntityWithoutLevelRenderer getCustomRenderer() {
			return INSTANCE.get();
		}

	};

	public SkilletBEWLR(BlockEntityRenderDispatcher dispatcher, EntityModelSet set) {
		super(dispatcher, set);
	}

	public void onResourceManagerReload(ResourceManager p_172555_) {
	}

	@Override
	public void renderByItem(ItemStack stack, ItemTransforms.TransformType type, PoseStack poseStack,
							 MultiBufferSource bufferSource, int light, int overlay) {
		if (stack.isEmpty() || stack.getItem() != CDItems.SKILLET.get()) return;
		poseStack.popPose();
		poseStack.pushPose();
		ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();
		int i = 0;
		BakedModel model = Minecraft.getInstance().getModelManager().getModel(CuisineDelightClient.SKILLET_MODEL);
		renderer.render(stack, type, false, poseStack, bufferSource, light, overlay, model);
		CookingData data = CuisineSkilletItem.getData(stack);
		if (data != null && data.contents.size() > 0) {
			data.update(Proxy.getClientWorld().getGameTime());
			poseStack.pushPose();
			model.applyTransform(type, poseStack, false);
			poseStack.mulPose(Vector3f.XP.rotationDegrees(90));
			float time = 0;
			LocalPlayer player = Proxy.getClientPlayer();
			if (player.getMainHandItem() == stack || player.getOffhandItem() == stack) {
				time = player.getCooldowns().getCooldownPercent(stack.getItem(), Minecraft.getInstance().getPartialTick());
			}
			float fly = time * (1 - time) * 4;
			poseStack.translate(0, 0, 29 / 64f - fly * 16 / 32f);
			for (var entry : data.contents) {
				poseStack.translate(0, 0, -(fly * 4 + 1) / 32f);
				renderer.renderStatic(entry.item, ItemTransforms.TransformType.GROUND, light, overlay, poseStack, bufferSource, i++);
			}
			poseStack.popPose();
		}
	}

}
