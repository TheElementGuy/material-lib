package com.github.theelementguy.tegmatlib.core.serialize;

import com.github.theelementguy.tegmatlib.core.tiers.MineabilityTier;
import com.github.theelementguy.tegmatlib.core.tiers.MiningTier;
import com.github.theelementguy.tegmatlib.worldgen.OreGenHolder;
import com.github.theelementguy.tegmatlib.worldgen.config.OreGenConfig;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record TotalOreConfig(OreGenHolder<OreGenConfig> oreGenConfigs, int dropsPerOre, int extraDrops, MiningTier tier, MineabilityTier mineabilityTier) {

	public static final Codec<TotalOreConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(OreGenHolder.codec(OreGenConfig.CODEC).fieldOf("ore_gen_configs").forGetter(TotalOreConfig::oreGenConfigs), Codec.INT.fieldOf("drops_per_ore").forGetter(TotalOreConfig::dropsPerOre), Codec.INT.fieldOf("extra_drops").forGetter(TotalOreConfig::extraDrops), MiningTier.CODEC.fieldOf("mining_tier").forGetter(TotalOreConfig::tier), MineabilityTier.CODEC.fieldOf("mineability_tier").forGetter(TotalOreConfig::mineabilityTier)).apply(instance, TotalOreConfig::new));

}
