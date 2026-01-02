package com.github.theelementguy.tegmatlib.worldgen;

import com.github.theelementguy.tegmatlib.core.FullyConfiguredMaterialHolder;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import com.github.theelementguy.tegmatlib.core.MaterialConfiguration;

import java.util.List;
import java.util.function.Supplier;

public class TEGMatLibPlacedFeatureProvider {

	private final Supplier<List<MaterialConfiguration>> MATERIALS;

	public TEGMatLibPlacedFeatureProvider(FullyConfiguredMaterialHolder materials) {
		MATERIALS = materials::getMaterials;
	}

	public void bootstrap(BootstrapContext<PlacedFeature> context) {

		for (MaterialConfiguration config : MATERIALS.get()) {
			config.registerPlacedFeatures(context);
		}

	}

}
