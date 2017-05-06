package dedoid.glutio.common.inventory.container;

import dedoid.glutio.common.core.util.ItemUtil;
import dedoid.glutio.common.inventory.slot.SlotBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public class ContainerBase extends Container {


    public ContainerBase() {

    }

    public void addSlot(Slot slot) {
        addSlotToContainer(slot);
    }


    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }

    @Nullable
    @Override
    public ItemStack slotClick(int slotId, int mouseButton, ClickType clickType, EntityPlayer player) {
        Slot slot = slotId < 0 ? null : inventorySlots.get(slotId);

        if (slot instanceof SlotBase && ((SlotBase) slot).isPhantom()) {
            return slotClickPhantom((SlotBase) slot, mouseButton, clickType, player);
        }

        return super.slotClick(slotId, mouseButton, clickType, player);
    }

    @Nullable
    private ItemStack slotClickPhantom(SlotBase slot, int mouseButton, ClickType clickType, EntityPlayer player) {
        ItemStack stack = ItemStack.EMPTY;

        if (mouseButton == 2) {
            if (slot.canAdjustPhantom()) {
                slot.putStack(ItemStack.EMPTY);
            }
        } else if (mouseButton == 0 || mouseButton == 1) {
            InventoryPlayer playerInv = player.inventory;

            slot.onSlotChanged();

            ItemStack stackSlot = slot.getStack();
            ItemStack stackHeld = playerInv.getItemStack();

            if (!stackSlot.isEmpty()) {
                stack = stackSlot.copy();
            }

            if (stackSlot.isEmpty()) {
                if (!stackHeld.isEmpty() && slot.isItemValid(stackHeld)) {
                    fillPhantomSlot(slot, stackHeld, mouseButton);
                }
            } else if (stackHeld.isEmpty()) {
                adjustPhantomSlot(slot, mouseButton, clickType);
                slot.onTake(player, playerInv.getItemStack());
            } else if (slot.isItemValid(stackHeld)) {
                if (ItemUtil.isItemEqual(stackSlot, stackHeld, true, true)) {
                    adjustPhantomSlot(slot, mouseButton, clickType);
                } else {
                    fillPhantomSlot(slot, stackHeld, mouseButton);
                }
            }
        }

        return stack;
    }

    private void adjustPhantomSlot(SlotBase slot, int mouseButton, ClickType clickType) {
        if (!slot.canAdjustPhantom()) {
            return;
        }

        ItemStack stackSlot = slot.getStack();

        if (stackSlot.isEmpty()) {
            return;
        }

        int stackSize;

        if (clickType == ClickType.QUICK_MOVE) {
            stackSize = mouseButton == 0 ? (stackSlot.getCount() + 1) / 2 : stackSlot.getCount() * 2;
        } else {
            stackSize = mouseButton == 0 ? stackSlot.getCount() - 1 : stackSlot.getCount() + 1;
        }

        if (stackSize > slot.getSlotStackLimit()) {
            stackSize = slot.getSlotStackLimit();
        }

        stackSlot.setCount(stackSize);

        if (stackSlot.getCount() <= 0) {
            slot.putStack(ItemStack.EMPTY);
        }
    }

    private void fillPhantomSlot(SlotBase slot, ItemStack stackHeld, int mouseButton) {
        if (!slot.canAdjustPhantom()) {
            return;
        }

        int stackSize = mouseButton == 0 ? stackHeld.getCount() : 1;

        if (stackSize > slot.getSlotStackLimit()) {
            stackSize = slot.getSlotStackLimit();
        }

        ItemStack phantomStack = stackHeld.copy();
        phantomStack.setCount(stackSize);

        slot.putStack(phantomStack);
    }

    private boolean shiftItemStack(ItemStack stackToShift, int start, int end) {
        boolean changed = false;

        if (stackToShift.isStackable()) {
            for (int slotIndex = start; stackToShift.getCount() > 0 && slotIndex < end; slotIndex++) {
                Slot slot = inventorySlots.get(slotIndex);
                ItemStack stackInSlot = slot.getStack();

                if (!stackInSlot.isEmpty() && ItemUtil.isItemEqual(stackInSlot, stackToShift, true, true)) {
                    int resultingStackSize = stackInSlot.getCount() + stackToShift.getCount();
                    int max = Math.min(stackToShift.getMaxStackSize(), slot.getSlotStackLimit());

                    if (resultingStackSize <= max) {
                        stackToShift.setCount(0);
                        stackInSlot.setCount(resultingStackSize);
                        slot.onSlotChanged();
                        changed = true;
                    } else if (stackInSlot.getCount() < max) {
                        stackToShift.setCount(stackToShift.getCount() - max - stackInSlot.getCount());
                        stackInSlot.setCount(max);
                        slot.onSlotChanged();
                        changed = true;
                    }
                }
            }
        }

        if (stackToShift.getCount() > 0) {
            for (int slotIndex = start; stackToShift.getCount() > 0 && slotIndex < end; slotIndex++) {
                Slot slot = inventorySlots.get(slotIndex);
                ItemStack stackInSlot = slot.getStack();

                if (stackInSlot.isEmpty()) {
                    int max = Math.min(stackToShift.getMaxStackSize(), slot.getSlotStackLimit());

                    stackInSlot = stackToShift.copy();
                    stackInSlot.setCount(Math.min(stackToShift.getCount(), max));

                    stackToShift.setCount(stackToShift.getCount() - stackInSlot.getCount());

                    slot.putStack(stackInSlot);
                    slot.onSlotChanged();

                    changed = true;
                }
            }
        }

        return changed;
    }

    private boolean tryShiftItem(ItemStack stackToShift, int numSlots) {
        for (int machineIndex = 0; machineIndex < numSlots - 9 * 4; machineIndex++) {
            Slot slot = inventorySlots.get(machineIndex);

            if (slot instanceof SlotBase) {
                SlotBase slotBase = (SlotBase) slot;
                if (slotBase.isPhantom()) {
                    continue;
                }

                if (!slotBase.canShift()) {
                    continue;
                }
            }

            if (!slot.isItemValid(stackToShift)) {
                continue;
            }

            if (shiftItemStack(stackToShift, machineIndex, machineIndex + 1)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
        ItemStack originalStack = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(slotIndex);
        int numSlots = inventorySlots.size();

        if (slot != null && slot.getHasStack()) {
            ItemStack stackInSlot = slot.getStack();

            originalStack = stackInSlot.copy();

            if (!(slotIndex >= numSlots - 9 * 4 && tryShiftItem(stackInSlot, numSlots))) {
                if (slotIndex >= numSlots - 9 * 4 && slotIndex < numSlots - 9) {
                    if (!shiftItemStack(stackInSlot, numSlots - 9, numSlots)) {
                        return ItemStack.EMPTY;
                    }
                } else if (slotIndex >= numSlots - 9 && slotIndex < numSlots) {
                    if (!shiftItemStack(stackInSlot, numSlots - 9 * 4, numSlots - 9)) {
                        return ItemStack.EMPTY;
                    }
                } else if (!shiftItemStack(stackInSlot, numSlots - 9 * 4, numSlots)) {
                    return ItemStack.EMPTY;
                }
            }

            slot.onSlotChange(stackInSlot, originalStack);

            if (stackInSlot.getCount() <= 0) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (stackInSlot.getCount() == originalStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, stackInSlot);
        }

        return originalStack;
    }
}
