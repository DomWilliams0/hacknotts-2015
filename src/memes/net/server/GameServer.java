package memes.net.server;

import memes.game.entity.BaseEntity;
import memes.game.entity.PlayerEntity;
import memes.game.event.MoveEvent;
import memes.game.world.World;
import memes.game.world.gen.FileOfficeGenerator;
import memes.net.PacketHandler;
import memes.net.packet.Packet;
import memes.net.packet.PacketType;
import memes.net.packet.PlayerConnectPacket;

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
    }

    public void start() {
        System.out.println("Starting server");
        netServer.start();
    }

    @Override
    public void onPacketReceive(Packet packet) {
        // new player joins
        if (packet.getPacketType() == PacketType.Connect) {
            PlayerConnectPacket cp = (PlayerConnectPacket) packet;
            try {
                netServer.send(cp);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // movement
        else if (packet.getPacketType() == PacketType.Move) {

            MoveEvent e = (MoveEvent) packet;
            BaseEntity entity = world.getEntityFromID(e.getID());

            entity.setPosition(e.getPosition());
            if (e.isMoveStart())
                entity.startMoving(e.getDirection(), e.getSpeed());
            else
                entity.stopMoving();
        }
    }

    public World getWorld() {
        return world;
    }

    public static void main(String[] args) throws Exception {
        INSTANCE = new GameServer();
        INSTANCE.start();
    }
}
