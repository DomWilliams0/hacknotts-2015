package memes.game.event;

import memes.game.entity.PlayerEntity;
import memes.game.input.InputKey;
import memes.net.packet.Packet;
import memes.net.packet.PacketType;

import java.io.Serializable;

public class InputEvent implements Packet, Serializable {

    private InputKey key;
    private boolean pressed;
    private long playerID;

    public InputEvent(InputKey key, boolean pressed, long playerID) {
        this.key = key;
        this.pressed = pressed;
        this.playerID = playerID;
    }

    public long getPlayerID() {
        return playerID;
    }

    public InputKey getKey() {
        return key;
    }

    public boolean isPressed() {
        return pressed;
    }

    @Override
    public PacketType getPacketType() {
        return PacketType.Input;
    }

    @Override
    public String toString() {
        return "InputEvent{" +
                "key=" + key +
                ", pressed=" + pressed +
                '}';
    }
}
