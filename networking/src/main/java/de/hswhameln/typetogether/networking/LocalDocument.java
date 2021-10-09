package de.hswhameln.typetogether.networking;

import de.hswhameln.typetogether.networking.api.Document;
import de.hswhameln.typetogether.networking.api.User;
import de.hswhameln.typetogether.networking.types.DocumentCharacter;
import de.hswhameln.typetogether.networking.types.Identifier;
import de.hswhameln.typetogether.networking.util.Decimal;
import de.hswhameln.typetogether.networking.util.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class LocalDocument implements Document {

    private static final Logger logger = LoggerFactory.getLogger(LocalDocument.class);
    private final String funcId;

    private final List<DocumentCharacter> content;
    private final Set<DocumentObserver> observers;

    public LocalDocument() {
        this.content = new ArrayList<>();
        this.content.add(new DocumentCharacter('#', List.of(new Identifier(0, 0))));
        this.observers = new HashSet<>();
        this.funcId = UUID.randomUUID().toString();
    }

    @Override
    public void addChar(User author, DocumentCharacter character) {
        logger.log(Level.INFO, "Received character '" + character.getValue() + "' from server.");
        this.addLocalChar(character);
        int offset = this.content.indexOf(character);
        this.observers.stream().map(DocumentObserver::getAddCharConsumer).forEach(c -> c.accept(character.getValue(), offset));
    }

    @Override
    public void removeChar(User author, DocumentCharacter character) {
        int offset = this.content.indexOf(character);
        this.removeLocalChar(character);
        this.observers.stream().map(DocumentObserver::getRemoveCharConsumer).forEach(c -> c.accept(character.getValue(), offset));
    }

    @Override
    public void close(User source) {
        // create a copy to allow observer modification in the closeDocument listener
        Set<DocumentObserver> observers = new HashSet<>(this.observers);
        observers.stream().map(DocumentObserver::getCloseDocument).forEach(c -> c.accept(source));
    }

    @Override
    public String getFuncId() {
        return this.funcId;
    }

    public DocumentCharacter getDocumentCharacterOfIndex(int index) {
        if (index < 0 || index >= content.size()) {
            return null;
        }
        return this.content.get(index);
    }

    public void addLocalChar(DocumentCharacter character) {
        this.content.add(character);
        Collections.sort(this.content);
        logger.log(Level.FINE, "Adding character " + character + " to the local document. The full document looks like this now:\n" + this.content.stream().map(DocumentCharacter::getStringRepresentation).collect(Collectors.joining("   ")));
    }

    public void removeLocalChar(DocumentCharacter character) {
        this.content.remove(character);
        logger.log(Level.FINE, "Removed character " + character + " from the local document. The full document looks like this now:\n" + this.content.stream().map(DocumentCharacter::getStringRepresentation).collect(Collectors.joining("   ")));
    }

    public void addObserver(DocumentObserver observer) {
        this.observers.add(observer);
    }

    public List<DocumentCharacter> getContent() {
        return Decimal.rest(content);
    }

    public void removeObserver(DocumentObserver observer) {
        this.observers.remove(observer);
    }

    // get adjacent DocumentCharacters (x2) (maybe)


}