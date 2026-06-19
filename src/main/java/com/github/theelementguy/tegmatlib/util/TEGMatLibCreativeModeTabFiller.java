package com.github.theelementguy.tegmatlib.util;

import com.github.theelementguy.tegmatlib.core.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

import javax.swing.text.html.Option;
import java.util.*;

public class TEGMatLibCreativeModeTabFiller {

	/**
	 * Automatically fills the inventory in creative mode.
	 * @param materialHolder A {@link FullyConfiguredMaterialHolder} with the materials <b>in order of how they would appear relatively in the inventory</b>.
	 * @param event The BuildCreativeModeTabContentsEvent from the addCreative method.
	 */
	public static void build(FullyConfiguredMaterialHolder materialHolder, BuildCreativeModeTabContentsEvent event) {
		//TODO: proper ordering
		List<MaterialConfiguration> materials = materialHolder.getMaterials();
		String modID = materialHolder.getModID();
		if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
			for (MaterialConfiguration m : materials) {
				TEGMatLibUtil.inventoryAddAfter(m.getBaseItem(), m.getItemBefore(), event);
				switch (m.getType()) {
					case IRON -> {
						IronTypeMaterialConfiguration ironMatConfig = (IronTypeMaterialConfiguration) m;
						TEGMatLibUtil.inventoryAddAfter(ironMatConfig.getRawItem(), TEGMatLibUtil.getItemFromKey("raw_" + ironMatConfig.getRawBefore(), modID), event);
						TEGMatLibUtil.inventoryAddAfter(ironMatConfig.getNugget(), TEGMatLibUtil.getItemFromKey(ironMatConfig.getRawBefore() + "_nugget", modID), event);
					}
					case CUBIC_ZIRCONIA -> {
						CubicZirconiaTypeMaterialConfiguration cubicMatConfig = (CubicZirconiaTypeMaterialConfiguration) m;
						TEGMatLibUtil.inventoryAddAfter(cubicMatConfig.getRawItem(), TEGMatLibUtil.getItemFromKey("raw_" + cubicMatConfig.getRawBefore(), modID), event);
					}
					case END_IRON -> {
						EndIronTypeMaterialConfiguration endIronMatConfig = (EndIronTypeMaterialConfiguration) m;
						TEGMatLibUtil.inventoryAddAfter(endIronMatConfig.getRawItem(), TEGMatLibUtil.getItemFromKey("raw_" + endIronMatConfig.getRawBefore(), modID), event);
						TEGMatLibUtil.inventoryAddAfter(endIronMatConfig.getNugget(), TEGMatLibUtil.getItemFromKey(endIronMatConfig.getRawBefore() + "_nugget", modID), event);
					}
				}
			}
		}
		if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
			for (MaterialConfiguration m : materials) {
				TEGMatLibUtil.inventoryAddAfter(m.getBaseBlock(), m.getBlockBefore(), event);
			}
		}
		if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
			for (MaterialConfiguration m : materials) {
				switch (m.getType()) {
					case IRON -> {
						IronTypeMaterialConfiguration ironMatConfig = (IronTypeMaterialConfiguration) m;
						TEGMatLibUtil.inventoryAddAfter(ironMatConfig.getOre(), TEGMatLibUtil.getBlockFromKey("deepslate_" + ironMatConfig.getOreBefore() + "_ore", modID), event);
						TEGMatLibUtil.inventoryAddAfter(ironMatConfig.getDeepslateOre(), ironMatConfig.getOre(), event);
						TEGMatLibUtil.inventoryAddAfter(ironMatConfig.getRawBlock(), TEGMatLibUtil.getBlockFromKey("raw_" + ironMatConfig.getRawBefore() + "_block", modID), event);
					}
					case DIAMOND -> {
						DiamondTypeMaterialConfiguration diamondMatConfig = (DiamondTypeMaterialConfiguration) m;
						TEGMatLibUtil.inventoryAddAfter(diamondMatConfig.getOre(), TEGMatLibUtil.getBlockFromKey("deepslate_" + diamondMatConfig.getOreBefore() + "_ore", modID), event);
						TEGMatLibUtil.inventoryAddAfter(diamondMatConfig.getDeepslateOre(), diamondMatConfig.getOre(), event);
					}
					case CUBIC_ZIRCONIA -> {
						CubicZirconiaTypeMaterialConfiguration cubicMatConfig = (CubicZirconiaTypeMaterialConfiguration) m;
						TEGMatLibUtil.inventoryAddAfter(cubicMatConfig.getOre(), TEGMatLibUtil.getBlockFromKey("deepslate_" + cubicMatConfig.getOreBefore() + "_ore", modID), event);
						TEGMatLibUtil.inventoryAddAfter(cubicMatConfig.getDeepslateOre(), cubicMatConfig.getOre(), event);
						TEGMatLibUtil.inventoryAddAfter(cubicMatConfig.getRawBlock(), TEGMatLibUtil.getBlockFromKey("raw_" + cubicMatConfig.getRawBefore() + "_block", modID), event);
					}
					case NETHER_DIAMOND -> {
						NetherDiamondTypeMaterialConfiguration netherDiamondMatConfig = (NetherDiamondTypeMaterialConfiguration) m;
						TEGMatLibUtil.inventoryAddAfter(netherDiamondMatConfig.getNetherOre(), TEGMatLibUtil.getBlockFromKey("nether_" + netherDiamondMatConfig.getOreBefore() + "_ore", modID), event);
					}
					case END_DIAMOND -> {
						EndDiamondTypeMaterialConfiguration endDiamondMatConfig = (EndDiamondTypeMaterialConfiguration) m;
						TEGMatLibUtil.inventoryAddAfter(endDiamondMatConfig.getEndOre(), TEGMatLibUtil.getBlockFromKey(endDiamondMatConfig.getOreBefore(), modID), event);
					}
					case END_IRON -> {
						EndIronTypeMaterialConfiguration endIronMatConfig = (EndIronTypeMaterialConfiguration) m;
						TEGMatLibUtil.inventoryAddAfter(endIronMatConfig.getEndOre(), TEGMatLibUtil.getBlockFromKey(endIronMatConfig.getOreBefore(), modID), event);
						TEGMatLibUtil.inventoryAddAfter(endIronMatConfig.getRawBlock(), TEGMatLibUtil.getBlockFromKey("raw_" + endIronMatConfig.getRawBefore() + "_block", modID), event);
					}
					case SAND_DIAMOND -> {
						SandDiamondTypeMaterialConfiguration sandDiamondTypeMatConfig = (SandDiamondTypeMaterialConfiguration) m;
						TEGMatLibUtil.inventoryAddAfter(sandDiamondTypeMatConfig.getSandOre(), TEGMatLibUtil.getBlockFromKey(sandDiamondTypeMatConfig.getOreBefore(), modID), event);
						TEGMatLibUtil.inventoryAddAfter(sandDiamondTypeMatConfig.getGravelOre(), sandDiamondTypeMatConfig.getSandOre(), event);
					}
				}
			}
		}
		if (event.getTabKey() == CreativeModeTabs.COMBAT) {
			for (MaterialConfiguration m : materials) {
				TEGMatLibUtil.setAddAfter(m.getBaseName(), m.getToolsBefore(), m.getArmorBefore(), event, modID);
				if (m.getHorseArmor().isUsing()) {
					TEGMatLibUtil.inventoryAddAfter(m.getHorseArmor().get().get().get(), TEGMatLibUtil.getItemFromKey(m.getAnimalArmorBefore() + "_horse_armor", materialHolder.getModID()), event);
				}
				if (m.getNautilusArmor().isUsing()) {
					if (Objects.equals(m.getAnimalArmorBefore(), "leather")) {
						event.insertBefore(new ItemStack(Items.COPPER_NAUTILUS_ARMOR, 1), new ItemStack(m.getNautilusArmor().get().get().asItem()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
					} else {
						TEGMatLibUtil.inventoryAddAfter(m.getNautilusArmor().get().get().get(), TEGMatLibUtil.getItemFromKey(m.getAnimalArmorBefore() + "_nautilus_armor", materialHolder.getModID()), event);
					}
				}
			}
		}
		if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
			for (MaterialConfiguration m : materials) {
				TEGMatLibUtil.toolsAddAfter(m.getBaseName(), m.getToolsBefore(), event, modID);
			}
		}

	}

	private record Entry(Item toAdd, Optional<Item> reference) {

	}

}
