package de.hswhameln.typetogether.client.proxy;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.Random;

import de.hswhameln.typetogether.networking.api.Document;
import de.hswhameln.typetogether.networking.api.User;
import de.hswhameln.typetogether.networking.shared.AbstractServerProxy;
import de.hswhameln.typetogether.networking.shared.ServerProxyAction;

public class UserServerProxy extends AbstractServerProxy {
    private User user;
    private Map<Document, Integer> communicationIdByLocalDocument;
    private int knownDocumentCount = 0;

    protected UserServerProxy(Socket socket, User user) {
        super(socket);
        this.user = user;
    }

    @Override
    protected Map<String, ServerProxyAction> createAvailableActions() {
        return Map.ofEntries(
            Map.entry("0", this.closeConnectionAction),
            Map.entry("1", ServerProxyAction.of("getId", this::doGetId)),
            Map.entry("2", ServerProxyAction.of("getName", this::doGetName)),
            Map.entry("3", ServerProxyAction.of("getDocument", this::doGetDocument))
        );
    }

    private void doGetId() {
        this.safelySendResult("getId", this.user::getId);
    }

    private void doGetName() {
        this.safelySendResult("getName", this.user::getName);
    }

    private void doGetDocument() {
        this.marshalDocument(this.user.getDocument());
    }

    private void marshalDocument(Document localDocument) {
        if (!this.communicationIdByLocalDocument.containsKey(localDocument)) {
            int communicationId = this.createCommunicationId();
            this.communicationIdByLocalDocument.put(localDocument, communicationId);
        }
        int communicationId = this.communicationIdByLocalDocument.get(localDocument);
        this.out.println(communicationId);
        String status;
        try {
            status = this.in.readLine();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return;
        }
        if (status.equals("0")) {
            logger.info("Server already got port for communicationId " + communicationId);
            return;
        }
        Random random = new Random();
        ServerSocket serverSocket = null;
        do {
            int port = random.nextInt(15000) + 50000;
            try {
                serverSocket = new ServerSocket(port);
            } catch (IOException e) {
                // continue
            }
        } while (serverSocket == null);
        
        // TODO accept + handle requests
    }

    private int createCommunicationId() {
        return this.knownDocumentCount++;
    }
        
}
