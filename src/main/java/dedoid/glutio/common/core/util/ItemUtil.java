package dedoid.glutio.common.core.util;

import net.minecraft.item.ItemStack;

public class ItemUtil {

    public static boolean areStackMergable(ItemStack s1, ItemStack s2) {
        if (s1 == ItemStack.EMPTY || s2 == ItemStack.EMPTY || !s1.isStackable() || !s2.isStackable()) {
            return false;
        }

        if (s1.getCount() == s1.getMaxStackSize()) {
            return false;
        }

        if (!s1.isItemEqual(s2)) {
            return false;
        }

        return ItemStack.areItemStackTagsEqual(s1, s2);
    }
}
