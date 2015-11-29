package memes.game.event;

import memes.net.packet.Packet;

public interface IEventHandler<E extends Packet> {

    void onEvent(E event);

}
