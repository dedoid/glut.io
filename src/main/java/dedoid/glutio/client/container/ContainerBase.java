package dedoid.glutio.client.container;

import dedoid.glutio.client.gui.IPhantomSlot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class ContainerBase extends Container {

    TileEntity tile;

    public ContainerBase(InventoryPlayer player, TileEntity tile) {
        this.tile = tile;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlotToContainer(new Slot(player, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int i = 0; i < 9; i++) {
            this.addSlotToContainer(new Slot(player, i, 8 + i * 18, 142));
        }
    }

    public TileEntity getTile() {
        return tile;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = getSlot(index);

        if (slot.getHasStack()) {
            if (slot instanceof IPhantomSlot) {
                slot.putStack(ItemStack.EMPTY);
            }
        }

        return stack;
    }

    @Override
    public boolean canDragIntoSlot(Slot slot) {
        return !(slot instanceof IPhantomSlot);
    }
}
