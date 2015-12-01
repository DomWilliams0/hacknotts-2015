package memes.net.packet;

import memes.game.world.World;

import java.io.Serializable;

public class WorldPacket extends Packet implements Serializable {
    private World world;

    public WorldPacket(World world) {
        this.world = world;
    }

    public World getWorld() {
        return world;
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
