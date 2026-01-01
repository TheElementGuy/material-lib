package com.github.theelementguy.tegmatlib.core.tiers;

public enum MineabilityTier {
	/**
	 * The default option if omitted in builder <code>setTier</code> method: the tier below the <code>MiningTier</code>.
	 */
	DEFAULT,
	WOOD,
	STONE,
	IRON,
	DIAMOND,
	NETHERITE,
	ALL
}
