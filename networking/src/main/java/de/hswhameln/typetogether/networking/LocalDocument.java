package de.hswhameln.typetogether.networking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import de.hswhameln.typetogether.networking.api.Document;
import de.hswhameln.typetogether.networking.api.User;
import de.hswhameln.typetogether.networking.types.DocumentCharacter;
import de.hswhameln.typetogether.networking.types.Identifier;
import de.hswhameln.typetogether.networking.util.Decimal;

public class LocalDocument implements Document {

    private final String funcId;

    private final List<DocumentCharacter> content;
    private final Set<DocumentObserver> observers;

    public LocalDocument() {
        this.content = new ArrayList<>();
        this.content.add(new DocumentCharacter('#', new Identifier(0, 0)));
        System.out.println("STARTING LocalDocument: " + this.content.stream().map(DocumentCharacter::getStringRepresentation).collect(Collectors.joining("/")));

        this.observers = new HashSet<>();
        this.funcId = UUID.randomUUID().toString();
    }

    @Override
    public void addChar(User author, DocumentCharacter character) {
        System.out.println("[LocalDocument] AddingChar: " + character.getValue());
        this.addLocalChar(character);
        int offset = this.content.indexOf(character);
        System.out.println("Index of character " + character.getStringRepresentation() + " is " + offset);
        this.observers.stream().map(DocumentObserver::getAddCharConsumer).forEach(c -> c.accept(character.getValue(), offset));
    }

    @Override
    public void removeChar(User author, DocumentCharacter character) {
        int offset = this.content.indexOf(character);
        this.removeLocalChar(character);
        this.observers.stream().map(DocumentObserver::getRemoveCharConsumer).forEach(c -> c.accept(character.getValue(), offset));
    }

    @Override
    public String getFuncId() {
        return this.funcId;
    }

    public DocumentCharacter getDocumentCharacterOfIndex(int index) {
        System.out.println("LocalDocument#getDocumentCharacterOfIndex for index " + index + " at length " +  this.content.size() );
        if (index < 0 || index >= content.size()) {
            return null;
        }
        return this.content.get(index);
    }

    public void addLocalChar(DocumentCharacter character) {
        System.out.println("[LocalDocument#addLocalChar] Adding LocalChar: " + character.getValue());

        this.content.add(character);
        Collections.sort(this.content);
        System.out.println("ADDED LocalDocument: " + this.content.stream().map(DocumentCharacter::getStringRepresentation).collect(Collectors.joining("/")));
    }

    public void removeLocalChar(DocumentCharacter character) {
        System.out.println("[LocalDocument#removeLocalChar] Removing LocalChar: " + character.getValue());

        this.content.remove(character);
        System.out.println("REMOVED LocalDocument: " + this.content.stream().map(DocumentCharacter::getStringRepresentation).collect(Collectors.joining("/")));
    }

    public void addObserver(DocumentObserver observer) {
        this.observers.add(observer);
    }

    public List<DocumentCharacter> getContent() {
        return Decimal.rest(content);
    }

    // get adjacent DocumentCharacters (x2) (maybe)


}