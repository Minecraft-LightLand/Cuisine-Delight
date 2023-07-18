package dev.xkmc.cuisine_delight.init;

import com.tterrag.registrate.util.entry.BlockEntityEntry;
import com.tterrag.registrate.util.entry.BlockEntry;
import dev.xkmc.cuisine_delight.content.block.*;
import dev.xkmc.cuisine_delight.content.client.CuisineSkilletRenderer;
import dev.xkmc.cuisine_delight.init.data.CopySkilletFunction;
import dev.xkmc.l2library.util.data.LootTableTemplate;
import dev.xkmc.l2modularblock.DelegateBlock;
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
	public static final BlockEntry<DelegateBlock> DARK_POT;

	public static final BlockEntityEntry<CuisineSkilletBlockEntity> BE_SKILLET;
	public static final BlockEntityEntry<DarkPotBlockEntity> BE_DARK_POT;

	static {
		SKILLET = CuisineDelight.REGISTRATE.block("cuisine_skillet", p -> new CuisineSkilletBlock())
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

		DARK_POT = CuisineDelight.REGISTRATE.block("dark_cooking_pot", p -> DelegateBlock.newBaseBlock(
						BlockBehaviour.Properties.of().mapColor(MapColor.METAL)
								.strength(0.5F, 6.0F).sound(SoundType.LANTERN),
						DarkPotBlock.ENTITY, DarkPotBlock.STATE, DarkPotBlock.TRAY))
				.blockstate(DarkPotModelGen::build)
				.defaultLang().simpleItem().register();

		BE_DARK_POT = CuisineDelight.REGISTRATE.blockEntity("dark_cooking_pot", DarkPotBlockEntity::new)
				.validBlock(DARK_POT).register();

	}

	public static void register() {
	}

}
