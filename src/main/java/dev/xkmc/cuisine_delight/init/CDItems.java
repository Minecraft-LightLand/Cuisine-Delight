package dev.xkmc.cuisine_delight.init;

import dev.xkmc.cuisine_delight.init.CuisineDelight;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.function.Supplier;

@SuppressWarnings({"rawtypes", "unsafe"})
@MethodsReturnNonnullByDefault
public class CDItems {

	public static class Tab extends CreativeModeTab {

		private final Supplier<Item> icon;

		public Tab(String id, Supplier<Item> icon) {
			super(CuisineDelight.MODID + "." + id);
			this.icon = icon;
		}

		@Override
		public ItemStack makeIcon() {
			return icon.get().getDefaultInstance();
		}
	}

	public static final Tab TAB_GENERATED = new Tab("generated", () -> Items.EGG);

	static {
		//CuisineDelight.REGISTRATE.creativeModeTab(() -> TAB_GENERATED);
	}

	public static void register() {
	}

}
