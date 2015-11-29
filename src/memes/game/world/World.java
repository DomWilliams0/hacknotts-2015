package memes.game.world;

import memes.game.entity.BaseEntity;
import memes.game.render.WorldRenderer;
import memes.util.GameObject;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;
import java.util.Optional;

public class World implements GameObject {

    private Tile[][] tiles;
    private int xSize, ySize;
    private ArrayList<BaseEntity> entities;
    private WorldRenderer renderer;

    public World(Tile[][] tiles, int xSize, int ySize) {
        this.tiles = tiles;
        this.xSize = xSize;
        this.ySize = ySize;
        this.renderer = new WorldRenderer(this);
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

    /**
     * @throws IllegalArgumentException if not found
     */
    public BaseEntity getEntityFromID(long id) {
        return entities.stream()
                .filter(e -> e.getID() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("An entity does not exist with the ID " + id));
    }

    @Override
    public void tick(float delta) {
        entities.forEach(e -> e.tick(delta));
    }

    @Override
    public void render(Graphics graphics) {
        renderer.render(0, 0);
        entities.forEach(e -> e.render(graphics));
    }

}
