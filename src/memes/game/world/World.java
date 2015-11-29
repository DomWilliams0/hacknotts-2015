package memes.game.world;

import memes.game.entity.BaseEntity;
import memes.util.Constants;
import memes.util.Point;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

public class World implements Serializable {

    private Tile[][] tiles;
    private int xSize, ySize;
    private ArrayList<BaseEntity> entities;

    public World(Tile[][] tiles, int xSize, int ySize) {
        this.tiles = tiles;
        this.xSize = xSize;
        this.ySize = ySize;
        this.entities = new ArrayList<>();

        initTiles();
    }

    /**
     * Send the updated world to clients
     */
    public void update() {
        //TODO
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
     *
     * @param e - The entity to spawn
     */
    public void addEntity(BaseEntity e) {
        entities.add(e);
        e.setWorld(this);
    }

    public int getXSize() {
        return xSize;
    }

    public int getYSize() {
        return ySize;
    }

    /**
     * Returns the tile at the specified coordinates
     *
     * @param x - The x coord
     * @param y - The x coord
     * @return Optional.empty() if the x and y coords are out of bounds
     */
    public Optional<Tile> getTile(int x, int y) {
        return x < xSize && y < ySize && x >= 0 && y >= 0 ? Optional.of(tiles[x][y]) : Optional.<Tile>empty();
    }

    public Point getRandomSpawn(long seed) {
        Random r = new Random(seed);
        Optional<Tile> tile;
        Point p;
        do {
            p = new Point(r.nextInt(xSize), r.nextInt(ySize));
            tile = getTile(p.getIntX(), p.getIntY());
        } while (tile.isPresent() && tile.get().type == TileType.FLOOR);

        return p.multiply(Constants.TILE_SIZE);
    }

    /**
     * @throws IllegalArgumentException if not found
     */
    public BaseEntity getEntityFromID(long id) {
        return entities.stream()
                .filter(e -> e.getID() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("An entity does not exist with the ID " + id));
    }

    public void tick(float delta) {
        entities.forEach(e -> e.tick(delta));
    }

    public ArrayList<BaseEntity> getEntities() {
        return entities;
    }

    public Tile[][] getTiles() {
        return tiles;
    }
}
