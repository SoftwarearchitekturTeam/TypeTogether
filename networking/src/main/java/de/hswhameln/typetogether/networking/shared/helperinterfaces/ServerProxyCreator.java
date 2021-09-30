package de.hswhameln.typetogether.networking.shared.helperinterfaces;


import de.hswhameln.typetogether.networking.shared.AbstractServerProxy;

import java.io.IOException;
import java.net.Socket;

@FunctionalInterface
public interface ServerProxyCreator<T> {
    AbstractServerProxy create(Socket socket, T t) throws IOException;
}
