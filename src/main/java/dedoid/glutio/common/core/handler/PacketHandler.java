package dedoid.glutio.common.core.handler;

import dedoid.glutio.common.lib.LibMisc;
import dedoid.glutio.common.net.MessageMolecularAssembler;
import dedoid.glutio.common.net.MessageTeleportTablet;
import dedoid.glutio.common.net.PacketMolecularAssembler;
import dedoid.glutio.common.net.PacketTeleportTablet;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {

    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(LibMisc.MOD_ID);

    public static void init() {
        INSTANCE.registerMessage(PacketMolecularAssembler.class, MessageMolecularAssembler.class, 0, Side.SERVER);
        INSTANCE.registerMessage(PacketTeleportTablet.class, MessageTeleportTablet.class, 1, Side.SERVER);
    }

    public static void sendTo(IMessage message, EntityPlayerMP player) {
        INSTANCE.sendTo(message, player);
    }

    public static void sendToAll(IMessage message) {
        MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();

        for (EntityPlayerMP player : server.getPlayerList().getPlayers()) {
            sendTo(message, player);
        }
    }

    public static void sendToAllAround(IMessage message, NetworkRegistry.TargetPoint point) {
        INSTANCE.sendToAllAround(message, point);
    }

    public static void sendToDimension(IMessage message, int dimension) {
        INSTANCE.sendToDimension(message, dimension);
    }

    public static void sendToServer(IMessage message) {
        INSTANCE.sendToServer(message);
    }
}
