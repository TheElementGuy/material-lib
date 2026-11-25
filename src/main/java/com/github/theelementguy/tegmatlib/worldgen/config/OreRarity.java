package com.github.theelementguy.tegmatlib.worldgen.config;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;

public enum OreRarity implements StringRepresentable {
	COMMON("common"),
	RARE("rare");

	private final String name;

	public static final Codec<OreRarity> CODEC = StringRepresentable.fromEnum(OreRarity::values);

	OreRarity(String name) {
		this.name = name;
	}

	@Override
	public String getSerializedName() {
		return name;
	}
}
