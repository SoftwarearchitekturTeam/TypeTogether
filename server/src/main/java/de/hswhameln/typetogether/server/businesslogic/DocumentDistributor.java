package de.hswhameln.typetogether.server.businesslogic;

import de.hswhameln.typetogether.networking.LocalDocument;
import de.hswhameln.typetogether.networking.api.Document;
import de.hswhameln.typetogether.networking.api.User;
import de.hswhameln.typetogether.networking.types.DocumentCharacter;
import de.hswhameln.typetogether.networking.util.LoggerFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.logging.Logger;

public class DocumentDistributor implements Document {

    private final Logger logger = LoggerFactory.getLogger(this);
    private final String id;

    private final Map<Integer, User> activeUsersByIds = new HashMap<>();

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
    public void addChars(User author, Collection<DocumentCharacter> characters) {
        this.serverBackup.addChars(author, characters);
        propagate(author, document -> document.addChars(author, characters));
    }

    @Override
    public void removeChars(User author, Collection<DocumentCharacter> characters) {
        this.serverBackup.removeChars(author, characters);
        propagate(author, document -> document.removeChars(author, characters));
    }

    @Override
    public void close(User source) {
        propagate(source, document -> document.close(source), true);
    }

    @Override
    public String getFuncId() {
        return id;
    }

    public void addUser(User user) {
        this.logger.fine("User " + user.getName() + " joined document " + id + ". Sending content of length " + this.serverBackup.getContent().size() + ".");
        this.activeUsersByIds.put(user.getId(), user);
        Document document = user.getDocument();
        document.addChars(this.dummyUser, this.serverBackup.getContent());
    }

    public void removeUser(User user) {
        this.removeUserById(user.getId());
    }

    public void removeUserById(int id) {
        this.activeUsersByIds.remove(id);
    }

    public boolean isUserParticipant(User user) {
        return activeUsersByIds.containsKey(user.getId());
    }

    private void propagate(User author, Consumer<Document> clientDocumentConsumer) {
        propagate(author, clientDocumentConsumer, false);
    }

    private void propagate(User author, Consumer<Document> clientDocumentConsumer, boolean copy) {
        int authorId = author.getId();
        Collection<Map.Entry<Integer, User>> usersToStream = copy ? new HashSet<>(this.activeUsersByIds.entrySet()) : this.activeUsersByIds.entrySet();
        usersToStream.stream()
                .filter(userEntry -> userEntry.getKey() != authorId)
                .map(Map.Entry::getValue)
                .map(User::getDocument)
                .forEach(clientDocumentConsumer);
    }


}
