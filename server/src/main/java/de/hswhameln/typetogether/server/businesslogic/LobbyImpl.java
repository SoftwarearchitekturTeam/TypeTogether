package de.hswhameln.typetogether.server.businesslogic;

import de.hswhameln.typetogether.networking.api.Document;
import de.hswhameln.typetogether.networking.api.Lobby;
import de.hswhameln.typetogether.networking.api.User;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

//TODO rename?
public class LobbyImpl implements Lobby {

    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private Map<String, DocumentDistributor> documentsById = new HashMap<>();

    @Override
    public Document joinDocument(User user, String documentId) {
        logger.info("Functional method joinDocument called with user " + user + " and documentId" + documentId);
        if (!this.documentsById.containsKey(documentId)) {
            createNewDocument(documentId);
        }
        DocumentDistributor documentToJoin = this.documentsById.get(documentId);
        documentToJoin.addLocalDocument(user.getDocument());
        return documentToJoin;
    }

    @Override
    public void leaveDocument(User user, String documentId) {
        logger.info("Functional method leaveDocument called with user " + user + " and documentId" + documentId);
        if (!this.documentsById.containsKey(documentId)) {
            throw new RuntimeException("Document with id " + documentId + " does not exist."); // TODO sophisticated exception handling
        }
        DocumentDistributor documentToLeave = this.documentsById.get(documentId);
        Document localDocument = user.getDocument();
        if (!documentToLeave.containsDocument(localDocument)) {
            throw new RuntimeException("Document does not know user " + user + ". Could not leave document.");
        }
        documentToLeave.removeLocalDocument(localDocument);
    }

    private void createNewDocument(String documentId) {
        logger.info("Functional method createNewDocument called with documentId" + documentId);
        DocumentDistributor documentDistributor = new DocumentDistributor(documentId);
        this.documentsById.put(documentId, documentDistributor);
    }

}
