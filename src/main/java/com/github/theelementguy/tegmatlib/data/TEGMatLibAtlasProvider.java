package com.github.theelementguy.tegmatlib.data;

import com.github.theelementguy.tegmatlib.core.FullyConfiguredMaterialHolder;
import com.github.theelementguy.tegmatlib.core.MaterialConfiguration;
import net.minecraft.client.renderer.texture.atlas.sources.DirectoryLister;
import net.minecraft.client.renderer.texture.atlas.sources.PalettedPermutations;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.AtlasIds;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.client.data.SpriteSourceProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class TEGMatLibAtlasProvider extends SpriteSourceProvider {

	private final List<MaterialConfiguration> MATERIALS;

	public TEGMatLibAtlasProvider(GatherDataEvent.Client event, FullyConfiguredMaterialHolder materials) {
		super(event.getGenerator().getPackOutput(), event.getWorldLookupProvider(), materials.getModID());
		MATERIALS = materials.getMaterials();
	}

	@Override
	protected void gather() {
		HashMap<String, Identifier> permutations = new HashMap<>();

		putMinecraftPermutation("amethyst", permutations);
		putMinecraftPermutation("emerald", permutations);
		putMinecraftPermutation("lapis", permutations);
		putMinecraftPermutation("quartz", permutations);
		putMinecraftPermutation("redstone", permutations);
		putMinecraftPermutation("resin", permutations);
		putMinecraftPermutationDarker("copper", permutations);
		putMinecraftPermutationDarker("diamond", permutations);
		putMinecraftPermutationDarker("gold", permutations);
		putMinecraftPermutationDarker("iron", permutations);
		putMinecraftPermutationDarker("netherite", permutations);

		for (MaterialConfiguration config : MATERIALS) {
			permutations.putIfAbsent(config.getBaseName(), Identifier.fromNamespaceAndPath(this.modid, "trim/" + config.getBaseName()));
			permutations.putIfAbsent(config.getBaseName() + "_darker", Identifier.fromNamespaceAndPath(this.modid, "trim/" + config.getBaseName() + "_darker"));
		}

		atlas(AtlasIds.ITEMS).addSource(new DirectoryLister("item", "/item")).addSource(new PalettedPermutations(List.of(Identifier.withDefaultNamespace("trims/items/helmet_trim"), Identifier.withDefaultNamespace("trims/items/helmet_trim"), Identifier.withDefaultNamespace("trims/items/helmet_trim"), Identifier.withDefaultNamespace("trims/items/helmet_trim")), Identifier.withDefaultNamespace("trim_base"), permutations));
	}

	private void putMinecraftPermutation(String name, Map<String, Identifier> toPut) {
		toPut.putIfAbsent(name, Identifier.withDefaultNamespace("trim/" + name));
	}

	private void putMinecraftPermutationDarker(String name, Map<String, Identifier> toPut) {
		putMinecraftPermutation(name, toPut);
		putMinecraftPermutation(name + "_darker", toPut);
	}
}
