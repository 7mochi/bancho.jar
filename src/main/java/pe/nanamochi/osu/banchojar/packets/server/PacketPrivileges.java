package pe.nanamochi.osu.banchojar.packets.server;

import pe.nanamochi.osu.banchojar.entities.db.User;
import pe.nanamochi.osu.banchojar.packets.io.ByteDataInputStream;
import pe.nanamochi.osu.banchojar.packets.io.ByteDataOutputStream;
import pe.nanamochi.osu.banchojar.packets.Packet;

import java.io.IOException;

public class PacketPrivileges extends Packet {
    int flags;

    public PacketPrivileges() {}

    public PacketPrivileges(int flags) {
        this.flags = flags;
    }

    @Override
    public void read(ByteDataInputStream stream, int length) throws IOException {
        flags = stream.readInt();
    }

    @Override
    public void write(ByteDataOutputStream stream) throws IOException {
        stream.write(flags);
    }

    @Override
    public int size(User user) {
        return 4;
    }
}
