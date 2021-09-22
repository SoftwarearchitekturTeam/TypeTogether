package de.hswhameln.typetogether.networking.proxy;

import de.hswhameln.typetogether.networking.util.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Logger;

/**
 * Complementary class to the {@link MarshallHandler}. Used to create and cache ClientProxies representing a given type T.
 */
public class ObjectResolver<T> {

    private final Function<Socket, T> clientProxySupplier;
    private final BufferedReader in;
    private final PrintWriter out;
    private final InetAddress targetInetAddress;

    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private final Map<Integer, T> objectsByCommunicationIds = new HashMap<>();


    public ObjectResolver(Function<Socket, T> clientProxySupplier, BufferedReader in, PrintWriter out, InetAddress targetInetAddress) {
        this.clientProxySupplier = clientProxySupplier;
        this.in = in;
        this.out = out;
        this.targetInetAddress = targetInetAddress;
    }

    public T resolveObject() throws IOException {
        int communicationId = Integer.parseInt(this.in.readLine());
        this.logger.info("Resolving object for communicationId " + communicationId);

        if (this.objectsByCommunicationIds.containsKey(communicationId)) {
            this.out.println(ResponseCodes.SUCCESS);
            return this.objectsByCommunicationIds.get(communicationId);
        }

        this.out.print(ResponseCodes.ADDITIONAL_INFO_REQUIRED);
        int port = IOUtils.getIntArgument("port", this.in, this.out);

        Socket socket = new Socket(this.targetInetAddress, port);

        T clientProxy = this.clientProxySupplier.apply(socket);
        this.objectsByCommunicationIds.put(communicationId, clientProxy);
        IOUtils.success(this.out);
        logger.info("Correctly resolved Object of type " + clientProxy.getClass().getName());
        return clientProxy;
    }



}
