package de.hswhameln.typetogether.networking.shared;

import java.io.*;
import java.net.Socket;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AbstractServerProxy extends AbstractProxy implements Runnable {

    private final Map<String, ServerProxyAction> availableActions;

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public AbstractServerProxy(Socket socket, Map<String, ServerProxyAction> availableActions) {
        super(socket);
        this.availableActions = availableActions;
    }

    @Override
    public void run() {
        try {
            this.openStreams();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Could not open input stream.", e);
            return;
        }

        this.sendInitializationMessage();

        this.waitForCommands();

    }

    /**
     * Listen to requests coming from {@link #in} and perform the relevant action.
     */
    private void waitForCommands() {
        while (!this.socket.isClosed()) {
            try {
                String line = this.in.readLine();
                this.logger.info("Client sent: " + line);

                if (!this.availableActions.containsKey(line)) {
                    this.out.println("Unknown command: " + line);
                    this.logger.log(Level.WARNING, "Unknown command from client: " + line);
                    continue;
                }

                this.availableActions.get(line).getAction().perform();

            } catch (Exception e) {
                this.logger.log(Level.WARNING, "Exception when handling command. Continuing...", e);
            }
        }
    }

    /**
     * Sends an initialization message, which includes all available commands and the key with which they can be accessed.
     */
    private void sendInitializationMessage() {
        this.out.println("Connection established. Available commands:");
        this.out.println(this.availableActions.size());
        this.availableActions.forEach((id, action) -> {
            this.out.println(id + " - " + action.getName());
        });
    }
}
