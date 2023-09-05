package dev.xkmc.cuisinedelight.content.block;

import dev.xkmc.cuisinedelight.content.item.CuisineSkilletItem;
import dev.xkmc.cuisinedelight.content.item.SpatulaItem;
import dev.xkmc.cuisinedelight.content.logic.CookingData;
import dev.xkmc.cuisinedelight.init.registrate.CDItems;
import dev.xkmc.l2library.base.tile.BaseBlockEntity;
import dev.xkmc.l2library.serial.SerialClass;
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

	@SerialClass.SerialField(toClient = true)
	public ItemStack baseItem = CDItems.SKILLET.asStack();

	@Nonnull
	@SerialClass.SerialField(toClient = true)
	public CookingData cookingData = new CookingData();

	@SerialClass.SerialField(toClient = true)
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
		return cookingData.contents.size() > 0;
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
		if (cookingData.contents.size() > 0) {
			CuisineSkilletItem.setData(ans, cookingData);
		}
		return ans;
	}

	public boolean canCook() {
		return baseItem.getEnchantmentLevel(Enchantments.FIRE_ASPECT) > 0 ||
				this.level != null && this.isHeated(this.level, this.getBlockPos());
	}

	public float getStirPercent(float pTick) {
		return Math.max(0, stirTimer - pTick) / SpatulaItem.ANIM_TIME;
	}

	public void stir(long gameTime) {
		cookingData.stir(gameTime);
		stirTimer = SpatulaItem.ANIM_TIME;
		sync();
	}
}
