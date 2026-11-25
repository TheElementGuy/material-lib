package com.github.theelementguy.tegmatlib.core.serialize;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

import java.util.function.Supplier;

public record PreItemProperties(boolean fireproof, Rarity rarity) {

	public static final Codec<PreItemProperties> CODEC = RecordCodecBuilder.create(instance -> instance.group(Codec.BOOL.fieldOf("fireproof").forGetter(PreItemProperties::fireproof), Rarity.CODEC.fieldOf("rarity").forGetter(PreItemProperties::rarity)).apply(instance, PreItemProperties::new));

	public Supplier<Item.Properties> convert() {
		return () -> {
			Item.Properties properties = new Item.Properties();
			if (fireproof) {
				properties.fireResistant();
			}
			properties.rarity(rarity);
			return properties;
		};
	}

}
