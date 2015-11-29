package memes.game.event;

import memes.game.entity.PlayerEntity;
import memes.net.packet.Packet;
import memes.net.packet.PacketType;
import memes.game.world.Tile;

import java.io.Serializable;

public class ActionEvent implements Packet, Serializable {

    public PlayerEntity player;
    public Tile tile;

    public ActionEvent(PlayerEntity player, Tile tile) {
        this.player = player;
        this.tile = tile;
    }

    @Override
    public PacketType getPacketType() {
        return PacketType.Action;
    }

}
