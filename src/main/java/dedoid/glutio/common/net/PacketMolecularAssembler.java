package dedoid.glutio.common.net;

import dedoid.glutio.common.block.tile.TileMolecularAssembler;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketMolecularAssembler extends MessageTileEntity<TileMolecularAssembler> implements IMessageHandler<PacketMolecularAssembler, IMessage> {

    private int slot;
    private ItemStack stack;

    public PacketMolecularAssembler() {

    }

    private PacketMolecularAssembler(TileMolecularAssembler tile) {
        super(tile);
    }

    public static PacketMolecularAssembler setSlot(TileMolecularAssembler tile, int slot, ItemStack stack) {
        PacketMolecularAssembler message = new PacketMolecularAssembler(tile);

        message.slot = slot;
        message.stack = stack;
        message.execute(tile);

        return message;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        super.fromBytes(buf);

        slot = buf.readShort();
        stack = ByteBufUtils.readItemStack(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        super.toBytes(buf);

        buf.writeShort(slot);
        ByteBufUtils.writeItemStack(buf, stack);
    }

    @Override
    public IMessage onMessage(PacketMolecularAssembler message, MessageContext ctx) {
        TileMolecularAssembler tile = message.getTileEntity(ctx.getServerHandler().playerEntity.world);

        if(tile != null) {
            message.execute(tile);
        }

        return null;
    }

    private void execute(TileMolecularAssembler tile) {
        for (int i = 0; i < 9; i++) {
            tile.getCraftingMatrix().setInventorySlotContents(i, tile.getPhantomGrid().getStackInSlot(i));
        }

        ItemStack recipe = CraftingManager.getInstance().findMatchingRecipe(tile.getCraftingMatrix(), tile.getWorld());

        tile.setRecipe(recipe);
        tile.getCraftResult().setInventorySlotContents(0, recipe);

        tile.markDirty();
    }
}
