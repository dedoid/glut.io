package dedoid.glutio.common.gui.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class SlotUntouchable extends SlotBase {

    public SlotUntouchable(IInventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);

        setPhantom();
        setCanAdjustPhantom(false);
    }

    @Override
    public boolean isItemValid(ItemStack itemstack) {
        return false;
    }
}
