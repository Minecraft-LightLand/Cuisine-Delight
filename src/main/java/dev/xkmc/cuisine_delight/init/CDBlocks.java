package dev.xkmc.cuisine_delight.init;

import dev.xkmc.cuisine_delight.content.block.CuisineSkilletBlock;
import dev.xkmc.cuisine_delight.content.block.CuisineSkilletBlockEntity;
import dev.xkmc.cuisine_delight.content.client.CuisineSkilletRenderer;
import dev.xkmc.l2library.repack.registrate.util.entry.BlockEntityEntry;
import dev.xkmc.l2library.repack.registrate.util.entry.BlockEntry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import vectorwing.farmersdelight.common.block.SkilletBlock;

public class CDBlocks {

	public static final BlockEntry<CuisineSkilletBlock> SKILLET;

	public static final BlockEntityEntry<CuisineSkilletBlockEntity> BE_SKILLET;

	static {

		SKILLET = CuisineDelight.REGISTRATE.block("cuisine_skillet", p -> new CuisineSkilletBlock())
				.blockstate((ctx, pvd) -> pvd.getVariantBuilder(ctx.getEntry()).forAllStates(e ->
						ConfiguredModel.builder().modelFile(new ModelFile.UncheckedModelFile(
										e.getValue(SkilletBlock.SUPPORT) ?
												new ResourceLocation(CuisineDelight.MODID, "block/cuisine_skillet_tray") :
												new ResourceLocation(CuisineDelight.MODID, "block/cuisine_skillet"))
								).rotationY(((int) e.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180) % 360)
								.build())).defaultLoot().register();

		BE_SKILLET = CuisineDelight.REGISTRATE.blockEntity("cuisine_skillet", CuisineSkilletBlockEntity::new)
				.validBlock(SKILLET).renderer(() -> CuisineSkilletRenderer::new).register();
	}

	public static void register() {
	}

}
