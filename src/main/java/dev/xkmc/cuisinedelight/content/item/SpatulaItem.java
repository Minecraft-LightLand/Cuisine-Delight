package dev.xkmc.cuisinedelight.content.item;

import dev.xkmc.cuisinedelight.content.block.CuisineSkilletBlockEntity;
import dev.xkmc.cuisinedelight.content.logic.CookingData;
import dev.xkmc.cuisinedelight.init.registrate.CDItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import vectorwing.farmersdelight.common.registry.ModSounds;

public class SpatulaItem extends Item {

	public static final int ANIM_TIME = 20;

	public SpatulaItem(Properties pProperties) {
		super(pProperties);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack spatulaStack = player.getItemInHand(hand);
		InteractionHand otherHand = hand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
		ItemStack skilletStack = player.getItemInHand(otherHand);
		if (!skilletStack.is(CDItems.SKILLET.get())) {
			return InteractionResultHolder.pass(spatulaStack);
		}
		CookingData data = CuisineSkilletItem.getData(skilletStack);
		if (data != null) {
			if (!level.isClientSide()) {
				data.stir(level.getGameTime(), SpatulaItem.getReduction(spatulaStack));
				CuisineSkilletItem.setData(skilletStack, data);
				player.getCooldowns().addCooldown(this, ANIM_TIME);
				player.getCooldowns().addCooldown(CDItems.SKILLET.get(), ANIM_TIME);
			} else {
				CuisineSkilletItem.playSound(player, level, ModSounds.BLOCK_SKILLET_ADD_FOOD.get());
			}
			return InteractionResultHolder.success(spatulaStack);
		}
		return InteractionResultHolder.fail(spatulaStack);
	}

	private static int getReduction(ItemStack stack) {
		return stack.getEnchantmentLevel(Enchantments.SILK_TOUCH) > 0 ? 20 : 0;
	}

	@Override
	public InteractionResult useOn(UseOnContext ctx) {
		Level level = ctx.getLevel();
		Player player = ctx.getPlayer();
		if (level.getBlockEntity(ctx.getClickedPos()) instanceof CuisineSkilletBlockEntity be) {
			if (be.cookingData.contents.size() > 0) {
				if (!level.isClientSide()) {
					be.stir(level.getGameTime(), getReduction(ctx.getItemInHand()));
					if (player != null) {
						player.getCooldowns().addCooldown(CDItems.SPATULA.get(), ANIM_TIME);
					}
				} else if (player != null) {
					CuisineSkilletItem.playSound(player, level, ModSounds.BLOCK_SKILLET_SIZZLE.get());
				}
			}
		}
		return InteractionResult.PASS;
	}

}
