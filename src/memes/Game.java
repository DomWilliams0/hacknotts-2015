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

import javax.swing.*;

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
        // ask for host and username
        String[] guiSelection = showGUI();
        String username = guiSelection[0];
        String serverHost = guiSelection[1]; // todo inetaddress

        // a bit of validation
        if (username.isEmpty() || serverHost.isEmpty())
            throw new IllegalArgumentException("Enter all fields you fool!");

        // load resources
        Animations.loadAll();

        // init world
        world = (new StaticOfficeGenerator()).genWorld(19, 5);

        // init player
        PlayerEntity testPlayer = new PlayerEntity(new Point(640, 640), username);
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

    /**
     * @return {username, server host}
     */
    private String[] showGUI() {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | UnsupportedLookAndFeelException | IllegalAccessException e) {
            System.out.println("Could not get a dank UI :(");
        }


        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        final int cols = 15;
        JTextField userName = new JTextField(cols);
        JTextField host = new JTextField(cols);

        panel.add(new JLabel("Username", SwingConstants.CENTER));
        panel.add(userName);

        panel.add(new JLabel("Server IP", SwingConstants.CENTER));
        panel.add(host);

        int choice = JOptionPane.showOptionDialog(null, panel, "The Memiest Meme You've Ever Memed",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);

        // nah
        if (choice != JOptionPane.OK_OPTION)
            System.exit(0);

        return new String[]{userName.getText(), host.getText()};
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
