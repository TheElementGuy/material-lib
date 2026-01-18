package com.github.theelementguy.tegmatlib.core;

import com.github.theelementguy.tegmatlib.loot.LootItemSlot;
import com.github.theelementguy.tegmatlib.loot.LootModifierType;
import com.github.theelementguy.tegmatlib.loot.PreLootModifierInfo;
import com.github.theelementguy.tegmatlib.worldgen.OreGenHolder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
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
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * Subclass of {@link MaterialConfiguration} for Cubic-Zirconia-type materials. Use the {@link CubicZirconiaTypeBuilder} for construction.
 * <p>Use this class for overworld materials with a Cubic-Zirconia-type format: a raw gem that needs to be smelted into the final product. Note that this differs from iron-type by not possessing a nugget and the final product not having "ingot" in the name.</p>
 */
public class CubicZirconiaTypeMaterialConfiguration extends MaterialConfiguration {

	protected DeferredItem<@NotNull Item> RAW_MATERIAL;

	protected DeferredBlock<@NotNull Block> RAW_BLOCK;
	protected DeferredBlock<@NotNull Block> ORE_BLOCK;
	protected DeferredBlock<@NotNull Block> DEEPSLATE_ORE_BLOCK;

	protected String RAW_BEFORE;

	CubicZirconiaTypeMaterialConfiguration(String modId, String baseName, String humanReadableName, String trimMaterialDescriptionColor, int toolDurability, float speed, float attackDamageBonus, int enchantmentValue, Supplier<Item.Properties> defaultProperties, int armorDurability, int helmetDefense, int chestplateDefense, float smeltingExperience, int leggingsDefense, int bootsDefense, int horseDefense, Supplier<Holder<@NotNull SoundEvent>> equipSound, float toughness, float knockbackResistance, Supplier<MapColor> mapColor, Supplier<SoundType> soundType, OreGenHolder<OreGenConfig> oreGenConfigs, int dropsPerOre, int extraDrops, MiningTier tier, MineabilityTier mineabilityTier, String toolsBefore, String armorBefore, Supplier<Item> itemBefore, Supplier<Block> blockBefore, String oreBefore, String rawBefore, float swingDuration, float damageMultiplier, float delay, float dismountMaxDuration, float dismountMinSpeed, float knockbackMaxDuration, float knockbackMinSpeed, float damageMaxDuration, float damageMinSpeed, boolean usingHorseArmor, boolean usingNautilusArmor, String animalArmorBefore, List<PreLootModifierInfo> lootModifiers) {
		super(modId, baseName, humanReadableName, MaterialType.CUBIC_ZIRCONIA, trimMaterialDescriptionColor, toolDurability, speed, attackDamageBonus, enchantmentValue, defaultProperties, armorDurability, helmetDefense, chestplateDefense, smeltingExperience, leggingsDefense, bootsDefense, horseDefense, equipSound, toughness, knockbackResistance, mapColor, soundType, oreGenConfigs, dropsPerOre, extraDrops, tier, mineabilityTier, toolsBefore, armorBefore, itemBefore, blockBefore, oreBefore, swingDuration, damageMultiplier, delay, dismountMaxDuration, dismountMinSpeed, knockbackMaxDuration, knockbackMinSpeed, damageMaxDuration, damageMinSpeed, usingHorseArmor, usingNautilusArmor, animalArmorBefore, lootModifiers);
		this.RAW_BEFORE = rawBefore;
	}

	@Override
	public void fillItems(DeferredRegister.Items register) {
		BASE_MATERIAL = registerSimpleItemWithTrimMaterial(BASE_NAME, register, MOD_ID);
		RAW_MATERIAL = registerSimpleItem("raw_" + BASE_NAME, register, MOD_ID);
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

}
