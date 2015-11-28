package memes.world;

import memes.render.TileRenderer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Represents an instance of a tile that can be of any TileType
 */
public class Tile {

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
