package memes.net.packet;

import memes.game.entity.PlayerEntity;

import java.io.Serializable;

public class ConnectPacket extends Packet implements Serializable {
    private PlayerEntity player;

    public ConnectPacket(PlayerEntity entity) {
        this.player = entity;
    }

    public PlayerEntity getPlayer() {
        return player;
    }

    @Override
    public PacketType getPacketType() {
        return PacketType.Connect;
    }

    @Override
    public String toString() {
        return "ConnectPacket{" +
                "player=" + player +
                '}';
    }
}
