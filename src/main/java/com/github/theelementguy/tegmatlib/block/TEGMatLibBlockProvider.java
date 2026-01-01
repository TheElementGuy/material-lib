package com.github.theelementguy.tegmatlib.block;

import com.mojang.logging.LogUtils;
import net.neoforged.neoforge.registries.DeferredRegister;
import com.github.theelementguy.tegmatlib.core.FullyConfiguredMaterialHolder;
import com.github.theelementguy.tegmatlib.core.MaterialConfiguration;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.function.Supplier;

/**
 * The provided class from TEG Material Library that automatically registers blocks. This class is meant to be created in the mod constructor.
 */
public class TEGMatLibBlockProvider {

	private final String MOD_ID;
	private final Logger LOG = LogUtils.getLogger();

	private final FullyConfiguredMaterialHolder MATERIALS;

	/**
	 * Constructor for the {@link TEGMatLibBlockProvider} class. Meant to be called in mod constructor.
	 * @param modId the mod ID of the mod in question
	 * @param materials a {@link FullyConfiguredMaterialHolder} holding the {@link MaterialConfiguration}s
	 */
	public TEGMatLibBlockProvider(String modId, FullyConfiguredMaterialHolder materials) {
		MOD_ID = modId;
		MATERIALS = materials;
	}

	/**
	 * Registers the blocks specified by each {@link MaterialConfiguration} to the game.
	 * @param blocksRegistry a {@link DeferredRegister} for the blocks. This should be static.
	 * @param itemsRegistry a supplier for a <code>DeferredRegister</code> for the block items. This should be static and the same <code>DeferredRegister</code> used for the items.
	 */
	public void registerBlocks(DeferredRegister.Blocks blocksRegistry, Supplier<DeferredRegister.Items> itemsRegistry) {

		LOG.info("HELLO from tegmatlib block registration: {}", MOD_ID);

		ArrayList<Supplier<MaterialConfiguration>> configs = new ArrayList<>(MATERIALS.getMaterials().size());

		for (MaterialConfiguration config : MATERIALS.getMaterials()) {
			config.fillBlocks(blocksRegistry, itemsRegistry);
			configs.add(() -> config);
		}

		MATERIALS.setMaterialConfiguration(configs);

	}

}
