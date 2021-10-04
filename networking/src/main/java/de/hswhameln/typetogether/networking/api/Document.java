package de.hswhameln.typetogether.networking.api;

import de.hswhameln.typetogether.networking.types.DocumentCharacter;

import java.util.Collection;

public interface Document {
    void addChar(User author, DocumentCharacter character);
    void removeChar(User author, DocumentCharacter character);
    void close(User source);
    String getFuncId();

    default void addChars(User author, Collection<DocumentCharacter> characters) {
        characters.forEach(c -> this.addChar(author, c));
    }

    default void removeChars(User author, Collection<DocumentCharacter> characters) {
        characters.forEach(c -> this.removeChar(author, c));
    }
}
