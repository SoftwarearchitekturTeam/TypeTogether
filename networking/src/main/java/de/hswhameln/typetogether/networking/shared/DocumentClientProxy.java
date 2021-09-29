package de.hswhameln.typetogether.networking.shared;

import java.io.IOException;
import java.net.Socket;

import de.hswhameln.typetogether.networking.api.Document;
import de.hswhameln.typetogether.networking.api.User;
import de.hswhameln.typetogether.networking.proxy.MarshallHandler;
import de.hswhameln.typetogether.networking.types.DocumentCharacter;
import de.hswhameln.typetogether.networking.util.IOUtils;

public class DocumentClientProxy extends AbstractClientProxy implements Document {

    private final MarshallHandler<User> userMarshallHandler;

    public DocumentClientProxy(Socket socket) throws IOException {
        super(socket);
        this.userMarshallHandler = new MarshallHandler<>(UserServerProxy::new, this.in, this.out);
    }

    @Override
    public void addChar(User author, DocumentCharacter character) {
        this.safelyExecute(() -> {
            this.chooseOption("1");
            this.userMarshallHandler.marshall(author);
            // "provide a char to add"
            this.logger.fine("[Server] " + this.in.readLine());
            this.out.println(character.getStringRepresentation());

            IOUtils.expectResponseCodeSuccess(this.in);
        });
    }

    @Override
    public void removeChar(User author, DocumentCharacter character) {
        this.safelyExecute(() -> {
            this.chooseOption("2");
            this.userMarshallHandler.marshall(author);
            // "provide a char to remove"
            this.logger.fine("[Server] " + this.in.readLine());
            this.out.println(character.getStringRepresentation());

            IOUtils.expectResponseCodeSuccess(this.in);
        });
    }

    @Override
    public String getFuncId() {
        return this.safelyExecute(() -> {
            this.chooseOption("3");
            IOUtils.expectResponseCodeSuccess(this.in);
            String funcId = this.in.readLine();
            logger.fine("getFuncId returned " + funcId);
            return funcId;
        });
    }
}
