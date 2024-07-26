package dev.xkmc.cuisinedelight.init.data;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.xkmc.cuisinedelight.content.block.CuisineSkilletBlockEntity;
import dev.xkmc.cuisinedelight.init.registrate.CDMisc;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import java.util.List;

public class CopySkilletFunction extends LootItemConditionalFunction {

	public static final MapCodec<CopySkilletFunction> CODEC = RecordCodecBuilder.mapCodec(i ->
			commonFields(i).apply(i, CopySkilletFunction::new));

	public CopySkilletFunction(List<LootItemCondition> conditions) {
		super(conditions);
	}

	public static Builder<?> builder() {
		return simpleBuilder(CopySkilletFunction::new);
	}

	protected ItemStack run(ItemStack stack, LootContext context) {
		BlockEntity tile = context.getParamOrNull(LootContextParams.BLOCK_ENTITY);
		if (tile instanceof CuisineSkilletBlockEntity skillet) {
			return skillet.toItemStack();
		}

		return stack;
	}

	public LootItemFunctionType<CopySkilletFunction> getType() {
		return CDMisc.LFT_COPY_SKILLET.get();
	}

}
