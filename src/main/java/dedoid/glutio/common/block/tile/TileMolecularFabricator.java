package dedoid.glutio.common.block.tile;

import com.mojang.authlib.GameProfile;
import dedoid.glutio.client.gui.PhantomCraftingGrid;
import dedoid.glutio.common.FakePlayerGlutIO;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;

import java.util.UUID;

public class TileMolecularFabricator extends TileBase implements ISidedInventory {

    PhantomCraftingGrid craftingGrid;
    NonNullList<ItemStack> inventory;

    private InventoryCraftResult craftResult;
    public IRecipe currentRecipe;

    private static UUID uuid;
    private static GameProfile molecularFabricatorProfile;
    private FakePlayerGlutIO internalPlayer;

    public TileMolecularFabricator() {
        craftingGrid = new PhantomCraftingGrid();
        inventory = NonNullList.withSize(9, ItemStack.EMPTY);

        craftResult = new InventoryCraftResult();

        uuid = UUID.fromString("4d10be36-8c25-4962-b6b1-f7f1c64a3077");
        molecularFabricatorProfile = new GameProfile(uuid, "[GlutIOMolecularFabricator]");
        //internalPlayer = new FakePlayerGlutIO(world, getPos(), molecularFabricatorProfile);
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

    @Override
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

        // Phantom slots
        craftingGrid.writeFromNBT(tagCompound, "CraftingGrid");

        // Inventory slots
        for (byte i = 0; i < inventory.size(); i++) {
            NBTTagCompound tagCompoundSlot = new NBTTagCompound();

            tagList.appendTag(tagCompoundSlot);

            tagCompoundSlot.setByte("Slot", i);

            inventory.get(i).writeToNBT(tagCompoundSlot);
        }

        tagCompound.setTag("Inventory", tagList);

        return tagCompound;
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        return new int[0];
    }

    @Override
    public boolean canInsertItem(int index, ItemStack stack, EnumFacing direction) {
        return index > 9 && index < 20;
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
        return index > 9 && index < 20;
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

        if (stack != ItemStack.EMPTY) {
            if (stack.getCount() <= count) {
                setInventorySlotContents(index, ItemStack.EMPTY);

                markDirty();

                return stack;
            }

            ItemStack splitStack = stack.splitStack(count);

            if (stack.getCount() == 0) {
                setInventorySlotContents(index, ItemStack.EMPTY);
            } else {
                setInventorySlotContents(index, stack);
            }

            markDirty();

            return splitStack;
        }

        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        if (getStackInSlot(index) != ItemStack.EMPTY) {
            ItemStack stack = getStackInSlot(index);

            setInventorySlotContents(index, ItemStack.EMPTY);

            return stack;
        }

        return ItemStack.EMPTY;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        inventory.set(index, stack);

        if (stack != ItemStack.EMPTY && stack.getCount() > getInventoryStackLimit()) {
            stack.setCount(getInventoryStackLimit());
        }

        markDirty();

        //onChanged
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
