package memes.game.world;

import memes.util.Constants;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import java.io.Serializable;

/**
 * Represents an instance of a tile that can be of any TileType
 */
public class Tile implements Serializable {

    public static SpriteSheet sprites = null;

    /*static {
        try {
            sprites = new SpriteSheet(new Image("res/terrain/interior_transparent.png"),
                    Constants.TILE_LOAD_SIZE, Constants.TILE_LOAD_SIZE, 1, 0);
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }*/

    public TileType type;
    public TileMetadata metadata;

    public Tile(TileType type) {
        this.type = type;
    }

    public void initMetadata(int x, int y, World world) {
        // Construct this tile's basic metadata from the type's factory
        this.metadata = type.factory != null ? type.factory.getMetadata(x, y, world) : null;
    }

}
