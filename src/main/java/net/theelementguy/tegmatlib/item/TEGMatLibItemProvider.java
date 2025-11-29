package net.theelementguy.tegmatlib.item;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.theelementguy.tegmatlib.core.MaterialConfiguration;

import java.util.List;
import java.util.function.Supplier;

public class TEGMatLibItemProvider {

	private final String MOD_ID;

	public final DeferredRegister.Items ITEMS;

	private final Supplier<List<MaterialConfiguration>> MATERIALS;

	public TEGMatLibItemProvider(String modId, Supplier<List<MaterialConfiguration>> materials) {
		MOD_ID = modId;
		MATERIALS = materials;
		ITEMS = DeferredRegister.createItems(MOD_ID);
	}

	public void register(IEventBus eventBus) {

		registerItems(ITEMS);
		ITEMS.register(eventBus);

	}

	protected void registerItems(DeferredRegister.Items itemRegistry) {

		for (MaterialConfiguration config : MATERIALS.get()) {
			config.fillItems(itemRegistry, MOD_ID);
		}

	}

	public DeferredRegister.Items getRegister() {
		return ITEMS;
	}

}
