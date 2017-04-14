package dedoid.glutio.client.container;

import dedoid.glutio.client.gui.SlotPhantom;
import dedoid.glutio.client.gui.SlotUntouchable;
import dedoid.glutio.common.block.tile.TileMolecularFabricator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMolecularFabricator extends ContainerBase {

    public ContainerMolecularFabricator(InventoryPlayer player, TileMolecularFabricator tile) {
        super(player, tile);

        this.tile = tile;

        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                addSlotToContainer(new SlotPhantom(getTile().getCraftingGrid(), x + y * 3, 8 + x * 18, 17 + y * 18));
            }
        }

        addSlotToContainer(new SlotUntouchable(getTile().getCraftResult(), 0, 79, 35));

        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                addSlotToContainer(new Slot(getTile(), x + y * 3, 116 + x * 18, 17 + y * 18));
            }
        }
    }

    @Override
    public TileMolecularFabricator getTile() {
        return (TileMolecularFabricator) tile;
    }

    @Override
    public ItemStack slotClick(int slotNum, int mouseButton, ClickType clickType, EntityPlayer player) {
        Slot slot = slotNum < 0 ? null : getSlot(slotNum);

        if (slot instanceof SlotPhantom) {
            ItemStack stack = player.inventory.getItemStack();

            if (stack != ItemStack.EMPTY) {
                ItemStack copy = stack.copy();

                copy.setCount(1);

                slot.putStack(copy);

                return stack;
            } else {
                slot.putStack(ItemStack.EMPTY);

                return ItemStack.EMPTY;
            }
        }

        if (slot instanceof SlotUntouchable) {
            for (int i = 0; i < 9; i++) {
                getTile().getCraftingGrid().setInventorySlotContents(i, ItemStack.EMPTY);
            }

            return ItemStack.EMPTY;
        }

        return super.slotClick(slotNum, mouseButton, clickType, player);
    }
}
