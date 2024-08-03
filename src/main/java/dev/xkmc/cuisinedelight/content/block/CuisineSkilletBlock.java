package dev.xkmc.cuisinedelight.content.block;

import dev.xkmc.cuisinedelight.content.item.CuisineSkilletItem;
import dev.xkmc.cuisinedelight.content.logic.IngredientConfig;
import dev.xkmc.cuisinedelight.init.data.CDConfig;
import dev.xkmc.cuisinedelight.init.data.LangData;
import dev.xkmc.cuisinedelight.init.registrate.CDBlocks;
import dev.xkmc.cuisinedelight.init.registrate.CDItems;
import dev.xkmc.l2core.init.reg.ench.EnchHelper;
import dev.xkmc.l2serial.util.Wrappers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.block.SkilletBlock;
import vectorwing.farmersdelight.common.registry.ModSounds;

public class CuisineSkilletBlock extends SkilletBlock {

	public CuisineSkilletBlock(Properties properties) {
		super(properties);
	}

	@Override
	public ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		BlockEntity tileEntity = level.getBlockEntity(pos);
		if (tileEntity instanceof CuisineSkilletBlockEntity be) {
			ItemStack heldStack = player.getItemInHand(hand);
			var config = IngredientConfig.get().getEntry(heldStack);
			if (config != null) {
				if (!be.canCook()) {
					if (player instanceof ServerPlayer serverPlayer) {
						serverPlayer.sendSystemMessage(LangData.MSG_NO_HEAT.get(), true);
					}
					return ItemInteractionResult.FAIL;
				}
				if (be.cookingData.contents.size() >= CDConfig.SERVER.maxIngredient.get()) {
					if (!level.isClientSide()) {
						((ServerPlayer) player).sendSystemMessage(LangData.MSG_FULL.get(), true);
					}
					return ItemInteractionResult.FAIL;
				}
				if (!level.isClientSide) {
					int count = 1 + EnchHelper.getLv(be.baseItem, Enchantments.EFFICIENCY);
					if (be.slowCook()) {
						be.cookingData.setSpeed(0.5f);
					}
					ItemStack add = heldStack.split(count);
					be.cookingData.addItem(add, level.getGameTime());
					ItemStack remain = add.getCraftingRemainingItem();
					remain.setCount(add.getCount());
					player.getInventory().placeItemBackInInventory(remain);
					be.sync();
				} else {
					CuisineSkilletItem.playSound(player, level, ModSounds.BLOCK_SKILLET_ADD_FOOD.get());
				}
				return ItemInteractionResult.SUCCESS;
			}
		}
		return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
	}

	@Override
	public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
		if (state.getBlock() != newState.getBlock()) {
			BlockEntity tileEntity = level.getBlockEntity(pos);
			if (tileEntity instanceof CuisineSkilletBlockEntity be) {
				Containers.dropContents(level, pos, be.getItems());
			}
			super.onRemove(state, level, pos, newState, isMoving);
		}
	}

	@Override
	public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state) {
		if (level.getBlockEntity(pos) instanceof CuisineSkilletBlockEntity be) {
			return be.toItemStack();
		}
		return CDItems.SKILLET.asStack();
	}

	@Override
	public void animateTick(BlockState stateIn, Level level, BlockPos pos, RandomSource rand) {
		BlockEntity tileEntity = level.getBlockEntity(pos);
		if (tileEntity instanceof CuisineSkilletBlockEntity skilletEntity) {
			if (skilletEntity.isCooking()) {
				double x = pos.getX() + 0.5D;
				double y = pos.getY();
				double z = pos.getZ() + 0.5D;
				if (rand.nextInt(10) == 0) {
					level.playLocalSound(x, y, z, ModSounds.BLOCK_SKILLET_SIZZLE.get(), SoundSource.BLOCKS,
							0.4F, rand.nextFloat() * 0.2F + 0.9F, false);
				}
			}
		}
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return CDBlocks.BE_SKILLET.create(pos, state);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
		return type == CDBlocks.BE_SKILLET.get() ? Wrappers.cast(getTicker()) : null;
	}

	private static BlockEntityTicker<CuisineSkilletBlockEntity> getTicker() {
		return (level, pos, state, be) -> be.tick(level, pos, state);
	}

}
