package memes.game.event;

import memes.game.entity.PlayerEntity;
import memes.net.packet.Packet;
import memes.net.packet.PacketType;
import memes.game.world.Tile;

import java.io.Serializable;

public class ActionEvent implements Packet, Serializable {

    public PlayerEntity player;
    public Tile tile;
    public boolean flag;
    private long sendTime;

    public ActionEvent(PlayerEntity player, Tile tile, boolean flag) {
        this.player = player;
        this.tile = tile;
        this.flag = flag;
    }

    @Override
    public PacketType getPacketType() {
        return PacketType.Action;
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
