package dedoid.glutio.common.net;

import dedoid.glutio.common.core.util.GlutTeleporter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketTeleportTablet implements IMessageHandler<MessageTeleportTablet, IMessage> {

    @Override
    public IMessage onMessage(MessageTeleportTablet message, MessageContext context) {
        EntityPlayer player = context.getServerHandler().playerEntity;
        ItemStack stack = player.getHeldItemMainhand();

        switch (message.packetID) {
            case 0:
                stack.setStackDisplayName(message.name);

                break;
            case 1:
                FMLCommonHandler.instance().getWorldThread(context.netHandler).addScheduledTask(() -> teleport(player, stack));

                break;
        }

        return null;
    }

    private void teleport(EntityPlayer player, ItemStack stack) {
        NBTTagCompound tagCompound = stack.getTagCompound();

        int x = tagCompound.getInteger("X");
        int y = tagCompound.getInteger("Y");
        int z = tagCompound.getInteger("Z");
        int dimension = tagCompound.getInteger("Dimension");
        int prevDimension = player.getEntityWorld().provider.getDimension();

        if (prevDimension != dimension) {
            EntityPlayerMP playerMP = (EntityPlayerMP) player;
            MinecraftServer server = player.getEntityWorld().getMinecraftServer();
            WorldServer worldServer = server.worldServerForDimension(dimension);

            player.addExperienceLevel(0);

            worldServer.getMinecraftServer().getPlayerList().transferPlayerToDimension(playerMP, dimension, new GlutTeleporter(worldServer, x, y, z));
            player.setPositionAndUpdate(x, y, z);

            if (prevDimension == 1) {
                player.setPositionAndUpdate(x, y, z);
                //WorldTools.spawnEntity(worldServer, player);
                worldServer.updateEntityWithOptionalForce(player, false);
            }
        } else {
            player.setPositionAndUpdate(x + 0.5, y + 1.5, z + 0.5);
        }
    }
}
