package de.hswhameln.typetogether.networking.proxy;

import de.hswhameln.typetogether.networking.shared.AbstractServerProxy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.logging.Logger;

public class MarshallHandler <T> {
    private final Map<T, Integer> communicationIdsByObjects = new HashMap<>();
    private final BiFunction<Socket, T, AbstractServerProxy> serverProxySupplier;
    private final PrintWriter out;
    private final BufferedReader in;

    private int knownObjectCount = 0;
    private final Random random = new Random();;
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public MarshallHandler(BiFunction<Socket, T, AbstractServerProxy> serverProxySupplier, BufferedReader in, PrintWriter out) {
        this.serverProxySupplier = serverProxySupplier;
        this.in = in;
        this.out = out;
    }

    /**
     * Marshall an object by sending its communication id to the server, creating a new ServerProxy for that object if the opposing system asks for it.
     * @param t The Object to be marshalled
     * @param <T> The Type of said object
     * @throws IOException If the connection is closed or otherwise obstructed
     */
    public void marshall(T t) throws IOException {
        if (!this.communicationIdsByObjects.containsKey(t)) {
            int communicationId = this.createCommunicationId();
            this.communicationIdsByObjects.put(t, communicationId);
        }
        int communicationId = this.communicationIdsByObjects.get(t);
        this.out.println(communicationId);
        String status = this.in.readLine();
        if (status.equals("0")) {
            logger.info("Server already got port for communicationId " + communicationId);
            return;
        }
        ServerSocket serverSocket = createServerSocket();

        while(!serverSocket.isClosed()) {
            Socket clientSocket = serverSocket.accept();
            new Thread(serverProxySupplier.apply(clientSocket, t)).start();
        }
    }

    private ServerSocket createServerSocket() {
        ServerSocket serverSocket = null;
        do {
            int port = this.random.nextInt(15000) + 50000;
            try {
                serverSocket = new ServerSocket(port);
            } catch (IOException e) {
                // continue
            }
        } while (serverSocket == null);
        return serverSocket;
    }

    private int createCommunicationId() {
        return this.knownObjectCount++;
    }
}
