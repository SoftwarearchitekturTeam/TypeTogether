package de.hswhameln.typetogether.server.businesslogic;

import de.hswhameln.typetogether.networking.LocalDocument;
import de.hswhameln.typetogether.networking.api.Document;
import de.hswhameln.typetogether.networking.api.User;
import de.hswhameln.typetogether.networking.types.DocumentCharacter;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.logging.Logger;

public class DocumentDistributor implements Document {

    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private final String id;

    private final Set<User> activeUsers = new HashSet<>();

    private final LocalDocument serverBackup;

    // only the actual content is stored as a backup, so the server itself takes the author role when a backup is requested.
    private final User dummyUser = new User() {
        @Override
        public int getId() {
            return 0;
        }

        @Override
        public String getName() {
            return "[SERVER]";
        }

        @Override
        public Document getDocument() {
            return serverBackup;
        }
    };

    public DocumentDistributor(String id) {
        this.serverBackup = new LocalDocument();
        this.id = id;
    }

    @Override
    public void addChar(User author, DocumentCharacter character) {
        this.serverBackup.addChar(author, character);
        propagate(author, document -> document.addChar(author, character));
    }

    @Override
    public void removeChar(User author, DocumentCharacter character) {
        this.serverBackup.removeChar(author, character);
        propagate(author, document -> document.removeChar(author, character));
    }

    @Override
    public void close(User source) {
        propagate(source, document -> document.close(source));
    }

    @Override
    public String getFuncId() {
        return id;
    }

    public void addUser(User user) {
        this.logger.fine("User " + user.getName() + " joined document " + id + ". Sending content of length " + this.serverBackup.getContent().size() + ".");
        this.activeUsers.add(user);
        Document document = user.getDocument();
        this.serverBackup.getContent().forEach(documentCharacter -> document.addChar(this.dummyUser, documentCharacter));
    }

    public void removeUser(User user) {
        this.activeUsers.remove(user);
    }

    public boolean isUserParticipant(User user) {
        return activeUsers.contains(user);
    }

    private void propagate(User author, Consumer<Document> clientDocumentConsumer) {
        int authorId = author.getId();
        this.activeUsers.stream()
                .filter(user -> user.getId() != authorId)
                .map(User::getDocument)
                .forEach(clientDocumentConsumer);
    }

}
