package memes.game.entity;

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

    @Override
    public void startMoving(Direction direction, int speed) {
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
