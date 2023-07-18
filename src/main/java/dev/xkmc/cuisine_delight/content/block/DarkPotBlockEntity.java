package dev.xkmc.cuisine_delight.content.block;

import dev.xkmc.l2library.base.tile.BaseBlockEntity;
import dev.xkmc.l2modularblock.tile_api.TickableBlockEntity;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import vectorwing.farmersdelight.common.block.entity.HeatableBlockEntity;
import vectorwing.farmersdelight.common.registry.ModParticleTypes;

@SerialClass
public class DarkPotBlockEntity extends BaseBlockEntity implements HeatableBlockEntity, TickableBlockEntity {

	public DarkPotBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	public boolean isHeated() {
		if (level == null) return false;
		for (int x = -1; x <= 1; x++) {
			for (int z = -1; z <= 1; z++) {
				if (!isHeated(level, getBlockPos().offset(x, 0, z))) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public boolean requiresDirectHeat() {
		return true;
	}

	public void tick() {
		if (level == null || !isHeated()) return;
		if (level.isClientSide) {
			BlockPos pos = getBlockPos();
			RandomSource random = level.random;
			double r0 = 0.3D;
			double r1 = 0.2D;
			if (random.nextFloat() < 0.2F) {
				double x = pos.getX() + 0.5D + (random.nextDouble() * 2 - 1) * r0;
				double y = pos.getY() + 0.5D + 0.2D;
				double z = pos.getZ() + 0.5D + (random.nextDouble() * 2 - 1) * r0;
				level.addParticle(ParticleTypes.BUBBLE_POP, x, y, z, 0.0D, 0.0D, 0.0D);
			}

			if (random.nextFloat() < 0.05F) {
				double x = pos.getX() + 0.5D + (random.nextDouble() * 2 - 1) * r1;
				double y = pos.getY() + 0.5D;
				double z = pos.getZ() + 0.5D + (random.nextDouble() * 2 - 1) * r1;
				double motionY = random.nextBoolean() ? 0.015D : 0.005D;
				level.addParticle(ModParticleTypes.STEAM.get(), x, y, z, 0.0D, motionY, 0.0D);
			}
		}
	}

}
