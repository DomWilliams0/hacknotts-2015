package memes.net.packet;

import java.io.Serializable;

public class PlayerConnectPacket implements Packet, Serializable {
    private String username;
    private long entityID;
    private long sendTime;

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

    @Override
    public long getSendTime() {
        return sendTime;
    }

    @Override
    public void setSendTime(long time) {
        sendTime = time;
    }
}
