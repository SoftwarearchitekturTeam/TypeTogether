package de.hswhameln.typetogether.server.proxy;

import de.hswhameln.typetogether.networking.api.Lobby;
import de.hswhameln.typetogether.networking.api.User;
import de.hswhameln.typetogether.networking.shared.AbstractServerProxy;
import de.hswhameln.typetogether.networking.shared.ServerProxyAction;
import de.hswhameln.typetogether.networking.util.IOUtils;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Logger;

public class LobbyServerProxy extends AbstractServerProxy {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    // TODO maybe UserClientProxy instead
    private Map<Integer, User> usersByCommunicationId = new HashMap<>();

    private Lobby lobby;

    public LobbyServerProxy(Socket socket, Lobby lobby) {
        super(socket);
        this.lobby = lobby;
    }

    @Override
    protected Map<String, ServerProxyAction> createAvailableActions() {
        return Map.ofEntries(
                Map.entry("0", this.closeConnectionAction),
                Map.entry("1", ServerProxyAction.of("joinDocument", this::doJoinDocument)),
                Map.entry("2", ServerProxyAction.of("leaveDocument", this::doLeaveDocument)));
    }

    public void doJoinDocument() throws IOException {
        User user = this.resolveUser();
        String documentId = IOUtils.getStringArgument("documentId", this.in, this.out);
        this.safelyExecute("joinDocument", () -> this.lobby.joinDocument(user, documentId));
    }

    public void doLeaveDocument() {
        // TODO
    }

    private User resolveUser() throws IOException {
        return this.resolveInputObject("User", this.usersByCommunicationId, UserClientProxy::new);
    }
}
