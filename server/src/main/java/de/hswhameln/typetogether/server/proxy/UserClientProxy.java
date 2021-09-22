package de.hswhameln.typetogether.server.proxy;

import de.hswhameln.typetogether.networking.api.Document;
import de.hswhameln.typetogether.networking.api.User;
import de.hswhameln.typetogether.networking.shared.AbstractClientProxy;
import de.hswhameln.typetogether.networking.shared.DocumentClientProxy;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class UserClientProxy extends AbstractClientProxy implements User {

    Map<Integer, Document> localDocumentsByCommunicationIds = new HashMap<>();

    public UserClientProxy(Socket socket) {
        super(socket);
    }

    private Document resolveDocument() {
        try {
            String sCommunicationId = this.in.readLine();
            int communicationId = Integer.parseInt(sCommunicationId);
            this.logger.info("Resolving document for Com-ID: " + communicationId);
    
            if (!this.localDocumentsByCommunicationIds.containsKey(communicationId)) {
    
                this.out.println("1");
    
                String sPort = this.in.readLine();
    
                int port = Integer.parseInt(sPort);
                DocumentClientProxy clientProxy = new DocumentClientProxy(new Socket(this.socket.getInetAddress(), port));
    
                this.localDocumentsByCommunicationIds.put(communicationId, clientProxy);
            } else {
                this.logger.warning("Could not resolve document for Com-ID: " + communicationId + " already in use");
                this.out.println("0");
            }
            return this.localDocumentsByCommunicationIds.get(communicationId);
        } catch (Exception e) {
            this.out.println("[ERROR]: Could not read input: " + e.getMessage());
            this.logger.log(Level.SEVERE, "Could not read input", e);
            return null;
        }
    }

    @Override
    public int getId() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Document getDocument() {
        // TODO Auto-generated method stub
        return null;
    }

    // TODO
}
