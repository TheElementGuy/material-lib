package com.github.theelementguy.tegmatlib.data;

import com.github.theelementguy.tegmatlib.core.FullyConfiguredMaterialHolder;
import com.mojang.logging.LogUtils;
import net.minecraft.client.data.models.EquipmentAssetProvider;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.EquipmentAsset;
import com.github.theelementguy.tegmatlib.core.MaterialConfiguration;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class TEGMatLibEquipmentAssetProvider extends EquipmentAssetProvider {

	private final Logger LOG = LogUtils.getLogger();

	protected final PackOutput.PathProvider pathProvider;

	protected final Supplier<List<MaterialConfiguration>> MATERIALS;

	protected final String MOD_ID;

	public TEGMatLibEquipmentAssetProvider(GatherDataEvent.Client event, FullyConfiguredMaterialHolder materials) {
		super(event.getGenerator().getPackOutput());
		this.pathProvider = event.getGenerator().getPackOutput().createPathProvider(PackOutput.Target.DATA_PACK, "equipment");
		MATERIALS = materials::getMaterials;
		MOD_ID = materials.getModID();
	}

	@Override
	protected void registerModels(BiConsumer<ResourceKey<EquipmentAsset>, EquipmentClientInfo> output) {
		LOG.info("Bootstrapping equipment assets for mod {}", MOD_ID);
		for (MaterialConfiguration m : MATERIALS.get()) {
			m.bootstrapEquipmentAsset(output);
		}
	}
}
