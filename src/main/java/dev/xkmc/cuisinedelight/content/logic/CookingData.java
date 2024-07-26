package dev.xkmc.cuisinedelight.content.logic;

import dev.xkmc.l2serial.serialization.marker.SerialClass;
import dev.xkmc.l2serial.serialization.marker.SerialField;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;

@SerialClass
public class CookingData {

	@SerialField
	public long lastActionTime;

	@SerialField
	public int glowstone, redstone;

	@SerialField
	public ArrayList<CookingEntry> contents = new ArrayList<>();

	public void update(long time) {
		lastActionTime = time;
	}

	public void stir(long time) {
		update(time);
		for (CookingEntry entry : contents) {
			entry.stir(time);
		}
	}

	public void addItem(ItemStack item, long time) {
		if (item.is(Items.GLOWSTONE_DUST)) {
			glowstone++;
		}
		if (item.is(Items.REDSTONE)) {
			redstone++;
		}
		update(time);
		contents.add(new CookingEntry(item, time));
	}

	@SerialClass
	public static class CookingEntry {

		@SerialField
		public ItemStack item;

		@SerialField
		public long startTime;

		@SerialField
		public long lastStirTime;

		@SerialField
		public int maxStirTime;

		@Deprecated
		public CookingEntry() {

		}

		public CookingEntry(ItemStack item, long time) {
			this.item = item;
			this.startTime = time;
			this.lastStirTime = time;
			this.maxStirTime = 0;
		}

		public void stir(long time) {
			maxStirTime = Math.max(maxStirTime, (int) (time - lastStirTime));
			lastStirTime = time;
		}

	}

}
