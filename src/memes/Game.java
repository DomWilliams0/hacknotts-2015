package memes;

import memes.game.anim.Animations;
import memes.game.entity.PlayerEntity;
import memes.game.event.InputEvent;
import memes.game.input.InputHandler;
import memes.game.input.InputKey;
import memes.game.world.World;
import memes.game.world.gen.StaticOfficeGenerator;
import memes.net.packet.PacketType;
import memes.util.Constants;
import memes.util.Point;
import org.newdawn.slick.*;

public class Game extends BasicGame {

    private World world;

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
        world = (new StaticOfficeGenerator()).genWorld(19, 5);

        // init player
        PlayerEntity testPlayer = new PlayerEntity(new Point(640, 640), "Top_Memer");
        world.addEntity(testPlayer);

        // input
        InputHandler input = new InputHandler(gameContainer);
        input.addHandler(testPlayer);
        input.addHandler(event -> {
            if (event.getPacketType() == PacketType.Input) {
                if (((InputEvent) event).getKey() == InputKey.EXIT) {
                    System.out.println("System going down NOW, stash your memes!");
                    gameContainer.exit();
                }
            }
        });
    }


    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {

        float delta = (float) i / 1000;
        world.tick(delta);
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        // render world
        world.render(graphics);
    }

    public static void main(String[] args) {
        new Game();
    }
}
