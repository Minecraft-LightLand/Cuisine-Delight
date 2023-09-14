package dev.xkmc.cuisinedelight.content.item;

import dev.xkmc.cuisinedelight.content.logic.CookedFoodData;
import dev.xkmc.cuisinedelight.content.recipe.BaseCuisineRecipe;
import dev.xkmc.cuisinedelight.content.recipe.CuisineRecipeContainer;
import dev.xkmc.cuisinedelight.init.data.LangData;
import dev.xkmc.l2library.serial.codec.TagCodec;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
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

	private static final String KEY_ROOT = "CookedFoodData";
	private static final String KEY_DISPLAY = "Display";

	public static ItemStack setResultDisplay(BaseCuisineRecipe<?> recipe, ItemStack stack) {
		CompoundTag tag = new CompoundTag();
		tag.putDouble("min", recipe.getMinSaturationBonus());
		tag.putDouble("max", recipe.getMaxSaturationBonus());
		stack.getOrCreateTag().put(KEY_DISPLAY, tag);
		return stack;
	}

	public static ItemStack setIngredientDisplay(ItemStack stack) {
		stack.getOrCreateTag().putBoolean("cuisinedelight:display", true);
		return stack;
	}

	@Nullable
	public static CookedFoodData getData(ItemStack stack) {
		var tag = stack.getTagElement(KEY_ROOT);
		if (tag == null) return null;
		return TagCodec.fromTag(tag, CookedFoodData.class);
	}

	public static void setData(ItemStack stack, @Nullable CookedFoodData data) {
		if (data == null) {
			stack.getOrCreateTag().remove(KEY_ROOT);
			return;
		}
		var tag = TagCodec.valueToTag(data);
		if (tag != null) {
			stack.getOrCreateTag().put(KEY_ROOT, tag);
		}
	}

	public BaseFoodItem(Properties properties) {
		super(properties);
	}

	@Override
	public boolean isEdible() {
		return true;
	}

	@Override
	public FoodProperties getFoodProperties(ItemStack stack, @Nullable LivingEntity entity) {
		CookedFoodData data = getData(stack);
		if (data == null) return CookedFoodData.BAD;
		return data.toFoodData();
	}

	@Nullable
	@Override
	public FoodProperties getFoodProperties() {
		return CookedFoodData.BAD;
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
	public int getUseDuration(ItemStack stack) {
		var data = getData(stack);
		if (data == null || data.score < 60) {
			return 72000;
		}
		return super.getUseDuration(stack);
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
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		if (stack.hasTag()) {
			var tag = stack.getTagElement(KEY_DISPLAY);
			if (tag != null) {
				double min = tag.getDouble("min");
				double max = tag.getDouble("max");
				if (max > 0) {
					list.add(LangData.INFO_DISPLAY.get(min, max));
				}
				return;
			}
		}
		CookedFoodData data = getData(stack);
		if (data == null) {
			list.add(LangData.BAD_FOOD.get());
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


		for (var e : prop.getEffects()) {
			MobEffectInstance mobeffectinstance = e.getFirst();
			MutableComponent mutablecomponent = Component.translatable(mobeffectinstance.getDescriptionId());
			MobEffect mobeffect = mobeffectinstance.getEffect();
			if (mobeffectinstance.getAmplifier() > 0) {
				mutablecomponent = Component.translatable("potion.withAmplifier", mutablecomponent, Component.translatable("potion.potency." + mobeffectinstance.getAmplifier()));
			}
			if (mobeffectinstance.getDuration() > 20) {
				mutablecomponent = Component.translatable("potion.withDuration", mutablecomponent, MobEffectUtil.formatDuration(mobeffectinstance, 1));
			}
			list.add(mutablecomponent.withStyle(mobeffect.getCategory().getTooltipFormatting()));
		}
	}

	public ItemStack displayStack(BaseCuisineRecipe<?> recipe) {
		ItemStack ans = getDefaultInstance();
		var ctag = ans.getOrCreateTagElement(KEY_DISPLAY);
		ctag.putDouble("min", recipe.getMinSaturationBonus());
		ctag.putDouble("max", recipe.getMaxSaturationBonus());
		return ans;
	}
}
