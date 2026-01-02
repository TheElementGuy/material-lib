package com.github.theelementguy.tegmatlib.core;

import com.github.theelementguy.tegmatlib.worldgen.OreGenHolder;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import com.github.theelementguy.tegmatlib.core.tiers.MineabilityTier;
import com.github.theelementguy.tegmatlib.core.tiers.MiningTier;
import com.github.theelementguy.tegmatlib.worldgen.*;
import com.github.theelementguy.tegmatlib.worldgen.config.OreGenConfig;

import java.util.List;
import java.util.function.Supplier;

/**
 * Subclass of {@link MaterialConfiguration} for iron-type materials. Use the {@link IronTypeMaterialConfiguration.Builder} for construction.
 * <p>Use this class for overworld materials with an iron-type format: a raw metal that needs to be smelted into the final ingot.</p>
 */
public class IronTypeMaterialConfiguration extends MaterialConfiguration {

	protected DeferredItem<Item> RAW_MATERIAL;
	protected DeferredItem<Item> NUGGET;

	protected DeferredBlock<Block> RAW_BLOCK;
	protected DeferredBlock<Block> ORE_BLOCK;
	protected DeferredBlock<Block> DEEPSLATE_ORE_BLOCK;

	protected String RAW_BEFORE;

	private IronTypeMaterialConfiguration(String modId, String baseName, String humanReadableName, String trimMaterialDescriptionColor, int toolDurability, float speed, float attackDamageBonus, int enchantmentValue, Supplier<Item.Properties> defaultProperties, int armorDurability, int helmetDefense, int chestplateDefense, float smeltingExperience, int leggingsDefense, int bootsDefense, int horseDefense, Supplier<Holder<SoundEvent>> equipSound, float toughness, float knockbackResistance, Supplier<MapColor> mapColor, Supplier<SoundType> soundType, OreGenHolder<OreGenConfig> oreGenConfigs, int dropsPerOre, int extraDrops, MiningTier tier, MineabilityTier mineabilityTier, String toolsBefore, String armorBefore, Item itemBefore, Block blockBefore, String oreBefore, String rawBefore) {
		super(modId, baseName, humanReadableName, MaterialType.IRON, trimMaterialDescriptionColor, toolDurability, speed, attackDamageBonus, enchantmentValue, defaultProperties, armorDurability, helmetDefense, chestplateDefense, smeltingExperience, leggingsDefense, bootsDefense, horseDefense, equipSound, toughness, knockbackResistance, mapColor, soundType, oreGenConfigs, dropsPerOre, extraDrops, tier, mineabilityTier, toolsBefore, armorBefore, itemBefore, blockBefore, oreBefore);
		this.RAW_BEFORE = rawBefore;
	}

	@Override
	public void fillItems(DeferredRegister.Items register) {
		BASE_MATERIAL = registerSimpleItemWithTrimMaterial(BASE_NAME + "_ingot", register, MOD_ID);
		RAW_MATERIAL = registerSimpleItem("raw_" + BASE_NAME, register, MOD_ID);
		NUGGET = registerSimpleItem(BASE_NAME + "_nugget", register, MOD_ID);
		fillBaseEquipment(register, MOD_ID);
	}

	@Override
	public void fillBlocks(DeferredRegister.Blocks register, Supplier<DeferredRegister.Items> itemsRegister) {
		RAW_BLOCK = registerSimpleBlock("raw_" + BASE_NAME + "_block", register, itemsRegister, 3f, 6f, MAP_COLOR.get(), SOUND_TYPE.get());
		ORE_BLOCK = registerSimpleBlock(BASE_NAME + "_ore", register, itemsRegister, 3f, 3f, MapColor.STONE, SoundType.STONE);
		DEEPSLATE_ORE_BLOCK = registerSimpleBlock("deepslate_" + BASE_NAME + "_ore", register, itemsRegister, 4.5f, 3f, MapColor.DEEPSLATE, SoundType.DEEPSLATE);
		fillBaseBlock(register, itemsRegister);
	}

