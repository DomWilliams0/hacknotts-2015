package memes.net.server;

import memes.net.PacketHandler;
import memes.net.packet.Packet;
import memes.util.Constants;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

public class NetServer {
    private ServerSocket servSocket;

    private Map<Long, NetClient> clientMap;
    private List<NetClient> clientSockets;
    private Queue<Packet> sendQueue, recvQueue;

    private Thread listenThread;
    protected List<PacketHandler> handlers;

    private boolean isRunning;

    public NetServer() throws IOException {
        this.isRunning = true;
        this.handlers = new ArrayList<>();
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

    public void addPacketHandler(PacketHandler handler) {
        handlers.add(handler);
    }

    private void listen() {
        while (isRunning) {
            try {
                Socket accept = servSocket.accept();

                // TODO: Handle client connection in the
                // game logic like getting ID and stuff
                long id = System.nanoTime();

                // TODO: Use actual client id
                NetClient cs = new NetClient(accept, id);
                if (!cs.handshake(id)) {
                    cs.sendText("You are not a real client. Do one!");
                    cs.disconnect();
                    continue;
                }
                cs.start();
                cs.addPacketHandler(System.out::println);

                System.out.println("Client " + cs.getID() + " is connected");

                clientSockets.add(cs);
                clientMap.put(cs.getID(), cs);

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

    public void send(Packet packet) throws IOException {
        send(packet, -1);
    }

    public void send(Packet packet, long clientID) throws IOException {
        if (clientID < 0) {
            sendAll(packet);
            return;
        }

        NetClient cs = clientMap.get(clientID);
        if (cs == null)
            throw new IllegalArgumentException("Client doesn't exist. Wtf");

        ObjectOutputStream oos = new ObjectOutputStream(cs.getSocket().getOutputStream());
        oos.writeObject(packet);
    }

    private void sendAll(Packet packet) {
        // TODO: Send packet to all
        throw new NotImplementedException();
    }
}