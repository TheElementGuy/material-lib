package com.github.theelementguy.tegmatlib.worldgen;

import com.github.theelementguy.tegmatlib.core.FullyConfiguredMaterialHolder;
import net.minecraft.data.worldgen.BootstrapContext;
import net.neoforged.neoforge.common.world.BiomeModifier;
import com.github.theelementguy.tegmatlib.core.MaterialConfiguration;

import java.util.List;
import java.util.function.Supplier;

public class TEGMatLibBiomeModifierProvider {

	private final Supplier<List<MaterialConfiguration>> MATERIALS;

	public TEGMatLibBiomeModifierProvider(FullyConfiguredMaterialHolder materials) {
		MATERIALS = materials::getMaterials;
	}

	public void bootstrap(BootstrapContext<BiomeModifier> context) {

		for (MaterialConfiguration config : MATERIALS.get()) {
			config.registerBiomeModifiers(context);
		}

	}

}
