package com.github.theelementguy.tegmatlib.core;

import com.github.theelementguy.tegmatlib.loot.LootItemSlot;
import com.github.theelementguy.tegmatlib.loot.LootModifierType;
import com.github.theelementguy.tegmatlib.loot.PreLootModifierInfo;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.event.level.PistonEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import com.github.theelementguy.tegmatlib.core.tiers.MineabilityTier;
import com.github.theelementguy.tegmatlib.core.tiers.MiningTier;
import com.github.theelementguy.tegmatlib.worldgen.OreGenHolder;
import com.github.theelementguy.tegmatlib.worldgen.config.OreGenConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * Subclass of {@link MaterialConfiguration} for Nether-diamond-type materials. Use the {@link NetherDiamondTypeMaterialConfiguration.Builder} for construction.
 * <p>Use this class for Nether materials with a diamond-type format: a gem as the final product.</p>
 */
public class NetherDiamondTypeMaterialConfiguration extends MaterialConfiguration {

	protected DeferredBlock<Block> NETHER_ORE_BLOCK;

	private NetherDiamondTypeMaterialConfiguration(String modId, String baseName, String humanReadableName, String trimMaterialDescriptionColor, int toolDurability, float speed, float attackDamageBonus, int enchantmentValue, Supplier<Item.Properties> defaultProperties, int armorDurability, int helmetDefense, int chestplateDefense, float smeltingExperience, int leggingsDefense, int bootsDefense, int horseDefense, Supplier<Holder<SoundEvent>> equipSound, float toughness, float knockbackResistance, Supplier<MapColor> mapColor, Supplier<SoundType> soundType, OreGenHolder<OreGenConfig> oreGenConfigs, int dropsPerOre, int extraDrops, MiningTier tier, MineabilityTier mineabilityTier, String toolsBefore, String armorBefore, Supplier<Item> itemBefore, Supplier<Block> blockBefore, String oreBefore, float swingDuration, float damageMultiplier, float delay, float dismountMaxDuration, float dismountMinSpeed, float knockbackMaxDuration, float knockbackMinSpeed, float damageMaxDuration, float damageMinSpeed, boolean usingHorseArmor, boolean usingNautilusArmor, String animalArmorBefore, List<PreLootModifierInfo> lootModifiers) {
		super(modId, baseName, humanReadableName, MaterialType.NETHER_DIAMOND, trimMaterialDescriptionColor, toolDurability, speed, attackDamageBonus, enchantmentValue, defaultProperties, armorDurability, helmetDefense, chestplateDefense, smeltingExperience, leggingsDefense, bootsDefense, horseDefense, equipSound, toughness, knockbackResistance, mapColor, soundType, oreGenConfigs, dropsPerOre, extraDrops, tier, mineabilityTier, toolsBefore, armorBefore, itemBefore, blockBefore, oreBefore, swingDuration, damageMultiplier, delay, dismountMaxDuration, dismountMinSpeed, knockbackMaxDuration, knockbackMinSpeed, damageMaxDuration, damageMinSpeed, usingHorseArmor, usingNautilusArmor, animalArmorBefore, lootModifiers);
	}

	@Override
	public void fillItems(DeferredRegister.Items register) {
		BASE_MATERIAL = registerSimpleItemWithTrimMaterial(BASE_NAME, register, MOD_ID);
		fillBaseEquipment(register);
	}

	@Override
	public void fillBlocks(DeferredRegister.Blocks register, Supplier<DeferredRegister.Items> itemsRegister) {
		NETHER_ORE_BLOCK = registerSimpleBlock("nether_" + BASE_NAME + "_ore", register, itemsRegister, 3f, 3f, MapColor.NETHER, SoundType.NETHER_ORE);
		fillBaseBlock(register, itemsRegister);
	}

	@Override
	public List<OreConfiguration.TargetBlockState> getOreStates() {
		return List.of(OreConfiguration.target(new BlockMatchTest(Blocks.NETHERRACK), NETHER_ORE_BLOCK.get().defaultBlockState()));
	}

	@Override
	public List<Block> getBlocks() {
		return List.of(BLOCK.get(), NETHER_ORE_BLOCK.get());
	}

	public Block getNetherOre() {
		return NETHER_ORE_BLOCK.get();
	}

