package dedoid.glutio.common.inventory.slot;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class SlotBase extends Slot {

    protected boolean isPhantom;
    protected boolean canAdjustPhantom = true;
    protected boolean canShift = true;
    protected int stackLimit = -1;

    public SlotBase(IInventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    public SlotBase setPhantom() {
        isPhantom = true;

        return this;
    }

    public SlotBase blockShift() {
        canShift = false;

        return this;
    }

    public SlotBase setCanAdjustPhantom(boolean canAdjust) {
        this.canAdjustPhantom = canAdjust;

        return this;
    }

    public SlotBase setCanShift(boolean canShift) {
        this.canShift = canShift;

        return this;
    }

    public SlotBase setStackLimit(int limit) {
        this.stackLimit = limit;

        return this;
    }

    @Override
    public int getSlotStackLimit() {
        if (stackLimit < 0) {
            return super.getSlotStackLimit();
        } else {
            return stackLimit;
        }
    }

    public boolean isPhantom() {
        return isPhantom;
    }

    public boolean canAdjustPhantom() {
        return canAdjustPhantom;
    }

    @Override
    public boolean canTakeStack(EntityPlayer player) {
        return !isPhantom();
    }

    public boolean canShift() {
        return canShift;
    }
}
