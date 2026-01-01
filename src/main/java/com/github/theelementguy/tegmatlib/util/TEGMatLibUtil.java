package com.github.theelementguy.tegmatlib.util;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.minecraft.world.item.equipment.trim.TrimMaterial;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import com.github.theelementguy.tegmatlib.core.tiers.MineabilityTier;
import com.github.theelementguy.tegmatlib.core.tiers.MiningTier;

import java.util.*;

public class TEGMatLibUtil {

	private static HashMap<Character, Character> upsideDown = new HashMap<>(mapOf('a', 'ɐ', 'b', 'q', 'c', 'ɔ', 'd', 'p', 'e', 'ǝ', 'f', 'ɟ', 'g', 'ᵷ', 'h', 'ɥ', 'i', 'ᴉ', 'k', 'ʞ', 'C', 'Ɔ', 'u', 'n', 'n', 'u', ' ', ' ', 'z', 'z', 'Z', 'Z', 'q', 'b', 'p', 'd', 'r', 'ɹ', 'o', 'o', 'l', 'l', 's', 's', 'R', 'ᴚ', 'S', 'S', 'B', 'ᗺ', 'A', 'Ɐ', 'w', 'ʍ', 'W', 'M', 'x', 'x', 'P', 'Ԁ', 'T', '⟘', 'H', 'H', 'v', 'ʌ', 'D', 'ᗡ', 'N', 'N', 'E', 'Ǝ', 'O', 'O', 'm', 'ɯ', 't', 'ʇ', 'L', 'Ꞁ', 'I', 'I'));

