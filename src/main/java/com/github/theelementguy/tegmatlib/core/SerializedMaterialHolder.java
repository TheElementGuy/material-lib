package com.github.theelementguy.tegmatlib.core;

import com.github.theelementguy.tegmatlib.core.serialize.MaterialConfigSerializable;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.JsonOps;
import net.neoforged.fml.ModList;
import net.neoforged.neoforgespi.language.IModFileInfo;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class SerializedMaterialHolder implements FullyConfiguredMaterialHolder {

	private final Logger LOGGER = LogUtils.getLogger();

	private final String modID;

	private List<Supplier<MaterialConfiguration>> materials;
	private final List<String> configIDOrder;

	public SerializedMaterialHolder(String modID) {
		this.modID = modID;
		this.materials = new ArrayList<>();
		fill();
		configIDOrder = this.materials.stream().map(config -> config.get().getBaseName()).toList();
	}

	@Override
	public void setMaterialConfiguration(List<Supplier<MaterialConfiguration>> material) {
		ArrayList<Supplier<MaterialConfiguration>> temp = new ArrayList<>();

		for (String s : configIDOrder) {
			temp.add(material.stream().filter(config -> config.get().getBaseName().equals(s)).findFirst().orElseThrow(() -> new IllegalStateException("Lost material configuration: " + s)));
		}

		this.materials = temp;
	}

	@Override
	public List<MaterialConfiguration> getMaterials() {
		return materials.stream().map(Supplier::get).toList();
	}

	@Override
	public String getModID() {
		return modID;
	}

	private void fill() {
		LOGGER.info("Searching for material configurations.");
		IModFileInfo mod = ModList.get().getModFileById(modID);
		mod.getFile().getContents().findFile("data/" + modID + "/material_lib/config/index.json").map(Path::of).ifPresent(path -> {
			LOGGER.info("Found index file.");
			JsonArray materialIndexArray;
			try (Reader reader = Files.newBufferedReader(path)) {
				materialIndexArray = JsonParser.parseReader(reader).getAsJsonArray();
				LOGGER.info("Successfully read index file.");
				for (Object o : materialIndexArray) {
					if (o instanceof String s) {
						LOGGER.info("Reading from config: {}", s);
						mod.getFile().getContents().findFile("data/" + modID + "/material_lib/config/" + s + ".json").map(Path::of).ifPresent(configPath -> {

							try (Reader configReader = Files.newBufferedReader(configPath)) {
								JsonElement rawConfig = JsonParser.parseReader(configReader);
								MaterialConfigSerializable serialized = MaterialConfigSerializable.CODEC.parse(JsonOps.INSTANCE, rawConfig).getOrThrow((error) -> new IllegalArgumentException("Error when reading material config: " + s + ": " + error));

								materials.add(() -> MaterialConfigurationSerializedConverter.convert(serialized));
							} catch (IOException e) {
								throw new RuntimeException("Error when reading material config: " + s, e);
							}

						});
						LOGGER.info("Finished reading config {}", s);
					}
				}
			} catch (IOException e) {
				throw new RuntimeException("Error when finding index.", e);
			}
		});
		LOGGER.info("Finished loading materials for mod {}", modID);
	}
}
