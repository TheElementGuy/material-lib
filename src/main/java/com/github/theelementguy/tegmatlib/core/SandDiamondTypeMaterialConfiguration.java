package com.github.theelementguy.tegmatlib.core;

import com.github.theelementguy.tegmatlib.data.ModelException;
import com.github.theelementguy.tegmatlib.loot.PreLootModifierInfo;
import com.github.theelementguy.tegmatlib.util.TEGMatLibUtil;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ColorRGBA;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ColoredFallingBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import com.github.theelementguy.tegmatlib.core.tiers.MineabilityTier;
import com.github.theelementguy.tegmatlib.core.tiers.MiningTier;
import com.github.theelementguy.tegmatlib.worldgen.OreGenHolder;
import com.github.theelementguy.tegmatlib.worldgen.config.OreGenConfig;

import java.util.List;
import java.util.function.Supplier;

/**
 * Subclass of {@link MaterialConfiguration} for sand diamond-type materials. Use the {@link SandDiamondTypeBuilder} for construction.
 * <p>Use this class for overworld materials with a diamond-type format found in ocean sand: a gem as the final product.</p>
 */
public class SandDiamondTypeMaterialConfiguration extends MaterialConfiguration {

	protected DeferredBlock<Block> SAND_ORE_BLOCK;
	protected DeferredBlock<Block> GRAVEL_ORE_BLOCK;

	public SandDiamondTypeMaterialConfiguration(String modId, String baseName, String humanReadableName, String trimMaterialDescriptionColor, int toolDurability, float speed, float attackDamageBonus, int enchantmentValue, Supplier<Item.Properties> defaultProperties, int armorDurability, int helmetDefense, int chestplateDefense, float smeltingExperience, int leggingsDefense, int bootsDefense, int horseDefense, Supplier<Holder<SoundEvent>> equipSound, float toughness, float knockbackResistance, Supplier<MapColor> mapColor, Supplier<SoundType> soundType, OreGenHolder<OreGenConfig> oreGenConfigs, int dropsPerOre, int extraDrops, MiningTier tier, MineabilityTier mineabilityTier, String toolsBefore, String armorBefore, Supplier<Item> itemBefore, Supplier<Block> blockBefore, String oreBefore, boolean usingHorseArmor, String animalArmorBefore, List<PreLootModifierInfo> lootModifiers, List<ModelException> modelExceptions) {
		super(modId, baseName, humanReadableName, MaterialType.SAND_DIAMOND, trimMaterialDescriptionColor, toolDurability, speed, attackDamageBonus, enchantmentValue, defaultProperties, armorDurability, helmetDefense, chestplateDefense, smeltingExperience, leggingsDefense, bootsDefense, horseDefense, equipSound, toughness, knockbackResistance, mapColor, soundType, oreGenConfigs, dropsPerOre, extraDrops, tier, mineabilityTier, toolsBefore, armorBefore, itemBefore, blockBefore, oreBefore, usingHorseArmor, animalArmorBefore, lootModifiers, modelExceptions);
	}

	@Override
	public void fillItems(DeferredRegister.Items register, DeferredRegister<ArmorMaterial> armorRegister) {
		BASE_MATERIAL = registerSimpleItemWithTrimMaterial(BASE_NAME, register);
		fillBaseEquipment(register, armorRegister);
	}

	@Override
	public void fillBlocks(DeferredRegister.Blocks register, Supplier<DeferredRegister.Items> itemsRegister) {
		SAND_ORE_BLOCK = register.registerBlock("sand_" + BASE_NAME + "_ore", (p) -> new ColoredFallingBlock(new ColorRGBA(14406560), p), BlockBehaviour.Properties.of().destroyTime(1.5f).explosionResistance(1f).mapColor(MapColor.SAND).sound(SoundType.SAND).requiresCorrectToolForDrops());
		itemsRegister.get().registerSimpleBlockItem("sand_" + BASE_NAME + "_ore", () -> SAND_ORE_BLOCK.get());
		GRAVEL_ORE_BLOCK = register.registerBlock("gravel_" + BASE_NAME + "_ore", (p) -> new ColoredFallingBlock(new ColorRGBA(14406560), p), BlockBehaviour.Properties.of().destroyTime(1.5f).explosionResistance(1f).mapColor(MapColor.STONE).sound(SoundType.GRAVEL).requiresCorrectToolForDrops());
		itemsRegister.get().registerSimpleBlockItem("gravel_" + BASE_NAME + "_ore", () -> GRAVEL_ORE_BLOCK.get());
		fillBaseBlock(register, itemsRegister);
	}

	@Override
	public List<OreConfiguration.TargetBlockState> getOreStates() {
		return List.of(OreConfiguration.target(new BlockMatchTest(Blocks.SAND), SAND_ORE_BLOCK.get().defaultBlockState()), OreConfiguration.target(new BlockMatchTest(Blocks.GRAVEL), GRAVEL_ORE_BLOCK.get().defaultBlockState()));
	}

	@Override
	public List<Block> getBlocks() {
		return List.of(BLOCK.get(), SAND_ORE_BLOCK.get(), GRAVEL_ORE_BLOCK.get());
	}

	public Block getSandOre() {
		return SAND_ORE_BLOCK.get();
	}

	public Block getGravelOre() {
		return GRAVEL_ORE_BLOCK.get();
	}

}