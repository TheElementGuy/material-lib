package com.github.theelementguy.tegmatlib.util;

import com.github.theelementguy.tegmatlib.core.*;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import org.slf4j.Logger;

import java.util.*;
import java.util.function.Predicate;

public class TEGMatLibCreativeModeTabFiller {

	private static final Logger LOG = LogUtils.getLogger();

	/**
	 * Automatically fills the inventory in creative mode.
	 * @param materialHolder A {@link FullyConfiguredMaterialHolder} with the materials.
	 * @param event The BuildCreativeModeTabContentsEvent from the addCreative method.
	 */
	public static void build(FullyConfiguredMaterialHolder materialHolder, BuildCreativeModeTabContentsEvent event) {
		LOG.info("Filling creative mode tabs for mod {}", materialHolder.getModID());
		List<Entry> entries = new ArrayList<>();
		List<MaterialConfiguration> materials = materialHolder.getMaterials();
		String modID = materialHolder.getModID();
		if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
			for (MaterialConfiguration m : materials) {
				registerEntry(m.getBaseItem(), m.getItemBefore(), entries);
				switch (m.getType()) {
					case IRON -> {
						IronTypeMaterialConfiguration ironMatConfig = (IronTypeMaterialConfiguration) m;
						registerEntry(ironMatConfig.getRawItem(), TEGMatLibUtil.getItemFromKey("raw_" + ironMatConfig.getRawBefore(), modID), entries);
						registerEntry(ironMatConfig.getNugget(), TEGMatLibUtil.getItemFromKey(ironMatConfig.getRawBefore() + "_nugget", modID), entries);
					}
					case CUBIC_ZIRCONIA -> {
						CubicZirconiaTypeMaterialConfiguration cubicMatConfig = (CubicZirconiaTypeMaterialConfiguration) m;
						registerEntry(cubicMatConfig.getRawItem(), TEGMatLibUtil.getItemFromKey("raw_" + cubicMatConfig.getRawBefore(), modID), entries);
					}
					case END_IRON -> {
						EndIronTypeMaterialConfiguration endIronMatConfig = (EndIronTypeMaterialConfiguration) m;
						registerEntry(endIronMatConfig.getRawItem(), TEGMatLibUtil.getItemFromKey("raw_" + endIronMatConfig.getRawBefore(), modID), entries);
						registerEntry(endIronMatConfig.getNugget(), TEGMatLibUtil.getItemFromKey(endIronMatConfig.getRawBefore() + "_nugget", modID), entries);
					}
				}
			}
		}
		if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
			for (MaterialConfiguration m : materials) {
				registerEntry(m.getBaseBlock(), m.getBlockBefore(), entries);
			}
		}
		if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
			for (MaterialConfiguration m : materials) {
				switch (m.getType()) {
					case IRON -> {
						IronTypeMaterialConfiguration ironMatConfig = (IronTypeMaterialConfiguration) m;
						registerEntry(ironMatConfig.getOre(), TEGMatLibUtil.getBlockFromKey("deepslate_" + ironMatConfig.getOreBefore() + "_ore", modID), entries);
						registerEntry(ironMatConfig.getDeepslateOre(), ironMatConfig.getOre(), entries);
						registerEntry(ironMatConfig.getRawBlock(), TEGMatLibUtil.getBlockFromKey("raw_" + ironMatConfig.getRawBefore() + "_block", modID), entries);
					}
					case DIAMOND -> {
						DiamondTypeMaterialConfiguration diamondMatConfig = (DiamondTypeMaterialConfiguration) m;
						registerEntry(diamondMatConfig.getOre(), TEGMatLibUtil.getBlockFromKey("deepslate_" + diamondMatConfig.getOreBefore() + "_ore", modID), entries);
						registerEntry(diamondMatConfig.getDeepslateOre(), diamondMatConfig.getOre(), entries);
					}
					case CUBIC_ZIRCONIA -> {
						CubicZirconiaTypeMaterialConfiguration cubicMatConfig = (CubicZirconiaTypeMaterialConfiguration) m;
						registerEntry(cubicMatConfig.getOre(), TEGMatLibUtil.getBlockFromKey("deepslate_" + cubicMatConfig.getOreBefore() + "_ore", modID), entries);
						registerEntry(cubicMatConfig.getDeepslateOre(), cubicMatConfig.getOre(), entries);
						registerEntry(cubicMatConfig.getRawBlock(), TEGMatLibUtil.getBlockFromKey("raw_" + cubicMatConfig.getRawBefore() + "_block", modID), entries);
					}
					case NETHER_DIAMOND -> {
						NetherDiamondTypeMaterialConfiguration netherDiamondMatConfig = (NetherDiamondTypeMaterialConfiguration) m;
						registerEntry(netherDiamondMatConfig.getNetherOre(), TEGMatLibUtil.getBlockFromKey("nether_" + netherDiamondMatConfig.getOreBefore() + "_ore", modID), entries);
					}
					case END_DIAMOND -> {
						EndDiamondTypeMaterialConfiguration endDiamondMatConfig = (EndDiamondTypeMaterialConfiguration) m;
						registerEntry(endDiamondMatConfig.getEndOre(), TEGMatLibUtil.getBlockFromKey(endDiamondMatConfig.getOreBefore(), modID), entries);
					}
					case END_IRON -> {
						EndIronTypeMaterialConfiguration endIronMatConfig = (EndIronTypeMaterialConfiguration) m;
						registerEntry(endIronMatConfig.getEndOre(), TEGMatLibUtil.getBlockFromKey(endIronMatConfig.getOreBefore(), modID), entries);
						registerEntry(endIronMatConfig.getRawBlock(), TEGMatLibUtil.getBlockFromKey("raw_" + endIronMatConfig.getRawBefore() + "_block", modID), entries);
					}
					case SAND_DIAMOND -> {
						SandDiamondTypeMaterialConfiguration sandDiamondTypeMatConfig = (SandDiamondTypeMaterialConfiguration) m;
						registerEntry(sandDiamondTypeMatConfig.getSandOre(), TEGMatLibUtil.getBlockFromKey(sandDiamondTypeMatConfig.getOreBefore(), modID), entries);
						registerEntry(sandDiamondTypeMatConfig.getGravelOre(), sandDiamondTypeMatConfig.getSandOre(), entries);
					}
				}
			}
		}
		if (event.getTabKey() == CreativeModeTabs.COMBAT) {
			for (MaterialConfiguration m : materials) {
				registerEntry(TEGMatLibUtil.getItemFromKey(m.getBaseName() + "_sword", modID), TEGMatLibUtil.getItemFromKey(m.getToolsBefore() + "_sword", modID), entries);
				registerEntry(TEGMatLibUtil.getItemFromKey(m.getBaseName() + "_axe", modID), TEGMatLibUtil.getItemFromKey(m.getToolsBefore() + "_axe", modID), entries);
				registerEntry(TEGMatLibUtil.getItemFromKey(m.getBaseName() + "_spear", modID), TEGMatLibUtil.getItemFromKey(m.getToolsBefore() + "_spear", modID), entries);
				registerEntry(TEGMatLibUtil.getItemFromKey(m.getBaseName() + "_helmet", modID), TEGMatLibUtil.getItemFromKey(m.getArmorBefore() + "_boots", modID), entries);
				registerEntry(TEGMatLibUtil.getItemFromKey(m.getBaseName() + "_chestplate", modID), TEGMatLibUtil.getItemFromKey(m.getBaseName() + "_helmet", modID), entries);
				registerEntry(TEGMatLibUtil.getItemFromKey(m.getBaseName() + "_leggings", modID), TEGMatLibUtil.getItemFromKey(m.getBaseName() + "_chestplate", modID), entries);
				registerEntry(TEGMatLibUtil.getItemFromKey(m.getBaseName() + "_boots", modID), TEGMatLibUtil.getItemFromKey(m.getBaseName() + "_leggings", modID), entries);
				if (m.getHorseArmor().isUsing()) {
					registerEntry(m.getHorseArmor().get().get().get(), TEGMatLibUtil.getItemFromKey(m.getAnimalArmorBefore() + "_horse_armor", materialHolder.getModID()), entries);
				}
				if (m.getNautilusArmor().isUsing()) {
					if (Objects.equals(m.getAnimalArmorBefore(), "leather")) {
						event.insertBefore(new ItemStack(Items.COPPER_NAUTILUS_ARMOR, 1), new ItemStack(m.getNautilusArmor().get().get().asItem()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
					} else {
						registerEntry(m.getNautilusArmor().get().get().get(), TEGMatLibUtil.getItemFromKey(m.getAnimalArmorBefore() + "_nautilus_armor", materialHolder.getModID()), entries);
					}
				}
			}
		}
		if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
			for (MaterialConfiguration m : materials) {
				registerEntry(TEGMatLibUtil.getItemFromKey(m.getBaseName() + "_axe", modID), TEGMatLibUtil.getItemFromKey(m.getToolsBefore() + "_hoe", modID), entries);
				registerEntry(TEGMatLibUtil.getItemFromKey(m.getBaseName() + "_pickaxe", modID), TEGMatLibUtil.getItemFromKey(m.getBaseName() + "_axe", modID), entries);
				registerEntry(TEGMatLibUtil.getItemFromKey(m.getBaseName() + "_shovel", modID), TEGMatLibUtil.getItemFromKey(m.getBaseName() + "_pickaxe", modID), entries);
				registerEntry(TEGMatLibUtil.getItemFromKey(m.getBaseName() + "_hoe", modID), TEGMatLibUtil.getItemFromKey(m.getBaseName() + "_shovel", modID), entries);
			}
		}

		runEntries(entries, event);

	}

	public record Entry(Item toAdd, Item reference) { }

	public static void addEntry(Entry toAdd, BuildCreativeModeTabContentsEvent event) {
		TEGMatLibUtil.inventoryAddAfter(toAdd.toAdd, toAdd.reference, event);
	}

	public static void registerEntry(ItemLike toAdd, ItemLike reference, List<Entry> list) {
		list.add(new Entry(toAdd.asItem(), reference.asItem()));
	}
	
	public static void runEntries(List<Entry> list, BuildCreativeModeTabContentsEvent event) {
		ArrayList<Entry> finalList = new ArrayList<>();
		ArrayList<Entry> remaining = new ArrayList<>(list);
		Predicate<Identifier> goes = identifier -> identifier.getNamespace().equals("minecraft") || finalList.stream().map(entry -> entry.toAdd.builtInRegistryHolder().key().identifier()).toList().contains(identifier);
		int tries = 0;
		while (!remaining.isEmpty()) {
			ArrayList<Entry> toRemove = new ArrayList<>();
			for (Entry e : remaining) {
				if (goes.test(e.reference.builtInRegistryHolder().key().identifier())) {
					finalList.add(e);
					toRemove.add(e);
				}
			}
			remaining.removeAll(toRemove);
			tries++;
			if (toRemove.isEmpty()) {
				throw new IllegalStateException("Infinite chain of reference determined during automatic inventory sorting after " + tries + " tries");
			}
		}
		LOG.info("Completed sorting inventory entries in {} tries", tries);
		for (Entry e : finalList) {
			addEntry(e, event);
		}
	}

}
