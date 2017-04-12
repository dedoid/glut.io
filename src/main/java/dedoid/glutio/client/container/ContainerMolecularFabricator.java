package dedoid.glutio.client.container;

import dedoid.glutio.client.gui.SlotPhantom;
import dedoid.glutio.client.gui.SlotUntouchable;
import dedoid.glutio.common.block.tile.TileMolecularFabricator;
import net.minecraft.entity.player.InventoryPlayer;

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

        /*for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                addSlotToContainer(new Slot(tile, x + y * 3, 116 + x * 18, 17 + y * 18));
            }
        }*/
    }

    @Override
    public TileMolecularFabricator getTile() {
        return (TileMolecularFabricator) tile;
    }
}
