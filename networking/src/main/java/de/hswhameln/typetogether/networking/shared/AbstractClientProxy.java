package de.hswhameln.typetogether.networking.shared;

import de.hswhameln.typetogether.networking.api.exceptions.FunctionalException;
import de.hswhameln.typetogether.networking.shared.helperinterfaces.ProxiedSupplier;
import de.hswhameln.typetogether.networking.shared.helperinterfaces.ProxiedTask;
import de.hswhameln.typetogether.networking.shared.helperinterfaces.UnsafeSupplier;
import de.hswhameln.typetogether.networking.util.IOUtils;

import java.io.IOException;
import java.net.Socket;

import static de.hswhameln.typetogether.networking.util.ExceptionUtil.sneakyThrow;

public abstract class AbstractClientProxy extends AbstractProxy implements ClientProxy{

    public AbstractClientProxy(Socket socket) throws IOException {
        super(socket);
        this.readInitializationMessage();
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

    /**
     * Method complementing AbstractServerProxy#handleCommand() on client side. Han
     *
     * @param code The command code to send to the server
     */
    protected void chooseOption(String code) throws IOException {
        this.logger.fine("[Server]" + this.in.readLine());
        this.logger.info("Requesting execution of code " + code);
        this.out.println(code);
        IOUtils.expectResponseCodeSuccess(this.in);
        this.logger.info("[Server] " + this.in.readLine());
    }

    /**
     * Executes a chunk of code, wrapping all IOExceptions in RuntimeExceptions, and returning the produced result
     * <p>
     * Note: I hope you know what you are doing when you use this method!
     * </p>
     */
    protected <T> T safelyExecute(ProxiedSupplier<T> supplierWithIOExceptions) {
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
    protected void safelyExecute(ProxiedTask runnableWithIOException) {
        try {
            runnableWithIOException.run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (FunctionalException e) {
            throw sneakyThrow(e);
        }
    }
}
