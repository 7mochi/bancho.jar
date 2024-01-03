package pe.nanamochi.osu.banchojar.packets.server;

import pe.nanamochi.osu.banchojar.entities.db.User;
import pe.nanamochi.osu.banchojar.packets.io.ByteDataInputStream;
import pe.nanamochi.osu.banchojar.packets.io.ByteDataOutputStream;
import pe.nanamochi.osu.banchojar.packets.Packet;

import java.io.IOException;

public class PacketUserID extends Packet {
    public int userID;

    public PacketUserID() {}

    public PacketUserID(int statusCode) {
        this.userID = statusCode;
    }

    @Override
    public void read(ByteDataInputStream stream, int length) throws IOException {
        userID = stream.readInt();
    }

    @Override
    public void write(ByteDataOutputStream stream) throws IOException {
        stream.writeInt(userID);
    }

    @Override
    public int size(User user) {
        return 4;
    }

    public int getUserId() {
        return userID;
    }

    public Status getStatus() {
        return Status.fromId(userID);
    }

    public enum Status {
        RECEIVING_DATA,
        AUTH_FAILED,
        OLD_VERSION,
        BANNED,
        ERROR_OCCURRED,
        SUPPORTER_REQUIRED,
        PASSWORD_RESET,
        VERIFICATION_REQUIRED;

        public static Status fromId(int id) {
            return switch (id) {
                case -8 -> VERIFICATION_REQUIRED;
                case -7 -> PASSWORD_RESET;
                case -6 -> SUPPORTER_REQUIRED;
                case -5 -> ERROR_OCCURRED;
                case -4, -3 -> BANNED;
                case -2 -> OLD_VERSION;
                case -1 -> AUTH_FAILED;
                default -> RECEIVING_DATA;
            };
        }
    }
}
