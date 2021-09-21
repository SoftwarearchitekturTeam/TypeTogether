package de.hswhameln.typetogether.networking.shared;

import java.io.IOException;
import java.net.Socket;
import java.util.Collections;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractServerProxy extends AbstractProxy implements Runnable {

    private Map<String, ServerProxyAction> availableActions;

    protected AbstractServerProxy(Socket socket) {
        super(socket);
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

    protected abstract Map<String, ServerProxyAction> createAvailableActions();

    /**
     * Listen to requests coming from {@link #in} and perform the relevant action.
     */
    private void waitForCommands() {
        while (!this.socket.isClosed()) {
            try {
                String line = this.in.readLine();
                this.logger.info("Client sent: " + line);

                if (!this.getAvailableActions().containsKey(line)) {
                    this.out.println("Unknown command: " + line);
                    this.logger.log(Level.WARNING, "Unknown command from client: " + line);
                    continue;
                }

                this.getAvailableActions().get(line).getAction().perform();

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
        this.out.println(this.getAvailableActions().size());
        this.getAvailableActions().forEach((id, action) -> {
            this.out.println(id + " - " + action.getName());
        });
    }

    private Map<String, ServerProxyAction> getAvailableActions() {
        if (this.availableActions == null) {
            Map<String, ServerProxyAction> availableActions = this.createAvailableActions();
            this.availableActions = availableActions != null ? availableActions : Collections.emptyMap();
        }
        return this.availableActions;
    }
}
