package pe.nanamochi.osu.banchojar.packets.server;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import pe.nanamochi.osu.banchojar.entities.db.User;
import pe.nanamochi.osu.banchojar.entities.Gamemode;
import pe.nanamochi.osu.banchojar.packets.io.ByteDataInputStream;
import pe.nanamochi.osu.banchojar.packets.io.ByteDataOutputStream;
import pe.nanamochi.osu.banchojar.packets.Packet;
import pe.nanamochi.osu.banchojar.utils.DataUtils;

import java.io.IOException;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PacketUserPresence extends Packet {
    private boolean bool;
    private int userID;
    private String username;
    private int timezone;
    private byte byte1;
    private float longitude;
    private float latitude;
    private int rank;
    private int userFlags;
    private Gamemode gamemode;

    @Override
    public void read(ByteDataInputStream stream, int length) throws IOException {
        userID = stream.readInt();
        if (userID < 0)
            userID = -userID;
        else
            bool = true;

        username = stream.readString();
        timezone = stream.readByte() - 24;
        byte1 = stream.readByte();
        byte num = stream.readByte();
        userFlags = num & 31;
        gamemode = Gamemode.values()[(num >> 5) & 7];
        longitude = stream.readFloat();
        latitude = stream.readFloat();
        rank = stream.readInt();
    }

    @Override
    public void write(ByteDataOutputStream stream) throws IOException {
        stream.writeInt(bool ? getUserID() : -getUserID());
        stream.writeString(username);
        stream.writeByte((byte) (timezone + 24));
        stream.writeByte(byte1);
        stream.writeByte((byte) (userFlags | (getGamemode().id << 5)));
        stream.writeFloat(longitude);
        stream.writeFloat(latitude);
        stream.writeInt(rank);
    }

    @Override
    public int size(User user) {
        return 4 * 4 + 3 + DataUtils.stringLen(username);
    }
}
