package de.hswhameln.typetogether.networking.shared;

import de.hswhameln.typetogether.networking.proxy.ResponseCodes;
import de.hswhameln.typetogether.networking.shared.helperinterfaces.FunctionalFunction;
import de.hswhameln.typetogether.networking.shared.helperinterfaces.FunctionalTask;
import de.hswhameln.typetogether.networking.util.IOUtils;

import java.io.IOException;
import java.net.Socket;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Level;

public abstract class AbstractServerProxy extends AbstractProxy implements Runnable {

    private Map<String, ServerProxyAction> availableActions;
    protected ServerProxyAction closeConnectionAction = ServerProxyAction.of("closeConnection", this::closeConnection);

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

    protected void safelyExecute(String name, FunctionalTask functionalTask) {
        try {
            functionalTask.run();
            this.success();
        } catch (Exception e) {
            this.out.println(1);
            this.out.println("Error when executing joinDocument: " + e.getMessage());
        }
    }

    protected <T> void safelySendResult(String name, FunctionalFunction<T> functionalTask) {
        try {
            T t = functionalTask.apply();
            this.success();
            this.out.println(t);
        } catch (Exception e) {
            //TODO: Error Codes festlegen
            this.error(ResponseCodes.FUNCTIONAL_ERROR, "Error when executing " + name + ": " + e.getMessage());
        }
    }

    private void closeConnection() throws IOException {
        this.socket.close();
    }

    /**
     * Resolve an input object by asking the client for a communicationId. If the input object with the provided communicationId is already known, return it. Otherwise, ask the client for a port and establish a new connection.
     *
     * @param objectName                    Type name of the input object - for debugging purposes only.
     * @param inputObjectsByCommunicationId Map of existing inputObjects. Will be mutated when a new connection is established
     * @param inputObjectSupplier           Function to create a client proxy for the given input object, given a socket
     * @param <T>                           Type of the input object
     * @return An client proxy object with the correct type
     * @throws IOException When there was a communication error
     */
    protected <T> T resolveInputObject(String objectName, Map<Integer, T> inputObjectsByCommunicationId, Function<Socket, T> inputObjectSupplier) throws IOException {
        int communicationId = IOUtils.getIntArgument(objectName + "communicationId", this.in, this.out);
        if (inputObjectsByCommunicationId.containsKey(communicationId)) {
            this.out.println(0);
            logger.finer(objectName + " with communicationId " + communicationId + " is already known. Continuing.");
            return inputObjectsByCommunicationId.get(communicationId);
        }
        this.out.print(1);
        this.out.println("Establishing connection. Please provide a communication port.");
        int port = Integer.parseInt(this.in.readLine());
        Socket clientSocket = new Socket(this.socket.getInetAddress().getHostAddress(), port);
        T inputObject = inputObjectSupplier.apply(clientSocket);
        inputObjectsByCommunicationId.put(communicationId, inputObject);
        this.out.println(0);
        logger.fine("Successfully added " + objectName + " with communicationId " + communicationId);
        return inputObject;
    }

    /**
     * Listen to requests coming from {@link #in} and perform the relevant action.
     */
    private void waitForCommands() {
        while (!this.socket.isClosed()) {
            try {
                handleCommand();

            } catch (Exception e) {
                this.logger.log(Level.WARNING, "Exception when handling command. Continuing...", e);
            }
        }
    }

    /**
     * @see AbstractClientProxy#chooseOption(String)
     * @throws IOException
     */
    private void handleCommand() throws IOException {
        String line = this.in.readLine();
        this.logger.info("Client sent: " + line);

        if (!this.getAvailableActions().containsKey(line)) {
            this.error(ResponseCodes.NOT_FOUND, "Unknown command: " + line);
            this.logger.log(Level.WARNING, "Unknown command from client: " + line);
            return;
        }

        ServerProxyAction serverProxyAction = this.getAvailableActions().get(line);
        this.success();
        this.out.println("Executing commmand " + serverProxyAction.getName());
        serverProxyAction.getAction().perform();
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
