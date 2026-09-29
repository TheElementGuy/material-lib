package com.github.theelementguy.tegmatlib.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;

import java.util.Optional;

/**
 * A loot modifier that adds an item to a pool. If said item is rolled, it will replace the loot that would have been generated. Best used for single item loot (e.g. archaeology).
 */
public class AddItemRollModifier extends LootModifier {

	/**
	 * Returns the <code>MapCodec</code> of this loot modifier
	 * @return the codec
	 */
	public static MapCodec<AddItemRollModifier> getCodec() {
		if (CODEC == null) {
			CODEC = RecordCodecBuilder.mapCodec(addItemModifierInstance -> LootModifier.codecStart(addItemModifierInstance).and(BuiltInRegistries.ITEM.byNameCodec().fieldOf("item").forGetter(e -> e.item)).and(Codec.FLOAT.fieldOf("chance").forGetter(e -> e.chance)).apply(addItemModifierInstance, AddItemRollModifier::new));
		}
		return CODEC;
	}

    private final Item item;

    private final float chance;

	private static MapCodec<AddItemRollModifier> CODEC;

	/**
	 * Builds a new <code>AddItemRollModifier</code>
	 * @param conditionsIn loot conditions for when this modifier will fire (e.g. the loot table ID)
	 * @param priority priority (default 1000)
	 * @param item the item that will replace the loot
	 * @param chance the chance with which the item replaces the loot
	 */
    public AddItemRollModifier(Optional<Holder<LootItemCondition>> conditionsIn, int priority, Item item, float chance) {
        super(conditionsIn, priority);
        this.item = item;
        this.chance = chance;
    }

    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> objectArrayList, LootContext lootContext) {
        RandomSource randomSource = lootContext.getRandom();
        float randInt = randomSource.nextFloat();
        System.out.println(randInt);
        if (randInt > chance) {
            return objectArrayList;
        }
        objectArrayList.clear();
        objectArrayList.add(new ItemStack(this.item));
        return objectArrayList;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return getCodec();
    }
}