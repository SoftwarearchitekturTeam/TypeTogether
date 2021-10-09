package de.hswhameln.typetogether.client.runtime;

import de.hswhameln.typetogether.client.proxy.LobbyClientProxy;
import de.hswhameln.typetogether.client.runtime.commands.CommandInvoker;
import de.hswhameln.typetogether.networking.api.Lobby;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;

public class ClientRuntime {

    private static final String DEFAULT_URL = "localhost";
    private static final int DEFAULT_PORT = 12557;

    private final String url;
    private final int port;

    public ClientRuntime() {
        String url = System.getProperty("url");
        String port = System.getProperty("port");
        this.port = port == null ? DEFAULT_PORT : Integer.parseInt(port);
        this.url = url == null ? DEFAULT_URL : url;
    }

    public Lobby createLobby() {
        try {
            Socket lobbyClientSocket = new Socket(this.url, this.port);
            return new LobbyClientProxy(lobbyClientSocket);
        } catch (IOException e) {
            throw new RuntimeException("Could not connect to server", e);
        }
    }
}
