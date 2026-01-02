package com.github.theelementguy.tegmatlib.trim;

import com.github.theelementguy.tegmatlib.core.FullyConfiguredMaterialHolder;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.equipment.trim.TrimMaterial;
import com.github.theelementguy.tegmatlib.core.MaterialConfiguration;

import java.util.List;
import java.util.function.Supplier;

public class TEGMatLibTrimMaterialProvider {

	private final Supplier<List<MaterialConfiguration>> MATERIALS;

	public TEGMatLibTrimMaterialProvider(FullyConfiguredMaterialHolder materials) {
		MATERIALS = materials::getMaterials;
	}

	public void bootstrap(BootstrapContext<TrimMaterial> context) {

		for (MaterialConfiguration config : MATERIALS.get()) {
			config.bootstrapTrimMaterial(context);
		}

	}

}
