package memes;

import memes.game.anim.Animations;
import memes.game.entity.PlayerEntity;
import memes.game.event.ActionHandler;
import memes.game.event.InputEvent;
import memes.game.input.InputHandler;
import memes.game.input.InputKey;
import memes.game.render.WorldRenderer;
import memes.game.world.World;
import memes.net.PacketHandler;
import memes.net.packet.Packet;
import memes.net.packet.PacketType;
import memes.net.packet.WorldPacket;
import memes.net.server.NetClient;
import memes.util.Constants;
import org.newdawn.slick.*;

import javax.swing.*;

public class GameClient extends BasicGame implements PacketHandler {

    public static GameClient INSTANCE;

    private NetClient client;
    private PlayerEntity player;
    private InputHandler input;
    private GameContainer gameContainer;

    private World world;
    private WorldRenderer worldRenderer;

    public GameClient() {
        // ALERT! Do not do any game initialisation in here, use
        // init(GameContainer) instead (so that OpenGL is already initialised etc.)

        super("Memes and more memes");
    }

    public void start() {
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
        this.gameContainer = gameContainer;

        // ask for host and username
        String[] guiSelection = showGUI();
        String username = guiSelection[0];
        String serverHost = guiSelection[1]; // todo inetaddress

        // a bit of validation
        if (username.isEmpty() || serverHost.isEmpty())
            throw new IllegalArgumentException("Enter all fields you fool!");

        // load resources
        Animations.loadAll();

        gameContainer.pause();

        try {
            client = NetClient.connectToServer(serverHost);
            client.addPacketHandler(this);
            client.requestServerWorld(client.getID(), username);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // input
        input = new InputHandler(gameContainer);
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
    public void onPacketReceive(Packet packet) {
        // set world
        if (packet.getPacketType() == PacketType.World) {
            System.out.println("Received world packet");

            world = ((WorldPacket) packet).getWorld();

            player = (PlayerEntity) world.getEntityFromID(client.getID());
            player.loadAnimation();

            worldRenderer = new WorldRenderer(world);

            input.addHandler(player);
            input.addHandler(new ActionHandler(player));

            gameContainer.resume();
        }
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
        JTextField userName = new JTextField("Top_Memer", cols);
        JTextField host = new JTextField("localhost", cols);

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
        if (world != null)
            world.tick(delta);
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        // render world
        if (worldRenderer != null)
            worldRenderer.render(graphics, 0, 0);
    }

    public World getWorld() {
        return world;
    }

    public static void main(String[] args) {
        INSTANCE = new GameClient();
        INSTANCE.start();
    }
}
