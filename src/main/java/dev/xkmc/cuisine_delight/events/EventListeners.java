package dev.xkmc.cuisine_delight.events;

import dev.xkmc.cuisine_delight.content.logic.IngredientConfig;
import dev.xkmc.cuisine_delight.init.CDItems;
import dev.xkmc.cuisine_delight.init.CuisineDelight;
import dev.xkmc.cuisine_delight.init.data.LangData;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import vectorwing.farmersdelight.common.registry.ModSounds;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = CuisineDelight.MODID)
public class EventListeners {

	@SubscribeEvent
	public static void onTooltip(ItemTooltipEvent event) {
		ItemStack stack = event.getItemStack();
		if (stack.isEmpty()) return;
		var config = IngredientConfig.get().getEntry(stack);
		if (config == null) return;
		if (Screen.hasShiftDown()) {
			event.getToolTip().add(LangData.INFO_SIZE.get(config.size));
			event.getToolTip().add(LangData.INFO_NUTRITION.get(config.nutrition));
			event.getToolTip().add(LangData.INFO_MIN_TIME.get(config.min_time / 20));
			event.getToolTip().add(LangData.INFO_MAX_TIME.get(config.max_time / 20));
			event.getToolTip().add(LangData.INFO_STIR_TIME.get(config.stir_time / 20));
			event.getToolTip().add(LangData.INFO_RAW_PENALTY.get(Math.round(config.raw_penalty * 100)));
			event.getToolTip().add(LangData.INFO_OVERCOOK_PENALTY.get(Math.round(config.overcook_penalty * 100)));
			for (var e : config.effects) {
				MobEffectInstance mobeffectinstance = new MobEffectInstance(e.effect(), e.time());
				MutableComponent mutablecomponent = Component.translatable(mobeffectinstance.getDescriptionId());
				MobEffect mobeffect = mobeffectinstance.getEffect();
				if (mobeffectinstance.getDuration() > 20) {
					mutablecomponent = Component.translatable("potion.withDuration", mutablecomponent, MobEffectUtil.formatDuration(mobeffectinstance, 1));
				}
				event.getToolTip().add(mutablecomponent.withStyle(mobeffect.getCategory().getTooltipFormatting()));
			}
		} else {
			event.getToolTip().add(LangData.SHIFT.get());
		}
	}

	@SubscribeEvent
	public static void onSkilletKnockback(LivingKnockBackEvent event) {
		LivingEntity attacker = event.getEntity().getKillCredit();
		ItemStack tool = attacker != null ? attacker.getItemInHand(InteractionHand.MAIN_HAND) : ItemStack.EMPTY;
		if (tool.is(CDItems.SKILLET.get())) {
			event.setStrength(event.getOriginalStrength() * 2.0F);
		}

	}

	@SubscribeEvent
	public static void onSkilletAttack(AttackEntityEvent event) {
		Player player = event.getEntity();
		float attackPower = player.getAttackStrengthScale(0.0F);
		ItemStack tool = player.getItemInHand(InteractionHand.MAIN_HAND);
		if (tool.is(CDItems.SKILLET.get())) {
			if (attackPower > 0.8F) {
				float pitch = 0.9F + player.getRandom().nextFloat() * 0.2F;
				player.getCommandSenderWorld().playSound(player, player.getX(), player.getY(), player.getZ(), ModSounds.ITEM_SKILLET_ATTACK_STRONG.get(), SoundSource.PLAYERS, 1.0F, pitch);
			} else {
				player.getCommandSenderWorld().playSound(player, player.getX(), player.getY(), player.getZ(), ModSounds.ITEM_SKILLET_ATTACK_WEAK.get(), SoundSource.PLAYERS, 0.8F, 0.9F);
			}
		}

	}

}
