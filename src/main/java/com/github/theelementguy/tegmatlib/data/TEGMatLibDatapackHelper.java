package com.github.theelementguy.tegmatlib.data;

import com.github.theelementguy.tegmatlib.core.FullyConfiguredMaterialHolder;
import com.mojang.logging.LogUtils;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import com.github.theelementguy.tegmatlib.trim.TEGMatLibTrimMaterialProvider;
import com.github.theelementguy.tegmatlib.worldgen.TEGMatLibBiomeModifierProvider;
import com.github.theelementguy.tegmatlib.worldgen.TEGMatLibConfiguredFeatureProvider;
import com.github.theelementguy.tegmatlib.worldgen.TEGMatLibPlacedFeatureProvider;
import org.slf4j.Logger;

import java.util.List;
import java.util.Set;

public class TEGMatLibDatapackHelper {

	private static final Logger LOG = LogUtils.getLogger();

	public static void run(GatherDataEvent.Client event, FullyConfiguredMaterialHolder materials) {
		TEGMatLibConfiguredFeatureProvider features = new TEGMatLibConfiguredFeatureProvider(materials);
		TEGMatLibPlacedFeatureProvider placedFeatures = new TEGMatLibPlacedFeatureProvider(materials);
		TEGMatLibBiomeModifierProvider biomeModifiers = new TEGMatLibBiomeModifierProvider(materials);
		TEGMatLibTrimMaterialProvider trims = new TEGMatLibTrimMaterialProvider(materials);
		RegistrySetBuilder world = new RegistrySetBuilder().add(Registries.FEATURE, features::bootstrap).add(Registries.PLACED_FEATURE, placedFeatures::bootstrap).add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, biomeModifiers::bootstrap).add(Registries.TRIM_MATERIAL, trims::bootstrap);
		RegistrySetBuilder reloadable = new RegistrySetBuilder().add(Registries.LOOT_TABLE, new LootTableProvider(Set.of(), List.of(new LootTableProvider.SubProviderEntry((context) -> new TEGMatLibBlockLootTableProvider(context, materials), LootContextParamSets.BLOCK)))).add(TEGMatLibRecipeProvider.create(materials));
		event.createWorldRegistryObjects(world);
		event.createReloadableRegistryObjects(reloadable);
		LOG.info("Instantiating datapack provider for mod {}", materials.getModID());
	}

}
