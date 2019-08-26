package com.genreshinobi.magelighter;

import com.genreshinobi.magelighter.blocks.ClericsOven;
import com.genreshinobi.magelighter.inventory.container.ClericsOvenContainer;
import com.genreshinobi.magelighter.tileEntities.ClericsOvenEntity;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ObjectHolder;

public class ModBlocks {

    @ObjectHolder("magelighter:clerics_oven")
    public static ClericsOven CLERICSOVEN;

    @ObjectHolder("magelighter:clerics_oven")
    public static TileEntityType<ClericsOvenEntity> CLERICSOVEN_TILE;

    @ObjectHolder("magelighter:clerics_oven")
    public static ContainerType<ClericsOvenContainer> CLERICSOVEN_CONTAINER;
}
