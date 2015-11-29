package memes;

import memes.gen.StaticOfficeGenerator;
import memes.render.WorldRenderer;
import memes.world.World;
import org.newdawn.slick.*;

public class Game extends BasicGame {

    public static final Game INSTANCE = new Game();
    public static final int WIDTH = 1280, HEIGHT = 720;
    WorldRenderer r = null;

    public Game() {
        super("Pokememe");
    }

    public static void main(String[] args) {
        INSTANCE.start();
    }

    public void start() {
        System.out.println("Game starting");

        try {
            AppGameContainer container = new AppGameContainer(this, WIDTH, HEIGHT, false);
            container.start();
        } catch (SlickException e) {
            System.err.println("Failed to start game: " + e.getMessage());
            e.printStackTrace();
        }

    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        World world = (new StaticOfficeGenerator()).genWorld(19, 5);
        r = new WorldRenderer(world);
    }

    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {

        float delta = (float) i / 1000;
        // todo tick world
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        if (r != null) r.render(19, 0);
        else System.out.println("oops m8");
    }
}
