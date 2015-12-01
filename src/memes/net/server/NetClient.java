package memes.net.server;

import memes.game.event.IEventHandler;
import memes.net.packet.ConnectRequest;
import memes.net.packet.Packet;
import memes.util.Constants;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.*;
import java.net.Socket;

public class NetClient extends Thread {
    protected Socket socket;
    protected ObjectInputStream ois;
    protected ObjectOutputStream oos;

    protected long clientID;
    private long lastPacketTime;
    private IEventHandler handler;

    public NetClient(Socket socket, long clientID, IEventHandler handler) throws IOException {
        super("Client [" + clientID + "]");

        this.socket = socket;
        this.clientID = clientID;
        this.handler = handler;
        this.lastPacketTime = 0L;
    }

    public void setPacketHandler(IEventHandler handler) {
        this.handler = handler;
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
                handler.onPacketReceive(packet);

            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error reading packet");
                try {
                    ois.skip(ois.available());
                } catch (IOException e1) {
                    System.out.println("Error clearing stream..");
                }
            }
        }
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

        return new NetClient(socket, id, null);
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
        packet.setSender(clientID);
        packet.setSendTime(System.nanoTime());
        sendPacket(packet, clientID);
    }
    /**
     * Only to be used by the server to send as serverID
     * @param packet
     * @param sender
     * @throws IOException
     */
    public void sendPacket(Packet packet, long sender) {
        packet.setRecipient(clientID);
        packet.setSendTime(System.nanoTime());
        packet.getPacketType();
        try {
            if (!socket.isClosed()) {
                oos.writeObject(packet);
                oos.flush();
            } else {
                System.out.println("Socket is closed");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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

}
