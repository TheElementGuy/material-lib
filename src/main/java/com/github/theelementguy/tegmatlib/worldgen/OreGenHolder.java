package com.github.theelementguy.tegmatlib.worldgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Supplier;

public class OreGenHolder<T> {

	private Supplier<T> SMALL_KEY;
	private Supplier<T> MEDIUM_KEY;
	private Supplier<T> LARGE_KEY;
	private Supplier<T> EXTRA_KEY;

	public static <U> Codec<OreGenHolder<U>> codec(Codec<U> heldCodec) {
		return RecordCodecBuilder.create(instance -> instance.group(heldCodec.optionalFieldOf("small").forGetter(holder -> ((OreGenHolder<U>) holder).getSmall()), heldCodec.optionalFieldOf("medium").forGetter(holder -> ((OreGenHolder<U>) holder).getMedium()), heldCodec.optionalFieldOf("large").forGetter(holder -> ((OreGenHolder<U>) holder).getLarge()), heldCodec.optionalFieldOf("extra").forGetter(holder -> ((OreGenHolder<U>) holder).getExtra())).apply(instance, OreGenHolder::createFromCodec));
	}

	private static <U> OreGenHolder<U> createFromCodec(Optional<U> small, Optional<U> medium, Optional<U> large, Optional<U> extra) {
		Supplier<U> smallSupplier = small.map(optional -> (Supplier<U>) () -> optional).orElse(null);
		Supplier<U> mediumSupplier = medium.map(optional -> (Supplier<U>) () -> optional).orElse(null);
		Supplier<U> largeSupplier = large.map(optional -> (Supplier<U>) () -> optional).orElse(null);
		Supplier<U> extraSupplier = extra.map(optional -> (Supplier<U>) () -> optional).orElse(null);
		return new OreGenHolder<>(smallSupplier, mediumSupplier, largeSupplier, extraSupplier);
	}

	public OreGenHolder(@Nullable Supplier<T> smallKey, @Nullable Supplier<T> mediumKey, @Nullable Supplier<T> largeKey, @Nullable Supplier<T> extraKey) {
		SMALL_KEY = smallKey;
		LARGE_KEY = largeKey;
		MEDIUM_KEY = mediumKey;
		EXTRA_KEY = extraKey;
	}

	public Optional<T> getSmall() {
		return Optional.ofNullable(SMALL_KEY).map(Supplier::get);
	}

	public boolean hasSmall() {
		return Optional.ofNullable(SMALL_KEY).isPresent();
	}

	public Optional<T> getMedium() {
		return Optional.ofNullable(MEDIUM_KEY).map(Supplier::get);
	}

	public boolean hasMedium() {
		return Optional.ofNullable(MEDIUM_KEY).isPresent();
	}

	public Optional<T> getLarge() {
		return Optional.ofNullable(LARGE_KEY).map(Supplier::get);
	}

	public boolean hasLarge() {
		return Optional.ofNullable(LARGE_KEY).isPresent();
	}

	public Optional<T> getExtra() {
		return Optional.ofNullable(EXTRA_KEY).map(Supplier::get);
	}

	public boolean hasExtra() {
		return Optional.ofNullable(EXTRA_KEY).isPresent();
	}
	
}
