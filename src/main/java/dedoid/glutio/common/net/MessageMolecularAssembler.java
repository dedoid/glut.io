package dedoid.glutio.common.net;

import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class MessageMolecularAssembler implements IMessage {

    public BlockPos pos;
    public int slot;
    public ItemStack stack;

    public MessageMolecularAssembler() {

    }

    public MessageMolecularAssembler(BlockPos pos, int slot, ItemStack stack) {
        this.pos = pos;
        this.slot = slot;
        this.stack = stack;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());

        slot = buf.readShort();

        stack = ByteBufUtils.readItemStack(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(pos.getX());
        buf.writeInt(pos.getY());
        buf.writeInt(pos.getZ());

        buf.writeShort(slot);

        ByteBufUtils.writeItemStack(buf, stack);
    }
}
