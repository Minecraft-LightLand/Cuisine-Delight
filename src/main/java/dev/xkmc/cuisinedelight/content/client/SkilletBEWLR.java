package dev.xkmc.cuisinedelight.content.client;

import com.google.common.base.Suppliers;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.xkmc.cuisinedelight.content.item.CuisineSkilletItem;
import dev.xkmc.cuisinedelight.content.logic.CookingData;
import dev.xkmc.cuisinedelight.init.CuisineDelightClient;
import dev.xkmc.cuisinedelight.init.registrate.CDItems;
import dev.xkmc.l2core.util.Proxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

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
	public void renderByItem(ItemStack stack, ItemDisplayContext type, PoseStack poseStack,
							 MultiBufferSource bufferSource, int light, int overlay) {
		var level = Minecraft.getInstance().level;
		if (level == null) return;
		if (stack.isEmpty() || stack.getItem() != CDItems.SKILLET.get()) return;
		poseStack.popPose();
		poseStack.pushPose();
		ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();
		int i = 0;
		BakedModel model = Minecraft.getInstance().getModelManager().getModel(CuisineDelightClient.SKILLET_MODEL);
		renderer.render(stack, type, false, poseStack, bufferSource, light, overlay, model);
		CookingData data = CuisineSkilletItem.getData(stack);
		if (data != null && !data.contents.isEmpty()) {
			data.update(level.getGameTime());
			poseStack.pushPose();
			model.applyTransform(type, poseStack, false);
			float time = 0;
			LocalPlayer player = Proxy.getClientPlayer();
			if (player.getMainHandItem() == stack || player.getOffhandItem() == stack) {
				time = player.getCooldowns().getCooldownPercent(stack.getItem(), Minecraft.getInstance().getTimer().getGameTimeDeltaTicks());//TODO partial
			}
			CuisineSkilletRenderer.renderItem(time, data, poseStack, bufferSource, light, overlay);
			poseStack.popPose();
		}
	}

}
