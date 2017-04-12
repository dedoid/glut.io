package dedoid.glutio.client.gui;

import dedoid.glutio.client.container.ContainerMolecularFabricator;
import dedoid.glutio.client.lib.LibResources;
import dedoid.glutio.common.block.tile.TileMolecularFabricator;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiMolecularFabricator extends GuiContainer {

    TileMolecularFabricator tile;

    public GuiMolecularFabricator(InventoryPlayer player, TileMolecularFabricator tile) {
        super(new ContainerMolecularFabricator(player, tile));

        this.tile = tile;

        xSize = 176;
        ySize = 167;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(new ResourceLocation(LibResources.GUI_MOLECULAR_FABRICATOR));

        int xStart = (width - xSize) / 2;
        int yStart = (height - ySize) / 2;

        drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);
    }

    @Override
    public void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        fontRendererObj.drawString(I18n.format(tile.getName()), xSize / 2 - fontRendererObj.getStringWidth(tile.getName()) / 2, 6, 4210752);
        fontRendererObj.drawString(I18n.format("container.inventory"), 8, ySize - 94, 4210752);
    }
}
