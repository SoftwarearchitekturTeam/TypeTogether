package de.hswhameln.typetogether.server.runtime;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Function;
import java.util.logging.Logger;

public class Server {

    private final Function<Socket, Runnable> onConnect;
    private ServerSocket serverSocket;
    private boolean running;

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public Server(int port, Function<Socket, Runnable> onConnect) {
        System.out.println("Starting server...");
        this.onConnect = onConnect;
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        if (this.running) {
            return;
        }
        this.running = true;
        while (this.running) {
            logger.info("Waiting for clients...");
            try {
                Socket clientSocket = this.serverSocket.accept();
                logger.info("Client connected.");
                new Thread(this.onConnect.apply(clientSocket)).start();
            } catch (Exception e) {
                // ignore any exception caused by the connection to a single client
                e.printStackTrace();
            }
        }

    }
}