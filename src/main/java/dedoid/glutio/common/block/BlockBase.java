package dedoid.glutio.common.block;

import dedoid.glutio.client.core.handler.ModelHandler;
import dedoid.glutio.client.render.IModelRegister;
import dedoid.glutio.common.item.block.ItemBlockBase;
import dedoid.glutio.common.core.CreativeTabGlutIO;
import net.minecraft.block.Block;;
import net.minecraft.block.material.Material;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockBase extends Block implements IModelRegister {

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

    @Override
    public void registerModels() {
        if(Item.getItemFromBlock(this) != Items.AIR) {
            ModelHandler.registerBlockToState(this, 0, getDefaultState());
        }
    }
}
