package memes.net.packet;

import memes.game.world.World;

import java.io.Serializable;

public class WorldPacket extends Packet implements Serializable {
    private World world;
    private long serverID;

    public WorldPacket(World world, long serverID) {
        this.world = world;
        this.serverID = serverID;
    }

    public World getWorld() {
        return world;
    }
    public long getServerID() {
        return serverID;
    }
    @Override
    public PacketType getPacketType() {
        return PacketType.World;
    }

    @Override
    public String toString() {
        return "WorldPacket{" +
                "world=" + world +
                ", serverID=" + serverID +
                "} " + super.toString();
    }
}
