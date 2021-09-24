package de.hswhameln.typetogether.networking.shared;

import de.hswhameln.typetogether.networking.api.Document;
import de.hswhameln.typetogether.networking.api.User;
import de.hswhameln.typetogether.networking.proxy.MarshallHandler;
import de.hswhameln.typetogether.networking.shared.AbstractServerProxy;
import de.hswhameln.typetogether.networking.shared.DocumentServerProxy;
import de.hswhameln.typetogether.networking.shared.ServerProxyAction;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;

public class UserServerProxy extends AbstractServerProxy {

    private final User user;
    private final MarshallHandler<Document> documentMarshallHandler;

    public UserServerProxy(Socket socket, User user) {
        super(socket);
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

    private void doGetId() {
        this.safelySendResult("getId", this.user::getId);
    }

    private void doGetName() {
        this.safelySendResult("getName", this.user::getName);
    }

    private void doGetDocument() throws IOException {
        this.documentMarshallHandler.marshall(this.user.getDocument());
    }
}
