package memes.game.event;

import memes.game.entity.BaseEntity;
import memes.net.packet.Packet;
import memes.net.packet.PacketType;
import memes.util.Direction;

import java.io.Serializable;

public class MoveEvent implements Packet, Serializable {
    private long entityID;
    private double x, y;
    private int speed;
    private Direction direction;
    private boolean isMoveStart;

    public MoveEvent(BaseEntity entity, PacketType moveType) {
        this.x = entity.getPixelPosition().getX();
        this.y = entity.getPixelPosition().getY();
        this.speed = entity.getCurrentMoveSpeed();
        this.direction = entity.getMovementDirection();
        this.entityID = entity.getID();

        if (moveType != PacketType.StartMove && moveType != PacketType.EndMove)
            throw new IllegalArgumentException("Must be a MoveStart or MoveEnd packet type");
        isMoveStart = moveType == PacketType.StartMove;
    }

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public long getID() {
        return entityID;
    }
    public int getSpeed() {
        return speed;
    }

    @Override
    public PacketType getPacketType() {
        return isMoveStart ? PacketType.StartMove : PacketType.EndMove;
    }
}
