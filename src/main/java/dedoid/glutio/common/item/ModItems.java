package dedoid.glutio.common.item;

import dedoid.glutio.common.lib.LibItemNames;
import net.minecraft.item.Item;

public class ModItems {

    public static Item teleportTablet;

    public static void init() {
        teleportTablet = new ItemTeleportTablet(LibItemNames.TELEPORT_TABLET[1]);
    }
}
