package dedoid.glutio.common.block;

import dedoid.glutio.client.lib.LibResources;
import dedoid.glutio.common.block.tile.TileMolecularFabricator;
import dedoid.glutio.common.lib.LibBlockNames;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks {

    public static Block molecularFabricator;

    public static void init() {
        molecularFabricator = new BlockMolecularFabricator();

        initTiles();
    }

    public static void initTiles() {
        registerTile(TileMolecularFabricator.class, LibBlockNames.MOLECULAR_FABRICATOR);
    }

    private static void registerTile(Class<? extends TileEntity> clazz, String key) {
        GameRegistry.registerTileEntity(clazz, LibResources.PREFIX_MOD + key);
    }
}
