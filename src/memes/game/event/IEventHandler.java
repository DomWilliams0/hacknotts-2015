package memes.game.event;

import memes.net.packet.Packet;

public interface IEventHandler {

    void onPacketReceive(Packet event);

}
