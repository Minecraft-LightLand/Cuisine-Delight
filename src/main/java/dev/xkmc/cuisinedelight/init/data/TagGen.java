package dev.xkmc.cuisinedelight.init.data;

import com.tterrag.registrate.providers.RegistrateItemTagsProvider;
import com.tterrag.registrate.providers.RegistrateTagsProvider;
import dev.xkmc.cuisinedelight.init.CuisineDelight;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class TagGen {

	public static final TagKey<Item> UTENSILS = createItemTag("utensils");

	public static void onBlockTagGen(RegistrateTagsProvider<Block> pvd) {
	}

	public static void onItemTagGen(RegistrateItemTagsProvider pvd) {

	}

	public static void onEntityTagGen(RegistrateTagsProvider<EntityType<?>> pvd) {
	}

	private static TagKey<Item> createItemTag(String id) {
		return ItemTags.create(CuisineDelight.loc(id));
	}

}
