package com.github.theelementguy.tegmatlib.data;

import com.github.theelementguy.tegmatlib.core.FullyConfiguredMaterialHolder;
import com.github.theelementguy.tegmatlib.core.MaterialConfiguration;
import com.github.theelementguy.tegmatlib.loot.AddItemRollModifier;
import com.github.theelementguy.tegmatlib.loot.ExtraItemRollModifier;
import com.github.theelementguy.tegmatlib.loot.LootModifierInfo;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

public class TEGMatLibGlobalLootModifierProvider extends GlobalLootModifierProvider {

	FullyConfiguredMaterialHolder MATERIALS;

	public TEGMatLibGlobalLootModifierProvider(GatherDataEvent.Client event, FullyConfiguredMaterialHolder materials) {
		MATERIALS = materials;
		super(event.getGenerator().getPackOutput(), event.getLookupProvider(), materials.getModID());
	}

	@Override
	protected void start() {

		for (MaterialConfiguration m : MATERIALS.getMaterials()) {
			for (LootModifierInfo l : m.getLootModifiers()) {
				switch (l.type()) {
					case ADD -> {
						addTo(l.table(), l.item(), l.chance());
					}
					case EXTRA -> {
						extraTo(l.table(), l.item(), l.chance());
					}
				}
			}
		}

	}

	protected void addTo(String table, Item item, float chance) {
		this.add(BuiltInRegistries.ITEM.getKey(item).getPath() + "_to_" + table.substring(table.lastIndexOf("/") + 1), new AddItemRollModifier(new LootItemCondition[] {new LootTableIdCondition.Builder(Identifier.withDefaultNamespace(table)).build()}, item, chance));
	}

	protected void extraTo(String table, Item item, float chance) {
		this.add(BuiltInRegistries.ITEM.getKey(item).getPath() + "_to_" + table.substring(table.lastIndexOf("/") + 1), new ExtraItemRollModifier(new LootItemCondition[] {new LootTableIdCondition.Builder(Identifier.withDefaultNamespace(table)).build()}, item, chance));
	}
}
