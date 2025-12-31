package net.theelementguy.tegmatlib.core;

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
import net.theelementguy.tegmatlib.core.tiers.MiningTier;
import net.theelementguy.tegmatlib.worldgen.OreGenHolder;
import net.theelementguy.tegmatlib.worldgen.config.OreGenConfig;

import java.util.List;
import java.util.function.Supplier;

public class DiamondTypeMaterialConfiguration extends MaterialConfiguration {

	protected DeferredBlock<Block> ORE_BLOCK;
	protected DeferredBlock<Block> DEEPSLATE_ORE_BLOCK;

	public DiamondTypeMaterialConfiguration(String modId, String baseName, String humanReadableName, String trimMaterialDescriptionColor, int toolDurability, float speed, float attackDamageBonus, int enchantmentValue, Supplier<Item.Properties> defaultProperties, int armorDurability, int helmetDefense, int chestplateDefense, float smeltingExperience, int leggingsDefense, int bootsDefense, int horseDefense, Supplier<Holder<SoundEvent>> equipSound, float toughness, float knockbackResistance, Supplier<MapColor> mapColor, Supplier<SoundType> soundType, OreGenHolder<OreGenConfig> oreGenConfigs, int dropsPerOre, int extraDrops, MiningTier tier, String setBefore, Item itemBefore, Block blockBefore, String oreBefore) {
		super(modId, baseName, humanReadableName, MaterialType.DIAMOND, trimMaterialDescriptionColor, toolDurability, speed, attackDamageBonus, enchantmentValue, defaultProperties, armorDurability, helmetDefense, chestplateDefense, smeltingExperience, leggingsDefense, bootsDefense, horseDefense, equipSound, toughness, knockbackResistance, mapColor, soundType, oreGenConfigs, dropsPerOre, extraDrops, tier, setBefore, itemBefore, blockBefore, oreBefore);
	}

	@Override
	public void fillItems(DeferredRegister.Items register, String modId) {
		BASE_MATERIAL = registerSimpleItemWithTrimMaterial(BASE_NAME, register, modId);
		fillBaseEquipment(register, modId);
	}

	@Override
	public void fillBlocks(DeferredRegister.Blocks register, Supplier<DeferredRegister.Items> itemsRegister) {
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
		return List.of(BLOCK.get(), ORE_BLOCK.get(), DEEPSLATE_ORE_BLOCK.get());
	}

	public Block getOre() {
		return ORE_BLOCK.get();
	}

	public Block getDeepslateOre() {
		return DEEPSLATE_ORE_BLOCK.get();
	}

	//i got this far

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

		protected String SET_BEFORE;
		protected Item ITEM_BEFORE;
		protected Block BLOCK_BEFORE;
		protected String ORE_BEFORE;

		public DiamondTypeMaterialConfiguration.Builder modId(String modId) {
			this.MOD_ID = modId;
			return this;
		}

		public DiamondTypeMaterialConfiguration.Builder baseName(String name) {
			this.BASE_NAME = name;
			return this;
		}

		public DiamondTypeMaterialConfiguration.Builder inGameName(String name) {
			this.HUMAN_READABLE_NAME = name;
			return this;
		}

		public DiamondTypeMaterialConfiguration.Builder defaultProperties(Supplier<Item.Properties> properties) {
			this.DEFAULT_PROPERTIES = properties;
			return this;
		}

		public DiamondTypeMaterialConfiguration.Builder toolMaterial(int durability, float speed, float attackDamageBonus, int enchantmentValue) {
			this.TOOL_DURABILITY = durability;
			this.SPEED = speed;
			this.ATTACK_DAMAGE_BONUS = attackDamageBonus;
			this.TOOL_ENCHANTMENT = enchantmentValue;
			return this;
		}