	/**
	 * Builder used to construct {@link NetherDiamondTypeMaterialConfiguration}s. Create a new instance and call methods until ready to call <code>build()</code>.
	 */
	public static class Builder {

		protected String BASE_NAME;
		protected String MOD_ID;
		protected String HUMAN_READABLE_NAME;

		protected Supplier<Item.Properties> DEFAULT_PROPERTIES = Item.Properties::new;

		protected float SMELTING_EXPERIENCE;

		protected String TRIM_MATERIAL_DESCRIPTION_COLOR;

		protected Supplier<MapColor> MAP_COLOR;
		protected Supplier<SoundType> SOUND_TYPE;
		protected OreGenHolder<OreGenConfig> ORE_GEN_CONFIGS;

		protected int DROPS_PER_ORE = 1;
		protected int EXTRA_DROPS = 0;

		protected MiningTier TIER;
		protected MineabilityTier MINEABILITY_TIER = MineabilityTier.DEFAULT;

		protected int TOOL_DURABILITY;
		protected float SPEED;
		protected float ATTACK_DAMAGE_BONUS;
		protected int TOOL_ENCHANTMENT;

		protected float SWING_DURATION;
		protected float DELAY;
		protected float DAMAGE_MULTIPLIER;
		protected float DISMOUNT_MAX_DURATION;
		protected float DISMOUNT_MIN_SPEED;
		protected float KNOCKBACK_MAX_DURATION;
		protected float KNOCKBACK_MIN_SPEED;
		protected float DAMAGE_MAX_DURATION;
		protected float DAMAGE_MIN_SPEED;

		protected int ARMOR_DURABILITY;
		protected int HEAD_DEFENSE;
		protected int CHESTPLATE_DEFENSE;
		protected int LEGGINGS_DEFENSE;
		protected int BOOTS_DEFENSE;
		protected int HORSE_DEFENSE;
		protected int ARMOR_ENCHANTMENT;
		protected float TOUGHNESS = 0f;
		protected float KNOCKBACK_RESISTANCE = 0f;
		protected Supplier<Holder<SoundEvent>> EQUIP_SOUND;

		protected String TOOLS_BEFORE;
		protected String ARMOR_BEFORE;
		protected Supplier<Item> ITEM_BEFORE;
		protected Supplier<Block> BLOCK_BEFORE;
		protected String ORE_BEFORE;

		protected boolean USING_HORSE_ARMOR = false;
		protected boolean USING_NAUTILUS_ARMOR = false;

		protected String ANIMAL_ARMOR_BEFORE = null;

		protected List<PreLootModifierInfo> LOOT_MODIFIERS = new ArrayList<>();

		/**
		 * Sets the mod ID for the material.
		 * @param modId your mod ID
		 * @return the updated <code>Builder</code>
		 */
		public NetherDiamondTypeMaterialConfiguration.Builder modId(String modId) {
			this.MOD_ID = modId;
			return this;
		}

		/**
		 * Sets the base name for the material. This should only use lowercase letters and underscores.
		 * @param name the name to be used
		 * @return the updated <code>Builder</code>
		 */
		public NetherDiamondTypeMaterialConfiguration.Builder baseName(String name) {
			this.BASE_NAME = name;
			return this;
		}

		/**
		 * Sets the in-game name for the material. This will be capitalized appropriately.
		 * @param name the in-game name to be used
		 * @return the updated <code>Builder</code>
		 */
		public NetherDiamondTypeMaterialConfiguration.Builder inGameName(String name) {
			this.HUMAN_READABLE_NAME = name;
			return this;
		}

		/**
		 * Sets the default properties of the items that are created (e.g. fire-proof).
		 * @param properties a supplier of the default properties (for example, <code>() -> new Item.Properties().fireResistant()</code>
		 * @return the updated <code>Builder</code>
		 */
		public NetherDiamondTypeMaterialConfiguration.Builder defaultProperties(Supplier<Item.Properties> properties) {
			this.DEFAULT_PROPERTIES = properties;
			return this;
		}

