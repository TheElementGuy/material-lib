package com.github.theelementguy.tegmatlib.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import com.github.theelementguy.tegmatlib.trim.TEGMatLibTrimMaterialProvider;
import com.github.theelementguy.tegmatlib.worldgen.TEGMatLibBiomeModifierProvider;
import com.github.theelementguy.tegmatlib.worldgen.TEGMatLibConfiguredFeatureProvider;
import com.github.theelementguy.tegmatlib.worldgen.TEGMatLibPlacedFeatureProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class TEGMatLibDatapackProvider extends DatapackBuiltinEntriesProvider {

	public TEGMatLibDatapackProvider(GatherDataEvent.Client event, String modId, TEGMatLibConfiguredFeatureProvider configuredFeatures, TEGMatLibPlacedFeatureProvider placedFeatures, TEGMatLibBiomeModifierProvider biomeModifiers, TEGMatLibTrimMaterialProvider trims) {
		super(event.getGenerator().getPackOutput(), event.getLookupProvider(), new RegistrySetBuilder().add(Registries.CONFIGURED_FEATURE, configuredFeatures::bootstrap).add(Registries.PLACED_FEATURE, placedFeatures::bootstrap).add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, biomeModifiers::bootstrap).add(Registries.TRIM_MATERIAL, trims::bootstrap), Set.of(modId));
	}

}
