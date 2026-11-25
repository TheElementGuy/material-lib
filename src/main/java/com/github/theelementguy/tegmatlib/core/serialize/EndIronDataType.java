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

public record EndIronDataType(String modId, String baseName, String humanReadableName, String trimMaterialDescriptionColor, PreItemProperties defaultProperties, float smeltingExperience, ToolArmorConfiguration toolArmorConfig, MapColor mapColor, SoundType soundType, TotalOreConfig oreConfig, InventoryConfiguration inventory, boolean usingHorseArmor, boolean usingNautilusArmor, List<PreLootModifierInfo> lootModifiers, List<ModelException> modelExceptions) implements MaterialConfigSerializable {

	public static final MapCodec<EndIronDataType> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Codec.STRING.fieldOf("mod_id").forGetter(EndIronDataType::modId), Codec.STRING.fieldOf("base_name").forGetter(EndIronDataType::baseName), Codec.STRING.fieldOf("human_readable_name").forGetter(EndIronDataType::humanReadableName), Codec.STRING.fieldOf("trim_material_description_color").forGetter(EndIronDataType::trimMaterialDescriptionColor), PreItemProperties.CODEC.fieldOf("default_properties").forGetter(EndIronDataType::defaultProperties), Codec.FLOAT.fieldOf("smelting_experience").forGetter(EndIronDataType::smeltingExperience), ToolArmorConfiguration.CODEC.fieldOf("tool_armor_config").forGetter(EndIronDataType::toolArmorConfig), EnvironmentCodecs.MAP_COLOR_CODEC.fieldOf("map_color").forGetter(EndIronDataType::mapColor), EnvironmentCodecs.SOUND_TYPE_CODEC.fieldOf("sound_type").forGetter(EndIronDataType::soundType), TotalOreConfig.CODEC.fieldOf("ore_config").forGetter(EndIronDataType::oreConfig), InventoryConfiguration.CODEC.fieldOf("inventory_config").forGetter(EndIronDataType::inventory), Codec.BOOL.fieldOf("using_horse_armor").forGetter(EndIronDataType::usingHorseArmor), Codec.BOOL.fieldOf("using_nautilus_armor").forGetter(EndIronDataType::usingNautilusArmor), PreLootModifierInfo.CODEC.listOf().fieldOf("loot_modifier").forGetter(EndIronDataType::lootModifiers), ModelException.CODEC.listOf().fieldOf("model_exception").forGetter(EndIronDataType::modelExceptions)).apply(instance, EndIronDataType::new));

	@Override
	public MaterialType getType() {
		return MaterialType.IRON;
	}
}
