package de.hswhameln.typetogether.server.proxy;

import de.hswhameln.typetogether.networking.api.Document;
import de.hswhameln.typetogether.networking.api.Lobby;
import de.hswhameln.typetogether.networking.api.User;
import de.hswhameln.typetogether.networking.proxy.MarshallHandler;
import de.hswhameln.typetogether.networking.proxy.ObjectResolver;
import de.hswhameln.typetogether.networking.shared.AbstractServerProxy;
import de.hswhameln.typetogether.networking.shared.DocumentServerProxy;
import de.hswhameln.typetogether.networking.shared.ServerProxyAction;
import de.hswhameln.typetogether.networking.shared.UserClientProxy;
import de.hswhameln.typetogether.networking.util.IOUtils;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;
import java.util.logging.Logger;

public class LobbyServerProxy extends AbstractServerProxy {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private final ObjectResolver<User> userObjectResolver;
    private final MarshallHandler<Document> documentMarshallHandler;

    private final Lobby lobby;

    public LobbyServerProxy(Socket socket, Lobby lobby) {
        super(socket);
        this.lobby = lobby;
        this.userObjectResolver = new ObjectResolver<>(UserClientProxy::new, this.in, this.out, this.socket.getInetAddress());
        this.documentMarshallHandler = new MarshallHandler<>(DocumentServerProxy::new, this.in, this.out);
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
        this.safelySendResult("joinDocument", () -> this.lobby.joinDocument(user, documentId), this.documentMarshallHandler::marshall);
    }

    public void doLeaveDocument() throws IOException {
        User user = this.resolveUser();
        String documentId = IOUtils.getStringArgument("documentId", this.in, this.out);
        this.safelyExecute("joinDocument", () -> this.lobby.leaveDocument(user, documentId));
    }

    private User resolveUser() throws IOException {
        return this.userObjectResolver.resolveObject();
    }
}
