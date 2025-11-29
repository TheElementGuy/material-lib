package net.theelementguy.tegmatlib.worldgen.config;

import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.theelementguy.tegmatlib.core.SingleOrMultiple;
import net.theelementguy.tegmatlib.util.OrePlacement;

import java.util.List;

public class OreGenConfig {

	private final OreGenSize SIZE;

	private final HeightRangePlacement PLACEMENT;

	private final int SIZE_INT;
	private final float DISCARD_ON_AIR_CHANCE;

	private final OreRarity RARITY;
	private final int PLACEMENT_INT;

	private final TagKey<Biome> BIOMES;
	private final ResourceKey<Biome> BIOME;

	private final SingleOrMultiple BIOME_HOLDING_TYPE;

	public OreGenConfig(OreGenSize size, HeightRangePlacement placement, int sizeInt, float discardOnAirChance, OreRarity rarity, int placementInt, TagKey<Biome> biomes) {
		SIZE = size;
		PLACEMENT = placement;
		SIZE_INT = sizeInt;
		DISCARD_ON_AIR_CHANCE = discardOnAirChance;
		RARITY = rarity;
		PLACEMENT_INT = placementInt;
		BIOMES = biomes;
		BIOME_HOLDING_TYPE = SingleOrMultiple.MULTIPLE;
		BIOME = null;
	}

	public OreGenConfig(OreGenSize size, HeightRangePlacement placement, int sizeInt, float discardOnAirChance, OreRarity rarity, int placementInt, ResourceKey<Biome> biomes) {
		SIZE = size;
		PLACEMENT = placement;
		SIZE_INT = sizeInt;
		DISCARD_ON_AIR_CHANCE = discardOnAirChance;
		RARITY = rarity;
		PLACEMENT_INT = placementInt;
		BIOME = biomes;
		BIOME_HOLDING_TYPE = SingleOrMultiple.SINGLE;
		BIOMES = null;
	}

	public static OreGenConfig smallAllBiomes(int upperBound, int lowerBound, OreDistribution distributionType, int veinSize, int numberPerChunk, Dimension dimension) {
		if (distributionType == OreDistribution.TRIANGLE) {
			return new OreGenConfig(OreGenSize.SMALL, HeightRangePlacement.triangle(VerticalAnchor.absolute(lowerBound), VerticalAnchor.absolute(upperBound)), veinSize, 0f, OreRarity.COMMON, numberPerChunk, dimension.getBiomesContained());
		} else if (distributionType == OreDistribution.UNIFORM) {
			return new OreGenConfig(OreGenSize.SMALL, HeightRangePlacement.uniform(VerticalAnchor.absolute(lowerBound), VerticalAnchor.absolute(upperBound)), veinSize, 0f, OreRarity.COMMON, numberPerChunk, dimension.getBiomesContained());
		}
		return new OreGenConfig(OreGenSize.SMALL, HeightRangePlacement.triangle(VerticalAnchor.absolute(lowerBound), VerticalAnchor.absolute(upperBound)), veinSize, 0f, OreRarity.COMMON, numberPerChunk, dimension.getBiomesContained());
	}

	public static OreGenConfig mediumAllBiomes(int upperBound, int lowerBound, OreDistribution distributionType, int veinSize, int numberPerChunk, Dimension dimension) {
		if (distributionType == OreDistribution.TRIANGLE) {
			return new OreGenConfig(OreGenSize.MEDIUM, HeightRangePlacement.triangle(VerticalAnchor.absolute(lowerBound), VerticalAnchor.absolute(upperBound)), veinSize, 0f, OreRarity.COMMON, numberPerChunk, dimension.getBiomesContained());
		} else if (distributionType == OreDistribution.UNIFORM) {
			return new OreGenConfig(OreGenSize.MEDIUM, HeightRangePlacement.uniform(VerticalAnchor.absolute(lowerBound), VerticalAnchor.absolute(upperBound)), veinSize, 0f, OreRarity.COMMON, numberPerChunk, dimension.getBiomesContained());
		}
		return new OreGenConfig(OreGenSize.MEDIUM, HeightRangePlacement.triangle(VerticalAnchor.absolute(lowerBound), VerticalAnchor.absolute(upperBound)), veinSize, 0f, OreRarity.COMMON, numberPerChunk, dimension.getBiomesContained());
	}

	public static OreGenConfig largeAllBiomes(int upperBound, int lowerBound, OreDistribution distributionType, int veinSize, int numberPerChunk, Dimension dimension) {
		if (distributionType == OreDistribution.TRIANGLE) {
			return new OreGenConfig(OreGenSize.LARGE, HeightRangePlacement.triangle(VerticalAnchor.absolute(lowerBound), VerticalAnchor.absolute(upperBound)), veinSize, 0f, OreRarity.COMMON, numberPerChunk, dimension.getBiomesContained());
		} else if (distributionType == OreDistribution.UNIFORM) {
			return new OreGenConfig(OreGenSize.LARGE, HeightRangePlacement.uniform(VerticalAnchor.absolute(lowerBound), VerticalAnchor.absolute(upperBound)), veinSize, 0f, OreRarity.COMMON, numberPerChunk, dimension.getBiomesContained());
		}
		return new OreGenConfig(OreGenSize.LARGE, HeightRangePlacement.triangle(VerticalAnchor.absolute(lowerBound), VerticalAnchor.absolute(upperBound)), veinSize, 0f, OreRarity.COMMON, numberPerChunk, dimension.getBiomesContained());
	}

	public void registerConfiguredFeature(BootstrapContext<ConfiguredFeature<?, ?>> context, List<OreConfiguration.TargetBlockState> ores, ResourceKey<ConfiguredFeature<?, ?>> key) {
		context.register(key, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(ores, this.SIZE_INT, this.DISCARD_ON_AIR_CHANCE)));
	}

	public void registerPlacedFeature(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> featureKey, ResourceKey<ConfiguredFeature<?, ?>> configKey) {
		context.register(featureKey, new PlacedFeature(context.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(configKey), (RARITY == OreRarity.COMMON) ? OrePlacement.commonOrePlacement(PLACEMENT_INT, PLACEMENT) : OrePlacement.rareOrePlacement(PLACEMENT_INT, PLACEMENT)));
	}

	public void registerBiomeModifier(BootstrapContext<BiomeModifier> context, ResourceKey<BiomeModifier> modifierKey, ResourceKey<PlacedFeature> featureKey) {
		if (BIOME_HOLDING_TYPE == SingleOrMultiple.SINGLE) {
			assert BIOME != null;
			context.register(modifierKey, new BiomeModifiers.AddFeaturesBiomeModifier(HolderSet.direct(context.lookup(Registries.BIOME).getOrThrow(BIOME)), HolderSet.direct(context.lookup(Registries.PLACED_FEATURE).getOrThrow(featureKey)), GenerationStep.Decoration.UNDERGROUND_ORES));
		} else if (BIOME_HOLDING_TYPE == SingleOrMultiple.MULTIPLE) {
			assert BIOMES != null;
			context.register(modifierKey, new BiomeModifiers.AddFeaturesBiomeModifier(context.lookup(Registries.BIOME).getOrThrow(BIOMES), HolderSet.direct(context.lookup(Registries.PLACED_FEATURE).getOrThrow(featureKey)), GenerationStep.Decoration.UNDERGROUND_ORES));
		}
	}

}
