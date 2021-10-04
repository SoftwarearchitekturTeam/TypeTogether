package de.hswhameln.typetogether.server.runtime;

import de.hswhameln.typetogether.networking.api.Lobby;
import de.hswhameln.typetogether.networking.util.LoggerUtils;
import de.hswhameln.typetogether.server.businesslogic.LobbyImpl;
import de.hswhameln.typetogether.server.proxy.LobbyServerProxy;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;


public class ServerStarter {

    private static final int DEFAULT_PORT = 12557;

    public static void main(String[] args) {
        LoggerUtils.setLogLevel(Level.parse(System.getProperty("loglevel")));
        String portArgument = System.getProperty("port");
        int port = portArgument == null ? DEFAULT_PORT : Integer.parseInt(portArgument);
        System.out.println("Server starting on port " + port);

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
