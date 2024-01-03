package pe.nanamochi.osu.banchojar.entities;

import lombok.Getter;
import pe.nanamochi.osu.banchojar.entities.db.User;
import pe.nanamochi.osu.banchojar.packets.server.PacketChannelInfo;
import pe.nanamochi.osu.banchojar.packets.server.PacketChannelJoinSuccess;

import java.util.ArrayList;
import java.util.List;

public class Channel {
    @Getter
    private String name;
    @Getter
    private String description;
    private List<User> users = new ArrayList<>();

    public Channel(String name, String description) {
        this.name = "#" + name;
        this.description = description;
    }

    public void join(User user) {
        if (!users.contains(user)) {
            users.add(user);
            user.sendPacket(new PacketChannelInfo(name, description, 1));
            user.sendPacket(new PacketChannelJoinSuccess(name));
        }
    }

    public void leave(User user) {
        if (users.contains(user)) {
            users.remove(user);
        }
    }

    public void sendMessage(User sender, String message) {
        for (User user : users)
            if (user != sender) {
                user.sendChatPacket(this, sender, message);
            }
    }
}
