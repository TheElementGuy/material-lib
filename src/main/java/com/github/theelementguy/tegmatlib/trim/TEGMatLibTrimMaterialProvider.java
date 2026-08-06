package com.github.theelementguy.tegmatlib.trim;

import com.github.theelementguy.tegmatlib.core.FullyConfiguredMaterialHolder;
import com.mojang.logging.LogUtils;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.equipment.trim.TrimMaterial;
import com.github.theelementguy.tegmatlib.core.MaterialConfiguration;
import org.slf4j.Logger;

import java.util.List;
import java.util.function.Supplier;

public class TEGMatLibTrimMaterialProvider {

	private final Logger LOG = LogUtils.getLogger();

	private final Supplier<List<MaterialConfiguration>> MATERIALS;
	private final String MOD_ID;

	public TEGMatLibTrimMaterialProvider(FullyConfiguredMaterialHolder materials) {
		MATERIALS = materials::getMaterials;
		MOD_ID = materials.getModID();
	}

	public void bootstrap(BootstrapContext<TrimMaterial> context) {

		LOG.info("Bootstrapping trim materials for mod {}", MOD_ID);

		for (MaterialConfiguration config : MATERIALS.get()) {
			config.bootstrapTrimMaterial(context);
		}

	}

}
