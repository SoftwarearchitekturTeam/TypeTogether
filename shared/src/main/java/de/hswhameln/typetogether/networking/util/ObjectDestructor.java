package de.hswhameln.typetogether.networking.util;

import de.hswhameln.typetogether.networking.shared.AbstractClientProxy;

import java.util.Collection;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.function.Predicate.not;

public class ObjectDestructor {
    private static final Logger logger = LoggerFactory.getLogger(ObjectDestructor.class);

    private static final Collection<AbstractClientProxy> allClientProxies = new HashSet<>();

    public static void destroy(Object object) {
        logger.info("Trying to destroy object " + object);
        if (!(object instanceof AbstractClientProxy)) {
            return;
        }
        var abstractClientProxy = (AbstractClientProxy) object;
        if (abstractClientProxy.isClosed()) {
            return;
        }
        abstractClientProxy.closeConnection();
    }

    public static void destroyAll() {
        logger.log(Level.INFO,"Cleaning up by closing all client connections");
        allClientProxies.stream()
                .filter(not(AbstractClientProxy::isClosed))
                .forEach(AbstractClientProxy::closeConnection);
    }

    public static void register(AbstractClientProxy abstractClientProxy) {
        allClientProxies.add(abstractClientProxy);
    }
}
