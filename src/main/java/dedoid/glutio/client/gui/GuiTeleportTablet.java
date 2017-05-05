package dedoid.glutio.client.gui;

import dedoid.glutio.client.lib.LibResources;
import dedoid.glutio.common.GlutIO;
import dedoid.glutio.common.net.MessageTeleportTablet;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.DimensionManager;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class GuiTeleportTablet extends GuiBase {

    private ItemStack stackTablet;

    private GuiTextField fieldName;

    public GuiTeleportTablet(InventoryPlayer invPlayer, ItemStack stackTablet) {
        super(LibResources.GUI_TELEPORT_TABLET);

        this.stackTablet = stackTablet;

        xSize = 176;
        ySize = 128;
    }

    private void setName() {
        if (!fieldName.getText().isEmpty()) {
            fieldName.setFocused(false);

            GlutIO.packetHandler.sendToServer(new MessageTeleportTablet(0, "§r§b" + fieldName.getText()));
        }
    }

    @Override
    public void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String dimensionName = DimensionManager.getProviderType(stackTablet.getTagCompound().getInteger("Dimension")).getName();
        String dimension = I18n.format("misc.dimension") + ": " + dimensionName;
        String coords = "X: " + stackTablet.getTagCompound().getInteger("X") + " Y: " + stackTablet.getTagCompound().getInteger("Y") + " Z: " + stackTablet.getTagCompound().getInteger("Z");

        fontRendererObj.drawString(dimension, xSize / 2 - fontRendererObj.getStringWidth(dimension) / 2, 40, 4210752);
        fontRendererObj.drawString(coords, xSize / 2 - fontRendererObj.getStringWidth(coords) / 2, 50, 4210752);
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);

        fieldName.drawTextBox();
    }

    @Override
    public void initGui() {
        super.initGui();

        int xStart = (width - xSize) / 2;
        int yStart = (height - ySize) / 2;

        fieldName = new GuiTextField(1, fontRendererObj, xStart + 38, yStart + 8, 100, 12);
        fieldName.setMaxStringLength(16);
        fieldName.setText(stackTablet.getDisplayName().substring(4));
    }

    @Override
    public void keyTyped(char ch, int key) throws IOException {
        if (key == Keyboard.KEY_RETURN) {
            setName();
        } else if (key == Keyboard.KEY_ESCAPE || (key == Keyboard.KEY_E && !fieldName.isFocused())) {
            mc.player.closeScreen();
        }

        fieldName.textboxKeyTyped(ch, key);
    }


    @Override
    public void updateScreen() {
        super.updateScreen();

        fieldName.updateCursorCounter();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int button) throws IOException {
        super.mouseClicked(mouseX, mouseY, button);

        fieldName.mouseClicked(mouseX, mouseY, button);

        if (button == 0) {
            int xAxis = (mouseX - (width - xSize) / 2);
            int yAxis = (mouseY - (height - ySize) / 2);

            if (xAxis >= 25 && xAxis <= 150 && yAxis >= 34 && yAxis <= 94) {
                GlutIO.packetHandler.sendToServer(new MessageTeleportTablet(1, null));
            }
        }
    }
}
