package dedoid.glutio.common.item;

import dedoid.glutio.common.lib.LibItemNames;
import net.minecraft.item.Item;

public class ModItems {

    public static Item linkingTablet;

    public static void init() {
        linkingTablet = new ItemLinkingTablet(LibItemNames.LINKING_TABLET);
    }
}
