package com.genreshinobi.magelighter.inventory.container;

import com.genreshinobi.magelighter.ModBlocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class ClericsOvenContainer extends Container {

    // TODO: Clerics Oven - Fuel Slot
    // TODO: Clerics Oven - Input Slot - No Ore
    // TODO: Clerics Oven - Output Slot - Recipes, begin with normal wood > coal then clay jars.
    // TODO: Clerics Oven - By Product Slot - Only Accepts Jars
    // TODO: Clerics Oven - By Product Output - Only By Products

    private TileEntity tileEntity;
    private PlayerEntity playerEntity;
    private IItemHandler playerInventory;

    public static final int INPUT = 0;
    public static final int FUEL = 1;
    public static final int COLLECTOR = 2;
    public static final int BYPRODUCT = 3;
    public static final int OUTPUT = 4;

    public ClericsOvenContainer(int windowId, World world, BlockPos pos, PlayerInventory inventory, PlayerEntity player) {
        super(ModBlocks.CLERICSOVEN_CONTAINER, windowId);

        this.tileEntity = world.getTileEntity(pos);
        this.playerEntity = player;
        this.playerInventory = new InvWrapper(inventory);

        this.tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
            addSlot(new SlotItemHandler(h, INPUT, 51, 18));
            addSlot(new SlotItemHandler(h, FUEL, 51, 54));
            addSlot(new SlotItemHandler(h, COLLECTOR, 80, 54));
            addSlot(new SlotItemHandler(h, BYPRODUCT, 112, 54));
            addSlot(new SlotItemHandler(h, OUTPUT, 112, 22));
        });

        layoutPlayerInventorySlots(8, 84);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(IWorldPosCallable.of(this.tileEntity.getWorld(), this.tileEntity.getPos()), this.playerEntity, ModBlocks.CLERICSOVEN);
    }

    private int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
        for(int i = 0; i < amount; i++) {
            addSlot(new SlotItemHandler(handler, index, x, y));
            x += dx;
            index++;
        }
        return index;
    }

    private int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for(int j = 0; j < verAmount; j++) {
            index = addSlotRange(handler, index, x, y, horAmount, dx);
            y += dy;
        }
        return index;
    }

    private void layoutPlayerInventorySlots(int leftCol, int topRow) {
        // Player inventory
        addSlotBox(playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);

        // Hotbar
        topRow += 58;
        addSlotRange(playerInventory, 0, leftCol, topRow, 9, 18);
    }
}
