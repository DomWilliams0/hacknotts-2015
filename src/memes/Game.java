package memes;

import memes.game.anim.Animations;
import memes.game.entity.PlayerEntity;
import memes.game.input.InputHandler;
import memes.gen.StaticOfficeGenerator;
import memes.render.WorldRenderer;
import memes.util.Constants;
import memes.util.Point;
import memes.world.World;
import org.newdawn.slick.*;

public class Game extends BasicGame {

    public static Game INSTANCE = new Game();

    private WorldRenderer worldRenderer;
    private PlayerEntity testPlayer;

    public Game() {
        // ALERT! Do not do any game initialisation in here, use init(GameContainer) instead (so that OpenGL is already initialised etc.)

        super("Memes and more memes");
        System.out.println("Game starting");

        try {
            new AppGameContainer(this,
                    Constants.WINDOW_SIZE.getIntX(),
                    Constants.WINDOW_SIZE.getIntY(),
                    false).start();

        } catch (SlickException e) {
            System.err.println("Failed to start game: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        // load resources
        Animations.loadAll();

        // init world
        World world = (new StaticOfficeGenerator()).genWorld(19, 5);
        worldRenderer = new WorldRenderer(world);

        // init player
        testPlayer = new PlayerEntity(new Point(640, 640), "Top_Memer");

        // input
        InputHandler input = new InputHandler(gameContainer);
        input.addHandler(testPlayer);
    }


    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {

        float delta = (float) i / 1000;

        // todo tick world

        testPlayer.tick(delta);
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        // render world
        worldRenderer.render(0, 0);

        // render player
        testPlayer.render(graphics);
    }

    public static void main(String[] args) {
        INSTANCE = new Game();
    }
}
