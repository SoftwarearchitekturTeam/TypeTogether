package de.hswhameln.typetogether.server.runtime;

import de.hswhameln.typetogether.networking.api.Lobby;
import de.hswhameln.typetogether.server.businesslogic.LobbyImpl;
import de.hswhameln.typetogether.server.proxy.LobbyServerProxy;

public class ServerStarter {

    public static void main(String[] args) {
        Lobby lobby = new LobbyImpl();
        Server server = new Server(12557, socket -> new LobbyServerProxy(socket, lobby));
        server.start();
    }
}
