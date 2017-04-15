package dedoid.glutio.common.core.util;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class FakeNetHandlerPlayServer extends NetHandlerPlayServer {

    public FakeNetHandlerPlayServer(EntityPlayerMP player) {
        super(FMLCommonHandler.instance().getMinecraftServerInstance(), new net.minecraft.network.NetworkManager(EnumPacketDirection.CLIENTBOUND), player);
    }

    @Override
    public NetworkManager getNetworkManager() {
        return null;
    }

    @Override
    public void processInput(CPacketInput packet) {
    }

    @Override
    public void processPlayer(CPacketPlayer packet) {
    }

    @Override
    public void setPlayerLocation(double x, double y, double z, float yaw, float pitch) {
    }

    @Override
    public void processPlayerDigging(CPacketPlayerDigging packet) {
    }


    @Override
    public void onDisconnect(ITextComponent packet) {
    }

    @Override
    public void sendPacket(Packet<?> packet) {
    }

    @Override
    public void processHeldItemChange(CPacketHeldItemChange packet) {
    }

    @Override
    public void processChatMessage(CPacketChatMessage packet) {
    }

    @Override
    public void handleAnimation(CPacketAnimation packet) {

    }

    @Override
    public void processEntityAction(CPacketEntityAction packet) {
    }

    @Override
    public void processUseEntity(CPacketUseEntity packet) {
    }

    @Override
    public void processClientStatus(CPacketClientStatus packet) {
    }

    @Override
    public void processCloseWindow(CPacketCloseWindow packet) {
    }

    @Override
    public void processClickWindow(CPacketClickWindow packet) {
    }

    @Override
    public void processEnchantItem(CPacketEnchantItem packet) {
    }

    @Override
    public void processCreativeInventoryAction(CPacketCreativeInventoryAction packet) {
    }

    @Override
    public void processConfirmTransaction(CPacketConfirmTransaction packet) {
    }

    @Override
    public void processUpdateSign(CPacketUpdateSign packet) {
    }

    @Override
    public void processKeepAlive(CPacketKeepAlive packet) {
    }

    @Override
    public void processPlayerAbilities(CPacketPlayerAbilities packet) {
    }

    @Override
    public void processTabComplete(CPacketTabComplete packet) {
    }

    @Override
    public void processClientSettings(CPacketClientSettings packet) {
    }

    @Override
    public void handleSpectate(CPacketSpectate packet) {
    }

    @Override
    public void handleResourcePackStatus(CPacketResourcePackStatus packet) {
    }
}
