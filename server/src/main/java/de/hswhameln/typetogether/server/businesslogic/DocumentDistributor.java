package de.hswhameln.typetogether.server.businesslogic;

import de.hswhameln.typetogether.networking.api.Document;
import de.hswhameln.typetogether.networking.api.User;
import de.hswhameln.typetogether.networking.types.DocumentCharacter;

import java.util.HashSet;
import java.util.Set;

public class DocumentDistributor implements Document {

    private final String id;

    private final Set<Document> localDocuments = new HashSet<>();

    public DocumentDistributor(String id) {
        this.id = id;
    }

    @Override
    public void addChar(User author, DocumentCharacter character) {
        this.localDocuments.stream()
                .filter(document -> document != author.getDocument())
                .forEach(document -> document.addChar(author, character));
    }

    @Override
    public void removeChar(User author, DocumentCharacter character) {
        this.localDocuments.stream()
                .filter(document -> document != author.getDocument())
                .forEach(document -> document.removeChar(author, character));

    }

    @Override
    public String getFuncId() {
        return id;
    }

    public void addLocalDocument(Document localDocument) {
        this.localDocuments.add(localDocument);
    }

    public void removeLocalDocument(Document localDocument) {
        this.localDocuments.remove(localDocument);
    }
}
