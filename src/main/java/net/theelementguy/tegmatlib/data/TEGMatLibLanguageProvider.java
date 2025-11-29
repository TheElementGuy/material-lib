package net.theelementguy.tegmatlib.data;

import net.minecraft.Util;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.theelementguy.tegmatlib.core.IronTypeMaterialConfiguration;
import net.theelementguy.tegmatlib.core.MaterialConfiguration;

import java.util.List;
import java.util.function.Supplier;

public class TEGMatLibLanguageProvider extends LanguageProvider {

	protected Supplier<List<MaterialConfiguration>> MATERIALS;

	public TEGMatLibLanguageProvider(PackOutput output, String modid, Supplier<List<MaterialConfiguration>> materials) {
		super(output, modid, "en_us");
		this.MATERIALS = materials;
	}

	@Override
	protected void addTranslations() {

		for (MaterialConfiguration config : MATERIALS.get()) {

			add(config.getSword(), config.getHumanReadableName() + " Sword");
			add(config.getAxe(), config.getHumanReadableName() + " Axe");
			add(config.getPickaxe(), config.getHumanReadableName() + " Pickaxe");
			add(config.getShovel(), config.getHumanReadableName() + " Shovel");
			add(config.getHoe(), config.getHumanReadableName() + " Hoe");

			add(config.getHelmet(), config.getHumanReadableName() + " Helmet");
			add(config.getChestplate(), config.getHumanReadableName() + " Chestplate");
			add(config.getLeggings(), config.getHumanReadableName() + " Leggings");
			add(config.getBoots(), config.getHumanReadableName() + " Boots");

			add(config.getBaseBlock(), "Block of " + config.getHumanReadableName());

			add(Util.makeDescriptionId("trim_material", config.getTrimMaterial().location()), config.getHumanReadableName());

			switch (config.getType()) {
				case IRON -> {
					IronTypeMaterialConfiguration ironMatConfig = (IronTypeMaterialConfiguration) config;
					add(ironMatConfig.getBaseItem(), ironMatConfig.getHumanReadableName() + " Ingot");
					add(ironMatConfig.getRawItem(), "Raw " + ironMatConfig.getHumanReadableName());
					add(ironMatConfig.getRawBlock(), "Block of Raw " + ironMatConfig.getHumanReadableName());
					add(ironMatConfig.getOre(), ironMatConfig.getHumanReadableName() + " Ore");
					add(ironMatConfig.getDeepslateOre(), "Deepslate " + ironMatConfig.getHumanReadableName() + " Ore");
				}
			}

		}

	}
}
