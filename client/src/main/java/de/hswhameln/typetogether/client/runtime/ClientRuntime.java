package de.hswhameln.typetogether.client.runtime;

import de.hswhameln.typetogether.networking.LocalDocument;
import de.hswhameln.typetogether.client.businesslogic.ClientUser;
import de.hswhameln.typetogether.client.businesslogic.LocalDocumentSender;
import de.hswhameln.typetogether.client.proxy.LobbyClientProxy;
import de.hswhameln.typetogether.networking.api.Document;
import de.hswhameln.typetogether.networking.api.Lobby;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;

public class ClientRuntime {

    private static final String DEFAULT_URL = "localhost";
    private static final int DEFAULT_PORT = 12557;

    private final String url;
    private final int port;
    private ClientUser user;
    private LocalDocumentSender sender;

    public ClientRuntime(Map<String, String> args) {
        String url = args.get("url");
        String port = args.get("port");
        this.port = port == null ? DEFAULT_PORT : Integer.parseInt(port);
        this.url = url == null ? DEFAULT_URL : url;
    }

    public Lobby getLobby() {
        try {
            Socket lobbyClientSocket = new Socket(this.url, this.port);
            return new LobbyClientProxy(lobbyClientSocket);
        } catch (IOException e) {
            throw new RuntimeException("Could not connect to server", e);
        }
    }

    public void setUser(ClientUser user) {
        this.user = user;
    }

    public ClientUser getUser() {
        return this.user;
    }

    public LocalDocument getLocalDocument() {
        return (LocalDocument) this.user.getDocument();
    }

    public void setLocalDocument(LocalDocument localDocument) {
        this.user.setDocument(localDocument);
    }

    public LocalDocumentSender getSender() {
        return sender;
    }

    public void generateSender(Document serverDocument) {
        this.sender = new LocalDocumentSender(serverDocument, this.user);
    }
}
