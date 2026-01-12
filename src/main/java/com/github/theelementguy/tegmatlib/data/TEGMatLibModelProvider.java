package com.github.theelementguy.tegmatlib.data;

import com.github.theelementguy.tegmatlib.core.*;
import net.minecraft.client.color.item.Dye;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.SelectItemModel;
import net.minecraft.client.renderer.item.properties.select.TrimMaterialProperty;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.minecraft.world.item.equipment.trim.MaterialAssetGroup;
import net.minecraft.world.item.equipment.trim.TrimMaterial;
import net.minecraft.world.item.equipment.trim.TrimMaterials;
import com.github.theelementguy.tegmatlib.core.*;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static net.minecraft.client.data.models.ItemModelGenerators.*;

public class TEGMatLibModelProvider extends ModelProvider {

	protected Supplier<List<MaterialConfiguration>> MATERIALS;

	public final List<ItemModelGenerators.TrimMaterialData> TRIM_MATERIAL_MODELS = new ArrayList<>(List.of(new ItemModelGenerators.TrimMaterialData(MaterialAssetGroup.QUARTZ, TrimMaterials.QUARTZ), new ItemModelGenerators.TrimMaterialData(MaterialAssetGroup.IRON, TrimMaterials.IRON), new ItemModelGenerators.TrimMaterialData(MaterialAssetGroup.NETHERITE, TrimMaterials.NETHERITE), new ItemModelGenerators.TrimMaterialData(MaterialAssetGroup.REDSTONE, TrimMaterials.REDSTONE), new ItemModelGenerators.TrimMaterialData(MaterialAssetGroup.COPPER, TrimMaterials.COPPER), new ItemModelGenerators.TrimMaterialData(MaterialAssetGroup.GOLD, TrimMaterials.GOLD), new ItemModelGenerators.TrimMaterialData(MaterialAssetGroup.EMERALD, TrimMaterials.EMERALD), new ItemModelGenerators.TrimMaterialData(MaterialAssetGroup.DIAMOND, TrimMaterials.DIAMOND), new ItemModelGenerators.TrimMaterialData(MaterialAssetGroup.LAPIS, TrimMaterials.LAPIS), new ItemModelGenerators.TrimMaterialData(MaterialAssetGroup.AMETHYST, TrimMaterials.AMETHYST), new ItemModelGenerators.TrimMaterialData(MaterialAssetGroup.RESIN, TrimMaterials.RESIN)));

	protected String MOD_ID;

	public TEGMatLibModelProvider(GatherDataEvent.Client event, FullyConfiguredMaterialHolder materials) {
		super(event.getGenerator().getPackOutput(), materials.getModID());
		this.MATERIALS = materials::getMaterials;
		this.MOD_ID = modId;
		ArrayList<ItemModelGenerators.TrimMaterialData> trimMaterialsToAdd = new ArrayList<>();
		for (MaterialConfiguration config : MATERIALS.get()) {
			MaterialConfiguration concrete;
			trimMaterialsToAdd.add(new ItemModelGenerators.TrimMaterialData(config.getMaterialAssetGroup(), config.getTrimMaterial()));
		}
		TRIM_MATERIAL_MODELS.addAll(trimMaterialsToAdd);
	}

	@Override
	protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {

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
				itemModels.generateFlatItem(config.getHorseArmor().get().get().get(), ModelTemplates.FLAT_ITEM);
			}

			generateTrimmableItemWithModdedMaterials(itemModels, config.getHelmet(), config.getEquipmentAsset(), false);
			generateTrimmableItemWithModdedMaterials(itemModels, config.getChestplate(), config.getEquipmentAsset(), false);
			generateTrimmableItemWithModdedMaterials(itemModels, config.getLeggings(), config.getEquipmentAsset(), false);
			generateTrimmableItemWithModdedMaterials(itemModels, config.getBoots(), config.getEquipmentAsset(), false);

