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


	public PlateItem(Properties pProperties) {
		super(pProperties);
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
			data.stir(level.getGameTime());
			CookedFoodData food = new CookedFoodData(data);
			ItemStack foodStack = BaseCuisineRecipe.findBestMatch(level, food);
			plateStack.shrink(1);
			player.getInventory().placeItemBackInInventory(foodStack);
			ExperienceOrb.award((ServerLevel) level, player.position(), food.score * food.size / 100);
		}
		return InteractionResultHolder.success(plateStack);
	}

	@Override
	public InteractionResult useOn(UseOnContext ctx) {
		Level level = ctx.getLevel();
		Player player = ctx.getPlayer();
		if (level.getBlockEntity(ctx.getClickedPos()) instanceof CuisineSkilletBlockEntity be) {
			if (be.cookingData.contents.size() == 0) {
				return InteractionResult.PASS;
			}
			if (!level.isClientSide()) {
				CookingData data = be.cookingData;
				data.stir(level.getGameTime());
				CookedFoodData food = new CookedFoodData(data);
				ItemStack foodStack = BaseCuisineRecipe.findBestMatch(level, food);
				ctx.getItemInHand().shrink(1);
				if (player != null) {
					player.getInventory().placeItemBackInInventory(foodStack);
					ExperienceOrb.award((ServerLevel) level, player.position(), food.score * food.size / 100);
				} else {
					Block.popResource(level, ctx.getClickedPos(), foodStack);
					ExperienceOrb.award((ServerLevel) level, Vec3.atCenterOf(ctx.getClickedPos()), food.score * food.size / 100);
				}
				be.cookingData = new CookingData();
				be.sync();
			}
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.PASS;
	}
}
