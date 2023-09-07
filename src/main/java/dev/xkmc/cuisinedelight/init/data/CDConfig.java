package dev.xkmc.cuisinedelight.init.data;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import org.apache.commons.lang3.tuple.Pair;

public class CDConfig {

	public static class Client {

		public final ForgeConfigSpec.DoubleValue uiScale;

		Client(ForgeConfigSpec.Builder builder) {
			uiScale = builder.comment("Cooking UI Scale")
					.defineInRange("uiScale", 1, 0, 16d);
		}

	}

	public static class Common {

		public final ForgeConfigSpec.IntValue baseServe;
		public final ForgeConfigSpec.DoubleValue baseNutrition;
		public final ForgeConfigSpec.DoubleValue varietyBonus;
		public final ForgeConfigSpec.DoubleValue perfectionBonus;
		public final ForgeConfigSpec.IntValue nourishmentDuration;

		public ForgeConfigSpec.IntValue maxIngredient;

		Common(ForgeConfigSpec.Builder builder) {
			baseServe = builder.comment("base size per serve")
					.defineInRange("baseServe", 4, 1, 100);
			baseNutrition = builder.comment("base nutrition factor")
					.defineInRange("baseNutrition", 0.1, 0, 100);
			varietyBonus = builder.comment("bonus for every extra food type")
					.defineInRange("varietyBonus", 0.2, 0, 100);
			perfectionBonus = builder.comment("bonus for perfect food")
					.defineInRange("perfectionBonus", 0.5, 0, 100);
			maxIngredient = builder.comment("max number of ingredient entries")
					.defineInRange("maxIngredient", 9, 1, 20);
			nourishmentDuration = builder.comment("nourishment duration per food type")
					.defineInRange("nourishmentDuration", 100, 1, 10000);
		}

	}

	public static final ForgeConfigSpec CLIENT_SPEC;
	public static final Client CLIENT;

	public static final ForgeConfigSpec COMMON_SPEC;
	public static final Common COMMON;

	static {
		final Pair<Client, ForgeConfigSpec> client = new ForgeConfigSpec.Builder().configure(Client::new);
		CLIENT_SPEC = client.getRight();
		CLIENT = client.getLeft();

		final Pair<Common, ForgeConfigSpec> common = new ForgeConfigSpec.Builder().configure(Common::new);
		COMMON_SPEC = common.getRight();
		COMMON = common.getLeft();
	}

	/**
	 * Registers any relevant listeners for config
	 */
	public static void init() {
		ModLoadingContext.get().registerConfig(net.minecraftforge.fml.config.ModConfig.Type.CLIENT, CDConfig.CLIENT_SPEC);
		ModLoadingContext.get().registerConfig(net.minecraftforge.fml.config.ModConfig.Type.COMMON, CDConfig.COMMON_SPEC);
	}


}
