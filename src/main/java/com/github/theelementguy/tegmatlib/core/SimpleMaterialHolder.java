package com.github.theelementguy.tegmatlib.core;

import java.util.List;
import java.util.function.Supplier;

public class SimpleMaterialHolder implements FullyConfiguredMaterialHolder {

	private final String MOD_ID;

	private List<Supplier<MaterialConfiguration>> MATERIALS;

	@SafeVarargs
	public SimpleMaterialHolder(String modID, Supplier<MaterialConfiguration>... materials) {
		MATERIALS = List.of(materials);
		MOD_ID = modID;
	}

	@Override
	public void setMaterialConfiguration(List<Supplier<MaterialConfiguration>> material) {
		MATERIALS = material;
	}

	@Override
	public List<MaterialConfiguration> getMaterials() {
		return MATERIALS.stream().map(Supplier::get).toList();
	}

	@Override
	public String getModID() {
		return MOD_ID;
	}
}
