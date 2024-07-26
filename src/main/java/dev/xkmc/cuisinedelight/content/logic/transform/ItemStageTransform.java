package dev.xkmc.cuisinedelight.content.logic.transform;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public record ItemStageTransform(Stage stage, Item next) implements CookTransform {

	@Override
	public ItemStack renderStack(Stage stage, ItemStack stack) {
		if (stage.ordinal() < this.stage.ordinal()) return stack;
		ItemStack ans = next.getDefaultInstance();
		ans.setCount(stack.getCount());
		ans.applyComponents(stack.getComponentsPatch());
		return ans;
	}

}
