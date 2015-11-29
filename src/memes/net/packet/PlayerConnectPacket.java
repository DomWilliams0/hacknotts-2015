package memes.net.packet;

import java.io.Serializable;

public class PlayerConnectPacket implements Packet, Serializable {
    private String username;
    private long entityID;

    public PlayerConnectPacket(long entityID, String username) {
        this.username = username;
        this.entityID = entityID;
    }

    public String getUsername() {
        return username;
    }
    public long getID() {
        return entityID;
    }

    @Override
    public PacketType getPacketType() {
        return PacketType.Connect;
    }
}