		public DiamondTypeMaterialConfiguration.Builder armorMaterial(int durability, int helmetDefense, int chestplateDefense, int leggingsDefense, int bootsDefense, int horseDefense, int enchantmentValue, Supplier<Holder<SoundEvent>> equipSound, float toughness, float knockbackResistance) {
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

		public DiamondTypeMaterialConfiguration.Builder armorMaterial(int durability, int helmetDefense, int chestplateDefense, int leggingsDefense, int bootsDefense, int horseDefense, int enchantmentValue, Supplier<Holder<SoundEvent>> equipSound) {
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

		public DiamondTypeMaterialConfiguration.Builder smeltingExperience(float experience) {
			this.SMELTING_EXPERIENCE = experience;
			return this;
		}

		public DiamondTypeMaterialConfiguration.Builder blockProperties(Supplier<MapColor> color, Supplier<SoundType> stepSound) {
			this.MAP_COLOR = color;
			this.SOUND_TYPE = stepSound;
			return this;
		}

		public DiamondTypeMaterialConfiguration.Builder oreConfigAll(Supplier<OreGenConfig> small, Supplier<OreGenConfig> medium, Supplier<OreGenConfig> large, Supplier<OreGenConfig> extra) {
			this.ORE_GEN_CONFIGS = new OreGenHolder<>(small, medium, large, extra);
			return this;
		}

		public DiamondTypeMaterialConfiguration.Builder oreConfigNoExtra(Supplier<OreGenConfig> small, Supplier<OreGenConfig> medium, Supplier<OreGenConfig> large) {
			this.ORE_GEN_CONFIGS = new OreGenHolder<>(small, medium, large, null);
			return this;
		}

		public DiamondTypeMaterialConfiguration.Builder oreConfigSimple(Supplier<OreGenConfig> small, Supplier<OreGenConfig> large) {
			this.ORE_GEN_CONFIGS = new OreGenHolder<>(small, null, large, null);
			return this;
		}

		public DiamondTypeMaterialConfiguration.Builder oreConfigSimpleWithExtra(Supplier<OreGenConfig> small, Supplier<OreGenConfig> large, Supplier<OreGenConfig> extra) {
			this.ORE_GEN_CONFIGS = new OreGenHolder<OreGenConfig>(small, null, large, extra);
			return this;
		}

		public DiamondTypeMaterialConfiguration.Builder dropsPerOre(int drops) {
			this.DROPS_PER_ORE = drops;
			return this;
		}

		public DiamondTypeMaterialConfiguration.Builder dropsPerOreMinMax(int min, int max) {
			this.DROPS_PER_ORE = min;
			this.EXTRA_DROPS = max - min;
			return this;
		}

		public DiamondTypeMaterialConfiguration.Builder dropsPerOre(int baseDrops, int extra) {
			this.DROPS_PER_ORE = baseDrops;
			this.EXTRA_DROPS = extra;
			return this;
		}

		public DiamondTypeMaterialConfiguration.Builder tier(MiningTier tier) {
			this.TIER = tier;
			return this;
		}

		public DiamondTypeMaterialConfiguration.Builder trimMaterialDescriptionColor(String colorHex) {
			this.TRIM_MATERIAL_DESCRIPTION_COLOR = colorHex;
			return this;
		}

		public DiamondTypeMaterialConfiguration.Builder setBefore(String setBefore, Item itemBefore, Block blockBefore, String oreBefore) {
			this.SET_BEFORE = setBefore;
			this.ITEM_BEFORE = itemBefore;
			this.BLOCK_BEFORE = blockBefore;
			this.ORE_BEFORE = oreBefore;
			return this;
		}

		public DiamondTypeMaterialConfiguration build() {
			return new DiamondTypeMaterialConfiguration(MOD_ID, BASE_NAME, HUMAN_READABLE_NAME, TRIM_MATERIAL_DESCRIPTION_COLOR, TOOL_DURABILITY, SPEED, ATTACK_DAMAGE_BONUS, TOOL_ENCHANTMENT, DEFAULT_PROPERTIES, ARMOR_DURABILITY, HEAD_DEFENSE, CHESTPLATE_DEFENSE, SMELTING_EXPERIENCE, LEGGINGS_DEFENSE, BOOTS_DEFENSE, HORSE_DEFENSE, EQUIP_SOUND, TOUGHNESS, KNOCKBACK_RESISTANCE, MAP_COLOR, SOUND_TYPE, ORE_GEN_CONFIGS, DROPS_PER_ORE, EXTRA_DROPS, TIER, SET_BEFORE, ITEM_BEFORE, BLOCK_BEFORE, ORE_BEFORE);
		}

	}
	
}
