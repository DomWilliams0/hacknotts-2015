package memes.render;

import jdk.nashorn.internal.runtime.regexp.joni.ast.ConsAltNode;
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
        int tileX = (int)cameraX / Constants.TILE_SIZE, tileY = (int)cameraY / Constants.TILE_SIZE;
        int numTilesX = Math.min(world.getXSize(), 1280 / Constants.TILE_SIZE), numTilesY = Math.min(world.getYSize(), 720 / Constants.TILE_SIZE);
        float scaleX = Constants.TILE_SIZE * ((1280 / Constants.TILE_SIZE) / numTilesX), scaleY = Constants.TILE_SIZE * ((720 / Constants.TILE_SIZE) / numTilesY);
        for(int x = tileX; x < tileX + numTilesX; x++) {
            for(int y = tileY; y < tileY + numTilesY; y++) {
                Tile tile = world.getTile(tileX + x, tileY + y).get();
                tile.type.render(tile.metadata, (float)cameraX + scaleX * (x - tileX), (float)cameraY + scaleY * (y - tileY), scaleX, scaleY);
            }
        }
    }
}
