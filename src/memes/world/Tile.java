package memes.world;

import memes.render.TileRenderer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Represents an instance of a tile that can be of any TileType
 */
public class Tile {

    // The standard tile renderer, just draws the image
    private static final TileRenderer standardRenderer = (img, x, y) -> img.draw(x, y);
    public TileType type;
    public TileMetadata metadata;

    public Tile(TileType type) {
        this.type = type;
    }

    public void initMetadata(int x, int y, World world) {
        // Construct this tile's basic metadata from the type's factory
        this.metadata = type.factory != null ? type.factory.getMetadata(x, y, world) : null;
    }

    public enum TileType {
        FLOOR((x, y, world) -> new TileMetadata.FloorMetadata(x, y, world)),
        DESK,
        WALL,
        COMPUTER((x, y, world) -> new TileMetadata.ComputerMetadata(x, y, world));

        // This defines how the tile type's metadata is constructed
        public TileMetadata.MetadataFactory factory;
        public TileRenderer renderer;
        private Image img;

        TileType(TileMetadata.MetadataFactory factory, TileRenderer renderer) {
            this.factory = factory;
            try {
                this.img = new Image("res/" + this.name().toLowerCase() + ".png");
            } catch (SlickException e) {
                e.printStackTrace();
            }
            this.renderer = renderer;
        }

        TileType(TileMetadata.MetadataFactory factory) {
            this(factory, standardRenderer);
        }

        TileType() {
            this(null);
        }

        public void render(TileMetadata meta, float x, float y) {
            renderer.render(img, x, y);
        }
    }

}
