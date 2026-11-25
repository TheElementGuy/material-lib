package com.github.theelementguy.tegmatlib.loot;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;

public enum LootModifierType implements StringRepresentable {
	ADD("add"),
	EXTRA("extra");

	private final String name;

	public static final Codec<LootModifierType> CODEC = StringRepresentable.fromEnum(LootModifierType::values);

	LootModifierType(String name) {
		this.name = name;
	}

	@Override
	public String getSerializedName() {
		return name;
	}
}
