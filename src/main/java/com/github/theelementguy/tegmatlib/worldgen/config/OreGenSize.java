package com.github.theelementguy.tegmatlib.worldgen.config;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;

public enum OreGenSize implements StringRepresentable {

	SMALL("small"),
	LARGE("large"),
	MEDIUM("medium"),
	EXTRA("extra");

	private final String sizeString;

	public static final Codec<OreGenSize> CODEC = StringRepresentable.fromEnum(OreGenSize::values);

	private OreGenSize(String sizeString) {
		this.sizeString = sizeString;
	}

	public String toString() {
		return sizeString;
	}

	@Override
	public String getSerializedName() {
		return sizeString;
	}
}