		/**
		 * Sets the parameters for the tool material used for all tools of this material.
		 * @param durability the number of uses
		 * @param speed the efficiency, with higher numbers being faster
		 * @param attackDamageBonus extra damage added to all tools
		 * @param enchantmentValue how easily enchantable the tools are, with higher numbers being more enchantable
		 * @return the updated <code>Builder</code>
		 */
		public NetherDiamondTypeMaterialConfiguration.Builder toolMaterial(int durability, float speed, float attackDamageBonus, int enchantmentValue) {
			this.TOOL_DURABILITY = durability;
			this.SPEED = speed;
			this.ATTACK_DAMAGE_BONUS = attackDamageBonus;
			this.TOOL_ENCHANTMENT = enchantmentValue;
			return this;
		}

		/**
		 * Sets the parameters for the armor material used for all armor pieces of the material.
		 * @param durability the number of hits that can be taken (approximately)
		 * @param helmetDefense armor points from the helmet
		 * @param chestplateDefense armor points from the chestplate
		 * @param leggingsDefense armor points from the leggings
		 * @param bootsDefense armor points from the boots
		 * @param horseDefense armor points from the horse armor (note that horse armor is not created by this library)
		 * @param enchantmentValue how easily enchantable the armor pieces are, with higher numbers being more enchantable
		 * @param equipSound supplier of a <code>SoundEvent</code> that dictates in-game equip sound
		 * @param toughness an additional source of defense
		 * @param knockbackResistance sets a dampener on how far knockback launches the user, with higher being more protective
		 * @return the updated <code>Builder</code>
		 */
		public NetherDiamondTypeMaterialConfiguration.Builder armorMaterial(int durability, int helmetDefense, int chestplateDefense, int leggingsDefense, int bootsDefense, int horseDefense, int enchantmentValue, Supplier<Holder<SoundEvent>> equipSound, float toughness, float knockbackResistance) {
			this.ARMOR_DURABILITY = durability;
			this.HEAD_DEFENSE = helmetDefense;
			this.CHESTPLATE_DEFENSE = chestplateDefense;
			this.LEGGINGS_DEFENSE = leggingsDefense;
			this.BOOTS_DEFENSE = bootsDefense;
			this.HORSE_DEFENSE = horseDefense;
			this.ARMOR_ENCHANTMENT = enchantmentValue;
			this.EQUIP_SOUND = equipSound;
			this.TOUGHNESS = toughness;
			this.KNOCKBACK_RESISTANCE = knockbackResistance;
			return this;
		}

		/**
		 * Sets the parameters for the armor material used for all armor pieces of the material. Toughness and knockback resistance are assumed to be zero.
		 * @param durability the number of hits that can be taken (approximately)
		 * @param helmetDefense armor points from the helmet
		 * @param chestplateDefense armor points from the chestplate
		 * @param leggingsDefense armor points from the leggings
		 * @param bootsDefense armor points from the boots
		 * @param horseDefense armor points from the horse armor (note that horse armor is not created by this library)
		 * @param enchantmentValue how easily enchantable the armor pieces are, with higher numbers being more enchantable
		 * @param equipSound supplier of a <code>SoundEvent</code> that dictates in-game equip sound
		 * @return the updated <code>Builder</code>
		 */
		public NetherDiamondTypeMaterialConfiguration.Builder armorMaterial(int durability, int helmetDefense, int chestplateDefense, int leggingsDefense, int bootsDefense, int horseDefense, int enchantmentValue, Supplier<Holder<SoundEvent>> equipSound) {
			this.ARMOR_DURABILITY = durability;
			this.HEAD_DEFENSE = helmetDefense;
			this.CHESTPLATE_DEFENSE = chestplateDefense;
			this.LEGGINGS_DEFENSE = leggingsDefense;
			this.BOOTS_DEFENSE = bootsDefense;
			this.HORSE_DEFENSE = horseDefense;
			this.ARMOR_ENCHANTMENT = enchantmentValue;
			this.EQUIP_SOUND = equipSound;
			return this;
		}

		/**
		 * Sets the amount of experience gained by smelting the raw form or ore
		 * @param experience the amount of experience to be gained
		 * @return the updated <code>Builder</code>
		 */
		public NetherDiamondTypeMaterialConfiguration.Builder smeltingExperience(float experience) {
			this.SMELTING_EXPERIENCE = experience;
			return this;
		}

		/**
		 * Sets the properties used for the block.
		 * @param color a supplier of the <code>MapColor</code> used on a map
		 * @param stepSound a supplier of the <code>SoundType</code> corresponding to the noise made from stepping on the block
		 * @return the updated <code>Builder</code>
		 */
		public NetherDiamondTypeMaterialConfiguration.Builder blockProperties(Supplier<MapColor> color, Supplier<SoundType> stepSound) {
			this.MAP_COLOR = color;
			this.SOUND_TYPE = stepSound;
			return this;
		}

