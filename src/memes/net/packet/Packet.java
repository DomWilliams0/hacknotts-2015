package memes.net.packet;

public abstract class Packet {
    private long sendTime;

    /**
     * Gets the Type of the Packet, useful for casting
     * @return type of the packet
     */
    public abstract PacketType getPacketType();

    public long getSendTime() {
        return sendTime;
    }
    public void setSendTime(long time) {
        sendTime = time;
    }
}
