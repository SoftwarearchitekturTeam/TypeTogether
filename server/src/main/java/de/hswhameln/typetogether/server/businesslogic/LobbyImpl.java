package de.hswhameln.typetogether.server.businesslogic;

import de.hswhameln.typetogether.networking.api.Document;
import de.hswhameln.typetogether.networking.api.Lobby;
import de.hswhameln.typetogether.networking.api.User;

//TODO rename?
public class LobbyImpl implements Lobby {

    @Override
    public Document joinDocument(User user, String documentId) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void leaveDocument(User user, String documentId) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
