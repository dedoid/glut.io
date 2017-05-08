package dedoid.glutio.common.item;

import dedoid.glutio.client.lib.LibGuiIDs;
import dedoid.glutio.common.GlutIO;
import dedoid.glutio.common.inventory.InventoryNullifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;

public class ItemNullifier extends ItemBase {

    public InventoryNullifier inventory;

    public ItemNullifier(String name) {
        super(name);

        inventory = new InventoryNullifier();

        setMaxStackSize(1);
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
        if (hand == EnumHand.OFF_HAND) {
            return ActionResult.newResult(EnumActionResult.PASS, player.getHeldItemOffhand());
        }

        ItemStack stack = player.getHeldItemMainhand();

        player.openGui(GlutIO.INSTANCE, LibGuiIDs.NULLIFIER, world, player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ());

        return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
    }

    @SubscribeEvent
    public void onItemPickup(EntityItemPickupEvent event) {
        System.out.println("pepe");

        EntityPlayer player = event.getEntityPlayer();
        ItemStack stack = event.getItem().getEntityItem();

        if (player == null || stack.isEmpty()) {
            return;
        }

        for (int i = 0; i < player.inventory.getSizeInventory(); i++) {

        }
    }
}
