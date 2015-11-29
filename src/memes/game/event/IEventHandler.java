package memes.game.event;

import memes.net.packet.Packet;

public interface IEventHandler {

    void onEvent(Packet event);

}
