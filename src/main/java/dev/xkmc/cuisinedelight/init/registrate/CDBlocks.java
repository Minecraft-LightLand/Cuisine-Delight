package dev.xkmc.cuisinedelight.init.registrate;

import com.tterrag.registrate.util.entry.BlockEntityEntry;
import com.tterrag.registrate.util.entry.BlockEntry;
import dev.xkmc.cuisinedelight.content.block.CuisineSkilletBlock;
import dev.xkmc.cuisinedelight.content.block.CuisineSkilletBlockEntity;
import dev.xkmc.cuisinedelight.content.client.CuisineSkilletRenderer;
import dev.xkmc.cuisinedelight.init.CuisineDelight;
import dev.xkmc.cuisinedelight.init.data.CopySkilletFunction;
import dev.xkmc.l2library.util.data.LootTableTemplate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import vectorwing.farmersdelight.common.block.SkilletBlock;

public class CDBlocks {

	public static final BlockEntry<CuisineSkilletBlock> SKILLET;

	public static final BlockEntityEntry<CuisineSkilletBlockEntity> BE_SKILLET;

	static {
		SKILLET = CuisineDelight.REGISTRATE.block("cuisine_skillet", p -> new CuisineSkilletBlock(
						BlockBehaviour.Properties.of().mapColor(MapColor.METAL)
								.strength(0.5F, 6.0F).sound(SoundType.LANTERN)))
				.blockstate((ctx, pvd) -> pvd.getVariantBuilder(ctx.getEntry()).forAllStates(e ->
						ConfiguredModel.builder().modelFile(new ModelFile.UncheckedModelFile(
										e.getValue(SkilletBlock.SUPPORT) ?
												new ResourceLocation(CuisineDelight.MODID, "block/cuisine_skillet_tray") :
												new ResourceLocation(CuisineDelight.MODID, "block/cuisine_skillet"))
								).rotationY(((int) e.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180) % 360)
								.build()))
				.loot((loot, block) -> loot.add(block,
						LootTable.lootTable().withPool(
								LootTableTemplate.getPool(1, 0).add(
										LootItem.lootTableItem(CDItems.SKILLET.get())
												.apply(CopySkilletFunction.builder())
								))))
				.register();

		BE_SKILLET = CuisineDelight.REGISTRATE.blockEntity("cuisine_skillet", CuisineSkilletBlockEntity::new)
				.validBlock(SKILLET).renderer(() -> CuisineSkilletRenderer::new).register();
	}

	public static void register() {
	}

}
