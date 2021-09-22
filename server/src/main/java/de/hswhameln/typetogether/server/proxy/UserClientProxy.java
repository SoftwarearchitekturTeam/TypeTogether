package de.hswhameln.typetogether.server.proxy;

import de.hswhameln.typetogether.networking.api.Document;
import de.hswhameln.typetogether.networking.api.User;
import de.hswhameln.typetogether.networking.proxy.ObjectResolver;
import de.hswhameln.typetogether.networking.shared.AbstractClientProxy;
import de.hswhameln.typetogether.networking.shared.DocumentClientProxy;
import de.hswhameln.typetogether.networking.shared.helperinterfaces.Action;
import de.hswhameln.typetogether.networking.shared.helperinterfaces.UnsafeSupplier;
import de.hswhameln.typetogether.networking.util.IOUtils;

import java.io.IOException;
import java.net.Socket;

public class UserClientProxy extends AbstractClientProxy implements User {

    private final ObjectResolver<Document> localDocumentResolver;

    public UserClientProxy(Socket socket) {
        super(socket);
        this.localDocumentResolver = new ObjectResolver<>(DocumentClientProxy::new, this.in, this.out, this.socket.getInetAddress());
    }

    @Override
    public int getId() {
        return safelyExecute(() -> {
            this.chooseOption("1");
            IOUtils.expectResponseCodeSuccess(this.in);
            int id = Integer.parseInt(this.in.readLine());
            logger.fine("getId returned " + id);
            return id;
        });
    }

    @Override
    public String getName() {
        return safelyExecute(() -> {
            this.chooseOption("2");
            String name = this.in.readLine();
            logger.fine("getName returned " + name);
            return name;
        });
    }

    @Override
    public Document getDocument() {
        return safelyExecute(() -> {
            this.chooseOption("3");
            return this.localDocumentResolver.resolveObject();
        });
    }

}