		/**
		 * Sets the {@link OreGenConfig}s for the material, with a small, medium, large, and extra.
		 * @param small a supplier for the <code>OreGenConfig</code> corresponding to the small vein
		 * @param medium a supplier for the <code>OreGenConfig</code> corresponding to the medium vein
		 * @param large a supplier for the <code>OreGenConfig</code> corresponding to the large vein
		 * @param extra a supplier for the <code>OreGenConfig</code> corresponding to the extra vein
		 * @return the updated <code>Builder</code>
		 */
		public NetherDiamondTypeMaterialConfiguration.Builder oreConfigAll(Supplier<OreGenConfig> small, Supplier<OreGenConfig> medium, Supplier<OreGenConfig> large, Supplier<OreGenConfig> extra) {
			this.ORE_GEN_CONFIGS = new OreGenHolder<>(small, medium, large, extra);
			return this;
		}

		/**
		 * Sets the {@link OreGenConfig}s for the material, with a small, medium, and large.
		 * @param small a supplier for the <code>OreGenConfig</code> corresponding to the small vein
		 * @param medium a supplier for the <code>OreGenConfig</code> corresponding to the medium vein
		 * @param large a supplier for the <code>OreGenConfig</code> corresponding to the large vein
		 * @return the updated <code>Builder</code>
		 */
		public NetherDiamondTypeMaterialConfiguration.Builder oreConfigNoExtra(Supplier<OreGenConfig> small, Supplier<OreGenConfig> medium, Supplier<OreGenConfig> large) {
			this.ORE_GEN_CONFIGS = new OreGenHolder<>(small, medium, large, null);
			return this;
		}

		/**
		 * Sets the {@link OreGenConfig}s for the material, with a small and large.
		 * @param small a supplier for the <code>OreGenConfig</code> corresponding to the small vein
		 * @param large a supplier for the <code>OreGenConfig</code> corresponding to the large vein
		 * @return the updated <code>Builder</code>
		 */
		public NetherDiamondTypeMaterialConfiguration.Builder oreConfigSimple(Supplier<OreGenConfig> small, Supplier<OreGenConfig> large) {
			this.ORE_GEN_CONFIGS = new OreGenHolder<>(small, null, large, null);
			return this;
		}

		/**
		 * Sets the {@link OreGenConfig}s for the material, with a small, large, and extra.
		 * @param small a supplier for the <code>OreGenConfig</code> corresponding to the small vein
		 * @param large a supplier for the <code>OreGenConfig</code> corresponding to the large vein
		 * @param extra a supplier for the <code>OreGenConfig</code> corresponding to the extra vein
		 * @return the updated <code>Builder</code>
		 */
		public NetherDiamondTypeMaterialConfiguration.Builder oreConfigSimpleWithExtra(Supplier<OreGenConfig> small, Supplier<OreGenConfig> large, Supplier<OreGenConfig> extra) {
			this.ORE_GEN_CONFIGS = new OreGenHolder<OreGenConfig>(small, null, large, extra);
			return this;
		}

		/**
		 * Sets the number of drops per ore, with no variation
		 * @param drops the number of drops per ore
		 * @return the updated <code>Builder</code>
		 */
		public NetherDiamondTypeMaterialConfiguration.Builder dropsPerOre(int drops) {
			this.DROPS_PER_ORE = drops;
			return this;
		}

		/**
		 * Sets the number of drops per ore, between a minimum and maximum, inclusive.
		 * @param min the minimum number of drops per ore
		 * @param max the maximum number of drops per ore
		 * @return the updated <code>Builder</code>
		 */
		public NetherDiamondTypeMaterialConfiguration.Builder dropsPerOreMinMax(int min, int max) {
			this.DROPS_PER_ORE = min;
			this.EXTRA_DROPS = max - min;
			return this;
		}

		/**
		 * Sets the number of drops per ore, with a minimum base number of drops, and maximum extra added on
		 * @param baseDrops the minimum, base number of drops
		 * @param extra a maximum attainable extra number of drops
		 * @return the updated <code>Builder</code>
		 */
		public NetherDiamondTypeMaterialConfiguration.Builder dropsPerOre(int baseDrops, int extra) {
			this.DROPS_PER_ORE = baseDrops;
			this.EXTRA_DROPS = extra;
			return this;
		}

