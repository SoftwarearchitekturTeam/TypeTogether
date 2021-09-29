package de.hswhameln.typetogether.client.gui;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

import de.hswhameln.typetogether.client.businesslogic.LocalDocument;
import de.hswhameln.typetogether.client.businesslogic.LocalDocumentSender;
import de.hswhameln.typetogether.networking.api.User;
import de.hswhameln.typetogether.networking.types.DocumentCharacter;
import de.hswhameln.typetogether.networking.types.Identifier;
import de.hswhameln.typetogether.networking.util.ExceptionHandler;

public class EditorListener implements DocumentListener {

    private LocalDocument localDocument;
    private LocalDocumentSender sender;
    private User author;

    public EditorListener(LocalDocument localDocument, LocalDocumentSender sender, User author) {
        this.localDocument = localDocument;
        this.sender = sender;
        this.author = author;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        if (e instanceof CustomSwingDocument.MyDefaultDocumentEvent) {
            System.out.println("Stopping event propagation from programmatic insert.");
            return;
        }
        String update = getStringFromDocumentEvent(e);
        System.out.println("Update Length: " + update.length());
        for (char c : update.toCharArray()) {
            int index = e.getOffset() + 1;
            DocumentCharacter characterToAdd = this.generateDocumentCharacter(this.localDocument.getDocumentCharacterOfIndex(index - 1),
                    this.localDocument.getDocumentCharacterOfIndex(index + 1), c);
            this.localDocument.addLocalChar(characterToAdd);

            this.sender.addChar(characterToAdd);
            System.out.println("Added char: " + c);
        }
        //Get Position of Changed Character(s)
        //Get DocumentCharacter for position
        //Get before and after
        //Add DocumentCharacter to localDocument (without re-adding it to the editor!)
        //Send DocumentCharacter with sender

        //this.localDocument.addChar(author, character);
        //this.sender.addChar(charBefore, charAfter, changedChar);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        String update = getStringFromDocumentEvent(e);

        int count = 0;
        for (char c : update.toCharArray()) {
            int index = e.getOffset() + count + 1;
            count++;
            DocumentCharacter characterToRemove = this.generateDocumentCharacter(this.localDocument.getDocumentCharacterOfIndex(index - 1),
                    this.localDocument.getDocumentCharacterOfIndex(index + 1), c);
            this.localDocument.removeChar(author, characterToRemove);
            this.sender.removeChar(characterToRemove);
        }
        //Get Position of Changed Character(s)
        // Get DocumentCharacter for position
        //Remove Character from localDocument
        //Send removeDocumentCharacter with sender        
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        //Plain text components do not fire these events        
    }

    private String getStringFromDocumentEvent(DocumentEvent e) {
        String update = "";
        try {
            update = e.getDocument().getText(e.getOffset(), e.getLength());
        } catch (BadLocationException e1) {
            ExceptionHandler.getExceptionHandler().handle(e1, "Error getting Position of change in Editor", EditorListener.class);
        }
        return update;
    }

    /**
     * charBefore and charAfter may be null
     */
    private DocumentCharacter generateDocumentCharacter(DocumentCharacter charBefore, DocumentCharacter charAfter, char changedChar) {
        DocumentCharacter characterToAdd;
        if (charBefore != null && charAfter != null) {
            characterToAdd = new DocumentCharacter(changedChar, charBefore.getPosition(), charAfter.getPosition(), author.getId());
        } else if (charBefore == null && charAfter != null) {
            throw new UnsupportedOperationException("Github Issue #1");
            //TODO add implementation for negative counts
        } else if (charBefore != null && charAfter == null) {
            characterToAdd = new DocumentCharacter(changedChar, charBefore.getPosition(), author.getId());
        } else {
            characterToAdd = new DocumentCharacter(changedChar, new Identifier(1, author.getId()));
        }
        return characterToAdd;
    }
}