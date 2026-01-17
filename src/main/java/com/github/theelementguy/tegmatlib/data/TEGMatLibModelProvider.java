package com.github.theelementguy.tegmatlib.data;

import com.github.theelementguy.tegmatlib.core.*;
import net.minecraft.client.color.item.Dye;
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
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.minecraft.world.item.equipment.trim.TrimMaterial;
import net.minecraft.world.item.equipment.trim.TrimMaterials;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static net.minecraft.client.data.models.ItemModelGenerators.*;

public class TEGMatLibModelProvider extends ModelProvider {

	protected Supplier<List<MaterialConfiguration>> MATERIALS;

	public static List<ItemModelGenerators.TrimMaterialData> TRIM_MATERIAL_MODELS_WITH_MODDED = new ArrayList<>(List.of(new ItemModelGenerators.TrimMaterialData("quartz", TrimMaterials.QUARTZ, Map.of()), new ItemModelGenerators.TrimMaterialData("iron", TrimMaterials.IRON, Map.of(EquipmentAssets.IRON, "iron_darker")), new ItemModelGenerators.TrimMaterialData("netherite", TrimMaterials.NETHERITE, Map.of(EquipmentAssets.NETHERITE, "netherite_darker")), new ItemModelGenerators.TrimMaterialData("redstone", TrimMaterials.REDSTONE, Map.of()), new ItemModelGenerators.TrimMaterialData("copper", TrimMaterials.COPPER, Map.of()), new ItemModelGenerators.TrimMaterialData("gold", TrimMaterials.GOLD, Map.of(EquipmentAssets.GOLD, "gold_darker")), new ItemModelGenerators.TrimMaterialData("emerald", TrimMaterials.EMERALD, Map.of()), new ItemModelGenerators.TrimMaterialData("diamond", TrimMaterials.DIAMOND, Map.of(EquipmentAssets.DIAMOND, "diamond_darker")), new ItemModelGenerators.TrimMaterialData("lapis", TrimMaterials.LAPIS, Map.of()), new ItemModelGenerators.TrimMaterialData("amethyst", TrimMaterials.AMETHYST, Map.of()), new ItemModelGenerators.TrimMaterialData("resin", TrimMaterials.RESIN, Map.of())));


	protected String MOD_ID;

	public TEGMatLibModelProvider(GatherDataEvent.Client event, FullyConfiguredMaterialHolder materials) {
		super(event.getGenerator().getPackOutput(), materials.getModID());
		this.MATERIALS = materials::getMaterials;
		this.MOD_ID = modId;
		ArrayList<ItemModelGenerators.TrimMaterialData> trimMaterialsToAdd = new ArrayList<>();
		for (MaterialConfiguration config : MATERIALS.get()) {
			MaterialConfiguration concrete;
			trimMaterialsToAdd.add(new ItemModelGenerators.TrimMaterialData(config.getBaseName(), config.getTrimMaterial(), Map.of()));
		}
		TRIM_MATERIAL_MODELS_WITH_MODDED.addAll(trimMaterialsToAdd);
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

			if (config.getHorseArmor().isUsing()) {
				itemModels.generateFlatItem(config.getHorseArmor().get().get().asItem(), ModelTemplates.FLAT_ITEM);
			}

			generateTrimmableItemWithModdedMaterials(config.getHelmet(), config.getEquipmentAsset(), config.getBaseName(), false, itemModels);
			generateTrimmableItemWithModdedMaterials(config.getChestplate(), config.getEquipmentAsset(), config.getBaseName(), false, itemModels);
			generateTrimmableItemWithModdedMaterials(config.getLeggings(), config.getEquipmentAsset(), config.getBaseName(), false, itemModels);
			generateTrimmableItemWithModdedMaterials(config.getBoots(), config.getEquipmentAsset(), config.getBaseName(), false, itemModels);

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

		generateTrimmableItemWithModdedMaterials(Items.LEATHER_HELMET, EquipmentAssets.LEATHER, "helmet", true, itemModels);
		generateTrimmableItemWithModdedMaterials(Items.LEATHER_CHESTPLATE, EquipmentAssets.LEATHER, "chestplate", true, itemModels);
		generateTrimmableItemWithModdedMaterials(Items.LEATHER_LEGGINGS, EquipmentAssets.LEATHER, "leggings", true, itemModels);
		generateTrimmableItemWithModdedMaterials(Items.LEATHER_BOOTS, EquipmentAssets.LEATHER, "boots", true, itemModels);


		generateTrimmableItemWithModdedMaterials(Items.CHAINMAIL_HELMET, EquipmentAssets.CHAINMAIL, "helmet", false, itemModels);
		generateTrimmableItemWithModdedMaterials(Items.CHAINMAIL_CHESTPLATE, EquipmentAssets.CHAINMAIL, "chestplate", false, itemModels);
		generateTrimmableItemWithModdedMaterials(Items.CHAINMAIL_LEGGINGS, EquipmentAssets.CHAINMAIL, "leggings", false, itemModels);
		generateTrimmableItemWithModdedMaterials(Items.CHAINMAIL_BOOTS, EquipmentAssets.CHAINMAIL, "boots", false, itemModels);


		generateTrimmableItemWithModdedMaterials(Items.IRON_HELMET, EquipmentAssets.IRON, "helmet", false, itemModels);
		generateTrimmableItemWithModdedMaterials(Items.IRON_CHESTPLATE, EquipmentAssets.IRON, "chestplate", false, itemModels);
		generateTrimmableItemWithModdedMaterials(Items.IRON_LEGGINGS, EquipmentAssets.IRON, "leggings", false, itemModels);
		generateTrimmableItemWithModdedMaterials(Items.IRON_BOOTS, EquipmentAssets.IRON, "boots", false, itemModels);


		generateTrimmableItemWithModdedMaterials(Items.DIAMOND_HELMET, EquipmentAssets.DIAMOND, "helmet", false, itemModels);
		generateTrimmableItemWithModdedMaterials(Items.DIAMOND_CHESTPLATE, EquipmentAssets.DIAMOND, "chestplate", false, itemModels);
		generateTrimmableItemWithModdedMaterials(Items.DIAMOND_LEGGINGS, EquipmentAssets.DIAMOND, "leggings", false, itemModels);
		generateTrimmableItemWithModdedMaterials(Items.DIAMOND_BOOTS, EquipmentAssets.DIAMOND, "boots", false, itemModels);
		generateTrimmableItemWithModdedMaterials(Items.GOLDEN_HELMET, EquipmentAssets.GOLD, "helmet", false, itemModels);
		generateTrimmableItemWithModdedMaterials(Items.GOLDEN_CHESTPLATE, EquipmentAssets.GOLD, "chestplate", false, itemModels);
		generateTrimmableItemWithModdedMaterials(Items.GOLDEN_LEGGINGS, EquipmentAssets.GOLD, "leggings", false, itemModels);
		generateTrimmableItemWithModdedMaterials(Items.GOLDEN_BOOTS, EquipmentAssets.GOLD, "boots", false, itemModels);
		generateTrimmableItemWithModdedMaterials(Items.NETHERITE_HELMET, EquipmentAssets.NETHERITE, "helmet", false, itemModels);
		generateTrimmableItemWithModdedMaterials(Items.NETHERITE_CHESTPLATE, EquipmentAssets.NETHERITE, "chestplate", false, itemModels);
		generateTrimmableItemWithModdedMaterials(Items.NETHERITE_LEGGINGS, EquipmentAssets.NETHERITE, "leggings", false, itemModels);
		generateTrimmableItemWithModdedMaterials(Items.NETHERITE_BOOTS, EquipmentAssets.NETHERITE, "boots", false, itemModels);

	}

