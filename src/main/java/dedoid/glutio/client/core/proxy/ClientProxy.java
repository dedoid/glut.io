package dedoid.glutio.client.core.proxy;

import dedoid.glutio.client.render.RenderTileMolecularAssembler;
import dedoid.glutio.common.block.tile.TileMolecularAssembler;
import dedoid.glutio.common.core.proxy.IProxy;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy implements IProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        ClientRegistry.bindTileEntitySpecialRenderer(TileMolecularAssembler.class, new RenderTileMolecularAssembler());
    }

    @Override
    public void init(FMLInitializationEvent event) {

    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {

    }
}
