package dedoid.glutio.common.net;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class MessageTeleportTablet implements IMessage {

    public int packetID;
    public String name;

    public MessageTeleportTablet() {

    }

    public MessageTeleportTablet(int packetID, String name) {
        this.packetID = packetID;
        this.name = name;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        packetID = buf.readInt();

        switch(packetID) {
            case 0:
                name = ByteBufUtils.readUTF8String(buf);
                break;
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(packetID);

        switch(packetID) {
            case 0:
                ByteBufUtils.writeUTF8String(buf, name);
                break;
        }
    }
}
