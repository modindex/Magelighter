package com.genreshinobi.magelighter.inventory.container;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class JarSlot extends Slot {
    private final ClericsOvenContainer clericsOvenContainer;

    public JarSlot(ClericsOvenContainer containerIn, IInventory inventory, int index, int xPos, int yPos) {
        super(inventory, index, xPos, yPos);
        this.clericsOvenContainer = containerIn;
    }

    /**
     * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
     */
    public boolean isItemValid(ItemStack stack) {
        return this.clericsOvenContainer.isJar(stack);
    }

    public int getItemStackLimit(ItemStack stack) {
        return isBucket(stack) ? 1 : super.getItemStackLimit(stack);
    }

    public static boolean isBucket(ItemStack stack) {
        return stack.getItem() == Items.BUCKET;
    }
}
