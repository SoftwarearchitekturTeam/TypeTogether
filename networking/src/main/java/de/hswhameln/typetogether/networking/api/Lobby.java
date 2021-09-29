package de.hswhameln.typetogether.networking.api;

public interface Lobby {


    void joinDocument(User user, String documentId);

    void leaveDocument(User user, String documentId);

    Document getDocumentById(String documentId);

    void createDocument(String documentId);
}