package memes.net.packet;

public interface Packet {
    /**
     * Gets the Type of the Packet, useful for casting
     * @return type of the packet
     */
    PacketType getPacketType();
}
