package dedoid.glutio.common.gui.slot;

import net.minecraft.inventory.IInventory;

//TODO fix phantom slot weirdness when drag clicking
public class SlotPhantom extends SlotBase {

    public SlotPhantom(IInventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);

        this.setPhantom();
        this.setStackLimit(1);
    }
}
