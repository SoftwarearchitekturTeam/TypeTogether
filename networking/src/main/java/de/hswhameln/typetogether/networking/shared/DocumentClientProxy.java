package de.hswhameln.typetogether.networking.shared;

import de.hswhameln.typetogether.networking.api.Document;
import de.hswhameln.typetogether.networking.api.User;
import de.hswhameln.typetogether.networking.proxy.MarshallHandler;
import de.hswhameln.typetogether.networking.types.DocumentCharacter;

import java.io.IOException;
import java.net.Socket;
import java.util.Collection;
import java.util.Objects;

import static de.hswhameln.typetogether.networking.FluentExceptionHandler.expectSuccess;

public class DocumentClientProxy extends AbstractClientProxy implements Document {

    private final MarshallHandler<User> userMarshallHandler;

    public DocumentClientProxy(Socket socket) throws IOException {
        super(socket);
        this.userMarshallHandler = new MarshallHandler<>(UserServerProxy::new, this.in, this.out);
    }

    @Override
    public void addChar(User author, DocumentCharacter character) {
        Objects.requireNonNull(author);
        Objects.requireNonNull(character);

        this.safelyExecute(() -> {
            this.chooseOption("1");
            this.userMarshallHandler.marshall(author);
            // "provide a char to add"
            this.logger.fine("[Server] " + this.in.readLine());
            this.out.println(character.getStringRepresentation());

            expectSuccess(this.in);
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
            expectSuccess(this.in);
        });
    }

    @Override
    public String getFuncId() {
        return this.safelyExecute(() -> {
            this.chooseOption("3");
            expectSuccess(this.in);
            String funcId = this.in.readLine();
            logger.fine("getFuncId returned " + funcId);
            return funcId;
        });
    }

    @Override
    public void close(User source) {
        this.safelyExecute(() -> {
            this.chooseOption("4");
            this.userMarshallHandler.marshall(source);
            expectSuccess(this.in);
        });
    }

    @Override
    public void addChars(User author, Collection<DocumentCharacter> characters) {
        this.safelyExecute(() -> {
            this.chooseOption("5");
            this.userMarshallHandler.marshall(author);

            // "provide the character collection size N, followed by N lines of DocumentCharacters"
            this.logger.fine("[Server] " + this.in.readLine());
            this.out.println(characters.size());

            for (DocumentCharacter c: characters) {
                this.out.println(c.getStringRepresentation());
            }

            expectSuccess(this.in);
        });
    }

    @Override
    public void removeChars(User author, Collection<DocumentCharacter> characters) {
        this.safelyExecute(() -> {
            this.chooseOption("6");
            this.userMarshallHandler.marshall(author);

            // "provide the character collection size N, followed by N lines of DocumentCharacters"
            this.logger.fine("[Server] " + this.in.readLine());
            this.out.println(characters.size());

            for (DocumentCharacter c: characters) {
                this.out.println(c.getStringRepresentation());
            }

            expectSuccess(this.in);
        });
    }
}
