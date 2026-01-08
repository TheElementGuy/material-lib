package com.github.theelementguy.tegmatlib.data;

import com.github.theelementguy.tegmatlib.core.*;
import net.minecraft.util.Util;
import net.minecraft.client.main.GameConfig;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import com.github.theelementguy.tegmatlib.core.*;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;
import java.util.function.Supplier;

public class TEGMatLibLanguageProvider extends LanguageProvider {

	protected Supplier<List<MaterialConfiguration>> MATERIALS;

	public TEGMatLibLanguageProvider(GatherDataEvent.Client event, FullyConfiguredMaterialHolder materials) {
		super(event.getGenerator().getPackOutput(), materials.getModID(), "en_us");
		this.MATERIALS = materials::getMaterials;
	}

	@Override
	protected void addTranslations() {

		for (MaterialConfiguration config : MATERIALS.get()) {

			add(config.getSword(), config.getHumanReadableName() + " Sword");
			add(config.getAxe(), config.getHumanReadableName() + " Axe");
			add(config.getPickaxe(), config.getHumanReadableName() + " Pickaxe");
			add(config.getShovel(), config.getHumanReadableName() + " Shovel");
			add(config.getHoe(), config.getHumanReadableName() + " Hoe");
			add(config.getSpear(), config.getHumanReadableName() + "Spear");

			add(config.getHelmet(), config.getHumanReadableName() + " Helmet");
			add(config.getChestplate(), config.getHumanReadableName() + " Chestplate");
			add(config.getLeggings(), config.getHumanReadableName() + " Leggings");
			add(config.getBoots(), config.getHumanReadableName() + " Boots");

			add(config.getBaseBlock(), "Block of " + config.getHumanReadableName());

			add(Util.makeDescriptionId("trim_material", config.getTrimMaterial().identifier()), config.getHumanReadableName());

			switch (config.getType()) {
				case IRON -> {
					IronTypeMaterialConfiguration ironMatConfig = (IronTypeMaterialConfiguration) config;
					add(ironMatConfig.getBaseItem(), ironMatConfig.getHumanReadableName() + " Ingot");
					add(ironMatConfig.getRawItem(), "Raw " + ironMatConfig.getHumanReadableName());
					add(ironMatConfig.getRawBlock(), "Block of Raw " + ironMatConfig.getHumanReadableName());
					add(ironMatConfig.getOre(), ironMatConfig.getHumanReadableName() + " Ore");
					add(ironMatConfig.getDeepslateOre(), "Deepslate " + ironMatConfig.getHumanReadableName() + " Ore");
					add(ironMatConfig.getNugget(), ironMatConfig.getHumanReadableName() + " Nugget");
				}
				case DIAMOND -> {
					DiamondTypeMaterialConfiguration diamondMatConfig = (DiamondTypeMaterialConfiguration) config;
					add(diamondMatConfig.getBaseItem(), diamondMatConfig.getHumanReadableName());
					add(diamondMatConfig.getOre(), diamondMatConfig.getHumanReadableName() + " Ore");
					add(diamondMatConfig.getDeepslateOre(), "Deepslate " + diamondMatConfig.getHumanReadableName() + " Ore");
				}
				case CUBIC_ZIRCONIA -> {
					CubicZirconiaTypeMaterialConfiguration cubicMatConfig = (CubicZirconiaTypeMaterialConfiguration) config;
					add(cubicMatConfig.getBaseItem(), cubicMatConfig.getHumanReadableName());
					add(cubicMatConfig.getRawItem(), "Raw " + cubicMatConfig.getHumanReadableName());
					add(cubicMatConfig.getRawBlock(), "Block of Raw " + cubicMatConfig.getHumanReadableName());
					add(cubicMatConfig.getOre(), cubicMatConfig.getHumanReadableName() + " Ore");
					add(cubicMatConfig.getDeepslateOre(), "Deepslate " + cubicMatConfig.getHumanReadableName() + " Ore");
				}
				case NETHER_DIAMOND -> {
					NetherDiamondTypeMaterialConfiguration netherDiamondMatConfig = (NetherDiamondTypeMaterialConfiguration) config;
					add(netherDiamondMatConfig.getBaseItem(), netherDiamondMatConfig.getHumanReadableName());
					add(netherDiamondMatConfig.getNetherOre(), "Nether " + netherDiamondMatConfig.getHumanReadableName() + " Ore");
				}
				case END_DIAMOND -> {
					EndDiamondTypeMaterialConfiguration endDiamondMatConfig = (EndDiamondTypeMaterialConfiguration) config;
					add(endDiamondMatConfig.getBaseItem(), endDiamondMatConfig.getHumanReadableName());
					add(endDiamondMatConfig.getEndOre(), "End " + endDiamondMatConfig.getHumanReadableName() + " Ore");
				}
			}

		}

	}
}
