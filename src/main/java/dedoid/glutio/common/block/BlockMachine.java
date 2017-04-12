package dedoid.glutio.common.block;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockMachine extends BlockBase implements ITileEntityProvider {

    public BlockMachine(String name) {
        super(Material.IRON, name);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return null;
    }
}