	public static ResourceKey<Item> createItemResourceKey(String name, String modId) {
		return ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(modId, name));
	}

	public static ResourceKey<Block> createBlockResourceKey(String name, String modId) {
		return ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(modId, name));
	}

	public static ResourceKey<Recipe<?>> createRecipeResourceKey(String name, String modId) {
		return ResourceKey.create(Registries.RECIPE, ResourceLocation.fromNamespaceAndPath(modId, name));
	}

	public static ResourceKey<EquipmentAsset> createEquipmentAssetResourceKey(String name, String modId) {
		return ResourceKey.create(ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath("minecraft", "equipment_asset")), ResourceLocation.fromNamespaceAndPath(modId, name));
	}

	public static void inventoryAddAfter(Item item, Item referenceItem, BuildCreativeModeTabContentsEvent event) {
		event.insertAfter(new ItemStack(referenceItem, 1), new ItemStack(item, 1), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
	}

	public static void inventoryAddAfter(DeferredItem<? extends Item> item, Item referenceItem, BuildCreativeModeTabContentsEvent event) {
		event.insertAfter(new ItemStack(referenceItem, 1), new ItemStack(item.get(), 1), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
	}

	public static void inventoryAddAfter(DeferredItem<? extends Item> item, DeferredItem<? extends Item> referenceItem, BuildCreativeModeTabContentsEvent event) {
		event.insertAfter(new ItemStack(referenceItem.get(), 1), new ItemStack(item.get(), 1), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
	}

	public static void inventoryAddAfter(Block item, Block referenceItem, BuildCreativeModeTabContentsEvent event) {
		event.insertAfter(new ItemStack(referenceItem, 1), new ItemStack(item, 1), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
	}

	public static void inventoryAddAfter(DeferredBlock<? extends Block> item, Block referenceItem, BuildCreativeModeTabContentsEvent event) {
		event.insertAfter(new ItemStack(referenceItem, 1), new ItemStack(item, 1), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
	}

	public static void inventoryAddAfter(DeferredBlock<? extends Block> item, DeferredBlock<? extends Block> referenceItem, BuildCreativeModeTabContentsEvent event) {
		event.insertAfter(new ItemStack(referenceItem, 1), new ItemStack(item, 1), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
	}

	public static void setAddAfter(String set, String tools, String armor, BuildCreativeModeTabContentsEvent event, String modId) {
		TEGMatLibUtil.inventoryAddAfter(TEGMatLibUtil.getItemFromKey(set + "_sword", modId), TEGMatLibUtil.getItemFromKey(tools + "_sword", modId), event);
		TEGMatLibUtil.inventoryAddAfter(TEGMatLibUtil.getItemFromKey(set + "_axe", modId), TEGMatLibUtil.getItemFromKey(tools + "_axe", modId), event);
		TEGMatLibUtil.inventoryAddAfter(TEGMatLibUtil.getItemFromKey(set + "_helmet", modId), TEGMatLibUtil.getItemFromKey(armor + "_boots", modId), event);
		TEGMatLibUtil.inventoryAddAfter(TEGMatLibUtil.getItemFromKey(set + "_chestplate", modId), TEGMatLibUtil.getItemFromKey(set + "_helmet", modId), event);
		TEGMatLibUtil.inventoryAddAfter(TEGMatLibUtil.getItemFromKey(set + "_leggings", modId), TEGMatLibUtil.getItemFromKey(set + "_chestplate", modId), event);
		TEGMatLibUtil.inventoryAddAfter(TEGMatLibUtil.getItemFromKey(set + "_boots", modId), TEGMatLibUtil.getItemFromKey(set + "_leggings", modId), event);
	}

	public static void toolsAddAfter(String set, String begin, BuildCreativeModeTabContentsEvent event, String modId) {
		TEGMatLibUtil.inventoryAddAfter(TEGMatLibUtil.getItemFromKey(set + "_axe", modId), TEGMatLibUtil.getItemFromKey(begin + "_hoe", modId), event);
		TEGMatLibUtil.inventoryAddAfter(TEGMatLibUtil.getItemFromKey(set + "_pickaxe", modId), TEGMatLibUtil.getItemFromKey(set + "_axe", modId), event);
		TEGMatLibUtil.inventoryAddAfter(TEGMatLibUtil.getItemFromKey(set + "_shovel", modId), TEGMatLibUtil.getItemFromKey(set + "_pickaxe", modId), event);
		TEGMatLibUtil.inventoryAddAfter(TEGMatLibUtil.getItemFromKey(set + "_hoe", modId), TEGMatLibUtil.getItemFromKey(set + "_shovel", modId), event);
	}

	public static Item getItemFromKey(String key, String modId) {

		if (BuiltInRegistries.ITEM.get(TEGMatLibUtil.createItemResourceKey(key, modId)).isEmpty()) {
			return getItemFromKeyMinecraft(key);
		}

		return BuiltInRegistries.ITEM.get(TEGMatLibUtil.createItemResourceKey(key, modId)).get().value();

	}

	public static Item getItemFromKeyMinecraft(String key) {
		return BuiltInRegistries.ITEM.get(ResourceKey.create(Registries.ITEM, ResourceLocation.withDefaultNamespace(key))).get().value();
	}

	public static Block getBlockFromKey(String key, String modId) {
		if (BuiltInRegistries.BLOCK.get(TEGMatLibUtil.createBlockResourceKey(key, modId)).isEmpty()) {
			return getBlockFromKey(key, "minecraft");
		}
		return BuiltInRegistries.BLOCK.get(TEGMatLibUtil.createBlockResourceKey(key, modId)).get().value();
	}

	public static EquipmentAsset getMaterialAssetGroupFromKey(String key, String modId) {
		Registry<EquipmentAsset> registry = (Registry<EquipmentAsset>) BuiltInRegistries.REGISTRY.get(EquipmentAssets.ROOT_ID.registry()).get().value();
		return registry.get(TEGMatLibUtil.createEquipmentAssetResourceKey(key, modId)).get().value();
	}

	public static String toUpsideDown(String given) {

		ArrayList<Character> charList = new ArrayList<>(given.length());

		for (char c : given.toCharArray()) {
			charList.add(c);
		}

		int i = 0;

		for (Character c : charList) {
			if (upsideDown.containsKey(c)) {
				charList.set(i, upsideDown.get(c));
			} else {
				throw new NoSuchElementException("No upside down equivalent for: " + c);
			}
			i++;
		}

		StringBuilder b = new StringBuilder();

		Collections.reverse(charList);

		charList.forEach(b::append);

		return b.toString();
	}

	public static <K, V> Map<K, V> mapOf(Object... things) {

		ArrayList<Object> temp = new ArrayList<>(2);
		HashMap<K, V> toReturn = new HashMap<>(things.length / 2);

		for (int i = 0; i < things.length; i++) {
			if (((i % 2) == 1)) {
				temp.add(things[i]);
				toReturn.put((K) temp.get(0), (V) temp.get(1));
				temp.clear();
			} else {
				temp.add(things[i]);
			}
		}

		return toReturn;

	}

	public static ResourceKey<ConfiguredFeature<?, ?>> createConfiguredFeatureResourceKey(String modId, String name) {
		return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(modId, name));
	}

	public static ResourceKey<PlacedFeature> createPlacedFeatureResourceKey(String modId, String name) {
		return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(modId, name));
	}

	public static ResourceKey<BiomeModifier> createBiomeModifierResourceKey(String modId, String name) {
		return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(modId, name));
	}

	public static TagKey<Block> getTagForTierUnder(MiningTier tier) {
		return switch (tier) {
			case WOOD -> {throw new IllegalArgumentException("Mining level of wood not permitted. Such a material does not need to be mined with a pickaxe.");}
			case STONE -> Tags.Blocks.NEEDS_WOOD_TOOL;
			case IRON -> BlockTags.NEEDS_STONE_TOOL;
			case DIAMOND -> BlockTags.NEEDS_IRON_TOOL;
			case NETHERITE -> BlockTags.NEEDS_DIAMOND_TOOL;
			case BEYOND_NETHERITE -> Tags.Blocks.NEEDS_NETHERITE_TOOL;
		};
	}

	public static TagKey<Block> getTagForTierIncorrect(MiningTier tier) {
		return switch (tier) {
			case WOOD -> BlockTags.INCORRECT_FOR_WOODEN_TOOL;
			case STONE -> BlockTags.INCORRECT_FOR_STONE_TOOL;
			case IRON -> BlockTags.INCORRECT_FOR_IRON_TOOL;
			case DIAMOND -> BlockTags.INCORRECT_FOR_DIAMOND_TOOL;
			case NETHERITE -> BlockTags.INCORRECT_FOR_NETHERITE_TOOL;
			case BEYOND_NETHERITE -> {throw new IllegalArgumentException("Mining level of beyond netherite not permitted. Such a material should be incorrect for netherite and remove needs for that level.");}
		};
	}

	public static TagKey<Block> getTagForTierNeeds(MiningTier tier) {
		return switch (tier) {
			case WOOD -> Tags.Blocks.NEEDS_WOOD_TOOL;
			case STONE -> BlockTags.NEEDS_STONE_TOOL;
			case IRON -> BlockTags.NEEDS_IRON_TOOL;
			case DIAMOND -> BlockTags.NEEDS_DIAMOND_TOOL;
			case NETHERITE -> Tags.Blocks.NEEDS_NETHERITE_TOOL;
			case BEYOND_NETHERITE -> {throw new IllegalArgumentException("Mining level of beyond netherite not permitted. Such a material should be incorrect for netherite and remove needs for that level.");}
		};
	}

	public static MineabilityTier getMineability(MiningTier tier) {
		return switch (tier) {
			case WOOD -> MineabilityTier.ALL;
			case STONE -> MineabilityTier.WOOD;
			case IRON -> MineabilityTier.STONE;
			case DIAMOND -> MineabilityTier.IRON;
			case NETHERITE -> MineabilityTier.DIAMOND;
			case BEYOND_NETHERITE -> MineabilityTier.NETHERITE;
		};
	}

	public static TagKey<Block> getNeedsTagForMineability(MineabilityTier tier) {
		return switch (tier) {
			case ALL -> {throw new IllegalArgumentException("All not permitted here. There is no minimum.");}
			case DEFAULT -> {throw new IllegalArgumentException("Default not permitted here. Input should be resolved.");}
			case WOOD -> Tags.Blocks.NEEDS_WOOD_TOOL;
			case STONE -> BlockTags.NEEDS_STONE_TOOL;
			case IRON -> BlockTags.NEEDS_IRON_TOOL;
			case DIAMOND -> BlockTags.NEEDS_DIAMOND_TOOL;
			case NETHERITE -> Tags.Blocks.NEEDS_NETHERITE_TOOL;
		};
	}

	public static ResourceKey<TrimMaterial> createTrimMaterialResourceKey(String name, String modId) {
		return ResourceKey.create(Registries.TRIM_MATERIAL, ResourceLocation.fromNamespaceAndPath(modId, name));
	}

}
