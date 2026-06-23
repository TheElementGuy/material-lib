package com.github.theelementguy.tegmatlib.data;

import com.github.theelementguy.tegmatlib.core.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;
import java.util.function.Supplier;

public class TEGMatLibBlockStateProvider extends BlockStateProvider {

	protected final Supplier<List<MaterialConfiguration>> MATERIALS;
	protected final String MOD_ID;

	public TEGMatLibBlockStateProvider(GatherDataEvent event, FullyConfiguredMaterialHolder materials) {
		super(event.getGenerator().getPackOutput(), materials.getModID(), event.getExistingFileHelper());
		MATERIALS = materials::getMaterials;
		MOD_ID = materials.getModID();
	}

	@Override
	protected void registerStatesAndModels() {

		for (MaterialConfiguration config : MATERIALS.get()) {

			cubeWithModelException(config.getBaseBlock(), config.getModelExceptions());

			switch (config.getType()) {
				case IRON -> {
					IronTypeMaterialConfiguration ironMatConfig = (IronTypeMaterialConfiguration) config;

					createTrivialCube(ironMatConfig.getRawBlock());
					createTrivialCube(ironMatConfig.getOre());
					createTrivialCube(ironMatConfig.getDeepslateOre());
				}
				case DIAMOND -> {
					DiamondTypeMaterialConfiguration diamondMatConfig = (DiamondTypeMaterialConfiguration) config;
					createTrivialCube(diamondMatConfig.getOre());
					createTrivialCube(diamondMatConfig.getDeepslateOre());
				}
				case CUBIC_ZIRCONIA -> {
					CubicZirconiaTypeMaterialConfiguration cubicMatConfig = (CubicZirconiaTypeMaterialConfiguration) config;

					createTrivialCube(cubicMatConfig.getRawBlock());
					createTrivialCube(cubicMatConfig.getOre());
					createTrivialCube(cubicMatConfig.getDeepslateOre());
				}
				case NETHER_DIAMOND -> {
					NetherDiamondTypeMaterialConfiguration netherDiamondMatConfig = (NetherDiamondTypeMaterialConfiguration) config;
					createTrivialCube(netherDiamondMatConfig.getNetherOre());
				}
				case END_DIAMOND -> {
					EndDiamondTypeMaterialConfiguration endDiamondMatConfig = (EndDiamondTypeMaterialConfiguration) config;
					createTrivialCube(endDiamondMatConfig.getEndOre());
				}
				case END_IRON -> {
					EndIronTypeMaterialConfiguration ironMatConfig = (EndIronTypeMaterialConfiguration) config;

					createTrivialCube(ironMatConfig.getRawBlock());
					createTrivialCube(ironMatConfig.getEndOre());
				}
				case SAND_DIAMOND -> {
					SandDiamondTypeMaterialConfiguration sandDiamondMatConfig = (SandDiamondTypeMaterialConfiguration) config;
					createTrivialCube(sandDiamondMatConfig.getSandOre());
					createTrivialCube(sandDiamondMatConfig.getGravelOre());
				}
			}

		}

	}

	protected void createTrivialCube(Block block) {
		simpleBlockWithItem(block, cubeAll(block));
	}

	protected void cubeWithModelException(Block block, List<ModelException> exceptions) {
		for (ModelException m : exceptions) {
			if (block.getDescriptionId().contains(m.name())) {
				switch (m.overrideTemplate()) {
					case CUBE -> createTrivialCube(block);
					case COLUMN -> {
						String name = BuiltInRegistries.BLOCK.getKey(block).getPath();
						ResourceLocation side = ResourceLocation.fromNamespaceAndPath(MOD_ID, "block/" + name);
						ResourceLocation top = ResourceLocation.fromNamespaceAndPath(MOD_ID, "block/" + name + "_top");
						models().cubeColumn(name, side, top);
						return;
					}
				}
			}
		}
		createTrivialCube(block);
	}

}
