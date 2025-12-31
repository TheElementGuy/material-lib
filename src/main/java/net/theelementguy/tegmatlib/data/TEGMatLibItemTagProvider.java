package net.theelementguy.tegmatlib.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.ItemTags;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ItemTagsProvider;
import net.theelementguy.tegmatlib.core.CubicZirconiaTypeMaterialConfiguration;
import net.theelementguy.tegmatlib.core.IronTypeMaterialConfiguration;
import net.theelementguy.tegmatlib.core.MaterialConfiguration;
import net.theelementguy.tegmatlib.core.MaterialType;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class TEGMatLibItemTagProvider extends ItemTagsProvider {

	protected final Supplier<List<MaterialConfiguration>> MATERIALS;

	public TEGMatLibItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId, Supplier<List<MaterialConfiguration>> materials) {
		super(output, lookupProvider, modId);
		MATERIALS = materials;
	}

	@Override
	protected void addTags(HolderLookup.Provider provider) {

		for (MaterialConfiguration config : MATERIALS.get()) {
			tag(ItemTags.SWORDS).add(config.getSword());
			tag(ItemTags.AXES).add(config.getAxe());
			tag(ItemTags.PICKAXES).add(config.getPickaxe());
			tag(ItemTags.SHOVELS).add(config.getShovel());
			tag(ItemTags.HOES).add(config.getHoe());
			tag(config.getRepairables()).add(config.getBaseItem());
			tag(ItemTags.HEAD_ARMOR).add(config.getHelmet());
			tag(ItemTags.CHEST_ARMOR).add(config.getChestplate());
			tag(ItemTags.LEG_ARMOR).add(config.getLeggings());
			tag(ItemTags.FOOT_ARMOR).add(config.getBoots());
			tag(Tags.Items.MELEE_WEAPON_TOOLS).add(config.getSword(), config.getAxe());
			tag(Tags.Items.MINING_TOOL_TOOLS).add(config.getPickaxe());
			switch (config.getType()) {
				case DIAMOND -> {
					tag(Tags.Items.GEMS).add(config.getBaseItem());
				}
				case IRON -> {
					IronTypeMaterialConfiguration mat = (IronTypeMaterialConfiguration) config;
					tag(Tags.Items.INGOTS).add(mat.getBaseItem());
					tag(Tags.Items.RAW_MATERIALS).add(mat.getRawItem());
				}
				case CUBIC_ZIRCONIA -> {
					CubicZirconiaTypeMaterialConfiguration mat = (CubicZirconiaTypeMaterialConfiguration) config;
					tag(Tags.Items.GEMS).add(mat.getBaseItem());
					tag(Tags.Items.RAW_MATERIALS).add(mat.getRawItem());
				}
			}
		}

	}
}
