package pe.nanamochi.osu.banchojar.packets.server;

import pe.nanamochi.osu.banchojar.entities.db.User;
import pe.nanamochi.osu.banchojar.packets.io.ByteDataInputStream;
import pe.nanamochi.osu.banchojar.packets.io.ByteDataOutputStream;
import pe.nanamochi.osu.banchojar.packets.Packet;

import java.io.IOException;

public class PacketProtocolVersion extends Packet {
    private int protocolVersion;

    public PacketProtocolVersion() {}

    public PacketProtocolVersion(int protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    @Override
    public void read(ByteDataInputStream stream, int length) throws IOException {
        protocolVersion = stream.readInt();
    }

    @Override
    public void write(ByteDataOutputStream stream) throws IOException {
        stream.writeInt(protocolVersion);
    }

    @Override
    public int size(User user) {
        return 4;
    }
}
