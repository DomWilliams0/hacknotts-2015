package memes.world;

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

    public enum TileType {
        FLOOR((x, y, world) -> new TileMetadata.FloorMetadata(x, y, world)),
        DESK,
        WALL,
        COMPUTER((x, y, world) -> new TileMetadata.ComputerMetadata(x, y, world));

        // This defines how the tile type's metadata is constructed
        public MetadataFactory factory;

        TileType(MetadataFactory factory) {
            this.factory = factory;
        }

        TileType() {
            this(null);
        }
    }

}
