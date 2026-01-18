package com.github.theelementguy.tegmatlib.core;

import com.github.theelementguy.tegmatlib.loot.LootItemSlot;
import com.github.theelementguy.tegmatlib.loot.LootModifierType;
import com.github.theelementguy.tegmatlib.loot.PreLootModifierInfo;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
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
 * Subclass of {@link MaterialConfiguration} for Nether-diamond-type materials. Use the {@link NetherDiamondTypeBuilder} for construction.
 * <p>Use this class for Nether materials with a diamond-type format: a gem as the final product.</p>
 */
public class NetherDiamondTypeMaterialConfiguration extends MaterialConfiguration {

	protected DeferredBlock<Block> NETHER_ORE_BLOCK;

	NetherDiamondTypeMaterialConfiguration(String modId, String baseName, String humanReadableName, String trimMaterialDescriptionColor, int toolDurability, float speed, float attackDamageBonus, int enchantmentValue, Supplier<Item.Properties> defaultProperties, int armorDurability, int helmetDefense, int chestplateDefense, float smeltingExperience, int leggingsDefense, int bootsDefense, int horseDefense, Supplier<Holder<SoundEvent>> equipSound, float toughness, float knockbackResistance, Supplier<MapColor> mapColor, Supplier<SoundType> soundType, OreGenHolder<OreGenConfig> oreGenConfigs, int dropsPerOre, int extraDrops, MiningTier tier, MineabilityTier mineabilityTier, String toolsBefore, String armorBefore, Supplier<Item> itemBefore, Supplier<Block> blockBefore, String oreBefore, float swingDuration, float damageMultiplier, float delay, float dismountMaxDuration, float dismountMinSpeed, float knockbackMaxDuration, float knockbackMinSpeed, float damageMaxDuration, float damageMinSpeed, boolean usingHorseArmor, boolean usingNautilusArmor, String animalArmorBefore, List<PreLootModifierInfo> lootModifiers) {
		super(modId, baseName, humanReadableName, MaterialType.NETHER_DIAMOND, trimMaterialDescriptionColor, toolDurability, speed, attackDamageBonus, enchantmentValue, defaultProperties, armorDurability, helmetDefense, chestplateDefense, smeltingExperience, leggingsDefense, bootsDefense, horseDefense, equipSound, toughness, knockbackResistance, mapColor, soundType, oreGenConfigs, dropsPerOre, extraDrops, tier, mineabilityTier, toolsBefore, armorBefore, itemBefore, blockBefore, oreBefore, swingDuration, damageMultiplier, delay, dismountMaxDuration, dismountMinSpeed, knockbackMaxDuration, knockbackMinSpeed, damageMaxDuration, damageMinSpeed, usingHorseArmor, usingNautilusArmor, animalArmorBefore, lootModifiers);
	}

	@Override
	public void fillItems(DeferredRegister.Items register) {
		BASE_MATERIAL = registerSimpleItemWithTrimMaterial(BASE_NAME, register, MOD_ID);
		fillBaseEquipment(register, MOD_ID);
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

}
