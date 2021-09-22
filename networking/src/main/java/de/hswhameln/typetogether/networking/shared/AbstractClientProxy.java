package de.hswhameln.typetogether.networking.shared;

import de.hswhameln.typetogether.networking.proxy.ResponseCodes;
import de.hswhameln.typetogether.networking.shared.helperinterfaces.Action;
import de.hswhameln.typetogether.networking.shared.helperinterfaces.UnsafeSupplier;
import de.hswhameln.typetogether.networking.util.IOUtils;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;

public abstract class AbstractClientProxy extends AbstractProxy {

    public AbstractClientProxy(Socket socket) {
        super(socket);
    }

    public AbstractClientProxy(String host, int port) throws IOException {
        this(new Socket(host, port));
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
