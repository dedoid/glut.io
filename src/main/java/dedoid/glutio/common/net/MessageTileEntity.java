package dedoid.glutio.common.net;


import com.google.common.reflect.TypeToken;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public abstract class MessageTileEntity<T extends TileEntity> implements IMessage {

    protected BlockPos pos;

    protected MessageTileEntity() {

    }

    protected MessageTileEntity(T tile) {
       pos = tile.getPos();
    }

    public void toBytes(ByteBuf buf) {
        buf.writeInt(pos.getX());
        buf.writeInt(pos.getY());
        buf.writeInt(pos.getZ());
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
    }

    @SuppressWarnings("unchecked")
    protected T getTileEntity(World world) {
        if (world == null) {
            return null;
        }

        TileEntity tile = world.getTileEntity(pos);

        if (tile == null) {
            return null;
        }

        TypeToken<?> teType = TypeToken.of(getClass()).resolveType(MessageTileEntity.class.getTypeParameters()[0]);

        if (teType.isAssignableFrom(tile.getClass())) {
            return (T) tile;
        }

        return null;
    }
}

