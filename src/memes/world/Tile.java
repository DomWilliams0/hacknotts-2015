package memes.world;

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
        FLOOR, DESK, WALL
    }

    public abstract class TileMetadata {

    }

}
