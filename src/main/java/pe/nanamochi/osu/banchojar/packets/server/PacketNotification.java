package pe.nanamochi.osu.banchojar.packets.server;

import pe.nanamochi.osu.banchojar.entities.db.User;
import pe.nanamochi.osu.banchojar.packets.io.ByteDataInputStream;
import pe.nanamochi.osu.banchojar.packets.io.ByteDataOutputStream;
import pe.nanamochi.osu.banchojar.packets.Packet;
import pe.nanamochi.osu.banchojar.utils.DataUtils;

import java.io.IOException;

public class PacketNotification extends Packet {

    public String message;

    public PacketNotification(){
    }

    public PacketNotification(String message){
        this.message = message;
    }

    @Override
    public void read(ByteDataInputStream stream, int length) throws IOException {
        message = stream.readString();
    }

    @Override
    public void write(ByteDataOutputStream stream) throws IOException {
        stream.writeString(message);
    }

    @Override
    public int size(User user) {
        return DataUtils.stringLen(message);
    }
}
