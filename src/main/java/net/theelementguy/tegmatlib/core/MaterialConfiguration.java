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
import net.minecraft.world.level.block.RailState;
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
import net.neoforged.neoforge.registries.RegisterEvent;
import net.theelementguy.tegmatlib.core.tiers.MineabilityTier;
import net.theelementguy.tegmatlib.core.tiers.MiningTier;
import net.theelementguy.tegmatlib.util.TEGMatLibUtil;
import net.theelementguy.tegmatlib.worldgen.*;
import net.theelementguy.tegmatlib.worldgen.config.OreGenConfig;

import java.util.EnumMap;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public abstract class MaterialConfiguration {

	//TODO: use given mod ID instead of passed-in one

	protected final String BASE_NAME;
	protected final String MOD_ID;
	protected final String HUMAN_READABLE_NAME;

	protected final Supplier<Item.Properties> DEFAULT_PROPERTIES;

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

	protected Supplier<ResourceKey<TrimMaterial>> TRIM_MATERIAL;
	protected Supplier<MaterialAssetGroup> MATERIAL_ASSET_GROUP;

	protected final String TRIM_MATERIAL_DESCRIPTION_COLOR;

	protected Supplier<ToolMaterial> TOOL_MATERIAL;
	protected Supplier<ArmorMaterial> ARMOR_MATERIAL;

	protected Supplier<TagKey<Block>> INCORRECT_FOR_MATERIAL;
	protected Supplier<TagKey<Block>> NEEDS_MATERIAL;
	protected Supplier<TagKey<Item>> REPAIRABLES;

	protected Supplier<ResourceKey<EquipmentAsset>> EQUIPMENT_ASSET;

	protected final Supplier<MapColor> MAP_COLOR;
	protected final Supplier<SoundType> SOUND_TYPE;

	protected OreGenHolder<ResourceKey<ConfiguredFeature<?, ?>>> CONFIGURED_FEATURE_KEYS;
	protected OreGenHolder<ResourceKey<PlacedFeature>> PLACED_FEATURE_KEYS;
	protected OreGenHolder<ResourceKey<BiomeModifier>> BIOME_MODIFIER_KEYS;
	protected final OreGenHolder<OreGenConfig> ORE_GEN_CONFIGS;

	protected final int DROPS_PER_ORE;
	protected final int EXTRA_DROPS;

	protected final MiningTier TIER;
	protected final MineabilityTier MINEABILITY_TIER;

	protected final String SET_BEFORE;
	protected final Item ITEM_BEFORE;
	protected final Block BLOCK_BEFORE;
	protected final String ORE_BEFORE;

	public MaterialConfiguration(String modId, String baseName, String humanReadableName, MaterialType materialType, String trimMaterialDescriptionColor, int toolDurability, float speed, float attackDamageBonus, int enchantmentValue, Supplier<Item.Properties> defaultProperties, int armorDurability, int helmetDefense, int chestplateDefense, float smeltingExperience, int leggingsDefense, int bootsDefense, int horseDefense, Supplier<Holder<SoundEvent>> equipSound, float toughness, float knockbackResistance, Supplier<MapColor> mapColor, Supplier<SoundType> soundType, OreGenHolder<OreGenConfig> oreGenConfigs, int dropsPerOre, int extraDrops, MiningTier tier, MineabilityTier mineabilityTier, String setBefore, Item itemBefore, Block blockBefore, String oreBefore) {
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
		MINEABILITY_TIER = mineabilityTier;
		SET_BEFORE = setBefore;
		ITEM_BEFORE = itemBefore;
		BLOCK_BEFORE = blockBefore;
		ORE_BEFORE = oreBefore;
		INCORRECT_FOR_MATERIAL = () -> BlockTags.create(ResourceLocation.fromNamespaceAndPath(MOD_ID, "incorrect_for_" + BASE_NAME + "_tool"));
		NEEDS_MATERIAL = () -> BlockTags.create(ResourceLocation.fromNamespaceAndPath(MOD_ID, "needs_" + BASE_NAME));
		REPAIRABLES = () -> ItemTags.create(ResourceLocation.fromNamespaceAndPath(MOD_ID, BASE_NAME + "_repairables"));
		EQUIPMENT_ASSET = () -> TEGMatLibUtil.createEquipmentAssetResourceKey(BASE_NAME, MOD_ID);
		TOOL_MATERIAL = () -> new ToolMaterial(INCORRECT_FOR_MATERIAL.get(), toolDurability, speed, attackDamageBonus, enchantmentValue, REPAIRABLES.get());
		ARMOR_MATERIAL = () -> new ArmorMaterial(armorDurability, Util.make(new EnumMap<ArmorType, Integer>(ArmorType.class), attribute -> {
			attribute.put(ArmorType.HELMET, helmetDefense);
			attribute.put(ArmorType.CHESTPLATE, chestplateDefense);
			attribute.put(ArmorType.LEGGINGS, leggingsDefense);
			attribute.put(ArmorType.BOOTS, bootsDefense);
			attribute.put(ArmorType.BODY, horseDefense);
		}), enchantmentValue, equipSound.get(), toughness, knockbackResistance, REPAIRABLES.get(), EQUIPMENT_ASSET.get());
		fillTrimMaterialKeys();
		fillConfiguredFeatureKeys();
		fillPlacedFeatureKeys();
		fillBiomeModifierKeys();
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
		CONFIGURED_FEATURE_KEYS = new OreGenHolder<ResourceKey<ConfiguredFeature<?, ?>>>((ORE_GEN_CONFIGS.hasSmall()) ? () -> TEGMatLibUtil.createConfiguredFeatureResourceKey(MOD_ID, "small_" + BASE_NAME) : null, (ORE_GEN_CONFIGS.hasMedium()) ? () -> TEGMatLibUtil.createConfiguredFeatureResourceKey(MOD_ID, "medium_" + BASE_NAME) : null, (ORE_GEN_CONFIGS.hasLarge()) ? () -> TEGMatLibUtil.createConfiguredFeatureResourceKey(MOD_ID, "large_" + BASE_NAME) : null, (ORE_GEN_CONFIGS.hasExtra()) ? () -> TEGMatLibUtil.createConfiguredFeatureResourceKey(MOD_ID, "extra_" + BASE_NAME) : null);
	}

	public void fillPlacedFeatureKeys() {
		PLACED_FEATURE_KEYS = new OreGenHolder<>((ORE_GEN_CONFIGS.hasSmall()) ? () -> TEGMatLibUtil.createPlacedFeatureResourceKey(MOD_ID, "small_" + BASE_NAME + "_ore_placed") : null, (ORE_GEN_CONFIGS.hasMedium()) ? () -> TEGMatLibUtil.createPlacedFeatureResourceKey(MOD_ID, "medium_" + BASE_NAME + "_ore_placed") : null, (ORE_GEN_CONFIGS.hasLarge()) ? () -> TEGMatLibUtil.createPlacedFeatureResourceKey(MOD_ID, "large_" + BASE_NAME + "_ore_placed") : null, (ORE_GEN_CONFIGS.hasExtra()) ? () -> TEGMatLibUtil.createPlacedFeatureResourceKey(MOD_ID, "extra_" + BASE_NAME + "_ore_placed") : null);
	}

	public void fillBiomeModifierKeys() {
		BIOME_MODIFIER_KEYS = new OreGenHolder<>((ORE_GEN_CONFIGS.hasSmall()) ? () -> TEGMatLibUtil.createBiomeModifierResourceKey(MOD_ID, "add_" + BASE_NAME + "_small_ore") : null, (ORE_GEN_CONFIGS.hasMedium()) ? () -> TEGMatLibUtil.createBiomeModifierResourceKey(MOD_ID, "add_" + BASE_NAME + "_medium_ore") : null, (ORE_GEN_CONFIGS.hasLarge()) ? () -> TEGMatLibUtil.createBiomeModifierResourceKey(MOD_ID, "add_" + BASE_NAME + "_large_ore") : null, (ORE_GEN_CONFIGS.hasExtra()) ? () -> TEGMatLibUtil.createBiomeModifierResourceKey(MOD_ID, "add_" + BASE_NAME + "_extra_ore") : null);
	}

	public abstract List<OreConfiguration.TargetBlockState> getOreStates();

	public void registerConfiguredFeatures(BootstrapContext<ConfiguredFeature<?, ?>> context) {
		ORE_GEN_CONFIGS.getSmall().ifPresent((oreConfig) -> {oreConfig.registerConfiguredFeature(context, getOreStates(), CONFIGURED_FEATURE_KEYS.getSmall().get());});
		ORE_GEN_CONFIGS.getMedium().ifPresent((oreConfig) -> {oreConfig.registerConfiguredFeature(context, getOreStates(), CONFIGURED_FEATURE_KEYS.getMedium().get());});
		ORE_GEN_CONFIGS.getLarge().ifPresent((oreConfig) -> {oreConfig.registerConfiguredFeature(context, getOreStates(), CONFIGURED_FEATURE_KEYS.getLarge().get());});
		ORE_GEN_CONFIGS.getExtra().ifPresent((oreConfig) -> {oreConfig.registerConfiguredFeature(context, getOreStates(), CONFIGURED_FEATURE_KEYS.getExtra().get());});
	}

	public void registerPlacedFeatures(BootstrapContext<PlacedFeature> context) {
		ORE_GEN_CONFIGS.getSmall().ifPresent((oreConfig) -> {oreConfig.registerPlacedFeature(context, PLACED_FEATURE_KEYS.getSmall().get(), CONFIGURED_FEATURE_KEYS.getSmall().get());});
		ORE_GEN_CONFIGS.getMedium().ifPresent((oreConfig) -> {oreConfig.registerPlacedFeature(context, PLACED_FEATURE_KEYS.getMedium().get(), CONFIGURED_FEATURE_KEYS.getMedium().get());});
		ORE_GEN_CONFIGS.getLarge().ifPresent((oreConfig) -> {oreConfig.registerPlacedFeature(context, PLACED_FEATURE_KEYS.getLarge().get(), CONFIGURED_FEATURE_KEYS.getLarge().get());});
		ORE_GEN_CONFIGS.getExtra().ifPresent((oreConfig) -> {oreConfig.registerPlacedFeature(context, PLACED_FEATURE_KEYS.getExtra().get(), CONFIGURED_FEATURE_KEYS.getExtra().get());});
	}

	public void registerBiomeModifiers(BootstrapContext<BiomeModifier> context) {
		ORE_GEN_CONFIGS.getSmall().ifPresent((oreConfig) -> {oreConfig.registerBiomeModifier(context, BIOME_MODIFIER_KEYS.getSmall().get(), PLACED_FEATURE_KEYS.getSmall().get());});
		ORE_GEN_CONFIGS.getMedium().ifPresent((oreConfig) -> {oreConfig.registerBiomeModifier(context, BIOME_MODIFIER_KEYS.getMedium().get(), PLACED_FEATURE_KEYS.getMedium().get());});
		ORE_GEN_CONFIGS.getLarge().ifPresent((oreConfig) -> {oreConfig.registerBiomeModifier(context, BIOME_MODIFIER_KEYS.getLarge().get(), PLACED_FEATURE_KEYS.getLarge().get());});
		ORE_GEN_CONFIGS.getExtra().ifPresent((oreConfig) -> {oreConfig.registerBiomeModifier(context, BIOME_MODIFIER_KEYS.getExtra().get(), PLACED_FEATURE_KEYS.getExtra().get());});
	}

	protected DeferredItem<Item> registerSimpleItem(String name, DeferredRegister.Items register, String modId) {
		return register.register(name, () -> new Item(DEFAULT_PROPERTIES.get().setId(TEGMatLibUtil.createItemResourceKey(name, modId))));
	}

	protected DeferredItem<Item> registerSimpleItemWithTrimMaterial(String name, DeferredRegister.Items register, String modId) {
		return register.register(name, () -> new Item(DEFAULT_PROPERTIES.get().trimMaterial(TRIM_MATERIAL.get()).setId(TEGMatLibUtil.createItemResourceKey(name, modId))));
	}

	protected DeferredBlock<Block> registerSimpleBlock(String name, DeferredRegister.Blocks register, Supplier<DeferredRegister.Items> itemsRegister, float destroyTime, float explosionResistance, MapColor color, SoundType soundType) {
		DeferredBlock<Block> blockToReturn = register.register(name, () -> new Block(BlockBehaviour.Properties.of().destroyTime(destroyTime).explosionResistance(explosionResistance).mapColor(color).sound(soundType).requiresCorrectToolForDrops().setId(TEGMatLibUtil.createBlockResourceKey(name, MOD_ID))));
		itemsRegister.get().registerSimpleBlockItem(name, blockToReturn, DEFAULT_PROPERTIES);
		return blockToReturn;
	}

	protected DeferredItem<Item> registerSword(DeferredRegister.Items register, String modId) {
		return register.register(BASE_NAME + "_sword", () -> new Item(DEFAULT_PROPERTIES.get().sword(TOOL_MATERIAL.get(), 3.0f, -2.4f).setId(TEGMatLibUtil.createItemResourceKey(BASE_NAME + "_sword", modId))));
	}

	protected DeferredItem<Item> registerAxe(DeferredRegister.Items register, String modId) {
		return register.register(BASE_NAME + "_axe", () -> new Item(DEFAULT_PROPERTIES.get().axe(TOOL_MATERIAL.get(), 6.0f, -3.1f).setId(TEGMatLibUtil.createItemResourceKey(BASE_NAME + "_axe", modId))));
	}

	protected DeferredItem<Item> registerPickaxe(DeferredRegister.Items register, String modId) {
		return register.register(BASE_NAME + "_pickaxe", () -> new Item(DEFAULT_PROPERTIES.get().pickaxe(TOOL_MATERIAL.get(), 1.0f, -2.0f).setId(TEGMatLibUtil.createItemResourceKey(BASE_NAME + "_pickaxe", modId))));
	}

	protected DeferredItem<Item> registerShovel(DeferredRegister.Items register, String modId) {
		return register.register(BASE_NAME + "_shovel", () -> new Item(DEFAULT_PROPERTIES.get().shovel(TOOL_MATERIAL.get(), 1.5f, -3f).setId(TEGMatLibUtil.createItemResourceKey(BASE_NAME + "_shovel", modId))));
	}

	protected DeferredItem<Item> registerHoe(DeferredRegister.Items register, String modId) {
		return register.register(BASE_NAME + "_hoe", () -> new Item(DEFAULT_PROPERTIES.get().hoe(TOOL_MATERIAL.get(), -2f, -1f).setId(TEGMatLibUtil.createItemResourceKey(BASE_NAME + "_hoe", modId))));
	}

	protected DeferredItem<Item> registerHelmet(DeferredRegister.Items register) {
		return register.register(BASE_NAME + "_helmet", () -> new Item(DEFAULT_PROPERTIES.get().humanoidArmor(ARMOR_MATERIAL.get(), ArmorType.HELMET).setId(TEGMatLibUtil.createItemResourceKey(BASE_NAME + "_helmet", MOD_ID))));
	}

	protected DeferredItem<Item> registerChestplate(DeferredRegister.Items register) {
		return register.register(BASE_NAME + "_chestplate", () -> new Item(DEFAULT_PROPERTIES.get().humanoidArmor(ARMOR_MATERIAL.get(), ArmorType.CHESTPLATE).setId(TEGMatLibUtil.createItemResourceKey(BASE_NAME + "_chestplate", MOD_ID))));
	}

	protected DeferredItem<Item> registerLeggings(DeferredRegister.Items register) {
		return register.register(BASE_NAME + "_leggings", () -> new Item(DEFAULT_PROPERTIES.get().humanoidArmor(ARMOR_MATERIAL.get(), ArmorType.LEGGINGS).setId(TEGMatLibUtil.createItemResourceKey(BASE_NAME + "_leggings", MOD_ID))));
	}

	protected DeferredItem<Item> registerBoots(DeferredRegister.Items register) {
		return register.register(BASE_NAME + "_boots", () -> new Item(DEFAULT_PROPERTIES.get().humanoidArmor(ARMOR_MATERIAL.get(), ArmorType.BOOTS).setId(TEGMatLibUtil.createItemResourceKey(BASE_NAME + "_boots", MOD_ID))));
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

		BLOCK = registerSimpleBlock(BASE_NAME + "_block", register, itemsRegister, 5f, 6f, MAP_COLOR.get(), SOUND_TYPE.get());

	}

	public Block getBaseBlock() {
		return BLOCK.get();
	}

	public MiningTier getMiningLevel() {
		return TIER;
	}

	public TagKey<Block> getIncorrectForMaterial() {
		return INCORRECT_FOR_MATERIAL.get();
	}

	public TagKey<Block> getNeedsMaterial() {
		return NEEDS_MATERIAL.get();
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
		consumer.accept(EQUIPMENT_ASSET.get(), EquipmentClientInfo.builder().addHumanoidLayers(ResourceLocation.fromNamespaceAndPath(MOD_ID, EQUIPMENT_ASSET.get().location().getPath())).build());
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
		return REPAIRABLES.get();
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
		context.register(TRIM_MATERIAL.get(), new TrimMaterial(MATERIAL_ASSET_GROUP.get(), Component.translatable(Util.makeDescriptionId("trim_material", TRIM_MATERIAL.get().location())).withStyle(Style.EMPTY.withColor(TextColor.parseColor(TRIM_MATERIAL_DESCRIPTION_COLOR).getOrThrow()))));
	}

	public void fillTrimMaterialKeys() {
		TRIM_MATERIAL = () -> TEGMatLibUtil.createTrimMaterialResourceKey(BASE_NAME, MOD_ID);
		MATERIAL_ASSET_GROUP = () -> MaterialAssetGroup.create(BASE_NAME);
	}

	public MaterialAssetGroup getMaterialAssetGroup() {
		return MATERIAL_ASSET_GROUP.get();
	}

	public ResourceKey<TrimMaterial> getTrimMaterial() {
		return TRIM_MATERIAL.get();
	}

	public ResourceKey<EquipmentAsset> getEquipmentAsset() {
		return EQUIPMENT_ASSET.get();
	}

	public String getHumanReadableName() {
		return HUMAN_READABLE_NAME;
	}

	public float getSmeltingExperience() {
		return SMELTING_EXPERIENCE;
	}

	public String getSetBefore() {
		return SET_BEFORE;
	}

	public Item getItemBefore() {
		return ITEM_BEFORE;
	}

	public Block getBlockBefore() {
		return BLOCK_BEFORE;
	}

	public String getOreBefore() {
		return ORE_BEFORE;
	}

	public MineabilityTier getMineabilityLevel() {
		return MINEABILITY_TIER;
	}
}
