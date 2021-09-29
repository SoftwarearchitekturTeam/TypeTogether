package de.hswhameln.typetogether.networking;

import java.util.function.ObjIntConsumer;

public class DocumentObserver {
    
    private final ObjIntConsumer<Character> addChar;
    private final ObjIntConsumer<Character> removeChar;

    public DocumentObserver(ObjIntConsumer<Character> addChar, ObjIntConsumer<Character> removeChar) {
        this.addChar = addChar;
        this.removeChar = removeChar;
    }

    public ObjIntConsumer<Character> getAddCharConsumer() {
        return this.addChar;
    }

    public ObjIntConsumer<Character> getRemoveCharConsumer() {
        return this.removeChar;
    }
}
