package dedoid.glutio.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;

public class PhantomCraftingGrid implements IInventory {

    NonNullList<ItemStack> inventory;

    public PhantomCraftingGrid() {
        inventory = NonNullList.withSize(9, ItemStack.EMPTY);
    }

    public void readFromNBT(NBTTagCompound tagCompound, String tag) {
        NBTTagList tagList = tagCompound.getTagList(tag, 10);

        for (int i = 0; i < tagList.tagCount(); i++) {
            NBTTagCompound tagCompoundSlot = tagList.getCompoundTagAt(i);

            int index = tagCompoundSlot.getByte("Slot");

            if (index >= 0 && index < inventory.size()) {
                inventory.set(i, new ItemStack(tagCompoundSlot));
            }
        }
    }

    public void writeFromNBT(NBTTagCompound tagCompound, String tag) {
        NBTTagList tagList = new NBTTagList();

        for (byte i = 0; i < inventory.size(); i++) {
            NBTTagCompound tagCompoundSlot = new NBTTagCompound();

            tagList.appendTag(tagCompoundSlot);

            tagCompoundSlot.setByte("Slot", i);

            inventory.get(i).writeToNBT(tagCompoundSlot);
        }

        tagCompound.setTag(tag, tagList);
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
        ItemStack stack = getStackInSlot(index);

        inventory.set(index, ItemStack.EMPTY);

        if (stack == ItemStack.EMPTY) {
            return ItemStack.EMPTY;
        }

        stack.setCount(0);

        return stack;
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        ItemStack stack = getStackInSlot(index);

        setInventorySlotContents(index, ItemStack.EMPTY);

        return stack;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        if (stack != ItemStack.EMPTY) {
            inventory.set(index, stack.copy()).setCount(1);
        } else {
            inventory.set(index, ItemStack.EMPTY);
        }
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }

    @Override
    public void markDirty() {

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
        return index < 9;
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
    public String getName() {
        return "PhantomCraftingGrid";
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    public ITextComponent getDisplayName() {
        return null;
    }
}
