package dev.xkmc.cuisinedelight.init.data;

import net.minecraft.resources.ResourceLocation;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class TagRef {

	public static final TagKey<Block> HEAT_SOURCES = modBlockTag("heat_sources");

	private static TagKey<Item> modItemTag(String path) {
		return ItemTags.create(new ResourceLocation("farmersdelight", path));
	}

	private static TagKey<Block> modBlockTag(String path) {
		return BlockTags.create(new ResourceLocation("farmersdelight", path));
	}


}
