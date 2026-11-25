package com.github.theelementguy.tegmatlib;

import com.github.theelementguy.tegmatlib.core.serialize.MaterialConfigSerializable;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.NewDatapackRegistryEvent;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber(modid = "tegmatlib")
public class TEGMatLibRegistries {

	public static final ResourceKey<@NotNull Registry<@NotNull MaterialConfigSerializable>> MATERIAL_CONFIGURATION = ResourceKey.createRegistryKey(Identifier.fromNamespaceAndPath("tegmatlib", "material_config"));

	@SubscribeEvent
	public static void registerDatapackRegistries(NewDatapackRegistryEvent event) {
		event.reloadableRegistry(MATERIAL_CONFIGURATION, MaterialConfigSerializable.CODEC);
	}

}
