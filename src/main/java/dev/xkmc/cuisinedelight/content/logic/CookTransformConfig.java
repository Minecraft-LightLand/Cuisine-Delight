package dev.xkmc.cuisinedelight.content.logic;

import dev.xkmc.cuisinedelight.content.logic.transform.*;
import dev.xkmc.cuisinedelight.init.CuisineDelight;
import dev.xkmc.l2core.serial.config.BaseConfig;
import dev.xkmc.l2core.serial.config.CollectType;
import dev.xkmc.l2core.serial.config.ConfigCollect;
import dev.xkmc.l2serial.serialization.marker.SerialClass;
import dev.xkmc.l2serial.serialization.marker.SerialField;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.LinkedHashMap;

@SerialClass
public class CookTransformConfig extends BaseConfig {

	public static CookTransform get(ItemStack stack) {
		return CuisineDelight.TRANSFORM.getMerged().getTransform(stack);
	}

	@ConfigCollect(CollectType.MAP_OVERWRITE)
	@SerialField
	public final LinkedHashMap<Item, ItemStageTransform> itemTransform = new LinkedHashMap<>();

	@ConfigCollect(CollectType.MAP_OVERWRITE)
	@SerialField
	public final LinkedHashMap<Item, FluidTransform> fluidTransform = new LinkedHashMap<>();

	public CookTransform getTransform(ItemStack stack) {
		Item item = stack.getItem();
		if (itemTransform.containsKey(item)) {
			return itemTransform.get(item);
		}
		if (fluidTransform.containsKey(item)) {
			return fluidTransform.get(item);
		}
		return new NoTransform();
	}

	public CookTransformConfig item(Item raw, Item cooked, Stage stage) {
		itemTransform.put(raw, new ItemStageTransform(stage, cooked));
		return this;
	}

	public CookTransformConfig fluid(Item item, int color) {
		fluidTransform.put(item, new FluidTransform(color));
		return this;
	}

}
