package memes.net.server;

import memes.game.entity.BaseEntity;
import memes.game.entity.PlayerEntity;
import memes.game.event.IEventHandler;
import memes.game.event.MoveEvent;
import memes.game.world.World;
import memes.game.world.gen.FileOfficeGenerator;
import memes.net.packet.ConnectRequest;
import memes.net.packet.Packet;
import memes.net.packet.PacketType;
import memes.net.packet.ConnectPacket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameServer implements IEventHandler {
    public static GameServer INSTANCE;

    private NetServer netServer;
    private List<PlayerEntity> players;
    private List<BaseEntity> entities;
    private World world;

    public GameServer() throws IOException {
        netServer = new NetServer(this);
        players = new ArrayList<>();
        entities = new ArrayList<>();

        world = FileOfficeGenerator.genWorld(160, 90);
    }

    public void start() {
        System.out.println("Starting server");
        netServer.start();
    }

    /**
     * Called when a packet is received from a Client
     * @param packet packet to process
     */
    @Override
    public void onPacketReceive(Packet packet) {
        System.out.println("[Server] Got packet from Client[" + packet.getSender() + "] " + packet);
        // new player joins
        if (packet.getPacketType() == PacketType.ConnectReq) {
            ConnectRequest cp = (ConnectRequest) packet;
            PlayerEntity player = world.playerConnected(cp.getID(), cp.getUsername());

            netServer.send(new WorldPacket(world, netServer.getServerID()), cp.getID());
            netServer.broadcast(new ConnectPacket(player), cp.getSender());
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

            netServer.broadcast(e, e.getSender());
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
