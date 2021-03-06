package dedoid.glutio.client.core.handler;

import dedoid.glutio.client.gui.GuiMolecularAssembler;
import dedoid.glutio.client.gui.GuiNullifier;
import dedoid.glutio.client.gui.GuiTeleportTablet;
import dedoid.glutio.client.lib.LibGuiIDs;
import dedoid.glutio.common.block.tile.TileMolecularAssembler;
import dedoid.glutio.common.inventory.container.ContainerBase;
import dedoid.glutio.common.inventory.container.ContainerMolecularAssembler;
import dedoid.glutio.common.inventory.container.ContainerNullifier;
import dedoid.glutio.common.item.ItemNullifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == LibGuiIDs.MOLECULAR_ASSEMBLER) {
            return new ContainerMolecularAssembler(player.inventory, (TileMolecularAssembler) world.getTileEntity(new BlockPos(x, y, z)));
        }

        if (ID == LibGuiIDs.TELEPORT_TABLET) {
            return new ContainerBase();
        }

        if (ID == LibGuiIDs.NULLIFIER) {
            return new ContainerNullifier(player.inventory, (ItemNullifier) player.getHeldItemMainhand().getItem());
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == LibGuiIDs.MOLECULAR_ASSEMBLER) {
            return new GuiMolecularAssembler(player.inventory, (TileMolecularAssembler) world.getTileEntity(new BlockPos(x, y, z)));
        }

        if (ID == LibGuiIDs.TELEPORT_TABLET) {
            return new GuiTeleportTablet(player.getHeldItemMainhand());
        }

        if (ID == LibGuiIDs.NULLIFIER) {
            return new GuiNullifier(player.inventory, (ItemNullifier) player.getHeldItemMainhand().getItem());
        }

        return null;
    }
}
