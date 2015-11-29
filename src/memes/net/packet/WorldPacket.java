package memes.net.packet;

import memes.game.world.World;

import java.io.Serializable;

public class WorldPacket implements Packet, Serializable {
    private World world;
    private long sendTime;

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
    public long getSendTime() {
        return sendTime;
    }

    @Override
    public void setSendTime(long time) {
        sendTime = time;
    }
}
