package memes.net.packet;

import memes.util.Point;

import java.io.Serializable;

public class ConnectRequest extends Packet implements Serializable {
    private String username;
    private long entityID;

    public ConnectRequest(long entityID, String username) {
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
        return PacketType.ConnectReq;
    }
}
