package memes.game.entity;

import memes.util.Constants;
import memes.util.Direction;
import memes.util.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import java.io.Serializable;

public abstract class BaseEntity implements Serializable {

    private static long LASTID = 0;

    private long id;
    protected Shape aabb;

    /**
     * Pixel position
     */
    protected Point position;

    /**
     * Pixels to move per tick when moving
     */
    protected int moveSpeed;

    /**
     * Current moving speed
     */
    protected int currentMoveSpeed;
    protected Direction movementDirection;

    public BaseEntity(Point position, int moveSpeed, int size) {
        this.aabb = new Rectangle(position.getIntX(), position.getIntY(), size, size);
        this.id = LASTID++;
        this.position = position;

        this.moveSpeed = moveSpeed;
        this.currentMoveSpeed = 0;
        this.movementDirection = Direction.SOUTH;

    }

    public abstract void tick(float delta);

    public abstract void render();

    public long getID() {
        return id;
    }

    public Point getPixelPosition() {
        return position;
    }

    public Point getTilePosition() {
        return new Point(position.getX() / Constants.TILE_SIZE, position.getY() / Constants.TILE_SIZE);
    }

    public Direction getMovementDirection() {
        return movementDirection;
    }

    public boolean isMoving() {
        return moveSpeed != 0;
    }

    public void startMoving() {
        currentMoveSpeed = moveSpeed;
        // todo update renderer
    }

    public void stopMoving() {
        currentMoveSpeed = 0;
        // todo update renderer
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "id=" + id +
                ", aabb=" + aabb +
                ", position=" + position +
                ", moveSpeed=" + moveSpeed +
                ", currentMoveSpeed=" + currentMoveSpeed +
                ", movementDirection=" + movementDirection +
                '}';
    }
}
