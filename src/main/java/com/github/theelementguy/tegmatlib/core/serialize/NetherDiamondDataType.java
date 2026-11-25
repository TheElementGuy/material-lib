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

public record NetherDiamondDataType(String modId, String baseName, String humanReadableName, String trimMaterialDescriptionColor, PreItemProperties defaultProperties, float smeltingExperience, ToolArmorConfiguration toolArmorConfig, MapColor mapColor, SoundType soundType, TotalOreConfig oreConfig, InventoryConfiguration inventory, boolean usingHorseArmor, boolean usingNautilusArmor, List<PreLootModifierInfo> lootModifiers, List<ModelException> modelExceptions) implements MaterialConfigSerializable {

	public static final MapCodec<NetherDiamondDataType> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Codec.STRING.fieldOf("mod_id").forGetter(NetherDiamondDataType::modId), Codec.STRING.fieldOf("base_name").forGetter(NetherDiamondDataType::baseName), Codec.STRING.fieldOf("human_readable_name").forGetter(NetherDiamondDataType::humanReadableName), Codec.STRING.fieldOf("trim_material_description_color").forGetter(NetherDiamondDataType::trimMaterialDescriptionColor), PreItemProperties.CODEC.fieldOf("default_properties").forGetter(NetherDiamondDataType::defaultProperties), Codec.FLOAT.fieldOf("smelting_experience").forGetter(NetherDiamondDataType::smeltingExperience), ToolArmorConfiguration.CODEC.fieldOf("tool_armor_config").forGetter(NetherDiamondDataType::toolArmorConfig), EnvironmentCodecs.MAP_COLOR_CODEC.fieldOf("map_color").forGetter(NetherDiamondDataType::mapColor), EnvironmentCodecs.SOUND_TYPE_CODEC.fieldOf("sound_type").forGetter(NetherDiamondDataType::soundType), TotalOreConfig.CODEC.fieldOf("ore_config").forGetter(NetherDiamondDataType::oreConfig), InventoryConfiguration.CODEC.fieldOf("inventory_config").forGetter(NetherDiamondDataType::inventory), Codec.BOOL.fieldOf("using_horse_armor").forGetter(NetherDiamondDataType::usingHorseArmor), Codec.BOOL.fieldOf("using_nautilus_armor").forGetter(NetherDiamondDataType::usingNautilusArmor), PreLootModifierInfo.CODEC.listOf().fieldOf("loot_modifier").forGetter(NetherDiamondDataType::lootModifiers), ModelException.CODEC.listOf().fieldOf("model_exception").forGetter(NetherDiamondDataType::modelExceptions)).apply(instance, NetherDiamondDataType::new));

	@Override
	public MaterialType getType() {
		return MaterialType.IRON;
	}
}
