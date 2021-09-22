package de.hswhameln.typetogether.server.proxy;

import de.hswhameln.typetogether.networking.api.Document;
import de.hswhameln.typetogether.networking.api.User;
import de.hswhameln.typetogether.networking.shared.AbstractClientProxy;
import de.hswhameln.typetogether.networking.shared.DocumentClientProxy;

import java.io.IOException;
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
        this.out.println("1");
        try {
            int status = Integer.parseInt(this.in.readLine());
            if (status != 200) {
                String errMsg = this.in.readLine();
                logger.warning("[ClIENT ERROR]: " + errMsg);
                throw new RuntimeException(errMsg);
            }
            return Integer.parseInt(this.in.readLine()); 
        } catch (NumberFormatException | IOException e) {
            logger.severe("[SERVER ERROR]: Could not get user-Id from client: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getName() {
        this.out.println("2");
        try {
            int status = Integer.parseInt(this.in.readLine());
            if (status != 200) {
                String errMsg = this.in.readLine();
                logger.warning("[ClIENT ERROR]: " + errMsg);
                throw new RuntimeException(errMsg);
            }
            return this.in.readLine(); 
        } catch (NumberFormatException | IOException e) {
            logger.severe("[SERVER ERROR]: Could not get user-name from client: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Document getDocument() {
        this.out.println("3");
        try {
            int status = Integer.parseInt(this.in.readLine());
            if (status != 200) {
                String errMsg = this.in.readLine();
                logger.warning("[ClIENT ERROR]: " + errMsg);
                throw new RuntimeException(errMsg);
            }
            return this.resolveDocument();
        } catch (NumberFormatException | IOException e) {
            logger.severe("[SERVER ERROR]: Could not get user-document from client: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    // TODO Close Connection, but how?
}
