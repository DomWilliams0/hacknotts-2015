package memes.game.entity;

import memes.util.Constants;
import org.lwjgl.util.Point;

public class Human extends BaseEntity {

    public Human(Point position) {
        super(position, Constants.TILE_SIZE / 8, Constants.TILE_SIZE);
    }

    @Override
    public void tick(int delta) {
        // todo move
    }

    @Override
    public void render() {

    }
}
