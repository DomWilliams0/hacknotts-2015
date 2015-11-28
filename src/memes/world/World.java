package memes.world;

import java.util.Optional;

public class World {

    private Tile[][] tiles;
    private int xSize, ySize;

    public World(Tile[][] tiles, int xSize, int ySize) {
        this.tiles = tiles;
        this.xSize = xSize;
        this.ySize = ySize;
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
