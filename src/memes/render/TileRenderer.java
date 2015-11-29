package memes.render;

import memes.util.Constants;
import org.newdawn.slick.Image;

public interface TileRenderer {

    // The standard tile renderer, just draws the image
    TileRenderer standardRenderer = (image, x, y) -> {
        image.setFilter(Image.FILTER_NEAREST);
        image.draw(x, y, (float) Constants.TILE_SIZE / Constants.TILE_LOAD_SIZE);
    };

    void render(Image img, float x, float y);

}
