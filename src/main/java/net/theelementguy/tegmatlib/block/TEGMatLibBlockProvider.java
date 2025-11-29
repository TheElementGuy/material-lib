package net.theelementguy.tegmatlib.block;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.theelementguy.tegmatlib.core.MaterialConfiguration;

import java.util.List;
import java.util.function.Supplier;

public class TEGMatLibBlockProvider {

	private final String MOD_ID;

	public final DeferredRegister.Blocks BLOCKS;

	private final Supplier<List<MaterialConfiguration>> MATERIALS;

	public TEGMatLibBlockProvider(String modId, Supplier<List<MaterialConfiguration>> materials) {
		MOD_ID = modId;
		MATERIALS = materials;
		BLOCKS = DeferredRegister.createBlocks(MOD_ID);
	}

	public void register(IEventBus eventBus, Supplier<DeferredRegister.Items> itemsRegistry) {

		registerBlocks(BLOCKS, itemsRegistry);
		BLOCKS.register(eventBus);

	}

	protected void registerBlocks(DeferredRegister.Blocks blocksRegistry, Supplier<DeferredRegister.Items> itemsRegistry) {

		for (MaterialConfiguration config : MATERIALS.get()) {
			config.fillBlocks(blocksRegistry, itemsRegistry);
		}

	}

}
