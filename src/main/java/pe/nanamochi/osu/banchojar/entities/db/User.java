package pe.nanamochi.osu.banchojar.entities.db;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import pe.nanamochi.osu.banchojar.entities.Channel;
import pe.nanamochi.osu.banchojar.packets.io.ByteDataOutputStream;
import pe.nanamochi.osu.banchojar.packets.Packet;
import pe.nanamochi.osu.banchojar.packets.Packets;
import pe.nanamochi.osu.banchojar.packets.server.PacketSendMessage;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native")
    @ColumnDefault("3")
    private int id;
    private String username;
    private String passwordMd5;


    @Transient
    private int protocolVersion;
    @Transient
    private final Queue<Packet> packetQueue = new LinkedList<>();

    @Transient
    public void sendPacket(Packet packet) {
        packetQueue.add(packet);
    }

    @Transient
    public void sendData(ByteDataOutputStream stream) throws IOException {
        while (!packetQueue.isEmpty()) {
            Packet packet = packetQueue.poll();

            short id = (short) Packets.getId(packet);
            if (id == -1) {
                System.err.println("Can't find ID for " + packet.getClass());
                continue;
            }
            stream.writeShort(id);
            stream.writeByte((byte) 0);
            stream.writeInt(packet.size(this));
            packet.write(stream);
        }
    }

    @Transient
    public void sendChatPacket(Channel channel, User user, String message) {
        String _channel = channel == null ? user.getUsername() : channel.getName();
        String _user = channel == null ? "" : user.getUsername();
        this.sendPacket(new PacketSendMessage(_user, message, _channel, getId()));
    }
}
