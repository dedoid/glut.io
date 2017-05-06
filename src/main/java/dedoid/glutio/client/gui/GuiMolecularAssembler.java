package dedoid.glutio.client.gui;

import dedoid.glutio.client.lib.LibResources;
import dedoid.glutio.common.block.tile.TileMolecularAssembler;
import dedoid.glutio.common.inventory.container.ContainerMolecularAssembler;
import dedoid.glutio.common.lib.LibBlockNames;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiMolecularAssembler extends GuiBase {

    public GuiMolecularAssembler(InventoryPlayer invPlayer, TileMolecularAssembler tile) {
        super(new ContainerMolecularAssembler(invPlayer, tile), LibResources.GUI_MOLECULAR_ASSEMBLER);

        xSize = 176;
        ySize = 167;
    }

    @Override
    public void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);

        String name = "tile." + LibBlockNames.MOLECULAR_ASSEMBLER + ".name";

        fontRendererObj.drawString(I18n.format(name), xSize / 2 - fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
    }
}
