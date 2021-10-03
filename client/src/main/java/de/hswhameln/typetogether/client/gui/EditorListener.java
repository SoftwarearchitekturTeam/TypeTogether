package de.hswhameln.typetogether.client.gui;

import de.hswhameln.typetogether.client.businesslogic.ClientUser;
import de.hswhameln.typetogether.client.runtime.PropertyChangeManager;
import de.hswhameln.typetogether.client.runtime.SessionStorage;
import de.hswhameln.typetogether.networking.LocalDocument;
import de.hswhameln.typetogether.networking.api.Document;
import de.hswhameln.typetogether.networking.types.DocumentCharacter;
import de.hswhameln.typetogether.networking.types.Identifier;
import de.hswhameln.typetogether.networking.util.DocumentCharacterFactory;
import de.hswhameln.typetogether.networking.util.ExceptionHandler;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import java.beans.PropertyChangeEvent;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EditorListener implements DocumentListener {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private Document sharedDocument;
    private ClientUser user;
    private LocalDocument localDocument;

    private final PropertyChangeManager propertyChangeManager;

    public EditorListener(SessionStorage sessionStorage) {
        this.sharedDocument = sessionStorage.getCurrentSharedDocument();
        this.setUser(sessionStorage.getCurrentUser());

        this.propertyChangeManager = new PropertyChangeManager();
        sessionStorage.addPropertyChangeListener(this.propertyChangeManager);

        this.propertyChangeManager.onPropertyChange(ClientUser.LOCAL_DOCUMENT, this::localDocumentChanged);
        this.propertyChangeManager.onPropertyChange(SessionStorage.CURRENT_USER, this::currentUserChanged);
        this.propertyChangeManager.onPropertyChange(SessionStorage.CURRENT_SHARED_DOCUMENT, this::currentSharedDocumentChanged);

    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        if (e instanceof CustomSwingDocument.MyDefaultDocumentEvent) {
            System.out.println("Stopping event propagation from programmatic insert.");
            return;
        }

        if (this.user == null) {
            this.logger.warning("Author is null. Ignoring insertUpdate");
            return;
        }
        if (this.localDocument == null) {
            this.logger.warning("LocalDocument is null. Ignoring insertUpdate");
            return;
        }
        if (this.sharedDocument == null) {
            this.logger.warning("SharedDocument is null. Ignoring insertUpdate");
            return;
        }

        String update = new StringBuilder(getStringFromDocumentEvent(e)).reverse().toString();
        System.out.println("Update Length: " + update.length());

        for (char c : update.toCharArray()) {
            //Get Position of Changed Character(s)
            int index = e.getOffset() + 1;
            System.out.println("EditorListener#insertUpdate: generating character at index " + index + ", between " + (index - 1) + " and " + (index));
            // Between old indices index - 1 and index <=> between new indices index - 1 and index + 1
            //Get DocumentCharacter for position
            //Get before and after
            DocumentCharacter characterToAdd = this.generateDocumentCharacter(this.localDocument.getDocumentCharacterOfIndex(index - 1),
                    this.localDocument.getDocumentCharacterOfIndex(index), c);
            //Add DocumentCharacter to localDocument (without re-adding it to the editor!)
            this.localDocument.addLocalChar(characterToAdd);

            //Send DocumentCharacter with sender
            this.sharedDocument.addChar(this.user, characterToAdd);
            System.out.println("Added char: " + c);
        }

    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        if (e instanceof CustomSwingDocument.MyDefaultDocumentEvent) {
            System.out.println("Stopping event propagation from programmatic insert.");
            return;
        }

        if (this.user == null) {
            this.logger.warning("Author is null. Ignoring removeUpdate");
            return;
        }
        if (this.localDocument == null) {
            this.logger.warning("LocalDocument is null. Ignoring removeUpdate");
            return;
        }
        if (this.sharedDocument == null) {
            this.logger.warning("SharedDocument is null. Ignoring removeUpdate");
            return;
        }

        for (int i = 0; i < e.getLength(); i++) {
            //Get Position of Changed Character(s)
            int index = e.getOffset() + 1;
            // Get DocumentCharacter for position
            DocumentCharacter characterToRemove = this.localDocument.getDocumentCharacterOfIndex(index);
            //Remove Character from localDocument
            this.localDocument.removeLocalChar(characterToRemove);
            //Send removeDocumentCharacter with sender
            this.sharedDocument.removeChar(this.user, characterToRemove);
        }
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
            ExceptionHandler.getExceptionHandler().handle(e1, Level.SEVERE, "Error getting Position of change in Editor. Update is ignored.", EditorListener.class);
        }
        return update;
    }

    /**
     * charBefore and charAfter may be null
     */
    private DocumentCharacter generateDocumentCharacter(DocumentCharacter charBefore, DocumentCharacter charAfter, char changedChar) {
        System.out.println("EditorListener#generateDocumentCharacter: generating " + changedChar + "  between " + saveGetStringRepr(charBefore) + " and " + saveGetStringRepr(charAfter));
        DocumentCharacter characterToAdd;
        if (charBefore != null && charAfter != null) {
            characterToAdd = DocumentCharacterFactory.getDocumentCharacter(changedChar, charBefore.getPosition(), charAfter.getPosition(), user.getId());
        } else if (charBefore == null && charAfter != null) {
            throw new UnsupportedOperationException("Inserting a char before the first character should not be possible");
        } else if (charBefore != null) {
            characterToAdd = DocumentCharacterFactory.getDocumentCharacter(changedChar, charBefore.getPosition(), user.getId());
        } else {
            characterToAdd = new DocumentCharacter(changedChar, List.of(new Identifier(1, user.getId())));
        }
        return characterToAdd;
    }

    private String saveGetStringRepr(DocumentCharacter charBefore) {
        return charBefore == null ? "null" : charBefore.getStringRepresentation();
    }

    private void localDocumentChanged(PropertyChangeEvent propertyChangeEvent) {
        setLocalDocument((LocalDocument) propertyChangeEvent.getNewValue());
    }

    private void setLocalDocument(LocalDocument newLocalDocument) {
        this.localDocument = newLocalDocument;
    }

    private void currentSharedDocumentChanged(PropertyChangeEvent propertyChangeEvent) {
        this.sharedDocument = (Document) propertyChangeEvent.getNewValue();
    }

    private void currentUserChanged(PropertyChangeEvent propertyChangeEvent) {
        setUser((ClientUser) propertyChangeEvent.getNewValue());
    }

    private void setUser(ClientUser newUser) {
        if (this.user != null) {
            this.user.removePropertyChangeListener(this.propertyChangeManager);
        }
        this.user = newUser;
        if (this.user != null) {
            this.user.addPropertyChangeListener(this.propertyChangeManager);
            this.setLocalDocument((LocalDocument) this.user.getDocument());
        }
    }
}