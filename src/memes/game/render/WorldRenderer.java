package memes.game.render;

import javafx.util.Pair;
import memes.GameClient;
import memes.game.entity.PlayerEntity;
import memes.game.world.Tile;
import memes.game.world.TileMetadata;
import memes.game.world.TileType;
import memes.game.world.World;
import memes.util.Constants;
import org.newdawn.slick.Color;
import org.newdawn.slick.Game;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import java.util.ArrayList;

public class WorldRenderer {

    private World world;

    public WorldRenderer(World world) {
        this.world = world;
    }

    public void render(Graphics graphics, double cameraX, double cameraY) {
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

        ArrayList<Pair<memes.util.Point, String>> computerLabels = new ArrayList<>();
        for (int x = firstTileX; x < lastTileX; x++) {
            // The screen coord that the tile should be rendered too, can be negative
            float pixelX = (float) ((cameraX + ((x - firstTileX) * Constants.TILE_SIZE)) - xOffScreen);

            for (int y = firstTileY; y < lastTileY; y++) {
                float pixelY = (float) ((cameraY + ((y - firstTileY) * Constants.TILE_SIZE)) - yOffScreen);

                Tile tile = world.getTile(x, y).get();
                Image img = null;
                if(tile.type.spriteX < 0 || tile.type.spriteY < 0) img = TextureManager.imageMap.get(tile.type.name);
                else img = TextureManager.sprites.getSubImage(tile.type.spriteX, tile.type.spriteY);
                tile.type.renderer.render(img, pixelX, pixelY);

                // Add computer user label to render list if necessary
                if(tile.type == TileType.COMPUTER) {
                    String user = ((TileMetadata.ComputerMetadata)tile.metadata).user;
                    if(user != null) computerLabels.add(new Pair<memes.util.Point, String>(new memes.util.Point(pixelX, pixelY), user));
                }

            }
        }

        // Render the computer labels over the tiles
        computerLabels.forEach(pair -> {
            memes.util.Point p = pair.getKey();
            String str = pair.getValue();
            float x = (float)p.getX() - (str.length() / 2 * 5), y = (float)p.getY() - Constants.TILE_SIZE / 2;
            graphics.setColor( new Color(0.17f, 0.17f, 0.19f, 0.9f));
            graphics.fillRect(x - 3, y - 2, graphics.getFont().getWidth(str) + 7, graphics.getFont().getHeight(str) + 5);
            graphics.setColor(Color.white);
            graphics.drawString(str, x, y);
        });

        world.getEntities().forEach(e -> e.render(graphics));

        PlayerEntity player = GameClient.INSTANCE.player;

        // draw caffeine bar
        player.caffeineLevel = PlayerEntity.MAX_CAFFEINE / 2;
        graphics.setColor(Color.white);
        graphics.drawString("Caffeine", 0, 30);
        graphics.setColor(Color.black);
        graphics.fillRect(10, 60, 40, PlayerEntity.MAX_CAFFEINE * 3);
        graphics.setColor(Color.green);
        graphics.fillRect(10, PlayerEntity.MAX_CAFFEINE * 3 - player.caffeineLevel * 3 + 60, 40, player.caffeineLevel * 3);

        // draw relaxation bar
        player.relaxationLevel = PlayerEntity.MAX_RELAXATION / 2;
        graphics.setColor(Color.white);
        graphics.drawString("Relaxation", 75, 30);
        graphics.setColor(Color.black);
        graphics.fillRect(85, 60, 40, PlayerEntity.MAX_RELAXATION * 3);
        graphics.setColor(Color.blue);
        graphics.fillRect(85, PlayerEntity.MAX_RELAXATION * 3 - player.relaxationLevel * 3 + 60, 40, player.relaxationLevel * 3);

    }
}
