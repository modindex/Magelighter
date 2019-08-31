package com.genreshinobi.magelighter.gui;

import com.genreshinobi.magelighter.Magelighter;
import com.genreshinobi.magelighter.inventory.container.ClericsOvenContainer;
import com.genreshinobi.magelighter.tileEntities.ClericsOvenEntity;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.gui.recipebook.FurnaceRecipeGui;
import net.minecraft.client.gui.screen.inventory.AbstractFurnaceScreen;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.screen.inventory.FurnaceScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.AbstractFurnaceContainer;
import net.minecraft.inventory.container.FurnaceContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ClericsOvenScreen extends ContainerScreen<ClericsOvenContainer> {

    // TODO: Figure out what's going on with the Recipe Book. Crashing on Click. Removed for now to prevent crash.

    private static final ResourceLocation GUI = new ResourceLocation(Magelighter.MODID, "textures/gui/clerics_oven.png");

    public ClericsOvenScreen(ClericsOvenContainer container, PlayerInventory inv, ITextComponent name) {
        super(container, inv, name);
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String s = this.title.getFormattedText();
        this.font.drawString(s, 8.0f, 6.0F, 4210752);
        this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8.0F, (float)(this.ySize - 96 + 2), 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.minecraft.getTextureManager().bindTexture(this.GUI);
        int i = this.guiLeft;
        int j = this.guiTop;
        this.blit(i, j, 0, 0, this.xSize, this.ySize);

        if(((ClericsOvenContainer)this.container).func_217061_l()) {
            int k = ((ClericsOvenContainer)this.container).getBurnLeftScaled();
            this.blit(i+51, j + 36 + 14 - k, 176, 12 - k, 14, k+1);
        }

        int l = ((ClericsOvenContainer)this.container).getCookProgressionScaled();
        this.blit(i + 77, j + 24, 176, 14, l + 1, 16);
    }


}
