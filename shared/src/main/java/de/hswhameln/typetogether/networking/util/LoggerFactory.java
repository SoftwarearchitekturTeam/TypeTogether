package de.hswhameln.typetogether.networking.util;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class LoggerFactory {

    private static final Level DEFAULT_LOG_LEVEL = Level.INFO;
    private static final Level LEVEL = getLogLevel();

    static {
        Logger rootLogger = LogManager.getLogManager().getLogger("");
        for (Handler h : rootLogger.getHandlers()) {
            h.setLevel(LEVEL);
        }
    }


    private static Level getLogLevel() {
        String systemProperty = System.getProperty("loglevel");
        return systemProperty != null ? Level.parse(systemProperty) : DEFAULT_LOG_LEVEL;
    }

    public static Logger getLogger(Class<?> clazz) {
        Logger logger = Logger.getLogger(clazz.getName());
        logger.setLevel(LEVEL);
        return logger;
    }

    public static Logger getLogger(Object thizz) {
        return getLogger(thizz.getClass());
    }
}
