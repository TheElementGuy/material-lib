package com.github.theelementguy.tegmatlib.worldgen;

import com.github.theelementguy.tegmatlib.core.FullyConfiguredMaterialHolder;
import com.mojang.logging.LogUtils;
import net.minecraft.data.worldgen.BootstrapContext;
import net.neoforged.neoforge.common.world.BiomeModifier;
import com.github.theelementguy.tegmatlib.core.MaterialConfiguration;
import org.slf4j.Logger;

import java.util.List;
import java.util.function.Supplier;

public class TEGMatLibBiomeModifierProvider {

	private final Logger LOG = LogUtils.getLogger();

	private final FullyConfiguredMaterialHolder MATERIALS;

	public TEGMatLibBiomeModifierProvider(FullyConfiguredMaterialHolder materials) {
		MATERIALS = materials;
	}

	public void bootstrap(BootstrapContext<BiomeModifier> context) {

		LOG.info("Bootstrapping biome modifiers for mod {}", MATERIALS.getModID());

		for (MaterialConfiguration config : MATERIALS.getMaterials()) {
			config.registerBiomeModifiers(context);
		}

	}

}
