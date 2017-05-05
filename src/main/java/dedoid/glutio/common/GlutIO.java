package dedoid.glutio.common;

import dedoid.glutio.client.core.handler.GuiHandler;
import dedoid.glutio.common.block.ModBlocks;
import dedoid.glutio.common.core.proxy.IProxy;
import dedoid.glutio.common.item.ModItems;
import dedoid.glutio.common.lib.LibMisc;
import dedoid.glutio.common.net.PacketHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod(modid = LibMisc.MOD_ID, name = LibMisc.MOD_NAME, version = LibMisc.VERSION)
public class GlutIO {

    @Mod.Instance(LibMisc.MOD_ID)
    public static GlutIO INSTANCE;

    @SidedProxy(clientSide = LibMisc.PROXY_CLIENT, serverSide = LibMisc.PROXY_SERVER)
    public static IProxy proxy;

    public static PacketHandler packetHandler = new PacketHandler();

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ModBlocks.init();
        ModItems.init();

        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(INSTANCE, new GuiHandler());

        packetHandler.init();

        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

        proxy.postInit(event);
    }
}
