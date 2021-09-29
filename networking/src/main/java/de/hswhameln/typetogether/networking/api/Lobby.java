package de.hswhameln.typetogether.networking.api;

public interface Lobby {

    Document getDocumentById(String documentId);

    void joinDocument(User user, String documentId);

    void leaveDocument(User user, String documentId);

}