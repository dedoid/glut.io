package dedoid.glutio.common.block;

import dedoid.glutio.client.lib.LibGuiIDs;
import dedoid.glutio.common.GlutIO;
import dedoid.glutio.common.block.tile.TileMolecularFabricator;
import dedoid.glutio.common.lib.LibBlockNames;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
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

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase player, ItemStack stack) {
        TileEntity tile = world.getTileEntity(pos);

        if (tile instanceof TileMolecularFabricator) {
            if (stack.getTagCompound() != null) {
                tile.readFromNBT(stack.getTagCompound().getCompoundTag("Data"));
            }
        }

        //facing = MathHelper.floor_double((double)(player.rotationYaw * 4.0F / 360.0F) + 2.5D) & 3;
        //world.setBlockMetadataWithNotify(x, y, z, facing, 2);
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileEntity tile = world.getTileEntity(pos);

        if (tile instanceof TileMolecularFabricator) {
            float f1 = world.rand.nextFloat() * 0.8F + 0.1F + pos.getX();
            float f2 = world.rand.nextFloat() * 0.8F + 0.1F + pos.getY();
            float f3 = world.rand.nextFloat() * 0.8F + 0.1F + pos.getZ();

            NBTTagCompound tagCompound = new NBTTagCompound();

            ItemStack stack = new ItemStack(ModBlocks.molecularFabricator);

            tile.writeToNBT(tagCompound);

            stack.setTagCompound(new NBTTagCompound());
            stack.getTagCompound().setTag("Data", tagCompound);

            EntityItem entityItem = new EntityItem(world, f1, f2, f3, stack.splitStack(1));

            world.spawnEntity(entityItem);
        }
    }
}
