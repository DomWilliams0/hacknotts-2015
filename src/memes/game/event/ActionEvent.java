package memes.game.event;

import memes.game.entity.PlayerEntity;
import memes.game.world.Tile;
import memes.net.packet.Packet;
import memes.net.packet.PacketType;

import java.io.Serializable;

public class ActionEvent extends Packet implements Serializable {
    public PlayerEntity player;
    public Tile tile;
    public boolean flag;

    public ActionEvent(PlayerEntity player, Tile tile, boolean flag) {
        this.player = player;
        this.tile = tile;
        this.flag = flag;
    }

    @Override
    public PacketType getPacketType() {
        return PacketType.Action;
    }
}
