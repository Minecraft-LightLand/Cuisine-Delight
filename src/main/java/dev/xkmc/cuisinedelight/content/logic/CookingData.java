package dev.xkmc.cuisinedelight.content.logic;

import dev.xkmc.l2library.serial.SerialClass;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.Random;

@SerialClass
public class CookingData {

	@SerialClass.SerialField
	private long lastActionTime;

	@SerialClass.SerialField
	private float speed = 1;

	@SerialClass.SerialField
	public int glowstone, redstone;

	@SerialClass.SerialField
	public ArrayList<CookingEntry> contents = new ArrayList<>();

	public void update(long time) {
		lastActionTime = time;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public void stir(long time, int reduce) {
		update(time);
		for (CookingEntry entry : contents) {
			entry.stir(time, reduce);
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
		private ItemStack item;

		@SerialClass.SerialField
		private long startTime;

		@SerialClass.SerialField
		private long lastStirTime;

		@SerialClass.SerialField
		private int maxStirTime;

		@Deprecated
		public CookingEntry() {

		}

		public CookingEntry(ItemStack item, long time) {
			this.item = item;
			this.startTime = time;
			this.lastStirTime = time;
			this.maxStirTime = 0;
		}

		public void stir(long time, int reduce) {
			maxStirTime = Math.max(maxStirTime, (int) (time - lastStirTime));
			lastStirTime = time + reduce;
		}

		public float getDuration(CookingData data, float partialTick) {
			return (partialTick + data.lastActionTime - startTime) * data.speed;
		}

		public float timeSinceStir(CookingData data, float partialTick) {
			return Math.max(0, partialTick + data.lastActionTime - lastStirTime) * data.speed;
		}

		public float getMaxStirTime(CookingData data) {
			return maxStirTime * data.speed;
		}

		public ItemStack getItem() {
			return item;
		}

		public long seed() {
			return new Random(startTime).nextLong();
		}

		public Stage getStage(CookingData data) {
			var config = IngredientConfig.get().getEntry(getItem());
			assert config!=null;
			float time = getDuration(data,0);
			if (time< config.min_time)return Stage.RAW;
			if (time < config.max_time)return Stage.COOKED;
			return Stage.OVERCOOKED;
		}
	}

}
