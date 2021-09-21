package de.hswhameln.typetogether.networking.api;

import de.hswhameln.typetogether.networking.types.DocumentCharacter;

public interface Document {
    void writeChar(User author, DocumentCharacter character);
    void deleteChar(User author, DocumentCharacter character);
}
