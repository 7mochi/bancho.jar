package pe.nanamochi.osu.banchojar.packets.server;

import pe.nanamochi.osu.banchojar.entities.db.User;
import pe.nanamochi.osu.banchojar.packets.io.ByteDataInputStream;
import pe.nanamochi.osu.banchojar.packets.io.ByteDataOutputStream;
import pe.nanamochi.osu.banchojar.packets.Packet;
import pe.nanamochi.osu.banchojar.utils.DataUtils;

import java.io.IOException;

public class PacketChannelInfo extends Packet {

    public String name;
    public String description;
    public int numUsers;

    public PacketChannelInfo() {
    }

    public PacketChannelInfo(String name, String description, int numUsers) {
        this.name = name;
        this.description = description;
        this.numUsers = numUsers;
    }

    @Override
    public void read(ByteDataInputStream stream, int length) throws IOException {
        name = stream.readString();
        description = stream.readString();
        numUsers = stream.readInt();
    }

    @Override
    public void write(ByteDataOutputStream stream) throws IOException {
        stream.writeString(name);
        stream.writeString(description);
        stream.writeInt(numUsers);
    }

    @Override
    public int size(User user) {
        return DataUtils.stringLen(name) + DataUtils.stringLen(description) + 4;
    }
}
