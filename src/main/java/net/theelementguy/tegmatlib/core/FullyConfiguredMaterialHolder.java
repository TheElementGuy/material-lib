package net.theelementguy.tegmatlib.core;

import java.util.List;
import java.util.function.Supplier;

public interface FullyConfiguredMaterialHolder {

	void setMaterialConfiguration(List<Supplier<MaterialConfiguration>> material);

	List<MaterialConfiguration> getMaterials();

}
