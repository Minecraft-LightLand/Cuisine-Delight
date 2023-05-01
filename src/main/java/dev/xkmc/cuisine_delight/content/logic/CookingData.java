package dev.xkmc.cuisine_delight.content.logic;

import dev.xkmc.l2library.serial.SerialClass;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;

@SerialClass
public class CookingData {

	@SerialClass.SerialField
	public long lastActionTime;

	@SerialClass.SerialField
	public int glowstone, redstone;

	@SerialClass.SerialField
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

		@SerialClass.SerialField
		public ItemStack item;

		@SerialClass.SerialField
		public long startTime;

		@SerialClass.SerialField
		public long lastStirTime;

		@SerialClass.SerialField
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
