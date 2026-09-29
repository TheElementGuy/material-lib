package com.github.theelementguy.tegmatlib.data;

import com.github.theelementguy.tegmatlib.core.*;
import com.mojang.logging.LogUtils;
import net.minecraft.client.color.item.Dye;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.SelectItemModel;
import net.minecraft.client.renderer.item.properties.select.TrimMaterialProperty;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.minecraft.world.item.equipment.trim.TrimMaterial;
import net.minecraft.world.item.equipment.trim.TrimMaterials;
import com.github.theelementguy.tegmatlib.core.*;
import net.neoforged.neoforge.client.model.item.TrimmedArmorModel;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static net.minecraft.client.data.models.ItemModelGenerators.*;

public class TEGMatLibModelProvider extends ModelProvider {

	private final Logger LOG = LogUtils.getLogger();

	public static final Identifier TRIM_PREFIX_HELMET = prefixForSlotTrim("helmet");
	public static final Identifier TRIM_PREFIX_CHESTPLATE = prefixForSlotTrim("chestplate");
	public static final Identifier TRIM_PREFIX_LEGGINGS = prefixForSlotTrim("leggings");
	public static final Identifier TRIM_PREFIX_BOOTS = prefixForSlotTrim("boots");

	protected Supplier<List<MaterialConfiguration>> MATERIALS;

	protected String MOD_ID;

	public TEGMatLibModelProvider(GatherDataEvent.Client event, FullyConfiguredMaterialHolder materials) {
		super(event.getGenerator().getPackOutput(), materials.getModID());
		this.MATERIALS = materials::getMaterials;
		this.MOD_ID = modId;
	}

	@Override
	protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {

		LOG.info("Generating models for mod {}", modId);

		for (MaterialConfiguration config : MATERIALS.get()) {

			itemModels.generateFlatItem(config.getBaseItem(), ModelTemplates.FLAT_ITEM);
			itemModels.generateFlatItem(config.getSword(), ModelTemplates.FLAT_HANDHELD_ITEM);
			itemModels.generateFlatItem(config.getAxe(), ModelTemplates.FLAT_HANDHELD_ITEM);
			itemModels.generateFlatItem(config.getPickaxe(), ModelTemplates.FLAT_HANDHELD_ITEM);
			itemModels.generateFlatItem(config.getShovel(), ModelTemplates.FLAT_HANDHELD_ITEM);
			itemModels.generateFlatItem(config.getHoe(), ModelTemplates.FLAT_HANDHELD_ITEM);
			itemModels.generateSpear(config.getSpear());

			if (config.getHorseArmor().isUsing()) {
				itemModels.generateFlatItem(config.getHorseArmor().get().get().asItem(), ModelTemplates.FLAT_ITEM);
			}
			if (config.getNautilusArmor().isUsing()) {
				itemModels.generateFlatItem(config.getNautilusArmor().get().get().get(), ModelTemplates.FLAT_ITEM);
			}

			trimmable(itemModels, config.getHelmet(), MOD_ID + ":" + config.getBaseName(), true);
			trimmable(itemModels, config.getChestplate(), MOD_ID + ":" + config.getBaseName(), true);
			trimmable(itemModels, config.getLeggings(), MOD_ID + ":" + config.getBaseName(), true);
			trimmable(itemModels, config.getBoots(), MOD_ID + ":" + config.getBaseName(), true);

			blockModels.createTrivialBlock(config.getBaseBlock(), translate(config.applyException(config.getBaseName() + "_block", ModelExceptionValues.CUBE)));

			switch (config.getType()) {
				case IRON -> {
					IronTypeMaterialConfiguration ironMatConfig = (IronTypeMaterialConfiguration) config;

					itemModels.generateFlatItem(ironMatConfig.getRawItem(), ModelTemplates.FLAT_ITEM);
					itemModels.generateFlatItem(ironMatConfig.getNugget(), ModelTemplates.FLAT_ITEM);

					blockModels.createTrivialCube(ironMatConfig.getRawBlock());
					blockModels.createTrivialCube(ironMatConfig.getOre());
					blockModels.createTrivialCube(ironMatConfig.getDeepslateOre());
				}
				case DIAMOND -> {
					DiamondTypeMaterialConfiguration diamondMatConfig = (DiamondTypeMaterialConfiguration) config;
					blockModels.createTrivialCube(diamondMatConfig.getOre());
					blockModels.createTrivialCube(diamondMatConfig.getDeepslateOre());
				}
				case CUBIC_ZIRCONIA -> {
					CubicZirconiaTypeMaterialConfiguration cubicMatConfig = (CubicZirconiaTypeMaterialConfiguration) config;

					itemModels.generateFlatItem(cubicMatConfig.getRawItem(), ModelTemplates.FLAT_ITEM);

					blockModels.createTrivialCube(cubicMatConfig.getRawBlock());
					blockModels.createTrivialCube(cubicMatConfig.getOre());
					blockModels.createTrivialCube(cubicMatConfig.getDeepslateOre());
				}
				case NETHER_DIAMOND -> {
					NetherDiamondTypeMaterialConfiguration netherDiamondMatConfig = (NetherDiamondTypeMaterialConfiguration) config;
					blockModels.createTrivialCube(netherDiamondMatConfig.getNetherOre());
				}
				case END_DIAMOND -> {
					EndDiamondTypeMaterialConfiguration endDiamondMatConfig = (EndDiamondTypeMaterialConfiguration) config;
					blockModels.createTrivialCube(endDiamondMatConfig.getEndOre());
				}
				case END_IRON -> {
					EndIronTypeMaterialConfiguration ironMatConfig = (EndIronTypeMaterialConfiguration) config;

					itemModels.generateFlatItem(ironMatConfig.getRawItem(), ModelTemplates.FLAT_ITEM);
					itemModels.generateFlatItem(ironMatConfig.getNugget(), ModelTemplates.FLAT_ITEM);

					blockModels.createTrivialCube(ironMatConfig.getRawBlock());
					blockModels.createTrivialCube(ironMatConfig.getEndOre());
				}
				case SAND_DIAMOND -> {
					SandDiamondTypeMaterialConfiguration sandDiamondMatConfig = (SandDiamondTypeMaterialConfiguration) config;
					blockModels.createTrivialCube(sandDiamondMatConfig.getSandOre());
					blockModels.createTrivialCube(sandDiamondMatConfig.getGravelOre());
				}
			}

		}

	}

