package memes.game.entity;

import org.lwjgl.util.Point;

public class Human extends BaseEntity {

    public Human(Point position, int speed, int size) {
        super(position, speed, size);
    }

    @Override
    public void tick(int delta) {
        // todo move
    }

    @Override
    public void render() {

    }
}
