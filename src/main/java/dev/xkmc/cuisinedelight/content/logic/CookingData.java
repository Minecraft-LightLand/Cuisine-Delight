package dev.xkmc.cuisinedelight.content.logic;

import dev.xkmc.cuisinedelight.content.logic.transform.Stage;
import dev.xkmc.l2serial.serialization.marker.SerialClass;
import dev.xkmc.l2serial.serialization.marker.SerialField;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Random;

@SerialClass
public class CookingData {

	@SerialField
	private long lastActionTime;

	@SerialField
	private float speed = 1;

	@SerialField
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
		update(time);
		contents.add(new CookingEntry(item, time));
	}

	@SerialClass
	public static class CookingEntry {

		@SerialField
		private ItemStack item;

		@SerialField
		private long startTime;

		@SerialField
		private long lastStirTime;

		@SerialField
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
			assert config != null;
			float time = getDuration(data, 0);
			if (time < config.min_time) return Stage.RAW;
			if (time < config.max_time) return Stage.COOKED;
			return Stage.OVERCOOKED;
		}

		public Immutable immutable() {
			return new Immutable(item, startTime, lastStirTime, maxStirTime);
		}

		public record Immutable(ItemStack item, long startTime, long lastStirTime, int maxStirTime) {

			public CookingEntry mutable() {
				var ans = new CookingEntry();
				ans.item = item;
				ans.startTime = startTime;
				ans.lastStirTime = lastStirTime;
				ans.maxStirTime = maxStirTime;
				return ans;
			}

		}

	}

	public Record immutable() {
		ArrayList<CookingEntry.Immutable> list = new ArrayList<>();
		for (var e : contents) list.add(e.immutable());
		return new Record(lastActionTime, speed, list);
	}

	public record Record(long lastActionTime, float speed, ArrayList<CookingEntry.Immutable> contents) {

		public CookingData mutable() {
			var ans = new CookingData();
			ans.lastActionTime = lastActionTime;
			ans.speed = speed;
			for (var e : contents)
				ans.contents.add(e.mutable());
			return ans;
		}

	}

}
