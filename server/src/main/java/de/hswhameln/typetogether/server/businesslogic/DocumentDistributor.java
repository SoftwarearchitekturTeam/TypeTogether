package de.hswhameln.typetogether.server.businesslogic;

import de.hswhameln.typetogether.networking.api.Document;
import de.hswhameln.typetogether.networking.api.User;
import de.hswhameln.typetogether.networking.types.DocumentCharacter;

import java.util.HashSet;
import java.util.Set;

public class DocumentDistributor implements Document {

    private final String id;

    private final Set<User> activeUsers = new HashSet<>();

    public DocumentDistributor(String id) {
        this.id = id;
    }

    @Override
    public void addChar(User author, DocumentCharacter character) {
        int authorId = author.getId();
        this.activeUsers.stream()
                .filter(user -> user.getId() != authorId)
                .map(User::getDocument)
                .forEach(document -> document.addChar(author, character));
    }

    @Override
    public void removeChar(User author, DocumentCharacter character) {
        int authorId = author.getId();
        this.activeUsers.stream()
                .filter(user -> user.getId() != authorId)
                .map(User::getDocument)
                .forEach(document -> document.removeChar(author, character));
    }

    @Override
    public String getFuncId() {
        return id;
    }

    public void addUser(User user) {
        this.activeUsers.add(user);
    }

    public void removeUser(User user) {
        this.activeUsers.remove(user);
    }

    public boolean isUserParticipant(User user) {
        return activeUsers.contains(user);
    }
}
