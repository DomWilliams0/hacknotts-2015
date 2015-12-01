package memes.game.render;

import com.sun.corba.se.impl.orbutil.closure.Constant;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import memes.GameClient;
import memes.game.entity.PlayerEntity;
import memes.game.world.Tile;
import memes.game.world.TileMetadata;
import memes.game.world.TileType;
import memes.game.world.World;
import memes.util.Constants;
import memes.util.Point;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import java.util.Map;
import java.util.TreeMap;

public class WorldRenderer {

    private World world;
    private Point camPos;
    private Map<Point, String> computerLabels;

    public WorldRenderer(World world) {
        this.world = world;
        computerLabels = new TreeMap<>();
    }

    public Point getCamPos() {
        return camPos;
    }

    /**
     * Sets the given point in the middle of the screen
     * @param camPos
     */
    public void setCamPos(Point camPos) {
        this.camPos = new Point(
                Constants.WINDOW_SIZE.getX() / 2 - camPos.getX(),
                Constants.WINDOW_SIZE.getY() / 2 - camPos.getY()
        ).multiply(-1);
    }
    /**
     * Render the world centred on x and y
     * @param graphics graphics to render to
     */
    public void render(Graphics graphics) {
        double xOffScreen = camPos.getX() % Constants.TILE_SIZE;
        double yOffScreen = camPos.getY() % Constants.TILE_SIZE;

        int firstTileX = Math.max(0, Math.min(world.getXSize(), (int) (camPos.getX() - xOffScreen) / Constants.TILE_SIZE));
        int firstTileY = Math.max(0, Math.min(world.getYSize(), (int) (camPos.getY() - yOffScreen) / Constants.TILE_SIZE));

        int lastTileX = Math.min((int) (camPos.getX() + Constants.WINDOW_SIZE.getIntX() +
                (Constants.TILE_SIZE - (camPos.getX() % Constants.TILE_SIZE))) / Constants.TILE_SIZE, world.getXSize());
        int lastTileY = Math.min((int) (camPos.getY() + Constants.WINDOW_SIZE.getIntY() +
                (Constants.TILE_SIZE - (camPos.getY() % Constants.TILE_SIZE))) / Constants.TILE_SIZE, world.getYSize());

        computerLabels.clear();

        for (int x = firstTileX; x < lastTileX; x++) {
            for (int y = firstTileY; y < lastTileY; y++) {

                Tile tile = world.getTile(x, y).get();
                Image img;
                if (tile.type.spriteX < 0 || tile.type.spriteY < 0)
                    img = TextureManager.imageMap.get(tile.type.name);
                else
                    img = TextureManager.sprites.getSubImage(tile.type.spriteX, tile.type.spriteY);

                tile.type.renderer.render(img,
                        (float) -camPos.getX() + x * Constants.TILE_SIZE,
                        (float) -camPos.getY() + y * Constants.TILE_SIZE
                );

                // Add computer user label to render list if necessary
                if (tile.type == TileType.COMPUTER) {
                    String user = ((TileMetadata.ComputerMetadata) tile.metadata).user;
                    if (user != null)
                        this.computerLabels.put(new Point(x * Constants.TILE_SIZE, y * Constants.TILE_SIZE), user);
                }
            }
        }

        // Render the computer labels over the tiles
        this.computerLabels.entrySet().forEach(pair -> {
            Point p = pair.getKey();
            String str = pair.getValue();
            float x = (float) p.getX() - (str.length() / 2 * 5), y = (float) p.getY() - Constants.TILE_SIZE / 2;
            graphics.setColor(new Color(0.17f, 0.17f, 0.19f, 0.9f));
            graphics.fillRect(x - 3, y - 2, graphics.getFont().getWidth(str) + 7, graphics.getFont().getHeight(str) + 5);
            graphics.setColor(Color.white);
            graphics.drawString(str, x, y);
        });

        /* //TODO: Move GUI code
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

        graphics.setColor(Color.white);
        graphics.drawString("Score: 1010101", 10, 100);*/
    }
}
