package com.github.theelementguy.tegmatlib.data;

import com.github.theelementguy.tegmatlib.core.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import com.github.theelementguy.tegmatlib.core.*;
import com.github.theelementguy.tegmatlib.core.tiers.MineabilityTier;
import com.github.theelementguy.tegmatlib.util.TEGMatLibUtil;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class TEGMatLibBlockTagProvider extends BlockTagsProvider {

	private final Supplier<List<MaterialConfiguration>> MATERIALS;

	public TEGMatLibBlockTagProvider(GatherDataEvent.Client event, FullyConfiguredMaterialHolder materials) {
		super(event.getGenerator().getPackOutput(), event.getLookupProvider(), materials.getModID());
		MATERIALS = materials::getMaterials;
	}

	@Override
	protected void addTags(HolderLookup.Provider provider) {

		for (MaterialConfiguration config : MATERIALS.get()) {
			addMaterial(config);
		}

	}

	protected void addMaterial(MaterialConfiguration material) {
		tag(Tags.Blocks.STORAGE_BLOCKS).add(TEGMatLibUtil.getResourceKeyFromBlock(material.getBaseBlock()));
		MineabilityTier tier = (material.getMineabilityLevel() == MineabilityTier.DEFAULT) ? TEGMatLibUtil.getMineability(material.getMiningLevel()) : material.getMineabilityLevel();
		switch (material.getType()) {
			case IRON -> {
				IronTypeMaterialConfiguration mat = (IronTypeMaterialConfiguration) material;
				if (tier == MineabilityTier.ALL) {
					tag(BlockTags.MINEABLE_WITH_PICKAXE).add(TEGMatLibUtil.getResourceKeyFromBlock(mat.getBaseBlock()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getRawBlock()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getOre()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getDeepslateOre()));
					tag(mat.getIncorrectForMaterial()).addTag(BlockTags.INCORRECT_FOR_WOODEN_TOOL);
					tag(mat.getNeedsMaterial()).addTag(Tags.Blocks.NEEDS_WOOD_TOOL);
				} else if (tier == MineabilityTier.NETHERITE) {
					tag(BlockTags.MINEABLE_WITH_PICKAXE).add(TEGMatLibUtil.getResourceKeyFromBlock(mat.getBaseBlock()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getRawBlock()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getOre()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getDeepslateOre()));
					tag(Tags.Blocks.NEEDS_NETHERITE_TOOL).add(TEGMatLibUtil.getResourceKeyFromBlock(mat.getBaseBlock()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getRawBlock()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getOre()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getDeepslateOre()));
					tag(mat.getNeedsMaterial()).addTag(Tags.Blocks.NEEDS_NETHERITE_TOOL);
					tag(mat.getIncorrectForMaterial()).addTag(BlockTags.INCORRECT_FOR_NETHERITE_TOOL).remove(mat.getNeedsMaterial());
				} else {
					tag(BlockTags.MINEABLE_WITH_PICKAXE).add(TEGMatLibUtil.getResourceKeyFromBlock(mat.getBaseBlock()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getRawBlock()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getOre()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getDeepslateOre()));
					tag(TEGMatLibUtil.getNeedsTagForMineability(tier)).add(TEGMatLibUtil.getResourceKeyFromBlock(mat.getBaseBlock()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getRawBlock()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getOre()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getDeepslateOre()));
					tag(mat.getIncorrectForMaterial()).addTag(TEGMatLibUtil.getTagForTierIncorrect(mat.getMiningLevel()));
					tag(mat.getNeedsMaterial()).addTag(TEGMatLibUtil.getTagForTierNeeds(mat.getMiningLevel()));
				}
				if (mat.isSingleOre()) {
					tag(Tags.Blocks.ORE_RATES_SINGULAR).add(TEGMatLibUtil.getResourceKeyFromBlock(mat.getOre()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getDeepslateOre()));
				} else {
					tag(Tags.Blocks.ORE_RATES_DENSE).add(TEGMatLibUtil.getResourceKeyFromBlock(mat.getOre()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getDeepslateOre()));
				}
				tag(Tags.Blocks.ORES_IN_GROUND_STONE).add(TEGMatLibUtil.getResourceKeyFromBlock(mat.getOre()));
				tag(Tags.Blocks.ORES_IN_GROUND_DEEPSLATE).add(TEGMatLibUtil.getResourceKeyFromBlock(mat.getDeepslateOre()));
			}
			case DIAMOND -> {
				DiamondTypeMaterialConfiguration mat = (DiamondTypeMaterialConfiguration) material;
				if (tier == MineabilityTier.ALL) {
					tag(BlockTags.MINEABLE_WITH_PICKAXE).add(TEGMatLibUtil.getResourceKeyFromBlock(mat.getBaseBlock()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getOre()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getDeepslateOre()));
					tag(mat.getIncorrectForMaterial()).addTag(BlockTags.INCORRECT_FOR_WOODEN_TOOL);
					tag(mat.getNeedsMaterial()).addTag(Tags.Blocks.NEEDS_WOOD_TOOL);
				} else if (tier == MineabilityTier.NETHERITE) {
					tag(BlockTags.MINEABLE_WITH_PICKAXE).add(TEGMatLibUtil.getResourceKeyFromBlock(mat.getBaseBlock()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getOre()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getDeepslateOre()));
					tag(Tags.Blocks.NEEDS_NETHERITE_TOOL).add(TEGMatLibUtil.getResourceKeyFromBlock(mat.getBaseBlock()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getOre()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getDeepslateOre()));
					tag(mat.getNeedsMaterial()).addTag(Tags.Blocks.NEEDS_NETHERITE_TOOL);
					tag(mat.getIncorrectForMaterial()).addTag(BlockTags.INCORRECT_FOR_NETHERITE_TOOL).remove(mat.getNeedsMaterial());
				} else {
					tag(BlockTags.MINEABLE_WITH_PICKAXE).add(TEGMatLibUtil.getResourceKeyFromBlock(mat.getBaseBlock()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getOre()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getDeepslateOre()));
					tag(TEGMatLibUtil.getNeedsTagForMineability(tier)).add(TEGMatLibUtil.getResourceKeyFromBlock(mat.getBaseBlock()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getOre()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getDeepslateOre()));
					tag(mat.getIncorrectForMaterial()).addTag(TEGMatLibUtil.getTagForTierIncorrect(mat.getMiningLevel()));
					tag(mat.getNeedsMaterial()).addTag(TEGMatLibUtil.getTagForTierNeeds(mat.getMiningLevel()));
				}
				if (mat.isSingleOre()) {
					tag(Tags.Blocks.ORE_RATES_SINGULAR).add(TEGMatLibUtil.getResourceKeyFromBlock(mat.getOre()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getDeepslateOre()));
				} else {
					tag(Tags.Blocks.ORE_RATES_DENSE).add(TEGMatLibUtil.getResourceKeyFromBlock(mat.getOre()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getDeepslateOre()));
				}
				tag(Tags.Blocks.ORES_IN_GROUND_STONE).add(TEGMatLibUtil.getResourceKeyFromBlock(mat.getOre()));
				tag(Tags.Blocks.ORES_IN_GROUND_DEEPSLATE).add(TEGMatLibUtil.getResourceKeyFromBlock(mat.getDeepslateOre()));
			}
			case CUBIC_ZIRCONIA -> {
				CubicZirconiaTypeMaterialConfiguration mat = (CubicZirconiaTypeMaterialConfiguration) material;
				if (tier == MineabilityTier.ALL) {
					tag(BlockTags.MINEABLE_WITH_PICKAXE).add(TEGMatLibUtil.getResourceKeyFromBlock(mat.getBaseBlock()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getRawBlock()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getOre()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getDeepslateOre()));
					tag(mat.getIncorrectForMaterial()).addTag(BlockTags.INCORRECT_FOR_WOODEN_TOOL);
					tag(mat.getNeedsMaterial()).addTag(Tags.Blocks.NEEDS_WOOD_TOOL);
				} else if (tier == MineabilityTier.NETHERITE) {
					tag(BlockTags.MINEABLE_WITH_PICKAXE).add(TEGMatLibUtil.getResourceKeyFromBlock(mat.getBaseBlock()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getRawBlock()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getOre()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getDeepslateOre()));
					tag(Tags.Blocks.NEEDS_NETHERITE_TOOL).add(TEGMatLibUtil.getResourceKeyFromBlock(mat.getBaseBlock()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getRawBlock()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getOre()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getDeepslateOre()));
					tag(mat.getNeedsMaterial()).addTag(Tags.Blocks.NEEDS_NETHERITE_TOOL);
					tag(mat.getIncorrectForMaterial()).addTag(BlockTags.INCORRECT_FOR_NETHERITE_TOOL).remove(mat.getNeedsMaterial());
				} else {
					tag(BlockTags.MINEABLE_WITH_PICKAXE).add(TEGMatLibUtil.getResourceKeyFromBlock(mat.getBaseBlock()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getRawBlock()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getOre()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getDeepslateOre()));
					tag(TEGMatLibUtil.getNeedsTagForMineability(tier)).add(TEGMatLibUtil.getResourceKeyFromBlock(mat.getBaseBlock()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getRawBlock()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getOre()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getDeepslateOre()));
					tag(mat.getIncorrectForMaterial()).addTag(TEGMatLibUtil.getTagForTierIncorrect(mat.getMiningLevel()));
					tag(mat.getNeedsMaterial()).addTag(TEGMatLibUtil.getTagForTierNeeds(mat.getMiningLevel()));
				}
				if (mat.isSingleOre()) {
					tag(Tags.Blocks.ORE_RATES_SINGULAR).add(TEGMatLibUtil.getResourceKeyFromBlock(mat.getOre()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getDeepslateOre()));
				} else {
					tag(Tags.Blocks.ORE_RATES_DENSE).add(TEGMatLibUtil.getResourceKeyFromBlock(mat.getOre()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getDeepslateOre()));
				}
				tag(Tags.Blocks.ORES_IN_GROUND_STONE).add(TEGMatLibUtil.getResourceKeyFromBlock(mat.getOre()));
				tag(Tags.Blocks.ORES_IN_GROUND_DEEPSLATE).add(TEGMatLibUtil.getResourceKeyFromBlock(mat.getDeepslateOre()));
			}
			case NETHER_DIAMOND -> {
				NetherDiamondTypeMaterialConfiguration mat = (NetherDiamondTypeMaterialConfiguration) material;
				if (tier == MineabilityTier.ALL) {
					tag(BlockTags.MINEABLE_WITH_PICKAXE).add(TEGMatLibUtil.getResourceKeyFromBlock(mat.getBaseBlock()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getNetherOre()));
					tag(mat.getIncorrectForMaterial()).addTag(BlockTags.INCORRECT_FOR_WOODEN_TOOL);
					tag(mat.getNeedsMaterial()).addTag(Tags.Blocks.NEEDS_WOOD_TOOL);
				} else if (tier == MineabilityTier.NETHERITE) {
					tag(BlockTags.MINEABLE_WITH_PICKAXE).add(TEGMatLibUtil.getResourceKeyFromBlock(mat.getBaseBlock()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getNetherOre()));
					tag(Tags.Blocks.NEEDS_NETHERITE_TOOL).add(TEGMatLibUtil.getResourceKeyFromBlock(mat.getBaseBlock()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getNetherOre()));
					tag(mat.getNeedsMaterial()).addTag(Tags.Blocks.NEEDS_NETHERITE_TOOL);
					tag(mat.getIncorrectForMaterial()).addTag(BlockTags.INCORRECT_FOR_NETHERITE_TOOL).remove(mat.getNeedsMaterial());
				} else {
					tag(BlockTags.MINEABLE_WITH_PICKAXE).add(TEGMatLibUtil.getResourceKeyFromBlock(mat.getBaseBlock()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getNetherOre()));
					tag(TEGMatLibUtil.getNeedsTagForMineability(tier)).add(TEGMatLibUtil.getResourceKeyFromBlock(mat.getBaseBlock()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getNetherOre()));
					tag(mat.getIncorrectForMaterial()).addTag(TEGMatLibUtil.getTagForTierIncorrect(mat.getMiningLevel()));
					tag(mat.getNeedsMaterial()).addTag(TEGMatLibUtil.getTagForTierNeeds(mat.getMiningLevel()));
				}
				if (mat.isSingleOre()) {
					tag(Tags.Blocks.ORE_RATES_SINGULAR).add(TEGMatLibUtil.getResourceKeyFromBlock(mat.getNetherOre()));
				} else {
					tag(Tags.Blocks.ORE_RATES_DENSE).add(TEGMatLibUtil.getResourceKeyFromBlock(mat.getNetherOre()));
				}
				tag(Tags.Blocks.ORES_IN_GROUND_NETHERRACK).add(TEGMatLibUtil.getResourceKeyFromBlock(mat.getNetherOre()));
			}
			case END_DIAMOND -> {
				EndDiamondTypeMaterialConfiguration mat = (EndDiamondTypeMaterialConfiguration) material;
				if (tier == MineabilityTier.ALL) {
					tag(BlockTags.MINEABLE_WITH_PICKAXE).add(TEGMatLibUtil.getResourceKeyFromBlock(mat.getBaseBlock()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getEndOre()));
					tag(mat.getIncorrectForMaterial()).addTag(BlockTags.INCORRECT_FOR_WOODEN_TOOL);
					tag(mat.getNeedsMaterial()).addTag(Tags.Blocks.NEEDS_WOOD_TOOL);
				} else if (tier == MineabilityTier.NETHERITE) {
					tag(BlockTags.MINEABLE_WITH_PICKAXE).add(TEGMatLibUtil.getResourceKeyFromBlock(mat.getBaseBlock()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getEndOre()));
					tag(Tags.Blocks.NEEDS_NETHERITE_TOOL).add(TEGMatLibUtil.getResourceKeyFromBlock(mat.getBaseBlock()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getEndOre()));
					tag(mat.getNeedsMaterial()).addTag(Tags.Blocks.NEEDS_NETHERITE_TOOL);
					tag(mat.getIncorrectForMaterial()).addTag(BlockTags.INCORRECT_FOR_NETHERITE_TOOL).remove(mat.getNeedsMaterial());
				} else {
					tag(BlockTags.MINEABLE_WITH_PICKAXE).add(TEGMatLibUtil.getResourceKeyFromBlock(mat.getBaseBlock()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getEndOre()));
					tag(TEGMatLibUtil.getNeedsTagForMineability(tier)).add(TEGMatLibUtil.getResourceKeyFromBlock(mat.getBaseBlock()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getEndOre()));
					tag(mat.getIncorrectForMaterial()).addTag(TEGMatLibUtil.getTagForTierIncorrect(mat.getMiningLevel()));
					tag(mat.getNeedsMaterial()).addTag(TEGMatLibUtil.getTagForTierNeeds(mat.getMiningLevel()));
				}
				if (mat.isSingleOre()) {
					tag(Tags.Blocks.ORE_RATES_SINGULAR).add(TEGMatLibUtil.getResourceKeyFromBlock(mat.getEndOre()));
				} else {
					tag(Tags.Blocks.ORE_RATES_DENSE).add(TEGMatLibUtil.getResourceKeyFromBlock(mat.getEndOre()));
				}
			}
			case END_IRON -> {
				EndIronTypeMaterialConfiguration mat = (EndIronTypeMaterialConfiguration) material;
				if (tier == MineabilityTier.ALL) {
					tag(BlockTags.MINEABLE_WITH_PICKAXE).add(TEGMatLibUtil.getResourceKeyFromBlock(mat.getBaseBlock()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getRawBlock()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getEndOre()));
					tag(mat.getIncorrectForMaterial()).addTag(BlockTags.INCORRECT_FOR_WOODEN_TOOL);
					tag(mat.getNeedsMaterial()).addTag(Tags.Blocks.NEEDS_WOOD_TOOL);
				} else if (tier == MineabilityTier.NETHERITE) {
					tag(BlockTags.MINEABLE_WITH_PICKAXE).add(TEGMatLibUtil.getResourceKeyFromBlock(mat.getBaseBlock()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getRawBlock()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getEndOre()));
					tag(Tags.Blocks.NEEDS_NETHERITE_TOOL).add(TEGMatLibUtil.getResourceKeyFromBlock(mat.getBaseBlock()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getRawBlock()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getEndOre()));
					tag(mat.getNeedsMaterial()).addTag(Tags.Blocks.NEEDS_NETHERITE_TOOL);
					tag(mat.getIncorrectForMaterial()).addTag(BlockTags.INCORRECT_FOR_NETHERITE_TOOL).remove(mat.getNeedsMaterial());
				} else {
					tag(BlockTags.MINEABLE_WITH_PICKAXE).add(TEGMatLibUtil.getResourceKeyFromBlock(mat.getBaseBlock()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getRawBlock()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getEndOre()));
					tag(TEGMatLibUtil.getNeedsTagForMineability(tier)).add(TEGMatLibUtil.getResourceKeyFromBlock(mat.getBaseBlock()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getRawBlock()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getEndOre()));
					tag(mat.getIncorrectForMaterial()).addTag(TEGMatLibUtil.getTagForTierIncorrect(mat.getMiningLevel()));
					tag(mat.getNeedsMaterial()).addTag(TEGMatLibUtil.getTagForTierNeeds(mat.getMiningLevel()));
				}
				if (mat.isSingleOre()) {
					tag(Tags.Blocks.ORE_RATES_SINGULAR).add(TEGMatLibUtil.getResourceKeyFromBlock(mat.getEndOre()));
				} else {
					tag(Tags.Blocks.ORE_RATES_DENSE).add(TEGMatLibUtil.getResourceKeyFromBlock(mat.getEndOre()));
				}
			}
			case SAND_DIAMOND -> {
				SandDiamondTypeMaterialConfiguration mat = (SandDiamondTypeMaterialConfiguration) material;
				if (tier == MineabilityTier.ALL) {
					tag(BlockTags.MINEABLE_WITH_SHOVEL).add(TEGMatLibUtil.getResourceKeyFromBlock(mat.getBaseBlock()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getSandOre()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getGravelOre()));
					tag(mat.getIncorrectForMaterial()).addTag(BlockTags.INCORRECT_FOR_WOODEN_TOOL);
					tag(mat.getNeedsMaterial()).addTag(Tags.Blocks.NEEDS_WOOD_TOOL);
				} else if (tier == MineabilityTier.NETHERITE) {
					tag(BlockTags.MINEABLE_WITH_SHOVEL).add(TEGMatLibUtil.getResourceKeyFromBlock(mat.getBaseBlock()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getSandOre()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getGravelOre()));
					tag(Tags.Blocks.NEEDS_NETHERITE_TOOL).add(TEGMatLibUtil.getResourceKeyFromBlock(mat.getBaseBlock()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getSandOre()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getGravelOre()));
					tag(mat.getNeedsMaterial()).addTag(Tags.Blocks.NEEDS_NETHERITE_TOOL);
					tag(mat.getIncorrectForMaterial()).addTag(BlockTags.INCORRECT_FOR_NETHERITE_TOOL).remove(mat.getNeedsMaterial());
				} else {
					tag(BlockTags.MINEABLE_WITH_SHOVEL).add(TEGMatLibUtil.getResourceKeyFromBlock(mat.getBaseBlock()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getSandOre()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getGravelOre()));
					tag(TEGMatLibUtil.getNeedsTagForMineability(tier)).add(TEGMatLibUtil.getResourceKeyFromBlock(mat.getBaseBlock()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getSandOre()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getGravelOre()));
					tag(mat.getIncorrectForMaterial()).addTag(TEGMatLibUtil.getTagForTierIncorrect(mat.getMiningLevel()));
					tag(mat.getNeedsMaterial()).addTag(TEGMatLibUtil.getTagForTierNeeds(mat.getMiningLevel()));
				}
				if (mat.isSingleOre()) {
					tag(Tags.Blocks.ORE_RATES_SINGULAR).add(TEGMatLibUtil.getResourceKeyFromBlock(mat.getSandOre()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getGravelOre()));
				} else {
					tag(Tags.Blocks.ORE_RATES_DENSE).add(TEGMatLibUtil.getResourceKeyFromBlock(mat.getSandOre()), TEGMatLibUtil.getResourceKeyFromBlock(mat.getGravelOre()));
				}
			}
		}

	}

}
