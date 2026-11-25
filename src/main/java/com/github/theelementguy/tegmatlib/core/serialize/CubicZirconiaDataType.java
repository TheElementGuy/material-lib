package com.github.theelementguy.tegmatlib.core.serialize;

import com.github.theelementguy.tegmatlib.core.MaterialType;
import com.github.theelementguy.tegmatlib.data.ModelException;
import com.github.theelementguy.tegmatlib.loot.PreLootModifierInfo;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;

import java.util.List;

public record CubicZirconiaDataType(String modId, String baseName, String humanReadableName, String trimMaterialDescriptionColor, PreItemProperties defaultProperties, float smeltingExperience, ToolArmorConfiguration toolArmorConfig, MapColor mapColor, SoundType soundType, TotalOreConfig oreConfig, InventoryConfiguration inventory, boolean usingHorseArmor, boolean usingNautilusArmor, List<PreLootModifierInfo> lootModifiers, List<ModelException> modelExceptions) implements MaterialConfigSerializable {

	public static final MapCodec<CubicZirconiaDataType> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Codec.STRING.fieldOf("mod_id").forGetter(CubicZirconiaDataType::modId), Codec.STRING.fieldOf("base_name").forGetter(CubicZirconiaDataType::baseName), Codec.STRING.fieldOf("human_readable_name").forGetter(CubicZirconiaDataType::humanReadableName), Codec.STRING.fieldOf("trim_material_description_color").forGetter(CubicZirconiaDataType::trimMaterialDescriptionColor), PreItemProperties.CODEC.fieldOf("default_properties").forGetter(CubicZirconiaDataType::defaultProperties), Codec.FLOAT.fieldOf("smelting_experience").forGetter(CubicZirconiaDataType::smeltingExperience), ToolArmorConfiguration.CODEC.fieldOf("tool_armor_config").forGetter(CubicZirconiaDataType::toolArmorConfig), EnvironmentCodecs.MAP_COLOR_CODEC.fieldOf("map_color").forGetter(CubicZirconiaDataType::mapColor), EnvironmentCodecs.SOUND_TYPE_CODEC.fieldOf("sound_type").forGetter(CubicZirconiaDataType::soundType), TotalOreConfig.CODEC.fieldOf("ore_config").forGetter(CubicZirconiaDataType::oreConfig), InventoryConfiguration.CODEC.fieldOf("inventory_config").forGetter(CubicZirconiaDataType::inventory), Codec.BOOL.fieldOf("using_horse_armor").forGetter(CubicZirconiaDataType::usingHorseArmor), Codec.BOOL.fieldOf("using_nautilus_armor").forGetter(CubicZirconiaDataType::usingNautilusArmor), PreLootModifierInfo.CODEC.listOf().fieldOf("loot_modifier").forGetter(CubicZirconiaDataType::lootModifiers), ModelException.CODEC.listOf().fieldOf("model_exception").forGetter(CubicZirconiaDataType::modelExceptions)).apply(instance, CubicZirconiaDataType::new));

	@Override
	public MaterialType getType() {
		return MaterialType.CUBIC_ZIRCONIA;
	}
}
