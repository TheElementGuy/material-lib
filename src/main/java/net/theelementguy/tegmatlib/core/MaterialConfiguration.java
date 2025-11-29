package net.theelementguy.tegmatlib.core;

import net.minecraft.Util;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.trim.MaterialAssetGroup;
import net.minecraft.world.item.equipment.trim.TrimMaterial;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.theelementguy.tegmatlib.core.tiers.MiningTier;
import net.theelementguy.tegmatlib.util.TEGMatLibUtil;
import net.theelementguy.tegmatlib.worldgen.BiomeModifierKeyHolder;
import net.theelementguy.tegmatlib.worldgen.OreGenConfigHolder;
import net.theelementguy.tegmatlib.worldgen.ConfiguredFeatureKeyHolder;
import net.theelementguy.tegmatlib.worldgen.PlacedFeatureKeyHolder;

import java.util.EnumMap;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public abstract class MaterialConfiguration {

	//TODO: use given mod ID instead of passed-in one

	protected final String BASE_NAME;
	protected final String MOD_ID;
	protected final String HUMAN_READABLE_NAME;

	protected final Item.Properties DEFAULT_PROPERTIES;

	protected final MaterialType MATERIAL_TYPE;

	protected DeferredItem<Item> BASE_MATERIAL;

	protected DeferredBlock<Block> BLOCK;

	protected DeferredItem<Item> SWORD;
	protected DeferredItem<Item> AXE;
	protected DeferredItem<Item> PICKAXE;
	protected DeferredItem<Item> SHOVEL;
	protected DeferredItem<Item> HOE;
	protected DeferredItem<Item> HELMET;
	protected DeferredItem<Item> CHESTPLATE;
	protected DeferredItem<Item> LEGGINGS;
	protected DeferredItem<Item> BOOTS;

	protected final float SMELTING_EXPERIENCE;

	protected ResourceKey<TrimMaterial> TRIM_MATERIAL;
	protected MaterialAssetGroup MATERIAL_ASSET_GROUP;

	protected final String TRIM_MATERIAL_DESCRIPTION_COLOR;

	protected ToolMaterial TOOL_MATERIAL;
	protected ArmorMaterial ARMOR_MATERIAL;

	protected TagKey<Block> INCORRECT_FOR_MATERIAL;
	protected TagKey<Block> NEEDS_MATERIAL;
	protected TagKey<Item> REPAIRABLES;

	protected ResourceKey<EquipmentAsset> EQUIPMENT_ASSET;

	protected final MapColor MAP_COLOR;
	protected final SoundType SOUND_TYPE;

	protected ConfiguredFeatureKeyHolder CONFIGURED_FEATURE_KEYS;
	protected PlacedFeatureKeyHolder PLACED_FEATURE_KEYS;
	protected BiomeModifierKeyHolder BIOME_MODIFIER_KEYS;
	protected final OreGenConfigHolder ORE_GEN_CONFIGS;

	protected final int DROPS_PER_ORE;
	protected final int EXTRA_DROPS;

	protected final MiningTier TIER;

	public MaterialConfiguration(String modId, String baseName, String humanReadableName, MaterialType materialType, String trimMaterialDescriptionColor, int toolDurability, float speed, float attackDamageBonus, int enchantmentValue, Item.Properties defaultProperties, int armorDurability, int helmetDefense, int chestplateDefense, float smeltingExperience, int leggingsDefense, int bootsDefense, int horseDefense, Holder<SoundEvent> equipSound, float toughness, float knockbackResistance, MapColor mapColor, SoundType soundType, OreGenConfigHolder oreGenConfigs, int dropsPerOre, int extraDrops, MiningTier tier) {
		BASE_NAME = baseName;
		MOD_ID = modId;
		HUMAN_READABLE_NAME = humanReadableName;
		TRIM_MATERIAL_DESCRIPTION_COLOR = trimMaterialDescriptionColor;
		DEFAULT_PROPERTIES = defaultProperties;
		MATERIAL_TYPE = materialType;
		SMELTING_EXPERIENCE = smeltingExperience;
		MAP_COLOR = mapColor;
		SOUND_TYPE = soundType;
		ORE_GEN_CONFIGS = oreGenConfigs;
		DROPS_PER_ORE = dropsPerOre;
		EXTRA_DROPS = extraDrops;
		TIER = tier;
		INCORRECT_FOR_MATERIAL = BlockTags.create(ResourceLocation.fromNamespaceAndPath(MOD_ID, "incorrect_for_" + BASE_NAME + "_tool"));
		NEEDS_MATERIAL = BlockTags.create(ResourceLocation.fromNamespaceAndPath(MOD_ID, "needs_" + BASE_NAME));
		REPAIRABLES = ItemTags.create(ResourceLocation.fromNamespaceAndPath(MOD_ID, BASE_NAME + "_repairables"));
		EQUIPMENT_ASSET = TEGMatLibUtil.createEquipmentAssetResourceKey(BASE_NAME, MOD_ID);
		TOOL_MATERIAL = new ToolMaterial(INCORRECT_FOR_MATERIAL, toolDurability, speed, attackDamageBonus, enchantmentValue, REPAIRABLES);
		ARMOR_MATERIAL = new ArmorMaterial(armorDurability, Util.make(new EnumMap<ArmorType, Integer>(ArmorType.class), attribute -> {
			attribute.put(ArmorType.HELMET, helmetDefense);
			attribute.put(ArmorType.CHESTPLATE, helmetDefense);
			attribute.put(ArmorType.LEGGINGS, leggingsDefense);
			attribute.put(ArmorType.BOOTS, bootsDefense);
			attribute.put(ArmorType.BODY, horseDefense);
		}), enchantmentValue, equipSound, toughness, knockbackResistance, REPAIRABLES, EQUIPMENT_ASSET);
	}

	public String getBaseName() {
		return BASE_NAME;
	}

	public MaterialType getType() {
		return MATERIAL_TYPE;
	}

	public abstract void fillItems(DeferredRegister.Items register, String modId);

	public abstract void fillBlocks(DeferredRegister.Blocks register, Supplier<DeferredRegister.Items> itemsRegister);

	public void fillConfiguredFeatureKeys() {
		CONFIGURED_FEATURE_KEYS = new ConfiguredFeatureKeyHolder((ORE_GEN_CONFIGS.getSmall().isPresent()) ? TEGMatLibUtil.createConfiguredFeatureResourceKey(MOD_ID, "small_" + BASE_NAME) : null, (ORE_GEN_CONFIGS.getMedium().isPresent()) ? TEGMatLibUtil.createConfiguredFeatureResourceKey(MOD_ID, "medium_" + BASE_NAME) : null, (ORE_GEN_CONFIGS.getLarge().isPresent()) ? TEGMatLibUtil.createConfiguredFeatureResourceKey(MOD_ID, "large_" + BASE_NAME) : null, (ORE_GEN_CONFIGS.getExtra().isPresent()) ? TEGMatLibUtil.createConfiguredFeatureResourceKey(MOD_ID, "extra_" + BASE_NAME) : null);
	}

	public void fillPlacedFeatureKeys() {
		PLACED_FEATURE_KEYS = new PlacedFeatureKeyHolder((ORE_GEN_CONFIGS.getSmall().isPresent()) ? TEGMatLibUtil.createPlacedFeatureResourceKey(MOD_ID, "small_" + BASE_NAME + "_ore_placed") : null, (ORE_GEN_CONFIGS.getMedium().isPresent()) ? TEGMatLibUtil.createPlacedFeatureResourceKey(MOD_ID, "medium_" + BASE_NAME + "_ore_placed") : null, (ORE_GEN_CONFIGS.getLarge().isPresent()) ? TEGMatLibUtil.createPlacedFeatureResourceKey(MOD_ID, "large_" + BASE_NAME + "_ore_placed") : null, (ORE_GEN_CONFIGS.getExtra().isPresent()) ? TEGMatLibUtil.createPlacedFeatureResourceKey(MOD_ID, "extra_" + BASE_NAME + "_ore_placed") : null);
	}

	public void fillBiomeModifierKeys() {
		BIOME_MODIFIER_KEYS = new BiomeModifierKeyHolder((ORE_GEN_CONFIGS.getSmall().isPresent()) ? TEGMatLibUtil.createBiomeModifierResourceKey(MOD_ID, "add_" + BASE_NAME + "_small_ore") : null, (ORE_GEN_CONFIGS.getMedium().isPresent()) ? TEGMatLibUtil.createBiomeModifierResourceKey(MOD_ID, "add_" + BASE_NAME + "_medium_ore") : null, (ORE_GEN_CONFIGS.getLarge().isPresent()) ? TEGMatLibUtil.createBiomeModifierResourceKey(MOD_ID, "add_" + BASE_NAME + "_large_ore") : null, (ORE_GEN_CONFIGS.getExtra().isPresent()) ? TEGMatLibUtil.createBiomeModifierResourceKey(MOD_ID, "add_" + BASE_NAME + "_extra_ore") : null);
	}

	public abstract List<OreConfiguration.TargetBlockState> getOreStates();

	public void registerConfiguredFeatures(BootstrapContext<ConfiguredFeature<?, ?>> context) {
		ORE_GEN_CONFIGS.getSmall().ifPresent((oreConfig) -> {oreConfig.registerConfiguredFeature(context, getOreStates(), CONFIGURED_FEATURE_KEYS.getSmallKey().get());});
		ORE_GEN_CONFIGS.getMedium().ifPresent((oreConfig) -> {oreConfig.registerConfiguredFeature(context, getOreStates(), CONFIGURED_FEATURE_KEYS.getMediumKey().get());});
		ORE_GEN_CONFIGS.getLarge().ifPresent((oreConfig) -> {oreConfig.registerConfiguredFeature(context, getOreStates(), CONFIGURED_FEATURE_KEYS.getLargeKey().get());});
		ORE_GEN_CONFIGS.getExtra().ifPresent((oreConfig) -> {oreConfig.registerConfiguredFeature(context, getOreStates(), CONFIGURED_FEATURE_KEYS.getExtraKey().get());});
	}

	public void registerPlacedFeatures(BootstrapContext<PlacedFeature> context) {
		ORE_GEN_CONFIGS.getSmall().ifPresent((oreConfig) -> {oreConfig.registerPlacedFeature(context, PLACED_FEATURE_KEYS.getSmallKey().get(), CONFIGURED_FEATURE_KEYS.getSmallKey().get());});
		ORE_GEN_CONFIGS.getMedium().ifPresent((oreConfig) -> {oreConfig.registerPlacedFeature(context, PLACED_FEATURE_KEYS.getMediumKey().get(), CONFIGURED_FEATURE_KEYS.getMediumKey().get());});
		ORE_GEN_CONFIGS.getLarge().ifPresent((oreConfig) -> {oreConfig.registerPlacedFeature(context, PLACED_FEATURE_KEYS.getLargeKey().get(), CONFIGURED_FEATURE_KEYS.getLargeKey().get());});
		ORE_GEN_CONFIGS.getExtra().ifPresent((oreConfig) -> {oreConfig.registerPlacedFeature(context, PLACED_FEATURE_KEYS.getExtraKey().get(), CONFIGURED_FEATURE_KEYS.getExtraKey().get());});
	}

	public void registerBiomeModifiers(BootstrapContext<BiomeModifier> context) {
		ORE_GEN_CONFIGS.getSmall().ifPresent((oreConfig) -> {oreConfig.registerBiomeModifier(context, BIOME_MODIFIER_KEYS.getSmallKey().get(), PLACED_FEATURE_KEYS.getSmallKey().get());});
		ORE_GEN_CONFIGS.getMedium().ifPresent((oreConfig) -> {oreConfig.registerBiomeModifier(context, BIOME_MODIFIER_KEYS.getMediumKey().get(), PLACED_FEATURE_KEYS.getMediumKey().get());});
		ORE_GEN_CONFIGS.getLarge().ifPresent((oreConfig) -> {oreConfig.registerBiomeModifier(context, BIOME_MODIFIER_KEYS.getLargeKey().get(), PLACED_FEATURE_KEYS.getLargeKey().get());});
		ORE_GEN_CONFIGS.getExtra().ifPresent((oreConfig) -> {oreConfig.registerBiomeModifier(context, BIOME_MODIFIER_KEYS.getExtraKey().get(), PLACED_FEATURE_KEYS.getExtraKey().get());});
	}

	protected DeferredItem<Item> registerSimpleItem(String name, DeferredRegister.Items register, String modId) {
		return register.register(name, () -> new Item(DEFAULT_PROPERTIES.setId(TEGMatLibUtil.createItemResourceKey(name, modId))));
	}

	protected DeferredItem<Item> registerSimpleItemWithTrimMaterial(String name, DeferredRegister.Items register, String modId) {
		return register.register(name, () -> new Item(DEFAULT_PROPERTIES.trimMaterial(TRIM_MATERIAL).setId(TEGMatLibUtil.createItemResourceKey(name, modId))));
	}

	protected DeferredBlock<Block> registerSimpleBlock(String name, DeferredRegister.Blocks register, Supplier<DeferredRegister.Items> itemsRegister, float destroyTime, float explosionResistance, MapColor color, SoundType soundType) {
		DeferredBlock<Block> blockToReturn = register.register(name, () -> new Block(BlockBehaviour.Properties.of().destroyTime(destroyTime).explosionResistance(explosionResistance).mapColor(color).sound(soundType).requiresCorrectToolForDrops().setId(TEGMatLibUtil.createBlockResourceKey(name, MOD_ID))));
		itemsRegister.get().registerSimpleBlockItem(name, blockToReturn, () -> DEFAULT_PROPERTIES);
		return blockToReturn;
	}

	protected DeferredItem<Item> registerSword(DeferredRegister.Items register, String modId) {
		return register.register(BASE_NAME + "_sword", () -> new Item(DEFAULT_PROPERTIES.sword(TOOL_MATERIAL, 3.0f, -2.4f).setId(TEGMatLibUtil.createItemResourceKey(BASE_NAME + "_sword", modId))));
	}

	protected DeferredItem<Item> registerAxe(DeferredRegister.Items register, String modId) {
		return register.register(BASE_NAME + "_axe", () -> new Item(DEFAULT_PROPERTIES.axe(TOOL_MATERIAL, 6.0f, -3.1f).setId(TEGMatLibUtil.createItemResourceKey(BASE_NAME + "_axe", modId))));
	}

	protected DeferredItem<Item> registerPickaxe(DeferredRegister.Items register, String modId) {
		return register.register(BASE_NAME + "_pickaxe", () -> new Item(DEFAULT_PROPERTIES.pickaxe(TOOL_MATERIAL, 1.0f, -2.0f).setId(TEGMatLibUtil.createItemResourceKey(BASE_NAME + "_pickaxe", modId))));
	}

	protected DeferredItem<Item> registerShovel(DeferredRegister.Items register, String modId) {
		return register.register(BASE_NAME + "_shovel", () -> new Item(DEFAULT_PROPERTIES.shovel(TOOL_MATERIAL, 1.5f, -3f).setId(TEGMatLibUtil.createItemResourceKey(BASE_NAME + "_shovel", modId))));
	}

	protected DeferredItem<Item> registerHoe(DeferredRegister.Items register, String modId) {
		return register.register(BASE_NAME + "_hoe", () -> new Item(DEFAULT_PROPERTIES.hoe(TOOL_MATERIAL, -2f, -1f).setId(TEGMatLibUtil.createItemResourceKey(BASE_NAME + "_hoe", modId))));
	}

	protected DeferredItem<Item> registerHelmet(DeferredRegister.Items register) {
		return register.register(BASE_NAME + "_helmet", () -> new Item(DEFAULT_PROPERTIES.humanoidArmor(ARMOR_MATERIAL, ArmorType.HELMET).setId(TEGMatLibUtil.createItemResourceKey(BASE_NAME + "_helmet", MOD_ID))));
	}

	protected DeferredItem<Item> registerChestplate(DeferredRegister.Items register) {
		return register.register(BASE_NAME + "_chestplate", () -> new Item(DEFAULT_PROPERTIES.humanoidArmor(ARMOR_MATERIAL, ArmorType.CHESTPLATE).setId(TEGMatLibUtil.createItemResourceKey(BASE_NAME + "_chestplate", MOD_ID))));
	}

	protected DeferredItem<Item> registerLeggings(DeferredRegister.Items register) {
		return register.register(BASE_NAME + "_leggings", () -> new Item(DEFAULT_PROPERTIES.humanoidArmor(ARMOR_MATERIAL, ArmorType.LEGGINGS).setId(TEGMatLibUtil.createItemResourceKey(BASE_NAME + "_leggings", MOD_ID))));
	}

	protected DeferredItem<Item> registerBoots(DeferredRegister.Items register) {
		return register.register(BASE_NAME + "_boots", () -> new Item(DEFAULT_PROPERTIES.humanoidArmor(ARMOR_MATERIAL, ArmorType.BOOTS).setId(TEGMatLibUtil.createItemResourceKey(BASE_NAME + "_boots", MOD_ID))));
	}

	protected void fillBaseEquipment(DeferredRegister.Items register, String modId) {
		SWORD = registerSword(register, modId);
		AXE = registerAxe(register, modId);
		PICKAXE = registerPickaxe(register, modId);
		SHOVEL = registerShovel(register, modId);
		HOE = registerHoe(register, modId);

		HELMET = registerHelmet(register);
		CHESTPLATE = registerChestplate(register);
		LEGGINGS = registerLeggings(register);
		BOOTS = registerBoots(register);
	}

	protected void fillBaseBlock(DeferredRegister.Blocks register, Supplier<DeferredRegister.Items> itemsRegister) {

		BLOCK = registerSimpleBlock(BASE_NAME + "_block", register, itemsRegister, 5f, 6f, MAP_COLOR, SOUND_TYPE);

	}

	public Block getBaseBlock() {
		return BLOCK.get();
	}

	public MiningTier getMiningLevel() {
		return TIER;
	}

	public TagKey<Block> getIncorrectForMaterial() {
		return INCORRECT_FOR_MATERIAL;
	}

	public TagKey<Block> getNeedsMaterial() {
		return NEEDS_MATERIAL;
	}

	public boolean isSingleOre() {
		return DROPS_PER_ORE == 1;
	}

	public int getMaxDrops() {
		return DROPS_PER_ORE + EXTRA_DROPS;
	}

	public int getBaseDrops() {
		return DROPS_PER_ORE;
	}

	public void bootstrapEquipmentAsset(BiConsumer<ResourceKey<EquipmentAsset>, EquipmentClientInfo> consumer) {
		consumer.accept(EQUIPMENT_ASSET, EquipmentClientInfo.builder().addHumanoidLayers(ResourceLocation.fromNamespaceAndPath(MOD_ID, EQUIPMENT_ASSET.location().getPath())).build());
	}

	public abstract List<Block> getBlocks();

	public Item getSword() {
		return SWORD.get();
	}

	public Item getAxe() {
		return AXE.get();
	}

	public Item getPickaxe() {
		return PICKAXE.get();
	}

	public Item getShovel() {
		return SHOVEL.get();
	}

	public Item getHoe() {
		return HOE.get();
	}

	public TagKey<Item> getRepairables() {
		return REPAIRABLES;
	}

	public Item getHelmet() {
		return HELMET.get();
	}

	public Item getChestplate() {
		return CHESTPLATE.get();
	}

	public Item getLeggings() {
		return LEGGINGS.get();
	}

	public Item getBoots() {
		return BOOTS.get();
	}

	public Item getBaseItem() {
		return BASE_MATERIAL.asItem();
	}

	public void bootstrapTrimMaterial(BootstrapContext<TrimMaterial> context) {
		context.register(TRIM_MATERIAL, new TrimMaterial(MATERIAL_ASSET_GROUP, Component.translatable(Util.makeDescriptionId("trim_material", TRIM_MATERIAL.location())).withStyle(Style.EMPTY.withColor(TextColor.parseColor(TRIM_MATERIAL_DESCRIPTION_COLOR).getOrThrow()))));
	}

	public void fillTrimMaterialKeys() {
		TRIM_MATERIAL = TEGMatLibUtil.createTrimMaterialResourceKey(BASE_NAME, MOD_ID);
		MATERIAL_ASSET_GROUP = MaterialAssetGroup.create(BASE_NAME);
	}

	public MaterialAssetGroup getMaterialAssetGroup() {
		return MATERIAL_ASSET_GROUP;
	}

	public ResourceKey<TrimMaterial> getTrimMaterial() {
		return TRIM_MATERIAL;
	}

	public ResourceKey<EquipmentAsset> getEquipmentAsset() {
		return EQUIPMENT_ASSET;
	}

	public String getHumanReadableName() {
		return HUMAN_READABLE_NAME;
	}

	public float getSmeltingExperience() {
		return SMELTING_EXPERIENCE;
	}

}
