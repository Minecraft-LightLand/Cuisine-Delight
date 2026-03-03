package dev.xkmc.cuisinedelight.init.data;

import com.tterrag.registrate.providers.RegistrateItemTagsProvider;
import com.tterrag.registrate.providers.RegistrateTagsProvider;
import dev.xkmc.cuisinedelight.init.CuisineDelight;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class TagGen {

	public static final TagKey<Item> UTENSILS = createItemTag("utensils");

	public static final TagKey<Block> LOW_HEAT = createBlockTag("low_heat");

	public static void onBlockTagGen(RegistrateTagsProvider.IntrinsicImpl<Block> pvd) {
		pvd.addTag(LOW_HEAT).add(Blocks.FIRE, Blocks.CAMPFIRE);
	}

	public static void onItemTagGen(RegistrateItemTagsProvider pvd) {

	}

	public static void onEntityTagGen(RegistrateTagsProvider<EntityType<?>> pvd) {
	}

	private static TagKey<Block> createBlockTag(String id) {
		return BlockTags.create(new ResourceLocation(CuisineDelight.MODID, id));
	}

	private static TagKey<Item> createItemTag(String id) {
		return ItemTags.create(new ResourceLocation(CuisineDelight.MODID, id));
	}

}
