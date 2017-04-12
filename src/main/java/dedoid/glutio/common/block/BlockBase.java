package dedoid.glutio.common.block;

import dedoid.glutio.common.item.block.ItemBlockBase;
import dedoid.glutio.common.core.CreativeTabGlutIO;
import net.minecraft.block.Block;;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockBase extends Block {

    public BlockBase(String name) {
        this(Material.ROCK, name);
    }

    public BlockBase(Material material, String name) {
        super(material);

        setUnlocalizedName(name);
        setRegistryName(name);

        GameRegistry.register(this);
        GameRegistry.register(new ItemBlockBase(this), getRegistryName());

        setCreativeTab(CreativeTabGlutIO.INSTANCE);
    }
}
