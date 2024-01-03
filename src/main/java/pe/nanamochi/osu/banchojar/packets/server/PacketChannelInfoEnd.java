package pe.nanamochi.osu.banchojar.packets.server;

import pe.nanamochi.osu.banchojar.entities.db.User;
import pe.nanamochi.osu.banchojar.packets.io.ByteDataInputStream;
import pe.nanamochi.osu.banchojar.packets.io.ByteDataOutputStream;
import pe.nanamochi.osu.banchojar.packets.Packet;

import java.io.IOException;

public class PacketChannelInfoEnd extends Packet {
    @Override
    public void read(ByteDataInputStream stream, int length) throws IOException {
        // no data
    }

    @Override
    public void write(ByteDataOutputStream stream) throws IOException {
        // no data
    }

    @Override
    public int size(User user) {
        return 0;
    }
}
