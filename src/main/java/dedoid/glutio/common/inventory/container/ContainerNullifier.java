package dedoid.glutio.common.inventory.container;

import dedoid.glutio.common.item.ItemNullifier;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

public class ContainerNullifier extends ContainerBase {

    public ContainerNullifier(InventoryPlayer invPlayer, ItemNullifier nullifier) {
        for (int rows = 0; rows < 5; rows++) {
            addSlot(new Slot(nullifier.inventory, rows, 26 + rows * 18, 35));
        }

        addSlot(new Slot(nullifier.inventory, 5, 134, 35));

        for (int cols = 0; cols < 3; cols++) {
            for (int rows = 0; rows < 9; rows++) {
                addSlot(new Slot(invPlayer, rows + cols * 9 + 9, 8 + rows * 18, 84 + cols * 18));
            }
        }

        for (int rows = 0; rows < 9; rows++) {
            addSlot(new Slot(invPlayer, rows, 8 + rows * 18, 142));
        }
    }
}
