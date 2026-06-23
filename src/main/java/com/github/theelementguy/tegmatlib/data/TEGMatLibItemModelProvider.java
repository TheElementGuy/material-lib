package com.github.theelementguy.tegmatlib.data;

import com.github.theelementguy.tegmatlib.core.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimMaterials;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Supplier;

public class TEGMatLibItemModelProvider extends ItemModelProvider {

	private final Supplier<List<MaterialConfiguration>> MATERIALS;
	private final String MOD_ID;

	private LinkedHashMap<ResourceKey<TrimMaterial>, Float> trimMaterials = new LinkedHashMap<>();

	public TEGMatLibItemModelProvider(GatherDataEvent event, FullyConfiguredMaterialHolder materials) {
		super(event.getGenerator().getPackOutput(), materials.getModID(), event.getExistingFileHelper());
		MATERIALS = materials::getMaterials;
		MOD_ID = materials.getModID();
	}
	
	private void fillTrimMaterials() {
		trimMaterials.put(TrimMaterials.QUARTZ, 0.1F);
		trimMaterials.put(TrimMaterials.IRON, 0.2F);
		trimMaterials.put(TrimMaterials.NETHERITE, 0.3F);
		trimMaterials.put(TrimMaterials.REDSTONE, 0.4F);
		trimMaterials.put(TrimMaterials.COPPER, 0.5F);
		trimMaterials.put(TrimMaterials.GOLD, 0.6F);
		trimMaterials.put(TrimMaterials.EMERALD, 0.7F);
		trimMaterials.put(TrimMaterials.DIAMOND, 0.8F);
		trimMaterials.put(TrimMaterials.LAPIS, 0.9F);
		trimMaterials.put(TrimMaterials.AMETHYST, 1.0F);
		for (int i = 0; i < MATERIALS.get().size(); i++) {
			trimMaterials.put(MATERIALS.get().get(i).getTrimMaterial(), 1 + 0.5f * (i + 1));
		}
	}

	@Override
	protected void registerModels() {
		for (MaterialConfiguration config : MATERIALS.get()) {

			basicItem(config.getBaseItem());
			handheldItem(config.getSword());
			handheldItem(config.getAxe());
			handheldItem(config.getPickaxe());
			handheldItem(config.getShovel());
			handheldItem(config.getHoe());

			if (config.getHorseArmor().isUsing()) {
				basicItem(config.getHorseArmor().get().get().asItem());
			}

			trimmedArmorItem(config.getHelmet());
			trimmedArmorItem(config.getChestplate());
			trimmedArmorItem(config.getLeggings());
			trimmedArmorItem(config.getBoots());

			switch (config.getType()) {
				case IRON -> {
					IronTypeMaterialConfiguration ironMatConfig = (IronTypeMaterialConfiguration) config;

					basicItem(ironMatConfig.getRawItem());
					basicItem(ironMatConfig.getNugget());
				}
				case CUBIC_ZIRCONIA -> {
					CubicZirconiaTypeMaterialConfiguration cubicMatConfig = (CubicZirconiaTypeMaterialConfiguration) config;

					basicItem(cubicMatConfig.getRawItem());
				}
				case END_IRON -> {
					EndIronTypeMaterialConfiguration ironMatConfig = (EndIronTypeMaterialConfiguration) config;

					basicItem(ironMatConfig.getRawItem());
					basicItem(ironMatConfig.getNugget());
				}
			}

		}

		trimmedArmorItem(Items.LEATHER_HELMET);
		trimmedArmorItem(Items.LEATHER_CHESTPLATE);
		trimmedArmorItem(Items.LEATHER_LEGGINGS);
		trimmedArmorItem(Items.LEATHER_BOOTS);


		trimmedArmorItem(Items.CHAINMAIL_HELMET);
		trimmedArmorItem(Items.CHAINMAIL_CHESTPLATE);
		trimmedArmorItem(Items.CHAINMAIL_LEGGINGS);
		trimmedArmorItem(Items.CHAINMAIL_BOOTS);


		trimmedArmorItem(Items.IRON_HELMET);
		trimmedArmorItem(Items.IRON_CHESTPLATE);
		trimmedArmorItem(Items.IRON_LEGGINGS);
		trimmedArmorItem(Items.IRON_BOOTS);

		trimmedArmorItem(Items.DIAMOND_HELMET);
		trimmedArmorItem(Items.DIAMOND_CHESTPLATE);
		trimmedArmorItem(Items.DIAMOND_LEGGINGS);
		trimmedArmorItem(Items.DIAMOND_BOOTS);
		trimmedArmorItem(Items.GOLDEN_HELMET);
		trimmedArmorItem(Items.GOLDEN_CHESTPLATE);
		trimmedArmorItem(Items.GOLDEN_LEGGINGS);
		trimmedArmorItem(Items.GOLDEN_BOOTS);
		trimmedArmorItem(Items.NETHERITE_HELMET);
		trimmedArmorItem(Items.NETHERITE_CHESTPLATE);
		trimmedArmorItem(Items.NETHERITE_LEGGINGS);
		trimmedArmorItem(Items.NETHERITE_BOOTS);

		trimmedArmorItem(Items.TURTLE_HELMET);
	}

	private void trimmedArmorItem(Item item) {

		if(item instanceof ArmorItem armorItem) {
			trimMaterials.forEach((trimMaterial, value) -> {
				float trimValue = value;

				String armorType = switch (armorItem.getEquipmentSlot()) {
					case HEAD -> "helmet";
					case CHEST -> "chestplate";
					case LEGS -> "leggings";
					case FEET -> "boots";
					default -> "";
				};

				String armorItemPath = armorItem.toString();
				String trimPath = "trims/items/" + armorType + "_trim_" + trimMaterial.location().getPath();
				String currentTrimName = armorItemPath + "_" + trimMaterial.location().getPath() + "_trim";
				ResourceLocation armorItemResLoc = ResourceLocation.parse(armorItemPath);
				ResourceLocation trimResLoc = ResourceLocation.parse(trimPath); // minecraft namespace
				ResourceLocation trimNameResLoc = ResourceLocation.parse(currentTrimName);
				
				ResourceLocation id = BuiltInRegistries.ITEM.getKey(item);

				// This is used for making the ExistingFileHelper acknowledge that this texture exist, so this will
				// avoid an IllegalArgumentException
				existingFileHelper.trackGenerated(trimResLoc, PackType.CLIENT_RESOURCES, ".png", "textures");

				// Trimmed armorItem files
				getBuilder(currentTrimName)
						.parent(new ModelFile.UncheckedModelFile("item/generated"))
						.texture("layer0", armorItemResLoc.getNamespace() + ":item/" + armorItemResLoc.getPath())
						.texture("layer1", trimResLoc);

				// Non-trimmed armorItem file (normal variant)
				this.withExistingParent(id.getPath(),
								mcLoc("item/generated"))
						.override()
						.model(new ModelFile.UncheckedModelFile(trimNameResLoc.getNamespace()  + ":item/" + trimNameResLoc.getPath()))
						.predicate(mcLoc("trim_type"), trimValue).end()
						.texture("layer0",
								ResourceLocation.fromNamespaceAndPath(MOD_ID,
										"item/" + id.getPath()));
			});
		}
	}
}
