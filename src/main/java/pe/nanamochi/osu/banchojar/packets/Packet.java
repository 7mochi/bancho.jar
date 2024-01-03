package pe.nanamochi.osu.banchojar.packets;

import pe.nanamochi.osu.banchojar.entities.db.User;
import pe.nanamochi.osu.banchojar.packets.io.ByteDataInputStream;
import pe.nanamochi.osu.banchojar.packets.io.ByteDataOutputStream;

import java.io.IOException;

public abstract class Packet {

    public void read(ByteDataInputStream stream, int length) throws IOException {
        throw new UnsupportedOperationException(this.getClass().toString());
    }

    public void write(ByteDataOutputStream stream) throws IOException {
        throw new UnsupportedOperationException(this.getClass().toString());
    }

    public int size(User user) {
        throw new UnsupportedOperationException(this.getClass().toString());
    }
}