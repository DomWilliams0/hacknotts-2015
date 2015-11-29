package memes.net.server;

import memes.net.PacketHandler;
import memes.net.packet.Packet;
import memes.util.Constants;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client extends Thread {
    protected Socket socket;
    protected ObjectInputStream ois;
    protected ObjectOutputStream oos;
    protected long clientID;
    protected List<PacketHandler> handlers;

    public Client(Socket socket, long clientID) throws IOException {
        super("Client " + clientID);

        this.socket = socket;
        this.clientID = clientID;
        this.handlers = new ArrayList<>();
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

                handlers.forEach(h -> h.onPacketRecieve((Packet) o));

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

    public void addPacketHandler(PacketHandler handler) {
        handlers.add(handler);
    }

    /**
     * To be used by the client to connectToServer to the server
     *
     * @param host Server address
     * @return a Client connected to the Server
     * @throws IOException
     */
    public static Client connectToServer(String host) throws IOException {
        Socket socket = new Socket(host, Constants.PORT_NUM);
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        DataInputStream dis = new DataInputStream(socket.getInputStream());

        // receive and verify server handshake
        String hs = dis.readUTF();

        // invalid handshake
        if (!hs.equals(Constants.HANDSHAKE_SERVER))
            throw new IllegalStateException("Did not receive valid server handshake");

        // send client handshake
        dos.writeUTF(Constants.HANDSHAKE_CLIENT);

        // receive client id
        long id = dis.readLong();

        // echo id for verification
        dos.writeLong(id);

        System.out.println("client's id = " + id);

        Client client = new Client(socket, id);
        client.start();

        return client;
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
     * @param clientID id to send to client
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
    public void sendPacket(Packet packet) throws Exception {
        oos.writeObject(packet);
    }
    public void sendText(String s) {
        // TODO: Send message packets to client
        throw new NotImplementedException();
    }
    public void disconnect() throws IOException {
        socket.close();
    }

    public long getID() {
        return clientID;
    }
    public Socket getSocket() {
        return socket;
    }
}
