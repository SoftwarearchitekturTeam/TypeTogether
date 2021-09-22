package de.hswhameln.typetogether.testclient;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class ConsoleClientStarter {
    public static void main(String[] args) throws Exception {
        Logger rootLogger = LogManager.getLogManager().getLogger("");
        rootLogger.setLevel(Level.FINEST);
        for (Handler h : rootLogger.getHandlers()) {
            h.setLevel(Level.FINEST);
        }
        Logger.getLogger("myLogger").fine("Helo");

        new ConsoleClient().start();
    }
}
