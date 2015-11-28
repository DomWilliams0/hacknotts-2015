package memes;

import memes.game.entity.Human;
import memes.util.Point;
import org.newdawn.slick.*;

public class Game extends BasicGame {

    public Game() {
        super("Pokememe");
    }

    public void start() {
        System.out.println("Game starting");

        try {
            AppGameContainer container = new AppGameContainer(this, 1280, 720, false);
            container.start();
        } catch (SlickException e) {
            System.err.println("Failed to start game: " + e.getMessage());
            e.printStackTrace();
        }

    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        System.out.println(new Human(new Point(5,5), "potato"));
    }

    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {

        float delta = (float) i / 1000;
        // todo tick world
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {

    }

    public static void main(String[] args) {
        new Game().start();
    }
}
