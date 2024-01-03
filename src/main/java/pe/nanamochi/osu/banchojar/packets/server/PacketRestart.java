package pe.nanamochi.osu.banchojar.packets.server;

import pe.nanamochi.osu.banchojar.entities.db.User;
import pe.nanamochi.osu.banchojar.packets.Packet;
import pe.nanamochi.osu.banchojar.packets.io.ByteDataInputStream;
import pe.nanamochi.osu.banchojar.packets.io.ByteDataOutputStream;

import java.io.IOException;

public class PacketRestart extends Packet {
    private int milisecondsUntilRestart;

    public PacketRestart() {
    }

    public PacketRestart(int milisecondsUntilRestart) {
        this.milisecondsUntilRestart = milisecondsUntilRestart;
    }

    @Override
    public void read(ByteDataInputStream stream, int length) throws IOException {
        milisecondsUntilRestart = stream.readInt();
    }

    @Override
    public void write(ByteDataOutputStream stream) throws IOException {
        stream.writeInt(milisecondsUntilRestart);
    }

    @Override
    public int size(User user) {
        return 4;
    }
}
