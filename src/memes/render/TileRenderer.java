package memes.render;

import org.newdawn.slick.Image;

public interface TileRenderer {

    // The standard tile renderer, just draws the image
    TileRenderer standardRenderer = Image::draw;

    void render(Image img, float x, float y, float width, float height);

}
