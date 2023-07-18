package dev.xkmc.cuisine_delight.content.block;

import dev.xkmc.l2modularblock.mult.CreateBlockStateBlockMethod;
import dev.xkmc.l2modularblock.mult.DefaultStateBlockMethod;
import dev.xkmc.l2modularblock.mult.PlacementBlockMethod;
import dev.xkmc.l2modularblock.mult.ShapeUpdateBlockMethod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import vectorwing.farmersdelight.common.tag.ModTags;

public class DarkPotTrayMethod implements DefaultStateBlockMethod, CreateBlockStateBlockMethod, PlacementBlockMethod, ShapeUpdateBlockMethod {

	public static final BooleanProperty SUPPORT = BooleanProperty.create("support");

	@Override
	public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(SUPPORT);
	}

	@Override
	public BlockState getDefaultState(BlockState state) {
		state.setValue(SUPPORT, false);
		return state;
	}

	@Override
	public BlockState getStateForPlacement(BlockState state, BlockPlaceContext ctx) {
		return ctx.getLevel().getBlockState(ctx.getClickedPos().below()).is(ModTags.TRAY_HEAT_SOURCES) ? state.setValue(SUPPORT, true) : state;
	}

	@Override
	public BlockState updateShape(Block block, BlockState current, BlockState original, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
		return facing == Direction.DOWN ? current.setValue(SUPPORT, facingState.is(ModTags.TRAY_HEAT_SOURCES)) : current;
	}

}
