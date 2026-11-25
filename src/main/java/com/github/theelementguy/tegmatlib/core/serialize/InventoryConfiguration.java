package com.github.theelementguy.tegmatlib.core.serialize;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public record InventoryConfiguration(String toolsBefore, String armorBefore, Holder<@NotNull Item> itemBefore, Holder<@NotNull Block> blockBefore, String oreBefore, Optional<String> rawBefore, Optional<String> animalArmorBefore) {

	public static final Codec<InventoryConfiguration> CODEC = RecordCodecBuilder.create(instance -> instance.group(Codec.STRING.fieldOf("tools").forGetter(InventoryConfiguration::toolsBefore), Codec.STRING.fieldOf("armor").forGetter(InventoryConfiguration::armorBefore), BuiltInRegistries.ITEM.holderByNameCodec().fieldOf("item").forGetter(InventoryConfiguration::itemBefore), BuiltInRegistries.BLOCK.holderByNameCodec().fieldOf("block").forGetter(InventoryConfiguration::blockBefore), Codec.STRING.fieldOf("ore").forGetter(InventoryConfiguration::oreBefore), Codec.STRING.optionalFieldOf("raw").forGetter(InventoryConfiguration::rawBefore), Codec.STRING.optionalFieldOf("animal_armor_before").forGetter(InventoryConfiguration::animalArmorBefore)).apply(instance, InventoryConfiguration::new));
}
