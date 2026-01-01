package com.github.theelementguy.tegmatlib.data;

import net.minecraft.client.data.models.EquipmentAssetProvider;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.EquipmentAsset;
import com.github.theelementguy.tegmatlib.core.MaterialConfiguration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class TEGMatLibEquipmentAssetProvider extends EquipmentAssetProvider {

	protected final PackOutput.PathProvider pathProvider;

	protected final Supplier<List<MaterialConfiguration>> MATERIALS;

	public TEGMatLibEquipmentAssetProvider(PackOutput output, PackOutput.PathProvider pathProvider, Supplier<List<MaterialConfiguration>> materials) {
		super(output);
		this.pathProvider = pathProvider;
		MATERIALS = materials;
	}

	@Override
	public CompletableFuture<?> run(CachedOutput p_387304_) {
		Map<ResourceKey<EquipmentAsset>, EquipmentClientInfo> MAP = new HashMap<>();
		for (MaterialConfiguration config : MATERIALS.get()) {
			config.bootstrapEquipmentAsset((key, model) -> {
				if (MAP.putIfAbsent(key, model) != null) {
					throw new IllegalStateException("Duplicate equipment asset for:" + key.location());
				}
			});
		}
		return DataProvider.saveAll(p_387304_, EquipmentClientInfo.CODEC, this.pathProvider::json, MAP);
	}
}
