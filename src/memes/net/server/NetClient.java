package memes.net.server;

import memes.game.entity.PlayerEntity;
import memes.game.event.IEventHandler;
import memes.game.world.World;
import memes.net.packet.ConnectRequest;
import memes.net.packet.Packet;
import memes.net.packet.ConnectPacket;
import memes.net.packet.WorldPacket;
import memes.util.Constants;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class NetClient extends Thread implements IEventHandler {
    protected Socket socket;
    protected ObjectInputStream ois;
    protected ObjectOutputStream oos;
    protected long clientID;

    private long lastPacketTime;

    protected List<IEventHandler> handlers;

    private boolean isServer;

    public NetClient(Socket socket, long clientID, boolean isServer) throws IOException {
        super("Client " + clientID);

        this.socket = socket;
        this.isServer = isServer;
        this.clientID = clientID;
        this.handlers = new ArrayList<>();
        this.lastPacketTime = 0L;
    }

    @Override
    public void run() {
        while (socket.isConnected()) {
            try {
                Object o = ois.readObject();
                if (o == null)
                    continue;

                if (!(o instanceof Packet))
                    throw new UnsupportedOperationException("Received object is not a Packet. Uh wat");

                Packet packet = (Packet) o;
                System.out.println("[server: " + isServer + "] packet received = " + packet);


                if (isServer) {
                    if (packet.getSendTime() < lastPacketTime)
                        return;

                    lastPacketTime = packet.getSendTime();

                    if (packet instanceof ConnectRequest) {
                        ConnectRequest connPacket = (ConnectRequest) packet;

                        World world = GameServer.INSTANCE.getWorld();
                        PlayerEntity player = new PlayerEntity(
                                connPacket.getID(),
                                connPacket.getUsername(),
                                world.getSpawnTile(connPacket.getID())
                                        .multiply(Constants.TILE_SIZE)
                                        .add(Constants.TILE_SIZE / 2)
                        );

                        world.addEntity(player);

                        WorldPacket worldPacket = new WorldPacket(world);
                        try {
                            sendPacket(worldPacket);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }

                handlers.forEach(h -> h.onPacketReceive(packet));

            } catch (IOException | ClassNotFoundException e) {
                try {
                    socket.close();
                } catch (IOException e1) {
                    break;
                }
                break;
            }
        }
    }

    public void addPacketHandler(IEventHandler handler) {
        handlers.add(handler);
    }

    public void removePacketHandler(IEventHandler handler) {
        handlers.remove(handler);
    }

    /**
     * To be used by the client to connectToServer to the server
     *
     * @param host Server address
     * @return a Client connected to the Server
     * @throws IOException
     */
    public static NetClient connectToServer(String host) throws IOException {
        Socket socket = new Socket(host, Constants.PORT_NUM);
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        DataInputStream dis = new DataInputStream(socket.getInputStream());

        // receive and verify server handshake
        String hs = dis.readUTF();

        // invalid handshake
        if (!hs.equals(Constants.HANDSHAKE_SERVER))
            throw new IllegalStateException("Did not receive valid server handshake");

        // broadcast client handshake
        dos.writeUTF(Constants.HANDSHAKE_CLIENT);

        // receive client id
        long id = dis.readLong();

        // echo id for verification
        dos.writeLong(id);

        System.out.println("client's id = " + id);

        return new NetClient(socket, id, false);
    }

    /**
     * Sends a connect packet to the server
     * and gets all the server details
     *
     * @param playerName
     */
    public void requestServerWorld(long clientID, String playerName) throws Exception {
        this.start();

        ConnectRequest connPacket = new ConnectRequest(clientID, playerName);
        sendPacket(connPacket);
    }

    @Override
    public synchronized void start() {
        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        super.start();
    }

    /**
     * Called by Server to handshake with a client
     *
     * @param clientID id to broadcast to client
     * @return true if a successful connection
     * @throws IOException
     */
    public boolean handshake(long clientID) throws IOException {
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        DataInputStream dis = new DataInputStream(socket.getInputStream());

        // write server handshake
        dos.writeUTF(Constants.HANDSHAKE_SERVER);

        // read client handshake
        String hs = dis.readUTF();

        // invalid handshake
        if (!hs.equals(Constants.HANDSHAKE_CLIENT))
            throw new IllegalStateException("Did not receive valid client handshake");

        // write client id
        dos.writeLong(clientID);

        // return false if client doesn't accept id
        return dis.readLong() == clientID;
    }

    public boolean ping() {
        // TODO: Implement ping-pong pattern
        throw new NotImplementedException();
    }

    public void sendPacket(Packet packet) throws IOException {
        if (!socket.isClosed()) {
            packet.setSendTime(System.nanoTime());
            oos.writeObject(packet);
        }
    }

    public void sendText(String s) {
        // TODO: Send message packets to client
        throw new NotImplementedException();
    }

    public void disconnect() {
        try {
            socket.close();
        } catch (IOException e) {
        }
        // It really doesn't matter at this point
    }

    public long getID() {
        return clientID;
    }

    public Socket getSocket() {
        return socket;
    }


    @Override
    public void onPacketReceive(Packet event) {
        try {
            sendPacket(event);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public boolean isServer() {
        return isServer;
    }
}
