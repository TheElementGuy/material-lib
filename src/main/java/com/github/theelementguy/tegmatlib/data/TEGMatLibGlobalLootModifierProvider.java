package com.github.theelementguy.tegmatlib.data;

import com.github.theelementguy.tegmatlib.core.FullyConfiguredMaterialHolder;
import com.github.theelementguy.tegmatlib.core.MaterialConfiguration;
import com.github.theelementguy.tegmatlib.loot.AddItemRollModifier;
import com.github.theelementguy.tegmatlib.loot.ExtraItemRollModifier;
import com.github.theelementguy.tegmatlib.loot.LootModifierInfo;
import com.mojang.logging.LogUtils;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.predicates.AllOfCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.slf4j.Logger;

import java.util.Optional;

public class TEGMatLibGlobalLootModifierProvider extends GlobalLootModifierProvider {

	private final Logger LOG = LogUtils.getLogger();

	private final FullyConfiguredMaterialHolder MATERIALS;

	public TEGMatLibGlobalLootModifierProvider(GatherDataEvent.Client event, FullyConfiguredMaterialHolder materials) {
		MATERIALS = materials;
		super(event.getGenerator().getPackOutput(), event.getWorldLookupProvider(), materials.getModID());
	}

	@Override
	protected void start() {

		LOG.info("Adding global loot modifiers for mod {}", MATERIALS.getModID());

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
		this.add(BuiltInRegistries.ITEM.getKey(item).getPath() + "_to_" + table.substring(table.lastIndexOf("/") + 1), new AddItemRollModifier(Optional.of(Holder.direct(AllOfCondition.allOf(LootTableIdCondition.builder(Identifier.withDefaultNamespace(table))).build())), 1000, item, chance));
	}

	protected void extraTo(String table, Item item, float chance) {
		this.add(BuiltInRegistries.ITEM.getKey(item).getPath() + "_to_" + table.substring(table.lastIndexOf("/") + 1), new ExtraItemRollModifier(Optional.of(Holder.direct(AllOfCondition.allOf(LootTableIdCondition.builder(Identifier.withDefaultNamespace(table))).build())), 1000, item, chance));
	}
}
