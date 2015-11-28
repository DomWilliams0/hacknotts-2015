package memes.game.entity;

import memes.util.Constants;
import org.lwjgl.util.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public abstract class BaseEntity {

    private static long LASTID = 0;

    private long id;

    protected Shape aabb;

    /**
     * Pixel position
     */
    protected Point position;

    /**
     * Pixels to move per tick
     */
    protected int moveSpeed;

    public BaseEntity(Point position, int moveSpeed, int size) {
        this.aabb = new Rectangle(position.getX(), position.getY(), size, size);
        this.position = position;
        this.moveSpeed = moveSpeed;
        id = LASTID++;

    }

    public abstract void tick(int delta);

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
}
