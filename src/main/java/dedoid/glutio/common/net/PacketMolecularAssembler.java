package dedoid.glutio.common.net;

import dedoid.glutio.common.block.tile.TileMolecularAssembler;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketMolecularAssembler implements IMessageHandler<MessageMolecularAssembler, IMessage> {

    @Override
    public IMessage onMessage(MessageMolecularAssembler message, MessageContext context) {
        World world = context.getServerHandler().playerEntity.world;
        TileMolecularAssembler tile = (TileMolecularAssembler) world.getTileEntity(message.pos);

        if(tile != null) {
            for (int i = 0; i < 9; i++) {
                tile.getCraftingMatrix().setInventorySlotContents(i, tile.getPhantomGrid().getStackInSlot(i));
            }

            ItemStack recipe = CraftingManager.getInstance().findMatchingRecipe(tile.getCraftingMatrix(), tile.getWorld());

            tile.setRecipe(recipe);
            tile.getCraftResult().setInventorySlotContents(0, recipe);

            tile.markDirty();
        }

        return null;
    }
}
