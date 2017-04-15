package dedoid.glutio.common.block.tile;

import dedoid.glutio.client.gui.PhantomCraftingGrid;
import dedoid.glutio.common.core.util.FakePlayerGlutIO;
import dedoid.glutio.common.core.util.ItemUtil;
import dedoid.glutio.common.net.PacketHandler;
import dedoid.glutio.common.net.PacketMolecularFabricator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class TileMolecularFabricator extends TileBase implements ISidedInventory, ITickable {

    PhantomCraftingGrid craftingGrid;
    NonNullList<ItemStack> inventory;
    InventoryCraftResult craftResult;

    FakePlayerGlutIO internalPlayer;

    long ticksSinceLastCraft;

    public TileMolecularFabricator() {
        craftingGrid = new PhantomCraftingGrid(this);
        inventory = NonNullList.withSize(9, ItemStack.EMPTY);
        craftResult = new InventoryCraftResult();

        ticksSinceLastCraft = 0;
    }

    public IInventory getCraftingGrid() {
        return craftingGrid;
    }

    public IInventory getCraftResult() {
        return craftResult;
    }

    public NonNullList<ItemStack> getInventory() {
        return inventory;
    }

    public boolean craftRecipe() {
        int[] usedItems = new int[9];

        InventoryCrafting craftingInventory = new InventoryCrafting(new Container() {
            @Override
            public boolean canInteractWith(EntityPlayer player) {
                return false;
            }
        }, 3, 3);

        for (int i = 0; i < 9; i++) {
            ItemStack required = getCraftingGrid().getStackInSlot(i);

            if (required != ItemStack.EMPTY) {
                for (int j = 0; j < 9; j++) {
                    if (getStackInSlot(j) != ItemStack.EMPTY && getStackInSlot(j).getCount() > usedItems[j] && compareDamageable(getStackInSlot(j), required)) {
                        required = ItemStack.EMPTY;
                        usedItems[j]++;

                        ItemStack craftingStack = getStackInSlot(j).copy();
                        craftingStack.setCount(1);

                        craftingInventory.setInventorySlotContents(i, craftingStack);
                    }
                }

                if (required != ItemStack.EMPTY) {
                    return false;
                }
            }

        }

        ItemStack output = CraftingManager.getInstance().findMatchingRecipe(craftingInventory, world);

        if (output != ItemStack.EMPTY) {

            if (internalPlayer == null) {
                internalPlayer = new FakePlayerGlutIO(world, getPos());
            }

            MinecraftForge.EVENT_BUS.post(new PlayerEvent.ItemCraftedEvent(internalPlayer, output, craftingInventory));

            NonNullList<ItemStack> remaining = CraftingManager.getInstance().getRemainingItems(craftingInventory, world);

            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < usedItems[i] && !getStackInSlot(i).isEmpty(); j++) {
                    setInventorySlotContents(i, eatOneItemForCrafting(i, getStackInSlot(i).copy(), remaining));
                }
            }

            /*for(ItemStack stack : remaining) {
                if(!stack.isEmpty()) {
                    containerItems.add(stack.copy());
                }
            }*/

            for (int i = 0; i < 9; i++) {
                if (getStackInSlot(i).isEmpty()) {
                    setInventorySlotContents(i, output);

                    break;
                } else if (ItemUtil.areStackMergable(getStackInSlot(i), output)) {
                    ItemStack cur = getStackInSlot(i).copy();
                    cur.setCount(cur.getCount() + output.getCount());

                    if (cur.getCount() > cur.getMaxStackSize()) {
                        ItemStack overflow = cur.copy();

                        overflow.setCount(cur.getCount() - cur.getMaxStackSize());
                        cur.setCount(cur.getMaxStackSize());
                        //containerItems.add(overflow);
                    }

                    setInventorySlotContents(i, cur);

                    break;
                }
            }
        }

        return true;
    }

    private ItemStack eatOneItemForCrafting(int slot, ItemStack avail, NonNullList<ItemStack> remaining) {
        if (remaining != null && remaining.size() > 0 && avail.getItem().hasContainerItem(avail)) {
            ItemStack used = avail.getItem().getContainerItem(avail);

            if(used != ItemStack.EMPTY) {
                for(int i=0; i < remaining.size();  i++) {
                    ItemStack stack = remaining.get(i);

                    if(stack != ItemStack.EMPTY && stack.isItemEqualIgnoreDurability(used) && isItemValidForSlot(slot, stack)) {
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

    private boolean compareDamageable(ItemStack stack, ItemStack required) {
        if (stack.isItemEqual(required)) {
            return true;
        }

        if (stack.isItemStackDamageable() && stack.getItem() == required.getItem()) {
            return stack.getItemDamage() < stack.getMaxDamage();
        }

        return false;
    }

    public void updateCraftingOutput() {
        InventoryCrafting inv = new InventoryCrafting(new Container() {

            @Override
            public boolean canInteractWith(EntityPlayer player) {
                return false;
            }
        }, 3, 3);

        for (int i = 0; i < 9; i++) {
            inv.setInventorySlotContents(i, getCraftingGrid().getStackInSlot(i));
        }

        ItemStack matches = CraftingManager.getInstance().findMatchingRecipe(inv, world);

        getCraftResult().setInventorySlotContents(0, matches);

        markDirty();
    }

    @Override
    public void update() {
        ticksSinceLastCraft++;

        if (ticksSinceLastCraft >= 3 && !getCraftResult().getStackInSlot(0).isEmpty()) {
            if (craftRecipe()) {
                ticksSinceLastCraft = 0;
            }
        }
    }

    /*@Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);

        NBTTagList tagList = tagCompound.getTagList("Inventory", 10);

        craftingGrid.readFromNBT(tagCompound, "CraftingGrid");

        for (int i = 0; i < tagList.tagCount(); i++) {
            NBTTagCompound tagCompoundSlot = tagList.getCompoundTagAt(i);

            int index = tagCompoundSlot.getByte("Slot");

            if (index >= 0 && index < inventory.size()) {
                inventory.set(i, new ItemStack(tagCompoundSlot));
            }
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);

        NBTTagList tagList = new NBTTagList();

        craftingGrid.writeFromNBT(tagCompound, "CraftingGrid");

        for (byte i = 0; i < inventory.size(); i++) {
            NBTTagCompound tagCompoundSlot = new NBTTagCompound();

            tagList.appendTag(tagCompoundSlot);

            tagCompoundSlot.setByte("Slot", i);

            inventory.get(i).writeToNBT(tagCompoundSlot);
        }

        tagCompound.setTag("Inventory", tagList);

        return tagCompound;

        return tagCompound;
    }*/

    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        return new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8};
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
                ItemStack result = stack;

                setInventorySlotContents(index, ItemStack.EMPTY);

                markDirty();

                return result;
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

        if (stack != ItemStack.EMPTY && stack.getCount() > getInventoryStackLimit()) {
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
}


