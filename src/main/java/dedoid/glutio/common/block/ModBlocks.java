package dedoid.glutio.common.block;

import dedoid.glutio.client.lib.LibResources;
import dedoid.glutio.common.block.tile.TileMolecularAssembler;
import dedoid.glutio.common.lib.LibBlockNames;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks {

    public static Block molecularAssembler;

    public static void init() {
        molecularAssembler = new BlockMolecularAssembler();

        initTiles();
    }

    public static void initTiles() {
        registerTile(TileMolecularAssembler.class, LibBlockNames.MOLECULAR_ASSEMBLER);
    }

    private static void registerTile(Class<? extends TileEntity> clazz, String key) {
        GameRegistry.registerTileEntity(clazz, LibResources.PREFIX_MOD + key);
    }
}
