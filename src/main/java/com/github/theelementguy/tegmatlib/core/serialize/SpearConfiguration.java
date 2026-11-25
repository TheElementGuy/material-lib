package com.github.theelementguy.tegmatlib.core.serialize;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record SpearConfiguration(float swingDuration, float damageMultiplier, float delay, float dismountMaxDuration, float dismountMinSpeed, float knockbackMaxDuration, float knockbackMinSpeed, float damageMaxDuration, float damageMinSpeed) {

	public static final Codec<SpearConfiguration> CODEC = RecordCodecBuilder.create(instance -> instance.group(Codec.FLOAT.fieldOf("swing_duration").forGetter(SpearConfiguration::swingDuration), Codec.FLOAT.fieldOf("damage_multiplier").forGetter(SpearConfiguration::damageMultiplier), Codec.FLOAT.fieldOf("delay").forGetter(SpearConfiguration::delay), Codec.FLOAT.fieldOf("dismount_max_duration").forGetter(SpearConfiguration::damageMaxDuration), Codec.FLOAT.fieldOf("dismount_min_speed").forGetter(SpearConfiguration::dismountMinSpeed), Codec.FLOAT.fieldOf("knockback_max_duration").forGetter(SpearConfiguration::knockbackMaxDuration), Codec.FLOAT.fieldOf("knockback_min_speed").forGetter(SpearConfiguration::knockbackMinSpeed), Codec.FLOAT.fieldOf("damage_max_duration").forGetter(SpearConfiguration::damageMaxDuration), Codec.FLOAT.fieldOf("damage_min_speed").forGetter(SpearConfiguration::damageMinSpeed)).apply(instance, SpearConfiguration::new));

}
