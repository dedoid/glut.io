package dedoid.glutio.common.gui.container;

import dedoid.glutio.common.block.tile.TileMolecularAssembler;
import dedoid.glutio.common.gui.slot.SlotPhantom;
import dedoid.glutio.common.gui.slot.SlotUntouchable;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

public class ContainerMolecularAssembler extends ContainerBase {

    private TileMolecularAssembler tile;

    public ContainerMolecularAssembler(InventoryPlayer invPlayer, TileMolecularAssembler tile) {
        this.tile = tile;

        for (int cols = 0; cols < 3; cols++) {
            for (int rows = 0; rows < 3; rows++) {
                addSlotToContainer(new SlotPhantom(tile.getPhantomGrid(), rows + cols * 3, 8 + rows * 18, 17 + cols * 18));
            }
        }

        addSlot(new SlotUntouchable(tile.getCraftResult(), 0, 79, 35));

        for (int cols = 0; cols < 3; cols++) {
            for (int rows = 0; rows < 3; rows++) {
                addSlotToContainer(new Slot(tile, rows + cols * 3, 116 + rows * 18, 17 + cols * 18));
            }
        }

        for (int cols = 0; cols < 3; cols++) {
            for (int rows = 0; rows < 9; rows++) {
                addSlot(new Slot(invPlayer, rows + cols * 9 + 9, 8 + rows * 18, 84 + cols * 18));
            }
        }

        for (int rows = 0; rows < 9; rows++) {
            addSlot(new Slot(invPlayer, rows, 8 + rows * 18, 142));
        }
    }
}
