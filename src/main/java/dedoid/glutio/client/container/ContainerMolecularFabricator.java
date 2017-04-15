package dedoid.glutio.client.container;

import dedoid.glutio.client.gui.SlotPhantom;
import dedoid.glutio.client.gui.SlotUntouchable;
import dedoid.glutio.common.block.tile.TileMolecularFabricator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMolecularFabricator extends ContainerBase {

    public ContainerMolecularFabricator(InventoryPlayer player, TileMolecularFabricator tile) {
        super(player, tile);

        this.tile = tile;

        int index = 0;

        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                addSlotToContainer(new SlotPhantom(getTile().getCraftingGrid(), index, 8 + x * 18, 17 + y * 18));

                index++;
            }
        }

        addSlotToContainer(new SlotUntouchable(getTile().getCraftResult(), 0, 79, 35));

        index = 0;

        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                addSlotToContainer(new Slot(getTile(), index, 116 + x * 18, 17 + y * 18));

                index++;
            }
        }
    }

    @Override
    public TileMolecularFabricator getTile() {
        return (TileMolecularFabricator) tile;
    }

    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        Slot slot = getSlot(index);

        if (slot instanceof SlotPhantom || slot instanceof SlotUntouchable) {

        } else {
            if (slot.getHasStack()) {
                ItemStack stack = slot.getStack().copy();


            }
        }

        return ItemStack.EMPTY;
    }
}
