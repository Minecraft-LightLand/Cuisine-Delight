package dev.xkmc.cuisinedelight.content.block;

import dev.xkmc.cuisinedelight.content.item.CuisineSkilletItem;
import dev.xkmc.cuisinedelight.content.item.SpatulaItem;
import dev.xkmc.cuisinedelight.content.logic.CookingData;
import dev.xkmc.cuisinedelight.content.logic.IngredientConfig;
import dev.xkmc.cuisinedelight.init.data.CDConfig;
import dev.xkmc.cuisinedelight.init.registrate.CDItems;
import dev.xkmc.l2library.base.tile.BaseBlockEntity;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import vectorwing.farmersdelight.common.block.entity.HeatableBlockEntity;
import vectorwing.farmersdelight.common.registry.ModSounds;

import javax.annotation.Nonnull;
import java.util.List;

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
        if (!pLevel.isClientSide) {
            for (ItemEntity itemEntity : getItemsAtAndAbove(pLevel, pPos)) {
                ItemStack heldStack = itemEntity.getItem();
                IngredientConfig.IngredientEntry config = IngredientConfig.get().getEntry(heldStack);
                if (config != null) {
                    if (!this.canCook()) {
                        return;
                    }

                    if (this.cookingData.contents.size() >= CDConfig.COMMON.maxIngredient.get()) {
                        return;
                    }

                    int count = 1 + this.baseItem.getEnchantmentLevel(Enchantments.BLOCK_EFFICIENCY);
                    if (this.slowCook()) {
                        this.cookingData.setSpeed(0.5F);
                    }
                    ItemStack add = heldStack.split(count);
                    this.cookingData.addItem(add, pLevel.getGameTime());
                    ItemStack remain = add.getCraftingRemainingItem();
                    remain.setCount(add.getCount());
                    itemEntity.setItem(remain);
                    if (remain.isEmpty()) {
                        itemEntity.discard();
                    }
                    pLevel.playSound(null, pPos.getX(), pPos.getY(), pPos.getZ(), ModSounds.BLOCK_SKILLET_ADD_FOOD.get(),
                            SoundSource.BLOCKS, 0.4F, pLevel.random.nextFloat() * 0.2F + 0.9F);
                    this.sync();
                }
            }
        }
	}

	public static List<ItemEntity> getItemsAtAndAbove(Level level, BlockPos pos) {
		return CuisineSkilletBlock.SHAPE_WITH_ABOVE.toAabbs().stream().flatMap((aabb) ->
				level.getEntitiesOfClass(ItemEntity.class, aabb.move(pos.getX(), pos.getY(), pos.getZ()), EntitySelector.ENTITY_STILL_ALIVE).stream()).toList();
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

	public boolean slowCook() {
		return baseItem.getEnchantmentLevel(Enchantments.FIRE_ASPECT) == 1 &&
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