	public void generateTrimmableItemWithModdedMaterials(Item item, ResourceKey<EquipmentAsset> key, String name, boolean dyeable, ItemModelGenerators gens) {
		ResourceLocation resourcelocation = ModelLocationUtils.getModelLocation(item);
		ResourceLocation resourcelocation1 = TextureMapping.getItemTexture(item);
		ResourceLocation resourcelocation2 = TextureMapping.getItemTexture(item, "_overlay");
		List<SelectItemModel.SwitchCase<ResourceKey<TrimMaterial>>> list = new ArrayList<>(TRIM_MATERIAL_MODELS_WITH_MODDED.size());

		for (ItemModelGenerators.TrimMaterialData itemmodelgenerators$trimmaterialdata : TRIM_MATERIAL_MODELS_WITH_MODDED) {
			ResourceLocation resourcelocation3 = resourcelocation.withSuffix("_" + itemmodelgenerators$trimmaterialdata.name() + "_trim");
			ResourceLocation resourcelocation4 = ResourceLocation.fromNamespaceAndPath(
					"minecraft", "trims/items/" + name + "_trim_" + itemmodelgenerators$trimmaterialdata.textureName(key)
			);
			ItemModel.Unbaked itemmodel$unbaked;
			if (dyeable) {
				gens.generateLayeredItem(resourcelocation3, resourcelocation1, resourcelocation2, resourcelocation4);
				itemmodel$unbaked = ItemModelUtils.tintedModel(resourcelocation3, new Dye(-6265536));
			} else {
				gens.generateLayeredItem(resourcelocation3, resourcelocation1, resourcelocation4);
				itemmodel$unbaked = ItemModelUtils.plainModel(resourcelocation3);
			}

			list.add(ItemModelUtils.when(itemmodelgenerators$trimmaterialdata.materialKey(), itemmodel$unbaked));

			System.out.println(resourcelocation3);
		}

		ItemModel.Unbaked itemmodel$unbaked1;
		if (dyeable) {
			ModelTemplates.TWO_LAYERED_ITEM.create(resourcelocation, TextureMapping.layered(resourcelocation1, resourcelocation2), gens.modelOutput);
			itemmodel$unbaked1 = ItemModelUtils.tintedModel(resourcelocation, new Dye(-6265536));
		} else {
			ModelTemplates.FLAT_ITEM.create(resourcelocation, TextureMapping.layer0(resourcelocation1), gens.modelOutput);
			itemmodel$unbaked1 = ItemModelUtils.plainModel(resourcelocation);
		}

		gens.itemModelOutput.accept(item, ItemModelUtils.select(new TrimMaterialProperty(), itemmodel$unbaked1, list));
	}

	public static ResourceLocation prefixForSlotTrimModded(String name) {
		return ResourceLocation.fromNamespaceAndPath("minecraft", "trims/items/" + name + "_trim");
	}
}
