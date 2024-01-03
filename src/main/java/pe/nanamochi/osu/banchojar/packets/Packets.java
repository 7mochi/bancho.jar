package pe.nanamochi.osu.banchojar.packets;

import pe.nanamochi.osu.banchojar.packets.server.*;

public enum Packets {
    PACKET_USER_ID      (5, PacketUserID.class),
    PACKET_SEND_MESSAGE (7, PacketSendMessage.class),
    PACKET_NOTIFICATION (24, PacketNotification .class),
    PACKET_PRIVILEGES   (47, PacketPrivileges.class),
    PACKET_CHANNEL_JOIN_SUCCESS (64, PacketChannelJoinSuccess.class),
    PACKET_CHANNEL_INFO     (65, PacketChannelInfo.class),
    PACKET_PROTOCOL_VERSION (75, PacketProtocolVersion.class),

    PACKET_USER_PRESENCE        (83, PacketUserPresence.class),
    PACKET_RESTART              (86, PacketRestart.class),
    PACKET_CHANNEL_INFO_END (89, PacketChannelInfoEnd.class);

    private int id;
    private Class<? extends Packet> klass;

    Packets(int id, Class<? extends Packet> klass) {
        this.id = id;
        this.klass = klass;
    }

    public static Class<? extends Packet> getById(int id) {
        for (Packets packet : Packets.values())
            if (packet.id == id)
                return packet.klass;
        return null;
    }

    public static int getId(Packet packet) {
        for (Packets p : Packets.values())
            if (p.klass == packet.getClass())
                return p.id;
        return -1;
    }
}
