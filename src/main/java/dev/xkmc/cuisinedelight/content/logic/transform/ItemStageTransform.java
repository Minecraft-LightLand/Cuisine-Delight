package dev.xkmc.cuisinedelight.content.logic.transform;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public record ItemStageTransform(Stage stage, Item next) implements CookTransform {

	@Override
	public ItemStack renderStack(ItemStack stack) {
		ItemStack ans = next.getDefaultInstance();
		ans.setCount(stack.getCount());
		ans.setTag(stack.getTag());
		return ans;
	}

}
