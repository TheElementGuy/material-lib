package com.github.theelementguy.tegmatlib.worldgen;

import com.github.theelementguy.tegmatlib.core.FullyConfiguredMaterialHolder;
import com.mojang.logging.LogUtils;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import com.github.theelementguy.tegmatlib.core.MaterialConfiguration;
import org.slf4j.Logger;

import java.util.List;
import java.util.function.Supplier;

public class TEGMatLibConfiguredFeatureProvider {

	private final Logger LOG = LogUtils.getLogger();

	private final FullyConfiguredMaterialHolder MATERIALS;

	public TEGMatLibConfiguredFeatureProvider(FullyConfiguredMaterialHolder materials) {
		MATERIALS = materials;
	}

	public void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {

		LOG.info("Bootstrapping configured features for mod {}", MATERIALS.getModID());

		for (MaterialConfiguration config : MATERIALS.getMaterials()) {
			config.registerConfiguredFeatures(context);
		}

	}

}
