package com.github.theelementguy.tegmatlib.core;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;

/**
 * Builder used to construct {@link EndDiamondTypeMaterialConfiguration}s. Create a new instance and call methods until ready to call <code>build()</code>.
 */
public class EndDiamondTypeBuilder extends MaterialConfigurationBuilder<EndDiamondTypeBuilder> {

	@Override
	protected EndDiamondTypeBuilder self() {
		return this;
	}

	/**
	 * Sets the position of the material's items in the inventory, in relation to other items.
	 * @param toolsBefore the tool set that this material will be placed after, as a string (for example, "stone")
	 * @param armorBefore the armor set that this material will be placed after, as a string (for example, "chainmail")
	 * @param itemBefore <code>Identifier</code> of the item that the base material will be placed after
	 * @param blockBefore <code>Identifier</code> of the block that the base block will be placed after
	 * @param oreBefore the ore that the ore will be placed after, as a full string (for example, "ancient_debris" or "end_aquaite_ore")
	 * @return the updated <code>MaterialConfigurationBuilder</code>
	 */
	public EndDiamondTypeBuilder setBefore(String toolsBefore, String armorBefore, Identifier itemBefore, Identifier blockBefore, String oreBefore) {
		this.TOOLS_BEFORE = toolsBefore;
		this.ARMOR_BEFORE = armorBefore;
		this.ITEM_BEFORE = () -> BuiltInRegistries.ITEM.get(itemBefore).orElseThrow().value();
		this.BLOCK_BEFORE = () -> BuiltInRegistries.BLOCK.get(blockBefore).orElseThrow().value();
		this.ORE_BEFORE = oreBefore;
		return self();
	}

	/**
	 * Builds the {@link EndDiamondTypeMaterialConfiguration} that has been specified.
	 * @return the built <code>EndDiamondTypeMaterialConfiguration</code> object
	 */
	public EndDiamondTypeMaterialConfiguration build() {
		return new EndDiamondTypeMaterialConfiguration(MOD_ID, BASE_NAME, HUMAN_READABLE_NAME, TRIM_MATERIAL_DESCRIPTION_COLOR, TOOL_DURABILITY, SPEED, ATTACK_DAMAGE_BONUS, TOOL_ENCHANTMENT, DEFAULT_PROPERTIES, ARMOR_DURABILITY, HEAD_DEFENSE, CHESTPLATE_DEFENSE, SMELTING_EXPERIENCE, LEGGINGS_DEFENSE, BOOTS_DEFENSE, HORSE_DEFENSE, EQUIP_SOUND, TOUGHNESS, KNOCKBACK_RESISTANCE, MAP_COLOR, SOUND_TYPE, ORE_GEN_CONFIGS, DROPS_PER_ORE, EXTRA_DROPS, TIER, MINEABILITY_TIER, TOOLS_BEFORE, ARMOR_BEFORE, ITEM_BEFORE, BLOCK_BEFORE, ORE_BEFORE, SWING_DURATION, DAMAGE_MULTIPLIER, DELAY, DISMOUNT_MAX_DURATION, DISMOUNT_MIN_SPEED, KNOCKBACK_MAX_DURATION, KNOCKBACK_MIN_SPEED, DAMAGE_MAX_DURATION, DAMAGE_MIN_SPEED, USING_HORSE_ARMOR, USING_NAUTILUS_ARMOR, ANIMAL_ARMOR_BEFORE, LOOT_MODIFIERS);
	}
}
