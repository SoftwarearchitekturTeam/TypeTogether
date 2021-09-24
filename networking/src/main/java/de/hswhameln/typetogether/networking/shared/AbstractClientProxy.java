package de.hswhameln.typetogether.networking.shared;

import de.hswhameln.typetogether.networking.shared.helperinterfaces.Action;
import de.hswhameln.typetogether.networking.shared.helperinterfaces.UnsafeSupplier;
import de.hswhameln.typetogether.networking.util.IOUtils;

import java.io.IOException;
import java.net.Socket;

public abstract class AbstractClientProxy extends AbstractProxy {

    public AbstractClientProxy(Socket socket) {
        super(socket);
        try {
            this.readInitializationMessage();
        } catch (IOException e) {
            throw new RuntimeException("Error when initializing client proxy.", e);
        }
    }

    /**
     * Initializes the connection to the server by reading the server's initialization message.
     */
    public void readInitializationMessage() throws IOException {
        logger.info("Reading initialization message...");
        int commandCount = Integer.parseInt(this.in.readLine());
        logger.finer(() -> "Found " + commandCount + " commands:");
        for (int i = 0; i < commandCount; i++) {
            logger.fine(this.in.readLine());
        }
    }

    /**
     * Method complementing AbstractServerProxy#handleCommand() on client side. Han
     *
     * @param code The command code to send to the server
     */
    protected void chooseOption(String code) throws IOException {
        this.logger.info("Requesting execution of code " + code);
        this.out.println(code);
        IOUtils.expectResponseCodeSuccess(this.in);
        logger.info("[Server]" + this.in.readLine());
    }

    /**
     * Executes a chunk of code, wrapping all IOExceptions in RuntimeExceptions, and returning the produced result
     * <p>
     * Note: I hope you know what you are doing when you use this method!
     * </p>
     */
    protected <T> T safelyExecute(UnsafeSupplier<T> supplierWithIOExceptions) {
        try {
            return supplierWithIOExceptions.supply();
        } catch (IOException e) {
            throw new RuntimeException("Unexpected communication exception", e);
        }
    }

    /**
     * Executes a chunk of code, wrapping all IOExceptions in RuntimeExceptions
     * <p>
     * Note: I hope you know what you are doing when you use this method!
     * </p>
     */
    protected void safelyExecute(Action runnableWithIOException) {
        try {
            runnableWithIOException.perform();
        } catch (IOException e) {
            throw new RuntimeException("Unexpected communication exception", e);
        }
    }

}
