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
import net.theelementguy.tegmatlib.worldgen.OreGenConfigHolder;

import java.util.List;
import java.util.function.Supplier;

public class IronTypeMaterialConfiguration extends MaterialConfiguration {

	protected DeferredItem<Item> RAW_MATERIAL;

	protected DeferredBlock<Block> RAW_BLOCK;
	protected DeferredBlock<Block> ORE_BLOCK;
	protected DeferredBlock<Block> DEEPSLATE_ORE_BLOCK;

	public IronTypeMaterialConfiguration(String modId, String baseName, String humanReadableName, MaterialType materialType, String trimMaterialDescriptionColor, int toolDurability, float speed, float attackDamageBonus, int enchantmentValue, Item.Properties defaultProperties, int armorDurability, int helmetDefense, int chestplateDefense, float smeltingExperience, int leggingsDefense, int bootsDefense, int horseDefense, Holder<SoundEvent> equipSound, float toughness, float knockbackResistance, MapColor mapColor, SoundType soundType, OreGenConfigHolder oreGenConfigs, int dropsPerOre, int extraDrops, MiningTier tier) {
		super(modId, baseName, humanReadableName, materialType, trimMaterialDescriptionColor, toolDurability, speed, attackDamageBonus, enchantmentValue, defaultProperties, armorDurability, helmetDefense, chestplateDefense, smeltingExperience, leggingsDefense, bootsDefense, horseDefense, equipSound, toughness, knockbackResistance, mapColor, soundType, oreGenConfigs, dropsPerOre, extraDrops, tier);
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

}
