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
    public void render(int cameraX, int cameraY) {
        int xLen = 0, yLen = 0;
        // The tile x and y coords (integers) to stop rendering at
        int endX = cameraX + xLen, endY = cameraY + yLen;
        for (int x = cameraX; x < endX; x++) {
            for (int y = cameraY; y < endY; y++) {
                // Render at pixel coordinates given by ((x - cameraX) * 16, (y - cameraY) * 16)
                Tile tile = world.getTile(x, y).get();
                tile.type.render(tile.metadata, (x - cameraX) * Constants.TILE_SIZE, (y - cameraY) * Constants.TILE_SIZE);
            }
        }
    }
}
