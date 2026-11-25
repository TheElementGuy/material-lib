package com.github.theelementguy.tegmatlib.core;

import com.github.theelementguy.tegmatlib.core.serialize.*;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.util.StringRepresentable;


public enum MaterialType implements StringRepresentable {
	DIAMOND("diamond", DiamondDataType.CODEC),
	IRON("iron", IronDataType.CODEC),
	CUBIC_ZIRCONIA("cubic_zirconia", CubicZirconiaDataType.CODEC),
	NETHER_IRON("nether_iron", null),
	NETHER_DIAMOND("nether_diamond", NetherDiamondDataType.CODEC),
	NETHER_CUBIC_ZIRCONIA("nether_cubic_zirconia", null),
	END_IRON("end_iron", EndIronDataType.CODEC),
	END_DIAMOND("end_diamond", EndDiamondDataType.CODEC),
	END_CUBIC_ZIRCONIA("end_cubic_zirconia", null),
	SAND_DIAMOND("sand_diamond", SandDiamondDataType.CODEC),
	SAND_IRON("sand_iron", null),
	SAND_CUBIC_ZIRCONIA("sand_cubic_zirconia", null);

	private final String name;
	private final MapCodec<? extends MaterialConfigSerializable> codec;

	public static final Codec<MaterialType> CODEC = StringRepresentable.fromEnum(MaterialType::values);

	MaterialType(String name, MapCodec<? extends MaterialConfigSerializable> codec) {
		this.name = name;
		this.codec = codec;
	}

	public MapCodec<? extends MaterialConfigSerializable> getCodec() {
		return codec;
	}

	@Override
	public String getSerializedName() {
		return name;
	}
}
