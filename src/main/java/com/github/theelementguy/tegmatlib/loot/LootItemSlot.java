package com.github.theelementguy.tegmatlib.loot;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;

public enum LootItemSlot implements StringRepresentable {
	BASE("base"),
	RAW("raw"),
	SWORD("sword"),
	AXE("axe"),
	PICKAXE("pickaxe"),
	SHOVEL("shovel"),
	HOE("hoe"),
	SPEAR("spear"),
	BOOTS("boots"),
	CHESTPLATE("chestplate"),
	LEGGINGS("leggings"),
	HELMET("helmet"),
	BLOCK("block"),
	HORSE_ARMOR("horse_armor"),
	NAUTILUS_ARMOR("nautilus_armor");

	private final String name;

	public static final Codec<LootItemSlot> CODEC = StringRepresentable.fromEnum(LootItemSlot::values);

	LootItemSlot(String name) {
		this.name = name;
	}

	@Override
	public String getSerializedName() {
		return name;
	}
}
