package de.hswhameln.typetogether.networking.util;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class LoggerUtils {
    public static void setLogLevel(Level level) {
        Logger rootLogger = LogManager.getLogManager().getLogger("");
        rootLogger.setLevel(level);
        for (Handler h : rootLogger.getHandlers()) {
            h.setLevel(level);
        }
    }
}
