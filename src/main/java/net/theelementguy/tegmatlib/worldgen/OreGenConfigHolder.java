package net.theelementguy.tegmatlib.worldgen;

import net.theelementguy.tegmatlib.worldgen.config.OreGenConfig;
import org.apache.http.cookie.SM;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Supplier;

public class OreGenConfigHolder {

	private Supplier<OreGenConfig> SMALL_KEY;
	private Supplier<OreGenConfig> MEDIUM_KEY;
	private Supplier<OreGenConfig> LARGE_KEY;
	private Supplier<OreGenConfig> EXTRA_KEY;

	public OreGenConfigHolder(@Nullable Supplier<OreGenConfig> smallKey, @Nullable Supplier<OreGenConfig> mediumKey, @Nullable Supplier<OreGenConfig> largeKey, @Nullable Supplier<OreGenConfig> extraKey) {
		SMALL_KEY = smallKey;
		LARGE_KEY = largeKey;
		MEDIUM_KEY = mediumKey;
		EXTRA_KEY = extraKey;
	}

	public Optional<OreGenConfig> getSmall() {
		return Optional.ofNullable(SMALL_KEY.get());
	}

	public boolean hasSmall() {
		return Optional.ofNullable(SMALL_KEY).isPresent();
	}

	public Optional<OreGenConfig> getMedium() {
		return Optional.ofNullable(MEDIUM_KEY.get());
	}

	public boolean hasMedium() {
		return Optional.ofNullable(MEDIUM_KEY).isPresent();
	}

	public Optional<OreGenConfig> getLarge() {
		return Optional.ofNullable(LARGE_KEY.get());
	}

	public boolean hasLarge() {
		return Optional.ofNullable(LARGE_KEY).isPresent();
	}

	public Optional<OreGenConfig> getExtra() {
		return Optional.ofNullable(EXTRA_KEY.get());
	}

	public boolean hasExtra() {
		return Optional.ofNullable(EXTRA_KEY).isPresent();
	}

}
