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
import de.hswhameln.typetogether.networking.util.LoggerFactory;

import java.io.IOException;
import java.net.Socket;
import java.util.Collection;
import java.util.Map;
import java.util.logging.Logger;

public class LobbyServerProxy extends AbstractServerProxy {

    private final Logger logger = LoggerFactory.getLogger(this);

    private final ObjectResolver<User> userObjectResolver;
    private final MarshallHandler<Document> documentMarshallHandler;

    private final Lobby lobby;

    public LobbyServerProxy(Socket socket, Lobby lobby) throws IOException {
        super(socket);
        if (lobby == null) {
            throw new IllegalArgumentException("UnderlyingLobby must not be null");
        }
        this.lobby = lobby;
        this.userObjectResolver = new ObjectResolver<>(UserClientProxy::new, this.in, this.out, this.socket.getInetAddress());
        this.documentMarshallHandler = new MarshallHandler<>(DocumentServerProxy::new, this.in, this.out);
    }

    @Override
    protected Map<String, ServerProxyAction> createAvailableActions() {
        return Map.ofEntries(
                Map.entry("0", this.closeConnectionAction),
                Map.entry("1", ServerProxyAction.of("joinDocument", this::doJoinDocument)),
                Map.entry("2", ServerProxyAction.of("leaveDocument", this::doLeaveDocument)),
                Map.entry("3", ServerProxyAction.of("getDocumentById", this::doGetDocumentById)),
                Map.entry("4", ServerProxyAction.of("createDocument", this::doCreateDocument)),
                Map.entry("5", ServerProxyAction.of("deleteDocument", this::doDeleteDocument)),
                Map.entry("6", ServerProxyAction.of("getDocuments", this::doGetDocumentIds)));
    }


    private void doJoinDocument() throws IOException {
        User user = this.resolveUser();
        String documentId = IOUtils.getStringArgument("documentId", this.in, this.out);
        this.safelyExecute("joinDocument", () -> this.lobby.joinDocument(user, documentId));
    }

    private void doLeaveDocument() throws IOException {
        User user = this.resolveUser();
        String documentId = IOUtils.getStringArgument("documentId", this.in, this.out);
        this.safelyExecute("leaveDocument", () -> this.lobby.leaveDocument(user, documentId));
    }

    private User resolveUser() throws IOException {
        this.logger.fine("Trying to resolve user...");
        return this.userObjectResolver.resolveObject();
    }

    private void doGetDocumentById() throws IOException {
        String documentId = IOUtils.getStringArgument("documentId", this.in, this.out);
        this.safelySendResult("getDocumentById", () -> this.lobby.getDocumentById(documentId), this.documentMarshallHandler::marshall);
    }

    private void doCreateDocument() throws IOException {
        String documentId = IOUtils.getStringArgument("documentId", this.in, this.out);
        this.safelyExecute("createDocument", () -> this.lobby.createDocument(documentId));
    }

    private void doDeleteDocument() throws IOException {
        User user = this.resolveUser();
        String documentId = IOUtils.getStringArgument("documentId", this.in, this.out);
        this.safelyExecute("deleteDocument", () -> this.lobby.deleteDocument(user, documentId));
    }

    private void doGetDocumentIds() throws IOException {
        this.safelyExecute("getDocumentIds", () -> {
            Collection<String> documentIds = lobby.getDocumentIds();
            this.out.println("Sending N followed by N documentIds...");
            this.out.println(documentIds.size());
            for (String s: documentIds) {
                this.out.println(s);
            }
        });
    }
}
