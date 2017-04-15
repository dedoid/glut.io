package dedoid.glutio.client.gui;

import dedoid.glutio.common.block.tile.TileBase;
import dedoid.glutio.common.block.tile.TileMolecularFabricator;
import dedoid.glutio.common.net.PacketHandler;
import dedoid.glutio.common.net.PacketMolecularFabricator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;

public class PhantomCraftingGrid implements IInventory {

    TileBase tile;

    NonNullList<ItemStack> inventory;

    public PhantomCraftingGrid(TileBase tile) {
        this.tile = tile;

        inventory = NonNullList.withSize(9, ItemStack.EMPTY);
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
        inventory.set(index, ItemStack.EMPTY);
        //setInventorySlotContents(index, ItemStack.EMPTY);

        changed(index, ItemStack.EMPTY);

        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        inventory.set(index, ItemStack.EMPTY);

        changed(index, ItemStack.EMPTY);

        return ItemStack.EMPTY;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        if (!stack.isEmpty()) {
            inventory.set(index, stack.copy());

            inventory.get(index).setCount(1);

        } else {
            inventory.set(index, ItemStack.EMPTY);
        }

        changed(index, stack);
    }

    @Override
    public int getInventoryStackLimit() {
        return 0;
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
    public String getName() {
        return "phantomCraftingGrid";
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    public ITextComponent getDisplayName() {
        return null;
    }

    public void changed(int index, ItemStack stack) {
        if (tile instanceof TileMolecularFabricator) {
            PacketHandler.INSTANCE.sendToServer(PacketMolecularFabricator.setSlot((TileMolecularFabricator) tile, index, stack));
        }
    }
}
