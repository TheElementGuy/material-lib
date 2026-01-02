package com.github.theelementguy.tegmatlib.item;

import com.mojang.logging.LogUtils;
import net.neoforged.neoforge.registries.DeferredRegister;
import com.github.theelementguy.tegmatlib.core.FullyConfiguredMaterialHolder;
import com.github.theelementguy.tegmatlib.core.MaterialConfiguration;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.function.Supplier;

public class TEGMatLibItemProvider {

	private final String MOD_ID;
	private final Logger LOG = LogUtils.getLogger();

	private final FullyConfiguredMaterialHolder MATERIALS;

	public TEGMatLibItemProvider(String modId, FullyConfiguredMaterialHolder materials) {
		MOD_ID = modId;
		MATERIALS = materials;
	}

	public void registerItems(DeferredRegister.Items itemsRegistry) {

		LOG.info("HELLO from tegmatlib item registration: {}", MOD_ID);

		ArrayList<Supplier<MaterialConfiguration>> configs = new ArrayList<>(MATERIALS.getMaterials().size());

		for (MaterialConfiguration config : MATERIALS.getMaterials()) {
			config.fillItems(itemsRegistry);
			configs.add(() -> config);
		}

		MATERIALS.setMaterialConfiguration(configs);

	}

}
