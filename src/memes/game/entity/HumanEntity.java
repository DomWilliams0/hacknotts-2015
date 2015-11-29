package memes.game.entity;

import memes.game.anim.HumanAnimation;
import memes.util.Constants;
import memes.util.Direction;
import memes.util.Point;
import org.newdawn.slick.Animation;

public class HumanEntity extends BaseEntity {

    private String username;
    private HumanAnimation animation;

    public HumanEntity(Point position, String username) {
        super(position, Constants.TILE_SIZE * 4, Constants.TILE_SIZE);
        this.username = username;
        this.animation = new HumanAnimation("business_man");
    }

    @Override
    public void tick(float delta) {
        move(delta);
    }

    @Override
    public void startMoving(Direction direction, int speed) {
        super.startMoving(direction, speed);
        animation.start();
    }

    @Override
    public void stopMoving() {
        super.stopMoving();
        animation.stop();
    }

    private void move(float delta) {
        if (!isMoving())
            return;

        position = movementDirection.translate(position, currentMoveSpeed * delta);
    }

    @Override
    public void render() {
        Animation anim = animation.getAnimation(movementDirection);
        anim.draw((float) position.getX(), (float) position.getY());
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "Human{" +
                "username='" + username + '\'' +
                "} " + super.toString();
    }
}
