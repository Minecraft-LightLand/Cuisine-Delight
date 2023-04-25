package dev.xkmc.cuisine_delight.content.item;

import dev.xkmc.cuisine_delight.content.logic.CookedFoodData;
import dev.xkmc.cuisine_delight.init.data.LangData;
import dev.xkmc.l2library.serial.codec.TagCodec;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BaseFoodItem extends Item {

	private static final String KEY_ROOT = "CookedFoodData";

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

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
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

}
