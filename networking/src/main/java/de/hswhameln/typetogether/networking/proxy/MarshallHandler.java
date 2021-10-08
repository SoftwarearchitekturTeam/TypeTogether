package de.hswhameln.typetogether.networking.proxy;

import de.hswhameln.typetogether.networking.shared.helperinterfaces.ServerProxyCreator;
import de.hswhameln.typetogether.networking.util.ExceptionHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import static de.hswhameln.typetogether.networking.FluentExceptionHandler.expectSuccess;
import static de.hswhameln.typetogether.networking.util.ExceptionUtil.sneakyThrow;

public class MarshallHandler<T> {
    private final Map<T, Integer> communicationIdsByObjects = new HashMap<>();
    private final ServerProxyCreator<T> serverProxySupplier;
    private final PrintWriter out;
    private final BufferedReader in;

    private final ExceptionHandler exceptionHandler = ExceptionHandler.getExceptionHandler();

    private int knownObjectCount = 0;
    private final Random random = new Random();

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public MarshallHandler(ServerProxyCreator<T> serverProxySupplier, BufferedReader in, PrintWriter out) {
        this.serverProxySupplier = serverProxySupplier;
        this.in = in;
        this.out = out;
    }

    /**
     * Marshall an object by sending its communication id to the server, creating a new ServerProxy for that object if the opposing system asks for it.
     *
     * @param t The Object to be marshalled
     * @throws IOException If the connection is closed or otherwise obstructed
     */
    public void marshall(T t) throws IOException {
        logger.fine("[Server]: " + this.in.readLine());
        if (!this.communicationIdsByObjects.containsKey(t)) {
            int communicationId = this.createCommunicationId();
            this.communicationIdsByObjects.put(t, communicationId);
        }
        int communicationId = this.communicationIdsByObjects.get(t);
        this.out.println(communicationId);
        logger.fine("Sent communicationId: " + communicationId);
        String responseCode = this.in.readLine();
        if (ResponseCodes.SUCCESS.equals(responseCode)) {
            logger.info("Server already got port for communicationId " + communicationId);
            return;
        }
        if (!ResponseCodes.ADDITIONAL_INFO_REQUIRED.equals(responseCode)) {
            throw new RuntimeException("Unexpected response code: " + responseCode);
        }
        ServerSocket serverSocket = createServerSocket();
        logger.info("Started server socket at port " + serverSocket.getLocalPort() + ".");

        // "Provide a port"
        logger.info("[Server] " + this.in.readLine());
        this.out.println(serverSocket.getLocalPort());
        Socket clientSocket = serverSocket.accept();
        logger.info("New Client connected who wants to access " + t);

        new Thread(
                this.serverProxySupplier
                        .create(clientSocket, t)
                        .withShutDownHook(() -> closeServerSocket(serverSocket))
        ).start();

        expectSuccess(this.in);

    }

    private ServerSocket createServerSocket() {
        ServerSocket serverSocket = null;
        do {
            int port = this.random.nextInt(15000) + 50000;
            try {
                serverSocket = new ServerSocket(port);
            } catch (IOException e) {
                this.exceptionHandler.handle(e, Level.INFO, "Error while creating server socket. The port " + port + " is probably already taken. Trying another port.", this.getClass());
                // continue
            }
        } while (serverSocket == null);
        return serverSocket;
    }

    private void closeServerSocket(ServerSocket serverSocket) {
        try {
            serverSocket.close();
        } catch (IOException e) {
            ExceptionHandler.getExceptionHandler().handle(e, Level.WARNING, "Could not close Server socket. It's probably already closed. Continuing as usual.", MarshallHandler.class);
        }
    }

    private int createCommunicationId() {
        return this.knownObjectCount++;
    }
}
