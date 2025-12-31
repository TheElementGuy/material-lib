package net.theelementguy.tegmatlib.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.theelementguy.tegmatlib.core.DiamondTypeMaterialConfiguration;
import net.theelementguy.tegmatlib.core.IronTypeMaterialConfiguration;
import net.theelementguy.tegmatlib.core.MaterialConfiguration;
import net.theelementguy.tegmatlib.core.tiers.MiningTier;
import net.theelementguy.tegmatlib.util.TEGMatLibUtil;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class TEGMatLibBlockTagProvider extends BlockTagsProvider {

	private Supplier<List<MaterialConfiguration>> MATERIALS;

	public TEGMatLibBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId, Supplier<List<MaterialConfiguration>> materials) {
		super(output, lookupProvider, modId);
		MATERIALS = materials;
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
		switch (material.getType()) {
			case IRON -> {
				IronTypeMaterialConfiguration mat = (IronTypeMaterialConfiguration) material;
				if (mat.getMiningLevel() != MiningTier.WOOD) {
					tag(BlockTags.MINEABLE_WITH_PICKAXE).add(mat.getBaseBlock(), mat.getRawBlock(), mat.getOre(), mat.getDeepslateOre());
					tag(TEGMatLibUtil.getTagForTierUnder(mat.getMiningLevel())).add(mat.getBaseBlock(), mat.getRawBlock(), mat.getOre(), mat.getDeepslateOre());
				}
				if (mat.getMiningLevel() != MiningTier.BEYOND_NETHERITE) {
					tag(mat.getIncorrectForMaterial()).addTag(TEGMatLibUtil.getTagForTierIncorrect(mat.getMiningLevel()));
					tag(mat.getNeedsMaterial()).addTag(TEGMatLibUtil.getTagForTierNeeds(mat.getMiningLevel()));
				} else {
					tag(mat.getNeedsMaterial()).addTag(Tags.Blocks.NEEDS_NETHERITE_TOOL);
					tag(mat.getIncorrectForMaterial()).addTag(BlockTags.INCORRECT_FOR_NETHERITE_TOOL).remove(mat.getNeedsMaterial());
				}
				if (mat.isSingleOre()) {
					tag(Tags.Blocks.ORE_RATES_SINGULAR).add(mat.getOre(), mat.getDeepslateOre());
				} else {
					tag(Tags.Blocks.ORE_RATES_DENSE).add(mat.getOre(), mat.getDeepslateOre());
				}
				tag(Tags.Blocks.ORES_IN_GROUND_STONE).add(mat.getOre());
				tag(Tags.Blocks.ORE_BEARING_GROUND_DEEPSLATE).add(mat.getDeepslateOre());
			}
			case DIAMOND -> {
				DiamondTypeMaterialConfiguration mat = (DiamondTypeMaterialConfiguration) material;
				if (mat.getMiningLevel() != MiningTier.WOOD) {
					tag(BlockTags.MINEABLE_WITH_PICKAXE).add(mat.getBaseBlock(), mat.getOre(), mat.getDeepslateOre());
					tag(TEGMatLibUtil.getTagForTierUnder(mat.getMiningLevel())).add(mat.getBaseBlock(), mat.getOre(), mat.getDeepslateOre());
				}
				if (mat.getMiningLevel() != MiningTier.BEYOND_NETHERITE) {
					tag(mat.getIncorrectForMaterial()).addTag(TEGMatLibUtil.getTagForTierIncorrect(mat.getMiningLevel()));
					tag(mat.getNeedsMaterial()).addTag(TEGMatLibUtil.getTagForTierNeeds(mat.getMiningLevel()));
				} else {
					tag(mat.getNeedsMaterial()).addTag(Tags.Blocks.NEEDS_NETHERITE_TOOL);
					tag(mat.getIncorrectForMaterial()).addTag(BlockTags.INCORRECT_FOR_NETHERITE_TOOL).remove(mat.getNeedsMaterial());
				}
				if (mat.isSingleOre()) {
					tag(Tags.Blocks.ORE_RATES_SINGULAR).add(mat.getOre(), mat.getDeepslateOre());
				} else {
					tag(Tags.Blocks.ORE_RATES_DENSE).add(mat.getOre(), mat.getDeepslateOre());
				}
				tag(Tags.Blocks.ORES_IN_GROUND_STONE).add(mat.getOre());
				tag(Tags.Blocks.ORE_BEARING_GROUND_DEEPSLATE).add(mat.getDeepslateOre());
			}
		}

	}

}
