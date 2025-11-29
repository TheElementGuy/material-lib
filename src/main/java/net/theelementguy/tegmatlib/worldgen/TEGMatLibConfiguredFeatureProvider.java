package net.theelementguy.tegmatlib.worldgen;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.theelementguy.tegmatlib.core.MaterialConfiguration;

import java.util.List;
import java.util.function.Supplier;

public class TEGMatLibConfiguredFeatureProvider {

	private final String MOD_ID;

	private final Supplier<List<MaterialConfiguration>> MATERIALS;

	public TEGMatLibConfiguredFeatureProvider(String modId, Supplier<List<MaterialConfiguration>> materials) {
		MOD_ID = modId;
		MATERIALS = materials;
	}

	public void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {

		for (MaterialConfiguration config : MATERIALS.get()) {
			config.fillConfiguredFeatureKeys();
			config.registerConfiguredFeatures(context);
		}

	}

}
