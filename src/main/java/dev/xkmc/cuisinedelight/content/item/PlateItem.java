package dev.xkmc.cuisinedelight.content.item;

import dev.xkmc.cuisinedelight.content.block.CuisineSkilletBlockEntity;
import dev.xkmc.cuisinedelight.content.logic.CookedFoodData;
import dev.xkmc.cuisinedelight.content.logic.CookingData;
import dev.xkmc.cuisinedelight.content.recipe.BaseCuisineRecipe;
import dev.xkmc.cuisinedelight.init.registrate.CDItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;

public class PlateItem extends Item {

	public interface ReturnTarget {

		void addItem(ItemStack foodStack);

		void addExp(int i);

	}

	public record PlayerTarget(Player player) implements ReturnTarget {

		@Override
		public void addItem(ItemStack foodStack) {
			player.getInventory().placeItemBackInInventory(foodStack);
		}

		@Override
		public void addExp(int i) {
			ExperienceOrb.award((ServerLevel) player.level, player.position(), i);
		}

	}

	public record BlockTarget(UseOnContext ctx) implements ReturnTarget {

		@Override
		public void addItem(ItemStack foodStack) {
			Block.popResource(ctx.getLevel(), ctx.getClickedPos(), foodStack);
		}

		@Override
		public void addExp(int i) {
			ExperienceOrb.award((ServerLevel) ctx.getLevel(), Vec3.atCenterOf(ctx.getClickedPos()), i);
		}
	}

	public PlateItem(Properties pProperties) {
		super(pProperties);
	}

	private void giveBack(ItemStack foodStack, CookedFoodData food, ReturnTarget target) {
		target.addItem(foodStack);
		target.addExp(food.score * food.size / 100);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack plateStack = player.getItemInHand(hand);
		InteractionHand otherHand = hand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
		ItemStack skilletStack = player.getItemInHand(otherHand);
		if (!skilletStack.is(CDItems.SKILLET.get())) {
			return InteractionResultHolder.pass(plateStack);
		}
		CookingData data = CuisineSkilletItem.getData(skilletStack);
		if (data == null) {
			return InteractionResultHolder.pass(plateStack);
		}
		if (!level.isClientSide()) {
			CuisineSkilletItem.setData(skilletStack, null);
			data.stir(level.getGameTime(), 0);
			CookedFoodData food = new CookedFoodData(data);
			ItemStack foodStack = BaseCuisineRecipe.findBestMatch(level, food);
			plateStack.shrink(1);
			giveBack(foodStack, food, new PlayerTarget(player));
		}
		return InteractionResultHolder.success(plateStack);
	}

	@Override
	public InteractionResult useOn(UseOnContext ctx) {
		Level level = ctx.getLevel();
		Player player = ctx.getPlayer();
		if (level.getBlockEntity(ctx.getClickedPos()) instanceof CuisineSkilletBlockEntity be) {
			if (be.cookingData.contents.isEmpty()) {
				return InteractionResult.PASS;
			}
			if (!level.isClientSide()) {
				CookingData data = be.cookingData;
				data.stir(level.getGameTime(), 0);
				CookedFoodData food = new CookedFoodData(data);
				ItemStack foodStack = BaseCuisineRecipe.findBestMatch(level, food);
				ctx.getItemInHand().shrink(1);
				if (player != null) {
					giveBack(foodStack, food, new PlayerTarget(player));
				} else {
					giveBack(foodStack, food, new BlockTarget(ctx));
				}
				be.cookingData = new CookingData();
				be.sync();
			}
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.PASS;
	}
}
