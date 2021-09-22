package de.hswhameln.typetogether.server.proxy;

import de.hswhameln.typetogether.networking.api.User;
import de.hswhameln.typetogether.networking.shared.AbstractClientProxy;

import java.net.Socket;

public class UserClientProxy extends AbstractClientProxy implements User {

    public UserClientProxy(Socket socket) {
        super(socket);
    }

    // TODO
}
