package net.theelementguy.tegmatlib.block;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.theelementguy.tegmatlib.core.FullyConfiguredMaterialHolder;
import net.theelementguy.tegmatlib.core.MaterialConfiguration;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class TEGMatLibBlockProvider {

	private final String MOD_ID;
	private final Logger LOG = LogUtils.getLogger();

	private final FullyConfiguredMaterialHolder MATERIALS;

	public TEGMatLibBlockProvider(String modId, FullyConfiguredMaterialHolder materials) {
		MOD_ID = modId;
		MATERIALS = materials;
	}

	public void registerBlocks(DeferredRegister.Blocks blocksRegistry, Supplier<DeferredRegister.Items> itemsRegistry) {

		LOG.info("HELLO from tegmatlib item registration: {}", MOD_ID);

		ArrayList<Supplier<MaterialConfiguration>> configs = new ArrayList<>(MATERIALS.getMaterials().size());

		for (MaterialConfiguration config : MATERIALS.getMaterials()) {
			config.fillBlocks(blocksRegistry, itemsRegistry);
			configs.add(() -> config);
		}

		MATERIALS.setMaterialConfiguration(configs);

	}

}
