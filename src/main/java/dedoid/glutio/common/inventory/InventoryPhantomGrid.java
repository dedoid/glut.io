package dedoid.glutio.common.inventory;

import dedoid.glutio.common.block.tile.TileMolecularAssembler;
import dedoid.glutio.common.core.handler.PacketHandler;
import dedoid.glutio.common.net.PacketMolecularAssembler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;

public class InventoryPhantomGrid implements IInventory {

    TileMolecularAssembler tile;

    NonNullList<ItemStack> inventory;

    public InventoryPhantomGrid(TileMolecularAssembler tile) {
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

        PacketHandler.INSTANCE.sendToServer(PacketMolecularAssembler.setSlot(tile, index, ItemStack.EMPTY));

        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        inventory.set(index, ItemStack.EMPTY);

        PacketHandler.INSTANCE.sendToServer(PacketMolecularAssembler.setSlot(tile, index, ItemStack.EMPTY));

        return ItemStack.EMPTY;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        if (!stack.isEmpty()) {
            inventory.set(index, stack.copy());

            inventory.get(index).setCount(1);

            PacketHandler.INSTANCE.sendToServer(PacketMolecularAssembler.setSlot(tile, index, inventory.get(index)));
        } else {
            inventory.set(index, ItemStack.EMPTY);

            PacketHandler.INSTANCE.sendToServer(PacketMolecularAssembler.setSlot(tile, index, ItemStack.EMPTY));
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
        return null;
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
