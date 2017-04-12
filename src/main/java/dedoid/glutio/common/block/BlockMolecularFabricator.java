package dedoid.glutio.common.block;

import dedoid.glutio.client.lib.LibGuiIDs;
import dedoid.glutio.common.GlutIO;
import dedoid.glutio.common.block.tile.TileMolecularFabricator;
import dedoid.glutio.common.lib.LibBlockNames;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockMolecularFabricator extends BlockMachine {

    public BlockMolecularFabricator() {
        super(LibBlockNames.MOLECULAR_FABRICATOR);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileMolecularFabricator();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote && !player.isSneaking()) {
            player.openGui(GlutIO.INSTANCE, LibGuiIDs.MOLECULAR_FABRICATOR, world, pos.getX(), pos.getY(), pos.getZ());
        }

        return true;
    }
}
