package de.hswhameln.typetogether.networking.api;

import de.hswhameln.typetogether.networking.types.DocumentCharacter;

public interface Document {
    void addChar(User author, DocumentCharacter character);
    void removeChar(User author, DocumentCharacter character);
    void close(User source);
    String getFuncId();
}
