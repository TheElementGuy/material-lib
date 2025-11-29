package net.theelementguy.tegmatlib.trim;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.equipment.trim.TrimMaterial;
import net.theelementguy.tegmatlib.core.MaterialConfiguration;

import java.util.List;
import java.util.function.Supplier;

public class TEGMatLibTrimMaterialProvider {

	private final String MOD_ID;

	private final Supplier<List<MaterialConfiguration>> MATERIALS;

	protected TEGMatLibTrimMaterialProvider(String modId, Supplier<List<MaterialConfiguration>> materials) {
		MOD_ID = modId;
		MATERIALS = materials;
	}

	public void bootstrap(BootstrapContext<TrimMaterial> context) {

		for (MaterialConfiguration config : MATERIALS.get()) {
			config.fillTrimMaterialKeys();
			config.bootstrapTrimMaterial(context);
		}

	}

}
