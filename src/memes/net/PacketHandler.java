package memes.net;

import memes.net.packet.Packet;

public interface PacketHandler {

    /**
     * Called by the Server and Client
     * every time a packet is recieved
     * @param packet packet to process
     */
    void onPacketRecieve(Packet packet);
}
