package de.hswhameln.typetogether.networking;

import de.hswhameln.typetogether.networking.api.User;

import java.util.function.Consumer;
import java.util.function.ObjIntConsumer;

public class DocumentObserver {
    
    private final ObjIntConsumer<Character> addChar;
    private final ObjIntConsumer<Character> removeChar;
    private final Consumer<User> closeDocument;

    public DocumentObserver(ObjIntConsumer<Character> addChar, ObjIntConsumer<Character> removeChar, Consumer<User> closeDocument) {
        this.addChar = addChar;
        this.removeChar = removeChar;
        this.closeDocument = closeDocument;
    }

    public ObjIntConsumer<Character> getAddCharConsumer() {
        return this.addChar;
    }

    public ObjIntConsumer<Character> getRemoveCharConsumer() {
        return this.removeChar;
    }

    public Consumer<User> getCloseDocument() {
        return closeDocument;
    }
}
