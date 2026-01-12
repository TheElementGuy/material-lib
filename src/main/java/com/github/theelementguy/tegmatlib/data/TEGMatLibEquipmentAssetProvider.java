package com.github.theelementguy.tegmatlib.data;

import com.github.theelementguy.tegmatlib.core.FullyConfiguredMaterialHolder;
import net.minecraft.client.data.models.EquipmentAssetProvider;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.EquipmentAsset;
import com.github.theelementguy.tegmatlib.core.MaterialConfiguration;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class TEGMatLibEquipmentAssetProvider extends EquipmentAssetProvider {

	protected final PackOutput.PathProvider pathProvider;

	protected final Supplier<List<MaterialConfiguration>> MATERIALS;

	public TEGMatLibEquipmentAssetProvider(GatherDataEvent.Client event, FullyConfiguredMaterialHolder materials) {
		super(event.getGenerator().getPackOutput());
		this.pathProvider = event.getGenerator().getPackOutput().createPathProvider(PackOutput.Target.DATA_PACK, "equipment");
		MATERIALS = materials::getMaterials;
	}

	@Override
	protected void registerModels(BiConsumer<ResourceKey<EquipmentAsset>, EquipmentClientInfo> output) {
		for (MaterialConfiguration m : MATERIALS.get()) {
			m.bootstrapEquipmentAsset(output);
		}
	}
}
