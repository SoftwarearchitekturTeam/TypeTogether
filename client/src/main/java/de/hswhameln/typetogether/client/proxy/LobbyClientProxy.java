package de.hswhameln.typetogether.client.proxy;

import de.hswhameln.typetogether.networking.api.Document;
import de.hswhameln.typetogether.networking.api.Lobby;
import de.hswhameln.typetogether.networking.api.User;
import de.hswhameln.typetogether.networking.proxy.MarshallHandler;
import de.hswhameln.typetogether.networking.proxy.ObjectResolver;
import de.hswhameln.typetogether.networking.shared.AbstractClientProxy;
import de.hswhameln.typetogether.networking.shared.DocumentClientProxy;
import de.hswhameln.typetogether.networking.shared.UserServerProxy;
import de.hswhameln.typetogether.networking.util.IOUtils;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

public class LobbyClientProxy extends AbstractClientProxy implements Lobby {

    private final MarshallHandler<User> userMarshallHandler;
    private final ObjectResolver<Document> documentObjectResolver;
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public LobbyClientProxy(Socket socket) throws IOException {
        super(socket);
        this.userMarshallHandler = new MarshallHandler<>(UserServerProxy::new, this.in, this.out);
        this.documentObjectResolver = new ObjectResolver<>(DocumentClientProxy::new, this.in, this.out, this.socket.getInetAddress());
    }

    @Override
    public void joinDocument(User user, String documentId) {
        this.safelyExecute(() -> {
            this.chooseOption("1");
            this.userMarshallHandler.marshall(user);

            // "Provide documentId"
            logger.fine(this.in.readLine());
            this.out.println(documentId);

            IOUtils.expectResponseCodeSuccess(this.in);
        });
    }

    @Override
    public void leaveDocument(User user, String documentId) {
        this.safelyExecute(() -> {
            this.chooseOption("2");
            this.userMarshallHandler.marshall(user);

            // "Provide documentId"
            logger.fine(this.in.readLine());
            this.out.println(documentId);

            IOUtils.expectResponseCodeSuccess(this.in);
        });
    }

    @Override
    public Document getDocumentById(String documentId) {
        return this.safelyExecute(() -> {
            this.chooseOption("3");
            // "Provide documentId"
            logger.fine(this.in.readLine());
            this.out.println(documentId);
            IOUtils.expectResponseCodeSuccess(this.in);
            return this.documentObjectResolver.resolveObject();
        });
    }

    @Override
    public void createDocument(String documentId) {
        this.safelyExecute(() -> {
            this.chooseOption("4");
            // "Provide documentId"
            logger.fine(this.in.readLine());
            this.out.println(documentId);
            IOUtils.expectResponseCodeSuccess(this.in);
        });
    }
}
