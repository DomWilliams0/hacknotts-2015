package memes.world;

import memes.game.entity.BaseEntity;
import memes.util.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class World {

    private Tile[][] tiles;
    private int xSize, ySize;
    private ArrayList<BaseEntity> entities = new ArrayList<>();

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

    /**
     * Add an entity to the world, note that this will overwrite an entities at the same Point
     * @param e - The entity to spawn
     */
    public void spawnEntity(BaseEntity e) {
        entities.add(e);
    }

    public int getXSize() {
        return xSize;
    }

    public int getYSize() {
        return ySize;
    }

    /**
     * Returns the tile at the specified coordinates
     * @param x - The x coord
     * @param y - The x coord
     * @return Optional.empty() if the x and y coords are out of bounds
     */
    public Optional<Tile> getTile(int x, int y) {
        return x < xSize && y < ySize && x >= 0 && y >= 0 ? Optional.of(tiles[x][y]) : Optional.<Tile>empty();
    }

}
