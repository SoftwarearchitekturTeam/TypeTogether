package de.hswhameln.typetogether.networking.util;

import de.hswhameln.typetogether.networking.shared.AbstractClientProxy;

import java.util.logging.Logger;

public class ObjectDestructor {
    private static final Logger logger = LoggerFactory.getLogger(ObjectDestructor.class);

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
}
