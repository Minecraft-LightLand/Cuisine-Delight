package dev.xkmc.cuisine_delight.content.block;

import dev.xkmc.cuisine_delight.content.item.CuisineSkilletItem;
import dev.xkmc.cuisine_delight.content.item.SpatulaItem;
import dev.xkmc.cuisine_delight.content.logic.CookingData;
import dev.xkmc.cuisine_delight.init.CDItems;
import dev.xkmc.l2library.base.tile.BaseBlockEntity;
import dev.xkmc.l2library.serial.SerialClass;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

@SerialClass
public class CuisineSkilletBlockEntity extends BaseBlockEntity {

	@SerialClass.SerialField(toClient = true)
	public ItemStack baseItem = CDItems.SKILLET.asStack();

	@SerialClass.SerialField(toClient = true)
	public CookingData cookingData = new CookingData();

	@SerialClass.SerialField(toClient = true)
	private boolean updateStir = false;

	private int stirTimer = 0;

	public CuisineSkilletBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	public void clientTick(Level pLevel, BlockPos pPos, BlockState pState) {
		if (stirTimer > 0) {
			stirTimer--;
		}
	}

	@SerialClass.OnInject
	public void onInject() {
		if (updateStir) {
			updateStir = false;
			stirTimer = SpatulaItem.ANIM_TIME;
		}
	}

	public void serverTick(Level pLevel, BlockPos pPos, BlockState pState) {
	}

	public boolean isCooking() {
		return cookingData.contents.size() > 0;
	}

	public NonNullList<ItemStack> getItems() {
		return NonNullList.create();
	}

	public void setSkilletItem(ItemStack stack) {
		baseItem = stack.copy();
		cookingData = CuisineSkilletItem.getData(stack);
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
		return true;//TODO predicate
	}

	public float getStirPercent(float pTick) {
		return Math.max(0, stirTimer - pTick) / SpatulaItem.ANIM_TIME;
	}

	public void stir(long gameTime) {
		cookingData.stir(gameTime);
		updateStir = true;
		sync();
		updateStir = false;
	}
}
