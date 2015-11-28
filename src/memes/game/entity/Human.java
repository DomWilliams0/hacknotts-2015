package memes.game.entity;

import memes.util.Constants;
import memes.util.Point;

public class Human extends BaseEntity {

    private String username;

    public Human(Point position, String username) {
        super(position, Constants.TILE_SIZE / 8, Constants.TILE_SIZE);
        this.username = username;
    }

    @Override
    public void tick(float delta) {
        move(delta);
    }

    private void move(float delta) {
        if (!isMoving())
            return;

        position = movementDirection.translate(position, currentMoveSpeed * delta);
    }

    @Override
    public void render() {

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
