package memes.world;

/**
 * Represents an instance of a tile that can be of any TileType
 */
public class Tile {

    public TileType type;
    public TileMetadata metadata;

    public Tile(TileType type, TileMetadata meta) {
        this.type = type;
        this.metadata = meta;
    }

    public Tile(TileType type) {
        this(type, null);
    }

    public enum TileType {
        FLOOR, DESK, WALL, COMPUTER
    }

    /**
     * Represents metadata that is specific to tile types, and belongs to a tile object.
     * This could store the amount of coffee left in a specific tile instance,
     * or the development progress of a certain computer
     */
    public abstract class TileMetadata {

    }

}
