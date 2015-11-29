package memes.net.server;

import memes.game.event.InputEvent;
import memes.game.input.InputKey;
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

public class Server {
    private ServerSocket servSocket;

    private Map<Long, Client> clientMap;
    private List<Client> clientSockets;
    private Queue<Packet> sendQueue, recvQueue;

    private Thread listenThread;
    private PacketHandler handler;

    private boolean isRunning;

    public Server(PacketHandler handler) throws IOException {
        this.handler = handler;
        this.isRunning = true;
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

    private void listen() {
        while (isRunning) {
            try {
                Socket accept = servSocket.accept();

                // TODO: Handle client connection in the
                // game logic like getting ID and stuff
                long id = System.nanoTime();

                // TODO: Use actual client id
                Client cs = new Client(accept, id);
                if (!cs.handshake(id)) {
                    cs.sendText("You are not a real client. Do one!");
                    cs.disconnect();
                    continue;
                }

                System.out.println("Client " + cs.getID() + " is connected");

                clientSockets.add(cs);
                clientMap.put(cs.getID(), cs);

            } catch (IOException e) {
                e.printStackTrace();
            }
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

        Client cs = clientMap.get(clientID);
        if (cs == null)
            throw new IllegalArgumentException("Client doesn't exist. Wtf");

        ObjectOutputStream oos = new ObjectOutputStream(cs.getSocket().getOutputStream());
        oos.writeObject(packet);
    }

    private void sendAll(Packet packet) {
        // TODO: Send packet to all
        throw new NotImplementedException();
    }

    public static void main(String[] args) throws InterruptedException {
        try {
            Server server = new Server(System.out::println);
            server.start();

            Client c1 = Client.connectToServer("localhost");
            c1.addPacketHandler(System.out::println);

            server.send(new InputEvent(InputKey.ACTION, true), c1.getID());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