		/**
		 * Sets the mining tier of the material. Note that the mineability tier will be set to <code>MineabilityTier.DEFAULT</code>.
		 * @param tier the mining tier of the material. This is the level that the tools can mine.
		 * @return the updated <code>Builder</code>
		 */
		public NetherDiamondTypeMaterialConfiguration.Builder tier(MiningTier tier) {
			this.TIER = tier;
			return this;
		}

		/**
		 * Sets the mining tier and mineability tier of the material.
		 * @param miningTier the mining tier of the material. This is the level that the tools can mine.
		 * @param mineabilityTier the mineability tier of the material. This is the level needed to mine the ores and other blocks.
		 * @return the updated <code>Builder</code>
		 */
		public NetherDiamondTypeMaterialConfiguration.Builder tier(MiningTier miningTier, MineabilityTier mineabilityTier) {
			this.TIER = miningTier;
			this.MINEABILITY_TIER = mineabilityTier;
			return this;
		}

		/**
		 * Sets the color that the trim material shows up in an armor pieces description.
		 * @param colorHex the color, as a hex code string
		 * @return the updated <code>Builder</code>
		 */
		public NetherDiamondTypeMaterialConfiguration.Builder trimMaterialDescriptionColor(String colorHex) {
			this.TRIM_MATERIAL_DESCRIPTION_COLOR = colorHex;
			return this;
		}

		/**
		 * Sets the position of the material's items in the inventory, in relation to other items.
		 * @param toolsBefore the tool set that this material will be placed after, as a string (for example, "stone")
		 * @param armorBefore the armor set that this material will be placed after, as a string (for example, "chainmail")
		 * @param itemBefore the item that the base material will be placed after
		 * @param blockBefore the block that the base block will be placed after
		 * @param oreBefore the ore that the ore will be placed after, as a string (for example, "quartz")
		 * @return the updated <code>Builder</code>
		 */
		public NetherDiamondTypeMaterialConfiguration.Builder setBefore(String toolsBefore, String armorBefore, Item itemBefore, Block blockBefore, String oreBefore) {
			this.TOOLS_BEFORE = toolsBefore;
			this.ARMOR_BEFORE = armorBefore;
			this.ITEM_BEFORE = () -> itemBefore;
			this.BLOCK_BEFORE = () -> blockBefore;
			this.ORE_BEFORE = oreBefore;
			return this;
		}

		/**
		 * Sets the position of the material's items in the inventory, in relation to other items.
		 * @param toolsBefore the tool set that this material will be placed after, as a string (for example, "stone")
		 * @param armorBefore the armor set that this material will be placed after, as a string (for example, "chainmail")
		 * @param itemBefore <code>ResourceLocation</code> of the item that the base material will be placed after
		 * @param blockBefore <code>ResourceLocation</code> of the block that the base block will be placed after
		 * @param oreBefore the ore that the ore will be placed after, as a string (for example, "quartz")
		 * @return the updated <code>Builder</code>
		 */
		public NetherDiamondTypeMaterialConfiguration.Builder setBefore(String toolsBefore, String armorBefore, ResourceLocation itemBefore, ResourceLocation blockBefore, String oreBefore) {
			this.TOOLS_BEFORE = toolsBefore;
			this.ARMOR_BEFORE = armorBefore;
			this.ITEM_BEFORE = () -> BuiltInRegistries.ITEM.get(itemBefore).orElseThrow().value();
			this.BLOCK_BEFORE = () -> BuiltInRegistries.BLOCK.get(blockBefore).orElseThrow().value();
			this.ORE_BEFORE = oreBefore;
			return this;
		}

