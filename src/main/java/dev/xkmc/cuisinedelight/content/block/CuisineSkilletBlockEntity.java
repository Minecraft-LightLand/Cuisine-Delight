package dev.xkmc.cuisinedelight.content.block;

import dev.xkmc.cuisinedelight.content.item.CuisineSkilletItem;
import dev.xkmc.cuisinedelight.content.item.SpatulaItem;
import dev.xkmc.cuisinedelight.content.logic.CookingData;
import dev.xkmc.cuisinedelight.content.logic.EnchHelper;
import dev.xkmc.cuisinedelight.init.registrate.CDItems;
import dev.xkmc.l2core.base.tile.BaseBlockEntity;
import dev.xkmc.l2serial.serialization.marker.SerialClass;
import dev.xkmc.l2serial.serialization.marker.SerialField;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import vectorwing.farmersdelight.common.block.entity.HeatableBlockEntity;

import javax.annotation.Nonnull;

@SerialClass
public class CuisineSkilletBlockEntity extends BaseBlockEntity implements HeatableBlockEntity {

	@SerialField
	public ItemStack baseItem = CDItems.SKILLET.asStack();

	@Nonnull
	@SerialField
	public CookingData cookingData = new CookingData();

	@SerialField
	private int stirTimer = 0;

	public CuisineSkilletBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
		if (stirTimer > 0) {
			stirTimer--;
		}
	}

	public boolean isCooking() {
		return !cookingData.contents.isEmpty();
	}

	public NonNullList<ItemStack> getItems() {
		return NonNullList.create();
	}

	public void setSkilletItem(ItemStack stack) {
		baseItem = stack.copy();
		var data = CuisineSkilletItem.getData(stack);
		if (data != null) {
			cookingData = data;
		}
		CuisineSkilletItem.setData(baseItem, null);
		sync();
	}

	public ItemStack toItemStack() {
		ItemStack ans = baseItem.copy();
		if (!cookingData.contents.isEmpty()) {
			CuisineSkilletItem.setData(ans, cookingData);
		}
		return ans;
	}

	public boolean canCook() {
		return this.level != null && this.isHeated(this.level, this.getBlockPos()) ||
				EnchHelper.getEnchLevel(baseItem, Enchantments.FIRE_ASPECT) > 0;
	}

	public boolean slowCook() {
		return EnchHelper.getEnchLevel(baseItem, Enchantments.FIRE_ASPECT) == 1 &&
				this.level != null && !this.isHeated(this.level, this.getBlockPos());
	}

	public float getStirPercent(float pTick) {
		return Math.max(0, stirTimer - pTick) / SpatulaItem.ANIM_TIME;
	}

	public void stir(long gameTime, int reduce) {
		cookingData.stir(gameTime, reduce);
		stirTimer = SpatulaItem.ANIM_TIME;
		sync();
	}
}
