package com.github.theelementguy.tegmatlib.core.tiers;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;

public enum MineabilityTier implements StringRepresentable {
	/**
	 * The default option if omitted in builder <code>setTier</code> method: the tier below the <code>MiningTier</code>.
	 */
	DEFAULT("default"),
	WOOD("wood"),
	STONE("stone"),
	IRON("iron"),
	DIAMOND("diamond"),
	NETHERITE("netherite"),
	ALL("all");

	private final String name;

	public static final Codec<MineabilityTier> CODEC = StringRepresentable.fromEnum(MineabilityTier::values);

	MineabilityTier(String name) {
		this.name = name;
	}

	@Override
	public String getSerializedName() {
		return name;
	}
}
