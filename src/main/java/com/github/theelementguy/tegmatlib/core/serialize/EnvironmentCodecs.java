package com.github.theelementguy.tegmatlib.core.serialize;

import com.mojang.serialization.Codec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;

public class EnvironmentCodecs {

	public static final Codec<SoundType> SOUND_TYPE_CODEC = Codec.STRING.xmap(EnvironmentCodecs::soundEventConvert, event -> Identifier.fromNamespaceAndPath(event.getBreakSound().location().getNamespace(), event.getBreakSound().location().getPath().replace("block.", "").replace(".break", "")).toString());

	public static final Codec<MapColor> MAP_COLOR_CODEC = Codec.INT.xmap(id -> MapColor.byId(id), color -> color.id);

	private static SoundType soundEventConvert(String typeString) {

		Identifier identifier = Identifier.parse(typeString);

		String[] endings = {".break", ".step", ".place", ".hit", ".fall"};
		Identifier[] lookups = new Identifier[endings.length];

		for (int i = 0; i < endings.length; i++) {
			lookups[i] = Identifier.fromNamespaceAndPath(identifier.getNamespace(), "block." + identifier.getPath() + endings[i]);
		}

		return new SoundType(1f, 1f, soundEventLookup(lookups[0]), soundEventLookup(lookups[1]), soundEventLookup(lookups[2]), soundEventLookup(lookups[3]), soundEventLookup(lookups[4]));
	}

	private static SoundEvent soundEventLookup(Identifier key) {
		return BuiltInRegistries.SOUND_EVENT.get(key).orElseGet(() -> BuiltInRegistries.SOUND_EVENT.get(Identifier.fromNamespaceAndPath("minecraft", "block.stone.break")).get()).value();
	}

}
