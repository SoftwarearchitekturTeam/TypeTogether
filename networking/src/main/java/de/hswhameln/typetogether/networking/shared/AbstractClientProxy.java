package de.hswhameln.typetogether.networking.shared;

import de.hswhameln.typetogether.networking.api.exceptions.FunctionalException;
import de.hswhameln.typetogether.networking.shared.helperinterfaces.ProxiedSupplier;
import de.hswhameln.typetogether.networking.shared.helperinterfaces.ProxiedTask;
import de.hswhameln.typetogether.networking.util.ObjectDestructor;

import java.io.IOException;
import java.net.Socket;

import static de.hswhameln.typetogether.networking.FluentExceptionHandler.expectSuccess;
import static de.hswhameln.typetogether.networking.util.ExceptionUtil.sneakyThrow;

public abstract class AbstractClientProxy extends AbstractProxy implements ClientProxy {

    private boolean closed = false;

    public AbstractClientProxy(Socket socket) throws IOException {
        super(socket);
        this.readInitializationMessage();
        ObjectDestructor.register(this);
    }

    /**
     * Initializes the connection to the server by reading the server's initialization message.
     */
    public void readInitializationMessage() throws IOException {
        logger.info("Reading initialization message...");
        logger.finer(this.in.readLine());
        int commandCount = Integer.parseInt(this.in.readLine());
        logger.finer(() -> "Found " + commandCount + " commands:");
        for (int i = 0; i < commandCount; i++) {
            logger.fine(this.in.readLine());
        }
    }

    public void closeConnection() {
        this.safelyExecute(() -> {
            this.logger.info("Requesting to close connection for " + this.getClass().getSimpleName() + " object.");
            this.out.println("0");
            this.closed = true;
        });
    }

    /**
     * Method complementing AbstractServerProxy#handleCommand() on client side. Han
     *
     * @param code The command code to send to the server
     */
    protected void chooseOption(String code) throws IOException, FunctionalException {
        this.logger.fine("[Server]" + this.in.readLine());
        this.logger.info("Requesting execution of code " + code);
        this.out.println(code);
        expectSuccess(this.in)
                .andHandleAllFunctionalExceptions();
        this.logger.info("[Server] " + this.in.readLine());
    }

    /**
     * Executes a chunk of code, wrapping all IOExceptions in RuntimeExceptions, and returning the produced result
     * <p>
     * Note: I hope you know what you are doing when you use this method!
     * </p>
     */
    protected synchronized <T> T safelyExecute(ProxiedSupplier<T> supplierWithIOExceptions) {
        try {
            return supplierWithIOExceptions.supply();
        } catch (IOException e) {
            throw new RuntimeException("Unexpected communication exception", e);
        } catch (FunctionalException e) {
            throw sneakyThrow(e);
        }
    }

    /**
     * Executes a chunk of code, wrapping all IOExceptions in RuntimeExceptions
     * <p>
     * Note: I hope you know what you are doing when you use this method!
     * </p>
     */
    protected synchronized void safelyExecute(ProxiedTask runnableWithIOException) {
        this.safelyExecute(() -> {
            runnableWithIOException.run();
            return null;
        });
    }

    public boolean isClosed() {
        return closed;
    }
}
