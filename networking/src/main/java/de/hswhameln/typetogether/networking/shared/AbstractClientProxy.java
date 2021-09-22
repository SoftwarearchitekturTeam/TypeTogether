package de.hswhameln.typetogether.networking.shared;

import de.hswhameln.typetogether.networking.proxy.ResponseCodes;

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
     * see AbstractServerProxy#handleCommand()
     *
     * @param code
     */
    protected void chooseOption(String code) {
        this.logger.info("Requesting execution of code " + code);
        this.out.println(code);
        try {
            String responseCode = this.in.readLine();
            if (!ResponseCodes.SUCCESS.equals(responseCode)) {
                logger.warning("Action unsuccessful, response code: " + responseCode);
                String message = this.in.readLine();
                throw new RuntimeException("Error (" + responseCode + ") when choosing option : " + message);
            }
            logger.info("[Server]" + this.in.readLine());
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Unexpected Error in chooseOption", e);
        }
    }

    /**
     * Read the response code - do nothing if it is SUCCESS, otherwise read the error message and throw a RuntimeException which includes said message.
     *
     * <p>
     * This is a simple method for when there are only two possible results - success or error. Don't use this method if there are some states which need special treatment.
     * </p>
     *
     * @throws IOException On communication errors
     */
    protected void expectResponseCodeSuccess() throws IOException {
        String responseCode = this.in.readLine();
        if (ResponseCodes.SUCCESS.equals(responseCode)) {
            return;
        }
        logger.warning("Action unsuccessful, response code: " + responseCode);
        String message = this.in.readLine();
        throw new RuntimeException("Error (" + responseCode + ") when choosing option : " + message);
    }
}
