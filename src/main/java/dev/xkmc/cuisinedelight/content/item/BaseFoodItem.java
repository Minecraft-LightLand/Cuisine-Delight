package dev.xkmc.cuisinedelight.content.item;

import dev.xkmc.cuisinedelight.content.logic.CookedFoodData;
import dev.xkmc.cuisinedelight.content.logic.JEIDisplayInfo;
import dev.xkmc.cuisinedelight.content.recipe.BaseCuisineRecipe;
import dev.xkmc.cuisinedelight.content.recipe.CuisineRecipeContainer;
import dev.xkmc.cuisinedelight.init.data.LangData;
import dev.xkmc.cuisinedelight.init.registrate.CDItems;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BaseFoodItem extends Item {

	@Nullable
	public static CookedFoodData getData(ItemStack stack) {
		return CDItems.COOKED.get(stack);
	}

	public static void setData(ItemStack stack, @Nullable CookedFoodData data) {
		if (data == null) stack.remove(CDItems.COOKED.get());
		else CDItems.COOKED.set(stack, data);
	}

	public BaseFoodItem(Properties properties) {
		super(properties);
	}

	@Nullable
	@Override
	public FoodProperties getFoodProperties(ItemStack stack, @Nullable LivingEntity entity) {
		CookedFoodData data = getData(stack);
		if (data == null) return null;
		return data.toFoodData();
	}

	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
		CookedFoodData food = getData(stack);
		super.finishUsingItem(stack, level, entity);
		if (food == null) return getCraftingRemainingItem(stack);
		food.size--;
		if (food.size <= 0) return getCraftingRemainingItem(stack);
		stack.setCount(1);
		setData(stack, food);
		return stack;
	}

	@Override
	public int getUseDuration(ItemStack stack, LivingEntity e) {
		var data = getData(stack);
		if (data == null || data.score < 60) {
			return 72000;
		}
		return super.getUseDuration(stack, e);
	}

	@Override
	public InteractionResult useOn(UseOnContext ctx) {
		BlockPos pos = ctx.getClickedPos();
		BlockState state = ctx.getLevel().getBlockState(pos);
		ItemStack stack = ctx.getItemInHand();
		if (state.is(Blocks.COMPOSTER)) {
			int level = state.getValue(ComposterBlock.LEVEL);
			if (level < ComposterBlock.MAX_LEVEL) {
				if (!ctx.getLevel().isClientSide()) {
					var data = getData(stack);
					if (data != null && data.size > 0) {
						if (ctx.getPlayer() == null || !ctx.getPlayer().getAbilities().instabuild) {
							data.size--;
							setData(stack, data);
						}
						if (ctx.getLevel().getRandom().nextDouble() < 0.3f) {
							BlockState newState = state.setValue(ComposterBlock.LEVEL, level + 1);
							ctx.getLevel().setBlockAndUpdate(pos, newState);
							if (level + 1 == ComposterBlock.MAX_LEVEL) {
								ctx.getLevel().scheduleTick(pos, state.getBlock(), 20);
							}
							ctx.getLevel().gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(ctx.getPlayer(), newState));
							ctx.getLevel().levelEvent(LevelEvent.COMPOSTER_FILL, pos, 1);
						} else {
							ctx.getLevel().levelEvent(LevelEvent.COMPOSTER_FILL, pos, 0);
						}
						if (data.size == 0) {
							ItemStack remain = getCraftingRemainingItem(stack);
							stack.shrink(1);
							if (ctx.getPlayer() != null) {
								ctx.getPlayer().getInventory().placeItemBackInInventory(remain);
							} else {
								Block.dropResources(state, ctx.getLevel(), ctx.getClickedPos(), null, null, remain);
							}
						}
					}
				}
				return InteractionResult.SUCCESS;
			}
		}
		return InteractionResult.PASS;
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext level, List<Component> list, TooltipFlag flag) {
		var disp = CDItems.DISPLAY.get(stack);
		if (disp != null) {
			list.add(LangData.INFO_DISPLAY.get(disp.min(), disp.max()));
			return;
		}
		CookedFoodData data = getData(stack);
		if (data == null) {
			list.add(LangData.CREATIVE.get());
			return;
		}
		FoodProperties prop = data.toFoodData();
		if (prop == CookedFoodData.BAD) {
			list.add(LangData.BAD_FOOD.get());
		} else {
			list.add(LangData.SERVE_SIZE.get(data.size));
			list.add(LangData.SCORE.get(data.score));
		}
		if (Screen.hasShiftDown()) {
			for (var e : data.entries) {
				ItemStack ingredient = e.stack();
				if (e.burnt()) {
					list.add(LangData.BAD_BURNT.get(ingredient.getHoverName()));
				}
				if (e.raw()) {
					list.add(LangData.BAD_RAW.get(ingredient.getHoverName()));
				}
				if (e.overcooked()) {
					list.add(LangData.BAD_OVERCOOKED.get(ingredient.getHoverName()));
				}
				if (!e.burnt() && !e.raw() && !e.overcooked()) {
					list.add(LangData.GOOD.get(ingredient.getHoverName()));
				}
			}
		} else {
			list.add(LangData.SHIFT.get());
			CuisineRecipeContainer cont = new CuisineRecipeContainer(data);
			for (var e : cont.list) {
				if (e.isEmpty()) continue;
				double perc = Math.round(1000d * e.getCount() / data.size) / 10d;
				list.add(e.getHoverName().copy().append(": " + perc + "%"));
			}
		}


		for (var e : prop.effects()) {
			MobEffectInstance mobeffectinstance = e.effect();
			MutableComponent mutablecomponent = Component.translatable(mobeffectinstance.getDescriptionId());
			var mobeffect = mobeffectinstance.getEffect();
			if (mobeffectinstance.getAmplifier() > 0) {
				mutablecomponent = Component.translatable("potion.withAmplifier", mutablecomponent, Component.translatable("potion.potency." + mobeffectinstance.getAmplifier()));
			}
			if (mobeffectinstance.getDuration() > 20) {
				mutablecomponent = Component.translatable("potion.withDuration", mutablecomponent, MobEffectUtil.formatDuration(mobeffectinstance, 1, 20));
			}
			list.add(mutablecomponent.withStyle(mobeffect.value().getCategory().getTooltipFormatting()));
		}
	}

	public ItemStack displayStack(BaseCuisineRecipe<?> recipe) {
		return CDItems.DISPLAY.set(getDefaultInstance(),
				new JEIDisplayInfo(recipe.getMinSaturationBonus(), recipe.getMaxSaturationBonus()));

	}
}
