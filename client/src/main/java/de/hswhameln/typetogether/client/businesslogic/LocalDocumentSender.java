package de.hswhameln.typetogether.client.businesslogic;

import de.hswhameln.typetogether.networking.api.Document;
import de.hswhameln.typetogether.networking.api.User;
import de.hswhameln.typetogether.networking.types.DocumentCharacter;
import de.hswhameln.typetogether.networking.types.Identifier;

public class LocalDocumentSender {

    private Document serverDocument;
    private User author;

    public LocalDocumentSender(Document serverDocument, User author) {
        this.serverDocument = serverDocument;
        this.author = author;
    }

    /**
     * charBefore and charAfter may be null
     */
    public void addChar(DocumentCharacter charBefore, DocumentCharacter charAfter, char changedChar) {
        DocumentCharacter characterToAdd;
        if(charBefore != null && charAfter != null) {
            characterToAdd = new DocumentCharacter(changedChar, charBefore.getPosition(), charAfter.getPosition(), author.getId());
        } else if(charBefore == null && charAfter != null) {
            throw new UnsupportedOperationException("Github Issue #1");
            //TODO add implementation for negative counts
        } else if(charBefore != null && charAfter == null) {
            characterToAdd = new DocumentCharacter(changedChar, charBefore.getPosition(), author.getId());
        } else { // both null
            characterToAdd = new DocumentCharacter(changedChar, new Identifier(1, author.getId()));
        }
        this.serverDocument.addChar(this.author, characterToAdd);
    }
  
    public void removeChar(DocumentCharacter charToRemove) {
        this.serverDocument.removeChar(author, charToRemove);
    }
    
}