		/**
		 * Sets the parameters for the spear. Note that I do not know what most of these do; see the vanilla code for typical values.
		 * @param swingDuration sets the swing duration
		 * @param damageMultiplier sets the damage multiplier
		 * @param delay sets the delay
		 * @param dismountMaxDuration sets the maximum duration for dismount
		 * @param dismountMinSpeed sets the minimum speed for dismount
		 * @param knockbackMaxDuration sets the maximum duration for knockback
		 * @param knockbackMinSpeed sets the minimum speed for knockback
		 * @param damageMaxDuration sets the maximum duration for damage
		 * @param damageMinSpeed sets the minimum speed for damage
		 * @return the updated <code>Builder</code>
		 */
		public Builder spearMaterial(float swingDuration, float damageMultiplier, float delay, float dismountMaxDuration, float dismountMinSpeed, float knockbackMaxDuration, float knockbackMinSpeed, float damageMaxDuration, float damageMinSpeed) {
			this.SWING_DURATION = swingDuration;
			this.DAMAGE_MULTIPLIER = damageMultiplier;
			this.DELAY = delay;
			this.DISMOUNT_MAX_DURATION = dismountMaxDuration;
			this.DISMOUNT_MIN_SPEED = dismountMinSpeed;
			this.KNOCKBACK_MAX_DURATION = knockbackMaxDuration;
			this.KNOCKBACK_MIN_SPEED = knockbackMinSpeed;
			this.DAMAGE_MAX_DURATION = damageMaxDuration;
			this.DAMAGE_MIN_SPEED = damageMinSpeed;
			return this;
		}

		/**
		 * Flags for the use of horse armor.
		 * @return the updated <code>Builder</code>
		 */
		public Builder usingHorseArmor() {
			this.USING_HORSE_ARMOR = true;
			return this;
		}

		/**
		 * Flags for the use of nautilus armor.
		 * @return the updated <code>Builder</code>
		 */
		public Builder usingNautilusArmor() {
			this.USING_NAUTILUS_ARMOR = true;
			return this;
		}

		/**
		 * Sets the animal armor preceeding the material's animal armor in the inventory.
		 * @param animalArmorBefore the raw string for the animal armor coming before (for example, "iron")
		 * @return the updated <code>Builder</code>
		 */
		public Builder animalArmorBefore(String animalArmorBefore) {
			this.ANIMAL_ARMOR_BEFORE = animalArmorBefore;
			return this;
		}

		/**
		 * Adds a loot modifier.
		 * @param slot the item to add
		 * @param type either <code>LootModifierType.ADD</code> or <code>LootModifierType.EXTRA</code>. Sets whether to give the item (<code>ADD</code>), or add the item to the current loot (<code>EXTRA</code>).
		 * @param table a string representing the table to add to
		 * @param chance the chance that the item will be added
		 * @return the updated <code>Builder</code>
		 */
		public Builder addLoot(LootItemSlot slot, LootModifierType type, String table, float chance) {
			this.LOOT_MODIFIERS.add(new PreLootModifierInfo(slot, type, table, chance));
			return this;
		}

		/**
		 * Builds the {@link NetherDiamondTypeMaterialConfiguration} that has been specified.
		 * @return the built <code>NetherDiamondTypeMaterialConfiguration</code> object
		 */
		public NetherDiamondTypeMaterialConfiguration build() {
			return new NetherDiamondTypeMaterialConfiguration(MOD_ID, BASE_NAME, HUMAN_READABLE_NAME, TRIM_MATERIAL_DESCRIPTION_COLOR, TOOL_DURABILITY, SPEED, ATTACK_DAMAGE_BONUS, TOOL_ENCHANTMENT, DEFAULT_PROPERTIES, ARMOR_DURABILITY, HEAD_DEFENSE, CHESTPLATE_DEFENSE, SMELTING_EXPERIENCE, LEGGINGS_DEFENSE, BOOTS_DEFENSE, HORSE_DEFENSE, EQUIP_SOUND, TOUGHNESS, KNOCKBACK_RESISTANCE, MAP_COLOR, SOUND_TYPE, ORE_GEN_CONFIGS, DROPS_PER_ORE, EXTRA_DROPS, TIER, MINEABILITY_TIER, TOOLS_BEFORE, ARMOR_BEFORE, ITEM_BEFORE, BLOCK_BEFORE, ORE_BEFORE, SWING_DURATION, DAMAGE_MULTIPLIER, DELAY, DISMOUNT_MAX_DURATION, DISMOUNT_MIN_SPEED, KNOCKBACK_MAX_DURATION, KNOCKBACK_MIN_SPEED, DAMAGE_MAX_DURATION, DAMAGE_MIN_SPEED, USING_HORSE_ARMOR, USING_NAUTILUS_ARMOR, ANIMAL_ARMOR_BEFORE, LOOT_MODIFIERS);
		}

	}

}
