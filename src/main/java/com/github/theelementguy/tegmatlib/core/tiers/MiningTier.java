package com.github.theelementguy.tegmatlib.core.tiers;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;

public enum MiningTier implements StringRepresentable {
	WOOD("wood"),
	STONE("stone"),
	IRON("iron"),
	DIAMOND("diamond"),
	NETHERITE("netherite"),
	BEYOND_NETHERITE("beyond_netherite");

	private final String name;

	public static final Codec<MiningTier> CODEC = StringRepresentable.fromEnum(MiningTier::values);

	MiningTier(String name) {
		this.name = name;
	}

	@Override
	public String getSerializedName() {
		return name;
	}
}
