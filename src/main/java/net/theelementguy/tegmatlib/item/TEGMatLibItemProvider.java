package net.theelementguy.tegmatlib.item;

import com.mojang.logging.LogUtils;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.theelementguy.tegmatlib.core.MaterialConfiguration;
import org.slf4j.Logger;

import java.util.List;
import java.util.function.Supplier;

public class TEGMatLibItemProvider {

	private final String MOD_ID;
	private final Logger LOG = LogUtils.getLogger();

	private final Supplier<List<MaterialConfiguration>> MATERIALS;

	public TEGMatLibItemProvider(String modId, Supplier<List<MaterialConfiguration>> materials) {
		MOD_ID = modId;
		MATERIALS = materials;
	}

	public void registerItems(DeferredRegister.Items itemRegistry) {

		LOG.info("HELLO from tegmatlib item registration: {}", MOD_ID);

		for (MaterialConfiguration config : MATERIALS.get()) {
			config.fillItems(itemRegistry, MOD_ID);
		}

	}

}
