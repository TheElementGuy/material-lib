package com.github.theelementguy.tegmatlib.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record ModelException(String name, ModelExceptionValues overrideTemplate) {

	public static final Codec<ModelException> CODEC = RecordCodecBuilder.create(instance -> instance.group(Codec.STRING.fieldOf("name").forGetter(ModelException::name), ModelExceptionValues.CODEC.fieldOf("override_template").forGetter(ModelException::overrideTemplate)).apply(instance, ModelException::new));

}