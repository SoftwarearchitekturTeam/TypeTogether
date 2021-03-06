package de.hswhameln.typetogether.server.runtime;

import de.hswhameln.typetogether.networking.util.ExceptionHandler;
import de.hswhameln.typetogether.networking.util.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    private final Function<Socket, Runnable> onConnect;
    private ServerSocket serverSocket;
    private boolean running;

    private final Logger logger = LoggerFactory.getLogger(this);

    public Server(int port, Function<Socket, Runnable> onConnect) {
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
                Runnable runnable = this.onConnect.apply(clientSocket);
                new Thread(runnable).start();
            } catch (Exception e) {
                // ignore any exception caused by the connection to a single client
                ExceptionHandler.getExceptionHandler().handle(e, Level.SEVERE, "Unexpected exception when handling client.", Server.class);
            }
        }

    }
}
