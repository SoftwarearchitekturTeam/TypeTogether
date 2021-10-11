package de.hswhameln.typetogether.networking.shared;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;

import de.hswhameln.typetogether.networking.api.Document;
import de.hswhameln.typetogether.networking.api.User;
import de.hswhameln.typetogether.networking.proxy.MarshallHandler;

public class UserServerProxy extends AbstractServerProxy {

    private final User user;
    private final MarshallHandler<Document> documentMarshallHandler;

    public UserServerProxy(Socket socket, User user) throws IOException {
        super(socket);
        if (user == null) {
            throw new IllegalArgumentException("UnderlyingUser must not be null.");
        }
        this.user = user;
        this.documentMarshallHandler = new MarshallHandler<>(DocumentServerProxy::new, this.in, this.out);
    }

    @Override
    protected Map<String, ServerProxyAction> createAvailableActions() {
        return Map.ofEntries(
                Map.entry("0", this.closeConnectionAction),
                Map.entry("1", ServerProxyAction.of("getId", this::doGetId)),
                Map.entry("2", ServerProxyAction.of("getName", this::doGetName)),
                Map.entry("3", ServerProxyAction.of("getDocument", this::doGetDocument))
        );
    }

    private void doGetId() throws IOException {
        this.safelySendResult("getId", this.user::getId);
    }

    private void doGetName() throws IOException {
        this.safelySendResult("getName", this.user::getName);
    }

    private void doGetDocument() throws IOException {
        this.documentMarshallHandler.marshall(this.user.getDocument());
    }
}
