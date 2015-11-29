package memes.game.render;

import memes.util.Constants;
import memes.game.world.Tile;
import memes.game.world.World;

public class WorldRenderer implements Renderer {

    private World world;

    public WorldRenderer(World world) {
        this.world = world;
    }

    @Override
    public void render(double cameraX, double cameraY) {
        // These represent by how many pixels the tiles should be rendered off the screen
        double xOffScreen = cameraX % Constants.TILE_SIZE;
        double yOffScreen = cameraY % Constants.TILE_SIZE;
        // The world coordinates of the first and last tiles
        int firstTileX = (int) (cameraX - xOffScreen) / Constants.TILE_SIZE;
        int firstTileY = (int) (cameraY - yOffScreen) / Constants.TILE_SIZE;

        int lastTileX = Math.min((int) (cameraX + Constants.WINDOW_SIZE.getIntX() +
                (Constants.TILE_SIZE - (cameraX % Constants.TILE_SIZE))) / Constants.TILE_SIZE, world.getXSize());

        int lastTileY = Math.min((int) (cameraY + Constants.WINDOW_SIZE.getIntY() +
                (Constants.TILE_SIZE - (cameraY % Constants.TILE_SIZE))) / Constants.TILE_SIZE, world.getYSize());

        for (int x = firstTileX; x < lastTileX; x++) {
            // The screen coord that the tile should be rendered too, can be negative
            float pixelX = (float) ((cameraX + ((x - firstTileX) * Constants.TILE_SIZE)) - xOffScreen);

            for (int y = firstTileY; y < lastTileY; y++) {
                float pixelY = (float) ((cameraY + ((y - firstTileY) * Constants.TILE_SIZE)) - yOffScreen);

                Tile tile = world.getTile(x, y).get();
                tile.type.render(tile.metadata, pixelX, pixelY);
            }
        }
    }
}
