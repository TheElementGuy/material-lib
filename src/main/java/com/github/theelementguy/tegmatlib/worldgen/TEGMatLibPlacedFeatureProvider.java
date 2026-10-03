package com.github.theelementguy.tegmatlib.worldgen;

import com.github.theelementguy.tegmatlib.core.FullyConfiguredMaterialHolder;
import com.mojang.logging.LogUtils;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import com.github.theelementguy.tegmatlib.core.MaterialConfiguration;
import org.slf4j.Logger;


public class TEGMatLibPlacedFeatureProvider {

	private final Logger LOG = LogUtils.getLogger();

	private final FullyConfiguredMaterialHolder MATERIALS;

	public TEGMatLibPlacedFeatureProvider(FullyConfiguredMaterialHolder materials) {
		MATERIALS = materials;
	}

	public void bootstrap(BootstrapContext<PlacedFeature> context) {

		LOG.info("Bootstrapping placed features for mod {}", MATERIALS.getModID());

		for (MaterialConfiguration config : MATERIALS.getMaterials()) {
			config.registerPlacedFeatures(context);
		}

	}

}
