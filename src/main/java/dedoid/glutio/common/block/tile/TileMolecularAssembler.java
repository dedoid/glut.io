package dedoid.glutio.common.block.tile;

import dedoid.glutio.common.core.util.ItemUtil;
import dedoid.glutio.common.inventory.InventoryPhantomGrid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;

public class TileMolecularAssembler extends TileBase implements ITickable, ISidedInventory {

    private NonNullList<ItemStack> inventory;
    private InventoryPhantomGrid phantomGrid;
    private InventoryCraftResult craftResult;

    private NonNullList<ItemStack> adjacentInvs[];

    private long TICKS_SINCE_LAST_CRAFT;

    public ItemStack recipe;

    public TileMolecularAssembler() {
        inventory = NonNullList.withSize(9, ItemStack.EMPTY);
        phantomGrid = new InventoryPhantomGrid(this);
        craftResult = new InventoryCraftResult();

        adjacentInvs = new NonNullList[4];

        TICKS_SINCE_LAST_CRAFT = 0;

        recipe = ItemStack.EMPTY;
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

    public ItemStack getRecipe() {
        return recipe;
    }

    public void setRecipe(ItemStack recipe) {
        this.recipe = recipe;
    }

    @Override
    public void update() {
        if (world.isRemote) {
            return;
        }

        TICKS_SINCE_LAST_CRAFT++;

        //updateInventories();

        if (TICKS_SINCE_LAST_CRAFT >= 2) {
            if (hasRecipe() && craftRecipe()) {
                TICKS_SINCE_LAST_CRAFT = 0;
            }
        }
    }

    private boolean hasRecipe() {
        return !recipe.isEmpty();
    }

    private boolean craftRecipe() {
        //ArrayList<Integer> used = new ArrayList<Integer>(9);
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

        for (int i = 0; i < 9; i++) {
            if (getStackInSlot(i).isEmpty()) {
                break;
            } else if (ItemUtil.areStacksMergable(getStackInSlot(i), recipe)) {
                break;
            }

            if (i == 8) {
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
                setInventorySlotContents(i, recipe);

                break;
            } else if (ItemUtil.areStacksMergable(getStackInSlot(i), recipe)) {
                ItemStack current = getStackInSlot(i).copy();
                current.setCount(current.getCount() + recipe.getCount());

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

    private void updateInventories() {
        for (EnumFacing dir : EnumFacing.HORIZONTALS) {
            int j = dir.getIndex() - 2;

            BlockPos neighbour = pos.offset(dir);

            TileEntity tile = world.getTileEntity(neighbour);

            if (tile != null) {
                if (tile instanceof IInventory) {
                    for (int i = 9 * j; i < ((IInventory) tile).getSizeInventory() + 9 * j; i++) {
                        inventory.add(((IInventory) tile).getStackInSlot(i));
                    }
                }
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
