package dedoid.glutio.client.gui;

import dedoid.glutio.client.lib.LibResources;
import dedoid.glutio.common.inventory.container.ContainerNullifier;
import dedoid.glutio.common.item.ItemNullifier;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiNullifier extends GuiBase {

    public GuiNullifier(InventoryPlayer invPlayer, ItemNullifier nullifier) {
        super(new ContainerNullifier(invPlayer, nullifier), LibResources.GUI_NULLIFIER);

        xSize = 176;
        ySize = 166;
    }
}
