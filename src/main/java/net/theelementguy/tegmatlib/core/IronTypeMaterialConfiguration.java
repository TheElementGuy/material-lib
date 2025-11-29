package net.theelementguy.tegmatlib.core;

import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.trim.MaterialAssetGroup;
import net.minecraft.world.item.equipment.trim.TrimMaterial;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.theelementguy.tegmatlib.core.tiers.MiningTier;
import net.theelementguy.tegmatlib.worldgen.BiomeModifierKeyHolder;
import net.theelementguy.tegmatlib.worldgen.ConfiguredFeatureKeyHolder;
import net.theelementguy.tegmatlib.worldgen.OreGenConfigHolder;
import net.theelementguy.tegmatlib.worldgen.PlacedFeatureKeyHolder;
import net.theelementguy.tegmatlib.worldgen.config.OreGenConfig;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

public class IronTypeMaterialConfiguration extends MaterialConfiguration {

	protected DeferredItem<Item> RAW_MATERIAL;

	protected DeferredBlock<Block> RAW_BLOCK;
	protected DeferredBlock<Block> ORE_BLOCK;
	protected DeferredBlock<Block> DEEPSLATE_ORE_BLOCK;

	private IronTypeMaterialConfiguration(String modId, String baseName, String humanReadableName, String trimMaterialDescriptionColor, int toolDurability, float speed, float attackDamageBonus, int enchantmentValue, Supplier<Item.Properties> defaultProperties, int armorDurability, int helmetDefense, int chestplateDefense, float smeltingExperience, int leggingsDefense, int bootsDefense, int horseDefense, Supplier<Holder<SoundEvent>> equipSound, float toughness, float knockbackResistance, MapColor mapColor, SoundType soundType, OreGenConfigHolder oreGenConfigs, int dropsPerOre, int extraDrops, MiningTier tier) {
		super(modId, baseName, humanReadableName, MaterialType.IRON, trimMaterialDescriptionColor, toolDurability, speed, attackDamageBonus, enchantmentValue, defaultProperties, armorDurability, helmetDefense, chestplateDefense, smeltingExperience, leggingsDefense, bootsDefense, horseDefense, equipSound, toughness, knockbackResistance, mapColor, soundType, oreGenConfigs, dropsPerOre, extraDrops, tier);
	}

	@Override
	public void fillItems(DeferredRegister.Items register, String modId) {
		BASE_MATERIAL = registerSimpleItemWithTrimMaterial(BASE_NAME + "_ingot", register, modId);
		RAW_MATERIAL = registerSimpleItem("raw_" + BASE_NAME, register, modId);
		fillBaseEquipment(register, modId);
	}

