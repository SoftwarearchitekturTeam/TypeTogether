package de.hswhameln.typetogether.server.proxy;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import de.hswhameln.typetogether.networking.api.Document;
import de.hswhameln.typetogether.networking.shared.AbstractServerProxy;
import de.hswhameln.typetogether.networking.shared.ServerProxyAction;

public class DocumentServerProxy extends AbstractServerProxy {

    //private IChatProvider chatProvider;
    private Map<Integer, DocumentClientProxy> documents = new HashMap<>();
    
    public DocumentServerProxy(Socket socket/*, provider*/) {
        super(socket);
        //this.chatProvider = chatProvider;
    }

    private Document resolveDocument () {

        this.out.println("Please provide Document-Com-ID");
        try {
            String sCommunicationId = this.in.readLine();
            int communicationId = Integer.parseInt(sCommunicationId);
            this.logger.info("Resolving document for Com-ID: " + communicationId);
    
            if (this.documents.containsKey(communicationId) == false) {
    
                this.out.println("Please provide Document-Port");
    
                String sPort = this.in.readLine();
    
                int port = Integer.parseInt(sPort);
                DocumentClientProxy clientProxy = new DocumentClientProxy(new Socket(this.socket.getInetAddress(), port));
    
                this.documents.put(communicationId, clientProxy);
            } else {
                this.logger.warning("Could not resolve document for Com-ID: " + communicationId + " already in use");
                this.out.println("Could not resolve Document: Document-Com-ID already in use");
            }
            return this.documents.get(communicationId);
        } catch (Exception e) {
            this.out.println("[ERROR]: Could not read input: " + e.getMessage());
            this.logger.log(Level.SEVERE, "Could not read input", e);
            return null;
        }
        
    }

    private void addChar() {
        Document document = this.resolveDocument();

        try {
            // TODO: Provider this.documentProvider.addChar(document);
            this.out.println("200");
        } catch (Exception e) {
            this.out.println("500");
            this.out.println("[ERROR]: Could not add char: " + e.getMessage());
        }
    }

    private void removeChar() {
        Document document = this.resolveDocument();

        try {
            // TODO: Provider this.documentProvider.removeChar(document);
            this.out.println("200");
        } catch (Exception e) {
            this.out.println("500");
            this.out.println("[ERROR]: Could not remove char: " + e.getMessage());
        }
    }

    @Override
    protected Map<String, ServerProxyAction> createAvailableActions() {
        return Map.ofEntries(
            Map.entry("0", ServerProxyAction.of("addChar", this::addChar)),
            Map.entry("1", ServerProxyAction.of("removeChar", this::removeChar))
        );
    }
    
}
