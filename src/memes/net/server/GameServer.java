package memes.net.server;

import memes.game.entity.BaseEntity;
import memes.game.entity.PlayerEntity;
import memes.net.PacketHandler;
import memes.net.packet.Packet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameServer implements PacketHandler {
    private NetServer netServer;
    private List<PlayerEntity> players;
    private List<BaseEntity> entities;

    public GameServer() throws IOException {
        netServer = new NetServer();
        players = new ArrayList<>();
        entities = new ArrayList<>();

        // Start listening to connections
        netServer.addPacketHandler(this);
        netServer.start();
    }

    @Override
    public void onPacketReceive(Packet packet) {
        System.out.println("packet = " + packet);
    }

    public static void main(String[] args) throws Exception {
        new GameServer();
    }
}
