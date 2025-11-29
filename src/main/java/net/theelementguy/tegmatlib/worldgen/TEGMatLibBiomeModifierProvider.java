package net.theelementguy.tegmatlib.worldgen;

import net.minecraft.data.worldgen.BootstrapContext;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.theelementguy.tegmatlib.core.MaterialConfiguration;

import java.util.List;
import java.util.function.Supplier;

public class TEGMatLibBiomeModifierProvider {

	private final Supplier<List<MaterialConfiguration>> MATERIALS;

	public TEGMatLibBiomeModifierProvider(Supplier<List<MaterialConfiguration>> materials) {
		MATERIALS = materials;
	}

	public void bootstrap(BootstrapContext<BiomeModifier> context) {

		for (MaterialConfiguration config : MATERIALS.get()) {
			config.registerBiomeModifiers(context);
		}

	}

}
