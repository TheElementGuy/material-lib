package com.github.theelementguy.tegmatlib.data;

import com.github.theelementguy.tegmatlib.core.*;
import com.github.theelementguy.tegmatlib.util.TEGMatLibUtil;
import com.mojang.logging.LogUtils;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.ItemTags;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ItemTagsProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.slf4j.Logger;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class TEGMatLibItemTagProvider extends ItemTagsProvider {

	private final Logger LOG = LogUtils.getLogger();

	protected final Supplier<List<MaterialConfiguration>> MATERIALS;

	private final String MOD_ID;

	public TEGMatLibItemTagProvider(GatherDataEvent.Client event, FullyConfiguredMaterialHolder materials) {
		super(event.getGenerator().getPackOutput(), event.getLookupProvider(), materials.getModID());
		MATERIALS = materials::getMaterials;
		MOD_ID = materials.getModID();
	}

	@Override
	protected void addTags(HolderLookup.Provider provider) {

		LOG.info("Adding item tags for mod {}", MOD_ID);

		for (MaterialConfiguration config : MATERIALS.get()) {
			tag(ItemTags.TRIM_MATERIALS).add(TEGMatLibUtil.getResourceKeyFromItem(config.getBaseItem()));
			tag(ItemTags.SWORDS).add(TEGMatLibUtil.getResourceKeyFromItem(config.getSword()));
			tag(ItemTags.AXES).add(TEGMatLibUtil.getResourceKeyFromItem(config.getAxe()));
			tag(ItemTags.PICKAXES).add(TEGMatLibUtil.getResourceKeyFromItem(config.getPickaxe()));
			tag(ItemTags.SHOVELS).add(TEGMatLibUtil.getResourceKeyFromItem(config.getShovel()));
			tag(ItemTags.HOES).add(TEGMatLibUtil.getResourceKeyFromItem(config.getHoe()));
			tag(ItemTags.SPEARS).add(TEGMatLibUtil.getResourceKeyFromItem(config.getSpear()));
			tag(config.getRepairables()).add(TEGMatLibUtil.getResourceKeyFromItem(config.getBaseItem()));
			tag(ItemTags.HEAD_ARMOR).add(TEGMatLibUtil.getResourceKeyFromItem(config.getHelmet()));
			tag(ItemTags.CHEST_ARMOR).add(TEGMatLibUtil.getResourceKeyFromItem(config.getChestplate()));
			tag(ItemTags.LEG_ARMOR).add(TEGMatLibUtil.getResourceKeyFromItem(config.getLeggings()));
			tag(ItemTags.FOOT_ARMOR).add(TEGMatLibUtil.getResourceKeyFromItem(config.getBoots()));
			tag(Tags.Items.MELEE_WEAPON_TOOLS).add(TEGMatLibUtil.getResourceKeyFromItem(config.getSword()), TEGMatLibUtil.getResourceKeyFromItem(config.getAxe()), TEGMatLibUtil.getResourceKeyFromItem(config.getSpear()));
			tag(Tags.Items.MINING_TOOL_TOOLS).add(TEGMatLibUtil.getResourceKeyFromItem(config.getPickaxe()));
			if (config.getHorseArmor().isUsing()) {
				tag(Tags.Items.ARMORS_HORSE).add(TEGMatLibUtil.getResourceKeyFromItem(config.getHorseArmor().get().get().asItem()));
			}
			if (config.getNautilusArmor().isUsing()) {
				tag(Tags.Items.ARMORS_NAUTILUS).add(TEGMatLibUtil.getResourceKeyFromItem(config.getNautilusArmor().get().get().asItem()));
			}
			switch (config.getType()) {
				case DIAMOND, NETHER_DIAMOND, END_DIAMOND, SAND_DIAMOND -> {
					tag(Tags.Items.GEMS).add(TEGMatLibUtil.getResourceKeyFromItem(config.getBaseItem()));
				}
				case IRON -> {
					IronTypeMaterialConfiguration mat = (IronTypeMaterialConfiguration) config;
					tag(Tags.Items.INGOTS).add(TEGMatLibUtil.getResourceKeyFromItem(mat.getBaseItem()));
					tag(Tags.Items.RAW_MATERIALS).add(TEGMatLibUtil.getResourceKeyFromItem(mat.getRawItem()));
					tag(Tags.Items.NUGGETS).add(TEGMatLibUtil.getResourceKeyFromItem(mat.getNugget()));
				}
				case CUBIC_ZIRCONIA -> {
					CubicZirconiaTypeMaterialConfiguration mat = (CubicZirconiaTypeMaterialConfiguration) config;
					tag(Tags.Items.GEMS).add(TEGMatLibUtil.getResourceKeyFromItem(mat.getBaseItem()));
					tag(Tags.Items.RAW_MATERIALS).add(TEGMatLibUtil.getResourceKeyFromItem(mat.getRawItem()));
				}
				case END_IRON -> {
					EndIronTypeMaterialConfiguration mat = (EndIronTypeMaterialConfiguration) config;
					tag(Tags.Items.INGOTS).add(TEGMatLibUtil.getResourceKeyFromItem(mat.getBaseItem()));
					tag(Tags.Items.RAW_MATERIALS).add(TEGMatLibUtil.getResourceKeyFromItem(mat.getRawItem()));
					tag(Tags.Items.NUGGETS).add(TEGMatLibUtil.getResourceKeyFromItem(mat.getNugget()));
				}
			}
		}

	}
}
