package com.github.theelementguy.tegmatlib.core;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum SingleOrMultiple implements StringRepresentable {
	SINGLE("single"),
	MULTIPLE("multiple");

	private final String name;

	public static final Codec<SingleOrMultiple> CODEC = StringRepresentable.fromEnum(SingleOrMultiple::values);

	SingleOrMultiple(String name) {
		this.name = name;
	}

	@Override
	public @NotNull String getSerializedName() {
		return this.name;
	}
}
