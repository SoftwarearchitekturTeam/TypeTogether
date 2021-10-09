package de.hswhameln.typetogether.client.gui;

import de.hswhameln.typetogether.client.businesslogic.ClientUser;
import de.hswhameln.typetogether.client.runtime.PropertyChangeManager;
import de.hswhameln.typetogether.client.runtime.SessionStorage;
import de.hswhameln.typetogether.client.runtime.commands.CommandInvoker;
import de.hswhameln.typetogether.client.runtime.commands.CreateDocumentCharactersCommand;
import de.hswhameln.typetogether.client.runtime.commands.DeleteDocumentCharactersCommand;
import de.hswhameln.typetogether.networking.LocalDocument;
import de.hswhameln.typetogether.networking.api.Document;
import de.hswhameln.typetogether.networking.types.DocumentCharacter;
import de.hswhameln.typetogether.networking.types.Identifier;
import de.hswhameln.typetogether.networking.util.DocumentCharacterFactory;
import de.hswhameln.typetogether.networking.util.ExceptionHandler;
import de.hswhameln.typetogether.networking.util.LoggerFactory;

import javax.print.Doc;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EditorListener implements DocumentListener {

    private final Logger logger = LoggerFactory.getLogger(this);

    private Document sharedDocument;
    private ClientUser user;
    private LocalDocument localDocument;
    private CommandInvoker invoker;

    private final PropertyChangeManager propertyChangeManager;

    public EditorListener(SessionStorage sessionStorage) {
        this.sharedDocument = sessionStorage.getCurrentSharedDocument();
        this.invoker = sessionStorage.getCommandInvoker();
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

        String update = getStringFromDocumentEvent(e);
        this.logger.log(Level.INFO, "insertUpdate called, trying to insert string " + update);

        List<DocumentCharacter> documentCharacters = new ArrayList<>(e.getLength());

        // the char that was the last one to be created and to which the next character from this batch must be appended.
        DocumentCharacter lastCreatedCharacter = null;
        for (char c : update.toCharArray()) {
            //Get Position of Changed Character(s)
            int index = e.getOffset() + 1;
            // Between old indices index - 1 and index <=> between new indices index - 1 and index + 1
            //Get DocumentCharacter for position
            //Get before and after
            DocumentCharacter previousCharacter = lastCreatedCharacter != null ? lastCreatedCharacter : this.localDocument.getDocumentCharacterOfIndex(e.getOffset());
            DocumentCharacter nextCharacter = this.localDocument.getDocumentCharacterOfIndex(e.getOffset() + 1);
            DocumentCharacter characterToAdd = this.generateDocumentCharacter(previousCharacter, nextCharacter, c);
            lastCreatedCharacter = characterToAdd;
            this.logger.log(Level.INFO, "Generated document character " + characterToAdd + " between " + previousCharacter + " and " + nextCharacter + ".");
            documentCharacters.add(characterToAdd);
        }
        this.invoker.execute(new CreateDocumentCharactersCommand(documentCharacters, this.user, this.localDocument, this.sharedDocument));
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        if (e instanceof CustomSwingDocument.MyDefaultDocumentEvent) {
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

        this.logger.log(Level.INFO, "removeUpdate called, trying to remove string of length " + e.getLength() + ", starting at index " + e.getOffset() + ".");

        List<DocumentCharacter> documentCharacters = new ArrayList<>(e.getLength());
        for (int i = 0; i < e.getLength(); i++) {
            //Get Position of Changed Character(s)
            int index = e.getOffset() + i + 1;
            // Get DocumentCharacter for position
            documentCharacters.add(this.localDocument.getDocumentCharacterOfIndex(index));
        }
        this.invoker.execute(new DeleteDocumentCharactersCommand(documentCharacters, this.user, this.localDocument, this.sharedDocument));
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