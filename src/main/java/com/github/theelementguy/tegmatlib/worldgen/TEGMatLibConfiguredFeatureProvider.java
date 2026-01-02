package com.github.theelementguy.tegmatlib.worldgen;

import com.github.theelementguy.tegmatlib.core.FullyConfiguredMaterialHolder;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import com.github.theelementguy.tegmatlib.core.MaterialConfiguration;

import java.util.List;
import java.util.function.Supplier;

public class TEGMatLibConfiguredFeatureProvider {

	private final Supplier<List<MaterialConfiguration>> MATERIALS;

	public TEGMatLibConfiguredFeatureProvider(FullyConfiguredMaterialHolder materials) {
		MATERIALS = materials::getMaterials;
	}

	public void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {

		for (MaterialConfiguration config : MATERIALS.get()) {
			config.registerConfiguredFeatures(context);
		}

	}

}
