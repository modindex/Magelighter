package com.genreshinobi.magelighter.item.crafting;

import com.genreshinobi.magelighter.Magelighter;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.Map;
import java.util.Map.Entry;

public class ByProductRecipes {
    private static final ByProductRecipes INSTANCE = new ByProductRecipes();
    private final Table<ItemStack, ItemStack, ItemStack> byProductList = HashBasedTable.<ItemStack, ItemStack, ItemStack>create();
    private final Map<ItemStack, Float> experienceList = Maps.<ItemStack, Float>newHashMap();

    public static ByProductRecipes getInstance() {
        return INSTANCE;
    }

    private ByProductRecipes() {
        addByProductRecipe(new ItemStack(Items.OAK_LOG), new ItemStack(Items.APPLE), new ItemStack(Items.POTATO), 5.0F);
        addByProductRecipe(new ItemStack(Items.BIRCH_LOG), new ItemStack(Items.APPLE), new ItemStack(Items.POTATO), 5.0F);
    }

    public void addByProductRecipe(ItemStack input1, ItemStack input2, ItemStack result, float experience) {
        if(getByProductResult(input1, input2) != ItemStack.EMPTY) return;
        this.byProductList.put(input1, input2, result);
        this.experienceList.put(result, Float.valueOf(experience));
    }

    public ItemStack getByProductResult(ItemStack input1, ItemStack input2) {
        for(Entry<ItemStack, Map<ItemStack, ItemStack>> entry : this.byProductList.columnMap().entrySet()) {
            if(this.compareItemStacks(input2, (ItemStack)entry.getKey())) {
                for(Entry<ItemStack, ItemStack> ent : entry.getValue().entrySet()) {
                    if(this.compareItemStacks(input1, (ItemStack)ent.getKey())) {
                        return (ItemStack)ent.getValue();
                    }
                }
            }
        }
        return ItemStack.EMPTY;
    }

    private boolean compareItemStacks(ItemStack stack1, ItemStack stack2) {
        Magelighter.LOGGER.info("Compared two Items: " + stack1 + " & " + stack2);
        return stack2.getItem() == stack1.getItem();
    }

    public Table<ItemStack, ItemStack, ItemStack> getByProductList() {
        return this.byProductList;
    }

    public float getByProductExperience(ItemStack stack) {
        for (Entry<ItemStack, Float> entry : this.experienceList.entrySet()) {
            if(this.compareItemStacks(stack, (ItemStack)entry.getKey())) {
                return ((Float)entry.getValue()).floatValue();
            }
        }
        return 0.0F;
    }
}
