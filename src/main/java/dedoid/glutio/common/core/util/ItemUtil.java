package dedoid.glutio.common.core.util;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ItemUtil {

    public static boolean isItemEqual(ItemStack a, ItemStack b, boolean matchDamage, boolean matchNBT) {
        if (a == null || b == null) {
            return false;
        }

        if (!a.isItemEqual(b)) {
            return false;
        }

        if (matchNBT && !ItemStack.areItemStackTagsEqual(a, b)) {
            return false;
        }

        if (matchDamage && a.getHasSubtypes()) {
            if (isWildcard(a) || isWildcard(b)) {
                return true;
            }

            if (a.getItemDamage() != b.getItemDamage()) {
                return false;
            }
        }

        return true;
    }

    public static boolean areStacksMergable(ItemStack a, ItemStack b) {
        if (a.isEmpty() || b.isEmpty() || !a.isStackable() || !b.isStackable()) {
            return false;
        }

        if (a.getCount() == b.getMaxStackSize()) {
            return false;
        }

        if (a.getCount() == b.getMaxStackSize()) {
            return false;
        }

        if (!a.isItemEqual(b)) {
            return false;
        }

        return ItemStack.areItemStackTagsEqual(a, b);
    }

    public static boolean isWildcard(ItemStack stack) {
        return stack.getItemDamage() == -1 || stack.getItemDamage() == OreDictionary.WILDCARD_VALUE;
    }
}
