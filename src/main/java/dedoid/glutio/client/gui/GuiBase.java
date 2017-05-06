package dedoid.glutio.client.gui;

import dedoid.glutio.common.inventory.container.ContainerBase;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

public class GuiBase extends GuiContainer {

    ResourceLocation texture;

    public GuiBase(String texture) {
        this(new ContainerBase(), texture);
    }

    public GuiBase(ContainerBase container, String texture) {
        super(container);

        this.texture = new ResourceLocation(texture);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(texture);

        int xStart = (width - xSize) / 2;
        int yStart = (height - ySize) / 2;

        drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);
    }

    @Override
    public void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        fontRendererObj.drawString(I18n.format("container.inventory"), 8, ySize - 94, 4210752);
    }
}
