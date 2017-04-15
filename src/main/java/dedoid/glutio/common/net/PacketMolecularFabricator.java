package dedoid.glutio.common.net;

import dedoid.glutio.common.block.tile.TileMolecularFabricator;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketMolecularFabricator extends MessageTileEntity<TileMolecularFabricator> implements IMessageHandler<PacketMolecularFabricator, IMessage> {

    private int slot;
    private ItemStack stack;

    public PacketMolecularFabricator() {

    }

    private PacketMolecularFabricator(TileMolecularFabricator tile) {
        super(tile);
    }

    public static PacketMolecularFabricator setSlot(TileMolecularFabricator tile, int slot, ItemStack stack) {
        PacketMolecularFabricator msg = new PacketMolecularFabricator(tile);

        msg.slot = slot;
        msg.stack = stack;
        msg.execute(tile);

        return msg;
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
    public IMessage onMessage(PacketMolecularFabricator msg, MessageContext ctx) {
        TileMolecularFabricator tile = msg.getTileEntity(ctx.getServerHandler().playerEntity.world);

        if(tile != null) {
            msg.execute(tile);
        }

        return null;
    }

    private void execute(TileMolecularFabricator tile) {
        //tile.getCraftingGrid().setInventorySlotContents(slot, stack);
        tile.updateCraftingOutput();
    }
}
