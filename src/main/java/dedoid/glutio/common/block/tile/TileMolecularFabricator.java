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

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);

        craftingGrid.readFromNBT(tagCompound, "CraftingGrid");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);

        craftingGrid.writeFromNBT(tagCompound, "CraftingGrid");

        return tagCompound;
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        return new int[0];
    }

    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
        return false;
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
        return false;
    }

    @Override
    public int getSizeInventory() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return null;
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        return null;
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return null;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {

    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        return false;
    }

    @Override
    public void openInventory(EntityPlayer player) {

    }

    @Override
    public void closeInventory(EntityPlayer player) {

    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return false;
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
