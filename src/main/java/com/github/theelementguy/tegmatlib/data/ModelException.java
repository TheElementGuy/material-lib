package com.github.theelementguy.tegmatlib.data;

import net.minecraft.client.data.models.model.ModelTemplate;

public record ModelException(String name, ModelTemplate overrideTemplate) {
}