			blockModels.createTrivialCube(config.getBaseBlock());

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
			}

		}

		generateTrimmableItemWithModdedMaterials(itemModels, Items.LEATHER_HELMET, EquipmentAssets.LEATHER, true);
		generateTrimmableItemWithModdedMaterials(itemModels, Items.LEATHER_CHESTPLATE, EquipmentAssets.LEATHER, true);
		generateTrimmableItemWithModdedMaterials(itemModels, Items.LEATHER_LEGGINGS, EquipmentAssets.LEATHER, true);
		generateTrimmableItemWithModdedMaterials(itemModels, Items.LEATHER_BOOTS, EquipmentAssets.LEATHER, true);


		generateTrimmableItemWithModdedMaterials(itemModels, Items.CHAINMAIL_HELMET, EquipmentAssets.CHAINMAIL, false);
		generateTrimmableItemWithModdedMaterials(itemModels, Items.CHAINMAIL_CHESTPLATE, EquipmentAssets.CHAINMAIL, false);
		generateTrimmableItemWithModdedMaterials(itemModels, Items.CHAINMAIL_LEGGINGS, EquipmentAssets.CHAINMAIL, false);
		generateTrimmableItemWithModdedMaterials(itemModels, Items.CHAINMAIL_BOOTS, EquipmentAssets.CHAINMAIL, false);


		generateTrimmableItemWithModdedMaterials(itemModels, Items.IRON_HELMET, EquipmentAssets.IRON, false);
		generateTrimmableItemWithModdedMaterials(itemModels, Items.IRON_CHESTPLATE, EquipmentAssets.IRON, false);
		generateTrimmableItemWithModdedMaterials(itemModels, Items.IRON_LEGGINGS, EquipmentAssets.IRON, false);
		generateTrimmableItemWithModdedMaterials(itemModels, Items.IRON_BOOTS, EquipmentAssets.IRON, false);


		generateTrimmableItemWithModdedMaterials(itemModels, Items.DIAMOND_HELMET, EquipmentAssets.DIAMOND, false);
		generateTrimmableItemWithModdedMaterials(itemModels, Items.DIAMOND_CHESTPLATE, EquipmentAssets.DIAMOND, false);
		generateTrimmableItemWithModdedMaterials(itemModels, Items.DIAMOND_LEGGINGS, EquipmentAssets.DIAMOND, false);
		generateTrimmableItemWithModdedMaterials(itemModels, Items.DIAMOND_BOOTS, EquipmentAssets.DIAMOND, false);
		generateTrimmableItemWithModdedMaterials(itemModels, Items.GOLDEN_HELMET, EquipmentAssets.GOLD, false);
		generateTrimmableItemWithModdedMaterials(itemModels, Items.GOLDEN_CHESTPLATE, EquipmentAssets.GOLD, false);
		generateTrimmableItemWithModdedMaterials(itemModels, Items.GOLDEN_LEGGINGS, EquipmentAssets.GOLD, false);
		generateTrimmableItemWithModdedMaterials(itemModels, Items.GOLDEN_BOOTS, EquipmentAssets.GOLD, false);
		generateTrimmableItemWithModdedMaterials(itemModels, Items.NETHERITE_HELMET, EquipmentAssets.NETHERITE, false);
		generateTrimmableItemWithModdedMaterials(itemModels, Items.NETHERITE_CHESTPLATE, EquipmentAssets.NETHERITE, false);
		generateTrimmableItemWithModdedMaterials(itemModels, Items.NETHERITE_LEGGINGS, EquipmentAssets.NETHERITE, false);
		generateTrimmableItemWithModdedMaterials(itemModels, Items.NETHERITE_BOOTS, EquipmentAssets.NETHERITE, false);

		generateTrimmableItemWithModdedMaterials(itemModels, Items.TURTLE_HELMET, EquipmentAssets.TURTLE_SCUTE, false);

		generateTrimmableItemWithModdedMaterials(itemModels, Items.COPPER_HELMET, EquipmentAssets.COPPER, false);
		generateTrimmableItemWithModdedMaterials(itemModels, Items.COPPER_CHESTPLATE, EquipmentAssets.COPPER, false);
		generateTrimmableItemWithModdedMaterials(itemModels, Items.COPPER_LEGGINGS, EquipmentAssets.COPPER, false);
		generateTrimmableItemWithModdedMaterials(itemModels, Items.COPPER_BOOTS, EquipmentAssets.COPPER, false);

	}

	public void generateTrimmableItemWithModdedMaterials(ItemModelGenerators itemModels, Item item, ResourceKey<EquipmentAsset> equipmentAsset, boolean usesSecondLayer) {
		Identifier Identifier = ModelLocationUtils.getModelLocation(item);
		Identifier Identifier1 = TextureMapping.getItemTexture(item);
		Identifier Identifier2 = TextureMapping.getItemTexture(item, "_overlay");
		List<SelectItemModel.SwitchCase<ResourceKey<TrimMaterial>>> list = new ArrayList(TRIM_MATERIAL_MODELS.size());

		for(ItemModelGenerators.TrimMaterialData itemmodelgenerators$trimmaterialdata : TRIM_MATERIAL_MODELS) {
			Identifier Identifier3 = Identifier.withSuffix("_" + itemmodelgenerators$trimmaterialdata.assets().base().suffix() + "_trim");
			String var10001 = itemmodelgenerators$trimmaterialdata.assets().assetId(equipmentAsset).suffix();
			String path = item.getDescriptionId();
			Identifier modelId = (path.contains("helmet")) ? TRIM_PREFIX_HELMET : ((path.contains("chestplate")) ? TRIM_PREFIX_CHESTPLATE : ((path.contains("leggings")) ? TRIM_PREFIX_LEGGINGS : ItemModelGenerators.TRIM_PREFIX_BOOTS));
			System.out.println(path);
			Identifier Identifier4 = modelId.withSuffix("_" + var10001);
			ItemModel.Unbaked itemmodel$unbaked;
			if (usesSecondLayer) {
				itemModels.generateLayeredItem(Identifier3, Identifier1, Identifier2, Identifier4);
				itemmodel$unbaked = ItemModelUtils.tintedModel(Identifier3, new ItemTintSource[]{new Dye(-6265536)});
			} else {
				itemModels.generateLayeredItem(Identifier3, Identifier1, Identifier4);
				itemmodel$unbaked = ItemModelUtils.plainModel(Identifier3);
			}

			list.add(ItemModelUtils.when(itemmodelgenerators$trimmaterialdata.materialKey(), itemmodel$unbaked));
		}

		ItemModel.Unbaked itemmodel$unbaked1;
		if (usesSecondLayer) {
			ModelTemplates.TWO_LAYERED_ITEM.create(Identifier, TextureMapping.layered(Identifier1, Identifier2), itemModels.modelOutput);
			itemmodel$unbaked1 = ItemModelUtils.tintedModel(Identifier, new ItemTintSource[]{new Dye(-6265536)});
		} else {
			ModelTemplates.FLAT_ITEM.create(Identifier, TextureMapping.layer0(Identifier1), itemModels.modelOutput);
			itemmodel$unbaked1 = ItemModelUtils.plainModel(Identifier);
		}

		itemModels.itemModelOutput.accept(item, ItemModelUtils.select(new TrimMaterialProperty(), itemmodel$unbaked1, list));
	}
}
