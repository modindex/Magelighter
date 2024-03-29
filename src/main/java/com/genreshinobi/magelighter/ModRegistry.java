package com.genreshinobi.magelighter;

import com.genreshinobi.magelighter.blocks.ClericsOven;
import com.genreshinobi.magelighter.inventory.container.ClericsOvenContainer;
import com.genreshinobi.magelighter.tileEntities.ClericsOvenEntity;
import net.minecraft.block.Block;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.genreshinobi.magelighter.Magelighter.magelighter;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModRegistry {

    // Register Blocks
    @SubscribeEvent
    public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegisteryEvent) {
        Magelighter.LOGGER.info("Registering Blocks");
        blockRegisteryEvent.getRegistry().register(new ClericsOven());
    }

    // Register Items
    @SubscribeEvent
    public static void onItemsRegistry(final RegistryEvent.Register<Item> itemRegistryEvent) {
        Magelighter.LOGGER.info("Registering Blocks");
        // Block Items
        itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.CLERICSOVEN, new Item.Properties().group(magelighter)).setRegistryName(ModBlocks.CLERICSOVEN.getRegistryName()));
        itemRegistryEvent.getRegistry().register(new Item(new Item.Properties().group(magelighter)).setRegistryName("jar_clay"));
        itemRegistryEvent.getRegistry().register(new Item(new Item.Properties().group(magelighter)).setRegistryName("jar"));
        itemRegistryEvent.getRegistry().register(new Item(new Item.Properties().group(magelighter)).setRegistryName("ashen_fond"));
    }

    // Register Tile Entities
    @SubscribeEvent
    public static void onTileEntityRegistry(final RegistryEvent.Register<TileEntityType<?>> tileEntityRegistryEvent) {
        Magelighter.LOGGER.info("Registering Tile Entities");
        tileEntityRegistryEvent.getRegistry().register(TileEntityType.Builder.create(ClericsOvenEntity::new, ModBlocks.CLERICSOVEN).build(null).setRegistryName("clerics_oven"));
    }

    // Register Containers
    @SubscribeEvent
    public static void onContainerRegistry(final RegistryEvent.Register<ContainerType<?>> containerRegisteryEvent) {
        Magelighter.LOGGER.info("Registering Containers");
        containerRegisteryEvent.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> {
            return new ClericsOvenContainer(windowId, inv);
        }).setRegistryName("clerics_oven"));
    }
}
