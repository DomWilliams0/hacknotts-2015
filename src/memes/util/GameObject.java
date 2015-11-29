package memes.util;

import org.newdawn.slick.Graphics;

public interface GameObject {
    void tick(float delta);

    void render(Graphics graphics);
}
