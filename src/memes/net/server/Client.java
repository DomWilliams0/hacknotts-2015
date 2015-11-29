package memes.net.server;

import memes.net.PacketHandler;
import memes.net.packet.Packet;
import memes.util.Constants;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Client extends Thread {
    protected Socket socket;
    protected ObjectInputStream ois;
    protected ObjectOutputStream oos;
    protected long clientID;
    protected List<PacketHandler> handlers;

    public Client(Socket socket, long clientID, PacketHandler handler) throws IOException {
        super("Client " + clientID);

        this.socket = socket;
        this.clientID = clientID;
        this.handlers = new ArrayList<>();
    }

    /**
     * To be used by the client to connect to the server
     * @param host
     * @return
     * @throws IOException
     */
    public static Client connect(String host) throws IOException {
        Socket socket = new Socket(host, Constants.PORT_NUM);
        OutputStream os = socket.getOutputStream();
        InputStream is = socket.getInputStream();

        os.write(Constants.HANDSHAKE_CLIENT.getBytes());
        byte[] reply = new byte[Constants.HANDSHAKE_SERVER.length()];
        int bytes = is.read(reply);

        if (bytes != reply.length)
            return null;

        Client client = new Client(socket, new Random().nextLong(), null);
        client.start();
        return client;
    }

    public void addPacketHandler(PacketHandler handler) {
        handlers.add(handler);
    }

    @Override
    public void run() {
        try {
            this.ois = new ObjectInputStream(socket.getInputStream());
            this.oos = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (socket.isConnected()) {
            try {
                Object o = ois.readObject();
                if (o == null)
                    continue;

                if (!(o instanceof Packet))
                    throw new UnsupportedOperationException("Received object is not a Packet. Uh wat");

                handlers.forEach(h -> h.onPacketRecieve((Packet) o));

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean handshake() throws IOException {
        OutputStream os = socket.getOutputStream();
        InputStream is = socket.getInputStream();

        byte[] reply = new byte[Constants.HANDSHAKE_CLIENT.length()];
        int bytes = is.read(reply);

        if (bytes != reply.length)
            return false;

        os.write(Constants.HANDSHAKE_SERVER.getBytes());
        return true;
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
