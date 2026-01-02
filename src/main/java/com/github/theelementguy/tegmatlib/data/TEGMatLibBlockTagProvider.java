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

	private Supplier<List<MaterialConfiguration>> MATERIALS;

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
		//TODO: investigate proper tags getbase, getraw, getore, getdore
		tag(Tags.Blocks.STORAGE_BLOCKS).add(material.getBaseBlock());
		MineabilityTier tier = (material.getMineabilityLevel() == MineabilityTier.DEFAULT) ? TEGMatLibUtil.getMineability(material.getMiningLevel()) : material.getMineabilityLevel();
		switch (material.getType()) {
			case IRON -> {
				IronTypeMaterialConfiguration mat = (IronTypeMaterialConfiguration) material;
				if (tier == MineabilityTier.ALL) {
					tag(BlockTags.MINEABLE_WITH_PICKAXE).add(mat.getBaseBlock(), mat.getRawBlock(), mat.getOre(), mat.getDeepslateOre());
					tag(mat.getIncorrectForMaterial()).addTag(BlockTags.INCORRECT_FOR_WOODEN_TOOL);
					tag(mat.getNeedsMaterial()).addTag(Tags.Blocks.NEEDS_WOOD_TOOL);
				} else if (tier == MineabilityTier.NETHERITE) {
					tag(BlockTags.MINEABLE_WITH_PICKAXE).add(mat.getBaseBlock(), mat.getRawBlock(), mat.getOre(), mat.getDeepslateOre());
					tag(Tags.Blocks.NEEDS_NETHERITE_TOOL).add(mat.getBaseBlock(), mat.getRawBlock(), mat.getOre(), mat.getDeepslateOre());
					tag(mat.getNeedsMaterial()).addTag(Tags.Blocks.NEEDS_NETHERITE_TOOL);
					tag(mat.getIncorrectForMaterial()).addTag(BlockTags.INCORRECT_FOR_NETHERITE_TOOL).remove(mat.getNeedsMaterial());
				} else {
					tag(BlockTags.MINEABLE_WITH_PICKAXE).add(mat.getBaseBlock(), mat.getRawBlock(), mat.getOre(), mat.getDeepslateOre());
					tag(TEGMatLibUtil.getNeedsTagForMineability(tier)).add(mat.getBaseBlock(), mat.getRawBlock(), mat.getOre(), mat.getDeepslateOre());
					tag(mat.getIncorrectForMaterial()).addTag(TEGMatLibUtil.getTagForTierIncorrect(mat.getMiningLevel()));
					tag(mat.getNeedsMaterial()).addTag(TEGMatLibUtil.getTagForTierNeeds(mat.getMiningLevel()));
				}
				if (mat.isSingleOre()) {
					tag(Tags.Blocks.ORE_RATES_SINGULAR).add(mat.getOre(), mat.getDeepslateOre());
				} else {
					tag(Tags.Blocks.ORE_RATES_DENSE).add(mat.getOre(), mat.getDeepslateOre());
				}
				tag(Tags.Blocks.ORES_IN_GROUND_STONE).add(mat.getOre());
				tag(Tags.Blocks.ORES_IN_GROUND_DEEPSLATE).add(mat.getDeepslateOre());
			}
			case DIAMOND -> {
				DiamondTypeMaterialConfiguration mat = (DiamondTypeMaterialConfiguration) material;
				if (tier == MineabilityTier.ALL) {
					tag(BlockTags.MINEABLE_WITH_PICKAXE).add(mat.getBaseBlock(), mat.getOre(), mat.getDeepslateOre());
					tag(mat.getIncorrectForMaterial()).addTag(BlockTags.INCORRECT_FOR_WOODEN_TOOL);
					tag(mat.getNeedsMaterial()).addTag(Tags.Blocks.NEEDS_WOOD_TOOL);
				} else if (tier == MineabilityTier.NETHERITE) {
					tag(BlockTags.MINEABLE_WITH_PICKAXE).add(mat.getBaseBlock(), mat.getOre(), mat.getDeepslateOre());
					tag(Tags.Blocks.NEEDS_NETHERITE_TOOL).add(mat.getBaseBlock(), mat.getOre(), mat.getDeepslateOre());
					tag(mat.getNeedsMaterial()).addTag(Tags.Blocks.NEEDS_NETHERITE_TOOL);
					tag(mat.getIncorrectForMaterial()).addTag(BlockTags.INCORRECT_FOR_NETHERITE_TOOL).remove(mat.getNeedsMaterial());
				} else {
					tag(BlockTags.MINEABLE_WITH_PICKAXE).add(mat.getBaseBlock(), mat.getOre(), mat.getDeepslateOre());
					tag(TEGMatLibUtil.getNeedsTagForMineability(tier)).add(mat.getBaseBlock(), mat.getOre(), mat.getDeepslateOre());
					tag(mat.getIncorrectForMaterial()).addTag(TEGMatLibUtil.getTagForTierIncorrect(mat.getMiningLevel()));
					tag(mat.getNeedsMaterial()).addTag(TEGMatLibUtil.getTagForTierNeeds(mat.getMiningLevel()));
				}
				if (mat.isSingleOre()) {
					tag(Tags.Blocks.ORE_RATES_SINGULAR).add(mat.getOre(), mat.getDeepslateOre());
				} else {
					tag(Tags.Blocks.ORE_RATES_DENSE).add(mat.getOre(), mat.getDeepslateOre());
				}
				tag(Tags.Blocks.ORES_IN_GROUND_STONE).add(mat.getOre());
				tag(Tags.Blocks.ORES_IN_GROUND_DEEPSLATE).add(mat.getDeepslateOre());
			}
			case CUBIC_ZIRCONIA -> {
				CubicZirconiaTypeMaterialConfiguration mat = (CubicZirconiaTypeMaterialConfiguration) material;
				if (tier == MineabilityTier.ALL) {
					tag(BlockTags.MINEABLE_WITH_PICKAXE).add(mat.getBaseBlock(), mat.getRawBlock(), mat.getOre(), mat.getDeepslateOre());
					tag(mat.getIncorrectForMaterial()).addTag(BlockTags.INCORRECT_FOR_WOODEN_TOOL);
					tag(mat.getNeedsMaterial()).addTag(Tags.Blocks.NEEDS_WOOD_TOOL);
				} else if (tier == MineabilityTier.NETHERITE) {
					tag(BlockTags.MINEABLE_WITH_PICKAXE).add(mat.getBaseBlock(), mat.getRawBlock(), mat.getOre(), mat.getDeepslateOre());
					tag(Tags.Blocks.NEEDS_NETHERITE_TOOL).add(mat.getBaseBlock(), mat.getRawBlock(), mat.getOre(), mat.getDeepslateOre());
					tag(mat.getNeedsMaterial()).addTag(Tags.Blocks.NEEDS_NETHERITE_TOOL);
					tag(mat.getIncorrectForMaterial()).addTag(BlockTags.INCORRECT_FOR_NETHERITE_TOOL).remove(mat.getNeedsMaterial());
				} else {
					tag(BlockTags.MINEABLE_WITH_PICKAXE).add(mat.getBaseBlock(), mat.getRawBlock(), mat.getOre(), mat.getDeepslateOre());
					tag(TEGMatLibUtil.getNeedsTagForMineability(tier)).add(mat.getBaseBlock(), mat.getRawBlock(), mat.getOre(), mat.getDeepslateOre());
					tag(mat.getIncorrectForMaterial()).addTag(TEGMatLibUtil.getTagForTierIncorrect(mat.getMiningLevel()));
					tag(mat.getNeedsMaterial()).addTag(TEGMatLibUtil.getTagForTierNeeds(mat.getMiningLevel()));
				}
				if (mat.isSingleOre()) {
					tag(Tags.Blocks.ORE_RATES_SINGULAR).add(mat.getOre(), mat.getDeepslateOre());
				} else {
					tag(Tags.Blocks.ORE_RATES_DENSE).add(mat.getOre(), mat.getDeepslateOre());
				}
				tag(Tags.Blocks.ORES_IN_GROUND_STONE).add(mat.getOre());
				tag(Tags.Blocks.ORES_IN_GROUND_DEEPSLATE).add(mat.getDeepslateOre());
			}
			case NETHER_DIAMOND -> {
				NetherDiamondTypeMaterialConfiguration mat = (NetherDiamondTypeMaterialConfiguration) material;
				if (tier == MineabilityTier.ALL) {
					tag(BlockTags.MINEABLE_WITH_PICKAXE).add(mat.getBaseBlock(), mat.getNetherOre());
					tag(mat.getIncorrectForMaterial()).addTag(BlockTags.INCORRECT_FOR_WOODEN_TOOL);
					tag(mat.getNeedsMaterial()).addTag(Tags.Blocks.NEEDS_WOOD_TOOL);
				} else if (tier == MineabilityTier.NETHERITE) {
					tag(BlockTags.MINEABLE_WITH_PICKAXE).add(mat.getBaseBlock(), mat.getNetherOre());
					tag(Tags.Blocks.NEEDS_NETHERITE_TOOL).add(mat.getBaseBlock(), mat.getNetherOre());
					tag(mat.getNeedsMaterial()).addTag(Tags.Blocks.NEEDS_NETHERITE_TOOL);
					tag(mat.getIncorrectForMaterial()).addTag(BlockTags.INCORRECT_FOR_NETHERITE_TOOL).remove(mat.getNeedsMaterial());
				} else {
					tag(BlockTags.MINEABLE_WITH_PICKAXE).add(mat.getBaseBlock(), mat.getNetherOre());
					tag(TEGMatLibUtil.getNeedsTagForMineability(tier)).add(mat.getBaseBlock(), mat.getNetherOre());
					tag(mat.getIncorrectForMaterial()).addTag(TEGMatLibUtil.getTagForTierIncorrect(mat.getMiningLevel()));
					tag(mat.getNeedsMaterial()).addTag(TEGMatLibUtil.getTagForTierNeeds(mat.getMiningLevel()));
				}
				if (mat.isSingleOre()) {
					tag(Tags.Blocks.ORE_RATES_SINGULAR).add(mat.getNetherOre());
				} else {
					tag(Tags.Blocks.ORE_RATES_DENSE).add(mat.getNetherOre());
				}
				tag(Tags.Blocks.ORES_IN_GROUND_NETHERRACK).add(mat.getNetherOre());
			}
			case END_DIAMOND -> {
				EndDiamondTypeMaterialConfiguration mat = (EndDiamondTypeMaterialConfiguration) material;
				if (tier == MineabilityTier.ALL) {
					tag(BlockTags.MINEABLE_WITH_PICKAXE).add(mat.getBaseBlock(), mat.getEndOre());
					tag(mat.getIncorrectForMaterial()).addTag(BlockTags.INCORRECT_FOR_WOODEN_TOOL);
					tag(mat.getNeedsMaterial()).addTag(Tags.Blocks.NEEDS_WOOD_TOOL);
				} else if (tier == MineabilityTier.NETHERITE) {
					tag(BlockTags.MINEABLE_WITH_PICKAXE).add(mat.getBaseBlock(), mat.getEndOre());
					tag(Tags.Blocks.NEEDS_NETHERITE_TOOL).add(mat.getBaseBlock(), mat.getEndOre());
					tag(mat.getNeedsMaterial()).addTag(Tags.Blocks.NEEDS_NETHERITE_TOOL);
					tag(mat.getIncorrectForMaterial()).addTag(BlockTags.INCORRECT_FOR_NETHERITE_TOOL).remove(mat.getNeedsMaterial());
				} else {
					tag(BlockTags.MINEABLE_WITH_PICKAXE).add(mat.getBaseBlock(), mat.getEndOre());
					tag(TEGMatLibUtil.getNeedsTagForMineability(tier)).add(mat.getBaseBlock(), mat.getEndOre());
					tag(mat.getIncorrectForMaterial()).addTag(TEGMatLibUtil.getTagForTierIncorrect(mat.getMiningLevel()));
					tag(mat.getNeedsMaterial()).addTag(TEGMatLibUtil.getTagForTierNeeds(mat.getMiningLevel()));
				}
				if (mat.isSingleOre()) {
					tag(Tags.Blocks.ORE_RATES_SINGULAR).add(mat.getEndOre());
				} else {
					tag(Tags.Blocks.ORE_RATES_DENSE).add(mat.getEndOre());
				}
			}
		}

	}

}
