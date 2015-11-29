package memes.world;

import memes.render.TileRenderer;
import memes.util.Constants;
import org.lwjgl.LWJGLException;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * Represents an instance of a tile that can be of any TileType
 */
public class Tile {

    public TileType type;
    public TileMetadata metadata;

    public static SpriteSheet sprites = null;

    static {
        try {
            sprites = new SpriteSheet(new Image("res/terrain/interior.png"), Constants.TILE_SIZE, Constants.TILE_SIZE, 1, 0);
        } catch(SlickException e) {
            e.printStackTrace();
        }
    }

    public Tile(TileType type) {
        this.type = type;
    }

    public void initMetadata(int x, int y, World world) {
        // Construct this tile's basic metadata from the type's factory
        this.metadata = type.factory != null ? type.factory.getMetadata(x, y, world) : null;
    }

}
