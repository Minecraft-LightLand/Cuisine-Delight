package dev.xkmc.cuisine_delight.content.block;

import dev.xkmc.cuisine_delight.init.CDBlocks;
import dev.xkmc.l2modularblock.impl.BlockEntityBlockMethodImpl;
import dev.xkmc.l2modularblock.mult.CreateBlockStateBlockMethod;
import dev.xkmc.l2modularblock.mult.DefaultStateBlockMethod;
import dev.xkmc.l2modularblock.mult.OnClickBlockMethod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

public class DarkPotBlock implements DefaultStateBlockMethod, CreateBlockStateBlockMethod, OnClickBlockMethod {

	public static final BlockEntityBlockMethodImpl<DarkPotBlockEntity> ENTITY =
			new BlockEntityBlockMethodImpl<>(CDBlocks.BE_DARK_POT, DarkPotBlockEntity.class);

	public static final DarkPotBlock STATE = new DarkPotBlock();
	public static final DarkPotTrayMethod TRAY = new DarkPotTrayMethod();

	protected static final BooleanProperty[] DIRE = {
			BlockStateProperties.SOUTH,
			BlockStateProperties.WEST,
			BlockStateProperties.NORTH,
			BlockStateProperties.EAST
	};

	@Override
	public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(DIRE);
	}

	@Override
	public BlockState getDefaultState(BlockState state) {
		for (var e : DIRE) {
			state = state.setValue(e, false);
		}
		return state;
	}

	@Override
	public InteractionResult onClick(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		Direction dire = hit.getDirection();
		if (dire.getAxis() == Direction.Axis.Y) return InteractionResult.PASS;
		ItemStack stack = player.getItemInHand(hand);
		if (!stack.is(CDBlocks.DARK_POT.asItem())) return InteractionResult.PASS;
		int ni = findDist(level, pos, state, dire.getCounterClockWise());
		int pi = findDist(level, pos, state, dire.getClockWise());
		int total = ni + pi + 1;
		if (stack.getCount() < total) return InteractionResult.FAIL;
		if (!checkPlaceable(level, pos, dire, ni, pi)) return InteractionResult.FAIL;
		var xpos = pos.mutable().move(dire.getCounterClockWise(), ni);
		for (int i = -ni; i <= pi; i++) {
			level.setBlockAndUpdate(pos, level.getBlockState(pos).setValue(DIRE[dire.get2DDataValue()], true));
			BlockState target = state.getBlock().defaultBlockState().setValue(DIRE[dire.getOpposite().get2DDataValue()], true);
			if (i != -ni) {
				target = target.setValue(DIRE[dire.getCounterClockWise().get2DDataValue()], true);
			}
			if (i != pi) {
				target = target.setValue(DIRE[dire.getClockWise().get2DDataValue()], true);
			}
			level.setBlockAndUpdate(pos.relative(dire), target);
			xpos.move(dire.getClockWise());
		}
		return InteractionResult.SUCCESS;
	}

	private static int findDist(Level level, BlockPos pos, BlockState state, Direction dire) {
		int ans = 0;
		var xpos = pos.mutable();
		BlockState xstate = state;
		while (xstate.is(state.getBlock()) && xstate.getValue(DIRE[dire.get2DDataValue()])) {
			ans++;
			xpos.move(dire);
			xstate = level.getBlockState(xpos);
		}
		return ans;
	}

	private static boolean checkPlaceable(Level level, BlockPos pos, Direction dire, int ni, int pi) {
		var xpos = pos.mutable().move(dire).move(dire.getCounterClockWise(), ni);
		for (int i = -ni; i <= pi; i++) {
			if (!level.getBlockState(xpos).canBeReplaced())
				return false;
			xpos.move(dire.getClockWise());
		}
		return true;
	}

}
