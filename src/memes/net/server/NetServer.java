package memes.net.server;

import memes.game.event.IEventHandler;
import memes.net.packet.Packet;
import memes.util.Constants;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

public class NetServer {
    private ServerSocket servSocket;
    private long serverID;

    private Map<Long, NetClient> clientMap;
    private List<NetClient> clientSockets;
    private Queue<Packet> sendQueue, recvQueue;

    private Thread listenThread;
    protected IEventHandler handler;

    private boolean isRunning;

    private int id = 1;

    /**
     * Creates a NetServer that interacts with NetClients
     *
     * @param packetHandler a handler for packets received from Clients
     * @throws IOException
     */
    public NetServer(IEventHandler packetHandler) throws IOException {
        this.serverID = System.nanoTime();
        this.isRunning = true;
        this.handler = packetHandler;
        this.listenThread = new Thread(this::listen, "ServerThread");

        servSocket = new ServerSocket(Constants.PORT_NUM);
        clientSockets = new ArrayList<>();
        clientMap = new HashMap<>();

        sendQueue = new LinkedBlockingQueue<>();
        recvQueue = new LinkedBlockingQueue<>();
    }

    public void start() {
        listenThread.start();
    }
    public void stop() {
        isRunning = false;
    }

    private void listen() {
        while (isRunning) {
            try {
                Socket accept = servSocket.accept();

                // TODO: Use actual client id
                NetClient cs = new NetClient(accept, id, handler);
                if (!cs.handshake(id)) {
                    //cs.sendText("You are not a real client. Do one!");
                    cs.disconnect();
                    continue;
                }
                System.out.println("Client " + cs.getID() + " is connected");

                cs.start();

                clientSockets.add(cs);
                clientMap.put(cs.getID(), cs);
                id++;

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error");
            }
        }
        try {
            servSocket.close();
            clientSockets.forEach(NetClient::disconnect);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void broadcast(Packet packet, long sender) {
        // TODO: Add to send queue
        clientSockets.stream()
                .filter(c -> c.getID() != sender)
                .forEach(c -> send(packet, c.getID()));
    }

    public void send(Packet packet, long recipient) {
        NetClient cs = clientMap.get(recipient);
        if (cs == null)
            throw new IllegalArgumentException("Client doesn't exist. Wtf");

        System.out.println("[Server] Sending packet to Client[" + recipient + "] " + packet);
        cs.sendPacket(packet, serverID);
    }
    public long getServerID() {
        return serverID;
    }
}
