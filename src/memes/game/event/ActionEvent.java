package memes.game.event;

import memes.game.entity.PlayerEntity;
import memes.net.packet.Packet;
import memes.net.packet.PacketType;
import memes.world.Tile;

public class ActionEvent implements Packet {

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
