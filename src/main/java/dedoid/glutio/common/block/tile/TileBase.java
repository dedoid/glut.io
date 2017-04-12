package dedoid.glutio.common.block.tile;

import net.minecraft.tileentity.TileEntity;

public class TileBase extends TileEntity {

    public TileBase() {

    }

    public String getName() {
        return getBlockType().getUnlocalizedName();
    }
}
