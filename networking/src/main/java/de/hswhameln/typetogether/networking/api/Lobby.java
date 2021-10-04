package de.hswhameln.typetogether.networking.api;

import de.hswhameln.typetogether.networking.api.exceptions.InvalidDocumentIdException;
import de.hswhameln.typetogether.networking.api.exceptions.UnknownUserException;

import java.util.Collection;

public interface Lobby {

    void joinDocument(User user, String documentId) throws InvalidDocumentIdException.DocumentDoesNotExistException;

    void leaveDocument(User user, String documentId) throws InvalidDocumentIdException.DocumentDoesNotExistException, UnknownUserException;

    Document getDocumentById(String documentId) throws InvalidDocumentIdException.DocumentDoesNotExistException;

    void createDocument(String documentId) throws InvalidDocumentIdException.DocumentAlreadyExistsException;

    void deleteDocument(User user, String documentId) throws InvalidDocumentIdException.DocumentDoesNotExistException;

    Collection<Document> getDocuments();

}