package de.hswhameln.typetogether.networking.shared;

import de.hswhameln.typetogether.networking.api.Document;
import de.hswhameln.typetogether.networking.api.User;
import de.hswhameln.typetogether.networking.proxy.ObjectResolver;
import de.hswhameln.typetogether.networking.types.DocumentCharacter;
import de.hswhameln.typetogether.networking.util.IOUtils;

import java.io.IOException;
import java.net.Socket;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

public class DocumentServerProxy extends AbstractServerProxy {

    private final ObjectResolver<User> objectResolver;
    private final Document underlyingDocument;

    public DocumentServerProxy(Socket socket, Document underlyingDocument) throws IOException {
        super(socket);
        if (underlyingDocument == null) {
            throw new IllegalArgumentException("UnderLyingDocument must not be null.");
        }
        this.underlyingDocument = underlyingDocument;
        this.objectResolver = new ObjectResolver<>(UserClientProxy::new, this.in, this.out, this.socket.getInetAddress());
    }

    @Override
    protected Map<String, ServerProxyAction> createAvailableActions() {
        return Map.ofEntries(
                Map.entry("0", this.closeConnectionAction),
                Map.entry("1", ServerProxyAction.of("addChar", this::doAddChar)),
                Map.entry("2", ServerProxyAction.of("removeChar", this::doRemoveChar)),
                Map.entry("3", ServerProxyAction.of("getFuncId", this::doGetFuncId)),
                Map.entry("4", ServerProxyAction.of("close", this::doClose)),
                Map.entry("5", ServerProxyAction.of("addChars", this::doAddChars)),
                Map.entry("6", ServerProxyAction.of("removeChars", this::doRemoveChars))
        );
    }

    private void doAddChar() throws IOException {
        this.safelyExecute("addChar", () -> {
            User author = this.objectResolver.resolveObject();
            DocumentCharacter character = IOUtils.getDocumentCharacterArgument("char to add", this.in, this.out);
            this.underlyingDocument.addChar(author, character);
        });
    }

    private void doRemoveChar() throws IOException {
        this.safelyExecute("removeChar", () -> {
            User author = this.objectResolver.resolveObject();
            DocumentCharacter character = IOUtils.getDocumentCharacterArgument("char to remove", this.in, this.out);
            this.underlyingDocument.removeChar(author, character);
        });
    }

    private void doGetFuncId() throws IOException {
        this.safelySendResult("getFuncId", this.underlyingDocument::getFuncId);
    }

    private void doClose() throws IOException {
        this.safelyExecute("close", () -> {
            User source = this.objectResolver.resolveObject();
            this.underlyingDocument.close(source);
        });
    }

    private void doAddChars() throws IOException {
        this.safelyExecute("addChars", () -> {
            User author = this.objectResolver.resolveObject();
            int size = IOUtils.getIntArgument("character count", this.in, this.out);
            Collection<DocumentCharacter> characters = new LinkedList<>();
            for (int i = 0; i < size; i++) {
                characters.add(DocumentCharacter.parse(this.in.readLine()));
            }
            this.underlyingDocument.addChars(author, characters);
        });
    }

    private void doRemoveChars() throws IOException {
        this.safelyExecute("removeChars", () -> {
            User author = this.objectResolver.resolveObject();
            int size = IOUtils.getIntArgument("character count", this.in, this.out);
            Collection<DocumentCharacter> characters = new LinkedList<>();
            for (int i = 0; i < size; i++) {
                characters.add(DocumentCharacter.parse(this.in.readLine()));
            }
            this.underlyingDocument.removeChars(author, characters);
        });
    }
}
