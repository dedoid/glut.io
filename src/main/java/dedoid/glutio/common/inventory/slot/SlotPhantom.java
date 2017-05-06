package dedoid.glutio.common.inventory.slot;

import net.minecraft.inventory.IInventory;

public class SlotPhantom extends SlotBase {

    public SlotPhantom(IInventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);

        this.setPhantom();
        this.setStackLimit(1);
    }
}
