package de.hswhameln.typetogether.server.runtime;

import de.hswhameln.typetogether.networking.api.Lobby;
import de.hswhameln.typetogether.networking.util.LoggerFactory;
import de.hswhameln.typetogether.networking.util.ShutdownHelper;
import de.hswhameln.typetogether.server.businesslogic.LobbyImpl;
import de.hswhameln.typetogether.server.proxy.LobbyServerProxy;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ServerStarter {

    private static final int DEFAULT_PORT = 12557;
    private static Logger logger = LoggerFactory.getLogger(ServerStarter.class);

    public static void main(String[] args) {
        System.out.println("_______   _____ ___ _____ __   __ ___ _____ _  _ ___ ___");
        System.out.println("|_  _\\ `v' / _,\\ __|_   _/__\\ / _] __|_   _| || | __| _ \\");
        System.out.println(" | |  `. .'| v_/ _|  | || \\/ | [/\\ _|  | | | >< | _|| v /");
        System.out.println(" |_|   !_! |_| |___| |_| \\__/ \\__/___| |_| |_||_|___|_|_\\");

        String portArgument = System.getProperty("port");
        int port = portArgument == null ? DEFAULT_PORT : Integer.parseInt(portArgument);
        logger.log(Level.INFO, "Server starting on port " + port);

        ShutdownHelper.initialize();
        Lobby lobby = new LobbyImpl();
        Server server = new Server(port, socket -> {
            try {
                return new LobbyServerProxy(socket, lobby);
            } catch (IOException e) {
               throw new RuntimeException("Could not start server", e);
            }
        });
        server.start();
    }
}
