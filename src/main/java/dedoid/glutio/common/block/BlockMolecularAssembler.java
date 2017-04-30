package dedoid.glutio.common.block;

import dedoid.glutio.client.lib.LibGuiIDs;
import dedoid.glutio.common.GlutIO;
import dedoid.glutio.common.block.tile.TileMolecularAssembler;
import dedoid.glutio.common.lib.LibBlockNames;
import dedoid.glutio.common.net.PacketHandler;
import dedoid.glutio.common.net.PacketMolecularAssembler;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BlockMolecularAssembler extends BlockMachine {

    public BlockMolecularAssembler() {
        super(LibBlockNames.MOLECULAR_ASSEMBLER);

        PacketHandler.INSTANCE.registerMessage(PacketMolecularAssembler.class, PacketMolecularAssembler.class, PacketHandler.nextID(), Side.SERVER);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileMolecularAssembler();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote && !player.isSneaking()) {
            player.openGui(GlutIO.INSTANCE, LibGuiIDs.MOLECULAR_ASSEMBLER, world, pos.getX(), pos.getY(), pos.getZ());
        }

        return true;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileMolecularAssembler tile = (TileMolecularAssembler) world.getTileEntity(pos);

        if (tile != null) {
            for (int i = 0; i < 9; i++) {
                if (!tile.getStackInSlot(i).isEmpty()) {
                    EntityItem item = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), tile.getStackInSlot(i));

                    world.spawnEntity(item);
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @Nonnull
    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }
}
