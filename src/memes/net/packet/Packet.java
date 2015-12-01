package memes.net.packet;

public abstract class Packet {
    private long sendTime, sender, recipient;

    /**
     * Gets the Type of the Packet, useful for casting
     * @return type of the packet
     */
    public abstract PacketType getPacketType();

    public long getSender() {
        return sender;
    }
    public void setSender(long sender) {
        this.sender = sender;
    }
    public long getRecipient() {
        return recipient;
    }
    public void setRecipient(long recipient) {
        this.recipient = recipient;
    }
    public long getSendTime() {
        return sendTime;
    }
    public void setSendTime(long time) {
        sendTime = time;
    }

    @Override
    public String toString() {
        return "Packet{" +
                "sendTime=" + sendTime +
                ", sender=" + sender +
                ", recipient=" + recipient +
                '}';
    }
}
