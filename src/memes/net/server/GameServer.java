package memes.net.server;

import memes.game.entity.BaseEntity;
import memes.game.entity.PlayerEntity;
import memes.game.world.World;
import memes.game.world.gen.FileOfficeGenerator;
import memes.net.PacketHandler;
import memes.net.packet.Packet;
import memes.util.Constants;
import org.newdawn.slick.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameServer implements PacketHandler {
    public static GameServer INSTANCE;

    private NetServer netServer;
    private List<PlayerEntity> players;
    private List<BaseEntity> entities;
    private World world;

    public GameServer() throws IOException {
        netServer = new NetServer();
        players = new ArrayList<>();
        entities = new ArrayList<>();

        world = FileOfficeGenerator.genWorld(160, 90);

        // Start listening to connections
        netServer.addPacketHandler(this);
        netServer.start();
    }

    @Override
    public void onPacketReceive(Packet packet) {
        System.out.println("packet = " + packet);
    }

    public World getWorld() {
        return world;
    }
    public static void main(String[] args) throws Exception {
        INSTANCE = new GameServer();
    }
}
