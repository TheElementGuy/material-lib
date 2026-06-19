package com.github.theelementguy.tegmatlib.data;

import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.TexturedModel;

public record ModelException(String name, TexturedModel.Provider overrideTemplate) {
}