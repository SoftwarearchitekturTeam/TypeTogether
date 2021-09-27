package de.hswhameln.typetogether.client.gui;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import de.hswhameln.typetogether.client.businesslogic.LocalDocument;
import de.hswhameln.typetogether.client.businesslogic.LocalDocumentSender;

public class EditorListener implements DocumentListener {
    
    private LocalDocument localDocument;
    private LocalDocumentSender sender;

    public EditorListener(LocalDocument localDocument, LocalDocumentSender sender) {
        this.localDocument = localDocument;
        this.sender = sender;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        // TODO Auto-generated method stub

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
        // TODO Auto-generated method stub

        //Get Position of Changed Character(s)
        // Get DocumentCharacter for position
        //Remove Character from localDocument
        //Send removeDocumentCharacter with sender        
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        //Plain text components do not fire these events        
    }
}