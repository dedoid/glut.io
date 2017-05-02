package dedoid.glutio.common.item;

import dedoid.glutio.common.core.util.GlutTeleporter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class ItemLinkingTablet extends ItemBase {

    public ItemLinkingTablet(String name) {
        super(name);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        NBTTagCompound tagCompound = stack.getTagCompound();

        if (tagCompound == null) {
            tagCompound = new NBTTagCompound();

            tagCompound.setInteger("X", player.getPosition().getX());
            tagCompound.setInteger("Y", player.getPosition().getY());
            tagCompound.setInteger("Z", player.getPosition().getZ());
            tagCompound.setInteger("Dimension", player.dimension);

            stack.setTagCompound(tagCompound);
        } else {
            if (!world.isRemote) {
                int x = tagCompound.getInteger("X");
                int y = tagCompound.getInteger("Y");
                int z = tagCompound.getInteger("Z");
                int dimension = tagCompound.getInteger("Dimension");
                int prevDimension = player.getEntityWorld().provider.getDimension();

                EntityPlayerMP entityPlayerMP = (EntityPlayerMP) player;
                MinecraftServer server = player.getEntityWorld().getMinecraftServer();
                WorldServer worldServer = server.worldServerForDimension(dimension);
                player.addExperienceLevel(0);

                worldServer.getMinecraftServer().getPlayerList().transferPlayerToDimension(entityPlayerMP, dimension, new GlutTeleporter(worldServer, x, y, z));
                player.setPositionAndUpdate(x, y, z);

                if (prevDimension == 1) {
                    player.setPositionAndUpdate(x, y, z);
                    //TODO: implement
                    //WorldTools.spawnEntity(worldServer, player);
                    worldServer.updateEntityWithOptionalForce(player, false);
                }
            }
        }

        return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
    }
}
