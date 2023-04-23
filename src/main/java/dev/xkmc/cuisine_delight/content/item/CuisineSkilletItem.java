package dev.xkmc.cuisine_delight.content.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import dev.xkmc.cuisine_delight.content.logic.CookingData;
import dev.xkmc.cuisine_delight.content.logic.IngredientConfig;
import dev.xkmc.l2library.serial.codec.TagCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.tag.ModTags;

import java.util.function.Consumer;

public class CuisineSkilletItem extends Item {

	private static final String KEY_ROOT = "CookingData";

	@Nullable
	public static CookingData getData(ItemStack stack) {
		var tag = stack.getTagElement(KEY_ROOT);
		if (tag == null) return null;
		return TagCodec.fromTag(tag, CookingData.class);
	}

	public static void setData(ItemStack stack, @Nullable CookingData data) {
		if (data == null) {
			stack.getOrCreateTag().remove(KEY_ROOT);
			return;
		}
		var tag = TagCodec.valueToTag(data);
		if (tag != null) {
			stack.getOrCreateTag().put(KEY_ROOT, tag);
		}
	}

	public static boolean canUse(ItemStack stack, Player player, Level level) {
		if (getData(stack) != null) return true;
		if (stack.getEnchantmentLevel(Enchantments.FIRE_ASPECT) > 0) return true;
		return isPlayerNearHeatSource(player, level);
	}

	public static final Tiers SKILLET_TIER = Tiers.IRON;
	private final Multimap<Attribute, AttributeModifier> toolAttributes;

	public CuisineSkilletItem(Properties properties) {
		super(properties.defaultDurability(SKILLET_TIER.getUses()));
		float attackDamage = 5.0F + SKILLET_TIER.getAttackDamageBonus();
		ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", (double) attackDamage, AttributeModifier.Operation.ADDITION));
		builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", -3.0999999046325684D, AttributeModifier.Operation.ADDITION));
		this.toolAttributes = builder.build();
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack skilletStack = player.getItemInHand(hand);
		if (!canUse(skilletStack, player, level)) {
			//TODO no heat
			return InteractionResultHolder.fail(skilletStack);
		}
		InteractionHand otherHand = hand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
		ItemStack otherStack = player.getItemInHand(otherHand);
		if (!level.isClientSide()) {
			if (!otherStack.isEmpty()) {
				IngredientConfig.IngredientEntry entry = IngredientConfig.get().getEntry(otherStack);
				if (entry != null) {
					long time = level.getGameTime();
					CookingData data = getData(skilletStack);
					if (data == null) {
						data = new CookingData();
					}
					int amount = 1 + getEnchantmentLevel(skilletStack, Enchantments.BLOCK_EFFICIENCY);
					data.addItem(otherStack.split(amount), time);
					setData(skilletStack, data);
				}
			}
		}
		return InteractionResultHolder.fail(skilletStack);
	}

	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		return false;
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
		return enchantment == Enchantments.FIRE_ASPECT || enchantment == Enchantments.BLOCK_EFFICIENCY;
	}

	@Override
	public void initializeClient(Consumer<IClientItemExtensions> consumer) {
		consumer.accept(SkilletBEWLR.EXTENSIONS);
	}

	//------

	private static boolean isPlayerNearHeatSource(Player player, LevelReader level) {
		if (player.isOnFire()) {
			return true;
		} else {
			BlockPos pos = player.blockPosition();
			for (BlockPos nearbyPos : BlockPos.betweenClosed(pos.offset(-1, -1, -1), pos.offset(1, 1, 1))) {
				if (level.getBlockState(nearbyPos).is(ModTags.HEAT_SOURCES)) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		stack.hurtAndBreak(1, attacker, (user) -> {
			user.broadcastBreakEvent(EquipmentSlot.MAINHAND);
		});
		return true;
	}

	public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
		return SKILLET_TIER.getRepairIngredient().test(repair) || super.isValidRepairItem(toRepair, repair);
	}

	public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity entity) {
		if (!level.isClientSide && state.getDestroySpeed(level, pos) != 0.0F) {
			stack.hurtAndBreak(1, entity, (user) -> {
				user.broadcastBreakEvent(EquipmentSlot.MAINHAND);
			});
		}

		return true;
	}

	public int getEnchantmentValue() {
		return SKILLET_TIER.getEnchantmentValue();
	}

	public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentSlot) {
		return equipmentSlot == EquipmentSlot.MAINHAND ? this.toolAttributes : super.getDefaultAttributeModifiers(equipmentSlot);
	}


}
