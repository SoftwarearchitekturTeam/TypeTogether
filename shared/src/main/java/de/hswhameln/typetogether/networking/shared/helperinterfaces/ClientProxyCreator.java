package de.hswhameln.typetogether.networking.shared.helperinterfaces;

import java.io.IOException;
import java.net.Socket;

@FunctionalInterface
public interface ClientProxyCreator<T> {
    T create(Socket socket) throws IOException;
}
