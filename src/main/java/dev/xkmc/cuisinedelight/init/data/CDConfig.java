package dev.xkmc.cuisinedelight.init.data;

import dev.xkmc.cuisinedelight.init.CuisineDelight;
import dev.xkmc.l2core.util.ConfigInit;
import net.neoforged.neoforge.common.ModConfigSpec;

public class CDConfig {

	public static class Client extends ConfigInit {

		Client(ModConfigSpec.Builder builder) {
			markPlain();
		}

	}

	public static class Server extends ConfigInit {

		public ModConfigSpec.IntValue baseServe;
		public ModConfigSpec.DoubleValue baseNutrition;
		public ModConfigSpec.DoubleValue varietyBonus;
		public ModConfigSpec.DoubleValue perfectionBonus;
		public ModConfigSpec.IntValue nourishmentDuration;

		public ModConfigSpec.IntValue maxIngredient;

		Server(Builder builder) {
			markPlain();
			baseServe = builder.text("base size per serve")
					.defineInRange("baseServe", 4, 1, 100);
			baseNutrition = builder.text("base nutrition factor")
					.defineInRange("baseNutrition", 0.1, 0, 100);
			varietyBonus = builder.text("bonus for every extra food type")
					.defineInRange("varietyBonus", 0.2, 0, 100);
			perfectionBonus = builder.text("bonus for perfect food")
					.defineInRange("perfectionBonus", 0.5, 0, 100);
			maxIngredient = builder.text("max number of ingredient entries")
					.defineInRange("maxIngredient", 9, 1, 20);
			nourishmentDuration = builder.text("nourishment duration per food type")
					.defineInRange("nourishmentDuration", 100, 1, 10000);
		}

	}

	public static final Client CLIENT = CuisineDelight.REGISTRATE.registerClient(Client::new);

	public static final Server SERVER = CuisineDelight.REGISTRATE.registerSynced(Server::new);


	public static void init() {
	}


}
