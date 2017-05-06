package dedoid.glutio.common.block.tile;

import dedoid.glutio.common.core.util.ItemUtil;
import dedoid.glutio.common.inventory.InventoryPhantomGrid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;

public class TileMolecularAssembler extends TileBase implements ITickable, ISidedInventory {

    private NonNullList<ItemStack> inventory;
    private InventoryPhantomGrid phantomGrid;
    private InventoryCraftResult craftResult;

    private ItemStack recipe;

    private TileMolecularAssembler adjacentMA[];

    private long TICKS_SINCE_LAST_INVENTORY_CHECK;
    private long TICKS_SINCE_LAST_CRAFT;

    public TileMolecularAssembler() {
        inventory = NonNullList.create();
        for (int i = 0; i < 9; i++) {
            inventory.add(ItemStack.EMPTY);
        }

        phantomGrid = new InventoryPhantomGrid(this);
        craftResult = new InventoryCraftResult();

        recipe = ItemStack.EMPTY;

        adjacentMA = new TileMolecularAssembler[4];

        TICKS_SINCE_LAST_INVENTORY_CHECK = 0;
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

        TICKS_SINCE_LAST_INVENTORY_CHECK++;
        TICKS_SINCE_LAST_CRAFT++;

        if (TICKS_SINCE_LAST_INVENTORY_CHECK >= 5) {
            updateInventories();
            TICKS_SINCE_LAST_INVENTORY_CHECK = 0;
        }

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
        int used[] = new int[inventory.size()];

        for (int i = 0; i < 9; i++) {
            ItemStack required = craftingMatrix.getStackInSlot(i);

            for (int j = 0; j < inventory.size(); j++) {
                if (!getStackInSlot(j).isEmpty() && getStackInSlot(j).getCount() > used[j] && ItemUtil.isItemEqual(getStackInSlot(j), required, true, false)) {
                    required = ItemStack.EMPTY;

                    used[j]++;
                }
            }

            if (!required.isEmpty()) {
                return false;
            }
        }

        boolean found[] = new boolean[4];
        for (int i = 0; i < 4; i++) {
            if (adjacentMA[i] != null) {
                for (int j = 0; j < 9; j++) {
                    if (adjacentMA[i].getStackInSlot(j).isEmpty()) {
                        found[i] = true;

                        break;
                    } else if (ItemUtil.areStacksMergable(adjacentMA[i].getStackInSlot(j), recipe)) {
                        found[i] = true;

                        break;
                    }

                    if (j == 8) {
                        found[i] = false;
                    }
                }
            }
        }

        boolean found2 = true;
        int dis = -1;
        for (int i = 0; i < 4; i++) {
            if (found[i]) {
                dis = i;

                break;
            }

            if (i == 3) {
                found2 = false;
            }
        }

        if (!found2) {
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
        }

        for (int i = 0; i < inventory.size(); i++) {
            for (int j = 0; j < used[i] && !getStackInSlot(i).isEmpty(); j++) {
                getStackInSlot(i).setCount(getStackInSlot(i).getCount() - 1);

                if (getStackInSlot(i).getCount() == 0) {
                    setInventorySlotContents(i, ItemStack.EMPTY);
                }
            }
        }

        if (found2) {
            for (int i = 0; i < 9; i++) {
                if (adjacentMA[dis].getStackInSlot(i).isEmpty()) {
                    adjacentMA[dis].setInventorySlotContents(i, recipe);

                    break;
                } else if (ItemUtil.areStacksMergable(adjacentMA[dis].getStackInSlot(i), recipe)) {
                    ItemStack current = adjacentMA[dis].getStackInSlot(i).copy();
                    current.setCount(current.getCount() + recipe.getCount());

                    adjacentMA[dis].setInventorySlotContents(i, current);

                    break;
                }
            }
        }

        if (!found2) {
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
        }

        return true;
    }

    private void updateInventories() {
        for (EnumFacing dir : EnumFacing.HORIZONTALS) {
            BlockPos neighbour = pos.offset(dir);

            TileEntity tile = world.getTileEntity(neighbour);

            if (tile != null) {
                if (tile instanceof TileMolecularAssembler) {
                    int ma = dir.getIndex() - 2;

                    adjacentMA[ma] = (TileMolecularAssembler) tile;

                    boolean add = false;
                    for (int i = 0; i < 9; i++) {
                        if (ItemUtil.isItemEqual(getRecipe(), adjacentMA[ma].getCraftingMatrix().getStackInSlot(i), true, false)) {
                            add = true;

                            break;
                        }
                    }

                    if (!add) {
                        adjacentMA[ma] = null;
                    }
                } else if (tile instanceof IInventory) {
                    for (int i = 0; i < ((IInventory) tile).getSizeInventory(); i++) {
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
        return direction != EnumFacing.UP;
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
        return direction != EnumFacing.UP;
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

                //markDirty();

                return stack;
            }

            ItemStack split = stack.splitStack(count);

            //markDirty();

            return split;
        }

        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        ItemStack stack = getStackInSlot(index);

        inventory.set(index, ItemStack.EMPTY);

        //markDirty();

        return stack;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        inventory.set(index, stack);

        if (!stack.isEmpty() && stack.getCount() > getInventoryStackLimit()) {
            stack.setCount(getInventoryStackLimit());
        }

        //markDirty();
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
