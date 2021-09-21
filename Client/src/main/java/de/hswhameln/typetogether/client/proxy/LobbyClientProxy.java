package de.hswhameln.typetogether.client.proxy;

import de.hswhameln.typetogether.networking.api.Document;
import de.hswhameln.typetogether.networking.api.Lobby;
import de.hswhameln.typetogether.networking.api.User;
import de.hswhameln.typetogether.networking.shared.AbstractClientProxy;

import java.net.Socket;
import java.util.logging.Logger;

public class LobbyClientProxy extends AbstractClientProxy implements Lobby {

    Logger logger = Logger.getLogger(this.getClass().getName());

    public LobbyClientProxy(Socket socket) {
        super(socket);
    }

    @Override
    public Document joinDocument(User user, String documentId) {
        logger.finer("joinDocument called");
        this.out.println("1");
        return null;
    }

    @Override
    public void leaveDocument(User user, String documentId) {
        logger.finer("leaveDocument called");
        this.out.println("2");

    }
}
