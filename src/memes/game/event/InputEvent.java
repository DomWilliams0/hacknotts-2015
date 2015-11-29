package memes.game.event;

import memes.game.input.InputKey;
import memes.net.packet.Packet;
import memes.net.packet.PacketType;

import java.io.Serializable;

public class InputEvent implements Packet, Serializable {

    private InputKey key;
    private boolean pressed;

    public InputEvent(InputKey key, boolean pressed) {
        this.key = key;
        this.pressed = pressed;
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
