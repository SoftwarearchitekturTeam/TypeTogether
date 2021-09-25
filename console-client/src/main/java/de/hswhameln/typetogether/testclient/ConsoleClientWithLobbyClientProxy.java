package de.hswhameln.typetogether.testclient;

import de.hswhameln.typetogether.client.proxy.LobbyClientProxy;
import de.hswhameln.typetogether.networking.api.Document;
import de.hswhameln.typetogether.networking.api.Lobby;
import de.hswhameln.typetogether.networking.api.User;
import de.hswhameln.typetogether.networking.types.DocumentCharacter;
import de.hswhameln.typetogether.networking.types.Identifier;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ConsoleClientWithLobbyClientProxy {

    private final Socket socket;
    private final User user;
    private Lobby lobbyClientProxy;
    private ConsoleClientHelper consoleClientHelper;
    private Scanner sc;

    private Map<String, Document> documentsById = new HashMap<>();

    public ConsoleClientWithLobbyClientProxy(Scanner sc) throws IOException {
        this.sc = sc;
        this.consoleClientHelper = new ConsoleClientHelper(this.sc);
        this.socket = this.consoleClientHelper.createSocket();
        this.lobbyClientProxy = new LobbyClientProxy(this.socket);
        this.user = this.consoleClientHelper.createUser();
    }

    public void start() throws IOException {
        while (true) {
            System.out.println("/quit: quit, 1 <docId>: joinDocument, 2 <docId>: leaveDocument, 3 <docId> <char> addCharacter");
            String input = sc.nextLine();
            if (input.startsWith("/quit")) {
                this.socket.close();
                this.sc.close();
                break;
            } else if (input.startsWith("1")) {
                String docId = input.split(" ")[1];
                Document doc = this.lobbyClientProxy.joinDocument(this.user, docId);
                this.documentsById.put(docId, doc);
            } else if (input.startsWith("2")) {
                String docId = input.split(" ")[1];
                this.lobbyClientProxy.leaveDocument(this.user, docId);
                this.documentsById.remove(docId);
            } else if (input.startsWith("3")) {
                String[] s = input.split(" ");
                String docId = s[1];
                char c = s[2].charAt(0);
                Document doc = documentsById.get(docId);
                doc.addChar(this.user, new DocumentCharacter(c, new Identifier(0, 1)));
            }
        }
    }
}

