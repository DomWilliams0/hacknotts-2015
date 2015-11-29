package memes.game.event;

import memes.game.entity.BaseEntity;
import memes.net.packet.Packet;
import memes.net.packet.PacketType;
import memes.util.Direction;
import memes.util.Point;

import java.io.Serializable;

public class MoveEvent implements Packet, Serializable {
    private long entityID;
    private Point position;
    private int speed;
    private Direction direction;
    private boolean isMoveStart;

    public MoveEvent(BaseEntity entity, boolean starting) {
        this.position = new Point(entity.getPixelPosition());
        this.speed = entity.getCurrentMoveSpeed();
        this.direction = entity.getMovementDirection();
        this.entityID = entity.getID();

        isMoveStart = starting;
    }

    public long getID() {
        return entityID;
    }

    public int getSpeed() {
        return speed;
    }

    public Point getPosition() {
        return position;
    }

    public Direction getDirection() {
        return direction;
    }

    public boolean isMoveStart() {
        return isMoveStart;
    }

    @Override
    public PacketType getPacketType() {
        return PacketType.Move;
    }

    @Override
    public String toString() {
        return "MoveEvent{" +
                "entityID=" + entityID +
                ", position=" + position +
                ", speed=" + speed +
                ", direction=" + direction +
                ", isMoveStart=" + isMoveStart +
                '}';
    }
}
