package memes.render;

import memes.util.Constants;
import memes.world.Tile;
import memes.world.World;

public class WorldRenderer implements Renderer {

    private World world;

    public WorldRenderer(World world) {
        this.world = world;
    }

    @Override
    public void render(double cameraX, double cameraY) {
        int xLen = 1280 / Constants.TILE_SIZE, yLen = 720 / Constants.TILE_SIZE;
        // The tile x and y coords (integers) to stop rendering at
        int endX = (int) (cameraX + xLen), endY = (int) (cameraY + yLen);
        for (int x = (int) cameraX; x < endX; x++) {
            for (int y = (int) cameraY; y < endY; y++) {
                // Render at pixel coordinates given by ((x - cameraX) * 16, (y - cameraY) * 16)
                Tile tile = world.getTile(x, y).get();
                tile.type.render(tile.metadata, (x - (int)cameraX) * Constants.TILE_SIZE, (y - (int)cameraY) * Constants.TILE_SIZE);
            }
        }
    }
}
