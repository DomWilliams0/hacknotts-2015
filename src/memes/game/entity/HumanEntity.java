package memes.game.entity;

import memes.game.anim.HumanAnimation;
import memes.util.Constants;
import memes.util.Direction;
import memes.util.Point;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.io.Serializable;

public class HumanEntity extends BaseEntity implements Serializable {

    private static final Color NAME_BOX_COLOUR = new Color(0.17f, 0.17f, 0.19f, 0.9f);

    private String username;
    private HumanAnimation animation;

    public HumanEntity(long entityID, Point position, String username) {
        super(entityID, position, Constants.TILE_SIZE * 4, Constants.TILE_SIZE);
        this.username = username;
    }

    public void loadAnimation() {
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

        position.translate(movementDirection, currentMoveSpeed * delta);
    }

    @Override
    public void render(Graphics graphics) {
        // player
        Animation anim = animation.getAnimation(movementDirection);
        anim.draw((float) position.getX(), (float) position.getY());

        // name
        int width = graphics.getFont().getWidth(username);
        int height = graphics.getFont().getHeight(username);
        final int paddingH = 6;
        final int paddingV = 3;

        float nameX = (float) position.getX() - width / 2 + aabb.getWidth() / 2;
        float nameY = (float) position.getY() - height / 2 - aabb.getHeight() * 0.4f;

        graphics.setColor(NAME_BOX_COLOUR);
        graphics.fillRect(nameX - paddingH / 2, nameY - paddingV / 2, width + paddingH, height + paddingV);

        graphics.setColor(Color.white);
        graphics.drawString(username, nameX, nameY);

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
