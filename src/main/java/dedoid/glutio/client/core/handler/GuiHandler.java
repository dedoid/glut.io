package dedoid.glutio.client.core.handler;

import dedoid.glutio.client.container.ContainerMolecularFabricator;
import dedoid.glutio.client.gui.GuiMolecularFabricator;
import dedoid.glutio.client.lib.LibGuiIDs;
import dedoid.glutio.common.block.tile.TileMolecularFabricator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == LibGuiIDs.MOLECULAR_FABRICATOR) {
            return new ContainerMolecularFabricator(player.inventory, (TileMolecularFabricator) world.getTileEntity(new BlockPos(x, y, z)));
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == LibGuiIDs.MOLECULAR_FABRICATOR) {
            return new GuiMolecularFabricator(player.inventory, (TileMolecularFabricator) world.getTileEntity(new BlockPos(x, y, z)));
        }

        return null;
    }
}
