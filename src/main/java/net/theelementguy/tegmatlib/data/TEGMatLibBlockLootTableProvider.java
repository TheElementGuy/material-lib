package net.theelementguy.tegmatlib.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.theelementguy.tegmatlib.core.IronTypeMaterialConfiguration;
import net.theelementguy.tegmatlib.core.MaterialConfiguration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class TEGMatLibBlockLootTableProvider extends BlockLootSubProvider {

	private Supplier<List<MaterialConfiguration>> MATERIALS;

	public TEGMatLibBlockLootTableProvider(HolderLookup.Provider registries, Supplier<List<MaterialConfiguration>> materials) {
		super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
		MATERIALS = materials;
	}

	public static LootTableProvider create(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, Supplier<List<MaterialConfiguration>> materials) {
		return new LootTableProvider(output, Collections.emptySet(), List.of(new LootTableProvider.SubProviderEntry(p -> {return new TEGMatLibBlockLootTableProvider(p, materials);}, LootContextParamSets.BLOCK)), provider);
	}

	@Override
	protected void generate() {

		for (MaterialConfiguration config : MATERIALS.get()) {
			add(config);
		}

	}

	protected void add(MaterialConfiguration config) {

		switch (config.getType()) {
			case IRON -> {
				IronTypeMaterialConfiguration mat = (IronTypeMaterialConfiguration) config;
				dropSelf(mat.getBaseBlock());
				dropSelf(mat.getRawBlock());
				if (config.isSingleOre()) {
					add(mat.getOre(), b -> createOreDrop(mat.getOre(), mat.getRawItem()));
					add(mat.getDeepslateOre(), b -> createOreDrop(mat.getDeepslateOre(), mat.getRawItem()));
				} else {
					add(mat.getOre(), b -> createMultipleOreDrops(mat.getOre(), mat.getRawItem(), mat));
					add(mat.getDeepslateOre(), b -> createMultipleOreDrops(mat.getDeepslateOre(), mat.getRawItem(), mat));
				}
			}
		}

	}

	protected LootTable.Builder createMultipleOreDrops(Block pBlock, Item item, MaterialConfiguration materialConfiguration) {
		return createMultipleOreDrops(pBlock, item, materialConfiguration.getBaseDrops(), materialConfiguration.getMaxDrops());
	}

	protected LootTable.Builder createMultipleOreDrops(Block pBlock, Item item, float minDrops, float maxDrops) {
		HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
		return this.createSilkTouchDispatchTable(pBlock, this.applyExplosionDecay(pBlock, LootItem.lootTableItem(item).apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, maxDrops))).apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))));
	}

	@Override
	protected Iterable<Block> getKnownBlocks() {
		List<Block> toReturn = new ArrayList<>();
		for (MaterialConfiguration config : MATERIALS.get()) {
			toReturn.addAll(config.getBlocks());
		}
		return toReturn;
	}
}