	@Override
	public List<OreConfiguration.TargetBlockState> getOreStates() {
		return List.of(OreConfiguration.target(new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES), ORE_BLOCK.get().defaultBlockState()), OreConfiguration.target(new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES), DEEPSLATE_ORE_BLOCK.get().defaultBlockState()));
	}

	@Override
	public List<Block> getBlocks() {
		return List.of(BLOCK.get(), RAW_BLOCK.get(), ORE_BLOCK.get(), DEEPSLATE_ORE_BLOCK.get());
	}

	public Block getRawBlock() {
		return RAW_BLOCK.get();
	}

	public Block getOre() {
		return ORE_BLOCK.get();
	}

	public Block getDeepslateOre() {
		return DEEPSLATE_ORE_BLOCK.get();
	}

	public Item getRawItem() {
		return RAW_MATERIAL.get();
	}

	public String getRawBefore() {
		return RAW_BEFORE;
	}

	public Item getNugget() {
		return NUGGET.get();
	}

	/**
	 * Builder used to construct {@link IronTypeMaterialConfiguration}s. Create a new instance and call methods until ready to call <code>build()</code>.
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
		protected Item ITEM_BEFORE;
		protected Block BLOCK_BEFORE;
		protected String ORE_BEFORE;
		protected String RAW_BEFORE;

		/**
		 * Sets the mod ID for the material.
		 * @param modId your mod ID
		 * @return the updated <code>Builder</code>
		 */
		public IronTypeMaterialConfiguration.Builder modId(String modId) {
			this.MOD_ID = modId;
			return this;
		}

		/**
		 * Sets the base name for the material. This should only use lowercase letters and underscores.
		 * @param name the name to be used
		 * @return the updated <code>Builder</code>
		 */
		public IronTypeMaterialConfiguration.Builder baseName(String name) {
			this.BASE_NAME = name;
			return this;
		}

		/**
		 * Sets the in-game name for the material. This will be capitalized appropriately.
		 * @param name the in-game name to be used
		 * @return the updated <code>Builder</code>
		 */
		public IronTypeMaterialConfiguration.Builder inGameName(String name) {
			this.HUMAN_READABLE_NAME = name;
			return this;
		}

		/**
		 * Sets the default properties of the items that are created (e.g. fire-proof).
		 * @param properties a supplier of the default properties (for example, <code>() -> new Item.Properties().fireResistant()</code>
		 * @return the updated <code>Builder</code>
		 */
		public IronTypeMaterialConfiguration.Builder defaultProperties(Supplier<Item.Properties> properties) {
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
		public IronTypeMaterialConfiguration.Builder toolMaterial(int durability, float speed, float attackDamageBonus, int enchantmentValue) {
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
		public IronTypeMaterialConfiguration.Builder armorMaterial(int durability, int helmetDefense, int chestplateDefense, int leggingsDefense, int bootsDefense, int horseDefense, int enchantmentValue, Supplier<Holder<SoundEvent>> equipSound, float toughness, float knockbackResistance) {
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
		public IronTypeMaterialConfiguration.Builder armorMaterial(int durability, int helmetDefense, int chestplateDefense, int leggingsDefense, int bootsDefense, int horseDefense, int enchantmentValue, Supplier<Holder<SoundEvent>> equipSound) {
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
		public IronTypeMaterialConfiguration.Builder smeltingExperience(float experience) {
			this.SMELTING_EXPERIENCE = experience;
			return this;
		}

		/**
		 * Sets the properties used for the block.
		 * @param color a supplier of the <code>MapColor</code> used on a map
		 * @param stepSound a supplier of the <code>SoundType</code> corresponding to the noise made from stepping on the block
		 * @return the updated <code>Builder</code>
		 */
		public IronTypeMaterialConfiguration.Builder blockProperties(Supplier<MapColor> color, Supplier<SoundType> stepSound) {
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
		public IronTypeMaterialConfiguration.Builder oreConfigAll(Supplier<OreGenConfig> small, Supplier<OreGenConfig> medium, Supplier<OreGenConfig> large, Supplier<OreGenConfig> extra) {
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
		public IronTypeMaterialConfiguration.Builder oreConfigNoExtra(Supplier<OreGenConfig> small, Supplier<OreGenConfig> medium, Supplier<OreGenConfig> large) {
			this.ORE_GEN_CONFIGS = new OreGenHolder<>(small, medium, large, null);
			return this;
		}

		/**
		 * Sets the {@link OreGenConfig}s for the material, with a small and large.
		 * @param small a supplier for the <code>OreGenConfig</code> corresponding to the small vein
		 * @param large a supplier for the <code>OreGenConfig</code> corresponding to the large vein
		 * @return the updated <code>Builder</code>
		 */
		public IronTypeMaterialConfiguration.Builder oreConfigSimple(Supplier<OreGenConfig> small, Supplier<OreGenConfig> large) {
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
		public IronTypeMaterialConfiguration.Builder oreConfigSimpleWithExtra(Supplier<OreGenConfig> small, Supplier<OreGenConfig> large, Supplier<OreGenConfig> extra) {
			this.ORE_GEN_CONFIGS = new OreGenHolder<OreGenConfig>(small, null, large, extra);
			return this;
		}

		/**
		 * Sets the number of drops per ore, with no variation
		 * @param drops the number of drops per ore
		 * @return the updated <code>Builder</code>
		 */
		public IronTypeMaterialConfiguration.Builder dropsPerOre(int drops) {
			this.DROPS_PER_ORE = drops;
			return this;
		}

		/**
		 * Sets the number of drops per ore, between a minimum and maximum, inclusive.
		 * @param min the minimum number of drops per ore
		 * @param max the maximum number of drops per ore
		 * @return the updated <code>Builder</code>
		 */
		public IronTypeMaterialConfiguration.Builder dropsPerOreMinMax(int min, int max) {
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
		public IronTypeMaterialConfiguration.Builder dropsPerOre(int baseDrops, int extra) {
			this.DROPS_PER_ORE = baseDrops;
			this.EXTRA_DROPS = extra;
			return this;
		}

		/**
		 * Sets the mining tier of the material. Note that the mineability tier will be set to <code>MineabilityTier.DEFAULT</code>.
		 * @param tier the mining tier of the material. This is the level that the tools can mine.
		 * @return the updated <code>Builder</code>
		 */
		public IronTypeMaterialConfiguration.Builder tier(MiningTier tier) {
			this.TIER = tier;
			return this;
		}

		/**
		 * Sets the mining tier and mineability tier of the material.
		 * @param miningTier the mining tier of the material. This is the level that the tools can mine.
		 * @param mineabilityTier the mineability tier of the material. This is the level needed to mine the ores and other blocks.
		 * @return the updated <code>Builder</code>
		 */
		public IronTypeMaterialConfiguration.Builder tier(MiningTier miningTier, MineabilityTier mineabilityTier) {
			this.TIER = miningTier;
			this.MINEABILITY_TIER = mineabilityTier;
			return this;
		}

		/**
		 * Sets the color that the trim material shows up in an armor pieces description.
		 * @param colorHex the color, as a hex code string
		 * @return the updated <code>Builder</code>
		 */
		public IronTypeMaterialConfiguration.Builder trimMaterialDescriptionColor(String colorHex) {
			this.TRIM_MATERIAL_DESCRIPTION_COLOR = colorHex;
			return this;
		}

		/**
		 * Sets the position of the material's items in the inventory, in relation to other items.
		 * @param toolsBefore the tool set that this material will be placed after, as a string (for example, "stone")
		 * @param armorBefore the armor set that this material will be placed after, as a string (for example, "chainmail")
		 * @param itemBefore the item that the base material will be placed after
		 * @param blockBefore the block that the base block will be placed after
		 * @param rawBefore the raw item/block that the raw material/block will be placed after, as a string (for example, "gold")
		 * @param oreBefore the ore that the stone and deepslate ores will be placed after, as a string (for example, "lapis")
		 * @return the updated <code>Builder</code>
		 */
		public IronTypeMaterialConfiguration.Builder setBefore(String toolsBefore, String armorBefore, Item itemBefore, Block blockBefore, String rawBefore, String oreBefore) {
			this.TOOLS_BEFORE = toolsBefore;
			this.ARMOR_BEFORE = armorBefore;
			this.ITEM_BEFORE = itemBefore;
			this.BLOCK_BEFORE = blockBefore;
			this.ORE_BEFORE = oreBefore;
			this.RAW_BEFORE = rawBefore;
			return this;
		}

		/**
		 * Builds the {@link IronTypeMaterialConfiguration} that has been specified.
		 * @return the built <code>IronTypeMaterialConfiguration</code> object
		 */
		public IronTypeMaterialConfiguration build() {
			return new IronTypeMaterialConfiguration(MOD_ID, BASE_NAME, HUMAN_READABLE_NAME, TRIM_MATERIAL_DESCRIPTION_COLOR, TOOL_DURABILITY, SPEED, ATTACK_DAMAGE_BONUS, TOOL_ENCHANTMENT, DEFAULT_PROPERTIES, ARMOR_DURABILITY, HEAD_DEFENSE, CHESTPLATE_DEFENSE, SMELTING_EXPERIENCE, LEGGINGS_DEFENSE, BOOTS_DEFENSE, HORSE_DEFENSE, EQUIP_SOUND, TOUGHNESS, KNOCKBACK_RESISTANCE, MAP_COLOR, SOUND_TYPE, ORE_GEN_CONFIGS, DROPS_PER_ORE, EXTRA_DROPS, TIER, MINEABILITY_TIER, TOOLS_BEFORE, ARMOR_BEFORE, ITEM_BEFORE, BLOCK_BEFORE, ORE_BEFORE, RAW_BEFORE);
		}

	}

}
