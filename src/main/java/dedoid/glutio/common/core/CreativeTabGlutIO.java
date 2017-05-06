package dedoid.glutio.common.core;

import dedoid.glutio.client.lib.LibResources;
import dedoid.glutio.common.item.ModItems;
import dedoid.glutio.common.lib.LibMisc;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class CreativeTabGlutIO extends CreativeTabs {

    public static final CreativeTabGlutIO INSTANCE = new CreativeTabGlutIO();

    public CreativeTabGlutIO() {
        super(LibMisc.MOD_ID);

        setBackgroundImageName(LibResources.GUI_CREATIVE);
    }

    @Nonnull
    @Override
    public ItemStack getIconItemStack() {
        return new ItemStack(ModItems.teleportTablet);
    }

    @Nonnull
    @Override
    public ItemStack getTabIconItem() {
        return getIconItemStack();
    }

    @Override
    public boolean hasSearchBar() {
        return true;
    }
}
