package dev.xkmc.cuisine_delight.init.data;

import dev.xkmc.cuisine_delight.init.CuisineDelight;
import dev.xkmc.l2library.repack.registrate.providers.RegistrateItemTagsProvider;
import dev.xkmc.l2library.repack.registrate.providers.RegistrateTagsProvider;
import net.minecraft.resources.ResourceLocation;
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
		return ItemTags.create(new ResourceLocation(CuisineDelight.MODID, id));
	}

}
