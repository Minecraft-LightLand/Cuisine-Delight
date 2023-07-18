package dev.xkmc.cuisine_delight.content.block;

import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import dev.xkmc.cuisine_delight.init.CuisineDelight;
import dev.xkmc.l2library.util.annotation.DataGenOnly;
import dev.xkmc.l2modularblock.DelegateBlock;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.MultiPartBlockStateBuilder;

import java.util.Locale;

public class DarkPotModelGen {

	enum Types {
		FULL(),
		CORNER(Direction.SOUTH, Direction.WEST),
		U_SHAPE(Direction.EAST),
		CHANNEL(Direction.WEST, Direction.EAST),
		MIDDLE(Direction.EAST, Direction.WEST, Direction.SOUTH, Direction.NORTH),
		SIDE(Direction.EAST, Direction.WEST, Direction.SOUTH);

		private final int id;

		Types(Direction... directions) {
			int ans = 0;
			for (var e : directions) {
				ans += 1 << e.get2DDataValue();
			}
			id = ans;
		}
	}

	record TypeRot(int id, Types type, int rot) {

		public void condition(ModelFile[] files, MultiPartBlockStateBuilder model) {
			var builder = model.part().modelFile(files[type.ordinal()]).rotationY(rot * 90).addModel();
			for (int j = 0; j < 4; j++) {
				boolean open = (id & 1 << j) > 0;
				builder.condition(DarkPotBlock.DIRE[j], open);
			}
			builder.end();
		}
	}

	private static final TypeRot[] ROTS = new TypeRot[16];

	static {
		for (var t : Types.values()) {
			for (int i = 0; i < 4; i++) {
				int id = rotate(t.id, i);
				if (ROTS[id] == null) {
					ROTS[id] = new TypeRot(id, t, i);
				}
			}
		}
	}

	private static int rotate(int id, int y) {
		id = id << y;
		id = id | id >> 4;
		return id & 0xF;
	}

	@DataGenOnly
	public static void build(DataGenContext<Block, DelegateBlock> ctx, RegistrateBlockstateProvider pvd) {
		var builder = pvd.getMultipartBuilder(ctx.getEntry());
		ModelFile[] files = new ModelFile[6];
		for (var e : Types.values()) {
			files[e.ordinal()] = getModel(ctx, pvd, e == Types.FULL ? "" : ("_" + e.name().toLowerCase(Locale.ROOT)));
		}
		for (int i = 0; i < 16; i++) {
			ROTS[i].condition(files, builder);
		}
		builder.part().modelFile(pvd.models().getBuilder(ctx.getName() + "_tray")
						.parent(new ModelFile.UncheckedModelFile(new ResourceLocation(CuisineDelight.MODID, "block/tray")))
						.texture("top", new ResourceLocation(CuisineDelight.MODID, "block/tray_top"))
						.texture("side", new ResourceLocation(CuisineDelight.MODID, "block/tray_side")))
				.addModel().condition(DarkPotTrayMethod.SUPPORT, true).end();
	}

	private static ModelFile getModel(DataGenContext<Block, DelegateBlock> ctx, RegistrateBlockstateProvider pvd, String suffix) {
		return pvd.models().getBuilder(ctx.getName() + suffix)
				.parent(new ModelFile.UncheckedModelFile(new ResourceLocation(CuisineDelight.MODID, "block/dark_pot" + suffix)))
				.texture("top", new ResourceLocation(CuisineDelight.MODID, "block/pot_top"))
				.texture("side", new ResourceLocation(CuisineDelight.MODID, "block/pot_side"))
				.texture("parts", new ResourceLocation(CuisineDelight.MODID, "block/pot_parts"))
				.texture("bottom", new ResourceLocation(CuisineDelight.MODID, "block/pot_bottom"));
	}

}
