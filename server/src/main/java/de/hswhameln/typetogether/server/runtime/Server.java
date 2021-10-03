package de.hswhameln.typetogether.server.runtime;

import de.hswhameln.typetogether.networking.util.ExceptionHandler;

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

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public Server(int port, Function<Socket, Runnable> onConnect) {
        System.out.println("_______   _____ ___ _____ __   __ ___ _____ _  _ ___ ___");  
        System.out.println("|_  _\\ `v' / _,\\ __|_   _/__\\ / _] __|_   _| || | __| _ \\");
        System.out.println(" | |  `. .'| v_/ _|  | || \\/ | [/\\ _|  | | | >< | _|| v /"); 
        System.out.println(" |_|   !_! |_| |___| |_| \\__/ \\__/___| |_| |_||_|___|_|_\\");
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
                Runnable runnable = this.onConnect.apply(clientSocket);
                new Thread(runnable).start();
            } catch (Exception e) {
                // ignore any exception caused by the connection to a single client
                ExceptionHandler.getExceptionHandler().handle(e, Level.SEVERE, "Unexpected exception when handling client.", Server.class);
            }
        }

    }
}
