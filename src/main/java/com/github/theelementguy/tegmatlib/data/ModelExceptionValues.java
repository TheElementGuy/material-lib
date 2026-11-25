package com.github.theelementguy.tegmatlib.data;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;

public enum ModelExceptionValues implements StringRepresentable {
	CUBE("cube"),
	CUBE_TOP("cube_top"),
	CUBE_TOP_BOTTOM("cube_top_bottom"),
	COLUMN("column");

	private final String name;

	public static final Codec<ModelExceptionValues> CODEC = StringRepresentable.fromEnum(ModelExceptionValues::values);

	ModelExceptionValues(String name) {
		this.name = name;
	}

	@Override
	public String getSerializedName() {
		return name;
	}
}