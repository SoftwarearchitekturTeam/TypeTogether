package de.hswhameln.typetogether.client.runtime;

import de.hswhameln.typetogether.client.proxy.LobbyClientProxy;
import de.hswhameln.typetogether.networking.api.Lobby;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;

public class ClientRuntime {

    private static final String DEFAULT_URL = "localhost";
    private static final int DEFAULT_PORT = 7777;

    private final String url;
    private final int port;

    public ClientRuntime(Map<String, String> args) {
        String url = args.get("url");
        String port = args.get("port");
        this.port = port == null ? DEFAULT_PORT : Integer.parseInt(port);
        this.url = url == null ? DEFAULT_URL : url;
    }

    public Lobby getLobby() {
        try {
        Socket lobbyClientSocket = null;
            lobbyClientSocket = new Socket(this.url, this.port);
        return new LobbyClientProxy(lobbyClientSocket);
        } catch (IOException e) {
            throw new RuntimeException("Could not connect to server", e);
        }
    }
}
