package de.hswhameln.typetogether.networking.shared;

import de.hswhameln.typetogether.networking.api.exceptions.FunctionalException;
import de.hswhameln.typetogether.networking.proxy.ResponseCodes;
import de.hswhameln.typetogether.networking.shared.helperinterfaces.FunctionalFunction;
import de.hswhameln.typetogether.networking.shared.helperinterfaces.FunctionalTask;
import de.hswhameln.typetogether.networking.shared.helperinterfaces.UnsafeConsumer;

import java.io.IOException;
import java.net.Socket;
import java.util.Collections;
import java.util.Map;
import java.util.logging.Level;

public abstract class AbstractServerProxy extends AbstractProxy implements Runnable {

    private Map<String, ServerProxyAction> availableActions;
    protected ServerProxyAction closeConnectionAction = ServerProxyAction.of("closeConnection", this::closeConnection);

    protected AbstractServerProxy(Socket socket) throws IOException{
        super(socket);
    }

    @Override
    public void run() {
        logger.info("Started new " + this.getClass().getSimpleName() + ". Sending InitializationMessage and waiting for commands.");
        this.sendInitializationMessage();
        this.waitForCommands();
    }

    protected abstract Map<String, ServerProxyAction> createAvailableActions();

    protected void safelyExecute(String name, FunctionalTask functionalTask) throws IOException {
        try {
            functionalTask.run();
            logger.info("Successfully completed " + name + ". Sending success message.");
            this.success();
        } catch (IOException e) {
            throw e;
        } catch (FunctionalException e) {
            this.logger.log(Level.INFO, "Functional error", e);
            this.out.println(ResponseCodes.FUNCTIONAL_ERROR);
            this.out.println(e.getClass().getName());
            this.out.println(e.getMessage());
        } catch (Exception e) {
            this.logger.log(Level.INFO, "Unexpected error", e);
            this.error(ResponseCodes.UNEXPECTED_ERROR, "Error when executing " + name + ": " + e.getMessage());
        }
    }

    protected <T> void safelySendResult(String name, FunctionalFunction<T> functionalTask) throws IOException {
        this.safelySendResult(name, functionalTask, this.out::println);
    }

    protected <T> void safelySendResult(String name, FunctionalFunction<T> functionalTask, UnsafeConsumer<T> sender) throws IOException {
        this.executeAndSendExceptions(name, () -> {
            T t = functionalTask.apply();
            logger.info("Successfully completed " + name + ". Sending success message and returning output.");
            this.success();
            sender.accept(t);
        });
    }

    private void executeAndSendExceptions(String name, FunctionalTask functionalTask) throws IOException {
        try {
            functionalTask.run();
        } catch (IOException e) {
            throw e;
        } catch (FunctionalException e) {
            this.logger.log(Level.INFO, "Functional error", e);
            this.out.println(ResponseCodes.FUNCTIONAL_ERROR);
            this.out.println(e.getClass().getName());
            this.out.println(e.getMessage());
        } catch (Exception e) {
            this.logger.log(Level.INFO, "Unexpected error", e);
            this.error(ResponseCodes.UNEXPECTED_ERROR, "Error when executing " + name + ": " + e.getMessage());
        }
    }

    private void closeConnection() throws IOException {
        this.socket.close();
    }

    /**
     * Listen to requests coming from {@link #in} and perform the relevant action.
     */
    private void waitForCommands() {

        try {
            while (!this.socket.isClosed()) {
                handleCommand();
            }
        } catch (IOException e) {
            exceptionHandler.handle(e, Level.SEVERE, "Communication with ClientProxy was interrupted. Stopping connection.", this.getClass());
        } catch (Exception e) {
            exceptionHandler.handle(e, Level.SEVERE, "Unexpected exception when executing a command. Stopping connection.", this.getClass());
        }

        try {
            this.closeConnection();
        } catch (IOException e) {
            this.exceptionHandler.handle(e, Level.WARNING, "Could not close the connection. It's probably already closed.", this.getClass());
        }

    }

    /**
     * @throws IOException
     * @see AbstractClientProxy#chooseOption(String)
     */
    private void handleCommand() throws IOException {
        this.out.println("Waiting for you to pass the id of a command that should be executed.");
        String line = this.in.readLine();
        this.logger.fine("Client sent: " + line);

        if (!this.getAvailableActions().containsKey(line)) {
            this.error(ResponseCodes.NOT_FOUND, "Unknown command: " + line);
            this.logger.log(Level.WARNING, "Unknown command from client: " + line);
            return;
        }

        ServerProxyAction serverProxyAction = this.getAvailableActions().get(line);
        this.success();
        this.logger.info("Executing commmand " + serverProxyAction.getName());
        this.out.println("Executing commmand " + serverProxyAction.getName());
        serverProxyAction.getAction().perform();
    }

    /**
     * Sends an initialization message, which includes all available commands and the key with which they can be accessed.
     */
    private void sendInitializationMessage() {
        this.logger.fine("Sending initialization message");
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
