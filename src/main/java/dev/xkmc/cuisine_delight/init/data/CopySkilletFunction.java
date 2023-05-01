package dev.xkmc.cuisine_delight.init.data;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import dev.xkmc.cuisine_delight.content.block.CuisineSkilletBlockEntity;
import dev.xkmc.cuisine_delight.init.CDBlocks;
import dev.xkmc.cuisine_delight.init.CDMisc;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class CopySkilletFunction extends LootItemConditionalFunction {

	public CopySkilletFunction(LootItemCondition[] conditions) {
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

	public LootItemFunctionType getType() {
		return CDMisc.LFT_COPY_SKILLET.get();
	}

	public static class Serializer extends LootItemConditionalFunction.Serializer<CopySkilletFunction> {
		public Serializer() {
		}

		public CopySkilletFunction deserialize(JsonObject json, JsonDeserializationContext context, LootItemCondition[] conditions) {
			return new CopySkilletFunction(conditions);
		}
	}

}
