package com.github.theelementguy.tegmatlib.worldgen;

import com.github.theelementguy.tegmatlib.core.FullyConfiguredMaterialHolder;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Provider for the custom feature used for sand ores.
 */
public class TEGMatLibFeatureProvider {

	private final FullyConfiguredMaterialHolder MATERIALS;

	public TEGMatLibFeatureProvider(FullyConfiguredMaterialHolder materials) {
		MATERIALS = materials;
	}

	public void register(DeferredRegister<Feature<?>> register) {
		register.register("sand_ore_feature", () -> new SandOreFeature(OreConfiguration.CODEC));
	}

}
