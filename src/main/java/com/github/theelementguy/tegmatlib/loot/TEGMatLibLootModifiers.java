package com.github.theelementguy.tegmatlib.loot;

import com.github.theelementguy.tegmatlib.core.FullyConfiguredMaterialHolder;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.MapCodec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.function.Supplier;

public class TEGMatLibLootModifiers {

	private final Logger LOG = LogUtils.getLogger();

	public final String MOD_ID;

	public Supplier<MapCodec<? extends IGlobalLootModifier>> ADD_ITEM_ROLL_MODIFIER;
	public Supplier<MapCodec<? extends IGlobalLootModifier>> EXTRA_ITEM_ROLL_MODIFIER;

	public TEGMatLibLootModifiers(FullyConfiguredMaterialHolder materialHolder) {
		MOD_ID = materialHolder.getModID();
	}

	public TEGMatLibLootModifiers(String modID) {
		MOD_ID = modID;
	}

	public void registerModifiers(DeferredRegister<@NotNull MapCodec<? extends IGlobalLootModifier>> register) {
		LOG.info("HELLO from tegmatlib loot modifier registration for mod {}", MOD_ID);
		ADD_ITEM_ROLL_MODIFIER = register.register("add_item_roll_modifier", AddItemRollModifier::getCodec);
		EXTRA_ITEM_ROLL_MODIFIER = register.register("extra_item_roll_modifier", ExtraItemRollModifier::getCodec);
	}

}
