package de.hswhameln.typetogether.server.businesslogic;

import de.hswhameln.typetogether.networking.api.Document;
import de.hswhameln.typetogether.networking.api.Lobby;
import de.hswhameln.typetogether.networking.api.User;
import de.hswhameln.typetogether.networking.api.exceptions.InvalidDocumentIdException;
import de.hswhameln.typetogether.networking.api.exceptions.UnknownUserException;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class LobbyImpl implements Lobby {

    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private final Map<String, DocumentDistributor> documentsById = new HashMap<>();

    @Override
    public Document getDocumentById(String documentId) throws InvalidDocumentIdException.DocumentDoesNotExistException {
        logger.info("Functional method getDocumentById called with documentId" + documentId);
        if (!this.documentsById.containsKey(documentId)) {
            throw new InvalidDocumentIdException.DocumentDoesNotExistException(documentId);
        }
        DocumentDistributor document = this.documentsById.get(documentId);
        return document;
    }

    @Override
    public void joinDocument(User user, String documentId) throws InvalidDocumentIdException.DocumentDoesNotExistException {
        logger.info("Functional method joinDocument called with user " + user + " and documentId" + documentId);
        if (!this.documentsById.containsKey(documentId)) {
            throw new InvalidDocumentIdException.DocumentDoesNotExistException(documentId);
        }
        DocumentDistributor documentToJoin = this.documentsById.get(documentId);
        documentToJoin.addUser(user);
    }

    @Override
    public void leaveDocument(User user, String documentId) throws InvalidDocumentIdException.DocumentDoesNotExistException, UnknownUserException {
        logger.info("Functional method leaveDocument called with user " + user + " and documentId" + documentId);
        if (!this.documentsById.containsKey(documentId)) {
            throw new InvalidDocumentIdException.DocumentDoesNotExistException(documentId);
        }
        DocumentDistributor documentToLeave = this.documentsById.get(documentId);
        if (!documentToLeave.isUserParticipant(user)) {
            throw new UnknownUserException();
        }
        documentToLeave.removeUser(user);
    }

    @Override
    public void createDocument(String documentId) throws InvalidDocumentIdException.DocumentAlreadyExistsException {
        logger.info("Functional method createDocument called with documentId" + documentId);
        if (this.documentsById.containsKey(documentId)) {
            throw new InvalidDocumentIdException.DocumentAlreadyExistsException(documentId);
        }
        DocumentDistributor documentDistributor = new DocumentDistributor(documentId);
        this.documentsById.put(documentId, documentDistributor);
    }

}
