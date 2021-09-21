package de.hswhameln.typetogether.server.proxy;

import de.hswhameln.typetogether.networking.shared.AbstractServerProxy;
import de.hswhameln.typetogether.networking.shared.ServerProxyAction;

import java.net.Socket;
import java.util.Map;

public class LobbyServerProxy extends AbstractServerProxy {

    public LobbyServerProxy(Socket socket) {
        super(socket);
    }

    @Override
    protected Map<String, ServerProxyAction> createAvailableActions() {
        return Map.ofEntries(
                Map.entry("1", ServerProxyAction.of("joinDocument", this::doJoinDocument)),
                Map.entry("2", ServerProxyAction.of("leaveDocument", this::doLeaveDocument)));
    }

    public void doJoinDocument() {

    }

    public void doLeaveDocument() {

    }
}
