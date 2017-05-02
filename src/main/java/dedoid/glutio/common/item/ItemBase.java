package dedoid.glutio.common.item;

import dedoid.glutio.common.core.CreativeTabGlutIO;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemBase extends Item {

    public ItemBase(String name) {
        setUnlocalizedName(name);
        setRegistryName(name);

        GameRegistry.register(this);

        setCreativeTab(CreativeTabGlutIO.INSTANCE);
    }
}
