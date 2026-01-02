package com.github.theelementguy.tegmatlib.data;

import com.github.theelementguy.tegmatlib.core.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import com.github.theelementguy.tegmatlib.core.*;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class TEGMatLibRecipeProvider extends RecipeProvider {

	private Supplier<List<MaterialConfiguration>> MATERIALS;

	private final String MOD_ID;

	protected TEGMatLibRecipeProvider(HolderLookup.Provider registries, RecipeOutput output, Supplier<List<MaterialConfiguration>> materials, String modId) {
		super(registries, output);
		MATERIALS = materials;
		MOD_ID = modId;
	}

	public static class Runner extends RecipeProvider.Runner {

		private Supplier<List<MaterialConfiguration>> MATERIALS;

		private final String NAME;

		private final String MOD_ID;

		public Runner(GatherDataEvent.Client event, String modName, FullyConfiguredMaterialHolder materials) {
			super(event.getGenerator().getPackOutput(), event.getLookupProvider());
			NAME = modName;
			MATERIALS = materials::getMaterials;
			MOD_ID = materials.getModID();
		}

		@Override
		protected @NotNull RecipeProvider createRecipeProvider(HolderLookup.@NotNull Provider registries, @NotNull RecipeOutput output) {
			return new TEGMatLibRecipeProvider(registries, output, MATERIALS, MOD_ID);
		}

		@Override
		public String getName() {
			return NAME + " Recipes";
		}
	}

	@Override
	protected void buildRecipes() {

		for (MaterialConfiguration config : MATERIALS.get()) {

			blockRecipe(config.getBaseItem(), config.getBaseBlock().asItem());

			swordRecipe(config.getSword(), config.getBaseItem(), "has_" + config.getBaseName());
			axeRecipe(config.getAxe(), config.getBaseItem(), "has_" + config.getBaseName());
			pickaxeRecipe(config.getPickaxe(), config.getBaseItem(), "has_" + config.getBaseName());
			shovelRecipe(config.getShovel(), config.getBaseItem(), "has_" + config.getBaseName());
			hoeRecipe(config.getHoe(), config.getBaseItem(), "has_" + config.getBaseName());

			helmetRecipe(config.getHelmet(), config.getBaseItem(), "has_" + config.getBaseName());
			chestplateRecipe(config.getChestplate(), config.getBaseItem(), "has_" + config.getBaseName());
			leggingsRecipe(config.getLeggings(), config.getBaseItem(), "has_" + config.getBaseName());
			bootsRecipe(config.getBoots(), config.getBaseItem(), "has_" + config.getBaseName());

			switch (config.getType()) {
				case IRON -> {
					IronTypeMaterialConfiguration ironConfig = (IronTypeMaterialConfiguration) config;
					blockRecipe(ironConfig.getRawItem(), ironConfig.getRawBlock().asItem());
					nuggetRecipe(ironConfig.getBaseItem(), ironConfig.getNugget());
					allOreSmelting(ironConfig.getBaseItem(), List.of(ironConfig.getOre(), ironConfig.getDeepslateOre(), ironConfig.getRawItem()), ironConfig.getSmeltingExperience(), ironConfig.getBaseName());
				}
				case DIAMOND -> {
					DiamondTypeMaterialConfiguration diamondMatConfig = (DiamondTypeMaterialConfiguration) config;
					allOreSmelting(diamondMatConfig.getBaseItem(), List.of(diamondMatConfig.getOre(), diamondMatConfig.getDeepslateOre()), diamondMatConfig.getSmeltingExperience(), diamondMatConfig.getBaseName());
				}
				case CUBIC_ZIRCONIA -> {
					CubicZirconiaTypeMaterialConfiguration cubicConfig = (CubicZirconiaTypeMaterialConfiguration) config;
					blockRecipe(cubicConfig.getRawItem(), cubicConfig.getRawBlock().asItem());
					allOreSmelting(cubicConfig.getBaseItem(), List.of(cubicConfig.getOre(), cubicConfig.getDeepslateOre(), cubicConfig.getRawItem()), cubicConfig.getSmeltingExperience(), cubicConfig.getBaseName());
				}
				case NETHER_DIAMOND -> {
					NetherDiamondTypeMaterialConfiguration netherDiamondMatConfig = (NetherDiamondTypeMaterialConfiguration) config;
					allOreSmelting(netherDiamondMatConfig.getBaseItem(), List.of(netherDiamondMatConfig.getNetherOre()), netherDiamondMatConfig.getSmeltingExperience(), netherDiamondMatConfig.getBaseName());
				}
				case END_DIAMOND -> {
					EndDiamondTypeMaterialConfiguration endDiamondMatConfig = (EndDiamondTypeMaterialConfiguration) config;
					allOreSmelting(endDiamondMatConfig.getBaseItem(), List.of(endDiamondMatConfig.getEndOre()), endDiamondMatConfig.getSmeltingExperience(), endDiamondMatConfig.getBaseName());
				}
			}

		}

	}

	protected void oreSmelting(List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTIme, String pGroup) {
		oreCooking(RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, pIngredients, pCategory, pResult, pExperience, pCookingTIme, pGroup, "_from_smelting");
	}

	protected void oreBlasting(List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
		oreCooking(RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup, "_from_blasting");
	}

	protected <T extends AbstractCookingRecipe> void oreCooking(RecipeSerializer<T> pCookingSerializer, AbstractCookingRecipe.Factory<T> factory, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
		for(ItemLike itemlike : pIngredients) {
			SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer, factory).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike)).save(this.output, MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
		}
	}

	protected void swordRecipe(Item result, Item ingredient, String advancementName) {
		shaped(RecipeCategory.COMBAT, result).pattern(" I ").pattern(" I ").pattern(" S ").define('I', ingredient).define('S', Items.STICK).unlockedBy(advancementName, has(ingredient)).save(output);
	}

	protected void axeRecipe(Item result, Item ingredient, String advancementName) {
		shaped(RecipeCategory.TOOLS, result).pattern("II ").pattern("IS ").pattern(" S ").define('I', ingredient).define('S', Items.STICK).unlockedBy(advancementName, has(ingredient)).save(output);
	}

	protected void pickaxeRecipe(Item result, Item ingredient, String advancementName) {
		shaped(RecipeCategory.TOOLS, result).pattern("III").pattern(" S ").pattern(" S ").define('I', ingredient).define('S', Items.STICK).unlockedBy(advancementName, has(ingredient)).save(output);
	}

	protected void shovelRecipe(Item result, Item ingredient, String advancementName) {
		shaped(RecipeCategory.TOOLS, result).pattern(" I ").pattern(" S ").pattern(" S ").define('I', ingredient).define('S', Items.STICK).unlockedBy(advancementName, has(ingredient)).save(output);
	}

	protected void hoeRecipe(Item result, Item ingredient, String advancementName) {
		shaped(RecipeCategory.COMBAT, result).pattern("II ").pattern(" S ").pattern(" S ").define('I', ingredient).define('S', Items.STICK).unlockedBy(advancementName, has(ingredient)).save(output);
	}

	protected void helmetRecipe(Item result, Item ingredient, String advancementName) {
		shaped(RecipeCategory.COMBAT, result).pattern("III").pattern("I I").pattern("   ").define('I', ingredient).unlockedBy(advancementName, has(ingredient)).save(output);
	}

	protected void chestplateRecipe(Item result, Item ingredient, String advancementName) {
		shaped(RecipeCategory.COMBAT, result).pattern("I I").pattern("III").pattern("III").define('I', ingredient).unlockedBy(advancementName, has(ingredient)).save(output);
	}

	protected void leggingsRecipe(Item result, Item ingredient, String advancementName) {
		shaped(RecipeCategory.COMBAT, result).pattern("III").pattern("I I").pattern("I I").define('I', ingredient).unlockedBy(advancementName, has(ingredient)).save(output);
	}

	protected void bootsRecipe(Item result, Item ingredient, String advancementName) {
		shaped(RecipeCategory.COMBAT, result).pattern("   ").pattern("I I").pattern("I I").define('I', ingredient).unlockedBy(advancementName, has(ingredient)).save(output);
	}

	protected void blockRecipe(Item material, Item block) {
		shapeless(RecipeCategory.MISC, material, 9).requires(block).unlockedBy("has_" + getItemName(material), has(block)).save(output);

		shapeless(RecipeCategory.MISC, block).requires(material, 9).unlockedBy("has_" + getItemName(material) + "_block", has(block)).save(output);
	}

	protected void nuggetRecipe(Item material, Item nugget) {
		shapeless(RecipeCategory.MISC, material, 9).requires(nugget).unlockedBy("has_" + getItemName(material), has(nugget)).save(output, MOD_ID + ":" + getItemName(material) + "_ingot_from_nugget");

		shapeless(RecipeCategory.MISC, nugget).requires(material, 9).unlockedBy("has_" + getItemName(material) + "_nugget", has(nugget)).save(output);
	}

	protected void allOreSmelting(Item material, List<ItemLike> smeltables, float experience, String group) {
		oreSmelting(smeltables, RecipeCategory.MISC, material, experience, 200, group);
		oreBlasting(smeltables, RecipeCategory.MISC, material, experience, 100, group);
	}
}
