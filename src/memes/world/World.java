package memes.world;

import java.util.Optional;

public class World {

    private Tile[][] tiles;
    private int xSize, ySize;

    public World(Tile[][] tiles, int xSize, int ySize) {
        this.tiles = tiles;
        this.xSize = xSize;
        this.ySize = ySize;
        initTiles();
    }

    /**
     * Initialises the metadata for each of the world's tiles.
     * Must be called after the world has been generated.
     */
    private void initTiles() {
        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles[y].length; x++) {
                tiles[y][x].initMetadata(x, y, this);
            }
        }
    }

    public int getXSize() {
        return xSize;
    }

    public int getYSize() {
        return ySize;
    }

    public Optional<Tile> getTile(int x, int y) {
        return x < xSize && y < ySize ? Optional.of(tiles[x][y]) : Optional.<Tile>empty();
    }

}
