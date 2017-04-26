package dedoid.glutio.common.block.tile;

import dedoid.glutio.common.core.util.ItemUtil;
import dedoid.glutio.common.inventory.InventoryPhantomGrid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;

public class TileMolecularAssembler extends TileBase implements ITickable, ISidedInventory {

    private NonNullList<ItemStack> inventory;
    private InventoryPhantomGrid phantomGrid;
    private InventoryCraftResult craftResult;

    private long TICKS_SINCE_LAST_CRAFT;

    public TileMolecularAssembler() {
        inventory = NonNullList.withSize(9, ItemStack.EMPTY);
        phantomGrid = new InventoryPhantomGrid(this);
        craftResult = new InventoryCraftResult();

        TICKS_SINCE_LAST_CRAFT = 0;
    }

    public IInventory getPhantomGrid() {
        return phantomGrid;
    }

    public InventoryCrafting getCraftingMatrix() {
        return craftingMatrix;
    }

    public IInventory getCraftResult() {
        return craftResult;
    }

    private boolean craftRecipe() {
        int used[] = new int[9];

        for (int i = 0; i < 9; i++) {
            ItemStack required = craftingMatrix.getStackInSlot(i);

            for (int j = 0; j < 9; j++) {
                if (!getStackInSlot(j).isEmpty() && getStackInSlot(j).getCount() > used[j] && ItemUtil.isItemEqual(getStackInSlot(j), required, true, false)) {
                    required = ItemStack.EMPTY;

                    used[j]++;
                }
            }

            if (!required.isEmpty()) {
                return false;
            }
        }

        ItemStack output = CraftingManager.getInstance().findMatchingRecipe(craftingMatrix, world);

        if (output.isEmpty()) {
            return false;
        }

        for (int i = 0; i < 9; i++) {
            if (getStackInSlot(i).isEmpty()) {
                break;
            } else if (ItemUtil.areStacksMergable(getStackInSlot(i), output)) {
                break;
            }

            if (i == 8) {
                System.out.println("cancel");
                return false;
            }
        }

        NonNullList<ItemStack> remaining = CraftingManager.getInstance().getRemainingItems(craftingMatrix, world);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < used[i] && !getStackInSlot(i).isEmpty(); j++) {
                setInventorySlotContents(i, eatItem(getStackInSlot(i).copy(), remaining));
            }
        }

        for (int i = 0; i < 9; i++) {
            if (getStackInSlot(i).isEmpty()) {
                setInventorySlotContents(i, output);

                break;
            } else if (ItemUtil.areStacksMergable(getStackInSlot(i), output)) {
                ItemStack current = getStackInSlot(i).copy();
                current.setCount(current.getCount() + output.getCount());

                setInventorySlotContents(i, current);

                break;
            }
        }

        return true;
    }

    private ItemStack eatItem(ItemStack avail, NonNullList<ItemStack> remaining) {
        if (!remaining.isEmpty() && remaining.size() > 0 && avail.getItem().hasContainerItem(avail)) {
            ItemStack used = avail.getItem().getContainerItem(avail);

            if (!used.isEmpty()) {
                for (int i = 0; i < remaining.size(); i++) {
                    ItemStack stack = remaining.get(i);

                    if (!stack.isEmpty() && stack.isItemEqualIgnoreDurability(used)) {
                        remaining.set(i, ItemStack.EMPTY);

                        return stack;
                    }
                }
            }
        }

        avail.setCount(avail.getCount() - 1);

        if (avail.getCount() == 0) {
            avail = ItemStack.EMPTY;
        }

        return avail;
    }

    @Override
    public void update() {
        if (world.isRemote) {
            return;
        }

        TICKS_SINCE_LAST_CRAFT++;

        if (TICKS_SINCE_LAST_CRAFT >= 3) {
            if (craftRecipe()) {
                TICKS_SINCE_LAST_CRAFT = 0;
            }
        }
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        return new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8};
    }

    @Override
    public boolean canInsertItem(int index, ItemStack stack, EnumFacing direction) {
        return true;
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
        return true;
    }

    @Override
    public int getSizeInventory() {
        return inventory.size();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        if (index < 0 || index >= inventory.size()) {
            return ItemStack.EMPTY;
        }

        return inventory.get(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        ItemStack stack = inventory.get(index);

        if (!stack.isEmpty()) {
            if (stack.getCount() <= count) {
                setInventorySlotContents(index, ItemStack.EMPTY);

                markDirty();

                return stack;
            }

            ItemStack split = stack.splitStack(count);

            markDirty();

            return split;
        }

        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        ItemStack stack = getStackInSlot(index);

        inventory.set(index, ItemStack.EMPTY);

        markDirty();

        return stack;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        inventory.set(index, stack);

        if (!stack.isEmpty() && stack.getCount() > getInventoryStackLimit()) {
            stack.setCount(getInventoryStackLimit());
        }

        markDirty();
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        return true;
    }

    @Override
    public void openInventory(EntityPlayer player) {

    }

    @Override
    public void closeInventory(EntityPlayer player) {

    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return true;
    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {

    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {

    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    private InventoryCrafting craftingMatrix = new InventoryCrafting(new Container() {
        @Override
        public boolean canInteractWith(EntityPlayer player) {
            return false;
        }
    }, 3, 3);
}