	private TexturedModel.Provider translate(ModelExceptionValues value) {
		return switch (value) {
			case CUBE -> TexturedModel.CUBE;
			case CUBE_TOP -> TexturedModel.CUBE_TOP;
			case CUBE_TOP_BOTTOM -> TexturedModel.CUBE_BOTTOM_TOP;
			case COLUMN -> TexturedModel.COLUMN;
		};
	}

	private void trimmable(ItemModelGenerators itemModels, Item trimmable, String palette, boolean replace) {
		String trimmablePath = BuiltInRegistries.ITEM.getKey(trimmable).getPath();
		Identifier prefix;
		if (trimmablePath.contains("helmet")) {
			prefix = TRIM_PREFIX_HELMET;
		} else if (trimmablePath.contains("chestplate")) {
			prefix = TRIM_PREFIX_CHESTPLATE;
		} else if (trimmablePath.contains("leggings")) {
			prefix = TRIM_PREFIX_LEGGINGS;
		} else if (trimmablePath.contains("boots")) {
			prefix = TRIM_PREFIX_BOOTS;
		} else {
			prefix = TRIM_PREFIX_HELMET;
			LOG.warn("Could not find proper trim prefix for: {}", trimmablePath);
		}
		if (replace) {
			itemModels.generateDynamicTrimmableItem(trimmable, prefix, new TrimmedArmorModel.PaletteTransform(Identifier.bySeparator(palette, ':'), Identifier.bySeparator(palette + "_darker", ':')));
		} else {
			itemModels.generateDynamicTrimmableItem(trimmable, prefix, null);
		}
		ModelTemplates.FLAT_ITEM.create(trimmable, TextureMapping.layer0(trimmable), itemModels.modelOutput);
	}
}
