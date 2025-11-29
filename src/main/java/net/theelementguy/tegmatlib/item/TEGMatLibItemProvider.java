package net.theelementguy.tegmatlib.item;

import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.theelementguy.tegmatlib.core.MaterialConfiguration;

import java.util.List;
import java.util.function.Supplier;

public class TEGMatLibItemProvider {

	private final String MOD_ID;

	private final Supplier<List<MaterialConfiguration>> MATERIALS;

	public TEGMatLibItemProvider(String modId, Supplier<List<MaterialConfiguration>> materials) {
		MOD_ID = modId;
		MATERIALS = materials;
	}

	public void registerItems(DeferredRegister.Items itemRegistry) {

		for (MaterialConfiguration config : MATERIALS.get()) {
			config.fillItems(itemRegistry, MOD_ID);
		}

	}

}
