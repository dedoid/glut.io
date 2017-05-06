package dedoid.glutio.common.item;

import dedoid.glutio.client.lib.LibGuiIDs;
import dedoid.glutio.common.GlutIO;
import dedoid.glutio.common.lib.LibItemNames;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class ItemTeleportTablet extends ItemBase {

    public ItemTeleportTablet(String name) {
        super(name);

        setHasSubtypes(true);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(@Nonnull Item item, CreativeTabs tab, NonNullList<ItemStack> stacks) {
        stacks.add(new ItemStack(item));
        stacks.add(new ItemStack(item, 1, 1));
    }

    @Nonnull
    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return "item." + LibItemNames.TELEPORT_TABLET[stack.getItemDamage()];
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
        ItemStack stack = player.getHeldItemMainhand();

        NBTTagCompound tagCompound = stack.getTagCompound();

        if (stack.getItemDamage() == 0) {
            if (player.isSneaking()) {
                if (tagCompound == null) {
                    tagCompound = new NBTTagCompound();

                    tagCompound.setInteger("X", player.getPosition().getX());
                    tagCompound.setInteger("Y", player.getPosition().getY());
                    tagCompound.setInteger("Z", player.getPosition().getZ());
                    tagCompound.setInteger("Dimension", player.dimension);

                    stack.setItemDamage(1);
                    stack.setTagCompound(tagCompound);
                }
            }
        } else if (stack.getItemDamage() == 1) {
            if (tagCompound != null) {
                if (!world.isRemote) {
                    player.openGui(GlutIO.INSTANCE, LibGuiIDs.TELEPORT_TABLET, world, player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ());
                }
            }
        }

        return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerModels() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(this, 1, new ModelResourceLocation(getRegistryName(), "inventory"));
    }
}
