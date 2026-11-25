package com.github.theelementguy.tegmatlib.core.serialize;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import org.jetbrains.annotations.NotNull;


public record ToolArmorConfiguration(int toolDurability, float speed, float attackDamageBonus, int enchantmentValue, int armorDurability, int helmetDefense, int chestplateDefense, int leggingsDefense, int bootsDefense, int horseDefense, Holder<@NotNull SoundEvent> equipSound, float toughness, float knockbackResistance, SpearConfiguration spear) {

	public static final Codec<ToolArmorConfiguration> CODEC = RecordCodecBuilder.create(instance -> instance.group(Codec.INT.fieldOf("tool_durability").forGetter(ToolArmorConfiguration::toolDurability), Codec.FLOAT.fieldOf("speed").forGetter(ToolArmorConfiguration::speed), Codec.FLOAT.fieldOf("attack_damage_bonus").forGetter(ToolArmorConfiguration::attackDamageBonus), Codec.INT.fieldOf("enchantment_value").forGetter(ToolArmorConfiguration::enchantmentValue), Codec.INT.fieldOf("armor_durability").forGetter(ToolArmorConfiguration::armorDurability), Codec.INT.fieldOf("helmet_defense").forGetter(ToolArmorConfiguration::helmetDefense), Codec.INT.fieldOf("chestplate_defense").forGetter(ToolArmorConfiguration::chestplateDefense), Codec.INT.fieldOf("leggings_defense").forGetter(ToolArmorConfiguration::leggingsDefense), Codec.INT.fieldOf("boots_defense").forGetter(ToolArmorConfiguration::bootsDefense), Codec.INT.fieldOf("horse_defense").forGetter(ToolArmorConfiguration::horseDefense), BuiltInRegistries.SOUND_EVENT.holderByNameCodec().fieldOf("equip_sound").forGetter(ToolArmorConfiguration::equipSound), Codec.FLOAT.fieldOf("toughness").forGetter(ToolArmorConfiguration::toughness), Codec.FLOAT.fieldOf("knockback_resistance").forGetter(ToolArmorConfiguration::knockbackResistance), SpearConfiguration.CODEC.fieldOf("spear_configuration").forGetter(ToolArmorConfiguration::spear)).apply(instance, ToolArmorConfiguration::new));

}
