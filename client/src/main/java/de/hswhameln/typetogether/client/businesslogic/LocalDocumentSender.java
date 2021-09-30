package de.hswhameln.typetogether.client.businesslogic;

import de.hswhameln.typetogether.networking.api.Document;
import de.hswhameln.typetogether.networking.api.User;
import de.hswhameln.typetogether.networking.types.DocumentCharacter;

public class LocalDocumentSender {

    private Document serverDocument;
    private User author;

    public LocalDocumentSender(Document serverDocument, User author) {
        this.serverDocument = serverDocument;
        this.author = author;
    }

    public void addChar(DocumentCharacter characterToAdd) {
        this.serverDocument.addChar(this.author, characterToAdd);
    }
  
    public void removeChar(DocumentCharacter charToRemove) {
        this.serverDocument.removeChar(author, charToRemove);
    }
    
}