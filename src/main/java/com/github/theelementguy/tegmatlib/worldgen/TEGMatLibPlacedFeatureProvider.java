package com.github.theelementguy.tegmatlib.worldgen;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import com.github.theelementguy.tegmatlib.core.MaterialConfiguration;

import java.util.List;
import java.util.function.Supplier;

public class TEGMatLibPlacedFeatureProvider {

	private final String MOD_ID;

	private final Supplier<List<MaterialConfiguration>> MATERIALS;

	public TEGMatLibPlacedFeatureProvider(String modId, Supplier<List<MaterialConfiguration>> materials) {
		MOD_ID = modId;
		MATERIALS = materials;
	}

	public void bootstrap(BootstrapContext<PlacedFeature> context) {

		for (MaterialConfiguration config : MATERIALS.get()) {
			config.registerPlacedFeatures(context);
		}

	}

}
