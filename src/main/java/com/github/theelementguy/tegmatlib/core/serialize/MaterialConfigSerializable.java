package com.github.theelementguy.tegmatlib.core.serialize;

import com.github.theelementguy.tegmatlib.core.MaterialType;
import com.mojang.serialization.Codec;

public interface MaterialConfigSerializable {

	MaterialType getType();

	Codec<MaterialConfigSerializable> CODEC = MaterialType.CODEC.dispatch(MaterialConfigSerializable::getType, MaterialType::getCodec);

}