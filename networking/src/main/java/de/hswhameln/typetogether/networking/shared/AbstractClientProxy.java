package de.hswhameln.typetogether.networking.shared;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

public abstract class AbstractClientProxy extends AbstractProxy{

    private Logger logger = Logger.getLogger(this.getClass().getName());
    public AbstractClientProxy(Socket socket) {
        super(socket);
    }

    public void start() throws Exception {
        try {
            this.openStreams();
            this.readInitializationMessage();
        } catch (IOException e) {
            throw new Exception("Error when initializing client proxy.", e);
        }


    }

    private void readInitializationMessage() throws IOException {
        logger.finer("Initializing connection...");
        int commandCount = Integer.parseInt(this.in.readLine());
        logger.finer(() -> "Found " + commandCount + " commands:");
        for (int i = 0; i < commandCount; i++) {
            logger.fine(this.in.readLine());
        }
    }

}
