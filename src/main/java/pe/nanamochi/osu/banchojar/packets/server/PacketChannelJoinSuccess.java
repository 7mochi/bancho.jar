package pe.nanamochi.osu.banchojar.packets.server;

import pe.nanamochi.osu.banchojar.entities.db.User;
import pe.nanamochi.osu.banchojar.packets.io.ByteDataInputStream;
import pe.nanamochi.osu.banchojar.packets.io.ByteDataOutputStream;
import pe.nanamochi.osu.banchojar.packets.Packet;
import pe.nanamochi.osu.banchojar.utils.DataUtils;

import java.io.IOException;

public class PacketChannelJoinSuccess extends Packet {
    public String channel;

    public PacketChannelJoinSuccess() {}

    public PacketChannelJoinSuccess(String channel) {
        this.channel = channel;
    }

    @Override
    public void read(ByteDataInputStream stream, int length) throws IOException {
        channel = stream.readString();
    }

    @Override
    public void write(ByteDataOutputStream stream) throws IOException {
        stream.writeString(channel);
    }

    @Override
    public int size(User user) {
        return DataUtils.stringLen(channel);
    }
}
