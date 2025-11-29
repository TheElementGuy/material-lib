package net.theelementguy.tegmatlib.worldgen;

import net.theelementguy.tegmatlib.worldgen.config.OreGenConfig;

import javax.annotation.Nullable;
import java.util.Optional;

public class OreGenConfigHolder {

	private OreGenConfig SMALL_KEY;
	private OreGenConfig MEDIUM_KEY;
	private OreGenConfig LARGE_KEY;
	private OreGenConfig EXTRA_KEY;

	public OreGenConfigHolder(@Nullable OreGenConfig smallKey, @Nullable OreGenConfig mediumKey, @Nullable OreGenConfig largeKey, @Nullable OreGenConfig extraKey) {
		SMALL_KEY = smallKey;
		LARGE_KEY = largeKey;
		MEDIUM_KEY = mediumKey;
		EXTRA_KEY = extraKey;
	}

	public Optional<OreGenConfig> getSmall() {
		return Optional.ofNullable(SMALL_KEY);
	}

	public Optional<OreGenConfig> getMedium() {
		return Optional.ofNullable(MEDIUM_KEY);
	}

	public Optional<OreGenConfig> getLarge() {
		return Optional.ofNullable(LARGE_KEY);
	}

	public Optional<OreGenConfig> getExtra() {
		return Optional.ofNullable(EXTRA_KEY);
	}

}
