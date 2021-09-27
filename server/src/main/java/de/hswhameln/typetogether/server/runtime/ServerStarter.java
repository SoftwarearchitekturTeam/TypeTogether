package de.hswhameln.typetogether.server.runtime;

import de.hswhameln.typetogether.networking.api.Lobby;
import de.hswhameln.typetogether.networking.util.ArgumentParser;
import de.hswhameln.typetogether.networking.util.LoggerUtils;
import de.hswhameln.typetogether.server.businesslogic.LobbyImpl;
import de.hswhameln.typetogether.server.proxy.LobbyServerProxy;

import java.util.Map;
import java.util.logging.Level;

public class ServerStarter {

    public static void main(String[] args) {
        Map<String, String> arguments = ArgumentParser.parse(args);
        LoggerUtils.setLogLevel(Level.FINEST);

        Lobby lobby = new LobbyImpl();
        Server server = new Server(12557, socket -> new LobbyServerProxy(socket, lobby));
        server.start();
    }
}
