package net.theelementguy.tegmatlib.util;

import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.theelementguy.tegmatlib.core.IronTypeMaterialConfiguration;
import net.theelementguy.tegmatlib.core.MaterialConfiguration;

import java.util.List;

public class TEGMatLibCreativeModeTabFiller {

	/**
	 * Automatically fills the inventory in creative mode.
	 * @param materials A list of MaterialConfigurations, <b>in order of how they would appear relatively in the inventory</b>.
	 * @param event The BuildCreativeModeTabContentsEvent from the addCreative method.
	 * @param modID The mod ID of the mod in question.
	 */
	public static void build(List<MaterialConfiguration> materials, BuildCreativeModeTabContentsEvent event, String modID) {
		if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
			for (MaterialConfiguration m : materials) {
				TEGMatLibUtil.inventoryAddAfter(m.getBaseItem(), m.getItemBefore(), event);
				switch (m.getType()) {
					case IRON -> {
						IronTypeMaterialConfiguration ironMatConfig = (IronTypeMaterialConfiguration) m;
						TEGMatLibUtil.inventoryAddAfter(ironMatConfig.getRawItem(), TEGMatLibUtil.getItemFromKey("raw_" + ironMatConfig.getRawBefore(), modID), event);
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
						TEGMatLibUtil.inventoryAddAfter(ironMatConfig.getOre(), TEGMatLibUtil.getBlockFromKey(ironMatConfig.getOreBefore() + "_ore", modID), event);
						TEGMatLibUtil.inventoryAddAfter(ironMatConfig.getDeepslateOre(), TEGMatLibUtil.getBlockFromKey("deepslate_" + ironMatConfig.getOreBefore() + "_ore", modID), event);
						TEGMatLibUtil.inventoryAddAfter(ironMatConfig.getRawBlock(), TEGMatLibUtil.getBlockFromKey("raw_" + ironMatConfig.getRawBefore() + "_block", modID), event);
					}
				}
			}
		}
		if (event.getTabKey() == CreativeModeTabs.COMBAT) {
			for (MaterialConfiguration m : materials) {
				TEGMatLibUtil.setAddAfter(m.getBaseName(), m.getSetBefore(), event, modID);
			}
		}
		if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
			for (MaterialConfiguration m : materials) {
				TEGMatLibUtil.toolsAddAfter(m.getBaseName(), m.getSetBefore(), event, modID);
			}
		}

	}

}
