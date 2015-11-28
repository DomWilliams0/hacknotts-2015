package memes.render;

import org.newdawn.slick.Image;

/**
 * Created by samtebbs on 28/11/2015.
 */
public interface TileRenderer {

    // The standard tile renderer, just draws the image
    TileRenderer standardRenderer = Image::draw;

    void render(Image img, float x, float y);

}