	@Override
	public void fillBlocks(DeferredRegister.Blocks register, Supplier<DeferredRegister.Items> itemsRegister) {
		RAW_BLOCK = registerSimpleBlock("raw_" + BASE_NAME + "_block", register, itemsRegister, 3f, 6f, MAP_COLOR, SOUND_TYPE);
		ORE_BLOCK = registerSimpleBlock(BASE_NAME + "_ore", register, itemsRegister, 3f, 3f, MapColor.STONE, SoundType.STONE);
		DEEPSLATE_ORE_BLOCK = registerSimpleBlock("deepslate_" + BASE_NAME + "_ore", register, itemsRegister, 4.5f, 3f, MapColor.DEEPSLATE, SoundType.DEEPSLATE);
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

	public static class Builder {

		protected String BASE_NAME;
		protected String MOD_ID;
		protected String HUMAN_READABLE_NAME;

		protected Supplier<Item.Properties> DEFAULT_PROPERTIES = Item.Properties::new;

		protected float SMELTING_EXPERIENCE;

		protected String TRIM_MATERIAL_DESCRIPTION_COLOR;

		protected MapColor MAP_COLOR;
		protected SoundType SOUND_TYPE;
		protected OreGenConfigHolder ORE_GEN_CONFIGS;

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

		public Builder modId(String modId) {
			this.MOD_ID = modId;
			return this;
		}

		public Builder baseName(String name) {
			this.BASE_NAME = name;
			return this;
		}

		public Builder inGameName(String name) {
			this.HUMAN_READABLE_NAME = name;
			return this;
		}

		public Builder defaultProperties(Supplier<Item.Properties> properties) {
			this.DEFAULT_PROPERTIES = properties;
			return this;
		}

		public Builder toolMaterial(int durability, float speed, float attackDamageBonus, int enchantmentValue) {
			this.TOOL_DURABILITY = durability;
			this.SPEED = speed;
			this.ATTACK_DAMAGE_BONUS = attackDamageBonus;
			this.TOOL_ENCHANTMENT = enchantmentValue;
			return this;
		}

		public Builder armorMaterial(int durability, int helmetDefense, int chestplateDefense, int leggingsDefense, int bootsDefense, int horseDefense, int enchantmentValue, Supplier<Holder<SoundEvent>> equipSound, float toughness, float knockbackResistance) {
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

		public Builder armorMaterial(int durability, int helmetDefense, int chestplateDefense, int leggingsDefense, int bootsDefense, int horseDefense, int enchantmentValue, Supplier<Holder<SoundEvent>> equipSound) {
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

		public Builder smeltingExperience(float experience) {
			this.SMELTING_EXPERIENCE = experience;
			return this;
		}

		public Builder blockProperties(MapColor color, SoundType stepSound) {
			this.MAP_COLOR = color;
			this.SOUND_TYPE = stepSound;
			return this;
		}

		public Builder oreConfigAll(Supplier<OreGenConfig> small, Supplier<OreGenConfig> medium, Supplier<OreGenConfig> large, Supplier<OreGenConfig> extra) {
			this.ORE_GEN_CONFIGS = new OreGenConfigHolder(small, medium, large, extra);
			return this;
		}

		public Builder oreConfigNoExtra(Supplier<OreGenConfig> small, Supplier<OreGenConfig> medium, Supplier<OreGenConfig> large) {
			this.ORE_GEN_CONFIGS = new OreGenConfigHolder(small, medium, large, null);
			return this;
		}

		public Builder oreConfigSimple(Supplier<OreGenConfig> small, Supplier<OreGenConfig> large) {
			this.ORE_GEN_CONFIGS = new OreGenConfigHolder(small, null, large, null);
			return this;
		}

		public Builder oreConfigSimpleWithExtra(Supplier<OreGenConfig> small, Supplier<OreGenConfig> large, Supplier<OreGenConfig> extra) {
			this.ORE_GEN_CONFIGS = new OreGenConfigHolder(small, null, large, extra);
			return this;
		}

		public Builder dropsPerOre(int drops) {
			this.DROPS_PER_ORE = drops;
			return this;
		}

		public Builder dropsPerOreMinMax(int min, int max) {
			this.DROPS_PER_ORE = min;
			this.EXTRA_DROPS = max - min;
			return this;
		}

		public Builder dropsPerOre(int baseDrops, int extra) {
			this.DROPS_PER_ORE = baseDrops;
			this.EXTRA_DROPS = extra;
			return this;
		}

		public Builder tier(MiningTier tier) {
			this.TIER = tier;
			return this;
		}

		public Builder trimMaterialDescriptionColor(String colorHex) {
			this.TRIM_MATERIAL_DESCRIPTION_COLOR = colorHex;
			return this;
		}

		public IronTypeMaterialConfiguration build() {
			return new IronTypeMaterialConfiguration(MOD_ID, BASE_NAME, HUMAN_READABLE_NAME, TRIM_MATERIAL_DESCRIPTION_COLOR, TOOL_DURABILITY, SPEED, ATTACK_DAMAGE_BONUS, TOOL_ENCHANTMENT, DEFAULT_PROPERTIES, ARMOR_DURABILITY, HEAD_DEFENSE, CHESTPLATE_DEFENSE, SMELTING_EXPERIENCE, LEGGINGS_DEFENSE, BOOTS_DEFENSE, HORSE_DEFENSE, EQUIP_SOUND, TOUGHNESS, KNOCKBACK_RESISTANCE, MAP_COLOR, SOUND_TYPE, ORE_GEN_CONFIGS, DROPS_PER_ORE, EXTRA_DROPS, TIER);
		}

	}

}
