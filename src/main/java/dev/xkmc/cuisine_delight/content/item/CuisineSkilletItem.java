package dev.xkmc.cuisine_delight.content.item;

import dev.xkmc.cuisine_delight.content.logic.CookingData;
import dev.xkmc.cuisine_delight.content.logic.IngredientConfig;
import dev.xkmc.l2library.serial.codec.TagCodec;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class CuisineSkilletItem extends Item {

	private static final String KEY_ROOT = "CookingData";

	@Nullable
	public static CookingData getData(ItemStack stack) {
		var tag = stack.getTagElement(KEY_ROOT);
		if (tag == null) return null;
		return TagCodec.fromTag(tag, CookingData.class);
	}

	public static void setData(ItemStack stack, @Nullable CookingData data) {
		if (data == null) {
			stack.getOrCreateTag().remove(KEY_ROOT);
			return;
		}
		var tag = TagCodec.valueToTag(data);
		if (tag != null) {
			stack.getOrCreateTag().put(KEY_ROOT, tag);
		}
	}

	public CuisineSkilletItem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack skilletStack = player.getItemInHand(hand);
		InteractionHand otherHand = hand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
		ItemStack otherStack = player.getItemInHand(otherHand);
		if (!level.isClientSide()) {
			if (!otherStack.isEmpty()) {
				IngredientConfig.IngredientEntry entry = IngredientConfig.get().getEntry(otherStack);
				if (entry != null) {
					long time = level.getGameTime();
					CookingData data = getData(skilletStack);
					if (data == null) {
						data = new CookingData();
					}
					data.addItem(otherStack.split(1), time);
					setData(skilletStack, data);
				}
			}
		}
		return InteractionResultHolder.fail(skilletStack);
	}

	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		return false;
	}

	@Override
	public void initializeClient(Consumer<IClientItemExtensions> consumer) {
		consumer.accept(SkilletBEWLR.EXTENSIONS);
	}

}
