package com.genreshinobi.magelighter.item.crafting;

import com.genreshinobi.magelighter.Magelighter;
import com.genreshinobi.magelighter.ModItems;
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
    private final Map<ItemStack, Integer> chanceList = Maps.<ItemStack, Integer>newHashMap();

    private int upper = 100;
    private int lower = 0;

    public static ByProductRecipes getInstance() {
        return INSTANCE;
    }

    private ByProductRecipes() {
        // ASHEN FOND
        addByProductRecipe(new ItemStack(Items.POTATO), new ItemStack(ModItems.JAR), new ItemStack(ModItems.ASHENFOND), 0F, 50);
        addByProductRecipe(new ItemStack(Items.CHICKEN), new ItemStack(ModItems.JAR), new ItemStack(ModItems.ASHENFOND), 0F,50);
        addByProductRecipe(new ItemStack(Items.COD), new ItemStack(ModItems.JAR), new ItemStack(ModItems.ASHENFOND), 0F, 50);
        addByProductRecipe(new ItemStack(Items.MUTTON), new ItemStack(ModItems.JAR), new ItemStack(ModItems.ASHENFOND), 0F, 50);
        addByProductRecipe(new ItemStack(Items.PORKCHOP), new ItemStack(ModItems.JAR), new ItemStack(ModItems.ASHENFOND), 0F, 50);
        addByProductRecipe(new ItemStack(Items.SALMON), new ItemStack(ModItems.JAR), new ItemStack(ModItems.ASHENFOND), 0F, 50);
        addByProductRecipe(new ItemStack(Items.RABBIT), new ItemStack(ModItems.JAR), new ItemStack(ModItems.ASHENFOND), 0F, 50);
        addByProductRecipe(new ItemStack(Items.BEEF), new ItemStack(ModItems.JAR), new ItemStack(ModItems.ASHENFOND), 0F, 50);
    }

    public void addByProductRecipe(ItemStack input1, ItemStack input2, ItemStack result, float experience, int chance) {
        if(getByProductResult(input1, input2) != ItemStack.EMPTY) return;
        this.byProductList.put(input1, input2, result);
        this.experienceList.put(result, Float.valueOf(experience));
        this.chanceList.put(input1, Integer.valueOf(chance));
    }

    public ItemStack getByProductResult(ItemStack input1, ItemStack input2) {
        int r = (int) (Math.random() * (upper - lower)) + lower;
        int chance = getByProductChance(input1);
        if ( r < chance) {
            for (Entry<ItemStack, Map<ItemStack, ItemStack>> entry : this.byProductList.columnMap().entrySet()) {
                if (this.compareItemStacks(input2, (ItemStack) entry.getKey())) {
                    for (Entry<ItemStack, ItemStack> ent : entry.getValue().entrySet()) {
                        if (this.compareItemStacks(input1, (ItemStack) ent.getKey())) {
                            return (ItemStack) ent.getValue();
                        }
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
    public int getByProductChance(ItemStack stack) {
        for (Entry<ItemStack, Integer> entry : this.chanceList.entrySet()) {
            if(this.compareItemStacks(stack, (ItemStack)entry.getKey())) {
                return (entry.getValue()).intValue();
            }
        }
        return 0;
    }
}
