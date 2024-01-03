package pe.nanamochi.osu.banchojar.packets.server;

import pe.nanamochi.osu.banchojar.entities.db.User;
import pe.nanamochi.osu.banchojar.packets.io.ByteDataInputStream;
import pe.nanamochi.osu.banchojar.packets.io.ByteDataOutputStream;
import pe.nanamochi.osu.banchojar.packets.Packet;
import pe.nanamochi.osu.banchojar.utils.DataUtils;

import java.io.IOException;

public class PacketSendMessage extends Packet {
    public String sender;
    public String message;
    public String channel;
    public int userId;

    public PacketSendMessage() {
    }

    public PacketSendMessage(String sender, String message, String channel, int userId) {
        this.sender = sender;
        this.message = message;
        this.channel = channel;
        this.userId = userId;
    }

    @Override
    public void read(ByteDataInputStream stream, int length) throws IOException {
        sender = stream.readString();
        message = stream.readString();
        channel = stream.readString();
        if (stream.getProtocolVersion() > 14) {
            userId = stream.readInt();
        }

    }

    @Override
    public void write(ByteDataOutputStream stream) throws IOException {
        stream.writeString(sender);
        stream.writeString(message);
        stream.writeString(channel);
        if (stream.getProtocolVersion() > 14) {
            stream.writeInt(userId);
        }
    }

    @Override
    public int size(User user) {
        int size = DataUtils.stringLen(sender) + DataUtils.stringLen(message) + DataUtils.stringLen(channel);
        if (user.getProtocolVersion() > 14) {
            size += 4;
        }

        return size;
    }
}
