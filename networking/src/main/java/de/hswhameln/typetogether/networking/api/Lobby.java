package de.hswhameln.typetogether.networking.api;

public interface Lobby {

    Document joinDocument(User user, String documentId);
    void leaveDocument(User user, String documentId);

}