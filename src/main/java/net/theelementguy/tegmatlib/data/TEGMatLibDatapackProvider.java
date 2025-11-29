package net.theelementguy.tegmatlib.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.theelementguy.tegmatlib.trim.TEGMatLibTrimMaterialProvider;
import net.theelementguy.tegmatlib.worldgen.TEGMatLibBiomeModifierProvider;
import net.theelementguy.tegmatlib.worldgen.TEGMatLibConfiguredFeatureProvider;
import net.theelementguy.tegmatlib.worldgen.TEGMatLibPlacedFeatureProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class TEGMatLibDatapackProvider extends DatapackBuiltinEntriesProvider {

	public TEGMatLibDatapackProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, String modId, TEGMatLibConfiguredFeatureProvider configuredFeatures, TEGMatLibPlacedFeatureProvider placedFeatures, TEGMatLibBiomeModifierProvider biomeModifiers, TEGMatLibTrimMaterialProvider trims) {
		super(output, registries, new RegistrySetBuilder().add(Registries.CONFIGURED_FEATURE, configuredFeatures::bootstrap).add(Registries.PLACED_FEATURE, placedFeatures::bootstrap).add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, biomeModifiers::bootstrap).add(Registries.TRIM_MATERIAL, trims::bootstrap), Set.of(modId));
	}

}
