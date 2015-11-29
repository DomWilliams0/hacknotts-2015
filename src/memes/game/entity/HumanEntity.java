package memes.game.entity;

import memes.game.world.TileType;
import memes.util.Constants;
import memes.util.Direction;
import memes.util.Point;
import org.newdawn.slick.Graphics;

import java.io.Serializable;

public class HumanEntity extends BaseEntity implements Serializable {

    private String username;
    private boolean playingAnimation;

    public HumanEntity(long entityID, Point position, String username) {
        super(entityID, position, Constants.TILE_SIZE * 4, Constants.TILE_SIZE);
        this.username = username;
        this.playingAnimation = false;

    }

    private boolean checkEast(int x, int y) {
        if(x >= world.getXSize() - 1) return true;
        else if(world.getTile(x + 1, y).get().type != TileType.FLOOR) return true;
        return false;
    }

    private boolean checkWest(int x, int y) {
        if(x <= 0) return true;
        else if(world.getTile(x - 1, y).get().type != TileType.FLOOR) return true;
        return false;
    }

    private boolean checkSouth(int x, int y) {
        if(y >= world.getYSize() - 1) return true;
        else if(world.getTile(x, y + 1).get().type != TileType.FLOOR) return true;
        return false;
    }

    private boolean checkNorth(int x, int y) {
        if(y <= 0) return true;
        else if(world.getTile(x, y - 1).get().type != TileType.FLOOR) return true;
        return false;
    }

    @Override
    public void startMoving(Direction direction, int speed) {
        Point p = super.getTilePosition();
        int x = p.getIntX(), y = p.getIntY();
        switch(direction) {
            case EAST:
                if(checkEast(x, y)) return;
                break;
            case NORTH:
                if(checkNorth(x, y)) return;
                break;
            case NORTH_EAST:
                if(checkEast(x, y) || checkNorth(x, y)) return;
                break;
            case NORTH_WEST:
                if(checkWest(x, y) || checkNorth(x, y)) return;
                break;
            case SOUTH:
                if(checkSouth(x, y)) return;
                break;
            case SOUTH_EAST:
                if(checkEast(x, y) || checkSouth(x, y)) return;
                break;
            case SOUTH_WEST:
                if(checkWest(x, y) || checkSouth(x, y)) return;
                break;
            case WEST:
                if(checkWest(x, y)) return;
                break;
        }
        super.startMoving(direction, speed);
        playingAnimation = true;
    }

    @Override
    public void stopMoving() {
        super.stopMoving();
        playingAnimation = false;
    }

    @Override
    public void tick(float delta) {
        move(delta);
    }

    private void move(float delta) {
        if (!isMoving())
            return;

        position.translate(movementDirection, currentMoveSpeed * delta);
    }

    @Override
    public void render(Graphics graphics) {

    }

    public String getUsername() {
        return username;
    }

    public boolean isPlayingAnimation() {
        return playingAnimation;
    }

    @Override
    public String toString() {
        return "Human{" +
                "username='" + username + '\'' +
                "} " + super.toString();
    }
}
