package com.genreshinobi.magelighter;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ModCreativeTab extends ItemGroup {
    ModCreativeTab(){
        super("magelighter");
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(ModBlocks.CLERICSOVEN);
    }
}
