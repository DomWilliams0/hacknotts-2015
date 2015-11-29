package memes.render;

import memes.Game;
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
        double xOfScreen = cameraX % Constants.TILE_SIZE;
        double yOfScreen = cameraY % Constants.TILE_SIZE;
        int firstTileX = (int) (cameraX - xOfScreen) / Constants.TILE_SIZE;
        int firstTileY = (int) (cameraY - (cameraY % Constants.TILE_SIZE)) / Constants.TILE_SIZE;
        int lastTileX = Math.min((int) (cameraX + Game.WIDTH + (Constants.TILE_SIZE - (cameraX % Constants.TILE_SIZE))) / Constants.TILE_SIZE, world.getXSize());
        int lastTileY = Math.min((int) (cameraY + Game.HEIGHT + (Constants.TILE_SIZE - (cameraY % Constants.TILE_SIZE))) / Constants.TILE_SIZE, world.getYSize());
        for (int x = firstTileX; x < lastTileX; x++) {
            float pixelX = (float) ((cameraX + (x - firstTileX) * Constants.TILE_SIZE) - xOfScreen);
            for (int y = firstTileY; y < lastTileY; y++) {
                float pixelY = (float) ((cameraY + (y - firstTileY) * Constants.TILE_SIZE) - yOfScreen);
                Tile tile = world.getTile(x, y).get();
                tile.type.render(tile.metadata, pixelX, pixelY);
            }
        }
    }
}
