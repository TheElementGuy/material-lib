package net.theelementguy.tegmatlib.worldgen;

import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.common.world.BiomeModifier;

import javax.annotation.Nullable;
import java.util.Optional;

public class BiomeModifierKeyHolder {

	private ResourceKey<BiomeModifier> SMALL_KEY;
	private ResourceKey<BiomeModifier> MEDIUM_KEY;
	private ResourceKey<BiomeModifier> LARGE_KEY;
	private ResourceKey<BiomeModifier> EXTRA_KEY;

	public BiomeModifierKeyHolder(@Nullable ResourceKey<BiomeModifier> smallKey, @Nullable ResourceKey<BiomeModifier> mediumKey, @Nullable ResourceKey<BiomeModifier> largeKey, @Nullable ResourceKey<BiomeModifier> extraKey) {
		SMALL_KEY = smallKey;
		LARGE_KEY = largeKey;
		MEDIUM_KEY = mediumKey;
		EXTRA_KEY = extraKey;
	}

	public Optional<ResourceKey<BiomeModifier>> getSmallKey() {
		return Optional.ofNullable(SMALL_KEY);
	}

	public Optional<ResourceKey<BiomeModifier>> getMediumKey() {
		return Optional.ofNullable(MEDIUM_KEY);
	}

	public Optional<ResourceKey<BiomeModifier>> getLargeKey() {
		return Optional.ofNullable(LARGE_KEY);
	}

	public Optional<ResourceKey<BiomeModifier>> getExtraKey() {
		return Optional.ofNullable(EXTRA_KEY);
	}
	
}
