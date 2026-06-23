package com.github.theelementguy.tegmatlib.trim;

import com.github.theelementguy.tegmatlib.core.FullyConfiguredMaterialHolder;
import net.minecraft.data.worldgen.BootstrapContext;
import com.github.theelementguy.tegmatlib.core.MaterialConfiguration;
import net.minecraft.world.item.armortrim.TrimMaterial;

import java.util.List;
import java.util.function.Supplier;

public class TEGMatLibTrimMaterialProvider {

	private final Supplier<List<MaterialConfiguration>> MATERIALS;

	public TEGMatLibTrimMaterialProvider(FullyConfiguredMaterialHolder materials) {
		MATERIALS = materials::getMaterials;
	}

	public void bootstrap(BootstrapContext<TrimMaterial> context) {

		float id = 1.0f;

		for (MaterialConfiguration config : MATERIALS.get()) {
			id += 0.1f;
			config.bootstrapTrimMaterial(context, id);
		}

	}

}
