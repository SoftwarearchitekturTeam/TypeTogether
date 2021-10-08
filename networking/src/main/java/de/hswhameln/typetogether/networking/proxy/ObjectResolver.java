package de.hswhameln.typetogether.networking.proxy;

import de.hswhameln.typetogether.networking.shared.AbstractClientProxy;
import de.hswhameln.typetogether.networking.shared.helperinterfaces.ClientProxyCreator;
import de.hswhameln.typetogether.networking.util.IOUtils;
import de.hswhameln.typetogether.networking.util.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Complementary class to the {@link MarshallHandler}. Used to create and cache ClientProxies representing a given type T.
 */
public class ObjectResolver<T> {

    private final ClientProxyCreator<T> clientProxySupplier;
    private final BufferedReader in;
    private final PrintWriter out;
    private final InetAddress targetInetAddress;

    private final Logger logger = LoggerFactory.getLogger(this);
    private final Map<Integer, T> objectsByCommunicationIds = new HashMap<>();


    public ObjectResolver(ClientProxyCreator<T> clientProxySupplier, BufferedReader in, PrintWriter out, InetAddress targetInetAddress) {
        this.clientProxySupplier = clientProxySupplier;
        this.in = in;
        this.out = out;
        this.targetInetAddress = targetInetAddress;
    }

    /**
     * Resolve an input object by asking the client for a communicationId. If the input object with the provided communicationId is already known, return it. Otherwise, ask the client for a port and establish a new connection.
     *
     * @return A client proxy object with the correct type
     * @throws IOException When there was a communication error
     */
    public T resolveObject() throws IOException {
        int communicationId = IOUtils.getIntArgument("communicationId", this.in, this.out);
        this.logger.info("Resolving object for communicationId " + communicationId);

        if (this.objectsByCommunicationIds.containsKey(communicationId)) {
            this.logger.info("Already knew object with communicationId " + communicationId + ". Continuing.");
            IOUtils.success(this.out);
            return this.objectsByCommunicationIds.get(communicationId);
        }

        this.out.println(ResponseCodes.ADDITIONAL_INFO_REQUIRED);
        this.logger.info("Requiring additional info for object resolution");
        int port = IOUtils.getIntArgument("port", this.in, this.out);

        logger.info("Starting new ClientSocket (" + this.targetInetAddress + ":" + port + ")");
        Socket socket = new Socket(this.targetInetAddress, port);
        logger.fine("Successfully connected to Socket.");
        T clientProxy = this.clientProxySupplier.create(socket);
        logger.fine("ClientProxy of type " + clientProxy.getClass().getSimpleName() + " was successfully created.");
        this.objectsByCommunicationIds.put(communicationId, clientProxy);
        IOUtils.success(this.out);
        logger.info("Correctly resolved Object of type " + clientProxy.getClass().getName());
        return clientProxy;
    }
}
